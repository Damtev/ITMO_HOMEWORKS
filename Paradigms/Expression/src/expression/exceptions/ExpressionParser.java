package expression.exceptions;

import expression.*;
import expression.parser.Operation;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.StringTokenizer;

public class ExpressionParser implements Parser {

    public TripleExpression parse(String expression) throws ParsingException {
        Stack<TripleExpression> operands = new Stack<>();
        try {
            expression = sort_station(expression);
        } catch (ParsingException error) {
            throw error;
        }
//        ArrayList<String> operations = new ArrayList<>();
//        for (Operation operation: Operation.values()) {
//            operations.add(new StringBuilder(operation.getSignature()).toString());
//        }
        StringTokenizer stringTokenizer = new StringTokenizer(expression, " ", false);
        int i = 0;
//        StringTokenizer stringTokenizer = new StringTokenizer(expression, operations, true);
        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken();
            i += token.length() + 2;
            if (token.length() == 1 && isOperator(token.charAt(0))) {
                char operation = token.charAt(0);
                try {
                    switch (operation) {
                        case '?':
                            operands.push(new CheckedNegate(operands.pop()));
                            break;
                        case '~':
                            operands.push(new CheckedNot(operands.pop()));
                            break;
                        case '$':
                            operands.push(new CheckedCount(operands.pop()));
                            break;
                        case '\\':
                            operands.push(new CheckedLog10(operands.pop()));
                            break;
                        case '_':
                            operands.push(new CheckedPow10(operands.pop()));
                            break;
                        default:
                            try {
                                TripleExpression second_operand = operands.pop();
                                TripleExpression first_operand = operands.pop();
                                switch (operation) {
                                    case '*':
                                        operands.push(new CheckedMultiply(first_operand, second_operand));
                                        break;
                                    case '/':
                                        operands.push(new CheckedDivide(first_operand, second_operand));
                                        break;
                                    case '+':
                                        operands.push(new CheckedAdd(first_operand, second_operand));
                                        break;
                                    case '-':
                                        operands.push(new CheckedSubtract(first_operand, second_operand));
                                        break;
                                    case '^':
                                        operands.push(new CheckedXor(first_operand, second_operand));
                                        break;
                                    case '&':
                                        operands.push(new CheckedAnd(first_operand, second_operand));
                                        break;
                                    case '|':
                                        operands.push(new CheckedOr(first_operand, second_operand));
                                        break;
                                }
                            } catch (EmptyStackException error) {
                                throw new MissingOperatorException("Missing operand", expression, i);
                            }
                    }
                } catch (EmptyStackException error) {
                    throw new MissingOperatorException("Missing operand", expression, i);
                }
            } else if (isVariable(token.charAt(0))) {
                operands.push(new Variable(token));
            } else {
                try {
                    operands.push(new Const(Integer.parseInt(token)));
                } catch (NumberFormatException error) {
                    throw new ParsingException("Const overflow");
                }
            }
        }
        return operands.pop();
    }

    public String sort_station(String expression) throws ParsingException {
        StringBuilder exit = new StringBuilder();
        Stack<Character> operators = new Stack<>();
        expression = expression.replaceAll("\\s", "");
        char lastOperator = '-';
        char token;
        int balance = 0;
        int i = 0;
        boolean isPreviousNumber = false;
        boolean isLastBinaryOperator = false;
        while (i < expression.length()) {
            token = expression.charAt(i);
            boolean association = false;
            if (isOperator(token)) {
                isPreviousNumber = false;
                if (token != '-') {
                    if (token == '~') {
                        association = true;
                        isLastBinaryOperator = false;
                    } else {
                        isLastBinaryOperator = true;
                    }
                    lastOperator = transform(operators, token, exit, association, lastOperator);
                } else {
                    boolean nextConst = false;
                    if (isOperator(lastOperator)) { //это унарный минус
                        isLastBinaryOperator = false;
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
                    } else {
                        isLastBinaryOperator = true;
                    }
                    if (!nextConst) {
                        lastOperator = transform(operators, token, exit, association, lastOperator);
                    }
                }
            } else if (token == '(') {
                operators.push(token);
                ++balance;
            } else if (token == ')') {
                --balance;
                if (balance < 0) {
                    throw new BracketsBalanceException("Wrong bracket balance (" + balance + ")", expression, i + 1);
                }
                if (isLastBinaryOperator) {
                    throw new MissingOperatorException("Right operand expected", expression, i + 1);
                }
                while (!operators.isEmpty() && operators.peek() != '(') {
                    exit.append(operators.pop() + " ");
                }
                operators.pop(); //удаляем открывающую скобку
            }  else if (Character.isDigit(token) ||
                    /*isVariable(token)*/Character.isLetter(token)) { //это переменная, константа или count
                isLastBinaryOperator = false;
                StringBuilder var = new StringBuilder();
                while (i < expression.length()) {
                    if (Character.isDigit(token) ||
                            /*isVariable(token)*/Character.isLetter(token)) {
                        var.append(token);
                        ++i;
                        if (i < expression.length()) {
                            token = expression.charAt(i);
                        }
                        if (var.toString().equals("count") || var.toString().equals("log10") ||
                                var.toString().equals("pow10")) {
                            break;
                        }
                    } else {
                        break;
                    }
                }
                if (var.toString().equals("count") || var.toString().equals("log10") ||
                        var.toString().equals("pow10")) {
                    switch (var.toString()) {
                        case "count":
                            token = '$';
                            break;
                        case "log10":
                            token = '\\';
                            break;
                        default:
                            token = '_';
                            break;
                    }
                    lastOperator = transform(operators, token, exit, true, lastOperator);
                    isPreviousNumber = false;
                } else {
                    if (!isNumeric(var.toString())) {
                        if (var.length() == 1 &&
                            /*Character.isLetter(var.charAt(0)) &&*/ !isVariable(var.charAt(0)) ||
                                var.length() > 1)
                            throw new TokenException("Wrong variable or const" + token);
                    }
                    if (isPreviousNumber) {
                        throw new TokenException("Two consequent numbers", expression, i + 1);
                    }
                    exit.append(var + " ");
                    lastOperator = 0;
                    isPreviousNumber = true;
                }
                --i;
            } else {
                throw new UnsupportedOperationException("Unsupported unary operation " + token, expression, i + 1);
            }
            ++i;
        }
        if (balance != 0) {
            throw new BracketsBalanceException("Wrong bracket balance (" + balance + ")", expression, i + 1);
        }
        while (!operators.isEmpty()) {
            exit.append(operators.pop() + " ");
        }
//        exit.deleteCharAt(exit.length() - 1);
        return exit.toString().trim();
    }

    private boolean isOperator(char operator) {
        for (Operation operation: Operation.values()) {
            if (operation.getSignature() == operator) {
                return true;
            }
        }
        return false;
    }

    private boolean isVariable(char variable) throws ParsingException {
        boolean isVariable = (variable == 'x' || variable == 'y' || variable == 'z');
        if (Character.isLetter(variable) && !isVariable) {
            throw new TokenException("Wrong variable or const" + variable);
        }
        return isVariable;
    }

    private boolean isNumeric(String token) {
        return token.matches("\\d+");
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
            default: //унарный минус, ~, count, log10 и pow10
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

    public static void main(String[] args) {
        ExpressionParser expressionParser = new ExpressionParser();
        try {
//            expressionParser.parse("- (- ((- (518759552)) - ((z-) - ((1189435043) - (y)))))");
//            expressionParser.parse("   3*  z");
            TripleExpression parsedExpression = expressionParser.parse("pow10 x * y");
            int result = parsedExpression.evaluate(10, 0, 0);
            System.out.println(result);
        } catch (Exception error) {
            System.err.println(error.getMessage());
        }
    }
}

