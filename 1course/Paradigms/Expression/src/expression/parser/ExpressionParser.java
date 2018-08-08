package expression.parser;

import expression.*;

import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class ExpressionParser implements Parser {

    public CommonExpression parse(String expression) {
        Stack<CommonExpression> operands = new Stack<>();
        expression = sort_station(expression);
        ArrayList<Character> operations = new ArrayList<>();
        for (Operation operation: Operation.values()) {
            operations.add(operation.getSignature());
        }
        StringTokenizer stringTokenizer = new StringTokenizer(expression, " ", false);
//        StringTokenizer stringTokenizer = new StringTokenizer(expression, operations, true);
        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken();
            if (token.length() == 1 && isOperator(token.charAt(0))) {
                char operation = token.charAt(0);
                switch (operation) {
                    case '?':
                        operands.push(new Negate(operands.pop()));
                        break;
                    case '~':
                        operands.push(new Not(operands.pop()));
                        break;
                    case '$':
                        operands.push(new Count(operands.pop()));
                        break;
                    default:
                        CommonExpression second_operand = operands.pop();
                        CommonExpression first_operand = operands.pop();
                        switch (operation) {
                            case '*':
                                operands.push(new Multiply(first_operand, second_operand));
                                break;
                            case '/':
                                operands.push(new Divide(first_operand, second_operand));
                                break;
                            case '+':
                                operands.push(new Add(first_operand, second_operand));
                                break;
                            case '-':
                                operands.push(new Subtract(first_operand, second_operand));
                                break;
                            case '^':
                                operands.push(new Xor(first_operand, second_operand));
                                break;
                            case '&':
                                operands.push(new And(first_operand, second_operand));
                                break;
                            case '|':
                                operands.push(new Or(first_operand, second_operand));
                                break;
                        }
                    }
            } else if (isVariable(token.charAt(0))) {
                operands.push(new Variable(token));
            } else {
                    operands.push(new Const(Integer.parseInt(token)));
            }
        }
        return operands.pop();
    }

    public String sort_station(String expression) {
        StringBuilder exit = new StringBuilder();
        Stack<Character> operators = new Stack<>();
        expression = expression.replaceAll("\\s", "");
        int i = 0;
        char lastOperator = '-';
        char token;
        while (i < expression.length()) {
            token = expression.charAt(i);
            boolean association = false;
            if (isOperator(token)) {
                if (token != '-') {
                    if (token == '~') {
                        association = true;
                    }
                    lastOperator = transform(operators, token, exit, association, lastOperator);
                } else {
                    boolean nextConst = false;
                    if (isOperator(lastOperator)) { //это унарный минус
                        token = '?';
                        if (Character.isDigit(expression.charAt(i + 1))) {
                            nextConst = true;
                            ++i;
                            StringBuilder number = new StringBuilder("-");
                            while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                                number.append(expression.charAt(i));
                                ++i;
                            }
                            exit.append(number + " ");
                            --i;
                            lastOperator = 0;
                        }
                        association = true;
                    }
                    if (!nextConst) {
                        lastOperator = transform(operators, token, exit, association, lastOperator);
                    }
                }
            } else if (token == '(') {
                operators.push(token);
            } else if (token == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    exit.append(operators.pop() + " ");
                }
                operators.pop(); //удаляем открывающую скобку
            }  else { //это переменная или константа
                StringBuilder var = new StringBuilder();
                while (i < expression.length()) {
                    if (Character.isDigit(expression.charAt(i)) ||
                            Character.isLetter(expression.charAt(i))) {
                        var.append(expression.charAt(i));
                        ++i;
                        if (var.toString().equals("count")) {
                            break;
                        }
                    } else {
                        break;
                    }
                }
                if (!var.toString().equals("count")) {
                    exit.append(var + " ");
                    lastOperator = 0;
                } else {
                    token = '$';
                    lastOperator = transform(operators, token, exit, true, lastOperator);
                }
                --i;
            }
            ++i;
        }
        while (!operators.isEmpty()) {
            exit.append(operators.pop() + " ");
        }
        exit.deleteCharAt(exit.length() - 1);
        return exit.toString();
    }

    public boolean isOperator(char operator) {
        for (Operation operation: Operation.values()) {
            if (operation.getSignature() == operator) {
                return true;
            }
        }
        return false;
    }

    public boolean isVariable(char variable) {
        return  (variable == 'x' ||
                variable == 'y' ||
                variable == 'z');
    }

    private int getPriority(char operation) {
        switch (operation) {
            case '|':
                return 1;
            case '^':
                return 2;
            case '&':
                return 3;
            case '-':
            case '+':
                return 4;
            case '*':
            case '/':
                return 5;
            default: //унарный минус, ~ и count
                return 6;
        }
    }

    private char transform(Stack<Character> operators, char token, StringBuilder exit, boolean isRightAssociation, char lastOperator) {
        getting(operators, token, exit, isRightAssociation);
        operators.push(token);
        return token;
    }

    private void getting(Stack<Character> operators, char token, StringBuilder exit, boolean isRightAssociation) {
        if (isRightAssociation) {
            while (!operators.isEmpty() && isOperator(operators.peek())
                    && getPriority(token) < getPriority(operators.peek())) {
                exit.append(operators.pop() + " ");
            }
        } else {
            while (!operators.isEmpty() && isOperator(operators.peek())
                    && getPriority(token) <= getPriority(operators.peek())) {
                exit.append(operators.pop() + " ");
            }
        }
    }
}
