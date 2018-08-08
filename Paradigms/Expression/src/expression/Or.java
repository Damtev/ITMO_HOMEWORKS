package expression;

public class Or extends BinaryExpression {

    public Or(CommonExpression first_operand, CommonExpression second_operand) {
        super(first_operand, second_operand);
    }

    protected double evaluateExpr(Number firstValue, Number secondValue) {
        return firstValue.intValue() | secondValue.intValue();
    }
}