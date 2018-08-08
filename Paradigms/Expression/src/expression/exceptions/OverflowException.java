package expression.exceptions;

public class OverflowException extends ArithmeticException {

    public OverflowException() {
    }

    public OverflowException(String message) {
        super(message);
    }
}
