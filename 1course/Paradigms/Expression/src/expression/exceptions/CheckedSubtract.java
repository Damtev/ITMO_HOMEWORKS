package expression.exceptions;

import expression.TripleExpression;

public class CheckedSubtract extends CheckedBinaryExpression {

    public CheckedSubtract(TripleExpression firstOperand, TripleExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected int evaluateExpr(int firstValue, int secondValue) {
        int result = firstValue - secondValue;
        if (firstValue >= 0 && secondValue < 0 && result <= 0 ||
                firstValue <= 0 && secondValue > 0 && result >= 0) {
            throw new OverflowException("overflow");
        }
        return result;
    }
}
