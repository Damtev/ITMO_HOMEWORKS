package expression.operations;

import expression.interfaces.CommonExpression;

import java.util.HashMap;

public abstract class AbstractExpression implements CommonExpression {
    protected static HashMap<String, Number> variablesMap;
    protected static boolean useDouble = false;

    @Override
    public abstract void addOperand(CommonExpression operand);

    @Override
    public Number evaluate(String variables) {
        if (variablesMap == null) {
            buildMap(variables);
        }
        Number evaluateResult;
        try {
            evaluateResult = evaluate();
        } catch (ArithmeticException e) {
            throw e;
        } finally {
            reset();
        }
        return evaluateResult;
    }

    public abstract Number evaluateImplementation();

    private void buildMap(String variables) {
        variablesMap = new HashMap<>();
        variables = variables.replaceAll("\\p{Space}", "");
        String[] variablesList = variables.split(",");
        for (String var: variablesList) {
            String[] variablePair = var.split("=");
            variablesMap.put(variablePair[0], Double.parseDouble(variablePair[1]));
        }
    }

    private void reset() {
        variablesMap = null;
        useDouble = false;
    }

    public Number evaluate() {
        Number evaluateResult;
        evaluateResult = evaluateImplementation();
        return evaluateResult;
    }

    @Override
    public double evaluate(double x) {
        useDouble = true;
        return evaluate("x=" + x).doubleValue();
    }

    @Override
    public int evaluate(int x) {
        return evaluate("x=" + x).intValue();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return evaluate(String.format("x=%d,y=%d,z=%d", x, y, z)).intValue();
    }
}
