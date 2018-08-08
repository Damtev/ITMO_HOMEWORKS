package expression;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double x = scanner.nextDouble();
        double ans = new Add(new Subtract(new Multiply
                (new Variable("x"), new Variable("x")), new Multiply
                (new Const(2.0), new Variable("x"))), new Const(1.0)).evaluate(x);
        System.out.println(ans);
    }
}
