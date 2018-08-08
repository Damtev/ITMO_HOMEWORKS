package expression.operations.wraps;

import expression.interfaces.CommonExpression;
import expression.operations.AbstractExpression;

public class Block extends AbstractExpression {

    private CommonExpression operand;

    @Override
    public void addOperand(CommonExpression operand) {
        this.operand = operand;
    }

    @Override
    public Number evaluateImplementation() {
        return operand.evaluate();
    }
}
