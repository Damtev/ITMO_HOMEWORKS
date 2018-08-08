package expression.exceptions;

public class MissingOperatorException extends ParsingException {

    public MissingOperatorException() {
        super();
    }

    public MissingOperatorException(String message) {
        super(message);
    }

    public MissingOperatorException(String message, String expression, int pos) {
        super(message, expression, pos);
    }
}
