package expression.exceptions;

public class NonPositiveArgumentException extends IllegalArgumentException {

    public NonPositiveArgumentException() {
        super("Non positive argument");
    }

    public NonPositiveArgumentException(String s) {
        super(s);
    }
}
