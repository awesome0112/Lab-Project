package core.node;

import com.fasterxml.jackson.annotation.JsonIgnore;
import core.structureTree.SNode;
import core.structureTree.SProjectNode;


import java.util.ArrayList;
import java.util.List;


public class FolderNode extends Node {



    @JsonIgnore
    public List<ClassAbstractableElementVisibleElementJavaNode> getClassNodes() {
        List<ClassAbstractableElementVisibleElementJavaNode> result = new ArrayList<>();
        for (Node child : this.getChildren()) {
            if (child instanceof ClassAbstractableElementVisibleElementJavaNode)
                result.add((ClassAbstractableElementVisibleElementJavaNode) child);
            else if (child instanceof FolderNode) {
                result.addAll(((FolderNode) child).getClassNodes());
            }
        }
        return result;
    }

    @JsonIgnore
    public ArrayList<ASTRelationshipNode> getClassRelationships() {
        ArrayList<ASTRelationshipNode> ASTRelationshipNodeList = new ArrayList<ASTRelationshipNode>();
        List<ClassAbstractableElementVisibleElementJavaNode> classes = this.getClassNodes();

        for (ClassAbstractableElementVisibleElementJavaNode cd : classes) {
            if (cd.getParentClass() != null) {
                int keySuperClass = this.findIdByQualifiedName(cd.getParentClass(), classes);
                if (keySuperClass != -1) {
                    ASTRelationshipNode r = new ASTRelationshipNode();
                    r.setFrom(cd.getId());
                    r.setTo(keySuperClass);
                    r.setRelationship(ASTRelationshipNode.CLASS_EXTENSION);
                    ASTRelationshipNodeList.add(r);
                }
            }

            if (cd.getInterfaceList() != null) {
                for (String s : cd.getInterfaceList()) {
                    int keySuperInterface = this.findIdByQualifiedName(s, classes);
                    if (keySuperInterface != -1) {
                        ASTRelationshipNode r = new ASTRelationshipNode();
                        r.setFrom(cd.getId());
                        r.setTo(keySuperInterface);
                        if (cd.isInterface()) r.setRelationship(ASTRelationshipNode.CLASS_EXTENSION);
                        else r.setRelationship(ASTRelationshipNode.CLASS_IMPLEMENTATION);
                        ASTRelationshipNodeList.add(r);
                    }
                }
            }
        }
        return ASTRelationshipNodeList;
    }

    public int findIdByQualifiedName(String name, List<ClassAbstractableElementVisibleElementJavaNode> classes) {
        if (classes.size() > 0) {
            for (ClassAbstractableElementVisibleElementJavaNode cd : classes) {
                if (name.equals(cd.getQualifiedName())) return cd.getId();
            }
            return -1;
        }
        return -1;
    }

    @Override
    public SNode parseToSNode() {
        SNode sNode = new SProjectNode();
        //todo: config more attribution here
        sNode.setName(getName());
        sNode.setType(getObjectType());
        return sNode;
    }
}
