package expression.interfaces;

public interface CommonExpression
        extends Expression, DoubleExpression, TripleExpression {

    Number evaluate();
    Number evaluate(String variables);

    void addOperand(CommonExpression operand);
}
