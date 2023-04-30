package SyntaxAnalyser;

import LexicalAnalyser.Token;

import java.util.ArrayList;
import java.util.List;

public class SyntaxTreeNode {
    private SyntaxTreeNode parent = null;
    private List<SyntaxTreeNode> children = new ArrayList<>();
    private Token value = null;

    public SyntaxTreeNode(SyntaxTreeNode parent, List<SyntaxTreeNode> children) {
        this.parent = parent;
        this.children = children;
    }

    public SyntaxTreeNode(SyntaxTreeNode parent) {
        this.parent = parent;
    }

    public SyntaxTreeNode(Token value) {
        this.value = value;
    }

    public SyntaxTreeNode() {
    }

    private void setParent(SyntaxTreeNode parent) {
        this.parent = parent;
    }

    public SyntaxTreeNode addChild(SyntaxTreeNode child) {
        this.children.add(child);
        return child;
    }


    public SyntaxTreeNode getParent() {
        return parent;
    }

    public List<SyntaxTreeNode> getChildren() {
        return children;
    }
}
