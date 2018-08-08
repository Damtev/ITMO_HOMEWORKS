package expression;

public abstract class UnaryExpression extends AbstractExpression {

    CommonExpression operand;

    UnaryExpression(CommonExpression operand) {
        this.operand = operand;
    }

    protected double evaluateImpl() {
        Number value;
        if (doubleArithmetic) {
            value = operand.evaluate(x.doubleValue());
        } else {
            value = operand.evaluate(x.intValue(), y.intValue(), z.intValue());
        }
        return evaluateExpr(value);
    }

    protected abstract double evaluateExpr(Number value);
}
