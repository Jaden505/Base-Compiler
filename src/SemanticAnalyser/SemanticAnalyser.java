package SemanticAnalyser;

import LexicalAnalyser.Token;
import LexicalAnalyser.Token.TokenType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

public class SemanticAnalyser {
    private final List<Token> tokens;
    private List<Token> delcaredVars = new ArrayList<>();

    public SemanticAnalyser(List<Token> tokens) {
        this.tokens = tokens;
    }

    private HashMap<String, Integer> getDeclaredVars() {
        HashMap<String, Integer> tokenMap = new HashMap<>();
        IntStream.range(0, tokens.size()-1)
                .filter(i -> tokens.get(i).getType() == TokenType.LET)
                .forEach(i -> tokenMap.put(tokens.get(i).getValue(), i));

        return tokenMap;
    }

    private List<Token> getUsedVars() {
        return tokens.stream().filter(token -> token.getType() == TokenType.VAR).toList();
    }

    public void analyse() {
        HashMap<String, Integer> declared_vars = getDeclaredVars();

        for (Token token : getUsedVars()) {
            String tokenValue = token.getValue();

            if (!declared_vars.containsKey(tokenValue)) {
                throw new SemanticException("Semantic error: Variable " + tokenValue + " is not declared");
            }
            else if (declared_vars.get(tokenValue) > tokens.indexOf(token)) {
                throw new SemanticException("Semantic error: Variable " + tokenValue + " is used before declaration");
            }
        }
    }
}
