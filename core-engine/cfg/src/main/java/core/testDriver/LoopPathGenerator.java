package core.testDriver;

import core.cfg.CfgBoolExprNode;
import core.cfg.CfgForEachExpressionNode;
import core.cfg.CfgNode;
import core.path.Path;

import java.util.*;

public class LoopPathGenerator {

    private static final int MAX_DEPTH = 200;
    private static final int MAX_PATHS = 100;

    // hàm tìm đường đi
    public static List<Path> generateLicoPaths(CfgNode cfgNode, CfgNode endNode) {
        List<Path> result = new ArrayList<Path>();
        Set<String> uniquePaths = new HashSet<>();

        Map<CfgNode, Set<CfgNode>> loops = detectLoops(cfgNode);

        if (loops.isEmpty()) {
            return result;
        }

        CfgNode loopHeader = loops.keySet().iterator().next();

        int[] scenarios = {0, 1, 2, 5};

        for (int k : scenarios) {
            Map<CfgNode, Integer> targets = new HashMap<>();
            targets.put(loopHeader, k);

            dfsLico(cfgNode, new Path(), new HashMap<>(), targets, result, loops, endNode, 0, uniquePaths);
        }
        return result;
    }

    /**
     * hàm tìm vòng lặp.
     * Trả về map với key là loop header
     * value là nút cuối cùng của thân vòng lặp.
     *
     * @param root cây cfg
     * @return trả về map
     */
    private static Map<CfgNode, Set<CfgNode>> detectLoops(CfgNode root) {
        Map<CfgNode, Set<CfgNode>> loops = new HashMap<>();
        Set<CfgNode> visited = new HashSet<>(); // đánh dấu node đã thăm
        Set<CfgNode> recursionStack = new HashSet<>(); // những node đang thăm

        detectLoopsRecursive(root, visited, recursionStack, loops);
        return loops;
    }

    private static void detectLoopsRecursive(CfgNode root, Set<CfgNode> visited,
                                             Set<CfgNode> recursionStack, Map<CfgNode, Set<CfgNode>> loops) {
        if (root == null) return;

        visited.add(root);
        recursionStack.add(root); // đánh dấu node này đang nằm trong stack đệ quy

        List<CfgNode> children = getChildren(root);

        for (CfgNode child : children) {
            if (!visited.contains(child)) {
                detectLoopsRecursive(child, visited, recursionStack, loops);
            } else if (recursionStack.contains(child)) {
                // child đã được thăm và đang ở trong stack nghĩa là nó đang bị lặp lại --> đây là back-edge
                // child chính là loop header, còn root chính là nút cuối của vòng lặp
                loops.computeIfAbsent(child, k -> new HashSet<>()).add(root);
            }
        }

        recursionStack.remove(root); // thăm xong và rút khỏi stack
    }

    /**
     * sinh đường đi
     *
     * @param currentNode      node hiện tại đang xét
     * @param currentPath      đường đi đang xét
     * @param loopCounters     đếm số lần đã lặp
     * @param targetIterations mục tiêu lặp bao lần
     * @param resultPaths      kết quả
     */
    private static void dfsLico(CfgNode currentNode, Path currentPath, Map<CfgNode,
                                        Integer> loopCounters, Map<CfgNode, Integer> targetIterations, List<Path> resultPaths,
                                Map<CfgNode, Set<CfgNode>> loops, CfgNode endNode, int currentLength, Set<String> uniquePaths) {

        currentPath.addLast(currentNode);

        // 1. Check giới hạn
        if (resultPaths.size() >= MAX_PATHS) return;
        if (currentLength > MAX_DEPTH) {
            currentPath.removeLast();
            return;
        }

        // 2. Check đích đến
        if (currentNode == endNode) {
            String pathSignature = currentPath.toString();
            if (!uniquePaths.contains(pathSignature)) {
                uniquePaths.add(pathSignature);
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

        // LỌC ĐƯỜNG ĐI NGAY KHI ĐỨNG TẠI HEADER
        // Nếu currentNode chính là Header của một vòng lặp nào đó
        if (targetIterations.containsKey(currentNode) && currentNode instanceof CfgBoolExprNode) {

            int target = targetIterations.get(currentNode);
            int current = loopCounters.getOrDefault(currentNode, 0);

            CfgBoolExprNode boolNode = (CfgBoolExprNode) currentNode;
            CfgNode trueNode = boolNode.getTrueNode();
            CfgNode falseNode = boolNode.getFalseNode();

            List<CfgNode> allowedNodes = new ArrayList<>();

            // Chưa đủ số lần -> BẮT BUỘC vào thân
            if (current < target) {
                if (trueNode != null) allowedNodes.add(trueNode);
                // (Không add falseNode -> Chặn thoát)
            }
            //Đã đủ (hoặc thừa) số lần -> BẮT BUỘC thoát
            else {
                if (falseNode != null) allowedNodes.add(falseNode);
                // (Không add trueNode -> Chặn lặp tiếp)
            }

            // Cập nhật lại danh sách cần duyệt
            nextNodes = allowedNodes;
        }


        for (CfgNode nextNode : nextNodes) {
            if (resultPaths.size() >= MAX_PATHS) break;

            boolean isBackEdge = false;
            if (loops.containsKey(nextNode)) {
                if (loops.get(nextNode).contains(currentNode)) {
                    isBackEdge = true;
                }
            }

            if (isBackEdge) {
                loopCounters.put(nextNode, loopCounters.getOrDefault(nextNode, 0) + 1);
            }

            dfsLico(nextNode, currentPath, loopCounters, targetIterations, resultPaths, loops, endNode, currentLength + 1, uniquePaths);

            // Backtrack: Cập nhật bộ đếm GIẢM
            if (isBackEdge) {
                int count = loopCounters.get(nextNode);
                if (count > 0) {
                    loopCounters.put(nextNode, count - 1);
                } else {
                    loopCounters.remove(nextNode);
                }
            }
        }

        currentPath.removeLast();
    }

    private static List<CfgNode> getChildren(CfgNode root) {
        List<CfgNode> result = new ArrayList<>();

        if (root instanceof CfgBoolExprNode) {
            CfgBoolExprNode boolNode = (CfgBoolExprNode) root;

            if (boolNode.getTrueNode() != null) {
                result.add(boolNode.getTrueNode());
            }
            if (boolNode.getFalseNode() != null) {
                result.add(boolNode.getFalseNode());
            }
        } else if (root instanceof CfgForEachExpressionNode) {
            CfgForEachExpressionNode feNode = (CfgForEachExpressionNode) root;
            if (feNode.getHasElementAfterNode() != null) {
                result.add(feNode.getHasElementAfterNode());
            }
            if (feNode.getNoMoreElementAfterNode() != null) {
                result.add(feNode.getNoMoreElementAfterNode());
            }
        }

        if (root.getAfterStatementNode() != null) {
            result.add(root.getAfterStatementNode());
        }

        return result;
    }
}