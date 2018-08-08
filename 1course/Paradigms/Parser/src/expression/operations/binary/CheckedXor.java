package expression.operations.binary;

import expression.operations.BinaryExpression;

public class CheckedXor extends BinaryExpression {

    @Override
    protected Number evaluateExpression(Number first, Number second) {
        return first.intValue() ^ second.intValue();
    }
}
