import java.io.*;
import java.util.*;

public class WordStatLineIndex {
	
	public static void main(String[] args) throws IOException {
		TreeMap<String, List<String>> map = new TreeMap<>();
		int line = 1;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]),
		"UTF-8"));BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]),  "UTF-8"))) {
			if (br.ready()) {
				String buf = br.readLine();
				StringBuilder s = new StringBuilder("");
				while (buf != null) {	
					int word = 1;
					buf += " ";
					for (int i = 0; i < buf.length(); i++) {
						char ch = buf.charAt(i);
						if (Character.isLetter(ch) || ch == '\'' || Character.DASH_PUNCTUATION == Character.getType(ch))
							s.append(ch);
						else if (s.length() > 0) {
							String k = s.toString().toLowerCase();					
							List<String> list = map.getOrDefault(k, new ArrayList<>());
							list.add(String.valueOf(line) + ":" + String.valueOf(word++));
							map.put(k, list);
							s.setLength(0);
						}
					}
					line++;
					buf = br.readLine();
				}
			}
			for (String key: map.keySet()) {
				wr.write(key + " ");
				List<String> s = map.get(key);
				wr.write(s.size() + " ");
				for (int i = 0; i < s.size() - 1; i++) 
					wr.write(s.get(i) + " ");
				wr.write(s.get(s.size() - 1));
				wr.newLine();
			}
		}catch (FileNotFoundException e) {
			System.err.println("File not found");
		}catch (IOException e) {
			System.err.println("Invalid input ERROR");
		}catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("No names of files");
		}	
	}	
}