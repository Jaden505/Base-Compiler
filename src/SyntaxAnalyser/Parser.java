package SyntaxAnalyser;

import LexicalAnalyser.Token;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private SyntaxTreeNode treeRoot;
    private Token currentToken;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.treeRoot = null;
    }

    private void parseProgram() throws SyntaxException {
        for (Token token : tokens) {
            this.currentToken = token;
            parseStatement();
        }
    }

    private void parseStatement() throws SyntaxException {
        if (currentToken.getType() == Token.TokenType.LET) {
            parseVariable();
        } else if (currentToken.getType() == Token.TokenType.SHOW) {
            parseExpression();
        }
        else {
            throw new SyntaxException("Invalid statement: Must begin with a variable declaration or show");
        }
    }

    private void parseExpression() {
        parseTerm();
    }


    private void parseTerm() {

    }

    private void parseFactor() {

    }

    private void parseShow() {

    }

    private void parseVariable() throws SyntaxException {
        boolean valid_var_name = Character.isLetter(currentToken.getValue().charAt(0));
        Token next_equals_token = tokens.get(tokens.indexOf(currentToken) + 1);
        boolean next_equals_opr = next_equals_token.getType() == Token.TokenType.EQUALS_OPERATOR;
        Token definition = tokens.get(tokens.indexOf(currentToken) + 2);
        boolean valid_definition = definition.getType() == Token.TokenType.NUMBER ||
                definition.getType() == Token.TokenType.LEFT_PARENTHESIS ||
                definition.getType() == Token.TokenType.MINUS;

        if (valid_var_name && next_equals_opr && valid_definition) {
            SyntaxTreeNode equals_opr = new SyntaxTreeNode(next_equals_token);
            if (treeRoot == null) {
                  this.treeRoot = equals_opr;
              } else {
                  this.treeRoot.addChild(equals_opr);
              }
            equals_opr.addChild(new SyntaxTreeNode(currentToken));
            equals_opr.addChild(new SyntaxTreeNode(definition));
        } else {
            throw new SyntaxException("Invalid variable");
        }
    }
}
