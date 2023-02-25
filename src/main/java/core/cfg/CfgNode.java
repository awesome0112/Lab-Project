package core.cfg;

import core.parser.ASTHelper;
import core.structureTree.structureNode.SFunctionNode;
import core.utils.Utils;
import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CfgNode
{
    private int startPosition;
    private int endPosition;

    private CfgNode beforeStatementNode;//Lenh ngay truoc
    private CfgNode afterStatementNode;//Lenh ngay sau
    private boolean isBeginCfgNode = false; //Nut dau CFG (nút giả)
    private boolean isEndCfgNode = false; //Nut cuoi CFG (nút giả)

    private String content = "";

    public CfgNode(ASTNode ast)
    {
        this.ast = ast;
        setStartPosition(ast.getStartPosition());
        setEndPosition(ast.getStartPosition() + ast.getLength());

    }

    public CfgNode()
    {
    }

    public ASTNode getAst()
    {
        return ast;
    }

    public void setAst(ASTNode ast)
    {
        this.ast = ast;
        this.content = ast.toString();
    }

    private ASTNode ast;

    public int getStartPosition()
    {
        return startPosition;
    }

    public void setStartPosition(int startPosition)
    {
        this.startPosition = startPosition;
    }

    public int getEndPosition()
    {
        return endPosition;
    }

    public void setEndPosition(int endPosition)
    {
        this.endPosition = endPosition;
    }

    private CfgNode parent;
    private List<CfgNode> children = new ArrayList<>();
    private boolean isVisited = false;

    public String getContent()
    {
        return content;
    }

    public String getContentReport()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public CfgNode getParent()
    {
        return parent;
    }

    public void setParent(CfgNode parent)
    {
        this.parent = parent;
    }

    public List<CfgNode> getChildren()
    {
        return children;
    }

    public void setChildren(List<CfgNode> children)
    {
        this.children = children;
    }

    public boolean isVisited()
    {
        return isVisited;
    }

    public void setVisited(boolean visited)
    {
        isVisited = visited;
    }

    public static CfgNode parseToCFG(SFunctionNode functionNode)
    {
        ASTNode astNode = functionNode.getAst().getAstNode();
        CfgNode cfgNode = new CfgStartNode(astNode);
        ASTHelper.generateCFGTreeFromASTNode(astNode, cfgNode);
        return cfgNode;
    }

    public static CfgNode parserToCFG(String sourceCode)
    {
        CfgNode cfg = new CfgNode();

        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setSource(sourceCode.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        ASTVisitor visitor = new ASTVisitor()
        {
            @Override
            public boolean visit(TypeDeclaration node)
            {

            List<ASTNode> children = Utils.getChildren(node);

            for (ASTNode func : children)
            {

            }

            ASTHelper.generateCFGTreeFromASTNode(node, cfg);
            return true;
            }
        };

        cu.accept(visitor);

        return cfg;
    }




    public static ArrayList<ASTNode> parserToAstFuncList(String sourceCodeFile)
    {
        ArrayList<ASTNode> AstFuncList = new ArrayList<>();
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setSource(sourceCodeFile.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        ASTVisitor visitor = new ASTVisitor()
        {
            @Override
            public boolean visit(TypeDeclaration node)
            {
                Utils.getFunctionChildren(node, AstFuncList);

                return true;
            }
        };

        cu.accept(visitor);

        return AstFuncList;
    }
    public static ASTNode parserToAstFuncList0(String sourceCodeFile, String funcName)
    {
        ArrayList<ASTNode> AstFuncList = new ArrayList<>();
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setSource(sourceCodeFile.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        ASTVisitor visitor = new ASTVisitor()
        {
            @Override
            public boolean visit(TypeDeclaration node)
            {
                List<MethodDeclaration> methods = Arrays.asList(node.getMethods());
                for (MethodDeclaration method : methods) {
                    if (method.isConstructor() == false)
                    {
                        AstFuncList.add(method);
                    }
                }

                return true;
            }
        };

        cu.accept(visitor);

        for (int i = 0; i < AstFuncList.size(); i++)
        {
            if (((MethodDeclaration)AstFuncList.get(i)).getName().getIdentifier().equals("foo"))
            {
                return AstFuncList.get(i);
            }
        }

        return null;
    }

    public String markContent(String testPath)
    {
        return "";
    }

    @Override
    public String toString()
    {
        return "CFGNode{" +
//                "start=" + startPosition +
//                ", end=" + endPosition +
                ("".equals(content)? "null" : ", content='" + content + '\'') +
                ", isRootNode=" + isBeginCfgNode +
                ", isEndNode=" + isEndCfgNode +
                //", children=" + children +
//                ", isVisited=" + isVisited +
                '}';
    }

    public CfgNode getBeforeStatementNode()
    {
        return beforeStatementNode;
    }

    public void setBeforeStatementNode(CfgNode beforeStatementNode)
    {
        this.beforeStatementNode = beforeStatementNode;
    }

    public CfgNode getAfterStatementNode()
    {
        return afterStatementNode;
    }

    public void setAfterStatementNode(CfgNode afterStatementNode)
    {
        this.afterStatementNode = afterStatementNode;
    }

    public boolean getIsBeginCfgNode()
    {
        return isBeginCfgNode;
    }

    public void setIsBeginCfgNode(boolean isBeginCfgNode)
    {
        this.isBeginCfgNode = isBeginCfgNode;
    }

    public boolean getIsEndCfgNode()
    {
        return isEndCfgNode;
    }

    public void setIsEndCfgNode(boolean isEndCfgNode)
    {
        this.isEndCfgNode = isEndCfgNode;
    }
}
