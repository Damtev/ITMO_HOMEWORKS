package expression.exceptions.parser;

public class UnsupportedOperationException extends ParsingException {
    public UnsupportedOperationException() {
        super();
    }

    public UnsupportedOperationException(String message) {
        super(message);
    }

    public UnsupportedOperationException(String message, String expression, int pos) {
        super(message, expression, pos);
    }
}
