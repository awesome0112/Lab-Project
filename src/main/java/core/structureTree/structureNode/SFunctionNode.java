package core.structureTree.structureNode;

import core.node.MethodAbstractableElementVisibleElementJavaNode;

public class SFunctionNode extends SAbstractableNode {

    private MethodAbstractableElementVisibleElementJavaNode ast;

    public MethodAbstractableElementVisibleElementJavaNode getAst() {
        return ast;
    }

    public void setAst(MethodAbstractableElementVisibleElementJavaNode ast) {
        this.ast = ast;
    }
}
