package SyntaxAnalyser;

import LexicalAnalyser.Token;
import LexicalAnalyser.Token.TokenType;
import SyntaxAnalyser.SyntaxTree.SyntaxTreeNode;

import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private Token currentToken;
    private int position = 0;
    private SyntaxTreeNode root;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.tokens.add(new Token(TokenType.END_OF_FILE));
        this.currentToken = tokens.get(0);
    }

    private void advance() {
        this.position++;
        this.currentToken = tokens.get(position);
    }

    public SyntaxTreeNode buildTree() throws SyntaxException {
        parseProgram();
        return root;
    }

    private void parseProgram() throws SyntaxException {
        this.root = new SyntaxTreeNode(new Token(TokenType.PROGRAM));

        while (currentToken.getType() != TokenType.END_OF_FILE) {
            parseStatement();
        }
    }

    private void parseStatement() throws SyntaxException {
        SyntaxTreeNode statement = new SyntaxTreeNode(currentToken);
        root.addChild(statement);

        if (currentToken.getType() == TokenType.LET) {
            parseVariable(statement);
        } else if (currentToken.getType() == TokenType.SHOW) {
            advance();
            parseExpression(statement);
        }
        else {
            throw new SyntaxException("Invalid statement: Must begin with a variable declaration or show");
        }
    }

    private void parseVariable(SyntaxTreeNode parent) throws SyntaxException {
        boolean valid_var_name = Character.isLetter(currentToken.getValue().charAt(0));
        Token next_equals_token = tokens.get(position + 1);
        boolean next_equals_opr = next_equals_token.getType() == TokenType.EQUALS_OPERATOR;

        if (valid_var_name && next_equals_opr) {
            SyntaxTreeNode variable = new SyntaxTreeNode(currentToken);
            SyntaxTreeNode equals_opr = new SyntaxTreeNode(next_equals_token);

            parent.addChild(equals_opr);
            equals_opr.addChild(variable);

            advance(); advance();
            parseExpression(equals_opr);
        } else {
            throw new SyntaxException("Invalid variable");
        }
    }

    private void parseExpression(SyntaxTreeNode parent) throws SyntaxException {
        SyntaxTreeNode term = new SyntaxTreeNode(new Token(TokenType.TERM));
        parent.addChild(term);
        parseTerm(term);

        while (currentToken.getType() == TokenType.PLUS || currentToken.getType() == TokenType.MINUS) {
            parent.addChild(new SyntaxTreeNode(currentToken));
            advance();

            term = new SyntaxTreeNode(new Token(TokenType.TERM));
            parent.addChild(term);
            parseTerm(term);
        }
    }

    private void parseTerm(SyntaxTreeNode parent) throws SyntaxException {
        parseFactor(parent);

        while (currentToken.getType() == TokenType.MULTIPLY || currentToken.getType() == TokenType.DIVIDE) {
            parent.addChild(new SyntaxTreeNode(currentToken));

            advance();
            parseFactor(parent);
        }
    }

    private void parseFactor(SyntaxTreeNode parent) throws SyntaxException {
        SyntaxTreeNode op = new SyntaxTreeNode(new Token(TokenType.FACTOR));
        parent.addChild(op);
        SyntaxTreeNode factor = new SyntaxTreeNode(currentToken);
        op.addChild(factor);

        if (currentToken.getType() == TokenType.NUMBER || currentToken.getType() == TokenType.VAR) {
            advance();
        } else if (currentToken.getType() == TokenType.MINUS) {
            advance();
            parseFactor(factor);
        } else if (currentToken.getType() == TokenType.LEFT_PARENTHESIS) {
            advance();
            parseExpression(factor);

            factor = new SyntaxTreeNode(currentToken);
            parent.addChild(factor);

            if (currentToken.getType() != TokenType.RIGHT_PARENTHESIS) {
                throw new SyntaxException("Mismatched parentheses");
            }
            advance();
        } else {
            throw new SyntaxException("Invalid factor");
        }
    }

    public void printTree(SyntaxTreeNode node, int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
        System.out.println(node.getToken());
        for (SyntaxTreeNode child : node.getChildren()) {
            printTree(child, depth + 1);
        }
    }
}

