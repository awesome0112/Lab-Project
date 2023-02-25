package core.node;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class JavaNode extends Node {

    @JsonProperty("isFinal")
    protected boolean isFinal = false;

    @JsonIgnore
    protected org.eclipse.jdt.core.dom.ASTNode astNode;

    @JsonProperty("isFinal")
    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    public org.eclipse.jdt.core.dom.ASTNode getAstNode() {
        return astNode;
    }

    public void setAstNode(org.eclipse.jdt.core.dom.ASTNode astNode) {
        this.astNode = astNode;
    }

}
