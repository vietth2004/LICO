package core.testDriver;

import core.cfg.CfgBoolExprNode;
import core.cfg.CfgForEachExpressionNode;
import core.cfg.CfgNode;
import core.path.Path;

import java.util.*;

public class LoopPathGenerator {

    private static final int MAX_DEPTH = 400; // Giới hạn độ sâu tối đa của đường đi
    private static final int MAX_PATHS_PER_SCENARIO = 400; // Giới hạn số lượng đường đi sinh ra cho mỗi kịch bản (k)
    private static final int M = 7; // Giá trị M cố định để test vòng lặp

    /**
     * Sinh các đường đi kiểm thử theo chiến lược LICO.
     *
     * @param cfgNode Node gốc của Control Flow Graph.
     * @param endNode Node kết thúc của CFG.
     * @return Danh sách các đường đi thỏa mãn các kịch bản lặp.
     */
    public static List<Path> generateLicoPaths(CfgNode cfgNode, CfgNode endNode) {
        List<Path> result = new ArrayList<>();

        // 1. Tìm tất cả các vòng lặp (loop header và back-edge)
        Map<CfgNode, Set<CfgNode>> loops = detectLoops(cfgNode);

        if (loops.isEmpty()) {
            return result;
        }

        // Các kịch bản số lần lặp cần test
        //int[] scenarios = {0, 1, 2, 4, M, M + 1};
        int[] scenarios = {3};
        Set<String> globalUniquePaths = new HashSet<>(); // Lưu trữ các đường đi duy nhất trên toàn bộ kịch bản

        for (int k : scenarios) {
            // targets: Map lưu mục tiêu số lần lặp cho mỗi loop header
            Map<CfgNode, Integer> targets = new HashMap<>();

            // conditionToHeaderMap: Ánh xạ từ Node Điều Kiện (thật) về Node Header
            // Mục đích: Lấy counter để kiểm tra logic
            Map<CfgNode, CfgNode> conditionToHeaderMap = new HashMap<>();

            // 2. Thiết lập mục tiêu lặp (k) cho tất cả các vòng lặp tìm được
            for (CfgNode header : loops.keySet()) {
                CfgNode realHeader = header;

                // Xử lý trường hợp đặc biệt: Header tìm thấy là Node Rỗng/Merge Node (không phải BoolExpr)
                // Cần đi xuống 1 bước để lấy Node Điều Kiện thật sự
                if (!(realHeader instanceof CfgBoolExprNode)) {
                    List<CfgNode> children = getChildren(realHeader);

                    // Nếu node con đầu tiên là Node Điều Kiện, lấy nó làm realHeader
                    if (!children.isEmpty() && children.get(0) instanceof CfgBoolExprNode) {
                        realHeader = children.get(0);
                    }
                }

                targets.put(realHeader, k);
                conditionToHeaderMap.put(realHeader, header);
            }

            List<Path> pathsForThisScenario = new ArrayList<>();

            // 3. Gọi DFS để sinh đường đi
            dfsLico(cfgNode, new Path(), new HashMap<>(), targets,
                    pathsForThisScenario, loops, endNode, 0, globalUniquePaths, conditionToHeaderMap);

            result.addAll(pathsForThisScenario);
        }

        return result;
    }

    /**
     * Hàm tìm vòng lặp bằng DFS.
     * Sử dụng tập hợp visited và recursionStack để phát hiện back-edge.
     *
     * @param root Node gốc của CFG.
     * @return Map với Key là loop header (nơi back-edge trỏ về), Value là các nút cuối (nơi back-edge đi ra).
     */
    private static Map<CfgNode, Set<CfgNode>> detectLoops(CfgNode root) {
        Map<CfgNode, Set<CfgNode>> loops = new HashMap<>();
        detectLoopsRecursive(root, new HashSet<>(), new HashSet<>(), loops);
        return loops;
    }

    private static void detectLoopsRecursive(CfgNode root, Set<CfgNode> visited,
                                             Set<CfgNode> recursionStack, Map<CfgNode, Set<CfgNode>> loops) {
        if (root == null) return;

        visited.add(root);
        recursionStack.add(root); // Đánh dấu node đang nằm trong stack đệ quy (đang thăm)

        List<CfgNode> children = getChildren(root);

        for (CfgNode child : children) {
            if (!visited.contains(child)) {
                detectLoopsRecursive(child, visited, recursionStack, loops);
            } else if (recursionStack.contains(child)) {
                // child đã được thăm và đang ở trong stack -> Phát hiện back-edge
                // child là loop header, root là nút cuối vòng lặp (back-edge đi từ root đến child)
                loops.computeIfAbsent(child, k -> new HashSet<>()).add(root);
            }
        }

        recursionStack.remove(root); // Kết thúc thăm và rút khỏi stack
    }

