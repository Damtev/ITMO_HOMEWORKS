package expression;

public class Variable extends ConstExpression {

    public Variable(String variable) {
        super(variable);
    }

    protected double evaluateExpr(Number value) {
        if (doubleArithmetic) {
            if (variable.equals("x")) {
                return x.doubleValue();
            }
            if (variable.equals("y")) {
                return y.doubleValue();
            }
            return z.doubleValue();
        }
        if (variable.equals("x")) {
            return x.intValue();
        }
        if (variable.equals("y")) {
            return y.intValue();
        }
        return z.intValue();
    }
}
