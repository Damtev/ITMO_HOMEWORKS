package expression.parser;

import expression.Add;
import expression.Const;
import expression.Multiply;
import expression.Subtract;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        ExpressionParser expressionParser = new ExpressionParser();
//        Scanner in = new Scanner(System.in);
//        String expression = "x * x + 7 * x + (7 - 2) + y / 3 - z * 2";
//        System.out.println(expressionParser.sort_station(expression));
//        System.out.println(expressionParser.parse(expression).evaluate(10, 18, 5));
//        String string = in.nextLine();
//        System.out.println(expressionParser.parse(string).evaluate(9, 18, 5));
//        System.out.println(expressionParser.sort_station("x + 7 + 5"));
//        System.out.println(expressionParser.parse(expression).evaluate(10, 0, 0));
//        System.out.println(expressionParser.parse("-x - 5").evaluate(8, 0 ,0));
//        System.out.println(new Multiply(new Add(new Const(4), new Const(5)),
//                new Subtract(new Const(9), new Const(5))).evaluate(5, 0, 0));
//        System.out.println(expressionParser.sort_station("-2147483648"));
        System.out.println(expressionParser.parse("x/ - 2").evaluate(10, 0, 0));
    }
}
