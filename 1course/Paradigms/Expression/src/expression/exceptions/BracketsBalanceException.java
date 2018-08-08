package expression.exceptions;

public class BracketsBalanceException extends ParsingException {

    public BracketsBalanceException() {
        super();
    }

    public BracketsBalanceException(String message) {
        super(message);
    }

    public BracketsBalanceException(String message, String expression, int pos) {
        super(message, expression, pos);
    }
}
