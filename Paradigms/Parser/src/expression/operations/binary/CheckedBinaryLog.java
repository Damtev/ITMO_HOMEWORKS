package expression.operations.binary;

import expression.exceptions.evaluate.NonPositiveArgumentException;
import expression.operations.BinaryExpression;

public class CheckedBinaryLog extends BinaryExpression {
    @Override
    protected Number evaluateExpression(Number first, Number second) {
        //Math.log(256) / Math.log(2) - 256 // 2
        if (first.intValue() <= 0 || second.intValue() <= 0) {
            throw new NonPositiveArgumentException();
        }
        if (second.intValue() == 1) {
            throw new IllegalArgumentException("Logarithm base equals 1");
        }
        return Math.log(first.intValue()) / Math.log(second.intValue());
    }
}
