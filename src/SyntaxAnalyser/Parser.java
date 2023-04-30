package SyntaxAnalyser;

import LexicalAnalyser.Token;
import LexicalAnalyser.Token.TokenType;

import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private SyntaxTreeNode treeRoot = null;
    private Token currentToken;
    private int position = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.tokens.add(new Token(TokenType.END_OF_FILE));
        this.currentToken = tokens.get(0);
    }

    private void advance() {
        this.position++;
        this.currentToken = tokens.get(position);
    }


    public void parseProgram() throws SyntaxException {
        while (currentToken.getType() != TokenType.END_OF_FILE) {
            parseStatement();
            System.out.println("Statement parsed");
        }
    }

    private void parseStatement() throws SyntaxException {
        if (currentToken.getType() == TokenType.LET) {
            parseVariable();
            System.out.println("Variable declaration parsed");
        } else if (currentToken.getType() == TokenType.SHOW) {
            advance();
            parseExpression();
            System.out.println("Show statement parsed");
        }
        else {
            System.out.println(currentToken);
            throw new SyntaxException("Invalid statement: Must begin with a variable declaration or show");
        }
    }


    private void parseVariable() throws SyntaxException {
        boolean valid_var_name = Character.isLetter(currentToken.getValue().charAt(0));
        Token next_equals_token = tokens.get(position + 1);
        boolean next_equals_opr = next_equals_token.getType() == TokenType.EQUALS_OPERATOR;

        if (valid_var_name && next_equals_opr) {
            SyntaxTreeNode variable = new SyntaxTreeNode(currentToken);
            SyntaxTreeNode equals_opr = new SyntaxTreeNode(next_equals_token);
            if (treeRoot == null) {
                this.treeRoot = variable;
            } else {
                this.treeRoot.addChild(variable);
            }
            variable.addChild(equals_opr);

            advance(); advance();
            parseExpression();
            equals_opr.addChild(treeRoot);
            treeRoot = variable;
        } else {
            throw new SyntaxException("Invalid variable");
        }
    }

    private void parseExpression() throws SyntaxException {
        parseTerm();
        while (currentToken.getType() == TokenType.PLUS || currentToken.getType() == TokenType.MINUS) {
            SyntaxTreeNode operation = new SyntaxTreeNode(currentToken);
            if (treeRoot == null) {
                this.treeRoot = operation;
            } else {
                this.treeRoot.addChild(operation);
            }
            advance();
            parseTerm();
            operation.addChild(treeRoot);
            treeRoot = operation;
        }
    }

    private void parseTerm() throws SyntaxException {
        parseFactor();

        while (currentToken.getType() == TokenType.MULTIPLY || currentToken.getType() == TokenType.DIVIDE) {
            SyntaxTreeNode operation = new SyntaxTreeNode(currentToken);
            if (treeRoot == null) {
                this.treeRoot = operation;
            } else {
                this.treeRoot.addChild(operation);
            }
            advance();
            parseFactor();
            operation.addChild(treeRoot);
            treeRoot = operation;
        }
    }

    private void parseFactor() throws SyntaxException {
        if (currentToken.getType() == TokenType.NUMBER) {
            SyntaxTreeNode number = new SyntaxTreeNode(currentToken);
            if (treeRoot == null) {
                this.treeRoot = number;
            } else {
                this.treeRoot.addChild(number);
            }
            advance();
        } else if (currentToken.getType() == TokenType.LET) {
            SyntaxTreeNode variable = new SyntaxTreeNode(currentToken);
            if (treeRoot == null) {
                this.treeRoot = variable;
            } else {
                this.treeRoot.addChild(variable);
            }
            advance();
        } else if (currentToken.getType() == TokenType.LEFT_PARENTHESIS) {
            advance();
            parseExpression();
            if (currentToken.getType() != TokenType.RIGHT_PARENTHESIS) {
                throw new SyntaxException("Mismatched parentheses");
            }
            advance();
        } else {
            throw new SyntaxException("Invalid factor");
        }
    }
}