    /**
     * Sinh đường đi bằng thuật toán DFS có giới hạn (LICO).
     */
    private static void dfsLico(CfgNode currentNode, Path currentPath, Map<CfgNode,
                                        Integer> loopCounters, Map<CfgNode, Integer> targetIterations, List<Path> resultPaths,
                                Map<CfgNode, Set<CfgNode>> loops, CfgNode endNode, int currentLength,
                                Set<String> uniquePaths,
                                Map<CfgNode, CfgNode> conditionToHeaderMap) {

        // 1. Giới hạn độ sâu/số lượng đường đi
        if (resultPaths.size() >= MAX_PATHS_PER_SCENARIO || currentLength > MAX_DEPTH) return;

        currentPath.addLast(currentNode);

        // 2. ĐẾN ĐÍCH
        if (currentNode == endNode) {
            String signature = currentPath.toString();
            if (!uniquePaths.contains(signature)) {
                uniquePaths.add(signature);
                resultPaths.add(new Path(currentPath));
            }
            currentPath.removeLast();
            return;
        }

        List<CfgNode> nextNodes = getChildren(currentNode);
        if (nextNodes.isEmpty()) {
            currentPath.removeLast();
            return;
        }

        // 3. LOGIC CHẶN ĐƯỜNG (LICO) - Chỉ thực hiện khi đang đứng ở Node Điều Kiện
        if (targetIterations.containsKey(currentNode) && currentNode instanceof CfgBoolExprNode) {
            int target = targetIterations.get(currentNode);

            // Lấy Node cần đếm (thường là Merge Node) thông qua ánh xạ
            CfgNode counterNode = conditionToHeaderMap.getOrDefault(currentNode, currentNode);
            int current = loopCounters.getOrDefault(counterNode, 0); // Lấy số lần lặp đã đếm

            CfgBoolExprNode CfgboolNode = (CfgBoolExprNode) currentNode;
            List<CfgNode> allowedNodes = new ArrayList<>();

            if (current < target) {
                // Nếu chưa đủ số lần lặp -> Bắt buộc đi nhánh TRUE
                if (CfgboolNode.getTrueNode() != null) allowedNodes.add(CfgboolNode.getTrueNode());
            } else {
                // Nếu đã đủ/vượt số lần lặp -> Bắt buộc đi nhánh FALSE
                if (CfgboolNode.getFalseNode() != null) allowedNodes.add(CfgboolNode.getFalseNode());
            }
            nextNodes = allowedNodes; // Chỉ cho phép đi theo nhánh đã chọn
        }

        for (CfgNode nextNode : nextNodes) {
            if (resultPaths.size() >= MAX_PATHS_PER_SCENARIO) break;

            // Kiểm tra: Nếu nextNode là Header và current là nút cuối -> Đây là back-edge (vừa hoàn thành 1 vòng lặp)
            boolean isBackEdge = loops.containsKey(nextNode) && loops.get(nextNode).contains(currentNode);

            if (isBackEdge) {
                // Tăng bộ đếm cho Loop Header đó
                loopCounters.put(nextNode, loopCounters.getOrDefault(nextNode, 0) + 1);
            }

            dfsLico(nextNode, currentPath, loopCounters, targetIterations,
                    resultPaths, loops, endNode, currentLength + 1, uniquePaths, conditionToHeaderMap);

            // Backtrack: Quay lại trạng thái trước khi đi vào
            if (isBackEdge) {
                int count = loopCounters.get(nextNode);
                if (count > 0) loopCounters.put(nextNode, count - 1);
                else loopCounters.remove(nextNode);
            }

        }
        currentPath.removeLast(); // Rút node hiện tại khỏi đường đi
    }

    /**
     * Trả về danh sách các node con của node hiện tại.
     */
    private static List<CfgNode> getChildren(CfgNode root) {
        List<CfgNode> result = new ArrayList<>();
        if (root instanceof CfgBoolExprNode) {
            CfgBoolExprNode boolNode = (CfgBoolExprNode) root;
            if (boolNode.getTrueNode() != null) result.add(boolNode.getTrueNode());
            if (boolNode.getFalseNode() != null) result.add(boolNode.getFalseNode());
        } else if (root instanceof CfgForEachExpressionNode) {
            CfgForEachExpressionNode feNode = (CfgForEachExpressionNode) root;
            if (feNode.getHasElementAfterNode() != null) result.add(feNode.getHasElementAfterNode());
            if (feNode.getNoMoreElementAfterNode() != null) result.add(feNode.getNoMoreElementAfterNode());
        }
        if (root.getAfterStatementNode() != null) {
            result.add(root.getAfterStatementNode());
        }
        return result;
    }
}