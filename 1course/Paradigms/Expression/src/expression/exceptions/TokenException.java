package expression.exceptions;

public class TokenException extends ParsingException {

    public TokenException() {
        super();
    }

    public TokenException(String message) {
        super(message);
    }

    public TokenException(String message, String expression, int pos) {
        super(message, expression, pos);
    }
}
