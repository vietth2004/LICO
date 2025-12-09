package core.testDriver;

import core.cfg.CfgBoolExprNode;
import core.cfg.CfgForEachExpressionNode;
import core.cfg.CfgNode;
import core.path.Path;

import java.util.*;

public class LoopPathGenerator {

    private static final int MAX_DEPTH = 400;
    private static final int MAX_PATHS_PER_SCENARIO = 50;

    // hàm tìm đường đi
    public static List<Path> generateLicoPaths(CfgNode cfgNode, CfgNode endNode) {
        List<Path> result = new ArrayList<Path>();

        Map<CfgNode, Set<CfgNode>> loops = detectLoops(cfgNode);

        if (loops.isEmpty()) {
            return result;
        }

        CfgNode loopHeader = loops.keySet().iterator().next();

        int[] scenarios = {0, 1, 2, 5};

        for (int k : scenarios) {
            Map<CfgNode, Integer> targets = new HashMap<>();
            targets.put(loopHeader, k);

            List<Path> pathsForThisScenario = new ArrayList<>();
            Set<String> uniquePaths = new HashSet<>();

            dfsLico(cfgNode, new Path(), new HashMap<>(), targets, pathsForThisScenario, loops, endNode, 0, uniquePaths);
            result.addAll(pathsForThisScenario);
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
     * sinh đường đi.
     * Hàm DFS tìm kiếm kiểu "Tham lam".
     * Trả về true ngay khi tìm thấy 1 đường đi thỏa mãn để dừng toàn bộ quá trình tìm kiếm cho kịch bản đó.
     *
     * @param currentNode      node hiện tại đang xét
     * @param currentPath      đường đi đang xét
     * @param loopCounters     đếm số lần đã lặp
     * @param targetIterations mục tiêu lặp bao lần
     * @param resultPaths      kết quả
     */
    private static void dfsLico(CfgNode currentNode, Path currentPath, Map<CfgNode,
                                        Integer> loopCounters, Map<CfgNode, Integer> targetIterations, List<Path> resultPaths,
                                Map<CfgNode, Set<CfgNode>> loops, CfgNode endNode, int currentLength,
                                Set<String> uniquePaths) {

        // 1. Giới hạn độ sâu
        if (resultPaths.size() >= MAX_PATHS_PER_SCENARIO) return;
        if (currentLength > MAX_DEPTH) return;

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

        // Chặn Loop Header
        if (targetIterations.containsKey(currentNode) && currentNode instanceof CfgBoolExprNode) {
            int target = targetIterations.get(currentNode);
            int current = loopCounters.getOrDefault(currentNode, 0);
            CfgBoolExprNode boolNode = (CfgBoolExprNode) currentNode;
            List<CfgNode> allowedNodes = new ArrayList<>();

            if (current < target) {
                if (boolNode.getTrueNode() != null) allowedNodes.add(boolNode.getTrueNode());
            } else {
                if (boolNode.getFalseNode() != null) allowedNodes.add(boolNode.getFalseNode());
            }
            nextNodes = allowedNodes;
        }

        for (CfgNode nextNode : nextNodes) {
            if (resultPaths.size() >= MAX_PATHS_PER_SCENARIO) break;


            boolean isBackEdge = loops.containsKey(nextNode) && loops.get(nextNode).contains(currentNode);

            if (isBackEdge) loopCounters.put(nextNode, loopCounters.getOrDefault(nextNode, 0) + 1);

            dfsLico(nextNode, currentPath, loopCounters, targetIterations,
                    resultPaths, loops, endNode, currentLength + 1, uniquePaths);

            if (isBackEdge) {
                int count = loopCounters.get(nextNode);
                if (count > 0) loopCounters.put(nextNode, count - 1);
                else loopCounters.remove(nextNode);
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