package expression.operations.wraps;

import expression.operations.UnaryExpression;

public class Variable extends UnaryExpression {

    private String id;
    private Number value;

    public Variable(String varID) {
        this.id = varID;
        this.value = null;
    }

    @Override
    public Number evaluateExpression(Number operand) {
        value = variablesMap.get(id);
        return value;
    }
}
