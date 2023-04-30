package SemanticAnalyser;

import LexicalAnalyser.Token;
import LexicalAnalyser.Token.TokenType;

import java.util.ArrayList;
import java.util.List;

public class SemanticAnalyser {
    private final List<Token> tokens;
    private List<Token> delcaredVars = new ArrayList<>();

    public SemanticAnalyser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void analyse() {
        for (int i=0; i<tokens.size(); i++) {
            Token token = tokens.get(i);
            if (token.getType() == TokenType.LET) {
                Token nextToken = tokens.get(i+1);
                if (nextToken.getType() == TokenType.EQUALS_OPERATOR) {
                    delcaredVars.add(token);
                } else if (!delcaredVars.contains(token)) {
                        throw new SemanticException("Variable " + token + " not declared");
                }
            }
        }
    }
}
