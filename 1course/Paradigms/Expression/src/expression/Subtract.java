package expression;

public class Subtract extends BinaryExpression {

    public Subtract(CommonExpression first_operand, CommonExpression second_operand) {
        super(first_operand, second_operand);
    }

    protected double evaluateExpr(Number firstValue, Number secondValue) {
        if (doubleArithmetic) {
            return firstValue.doubleValue() - secondValue.doubleValue();
        }
        return firstValue.intValue() - secondValue.intValue();
    }
}
