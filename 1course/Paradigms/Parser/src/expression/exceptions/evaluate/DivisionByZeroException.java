package expression.exceptions.evaluate;

public class DivisionByZeroException extends ArithmeticException {

    public DivisionByZeroException() {
        super();
    }

    public DivisionByZeroException(String message) {
        super(message);
    }
}
