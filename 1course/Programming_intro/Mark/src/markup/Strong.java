package markup;

import java.util.*;
import java.io.*;

public class Strong extends Abstract {

    Strong(List<Interface> data) {
        super(data);
    }

    public StringBuilder toMarkdown(StringBuilder sb) {
        return sb = new StringBuilder("____").insert(2, super.toMarkdown(sb));
    }

    public StringBuilder toTex(StringBuilder sb) {
        return sb = new StringBuilder("\\textbf{}").insert(8, super.toTex(sb));
    }
}