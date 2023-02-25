package core.node;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AbstractableElementVisibleElementJavaNode extends VisibleElementJavaNode {

    protected boolean isAbstract = false;

    @JsonProperty("isAbstract")
    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean anAbstract) {
        isAbstract = anAbstract;
    }
}
