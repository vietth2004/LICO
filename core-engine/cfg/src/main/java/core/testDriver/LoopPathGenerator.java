package core.testDriver;

import core.cfg.CfgBoolExprNode;
import core.cfg.CfgForEachExpressionNode;
import core.cfg.CfgNode;
import core.path.Path;

import java.util.*;

public class LoopPathGenerator {

    // hàm tìm đường đi
    public static List<Path> generateLicoPaths(CfgNode cfgNode, CfgNode endNode) {
        List<Path> result = new ArrayList<Path>();

        Map<CfgNode, CfgNode> loops = detectLoops(cfgNode);

        if (loops.isEmpty()) {
            return result;
        }

        CfgNode loopHeader = loops.keySet().iterator().next();

        int[] scenarios = {0, 2, 5, 10};

        for (int k : scenarios) {
            Map<CfgNode, Integer> targets = new HashMap<>();
            targets.put(loopHeader, k);

            dfsLico(cfgNode, new Path(), new HashMap<>(), targets, result, loops, endNode);
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
    private static Map<CfgNode, CfgNode> detectLoops(CfgNode root) {
        HashMap<CfgNode, CfgNode> loops = new HashMap<>();
        Set<CfgNode> visited = new HashSet<>(); // đánh dấu node đã thăm
        Set<CfgNode> recursionStack = new HashSet<>(); // những node đang thăm

        detectLoopsRecursive(root, visited, recursionStack, loops);
        return loops;
    }

    private static void detectLoopsRecursive(CfgNode root, Set<CfgNode> visited,
                                             Set<CfgNode> recursionStack, HashMap<CfgNode, CfgNode> loops) {
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
                loops.put(child, root);
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
                                Map<CfgNode, CfgNode> loops, CfgNode targetEndNode) {
        currentPath.addLast(currentNode);

        currentPath.addLast(currentNode);

        if (currentNode == targetEndNode) {
            resultPaths.add(new Path(currentPath));
            currentPath.removeLast();
            return;
        }

        List<CfgNode> nextNodes = getChildren(currentNode);

        for (CfgNode nextNode : nextNodes) {
            boolean isBackEdge = false;

            if (loops.containsKey(nextNode)) {
                if (loops.get(nextNode) == currentNode) {
                    isBackEdge = true;
                }
            }

            // kiểm tra xem nextNode có là loop header không ?
            if (targetIterations.containsKey(nextNode)) {
                int target = targetIterations.get(nextNode); // lấy số lần muốn lặp loop này
                int current = loopCounters.getOrDefault(nextNode, 0); // số lần lặp hiện tại

                // Nếu current < target: Bắt buộc ta phải đi tiếp vào thân
                // Nếu current = target: thì ta lập tức ngắt đường đi và thoát ra ngoài

                if (currentNode instanceof CfgBoolExprNode) {
                    CfgBoolExprNode boolExprNode = (CfgBoolExprNode) currentNode;

                    if (nextNode == boolExprNode.getTrueNode()) {
                        if (current >= target) continue;
                    }
                    if (nextNode == boolExprNode.getFalseNode()) {
                        if (current < target) continue;
                    }
                }
            }

            // cập nhật bộ đếm khi đi qua Back-edge
            if (isBackEdge) {
                loopCounters.put(nextNode, loopCounters.getOrDefault(nextNode, 0) + 1);
            }

            dfsLico(nextNode, currentPath, loopCounters, targetIterations, resultPaths, loops, targetEndNode);

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
