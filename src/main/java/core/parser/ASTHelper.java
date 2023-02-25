package core.parser;

//import com.sun.istack.internal.NotNull;

import core.cfg.*;
import core.utils.Utils;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.internal.compiler.ast.ForeachStatement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ASTHelper
{

    protected static List<String> primitiveTypes = Arrays.asList("boolean", "short", "int", "long", "float", "double", "void");
    protected static List<String> javaLangTypes = Arrays.asList("Boolean", "Byte", "Character.Subset", "Character.UnicodeBlock", "ClassLoader", "Double",
            "Float", "Integer", "Long", "Math", "Number", "Object", "Package", "Process", "Runtime",
            "Short", "String", "StringBuffer", "StringBuilder", "System", "Thread", "ThreadGroup",
            "Throwable", "Void");


    public static String getFullyQualifiedName(Type type, CompilationUnit cu)
    {
        if (type.isParameterizedType())
        {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return getFullyQualifiedTypeName(parameterizedType, cu);
        }
        else if (type.isArrayType())
        {
            ArrayType arrayType = (ArrayType) type;
            return getFullyQualifiedTypeName(arrayType, cu);
        }
        else
        {
            return getFullyQualifiedTypeName(type.toString(), cu);
        }
    }

    protected static String getFullyQualifiedTypeName(ParameterizedType parameterizedType, CompilationUnit cu)
    {
        String result = "";
        String type = parameterizedType.getType().toString();
        result += getFullyQualifiedTypeName(type, cu) + "<";

        List args = parameterizedType.typeArguments();
        if (args.size() == 1)
        {
            String argQualifiedName = getFullyQualifiedTypeName(args.get(0).toString(), cu);
            result += argQualifiedName;
        }
        else
        {
            for (Object arg : args)
            {
                String argQualifiedName = getFullyQualifiedTypeName(arg.toString(), cu);
                result += argQualifiedName + ",";
            }
            result = result.substring(0, result.length() - 1);
        }
        result += ">";
        return result;
    }

    protected static String getFullyQualifiedTypeName(ArrayType arrayType, CompilationUnit cu)
    {
        String result = "";
        result += getFullyQualifiedTypeName(arrayType.getElementType().toString(), cu);
        for (Object dimen : arrayType.dimensions())
        {
            result += dimen.toString();
        }
        return result;
    }

    protected static String getFullyQualifiedTypeName(String typeName, CompilationUnit cu)
    {
        // input is null or input is already a fully qualified type
        if (typeName == null || typeName.contains("."))
        {
            return typeName;
        }

        // is primitive type?
        if (primitiveTypes.contains(typeName))
        {
            return typeName;
        }

        // find in import statements
        for (Object o : cu.imports())
        {
            if (o instanceof ImportDeclaration)
            {
                ImportDeclaration id = (ImportDeclaration) o;
                String idStr = id.getName().getFullyQualifiedName();
                if (idStr.endsWith("." + typeName))
                {
                    return idStr;
                }
            }
        }

        // find in java.lang package
        if (javaLangTypes.contains(typeName))
        {
            return "java.lang." + typeName;
        }

        PackageDeclaration packageDeclaration = cu.getPackage();
        if (packageDeclaration == null)
        {
            return typeName;
        }
        else
        {
            return packageDeclaration.getName() + "." + typeName;
        }
    }

    public static void generateCFGTreeFromASTNode(ASTNode astNode, CfgNode rootCFG)
    {

        List<ASTNode> children = Utils.getChildren(astNode);
        for (ASTNode node : children)
        {
            CfgNode cfgChild = null;
            if (node instanceof IfStatement)
            {
                cfgChild = new CfgIfStatementBlockNode();
            }
            else if (node instanceof TypeDeclaration || node instanceof MethodDeclaration)
            {
                cfgChild = new CfgStartNode();
            }
            else if (node instanceof FieldDeclaration)
            {
                cfgChild = new CfgNode();
            }
            else if (node instanceof Block)
            {
                cfgChild = new CfgBlock();
            }
            else if (node instanceof ExpressionStatement)
            {
                cfgChild = new CfgBoolExprNode();
            }
            else if (node instanceof Expression)
            {
                cfgChild = new CfgExpressionNode();
            }
            else if (node instanceof ReturnStatement)
            {
                cfgChild = new CfgReturnStatementNode();
            }
            else if (node instanceof VariableDeclarationStatement)
            {
                cfgChild = new CfgNormalNode();
            }
            if (cfgChild != null)
            {
                cfgChild.setContent(node.toString());
                cfgChild.setStartPosition(node.getStartPosition());
                cfgChild.setEndPosition(node.getStartPosition() + node.getLength());
                cfgChild.setAst(node);

                cfgChild.setParent(rootCFG);
                rootCFG.getChildren().add(cfgChild);
                generateCFGTreeFromASTNode(node, cfgChild);
            }
        }

    }

    public static CfgNode generateCFGFromASTBlockNode(CfgNode block)
    {
        CfgNode beginStatementNode = block.getBeforeStatementNode();
        CfgNode endStatementNode = block.getAfterStatementNode();

        CfgNode cfgRootNode = null;// = beginStatementNode;

        if (block.getAst() instanceof Block)
        {
            //region Block processing
            List<ASTNode> statements = ((Block) block.getAst()).statements();

            if (statements.size() > 0)
            {
                for (int i = 0; i < statements.size(); i++)
                {
                    ASTNode statement = statements.get(i);

                    CfgNode currentNode = generateCFGForOneStatement(statement, beginStatementNode, endStatementNode);

                    if (currentNode instanceof CfgBoolExprNode)
                    {
                        beginStatementNode = ((CfgBoolExprNode)currentNode).getEndBlockNode();
                    }
                    else if (currentNode instanceof CfgBeginForNode)
                    {
                        beginStatementNode = ((CfgBeginForNode)currentNode).getEndBlockNode();
                    }
                    else if (currentNode instanceof CfgBeginDoNode)
                    {
                        beginStatementNode = ((CfgBeginDoNode)currentNode).getEndBlockNode();
                    }
                    else if (currentNode instanceof CfgBeginForEachNode)
                    {
                        beginStatementNode = ((CfgBeginForEachNode)currentNode).getEndBlockNode();
                    }
                    else
                    {
                        beginStatementNode = currentNode;
                    }

                    if (i == 0)
                    {
                        cfgRootNode = currentNode;
                    }

                }
            }

            //endregion Block processing

            return cfgRootNode;
        }
        else
        {
            ASTNode statement = block.getAst();

            CfgNode currentNode = generateCFGForOneStatement(statement, beginStatementNode, endStatementNode);

            return currentNode;
        }

    }

    //Trong TH beforeNode là câu lệnh điều kiện boolean CfgBoolExprNode thì thenOrElse sẽ xác định ta gắn
    //câu lệnh mới vào afterNode hay falseNode của beforeNode
    //Hàm trả ra là Nút tương ứng với câu lệnh đầu tiên và một danh sách tương ứng với câu lệnh cuối cùng của
    // khối lệnh mà đứng trước nút End của khối
    public static CfgNode generateCFGForOneStatement(ASTNode statement, CfgNode beforeNode,
                                                     CfgNode afterNode)
    {
        CfgNode currentNode;
        if (statement instanceof IfStatement)
        {
            currentNode = new CfgIfStatementBlockNode();

            currentNode.setAst(statement);

            LinkCurrentNode(beforeNode, currentNode, afterNode);

            CfgBoolExprNode beginIfNode = generateCFGFromIfASTNode((CfgIfStatementBlockNode) currentNode);

//            System.out.println("beginIfNode = " + beginIfNode.toString());

            return beginIfNode;
        }
        else if (statement instanceof ForStatement)
        {
            currentNode = new CfgForStatementBlockNode();
            currentNode.setAst(statement);

            LinkCurrentNode(beforeNode, currentNode, afterNode);

            CfgBeginForNode beginForNode = generateCFGFromForASTNode((CfgForStatementBlockNode) currentNode);

//            System.out.println("forRootNode = " + beginForNode.toString());

            return beginForNode;

        }
        else if (statement instanceof EnhancedForStatement)
        {
            //foreach statement
            currentNode = new CfgForEachStatementBlockNode();
            currentNode.setAst(statement);

            LinkCurrentNode(beforeNode, currentNode, afterNode);

            CfgBeginForEachNode beginForEachNode =
                    generateCFGFromForEachASTNode((CfgForEachStatementBlockNode) currentNode);

//            System.out.println("beginForEachNode = " + beginForEachNode.toString());

            return beginForEachNode;

        }
        else if (statement instanceof WhileStatement)
        {
            currentNode = new CfgWhileStatementBlockNode();
            currentNode.setAst(statement);

            LinkCurrentNode(beforeNode, currentNode, afterNode);

            CfgBoolExprNode beginWhileNode = generateCFGFromWhileASTNode((CfgWhileStatementBlockNode) currentNode);

//            System.out.println("beginWhileNode = " + beginWhileNode.toString());

            return beginWhileNode;

        }
        else if (statement instanceof DoStatement)
        {
            currentNode = new CfgDoStatementBlockNode();
            currentNode.setAst(statement);

            LinkCurrentNode(beforeNode, currentNode, afterNode);

            CfgBeginDoNode beginDoNode = generateCFGFromDoASTNode((CfgDoStatementBlockNode) currentNode);

//            System.out.println("beginDoNode = " + beginDoNode.toString());

            return beginDoNode;

        }
        else if (statement instanceof Block)
        {
            currentNode = new CfgBlock();

            currentNode.setAst(statement);

            LinkCurrentNode(beforeNode, currentNode, afterNode);
        }
        else if (statement instanceof ExpressionStatement)
        {
            currentNode = new CfgNormalNode();

            currentNode.setAst(statement);

            LinkCurrentNode(beforeNode, currentNode, afterNode);
        }
        else if (statement instanceof ReturnStatement)
        {
            currentNode = new CfgReturnStatementNode();

            currentNode.setAst(statement);

            LinkCurrentNode(beforeNode, currentNode, afterNode);
        }
        else if (statement instanceof VariableDeclarationStatement)
        {
            currentNode = new CfgNormalNode();

            currentNode.setAst(statement);

            LinkCurrentNode(beforeNode, currentNode, afterNode);
        }
        else
        {
            currentNode = new CfgNormalNode();

            currentNode.setAst(statement);

            LinkCurrentNode(beforeNode, currentNode, afterNode);
        }

        currentNode.setStartPosition(statement.getStartPosition());
        currentNode.setEndPosition(statement.getStartPosition() + statement.getLength());

        currentNode.setBeforeStatementNode(beforeNode);

        return currentNode;
    }

    private static void LinkCurrentNode(CfgNode beforeNode, CfgNode currentNode, CfgNode afterNode)
    {
        beforeNode.setAfterStatementNode(currentNode);
        currentNode.setBeforeStatementNode(beforeNode);

        currentNode.setAfterStatementNode(afterNode);
        afterNode.setBeforeStatementNode(currentNode);

        if (afterNode instanceof CfgEndBlockNode)
        {
            ((CfgEndBlockNode) afterNode).getBeforeEndBoolNodeList().add(currentNode);
        }

    }

    public static CfgBoolExprNode generateCFGFromIfASTNode(CfgIfStatementBlockNode ifCfgNode)
    {
        CfgNode beforeNode = ifCfgNode.getBeforeStatementNode();
        CfgEndBlockNode cfgEndBoolNode = new CfgEndBlockNode();

        CfgNode afterNode = ifCfgNode.getAfterStatementNode();

        cfgEndBoolNode.setAfterStatementNode(afterNode);
        afterNode.setBeforeStatementNode(cfgEndBoolNode);

        //CfgNode ifConditionNode =
        Expression ifConditionAST = ((IfStatement) ifCfgNode.getAst()).getExpression();

        CfgBoolExprNode ifCondition = new CfgBoolExprNode();
        ifCondition.setAst(ifConditionAST);
        ifCondition.setContent(ifConditionAST.toString());

        ifCondition.setBeforeStatementNode(beforeNode);
        beforeNode.setAfterStatementNode(ifCondition);

        Statement thenAST = ((IfStatement) ifCfgNode.getAst()).getThenStatement();
        CfgNode cfgThenNodeBlock = new CfgBlock();

        cfgThenNodeBlock.setAst(thenAST);
        cfgThenNodeBlock.setContent(thenAST.toString());

        cfgThenNodeBlock.setBeforeStatementNode(ifCondition);

        cfgThenNodeBlock.setAfterStatementNode(cfgEndBoolNode);

        CfgNode cfgThenNode = generateCFGFromASTBlockNode(cfgThenNodeBlock);

        ifCondition.setTrueNode(cfgThenNode);

        Statement elseAST = ((IfStatement) ifCfgNode.getAst()).getElseStatement();

        if (elseAST != null)
        {
            CfgNode cfgElseNodeBlock = new CfgBlock();
            cfgElseNodeBlock.setAst(elseAST);
            cfgElseNodeBlock.setContent(elseAST.toString());

            cfgElseNodeBlock.setBeforeStatementNode(ifCondition);

            cfgElseNodeBlock.setAfterStatementNode(cfgEndBoolNode);

            CfgNode cfgElseNode = generateCFGFromASTBlockNode(cfgElseNodeBlock);

            ifCondition.setFalseNode(cfgElseNode);
        }
        else
        {
            ifCondition.setFalseNode(cfgEndBoolNode);
        }

        ifCondition.setEndBlockNode(cfgEndBoolNode);

        return ifCondition;
    }

    public static CfgBeginForNode generateCFGFromForASTNode(CfgForStatementBlockNode forCfgNode)
    {
//        System.out.println("generateCFGFromForASTNode starts...");
        CfgNode beforeNode = forCfgNode.getBeforeStatementNode();
        CfgBeginForNode beginForNode = new CfgBeginForNode();

        beforeNode.setAfterStatementNode(beginForNode);
        beginForNode.setBeforeStatementNode(beforeNode);

        CfgEndBlockNode cfgEndBlockNode = new CfgEndBlockNode();

        CfgNode afterNode = forCfgNode.getAfterStatementNode();

        cfgEndBlockNode.setAfterStatementNode(afterNode);
        afterNode.setBeforeStatementNode(cfgEndBlockNode);

        beginForNode.setEndBlockNode(cfgEndBlockNode);

        List initializers = ((ForStatement) forCfgNode.getAst()).initializers();

        CfgNode tempBeforeNode = beginForNode;

        for (int i = 0;
             i < initializers.size();
             i++)
        {
            CfgNormalNode normalNode = new CfgNormalNode();

            System.out.println("");

            if (initializers.get(i) instanceof VariableDeclarationExpression)
            {
                normalNode.setAst((VariableDeclarationExpression) initializers.get(i));
            }
            else if (initializers.get(i) instanceof Assignment)
            {
                normalNode.setAst((Assignment) initializers.get(i));
            }

            LinkCurrentNode(tempBeforeNode, normalNode, afterNode);

            tempBeforeNode = normalNode;
        }

        //Dieu kien
        Expression forConditionAST = ((ForStatement) forCfgNode.getAst()).getExpression();

        CfgBoolExprNode forConditionNode = new CfgBoolExprNode();
        forConditionNode.setAst(forConditionAST);
        forConditionNode.setContent(forConditionAST.toString());

        LinkCurrentNode(tempBeforeNode, forConditionNode, afterNode);

        //Khoi body
        Statement bodyStatementBlock = ((ForStatement) forCfgNode.getAst()).getBody();
        CfgNode bodyStatementNode = new CfgBlock();

        bodyStatementNode.setAst(bodyStatementBlock);
        bodyStatementNode.setContent(bodyStatementBlock.toString());

        //Updater
        List updaters = ((ForStatement) forCfgNode.getAst()).updaters();

        CfgNode tempBeforeUpdaterNode = bodyStatementNode;

        CfgNode firstUpdaterNode = null;

        for (int i = 0; i < updaters.size(); i++)
        {
            CfgNormalNode normalNode = new CfgNormalNode();

            if (updaters.get(i) instanceof PostfixExpression)
            {
                normalNode.setAst((PostfixExpression) updaters.get(i));
            }
            else if (updaters.get(i) instanceof Assignment)
            {
                normalNode.setAst((Assignment) updaters.get(i));
            }

            LinkCurrentNode(tempBeforeUpdaterNode, normalNode, afterNode);

            tempBeforeUpdaterNode = normalNode;

            if (i == 0)
            {
                firstUpdaterNode = normalNode;
            }
        }

        tempBeforeUpdaterNode.setAfterStatementNode(forConditionNode);

        bodyStatementNode.setAfterStatementNode(firstUpdaterNode);
        bodyStatementNode.setBeforeStatementNode(forConditionNode);

        CfgNode cfgBodyNode = generateCFGFromASTBlockNode(bodyStatementNode);

        forConditionNode.setTrueNode(cfgBodyNode);
        forConditionNode.setFalseNode(cfgEndBlockNode);

        cfgEndBlockNode.getBeforeEndBoolNodeList().add(forConditionNode);
        cfgEndBlockNode.setBeforeStatementNode(forConditionNode);

//        System.out.println("generateCFGFromForASTNode ends...");

        return beginForNode;
    }

    public static CfgBoolExprNode generateCFGFromWhileASTNode(CfgWhileStatementBlockNode whileCfgNode)
    {
//        System.out.println("generateCFGFromWhileASTNode starts...");
        CfgNode beforeNode = whileCfgNode.getBeforeStatementNode();

        CfgEndBlockNode cfgEndBlockNode = new CfgEndBlockNode();

        CfgNode afterNode = whileCfgNode.getAfterStatementNode();

        cfgEndBlockNode.setAfterStatementNode(afterNode);
        afterNode.setBeforeStatementNode(cfgEndBlockNode);

        //Dieu kien
        Expression whileConditionAST = ((WhileStatement) whileCfgNode.getAst()).getExpression();

        CfgBoolExprNode whileConditionNode = new CfgBoolExprNode();
        whileConditionNode.setAst(whileConditionAST);
        whileConditionNode.setContent(whileConditionAST.toString());

        whileConditionNode.setEndBlockNode(cfgEndBlockNode);
        beforeNode.setAfterStatementNode(whileConditionNode);
        whileConditionNode.setBeforeStatementNode(beforeNode);

        //Khoi body
        Statement bodyStatementBlock = ((WhileStatement) whileCfgNode.getAst()).getBody();
        CfgNode bodyStatementNode = new CfgBlock();

        bodyStatementNode.setAst(bodyStatementBlock);
        bodyStatementNode.setContent(bodyStatementBlock.toString());

        bodyStatementNode.setAfterStatementNode(whileConditionNode);
        bodyStatementNode.setBeforeStatementNode(whileConditionNode);

        CfgNode cfgBodyNode = generateCFGFromASTBlockNode(bodyStatementNode);

        whileConditionNode.setTrueNode(cfgBodyNode);
        whileConditionNode.setFalseNode(cfgEndBlockNode);

        cfgEndBlockNode.getBeforeEndBoolNodeList().add(whileConditionNode);
        cfgEndBlockNode.setBeforeStatementNode(whileConditionNode);

//        System.out.println("generateCFGFromWhileASTNode ends...");

        return whileConditionNode;
    }

    public static CfgBeginDoNode generateCFGFromDoASTNode(CfgDoStatementBlockNode doCfgNode)
    {
//        System.out.println("generateCFGFromDoASTNode starts...");
        CfgNode beforeNode = doCfgNode.getBeforeStatementNode();

        CfgEndBlockNode cfgEndBlockNode = new CfgEndBlockNode();

        CfgNode afterNode = doCfgNode.getAfterStatementNode();

        cfgEndBlockNode.setAfterStatementNode(afterNode);
        afterNode.setBeforeStatementNode(cfgEndBlockNode);

        CfgBeginDoNode beginDoNode = new CfgBeginDoNode();
        beginDoNode.setBeforeStatementNode(beforeNode);
        beforeNode.setAfterStatementNode(beginDoNode);

        beginDoNode.setEndBlockNode(cfgEndBlockNode);

        //Khoi body
        Statement bodyStatementBlock = ((DoStatement) doCfgNode.getAst()).getBody();
        CfgNode bodyStatementNode = new CfgBlock();

        bodyStatementNode.setAst(bodyStatementBlock);
        bodyStatementNode.setContent(bodyStatementBlock.toString());

        //Dieu kien
        Expression doConditionAST = ((DoStatement) doCfgNode.getAst()).getExpression();

        CfgBoolExprNode doConditionNode = new CfgBoolExprNode();
        doConditionNode.setAst(doConditionAST);
        doConditionNode.setContent(doConditionAST.toString());

        doConditionNode.setEndBlockNode(cfgEndBlockNode);

        bodyStatementNode.setAfterStatementNode(doConditionNode);
        bodyStatementNode.setBeforeStatementNode(beginDoNode);

        CfgNode cfgBodyNode = generateCFGFromASTBlockNode(bodyStatementNode);

        doConditionNode.setTrueNode(cfgBodyNode);
        doConditionNode.setFalseNode(cfgEndBlockNode);

        cfgEndBlockNode.getBeforeEndBoolNodeList().add(doConditionNode);
        cfgEndBlockNode.setBeforeStatementNode(doConditionNode);

//        System.out.println("generateCFGFromDoASTNode ends...");

        return beginDoNode;
    }

    public static CfgBeginForEachNode generateCFGFromForEachASTNode(CfgForEachStatementBlockNode forEachCfgNode)
    {
//        System.out.println("generateCFGFromForEachASTNode starts...");
        CfgNode beforeNode = forEachCfgNode.getBeforeStatementNode();

        CfgEndBlockNode cfgEndBlockNode = new CfgEndBlockNode();

        CfgNode afterNode = forEachCfgNode.getAfterStatementNode();

        cfgEndBlockNode.setAfterStatementNode(afterNode);
        afterNode.setBeforeStatementNode(cfgEndBlockNode);

        CfgBeginForEachNode beginForEachNode = new CfgBeginForEachNode();
        beginForEachNode.setBeforeStatementNode(beforeNode);
        beforeNode.setAfterStatementNode(beginForEachNode);

        beginForEachNode.setEndBlockNode(cfgEndBlockNode);

        //Khoi expression
        Expression expressionAST = ((EnhancedForStatement) forEachCfgNode.getAst()).getExpression();
        CfgForEachExpressionNode expressionNode = new CfgForEachExpressionNode();
        expressionNode.setAst(expressionAST);

        //Khoi parameter
        SingleVariableDeclaration parameterAST = ((EnhancedForStatement) forEachCfgNode.getAst()).getParameter();
        CfgNormalNode parameterNode = new CfgNormalNode();
        parameterNode.setAst(parameterAST);

        beginForEachNode.setAfterStatementNode(expressionNode);
        expressionNode.setBeforeStatementNode(beginForEachNode);
        expressionNode.setParameterNode(parameterNode);

        //Khoi body
        Statement bodyStatementBlock = ((EnhancedForStatement) forEachCfgNode.getAst()).getBody();
        CfgNode bodyStatementNode = new CfgBlock();

        bodyStatementNode.setAst(bodyStatementBlock);
        bodyStatementNode.setContent(bodyStatementBlock.toString());

        LinkCurrentNode(expressionNode, bodyStatementNode, expressionNode);

        CfgNode cfgBodyNode = generateCFGFromASTBlockNode(bodyStatementNode);

        expressionNode.setHasElementAfterNode(cfgBodyNode);
        expressionNode.setNoMoreElementAfterNode(cfgEndBlockNode);

        cfgEndBlockNode.getBeforeEndBoolNodeList().add(expressionNode);
        cfgEndBlockNode.setBeforeStatementNode(expressionNode);

//        System.out.println("generateCFGFromDoASTNode ends...");

        return beginForEachNode;
    }
}
