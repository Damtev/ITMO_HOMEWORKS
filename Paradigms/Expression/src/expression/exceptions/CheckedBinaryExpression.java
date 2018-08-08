package expression.exceptions;

import expression.TripleExpression;

public abstract class CheckedBinaryExpression extends CheckedAbstractExpression {

    private TripleExpression firstOperand;
    private TripleExpression secondOperand;

    CheckedBinaryExpression(TripleExpression firstOperand, TripleExpression secondOperand) {
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
    }

    protected int evaluateImpl() {
        int firstValue;
        int secondValue;
        firstValue = firstOperand.evaluate(x, y, z);
        secondValue = secondOperand.evaluate(x, y, z);
        return evaluateExpr(firstValue, secondValue);
    }

    protected abstract int evaluateExpr(int firstValue, int secondValue);
}
