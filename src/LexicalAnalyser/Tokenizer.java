package LexicalAnalyser;

import LexicalAnalyser.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tokenizer {
    private final String input;
    private Character current_char;
    private Integer position = 0;
    private final List<Character> white_spaces = Arrays.asList(' ', '\r', '\t', '\n');

    public Tokenizer(String input) {
        this.input = input;
    }

    private String getNextToken() {
        StringBuilder token = new StringBuilder();
        while (!white_spaces.contains(current_char)) {
            token.append(current_char);
            if (isEndOfCode()) {break;}
            advance();
        }

        return token.toString();
    }

    private void skipWhiteSpaces() {
        while (white_spaces.contains(current_char)) {
            advance();
        }
    }

    private void advance() {
        this.position++;
        this.current_char = this.input.charAt(position);
    }

    private boolean isEndOfCode() {
        return position >= this.input.length()-1;
    }

    public List<Token> tokenize() throws TokenizerException {
        List<Token> tokens = new ArrayList<>();

        while (position < this.input.length()) {
            current_char = this.input.charAt(position);

            if (white_spaces.contains(current_char)) { // White spaces
                skipWhiteSpaces();
            }  else if (current_char == '=') { // 2. LET
                tokens.add(new Token(Token.TokenType.EQUALS_OPERATOR));
                this.position++;
            } else if (current_char == '+') { // 3. ADD
                tokens.add(new Token(Token.TokenType.PLUS));
                this.position++;
            } else if (current_char == '-') { // 4. SUB
                tokens.add(new Token(Token.TokenType.MINUS));
                this.position++;
            } else if (current_char == '*') { // 5. MUL
                tokens.add(new Token(Token.TokenType.MULTIPLY));
                this.position++;
            } else if (current_char == '/') { // 6. DIV
                tokens.add(new Token(Token.TokenType.DIVIDE));
                this.position++;
            } else if (current_char == '(') { // 7. LEFT_PARENTHESIS
                tokens.add(new Token(Token.TokenType.LEFT_PARENTHESIS));
                this.position++;
            } else if (current_char == ')') { // 8. RIGHT_PARENTHESIS
                tokens.add(new Token(Token.TokenType.RIGHT_PARENTHESIS));
                this.position++;
            } else if (Character.isDigit(current_char)) { // INT
                String token_str = getNextToken();
                tokens.add(new Token(Token.TokenType.NUMBER, token_str));
            } else if (Character.isLetter(current_char)) {
                String token_str = getNextToken();
                if (token_str.equals("show")) { // SHOW
                    tokens.add(new Token(Token.TokenType.SHOW));
                } else { // VAR
                    tokens.add(new Token(Token.TokenType.VARIABLE, token_str));
                }
            } else {
                throw new TokenizerException("Syntax error: couldn't tokenize character '" + current_char + "'");
            }

            this.position++;
        }

        return tokens;
    }
}