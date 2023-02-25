package core.node;

import core.cfg.CfgNode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import core.structureTree.SNode;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.MINIMAL_CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "json_type")

public class Node implements Serializable {

    private static final long serialVersionUID = -1411216676620846129L;

    public static int countId = 0;
    protected int id;
    protected String name;
    protected int line;

    protected String absolutePath;

    protected int parentNodeId;
    protected int startPosition;
    private CfgNode cfg;

    @JsonIgnore
    protected Node parent;
    protected List<Node> children;

    public CfgNode getCfg() {
        return cfg;
    }

    public void setCfg(CfgNode cfg) {
        this.cfg = cfg;
    }


    public static int getCountId() {
        return countId;
    }

    public static void setCountId(int countId) {
        Node.countId = countId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
        setChildrenAbsolutePath();
    }

    public int getParentNodeId() {
        return parentNodeId;
    }

    public void setParentNodeId(int parentNodeId) {
        this.parentNodeId = parentNodeId;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
        this.parentNodeId = parent.getId();
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public Node() {
        children = new ArrayList<>();
        countId++;
        this.id = countId;
    }

    public void addChildren(List<Node> children, CompilationUnit cu) {
        for (Node Node : children) {
            int lineNumber = cu.getLineNumber(Node.getStartPosition());
            Node.setLine(lineNumber);
            Node.setParentNodeId(this.getId());
            Node.setParent(this);
            this.children.add(Node);
        }
    }

    public void addChildrenFolder(List<? extends Node> children) {
        for (Node Node : children) {
            Node.setParentNodeId(this.getId());
            Node.setParent(this);
            this.children.add(Node);
        }
    }

    public void setName(String name) {
        this.name = name;
        if (absolutePath == null) {
            setAbsolutePathByName();
        }
    }

    public void setAbsolutePathByName() {
        if (this.parent != null) {
            setAbsolutePath(this.parent.absolutePath + File.separator + this.name);
        }
    }

    /**
     * Get relative path of node
     *
     * @return relative path of node
     */
    @JsonIgnore
    public String getRelativePath() {
        Node root = getRootNode(this);
        if (root != null) {
            return getAbsolutePath().replace(root.getAbsolutePath() + File.separator, "");
        } else return getAbsolutePath();
    }

    /**
     * Get root node of project which contains given input node
     *
     * @param n
     * @return
     */
    public Node getRootNode(Node n) {
        if (n.getParent() != null) {
            return getRootNode(n.getParent());
        }
        return n;
    }

    /**************************************
     * Use getters, setters instead of direct attribute assignment
     * for following methods (due to these methods are not
     * overridden by derive Decorator)
     ***************************************/

    @JsonIgnore
    public List<Node> getAllChildren() {
        return doGetAllChildren(this);
    }

    private List<Node> doGetAllChildren(Node rootNode) {
        List<Node> allChildren = new ArrayList<>();
        for (Node child : rootNode.getChildren()) {
            allChildren.add(child);
            allChildren.addAll(doGetAllChildren(child));
        }
        return allChildren;
    }

    /******
     *Get Type of ASTNode
     *
     *
     */
    //protected string type;
    @JsonIgnore
    public String getObjectType() {
        Class<?> enclosingClass = this.getClass().getEnclosingClass();
        String nodeClass = enclosingClass != null ? enclosingClass.getSimpleName() : this.getClass().getSimpleName();
        return nodeClass;
    }

    @Override
    public String toString() {
        return "ASTNode{" +
                "name='" + name + '\'' +
                '}';
    }

    public SNode parseToSNode() {
        return null;
    }

    public void setChildrenAbsolutePath() {
        for (Node child : children) {
            child.setAbsolutePath(absolutePath + "/" + child.getName());
            child.setChildrenAbsolutePath();
        }
    }
}
