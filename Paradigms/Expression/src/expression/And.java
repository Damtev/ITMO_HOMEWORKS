package expression;

public class And extends BinaryExpression {

    public And(CommonExpression first_operand, CommonExpression second_operand) {
        super(first_operand, second_operand);
    }

//    public int evaluate(int intValue) {
//        return (firstOperand.evaluate(intValue) & secondOperand.evaluate(intValue));
//    }
//
//    public double evaluate(double doubleValue) {
//        return 0;
//    }
//
//    public int evaluate(int x, int y, int z) {
//        return (firstOperand.evaluate(x, y, z) & secondOperand.evaluate(x, y, z));
//    }

    protected double evaluateExpr(Number firstValue, Number secondValue) {
        return firstValue.intValue() & secondValue.intValue();
    }
}