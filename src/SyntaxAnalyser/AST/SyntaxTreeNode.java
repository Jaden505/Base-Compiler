package SyntaxAnalyser.AST;

import LexicalAnalyser.Token;

import java.util.ArrayList;
import java.util.List;

public class SyntaxTreeNode {
    private Token token;
    private List<SyntaxTreeNode> children;

    public SyntaxTreeNode(Token token) {
        this.token = token;
        this.children = new ArrayList<>();
    }

    public void addChild(SyntaxTreeNode node) {
        this.children.add(node);
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public List<SyntaxTreeNode> getChildren() {
        return children;
    }
}
