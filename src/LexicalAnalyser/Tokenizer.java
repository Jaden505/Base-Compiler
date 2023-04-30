package LexicalAnalyser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tokenizer {
    private final String input;
    private Character currentChar;
    private Integer position = 0;
    private final List<Character> WHITE_SPACES = Arrays.asList(' ', '\r', '\t', '\n');

    public Tokenizer(String input) {
        this.input = input;
    }

    private String getNextToken() {
        StringBuilder token = new StringBuilder();

        while (Character.isLetterOrDigit(currentChar)) {
            token.append(currentChar);
            if (isEndOfCode()) {
                this.position++;
                break;
            } else {
                advance();
            }
        }

        return token.toString();
    }

    private void skipWhiteSpaces() {
        while (WHITE_SPACES.contains(currentChar)) {
            advance();
        }
    }

    private void advance() {
        this.position++;
        this.currentChar = this.input.charAt(position);
    }

    private boolean isEndOfCode() {
        return position >= this.input.length()-1;
    }

    public List<Token> tokenize() throws TokenizerException {
        List<Token> tokens = new ArrayList<>();

        while (position < this.input.length()) {
            currentChar = this.input.charAt(position);

            if (WHITE_SPACES.contains(currentChar)) { // White spaces
                skipWhiteSpaces();
            }  else if (currentChar == '=') { // 2. LET
                Token prevToken = tokens.get(tokens.size()-1);
                if (prevToken.getType() == Token.TokenType.VAR) {
                    prevToken.setType(Token.TokenType.LET);
                }
                tokens.add(new Token(Token.TokenType.EQUALS_OPERATOR));
                this.position++;
            } else if (currentChar == '+') { // 3. ADD
                tokens.add(new Token(Token.TokenType.PLUS));
                this.position++;
            } else if (currentChar == '-') { // 4. SUB
                tokens.add(new Token(Token.TokenType.MINUS));
                this.position++;
            } else if (currentChar == '*') { // 5. MUL
                tokens.add(new Token(Token.TokenType.MULTIPLY));
                this.position++;
            } else if (currentChar == '/') { // 6. DIV
                tokens.add(new Token(Token.TokenType.DIVIDE));
                this.position++;
            } else if (currentChar == '(') { // 7. LEFT_PARENTHESIS
                tokens.add(new Token(Token.TokenType.LEFT_PARENTHESIS));
                this.position++;
            } else if (currentChar == ')') { // 8. RIGHT_PARENTHESIS
                tokens.add(new Token(Token.TokenType.RIGHT_PARENTHESIS));
                this.position++;
            } else if (Character.isDigit(currentChar)) { // INT
                String tokenStr = getNextToken();
                tokens.add(new Token(Token.TokenType.NUMBER, tokenStr));
            } else if (Character.isLetter(currentChar)) {
                String tokenStr = getNextToken();
                if (tokenStr.equals("show")) { // SHOW
                    tokens.add(new Token(Token.TokenType.SHOW));
                } else { // VAR
                    tokens.add(new Token(Token.TokenType.VAR, tokenStr));
                }
            } else {
                throw new TokenizerException("Syntax error: couldn't tokenize character '" + currentChar + "'");
            }

        }

        return tokens;
    }
}