package core.cfg;

import core.utils.Utils;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.IfStatement;

public class CfgBoolExprNode extends CfgNode implements IEvaluateCoverage
{
    private CfgNode trueNode = null;
    private CfgNode falseNode = null;
    private CfgEndBlockNode endBlockNode = null;

    @Override
    public String markContent(String testPath) {
        StringBuilder content = new StringBuilder("");
        content.append(getStartPosition()).append(getClass().getSimpleName()).append("{StartAt:" + getStartPosition() + ",").append("EndAt:" + getEndPosition());
//        content.append(toString());
        return Utils.getWriteToTestPathContent(String.valueOf(content), testPath);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String getContentReport() {
        ASTNode ast = getAst();
        if (ast instanceof IfStatement) return ((IfStatement) ast).getExpression().toString();
        return getContent();
    }

    public CfgNode getTrueNode()
    {
        return trueNode;
    }

    public void setTrueNode(CfgNode trueNode)
    {
        this.trueNode = trueNode;
    }

    public CfgNode getFalseNode()
    {
        return falseNode;
    }

    public void setFalseNode(CfgNode falseNode)
    {
        this.falseNode = falseNode;
    }

    public CfgEndBlockNode getEndBlockNode()
    {
        return endBlockNode;
    }

    public void setEndBlockNode(CfgEndBlockNode endBlockNode)
    {
        this.endBlockNode = endBlockNode;
    }
}
