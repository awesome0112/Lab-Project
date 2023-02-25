package core.testdata;

import java.util.ArrayList;
import java.util.List;

public abstract class DataNode {
    private String name;
    private String type;
    private List<DataNode> children = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<DataNode> getChildren() {
        return children;
    }

    public void setChildren(List<DataNode> children) {
        this.children = children;
    }
}
