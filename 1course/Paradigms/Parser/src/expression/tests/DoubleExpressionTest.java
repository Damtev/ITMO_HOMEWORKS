package expression.tests;

import expression.operations.binary.CheckedDivide;
import expression.operations.binary.CheckedMultiply;
import expression.operations.binary.CheckedSubtract;
import expression.operations.wraps.Const;
import expression.interfaces.DoubleExpression;
import expression.operations.wraps.Variable;
import expression.operations.binary.CheckedAdd;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class DoubleExpressionTest extends ExpressionTest {
    public static void main(final String[] args) {
        new DoubleExpressionTest().run();
    }

    @Override
    protected void test() {
        super.test();

        testExpression("10", new Const(10), x -> 10);
        testExpression("x", new Variable("x"), x -> x);
        testExpression("x+2", new CheckedAdd(new Variable("x"), new Const(2)), x -> x + 2);
        testExpression("2-x", new CheckedSubtract(new Const(2), new Variable("x")), x -> 2 - x);
        testExpression("3*x", new CheckedMultiply(new Const(3), new Variable("x")), x -> 3*x);
        testExpression("x/-2", new CheckedDivide(new Variable("x"), new Const(-2)), x -> -x / 2);
        testExpression(
                "x*x+(x-1)/10",
                new CheckedAdd(
                        new CheckedMultiply(new Variable("x"), new Variable("x")),
                        new CheckedDivide(new CheckedSubtract(new Variable("x"), new Const(1)), new Const(10))
                ),
                x -> x * x + (x - 1) / 10
        );
        testExpression("x*-1_000_000_000", new CheckedMultiply(new Variable("x"), new Const(-1_000_000_000)), x -> x * -1_000_000_000.0);
        testExpression("10/x", new CheckedDivide(new Const(10), new Variable("x")), x -> 10.0 / x);
        testExpression("x/x", new CheckedDivide(new Variable("x"), new Variable("x")), x -> x / x);
    }

    private void testExpression(final String description, final DoubleExpression actual, final DoubleExpression expected) {
        System.out.println("Testing " + description);
        ops(description.length());
        for (int i = 0; i < 10; i++) {
            check(i, actual, expected);
            check(-i, actual, expected);
        }
    }

    private void check(final int x, final DoubleExpression actual, final DoubleExpression expected) {
        assertEquals(String.format("f(%d)", x), actual.evaluate(x), expected.evaluate(x));
    }
}
