package expression;

public abstract class AbstractExpression implements CommonExpression {

    Number x;
    Number y;
    Number z;
    boolean doubleArithmetic = false;

    public double evaluate(double x) {
        doubleArithmetic = true;
        this.x = x;
        this.y = 0;
        this.z = 0;
        return evaluateImpl();
    }

    public int evaluate(int x) {
        return evaluate(x, 0, 0);
    }

    public int evaluate(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return (int) evaluateImpl();
    }

    protected abstract double evaluateImpl();
}
