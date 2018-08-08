package markup;

import java.util.*;
import java.io.*;

public class Strikeout extends Abstract {

    Strikeout(List<Interface> data) {
        super(data);
    }

    public StringBuilder toMarkdown(StringBuilder sb) {
        return sb = new StringBuilder("~~").insert(1, super.toMarkdown(sb));
    }

    public StringBuilder toTex(StringBuilder sb) {
        return sb = new StringBuilder("\\textst{}").insert(8, super.toTex(sb));
    }
}