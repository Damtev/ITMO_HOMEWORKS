package expression.exceptions;

import expression.TripleExpression;

public abstract class CheckedAbstractExpression implements TripleExpression {

    int x;
    int y;
    int z;

    public int evaluate(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        int evaluateResult;
        try {
            evaluateResult = evaluateImpl();
        } catch (ArithmeticException error) {
            throw error;
        }
        return evaluateResult;
    }

    protected abstract int evaluateImpl();
}
