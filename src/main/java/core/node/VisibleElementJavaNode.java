package core.node;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VisibleElementJavaNode extends JavaNode {

    public static final String PUBLIC_MODIFIER = "public";
    public static final String DEFAULT_MODIFIER = "default";
    public static final String PROTECTED_MODIFIER = "protected";
    public static final String PRIVATE_MODIFIER = "private";

    @JsonProperty("visibility")
    private String visibility = DEFAULT_MODIFIER;

    @JsonProperty("isStatic")
    private boolean isStatic = false;

    @JsonProperty("isStatic")
    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
}
