package expression.exceptions;

import expression.TripleExpression;

public class CheckedDivide extends CheckedBinaryExpression {

    public CheckedDivide(TripleExpression firstOperand, TripleExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected int evaluateExpr(int firstValue, int secondValue) {
        if (secondValue == 0) {
            throw new DivisionByZeroException("divizion by zero");
        }
        if (firstValue == Integer.MIN_VALUE && secondValue == -1) {
            throw new OverflowException("overflow");
        }
        return firstValue / secondValue;
    }
}
