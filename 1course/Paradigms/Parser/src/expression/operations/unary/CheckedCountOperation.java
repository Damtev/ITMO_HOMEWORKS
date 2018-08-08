package expression.operations.unary;

import expression.operations.UnaryExpression;

import java.math.BigInteger;

public class CheckedCountOperation extends UnaryExpression {

    @Override
    protected Number evaluateExpression(Number operandValue) {
        // hi, SO
        return Integer.bitCount(operandValue.intValue());
//        int i = operandValue.intValue();
//        i = i - ((i >>> 1) & 0x55555555);
//        i = (i & 0x33333333) + ((i >>> 2) & 0x33333333);
//        return (((i + (i >>> 4)) & 0x0F0F0F0F) * 0x01010101) >>> 24;
    }
}
