package expression.operations.unary;

import expression.operations.UnaryExpression;

public class CheckedNot extends UnaryExpression {

    @Override
    protected Number evaluateExpression(Number operandValue) {
        return ~operandValue.intValue();
    }
}
