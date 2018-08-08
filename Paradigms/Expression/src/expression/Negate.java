package expression;

public class Negate extends UnaryExpression {

    public Negate(CommonExpression operand) {
        super(operand);
    }

    protected double evaluateExpr(Number value) {
        return -value.intValue();
    }
}
