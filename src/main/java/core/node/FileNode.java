package core.node;

import core.structureTree.SNode;
import core.structureTree.SProjectNode;

public class FileNode extends Node {

    @Override
    public SNode parseToSNode() {
        SNode sNode = new SProjectNode();
        //todo: config more attribution here
        sNode.setName(getName());
        sNode.setType(getObjectType());
        return sNode;
    }
}
