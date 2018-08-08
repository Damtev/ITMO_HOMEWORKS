package expression.exceptions;

import expression.TripleExpression;

public class CheckedLog10 extends CheckedUnaryExpression {

    public CheckedLog10(TripleExpression operand) {
        super(operand);
    }

    protected int evaluateExpr(int value) {
        if (value <= 0) {
            throw new NonPositiveArgumentException();
        }
        int lg = 0;
        while (value >= 10) {
            ++lg;
            value /= 10;
        }
        return lg;
    }
}
