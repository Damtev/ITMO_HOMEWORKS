package expression.exceptions;

import expression.TripleExpression;

public class CheckedAnd extends CheckedBinaryExpression {

    public CheckedAnd(TripleExpression first_operand, TripleExpression second_operand) {
        super(first_operand, second_operand);
    }

    protected int evaluateExpr(int firstValue, int secondValue) {
        return firstValue ^ secondValue;
    }
}
