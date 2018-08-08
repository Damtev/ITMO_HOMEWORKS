package expression.operations.binary;

import expression.exceptions.evaluate.OverflowException;
import expression.operations.BinaryExpression;

public class CheckedBinaryPow extends BinaryExpression {
    @Override
    protected Number evaluateExpression(Number first, Number second) {
        if (first.intValue() == 0) {
            if (second.intValue() == 0) {
                throw new ArithmeticException("Power 0 and 0 is undefined");
            }
            if (second.intValue() < 0) {
                throw new ArithmeticException("0 cannot be raised to a negative power");
            }
        }
        if (second.intValue() < 0) {
            throw new ArithmeticException("Raising to a negative power");
        }
        if (first.intValue() == 1 && second.intValue() >= 0) {
            return 1;
        }
        Double powResult = Math.pow(first.intValue(), second.intValue());
        if (first.intValue() != 0 && (powResult < Integer.MIN_VALUE ||
                powResult > Integer.MAX_VALUE)) {
            throw new OverflowException("overflow");
        }
        return powResult;
    }
}
