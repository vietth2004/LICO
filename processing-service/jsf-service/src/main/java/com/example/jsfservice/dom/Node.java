package com.example.jsfservice.dom;

import com.example.jsfservice.dom.Xml.XmlTagNode;
import com.example.jsfservice.utils.Exception.JciaIgnore;
import com.example.jsfservice.utils.Helper.NodeHelper;
import com.example.jsfservice.utils.JciaData;
import com.example.jsfservice.utils.Type.ComponentType;
import com.example.jsfservice.utils.Type.Tier;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    protected String status;

    @JsonIgnore
    @JciaIgnore
    protected Node parent;
    @JciaIgnore
    protected List<Node> nodeChildren;

    protected List<XmlTagNode> children;

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

    public List getNodeChildren() {
        return this.children;
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

    public void addChild(Node child) {
        if (child != null)
            this.nodeChildren.add(child);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<XmlTagNode> getChildren() {
        return children;
    }

    public void setChildren(List<XmlTagNode> children) {
        this.children = children;
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
        final int prime = 31;
        int result = 1;
        result = prime * result + ((absolutePath == null) ? 0 : absolutePath.hashCode());
        result = prime * result + (id ^ (id >>> 32));
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
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
                for (Node child : root.getChildren())
                    if (child.getAbsolutePath().endsWith(File.separator + "src")) {
                        return child;
                    }
                root = root.getChildren().get(0);
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
        for (Node child : rootNode.getChildren()) {
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

        for (Node child : node.getChildren()) {
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
                ", children='" + getNodeChildren() + '\'' +
                '}';
    }

}