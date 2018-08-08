package expression.operations.binary;

import expression.exceptions.evaluate.OverflowException;
import expression.interfaces.CommonExpression;
import expression.operations.BinaryExpression;

public class CheckedMultiply extends BinaryExpression {

    public CheckedMultiply(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    public CheckedMultiply() {
        super();
    }

    @Override
    protected Number evaluateExpression(Number first, Number second) {
        if (useDouble) {
            return first.doubleValue() * second.doubleValue();
        }
        int result = first.intValue() * second.intValue();
        if (second.intValue() != 0 && result / second.intValue() != first.intValue() ||
                first.intValue() == -1 && second.intValue() == Integer.MIN_VALUE ||
                first.intValue() == Integer.MIN_VALUE && second.intValue() == -1) {
            throw new OverflowException("overflow");
        }
        return result;
    }

}
