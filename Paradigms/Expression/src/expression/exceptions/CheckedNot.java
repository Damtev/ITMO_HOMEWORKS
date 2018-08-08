package expression.exceptions;

import expression.TripleExpression;

public class CheckedNot extends CheckedUnaryExpression {

    public CheckedNot(TripleExpression operand) {
        super(operand);
    }

    protected int evaluateExpr(int value) {
        return ~value;
    }
}
