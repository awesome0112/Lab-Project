package core.cfg;

import core.utils.Utils;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.IfStatement;

public class CfgIfStatementBlockNode extends CfgNode implements IEvaluateCoverage
{
    public CfgIfStatementBlockNode(){}

    public CfgIfStatementBlockNode(ASTNode astNode)
    {
        this.setAst(astNode);
    }

    @Override
    public String markContent(String testPath) {
        StringBuilder content = new StringBuilder("");
        content.append(getStartPosition()).append(getClass().getSimpleName()).append("{StartAt:" + getStartPosition()+ ",").append("EndAt:" + getEndPosition());
//        content.append(toString());
        return Utils.getWriteToTestPathContent(String.valueOf(content), testPath);
//        return "System.out.println(\"" + content + "\");";
    }

    @Override
    public String getContentReport() {
        ASTNode ast = getAst();
        if (ast instanceof IfStatement) return ((IfStatement) ast).getExpression().toString();
        return getContent();
    }

}
