package core.utils;

import core.cfg.CfgNode;
import core.cfg.CfgReturnStatementNode;
import core.cfg.IEvaluateCoverage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CFGUtils {
    public static CfgNode findCFGNodeByStart(CfgNode root, int start) {
        if (root.getStartPosition() == start) return root;
        for (CfgNode child : root.getChildren()) {
            CfgNode find = findCFGNodeByStart(child, start);
            if (find != null) return find;
        }
        return null;
    }

    public static List<IEvaluateCoverage> findListEvaluateCFGNode(CfgNode root) {
        List<IEvaluateCoverage> list = new ArrayList<>();
        if (root instanceof IEvaluateCoverage) list.add((IEvaluateCoverage) root);
        for (CfgNode child : root.getChildren()) {
            list.addAll(findListEvaluateCFGNode(child));
        }
        return list;
    }

    public static List<CfgReturnStatementNode> findCFGReturnStatementDecreaseSort(CfgNode root) {
        List<CfgReturnStatementNode> list = new ArrayList<>();
        if (root instanceof CfgReturnStatementNode) list.add((CfgReturnStatementNode) root);
        for (CfgNode child : root.getChildren()) {
            list.addAll(findCFGReturnStatementDecreaseSort(child));
        }
        list.sort(new Comparator<CfgReturnStatementNode>() {
            @Override
            public int compare(CfgReturnStatementNode o1, CfgReturnStatementNode o2) {
                if (o1.getStartPosition() == o2.getStartPosition()) return 0;
                return (o1.getStartPosition() > o2.getStartPosition()) ? -1 : 1;
            }
        });
        return list;
    }
}
