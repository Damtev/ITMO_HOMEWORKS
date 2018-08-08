package expression.exceptions;

import expression.TripleExpression;

public class CheckedCount extends CheckedUnaryExpression {

    public CheckedCount(TripleExpression operand) {
        super(operand);
    }

    protected int evaluateExpr(int value) {
        return Integer.bitCount(value);
    }
}
