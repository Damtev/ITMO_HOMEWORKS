package expression;

public class Const extends ConstExpression {

    public Const(Number value) {
        super(value);
    }

    protected double evaluateExpr(Number value) {
        if (doubleArithmetic) {
            return value.doubleValue();
        }
        return value.intValue();
    }
}
