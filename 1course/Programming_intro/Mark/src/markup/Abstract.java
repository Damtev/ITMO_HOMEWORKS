package markup;

import java.util.List;

public abstract class Abstract implements Interface{

    private List<Interface> data;
    StringBuilder text;

    Abstract(List<Interface> data) {
        this.data = data;
    }

    Abstract(String text) {
        this.text = new StringBuilder(text);
    }

    public StringBuilder toMarkdown(StringBuilder sb) {
        for (Interface classes: data) {
            sb.append(classes.toMarkdown(new StringBuilder()));
        }
        return sb;
    }

    public StringBuilder toTex(StringBuilder sb) {
        for (Interface classes: data) {
            sb.append(classes.toTex(new StringBuilder()));
        }
        return sb;
    }

}
