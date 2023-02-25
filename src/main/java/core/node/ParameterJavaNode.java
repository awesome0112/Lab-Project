package core.node;

import core.structureTree.SNode;
import core.structureTree.normalNode.SNormalCharNode;
import core.structureTree.normalNode.SNormalDoubleNode;
import core.structureTree.normalNode.SNormalFloatNode;
import core.structureTree.normalNode.SNormalIntegerNode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParameterJavaNode extends JavaNode {
    protected String type;
    protected boolean isFinal;

    public ParameterJavaNode() {

    }

    public ParameterJavaNode(String name, String type) {
        super();
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return "ASTParameterJavaNode{" +
                "type='" + type + '\'' +
                '}';
    }

    @Override
    public SNode parseToSNode() {
        SNode sNode = null;
        String type = getType();
        switch (type) {
            case "int": {
                sNode = new SNormalIntegerNode();
                //todo: config here

            }
            case "char" : {
                sNode = new SNormalCharNode();
                //todo: config here

            }
            case "float" : {
                sNode = new SNormalFloatNode();
                //todo: config here

            }
            case "double": {
                sNode = new SNormalDoubleNode();
                //todo: config here

            }
            default: {

            }
        }
        if (sNode == null) return null;
        sNode.setName(getName());
        sNode.setType(getType());
        return sNode;
    }
}
