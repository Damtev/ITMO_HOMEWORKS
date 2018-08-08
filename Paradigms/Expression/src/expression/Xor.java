package expression;

public class Xor extends BinaryExpression {

    public Xor(CommonExpression first_operand, CommonExpression second_operand) {
        super(first_operand, second_operand);
    }

    protected double evaluateExpr(Number firstValue, Number secondValue) {
        return firstValue.intValue() ^ secondValue.intValue();
    }
}
