package expression;

public class Add extends BinaryExpression {

    public Add(CommonExpression firstOperand, CommonExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    protected double evaluateExpr(Number firstValue, Number secondValue) {
        if (doubleArithmetic) {
            return firstValue.doubleValue() + secondValue.doubleValue();
        }
        return firstValue.intValue() + secondValue.intValue();
    }
}
