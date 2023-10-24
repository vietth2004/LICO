package core.dataStructure;

import core.cfg.CfgBoolExprNode;
import core.cfg.CfgNode;

import java.util.ArrayList;
import java.util.List;

public final class MarkedPath {

    private static List<MarkedStatement> markedStatements = new ArrayList<>();

    private MarkedPath() {
    }

    public static boolean markOneStatement(String statement, boolean isTrueCondition, boolean isFalseCondition) {
        tmpAdd(statement, isTrueCondition, isFalseCondition);
        if (!isTrueCondition && !isFalseCondition) return true;
        return !isFalseCondition;
    }

    public static List<MarkedStatement> markPathToCFG(CfgNode rootNode) {
//        List<CfgNode> coveredStatements = new ArrayList<>();
        List<MarkedStatement> result = markedStatements;

        int i = 0;
        while (rootNode != null && i < markedStatements.size()) {
            // Kiểm tra những CfgNode không có content
            if(rootNode.getContent().equals("")) {
                rootNode.setMarked(true);
                rootNode = rootNode.getAfterStatementNode();
                continue;
            }

            MarkedStatement markedStatement = markedStatements.get(i);
            if (rootNode.getContent().equals(markedStatement.getStatement())) {
                rootNode.setMarked(true);
                markedStatement.setCfgNode(rootNode);
//                coveredStatements.add(rootNode);
            } else {
                reset();
                return result;
//                return coveredStatements;
            }

            if (rootNode instanceof CfgBoolExprNode) {
                CfgBoolExprNode boolExprNode = (CfgBoolExprNode) rootNode;
                if (markedStatement.isFalseConditionalStatement()) {
                    boolExprNode.setFalseMarked(true);
                    rootNode = boolExprNode.getFalseNode();
                } else if (markedStatement.isTrueConditionalStatement()) {
                    boolExprNode.setTrueMarked(true);
                    rootNode = boolExprNode.getTrueNode();
                }
                i++;
                continue;
            }

            // Updater
            i++;
            rootNode = rootNode.getAfterStatementNode();
        }
        while (rootNode != null) {
            if(rootNode.getContent().equals("")) {
                rootNode.setMarked(true);
                rootNode = rootNode.getAfterStatementNode();
            }
        }

        reset();
        return result;
//        return coveredStatements;
    }

    public static List<String> getMarkedStatementsStringList() {
        List<String> result = new ArrayList<>();

        for(MarkedStatement markedStatement : markedStatements) {
            result.add(markedStatement.getStatement());
        }

        return result;
    }

    public static CfgNode findUncoveredNode(CfgNode rootNode, CfgNode duplicateNode) {
        if (rootNode == null || !rootNode.isMarked()) {
            return rootNode;
        }
        if (rootNode instanceof CfgBoolExprNode) {
            CfgBoolExprNode boolExprNode = (CfgBoolExprNode) rootNode;



            if (!boolExprNode.isTrueMarked()) {
                return boolExprNode.getTrueNode();
            }
            if (!boolExprNode.isFalseMarked()) {
                return boolExprNode.getFalseNode();
            }

            if(boolExprNode != duplicateNode) {
                duplicateNode = boolExprNode;
                return findUncoveredNode(boolExprNode.getTrueNode(), duplicateNode);
            }
            else {
                return findUncoveredNode(boolExprNode.getFalseNode(), duplicateNode);
            }
        }

        CfgNode cfgNode =  findUncoveredNode(rootNode.getAfterStatementNode(), duplicateNode);
        return cfgNode;
    }

//    public static CfgNode findUncoveredNode(CfgNode rootNode, CfgNode duplicateNode) {
//        if (rootNode == null || !rootNode.isMarked()) {
//            return rootNode;
//        }
//        if (rootNode instanceof CfgBoolExprNode) {
//            CfgBoolExprNode boolExprNode = (CfgBoolExprNode) rootNode;
//
//            // Check for duplicateNode. Nếu có node trùng lặp tức là đã duyệt qua 1 vòng của loop đấy và k thấy node chưa mark nên return null.
//            if(boolExprNode != duplicateNode) duplicateNode = boolExprNode;
//            else {
//                return null;
//            }
//
//            if (!boolExprNode.isTrueMarked()) {
//                return boolExprNode.getTrueNode();
//            }
//            if (!boolExprNode.isFalseMarked()) {
//                return boolExprNode.getFalseNode();
//            }
//
//            CfgNode falseBranchUncoveredNode = findUncoveredNode(boolExprNode.getFalseNode(), duplicateNode);
//            CfgNode trueBranchUncoveredNode = findUncoveredNode(boolExprNode.getTrueNode(), duplicateNode);
////            if(tmpUncoveredNode == null) {
////                return findUncoveredNode(boolExprNode.getTrueNode(), duplicateNode);
////            } else {
////                return tmpUncoveredNode;
////            }
//            return falseBranchUncoveredNode == null ? trueBranchUncoveredNode : falseBranchUncoveredNode;
//        }
//
//        CfgNode cfgNode =  findUncoveredNode(rootNode.getAfterStatementNode(), duplicateNode);
//        return cfgNode;
//    }

    private static void reset() {
        markedStatements = new ArrayList<>();
    }

    public static void tmpAdd(String statement, boolean isTrueCondition, boolean isFalseCondition) {
        MarkedStatement markedStatement = new MarkedStatement(statement, isTrueCondition, isFalseCondition);
        markedStatements.add(markedStatement);
    }

    public static List<MarkedStatement> getMarkedStatements() {
        return markedStatements;
    }
}
