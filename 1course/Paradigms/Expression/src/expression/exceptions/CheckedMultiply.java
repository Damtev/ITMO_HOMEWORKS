package expression.exceptions;

import expression.TripleExpression;

public class CheckedMultiply extends CheckedBinaryExpression {

    public CheckedMultiply(TripleExpression firstOperand, TripleExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected int evaluateExpr(int firstValue, int secondValue) {
        int result = firstValue * secondValue;
        if (secondValue != 0 && result / secondValue != firstValue ||
                firstValue == -1 && secondValue == Integer.MIN_VALUE ||
                firstValue == Integer.MIN_VALUE && secondValue == -1) {
            throw new OverflowException("overflow");
        }
        return result;
    }
}
