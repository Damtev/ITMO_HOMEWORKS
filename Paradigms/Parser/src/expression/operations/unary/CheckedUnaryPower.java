package expression.operations.unary;

import expression.exceptions.evaluate.OverflowException;
import expression.operations.UnaryExpression;

public class CheckedUnaryPower extends UnaryExpression {
    @Override
    protected Number evaluateExpression(Number operand) {
        int powResult = (int) Math.pow(10, operand.intValue());
        if (powResult <= 0 || Math.log10(powResult) != operand.intValue()) {
            throw new OverflowException("overflow");
        }
        return (int) Math.pow(10, operand.intValue());
    }
}
