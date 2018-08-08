package md2html;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class Md2Html {

    static HashMap<String, String> open;
    static HashMap<String, String> close;
    static HashMap<String, String> special;

    static void fillingAllocation() {
        open.put("_", "<em>");close.put("_", "</em>");
        open.put("*", "<em>");close.put("*", "</em>");
        open.put("__", "<strong>");close.put("__", "</strong>");
        open.put("**", "<strong>");close.put("**", "</strong>");
        open.put("++", "<u>");close.put("++", "</u>");
        open.put("--", "<s>");close.put("--", "</s>");
        open.put("`", "<code>");close.put("`", "</code>");
        open.put("~", "<mark>");close.put("~", "</mark>");
    }

    static void fillingSpecial() {
        special.put("<", "&lt;");
        special.put(">", "&gt;");
        special.put("&", "&amp;");
    }

    static ArrayList<String> convert(StringBuilder buf) {
        ArrayList<String> paragraph = read(buf);
        for (int i = 1; i < paragraph.size() - 1; i++) {
            if (open.containsKey(paragraph.get(i))) {
                String curOpen = paragraph.get(i);
                paragraph.set(i, open.get(curOpen));
                for (int j = i; j < paragraph.size() - 1; j++) {
                    if (paragraph.get(j).equals(curOpen)) {
                        String curClose = paragraph.get(j);
                        paragraph.set(j, close.get(curClose));
                        break;
                    }
                }
            }
            else if (special.containsKey(paragraph.get(i))) {
                String curSpecial = paragraph.get(i);
                paragraph.set(i, special.get(curSpecial));
            }
        }
        return paragraph;
    }

    static ArrayList<String> read(StringBuilder buf) {
        ArrayList<String> paragraph = new ArrayList<>();
        String cur = buf.toString();
        int countPar = 0;
        String parBeg;
        String parEnd;
        if (cur.charAt(0) == '#' && (cur.charAt(1) == '#' || cur.charAt(1) == ' ')) { //если есть заголовок
            int k = 0;
            while (cur.charAt(k) == '#') {
                countPar++;
                k++;
            }
            k++; //пропускаем пробел после заголовка
            cur = cur.substring(k);
        }
        if (countPar > 0) {
            parBeg = "<h" + String.valueOf(countPar) + ">";
            parEnd = "</h" + String.valueOf(countPar) + ">";
        }
        else {
            parBeg = "<p>";
            parEnd = "</p>";
        }
        paragraph.add(parBeg);
        int i = 0;
        char ch;
        StringBuilder temp = new StringBuilder("");
        while (i < cur.length()) {
            ch = cur.charAt(i);
            switch (ch) {
                case '`': //это код
                    wasText(paragraph, temp);
                    temp.append(ch);
                    paragraph.add(temp.toString());
                    temp.setLength(0);
                    i++;
                    break;
                case '*':
                    if (isAllocation(cur, i)) {
                        wasText(paragraph, temp);
                        while ((ch = cur.charAt(i)) == '*') {
                            temp.append(ch);
                            i++;
                        }
                        paragraph.add(temp.toString());
                        temp.setLength(0);
                    }
                    else { //одиночная
                        temp.append(ch);
                        i++;
                    }
                    break;
                case '_':
                    if (isAllocation(cur, i)) {
                        wasText(paragraph, temp);
                        while ((ch = cur.charAt(i)) == '_') {
                            temp.append(ch);
                            i++;
                        }
                        paragraph.add(temp.toString());
                        temp.setLength(0);
                    }
                    else { //одиночная
                        temp.append(ch);
                        i++;
                    }
                    break;
                case '~':
                    if (isAllocation(cur, i)) {
                        wasText(paragraph, temp);
                        while ((ch = cur.charAt(i)) == '~') {
                            temp.append(ch);
                            i++;
                        }
                        paragraph.add(temp.toString());
                        temp.setLength(0);
                    }
                    else { //одиночная
                        temp.append(ch);
                        i++;
                    }
                    break;
                case '<': //это спецсимволы
                case '>':
                case '&':
                    wasText(paragraph, temp);
                    temp.append(ch);
                    paragraph.add(temp.toString());
                    temp.setLength(0);
                    i++;
                    break;
                default: //это или текст, или --, или ++,  или перевод строки, или символ со слэшем
                    if (ch == '\n') { //это перевод строки
                        wasText(paragraph, temp);
                        temp.append(ch);
                        paragraph.add(temp.toString());
                        temp.setLength(0);
                        i++;
                    }
                    else if (ch == '\\'){ //это экранированный символ
                        temp.append(cur.charAt(i + 1));
                        i += 2;
                    }
                    else if (ch == '-' && i < cur.length() - 1 && cur.charAt(i + 1) == '-') { //это выделение --
                        wasText(paragraph, temp);
                        additAlloc(cur, paragraph, temp, ch, i);
                        i += 2;
                    }
                    else if (ch == '+' && i < cur.length() - 1 && cur.charAt(i + 1) == '+') { //это выделение ++
                        wasText(paragraph, temp);
                        additAlloc(cur, paragraph, temp, ch, i);
                        i += 2;
                    }
                    else {
                        temp.append(ch);
                        i++;
                    }
            }
        }
        paragraph.add(paragraph.size() - 1, parEnd); //вписываем перед последним переводом строки
        return paragraph;
    }

    private static void additAlloc(String line, ArrayList<String> paragraph, StringBuilder temp, char ch, int index) {
        temp.append(ch);
        temp.append(line.charAt(index + 1));
        paragraph.add(temp.toString());
        temp.setLength(0);
    }

    private static void wasText(ArrayList<String> paragraph, StringBuilder temp) {
        if (temp.length() > 0) { //тут был текст
            paragraph.add(temp.toString());
            temp.setLength(0);
        }
    }

    private static boolean isAllocation(String line, int index) {
        if ((index > 0 && index < line.length() - 1 && !Character.isWhitespace(line.charAt(index - 1)) && !Character.isWhitespace(line.charAt(index + 1)))
                || (index > 0 && index < line.length() - 1 && Character.isWhitespace(line.charAt(index - 1)) && !Character.isWhitespace(line.charAt(index + 1)))
                || (index > 0 && index < line.length() - 1 && !Character.isWhitespace(line.charAt(index - 1)) && Character.isWhitespace(line.charAt(index + 1)))
                || (index == 0)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8));
             BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream
                     (args[1]), StandardCharsets.UTF_8))) {
            open = new HashMap<>();
            close = new HashMap<>();
            special = new HashMap<>();
            fillingAllocation();
            fillingSpecial();
            StringBuilder ans = new StringBuilder("");
            String line = br.readLine();
            while (line != null) {
                StringBuilder buf = new StringBuilder("");
                while (line != null && !line.equals("")) {
                    buf.append(line + '\n');
                    line = br.readLine();
                }
                if (buf.length() != 0) {
                    ArrayList<String> paragraph = convert(buf);
                    for (String s : paragraph) {
                        wr.write(s);
                    }
                }
                while (line != null && line.equals("")) {
                    line = br.readLine();
                }
            }

        }
    }
}