public class Token {
    enum TokenType {
        NUMBER,
        EQUALS_OPERATOR,
        SHOW,
        VARIABLE,
        PLUS,
        MINUS,
        MULTIPLY,
        DIVIDE,
        LEFT_PARENTHESIS,
        RIGHT_PARENTHESIS
    }

    private final TokenType type;
    private final String value;

    public Token(TokenType type) {
        this.type = type;
        this.value = null;
    }

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        if (value != null) {
            return String.format("%s(%s)", type, value);
        } else {
            return type.toString();
        }
    }
}

