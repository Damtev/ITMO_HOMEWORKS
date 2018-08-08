package expression;

public abstract class BinaryExpression extends AbstractExpression {

    private CommonExpression firstOperand;
    private CommonExpression secondOperand;

    BinaryExpression(CommonExpression firstOperand, CommonExpression secondOperand) {
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
    }

    protected double evaluateImpl() {
        Number firstValue;
        Number secondValue;
        if (doubleArithmetic) {
            firstValue = firstOperand.evaluate(x.doubleValue());
            secondValue = secondOperand.evaluate(x.doubleValue());
        } else {
            firstValue = firstOperand.evaluate(x.intValue(), y.intValue(), z.intValue());
            secondValue = secondOperand.evaluate(x.intValue(), y.intValue(), z.intValue());
        }
        return evaluateExpr(firstValue, secondValue);
    }

    protected abstract double evaluateExpr(Number firstValue, Number secondValue);
}
