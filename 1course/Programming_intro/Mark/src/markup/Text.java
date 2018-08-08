package markup;

import java.util.*;
import java.io.*;

public class Text extends Abstract {

    Text(String text) {
        super(text);
    }

    Text(List<Interface> data) {
        super(data);
    }

    public StringBuilder toMarkdown(StringBuilder sb) {
        return text;
    }

    public StringBuilder toTex(StringBuilder sb) {
        return text;
    }

}
