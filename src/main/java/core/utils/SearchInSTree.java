package core.utils;

import core.structureTree.SNode;
import core.structureTree.structureNode.SFunctionNode;
import core.node.FileNode;
import core.node.Node;

import java.util.ArrayList;
import java.util.List;

public class SearchInSTree {

    public static List<SFunctionNode> searchSFunction(SNode root) {
        List<SFunctionNode> functionNodeList = new ArrayList<>();
        if (root instanceof SFunctionNode) functionNodeList.add((SFunctionNode) root);
        for (SNode child : root.getChildren()) {
            functionNodeList.addAll(searchSFunction(child));
        }
        return functionNodeList;
    }

    public static FileNode getJavaFileNode(SFunctionNode functionNode) {
        Node node = functionNode.getAst();
        while (!(node instanceof FileNode)) {
            node = node.getParent();
        }
        return (FileNode) node;
    }
}
