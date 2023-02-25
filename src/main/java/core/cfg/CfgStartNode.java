package core.cfg;

import org.eclipse.jdt.core.dom.ASTNode;

public class CfgStartNode extends CfgNode
{

    public CfgStartNode(){}
    public CfgStartNode(ASTNode ast) {
        super(ast);
    }
}
