package expression.operations.unary;

import expression.exceptions.evaluate.NonPositiveArgumentException;
import expression.operations.UnaryExpression;

public class CheckedUnaryLog extends UnaryExpression {
    @Override
    protected Number evaluateExpression(Number operandValue) {
        if (operandValue.intValue() <= 0) {
            throw new NonPositiveArgumentException();
        }
        return (int) Math.log10(operandValue.intValue());
    }
}
