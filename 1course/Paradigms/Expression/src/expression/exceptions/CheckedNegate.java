package expression.exceptions;

import expression.TripleExpression;

public class CheckedNegate extends CheckedUnaryExpression {

    public CheckedNegate(TripleExpression operand) {
        super(operand);
    }

    protected int evaluateExpr(int value) {
        if (value == Integer.MIN_VALUE) {
            throw new OverflowException("overflow");
        }
        return -value;
    }
}
