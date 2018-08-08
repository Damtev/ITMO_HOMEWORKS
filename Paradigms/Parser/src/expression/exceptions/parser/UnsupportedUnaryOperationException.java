package expression.exceptions.parser;

public class UnsupportedUnaryOperationException extends ParsingException {
    public UnsupportedUnaryOperationException() {
        super();
    }

    public UnsupportedUnaryOperationException(String message) {
        super(message);
    }

    public UnsupportedUnaryOperationException(String message, String expression, int pos) {
        super(message, expression, pos);
    }
}
