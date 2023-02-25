package core.testdata;

import core.structureTree.structureNode.SFunctionNode;

public class SubProgramDataNode extends DataNode {
    private SFunctionNode functionNode;

    public SFunctionNode getFunctionNode() {
        return functionNode;
    }

    public void setFunctionNode(SFunctionNode functionNode) {
        this.functionNode = functionNode;
    }

    public SubProgramDataNode(SFunctionNode functionNode) {
        this.functionNode = functionNode;
        setName(functionNode.getName());
        setType("Unit under test");
    }
}
