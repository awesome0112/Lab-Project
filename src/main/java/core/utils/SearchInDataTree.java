package core.utils;

import core.testdata.DataNode;
import core.testdata.SubProgramDataNode;

public class SearchInDataTree {

    public static SubProgramDataNode searchSubprogramNode(DataNode dataNode) {
        for (DataNode child : dataNode.getChildren()) {
            if (child instanceof SubProgramDataNode) return (SubProgramDataNode) child;
            SubProgramDataNode subProgramDataNode = searchSubprogramNode(child);
            if (subProgramDataNode != null) return subProgramDataNode;
        }
        return null;
    }
}
