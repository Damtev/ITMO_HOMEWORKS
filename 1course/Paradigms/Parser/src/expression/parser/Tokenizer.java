package expression.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import expression.operations.Operation;

public class Tokenizer {

    public List<String> tokenize(String expression) {

        List<String> tokens = new ArrayList<>();
        Pattern p = Pattern.compile(makeRegex());
        Matcher m = p.matcher(expression);
        while (m.find())
            tokens.add(m.group());
        return tokens;
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
        regex.append("(\\d+)|(\\p{Space}+)|([^\\p{Space}]+)");
        return regex.toString();
    }

    private static void print(List<String> tokens) {
        for (String s: tokens) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) {
        System.out.println(new Tokenizer().makeRegex());
        print(new Tokenizer().tokenize("hello"));
    }

}
