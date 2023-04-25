package SyntaxAnalyser;

import LexicalAnalyser.Token;
import LexicalAnalyser.Token.TokenType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private SyntaxTreeNode treeRoot = null;
    private Token currentToken;
    private int position;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.tokens.add(new Token(TokenType.END_OF_FILE));
    }

    public void parseProgram() throws SyntaxException {
        for (this.position=0; position < tokens.size()-1; position++) {
            this.currentToken = tokens.get(position);
            parseStatement();
        }
    }

    private void parseStatement() throws SyntaxException {
        if (currentToken.getType() == TokenType.LET) {
            parseVariable();
            System.out.println("Variable declaration parsed");
        } else if (currentToken.getType() == TokenType.SHOW) {
            parseShow();
            System.out.println("Show statement parsed");
        }
        else {
            throw new SyntaxException("Invalid statement: Must begin with a variable declaration or show");
        }
    }


    private void parseShow() throws SyntaxException {
        advance();
        int original_pos = position;

        if (!parseExpression()) {
            this.position = original_pos;
            this.currentToken = tokens.get(position);
        } else if (!parseTerm()) {
            this.position = original_pos;
            this.currentToken = tokens.get(position);
        } else if (!parseFactor()) {
            this.position = original_pos;
            this.currentToken = tokens.get(position);
        } else {
            throw new SyntaxException("Invalid statement");
        }

    }

    private boolean parseExpression() throws SyntaxException {
        parseTerm();
        if (currentToken.getType() == TokenType.PLUS || currentToken.getType() == TokenType.MINUS) {
            advance();
        } else {
            return false;
        }
        parseTerm();

        return true;
    }

    private boolean parseTerm() throws SyntaxException {
        parseFactor();
        if (currentToken.getType() == TokenType.MULTIPLY || currentToken.getType() == TokenType.DIVIDE) {
            advance();
        } else {
            return false;
        }
        parseFactor();

        return true;
    }

    private boolean parseFactor() throws SyntaxException {
        if (currentToken.getType() == TokenType.NUMBER || currentToken.getType() == TokenType.LET) {
            advance();
        } else if (currentToken.getType() == TokenType.MINUS && tokens.get(position+1).getType() == TokenType.NUMBER) {
            advance(); advance();
        }else if (currentToken.getType() == TokenType.LEFT_PARENTHESIS) {
            advance();
            parseExpression();
            if (currentToken.getType() == TokenType.RIGHT_PARENTHESIS) {
                advance();
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    private void parseVariable() throws SyntaxException {
        boolean valid_var_name = Character.isLetter(currentToken.getValue().charAt(0));
        Token next_equals_token = tokens.get(tokens.indexOf(currentToken) + 1);
        boolean next_equals_opr = next_equals_token.getType() == TokenType.EQUALS_OPERATOR;

        if (valid_var_name && next_equals_opr) {
            SyntaxTreeNode equals_opr = new SyntaxTreeNode(next_equals_token);
            if (treeRoot == null) {
                  this.treeRoot = equals_opr;
              } else {
                  this.treeRoot.addChild(equals_opr);
              }
            equals_opr.addChild(new SyntaxTreeNode(currentToken));

            advance(); advance();
            parseShow();
        } else {
            throw new SyntaxException("Invalid variable");
        }
    }

    private void advance() {
        this.position++;
        this.currentToken = tokens.get(position);
    }
}
