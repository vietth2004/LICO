package com.example.parserservice.dom;

import com.example.parserservice.dom.Xml.XmlTagNode;
import com.example.parserservice.util.exception.JciaIgnore;
import com.example.parserservice.util.helper.NodeHelper;
import com.example.parserservice.util.JciaData;
import com.example.parserservice.util.type.ComponentType;
import com.example.parserservice.util.type.Tier;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.File;
import java.io.Serializable;
import java.util.*;

//@JsonTypeInfo(
//        use = JsonTypeInfo.Id.MINIMAL_CLASS,
//        include = JsonTypeInfo.As.PROPERTY,
//        property = "json_type")
public class Node implements Serializable {
    private static final long serialVersionUID = -1411216676620846129L;
    protected int id;
    protected String name;
    protected String fullyQualifiedName = new String();
    protected String absolutePath;
    protected String entityClass;

    protected String type = "Node";

    @JsonIgnore
    @JciaIgnore
    protected Node parent;
    @JciaIgnore
    protected List<Node> nodeChildren;

    private List<XmlTagNode> children;

    //@JciaIgnore
    protected Set<ComponentType> componentTypes;

//    @JsonIgnore
//    @JciaIgnore
//    protected List<Dependency> dependencies;

    protected boolean isConverted;

    public Node() {
        this.id = JciaData.getInstance().generateNodeId();
        nodeChildren = new ArrayList<>();
//        dependencies = new ArrayList<>();
        componentTypes = new HashSet<>();
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    /****************************************
     * MainController.analyzeChangeSet() are using partId of node
     * which will be removed if wanna change it to relative path
     * @param absolutePath
     ****************************************/
    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public boolean isConverted() {
        return isConverted;
    }

    public void setConverted(boolean converted) {
        isConverted = converted;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getNodeChildren() {
        return nodeChildren;
    }

    public void setNodeChildren(List<Node> nodeChildren) {
        this.nodeChildren = nodeChildren;
    }

    public String getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(String entityClass) {
        this.entityClass = entityClass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void addChild(Node child) {
        if (child != null)
            this.nodeChildren.add(child);
    }

    public void addChildren(List<Node> children) {
        this.nodeChildren.addAll(children);
    }

    public String getName() {
        return name;
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

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

//    public List<Dependency> getDependencies() {
//        return dependencies;
//    }
//
//    public void addDependency(Dependency dependencies) {
//        this.dependencies.add(dependencies);
//    }
//
//    public void addDependencies(List<Dependency> dependencies) {
//        this.dependencies.addAll(dependencies);
//    }
//
//    public void setDependencies(List<Dependency> dependencies) {
//        this.dependencies = dependencies;
//    }

    public Set<ComponentType> getComponentTypes() {
        return componentTypes;
    }

    public void addComponentType(ComponentType type) {
        if (!containComponentType(type))
            this.componentTypes.add(type);

        Node fileNode = NodeHelper.getFileAndTableScopedNode(this);
        if (fileNode.equals(this))
            return;

        if (fileNode != null)
            fileNode.addComponentType(type);
    }


    public boolean containComponentType(ComponentType type) {
        return componentTypes.contains(type);
    }

    public Set<Tier> getTiers() {
        Set<Tier> tiers = new HashSet<>();
        for (ComponentType c : componentTypes) {
            Tier t = c.getTier();
            if (t != null && !tiers.contains(t))
                tiers.add(t);
        }
        return tiers;
    }

    public List<XmlTagNode> getChildren() {
        return children;
    }

    public void setChildren(List<XmlTagNode> children) {
        this.children = children;
    }

    public String getFullyQualifiedName() {
        return fullyQualifiedName;
    }

    public void setFullyQualifiedName(String fullyQualifiedName) {
        this.fullyQualifiedName = fullyQualifiedName;
    }

//    /**
//     * Get nodes called by this node
//     *
//     * @return node list of callee
//     */
//    @JsonIgnore
//    public List<Node> getCallees() {
//        List<Node> callees = new ArrayList<>();
//        for (Dependency d : dependencies) {
//            if (d.getCaller().equals(this)) {
//                callees.add(d.getCallee());
//            }
//        }
//        return callees;
//    }
//
//    @JsonIgnore
//    public List<Node> getCallers() {
//        List<Node> callers = new ArrayList<>();
//        for (Dependency d : dependencies) {
//            if (d.getCallee().equals(this)) {
//                callers.add(d.getCaller());
//            }
//        }
//        return callers;
//    }

    @Override
    public boolean equals(Object obj) {
        Node rhs = (Node) obj;
        return this.getId() == rhs.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
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

    /**
     * Get root node of sub-project which contains given node
     *
     * @param n input node
     * @return root node of sub-project
     */
    public Node getSubProjectNode(Node n) {
        Node root = getRootNode(n);

        if (root == null) {
            return null;
        } else {
            while (root.getNodeChildren().size() == 1) {
                for (Node child : root.getNodeChildren())
                    if (child.getAbsolutePath().endsWith(File.separator + "src")) {
                        return child;
                    }
                root = root.getNodeChildren().get(0);
            }
            return root;
        }
    }

    @JsonIgnore
    public static Node getFileNode(Node n) {
        if (n != null && !(n instanceof FileNode)) {
            return getFileNode(n.getParent());
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
        for (Node child : rootNode.getNodeChildren()) {
            allChildren.add(child);
            allChildren.addAll(doGetAllChildren(child));
        }
        return allChildren;
    }

    /**********************
     *Left - Right
     *
     */

    @JsonIgnore
    protected int left;

    @JsonIgnore
    protected int right;

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public void setLeft(int l) {
        this.left = l;
    }

    public void setRight(int r) {
        this.right = r;
    }

    public int dfs(Node node, int count) {
        node.setLeft(count);
        count++;

        for (Node child : node.getNodeChildren()) {
            count = dfs(child, count);
        }

        node.setRight(count);
        count++;

        return count;
    }

    /******
     *Get Type of Node
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
        return "Node{" +
                "name='" + name + '\'' +
                '}';
    }
}