package expression.parser;

import static expression.operations.Operation.*;

import expression.exceptions.parser.UnsupportedOperationException;
import expression.exceptions.parser.*;
import expression.interfaces.CommonExpression;
import expression.operations.*;
import expression.operations.binary.*;
import expression.operations.unary.*;
import expression.operations.wraps.*;
import expression.parser.tests.*;

import java.util.ArrayDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionParser implements Parser {
    private final String TOKEN_REGEX = "(log10)|(pow10)|(count)|(//)|(\\*\\*)|(\\+)|(-)|(\\*)|(/)|(\\()|(\\))|(&)|(\\^)|(\\|)|(~)|(\\d+)|(\\p{Space}+)|([^\\p{Space}+])";
    private ArrayDeque<CommonExpression> data = new ArrayDeque<>();
    private int position = 0;
    private String expression;

    @Override
    public CommonExpression parse(String expression) throws ParsingException {
        this.data = new ArrayDeque<>();
        this.expression = expression.trim();
        this.position = 0;
        int balance = 0;
        Pattern p = Pattern.compile(TOKEN_REGEX);
        Matcher m = p.matcher(expression);
        String number = "";
        boolean isVariable = false;
        boolean isNextUnary = true;
        boolean previousIsNumber = false;
        while (m.find()) {
            String token = m.group();
            position += token.length();
//            if (token.matches("\\p{Space}+")) {
//                continue;
//            }
            if (Character.isWhitespace(token.charAt(0))) {
                continue;
            }

            if (!isOperator(token)) {
                // читаем токен (переменная/константа)
                if (previousIsNumber) {
                    throw new TokenException("Two consequent numbers", expression, position);
                }
                previousIsNumber = true;
                isNextUnary = false;
                if (!isNumeric(token)) {
                    isVariable = true;
                }
                number = token;

            } else {
                previousIsNumber = false;
                checkNumber(number, isVariable);

                CommonExpression operand;

                // pushing right after another operation => operation is unary
                if (isNextUnary) {
                    if (isOperation(token, OP_BRACKET)) {
                        ++balance;
                    }
                    pushUnaryOperator(token);
                    continue;
                }

                // если у нас была унарная операция, тогда операндом становится она, иначе переменная/константа
                operand = getOperand(number, isVariable);

                // вкладываем унарные операции друг в друга
                operand = nestUnaryOperands(operand);

                CommonExpression lastElement = data.isEmpty() ? null : data.peek();
                if (isOperation(token, CL_BRACKET)) {
                    --balance;
                    if (balance < 0) {
                        throw new BracketsBalanceException(
                                "Wrong bracket balance (" + balance + ")", expression, position);
                    }
                    isNextUnary = false;

                    assert lastElement != null;
                    // если у нас вдруг такая неприятная ситуация, как (234), то запушим операнд,
                    // иначе добавляем операнд в элемент на вершине стека
                    if (data.peek() instanceof Block) {
                        data.push(operand);
                    } else {
                        lastElement.addOperand(operand);
                    }
                    operand = data.pop();
                    assert !data.isEmpty();

                    // пока не дошли до открывающей скобки, вкладываем выражения друг в друга
                    while (!(data.peek() instanceof Block)) {
                        data.peek().addOperand(operand);
                        operand = data.pop();
                    }

                    // вложим выражение в блок
                    assert !data.isEmpty() && data.peek() instanceof Block;
                    data.peek().addOperand(operand);

                } else {
                    isNextUnary = true;

                    // [a] -> [a, b]
                    // если текущий приоритет выше последнего, то добавим оператор в стек
                    // если равен - вложим один в другой
                    if (data.isEmpty() || getPriority(token) > getPriority(lastElement)) {
                        pushBinaryOperator(token, operand);

                    } else if (getPriority(token) == getPriority(lastElement)) {
                        // [a, b] -> [a]
                        lastElement.addOperand(operand);
                        pushBinaryOperator(token, data.pop());

                    } else {
                        assert getPriority(token) < getPriority(lastElement);
                        lastElement.addOperand(operand);
                        CommonExpression t = data.pop();
                        //[a, b]: 1) [a]; 2) [b]
                        // 1) если приоритет предыдущей операции выше следующей,
                        // то текущий операнд мы должны добавить к предыдущей, удалить её и
                        // добавить как операнд текущей операции
                        //
                        // 2) иначе добавляем текущий операнд к текущей операции
                        while (!data.isEmpty() &&
                                (getPriority(data.peek()) >= getPriority(token))) {
                            data.peek().addOperand(t);
                            t = data.pop();
                        }
                        pushBinaryOperator(token, t);
                    }
                }

                number = "";
                isVariable = false;
            }
        }

        if (balance != 0) {
            throw new BracketsBalanceException(
                    "Wrong bracket balance (" + balance + ")", expression, position);
        }

        if (!data.isEmpty()) {
            CommonExpression operand;
            if (number.length() == 0) {
                operand = data.pop();
                if (!(operand instanceof Block)) {
                    throw new MissingOperatorException(
                            "Right operand expected", expression, position);
                }
            } else {
                operand = getNumberToken(number, isVariable);
            }

            if (data.isEmpty()) {
                return operand;
            }

            // вложим выражения друг в друга
            data.peek().addOperand(operand);
            while (data.size() != 1) {
                CommonExpression t = data.pop();
                data.peek().addOperand(t);
            }

        } else {
            assert number.length() > 0;
            data.push(getNumberToken(number, isVariable));
        }

        assert data.size() == 1;
        assert position == expression.length();
        return data.pop();
    }

    private boolean isOperation(String token, Operation op) {
        return token.equals(op.getSignature());
    }

    private boolean isNumeric(String token) {
        return token.matches("\\d+");
    }

    private CommonExpression getOperand(String buffer, boolean isVariable)
            throws ParsingException {
        if (buffer.length() != 0) {
            return getNumberToken(buffer, isVariable);
        } else {
            return data.pop();
        }
    }

    private void checkNumber(String token, boolean isVariable) throws TokenException {
        if (isVariable && !(token.equals("x") ||
                token.equals("y") ||
                token.equals("z"))) {
            throw new TokenException("Wrong token: " + token);
        }
    }

    private CommonExpression nestUnaryOperands(CommonExpression operand) {
        while (!data.isEmpty() && isUnaryOperator(data.peek())) {
            data.peek().addOperand(operand);
            operand = data.pop();
        }
        return operand;
    }

    private int nextChar() {
        if (position == expression.length()) {
            return -1;
        }
        char ch = expression.charAt(position++);
        while (Character.isWhitespace(ch) && position != expression.length()) {
            ch = expression.charAt(position++);
        }
        return ch;
    }

    private boolean isOperator(String token) {
        for (Operation op : Operation.values()) {
            if (op.getSignature().equals(token)) {
                return true;
            }
        }
        return !token.matches("\\w+");
    }

    private boolean isUnaryOperator(CommonExpression expression) {
        return expression instanceof UnaryExpression;
    }

    private void pushUnaryOperator(String type) throws ParsingException {
        CommonExpression operator;
        if (isOperation(type, SUB)) {
            operator = new CheckedNegate();

        } else if (isOperation(type, NOT)) {
            operator = new CheckedNot();

        } else if (isOperation(type, COUNT)) {
            operator = new CheckedCountOperation();

        } else if (isOperation(type, OP_BRACKET)) {
            operator = new Block();

        } else if (isOperation(type, UNARY_LOG)) {
            operator = new CheckedUnaryLog();

        } else if (isOperation(type, UNARY_POW)) {
            operator = new CheckedUnaryPower();

        } else if (isOperation(type, CL_BRACKET)) {
            throw new MissingOperatorException("Missing right operand", expression, position);
        }
        else {
            throw new UnsupportedUnaryOperationException("Unsupported unary operation " + type, expression, position);
        }
        data.push(operator);
    }

    private void pushBinaryOperator(String token, CommonExpression firstOperand)
            throws UnsupportedOperationException {
        CommonExpression operator;
        if (isOperation(token, ADD)) {
            operator = new CheckedAdd();

        } else if (isOperation(token, SUB)) {
            operator = new CheckedSubtract();

        } else if (isOperation(token, MUL)) {
            operator = new CheckedMultiply();

        } else if (isOperation(token, DIV)) {
            operator = new CheckedDivide();

        } else if (isOperation(token, AND)) {
            operator = new CheckedAnd();

        } else if (isOperation(token, XOR)) {
            operator = new CheckedXor();

        } else if (isOperation(token, OR)) {
            operator = new CheckedOr();

        } else if (isOperation(token, LOG)) {
            operator = new CheckedBinaryLog();

        } else if (isOperation(token, POW)) {
            operator = new CheckedBinaryPow();

        } else {
            throw new UnsupportedOperationException(
                    "Unsupported binary operation: " + token, expression, position);
        }
        operator.addOperand(firstOperand);

        data.push(operator);
    }

    private CommonExpression getNumberToken(String text, boolean isVariable) throws ParsingException {
        if (isVariable) {
            return new Variable(text);
        }
        if (text.equals("2147483648")) {
            if (!data.isEmpty() && data.peek() instanceof CheckedNegate) {
                data.pop();
                return new Const(Integer.MIN_VALUE);
            }
        }
        try {
            return new Const(Integer.parseInt(text));
        } catch (NumberFormatException e) {
            throw new ParsingException("Const overflow", expression, position);
        }
    }

    private int getPriority(String token) throws UnsupportedOperationException {
        if (isOperation(token, OR)) {
            return 10;
        }
        if (isOperation(token, XOR)) {
            return 20;
        }
        if (isOperation(token, AND)) {
            return 30;
        }
        if (isOperation(token, ADD) || isOperation(token, SUB)) {
            return 40;
        }
        if (isOperation(token, MUL) || isOperation(token, DIV)) {
            return 50;
        }
        if (isOperation(token, LOG) || isOperation(token, POW)) {
            return 60;
        }

        throw new UnsupportedOperationException("Attempted to get priority of an unsupported operator: " + token);
    }

    private int getPriority(CommonExpression expr) throws UnsupportedOperationException {
        if (expr instanceof CheckedOr) {
            return 10;
        }
        if (expr instanceof CheckedXor) {
            return 20;
        }
        if (expr instanceof CheckedAnd) {
            return 30;
        }
        if (expr instanceof CheckedAdd || expr instanceof CheckedSubtract) {
            return 40;
        }
        if (expr instanceof CheckedMultiply || expr instanceof CheckedDivide) {
            return 50;
        }
        if (expr instanceof CheckedBinaryLog || expr instanceof CheckedBinaryPow) {
            return 60;
        }
        if (expr instanceof Block) {
            return -1;
        }

        throw new UnsupportedOperationException("Attempted to get priority of an unsupported operator: " + expr.toString());
    }

    private String makeRegex() {
        StringBuilder regex = new StringBuilder();
        for (Operation op: Operation.values()) {
            regex.append("(");
            switch (op) {
                case POW:
                    regex.append("\\*\\*");
                    break;

                case ADD:
                case MUL:
                case OP_BRACKET:
                case CL_BRACKET:
                case XOR:
                case OR:
                    regex.append("\\");
                    // fall-through
                default:
                    regex.append(op.getSignature());
                    break;
            }
            regex.append(")|");
        }
        regex.append("(\\d+)|(\\p{Space}+)|([^\\p{Space}])");
        return regex.toString();
    }

    public static void main(String[] args){
        MyTest.testParser();
    }
}

//x ^ - y + (y // z // y) + (y * -2 // x) | y ^ z - - x & -3 | z - 4 // y + 5 * (-6 & -7 * (8 - y & - 9 * x & - 10 // 11 - -12 // 13 | x))
//x ^ - y + y // z // y + y * -217161056 // x | (y) ^ z - (- x) & -1005794206 | z - 1552011093 // y + (2120955813) * (-1757239591 & -745861604 * (30066036 - y & - 853045301 * x & - 1827888068 // 486720750 - -901750066 // 922330823 | x))