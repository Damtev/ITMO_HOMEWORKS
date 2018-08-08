package expression;

public abstract class ConstExpression extends AbstractExpression {

    String variable;
    Number value;

    ConstExpression(Number value) {
        this.value = value;
    }

    ConstExpression(String variable) {
        this.variable = variable;
    }

    protected double evaluateImpl() {
        return evaluateExpr(value);
    }

    protected abstract double evaluateExpr(Number value);
}
