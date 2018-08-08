package expression.exceptions;

import expression.TripleExpression;

public abstract class CheckedUnaryExpression extends CheckedAbstractExpression {

    TripleExpression operand;

    CheckedUnaryExpression(TripleExpression operand) {
        this.operand = operand;
    }

    protected int evaluateImpl() {
        int value;
        value = operand.evaluate(x, y, z);
        return evaluateExpr(value);
    }

    protected abstract int evaluateExpr(int value);
}
