package expression.exceptions;

import expression.Const;
import expression.TripleExpression;

public class CheckedPow10 extends CheckedUnaryExpression {

    public CheckedPow10(TripleExpression operand) {
        super(operand);
    }

    protected int evaluateExpr(int value){
        int base = 1;
        int degree = value;
        CheckedMultiply pow;
        if (value < 0) {
            throw new NonPositiveArgumentException();
        }
        while (degree > 0) {
            try {
                pow = new CheckedMultiply(new Const(base), new Const(10));
            } catch (OverflowException error) {
                throw new OverflowException("overflow");
            }
            base = pow.evaluate(0, 0, 0);
            --degree;
        }
        return base;
    }
}
