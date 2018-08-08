import java.io.*;
import java.util.*;
import java.lang.*;

public class WordStatWords{
	public static void main(String[] args) throws IOException{
		TreeMap<String, Integer> counts = new TreeMap<String, Integer>();
		int ex;
		char ch;
		try (BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(args[0]),
		"UTF-8"));BufferedWriter wr=new BufferedWriter(new OutputStreamWriter(new FileOutputStream
		(args[1]), "UTF-8"))) {	
			if (br.ready()) {
				String s = "";
				while (( ex = br.read()) != -1) {
					ch = (char) ex;
					if (Character.isLetter(ch) || ch == '\'' || Character.DASH_PUNCTUATION ==
					 Character.getType(ch))
						s += ch;
					//if (Character.isWhitespace(ch) || ad.indexOf(ch) != -1) {
					else {							
						s = s.toLowerCase();
						if(counts.containsKey(s)) {
							int j = counts.get(s)+1;
							counts.put(s,j);
							s = "";
						}	
						else {
							if (s != "") {
								counts.put(s,1);
								s = "";
							}	
						}
					}	
				}		
			}	
			Set<Map.Entry<String, Integer>> set = counts.entrySet();	
			for(Map.Entry<String, Integer> item : counts.entrySet()){	
				wr.write(item.getKey() + " " + item.getValue());
				wr.newLine();
			}
		}catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("No names of files: " + e.getMessage());
		}catch (FileNotFoundException e) {
			System.err.println("File not found: " + e.getMessage());
		}catch (NumberFormatException e) {
			System.err.println("Invalid input: " + e.getMessage());
		}catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
		}
	}
}	