import java.io.*;
import java.util.*;

public class WordStatLineIndex {
	
	public static void main(String[] args) throws IOException {
		TreeMap<String, List<String>> map = new TreeMap<>();
		LinkedHashMap<String, Integer> counts = new LinkedHashMap<>();
		int line = 1;
		int ex;
		char ch;
		int word = 1;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]),
		"UTF-8"));BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]),  "UTF-8"))) {
			if (br.ready()) {
				String buf = "";
				/*while ((buf = br.readLine()) != null) {
					String[] words = buf.toLowerCase().split("[^\\p{L}\\p{Pd}']+");
					for (int i = 0; i < words.length; i++) {
						if(counts.containsKey(words[i])) {
							int j = counts.get(words[i]) + 1;
							counts.put(words[i], j);
						}	
						else
							if (words[i] != "")
								counts.put(words[i], 1);
						List<String> list = map.getOrDefault(words[i], new ArrayList<>());
						int temp = i + 1;
						if (list.size() != 0)
							list.set(0, String.valueOf(counts.get(words[i])));
						else 
							list.add(String.valueOf(counts.get(words[i])));
						list.add(String.valueOf(line) + ":" + String.valueOf(temp));
						map.put(words[i], list);
					}
					line++;
				}*/
				while ((ex = br.read()) != -1) {
					ch = (char) ex;
					if (Character.isLetter(ch) || ch == '\'' || Character.DASH_PUNCTUATION == Character.getType(ch))
						buf += ch;
					else {
						buf = buf.toLowerCase();
						if (counts.containsKey(buf)) {
							int j = counts.get(buf) + 1;
							counts.put(buf, j);
						}
						else
							if (buf != "")
								counts.put(buf, 1);
						List<String> list = map.getOrDefault(buf, new ArrayList<>());
						if (list.size() != 0)
							list.set(0, String.valueOf(counts.get(buf)));
						else 
							list.add(String.valueOf(counts.get(buf)));
						list.add(String.valueOf(line) + ":" + String.valueOf(word));
						map.put(buf, list);
						word++;
					}
					if (ch == '\n') {
						line++;
						word = 1;
					}
				}
			}
			for (String key: map.keySet()) {
				wr.write(key + " ");
				//for (String s: map.get(key))
				List<String> s = map.get(key);
				for (int i = 0; i < s.size() - 1; i++) 
					wr.write(s.get(i) + " ");
				wr.write(s.get(s.size() - 1));
				wr.newLine();
			}
			/*Set<Map.Entry<String, List<String>>> set = map.entrySet();	
			for(Map.Entry<String, List<String>> item : map.entrySet()){	
				wr.write(item.getKey() + " " + item.getValue());
				wr.newLine();
			}*/
		}catch (FileNotFoundException e) {
			System.err.println("File not found: " + e.getMessage());
		}catch (IOException e) {
			System.err.println("ERROR" + e.getMessage());
		}	
	}	
}