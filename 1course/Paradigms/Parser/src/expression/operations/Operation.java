package expression.operations;

/** Insert operation with more symbols in signature first to make regex maker work TODO: fix it*/
public enum Operation {
    UNARY_LOG("log10"),
    UNARY_POW("pow10"),
    COUNT("count"),
    LOG("//"),
    POW("**"),
    ADD("+"),
    SUB("-"),
    MUL("*"),
    DIV("/"),
    OP_BRACKET("("),
    CL_BRACKET(")"),
    AND("&"),
    XOR("^"),
    OR("|"),
    NOT("~");

    String signature;

    Operation(String signature) {
        this.signature = signature;
    }

    public String getSignature() {
        return signature;
    }
}
