package expression;

public class Block implements CommonExpression {

    CommonExpression commonExpression;

    public Block(CommonExpression commonExpression) {
        this.commonExpression = commonExpression;
    }

    public int evaluate(int x, int y, int z) {
        return commonExpression.evaluate(x, y, z);
    }

    public int evaluate(int intValue) {
        return this.commonExpression.evaluate(intValue);
    }

    public double evaluate(double doubleValue) {
        return this.commonExpression.evaluate(doubleValue);
    }

}
