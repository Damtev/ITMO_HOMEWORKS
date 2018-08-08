package expression.operations.binary;

import expression.exceptions.evaluate.OverflowException;
import expression.interfaces.CommonExpression;
import expression.operations.BinaryExpression;

public class CheckedAdd extends BinaryExpression {

    public CheckedAdd() {
        super();
    }

    public CheckedAdd(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    @Override
    protected Number evaluateExpression(Number first, Number second) {
        if (useDouble) {
            return first.doubleValue() + second.doubleValue();
        }
        int result = first.intValue() + second.intValue();
        if (first.intValue() > 0 && second.intValue() > 0 && result <= 0 ||
                first.intValue() < 0 && second.intValue() < 0 && result >= 0) {
            throw new OverflowException("overflow");
        }
        return result;
    }

}