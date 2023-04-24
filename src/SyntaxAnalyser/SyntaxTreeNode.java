package SyntaxAnalyser;

import java.util.List;

public class SyntaxTreeNode {
    private SyntaxTreeNode parent;
    private List<SyntaxTreeNode> children;

    public SyntaxTreeNode(SyntaxTreeNode parent, List<SyntaxTreeNode> children) {
        this.parent = parent;
        this.children = children;
    }

    private void setParent(SyntaxTreeNode parent) {
        this.parent = parent;
    }

    private void addChild(SyntaxTreeNode child) {
        this.children.add(child);
    }


    public SyntaxTreeNode getParent() {
        return parent;
    }

    public List<SyntaxTreeNode> getChildren() {
        return children;
    }
}
