package expression.operations.wraps;

import expression.operations.UnaryExpression;

public class Const extends UnaryExpression {

    private Number value;

    public Const(Number value) {
        this.value = value;
    }

    @Override
    protected Number evaluateExpression(Number operand) {
        return value;
    }
}
