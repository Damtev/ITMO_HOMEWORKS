package expression.exceptions.parser;

public class ParsingException extends Exception {

    public ParsingException() {
        super();
    }

    public ParsingException(String message) {
        super(message);
    }

    public ParsingException(String message, String expression, int pos) {
        super(String.format("%s:\n   %s\n%" + (pos + 3) + "s", message, expression, "^"));
    }
}
