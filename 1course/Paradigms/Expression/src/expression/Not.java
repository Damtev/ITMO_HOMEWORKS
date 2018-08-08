package expression;

public class Not extends UnaryExpression {

    public Not(CommonExpression operand) {
        super(operand);
    }

    protected double evaluateExpr(Number value) {
        return ~value.intValue();
    }
}