package expression;

public class Count extends UnaryExpression {

    public Count(CommonExpression operand) {
        super(operand);
    }

    protected double evaluateExpr(Number value) {
        return Integer.bitCount(value.intValue());
    }
}
