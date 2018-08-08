import java.io.*;
import java.util.*;

class Counter {
  private int i = 1;
  int read() { return i; }
  void increment() { i++; }
}

public class WordStat {
  private FileReader file;
  private StreamTokenizer st;
  // TreeMap хранит ключи в отсортированном порядке:
  private TreeMap counts = new TreeMap();
  void WordStat(String filename)
    throws FileNotFoundException {
    try {
      file = new FileReader(filename);
      st = new StreamTokenizer(
        new BufferedReader(file));
      st.ordinaryChar('.');
      st.ordinaryChar(',');
	  st.ordinaryChar('!');
	  st.ordinaryChar('?');
    } catch(FileNotFoundException e) {
      System.err.println(
        "Could not open " + filename);
      throw e;
    }
  }
  void cleanup() {
    try {
      file.close();
    } catch(IOException e) {
      System.err.println(
        "file.close() unsuccessful");
    }
  }
  void countWords() {
    try {
      while(st.nextToken() !=
        StreamTokenizer.TT_EOF) {
        String s;
        switch(st.ttype) {
          case StreamTokenizer.TT_EOL:
            s = new String("EOL");
            break;
          case StreamTokenizer.TT_NUMBER:
            s = Double.toString(st.nval);
            break;
          case StreamTokenizer.TT_WORD:
            s = st.sval; // Уже String
            break;
          default: // единственный символ в ttype
            s = String.valueOf((char)st.ttype);
        }
        if(counts.containsKey(s))
          ((Counter)counts.get(s)).increment();
        else
          counts.put(s, new Counter());
      }
    } catch(IOException e) {
      System.err.println(
        "st.nextToken() unsuccessful");
    }
  }
  Collection values() {
    return counts.values();
  }
  Set keySet() { return counts.keySet(); }
  Counter getCounter(String s) {
    return (Counter)counts.get(s);
  }
  public static void main(String[] args) 
  throws FileNotFoundException {
    WordStat wc =
      new WordStat(args[0]);
	BufferedWriter wr=new BufferedWriter(new OutputStreamWriter(new FileOutputStream
		(args[1]), "UTF-8"));  
    wc.countWords();
    Iterator keys = wc.keySet().iterator();
    while(keys.hasNext()) {
      String key = (String)keys.next();
      wr.write(key + ": "
               + wc.getCounter(key).read());
		wr.newLine();	   
    }
    wc.cleanup();
  }
} ///:~