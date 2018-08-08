package markup;

import java.util.*;
import java.io.*;

public class Emphasis extends Abstract {

    Emphasis(List<Interface> data) {
        super(data);
    }

    public StringBuilder toMarkdown(StringBuilder sb) {
        return sb = new StringBuilder("**").insert(1, super.toMarkdown(sb));
    }

    public StringBuilder toTex(StringBuilder sb) {
        return sb = new StringBuilder("\\emph{}").insert(6, super.toTex(sb));
    }
}
