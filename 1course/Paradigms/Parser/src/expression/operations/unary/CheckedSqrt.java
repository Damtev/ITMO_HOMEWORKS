package expression.operations.unary;

import expression.operations.UnaryExpression;

public class CheckedSqrt extends UnaryExpression {
    @Override
    protected Number evaluateExpression(Number operandValue) {
        return Math.sqrt(operandValue.intValue());
    }
}
