package expression.operations;

import expression.exceptions.evaluate.DivisionByZeroException;
import expression.interfaces.CommonExpression;

public abstract class BinaryExpression extends AbstractExpression {

    private CommonExpression firstOperand;
    private CommonExpression secondOperand;

    public BinaryExpression() {
        firstOperand = secondOperand = null;
    }

    public BinaryExpression(CommonExpression first, CommonExpression second) {
        this.firstOperand = first;
        this.secondOperand = second;
    }

    @Override
    public void addOperand(CommonExpression operand) {
        assert (firstOperand == null || secondOperand == null) : "Operand overwriting attempt";
        if (firstOperand == null) {
            firstOperand = operand;
        } else {
            secondOperand = operand;
        }
    }

    public Number evaluateImplementation() {
        assert firstOperand != null && secondOperand != null;
        Number evaluateResult;
        Number firstOperandValue = firstOperand.evaluate();
        Number secondOperandValue = secondOperand.evaluate();
        evaluateResult = evaluateExpression(firstOperandValue, secondOperandValue);
        return evaluateResult;
    }

    protected abstract Number evaluateExpression(Number first, Number second);
}
