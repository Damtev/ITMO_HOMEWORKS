package expression.operations;

import expression.interfaces.CommonExpression;

public abstract class UnaryExpression extends AbstractExpression {

    private CommonExpression operand;

    public UnaryExpression() {
        operand = null;
    }

    public UnaryExpression(CommonExpression operand) {
        this.operand = operand;
    }

    @Override
    public void addOperand(CommonExpression operand) {
        assert this.operand == null : "Operand overwriting";
        this.operand = operand;
    }

    @Override
    public Number evaluateImplementation() {
        Number evaluateResult;
        Number operandValue = null;
        if (operand != null) {
            operandValue = operand.evaluate();
        }
        evaluateResult = evaluateExpression(operandValue);
        return evaluateResult;
    }

    protected abstract Number evaluateExpression(Number operandValue);
}
