package expression.operations.unary;

import expression.exceptions.evaluate.OverflowException;
import expression.interfaces.CommonExpression;
import expression.operations.UnaryExpression;

public class CheckedNegate extends UnaryExpression {

    public CheckedNegate() {
        super();
    }

    public CheckedNegate(CommonExpression operand) {
        super(operand);
    }

    @Override
    protected Number evaluateExpression(Number operandValue) {
        if (useDouble) {
            return -operandValue.doubleValue();
        }
        if (operandValue.intValue() == Integer.MIN_VALUE) {
            throw new OverflowException("overflow");
        }
        return -operandValue.intValue();
    }
}
