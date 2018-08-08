package expression.operations.binary;

import expression.exceptions.evaluate.DivisionByZeroException;
import expression.exceptions.evaluate.OverflowException;
import expression.interfaces.CommonExpression;
import expression.operations.BinaryExpression;

public class CheckedDivide extends BinaryExpression {

    public CheckedDivide() {
        super();
    }

    public CheckedDivide(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    @Override
    protected Number evaluateExpression(Number first, Number second) {
        if (second.doubleValue() == 0) {
            throw new DivisionByZeroException("DVZ");
        }
        if (useDouble) {
            return first.doubleValue() /
                    second.doubleValue();
        }
        if (first.intValue() == Integer.MIN_VALUE && second.intValue() == -1) {
            throw new OverflowException("overflow");
        }
        return first.intValue() / second.intValue();
    }

}
