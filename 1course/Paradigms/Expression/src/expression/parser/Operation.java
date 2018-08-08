package expression.parser;

public enum Operation {
    ADD('+'),
    SUB('-'),
    MUL('*'),
    DIV('/'),
    AND('&'),
    XOR('^'),
    OR('|'),
    NOT('~'),
    COUNT('$'),
    NEGATE('?'),
    LOG10('\\'),
    POW10('_');

    private char signature;

    Operation(char signature) {
        this.signature = signature;
    }

    public char getSignature() {
        return signature;
    }
}
