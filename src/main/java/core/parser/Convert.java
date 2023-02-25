package core.parser;

import core.node.ClassAbstractableElementVisibleElementJavaNode;
import core.node.FieldVisibleElementJavaNode;
import core.node.MethodAbstractableElementVisibleElementJavaNode;
import core.node.Node;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.ArrayList;
import java.util.List;

public class Convert {
    public static List<Node> convertASTListNodeToFieldNode(FieldDeclaration[] fields) {
        List<Node> result = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            List<FieldVisibleElementJavaNode> fieldNodes = FieldVisibleElementJavaNode.setInforFromASTNode(fields[i]);
            result.addAll(fieldNodes);
        }
        return result;
    }

    public static List<Node> convertASTListNodeToMethodNode(MethodDeclaration[] methods, CompilationUnit cu) {
        List<Node> result = new ArrayList<>();
        for (int i = 0; i < methods.length; i++) {
            MethodAbstractableElementVisibleElementJavaNode methodNode = new MethodAbstractableElementVisibleElementJavaNode();
            methodNode.setInforFromASTNode(methods[i], cu);
            methodNode.setAstNode(methods[i]);
            result.add(methodNode);
        }
        return result;
    }


    public static List<Node> convertASTListNodeToClassNode(TypeDeclaration[] innerClasses, CompilationUnit cu) {
        List<Node> result = new ArrayList<>();
        for (int i = 0; i < innerClasses.length; i++) {
            ClassAbstractableElementVisibleElementJavaNode classNode = new ClassAbstractableElementVisibleElementJavaNode();
            classNode.setInforFromASTNode(innerClasses[i], cu);
            result.add(classNode);
        }
        return result;
    }

}
