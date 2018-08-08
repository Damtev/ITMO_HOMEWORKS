package expression.tests;

import expression.operations.wraps.Const;
import expression.operations.wraps.Variable;
import expression.operations.binary.CheckedMultiply;

public class MainTest {

    public static void main(String[] args) {

//        Number i = new Double(1).doubleValue() / new Double(10).doubleValue();
//        System.out.println(i.doubleValue());
//
//        CommonExpression p = new CheckedAdd(
//                new CheckedMultiply(
//                        new Variable("x"),
//                        new Variable("y")),
//                new CheckedDivide(
//                        new CheckedSubtract(
//                                new Variable("z"),
//                                new Const(1)),
//                        new Const(10))
//        );
//
//        System.out.println(p.evaluate(1, 1, 0));


//        Number p = new CheckedMultiply(
//                new Variable("x"),
//                new Variable("y")
//        ).evaluate(-10, -3);

        Number t = new CheckedMultiply(
                new Variable("x"),
                new Const(-1_000_000_000)
        ).evaluate(3);

        Number p = new Integer(-1_000_000_000).doubleValue()*new Integer(3).doubleValue();

        System.out.println(-1327305691*(-2));

    }
}
