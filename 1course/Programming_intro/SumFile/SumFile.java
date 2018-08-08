import java.io.*;
public class SumFile {
	public static void main (String[] args) throws IOException {
		int sum = 0;
		String sub = "";
		String line;
		try (BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(args[0]),
		"UTF-8"));BufferedWriter wr=new BufferedWriter(new OutputStreamWriter(new FileOutputStream
		(args[1]), "UTF-8"))) {
			if (br.ready()) {
				while ((line = br.readLine()) != null) {			
					String[] buf = line.split("\\s+");
					for (int i = 0;i < buf.length;i++) {			
						for (int j=0;j < buf[i].length();j++) {
							if (!Character.isWhitespace(buf[i].charAt(j))) {
								sub += buf[i].charAt(j);
							}	
								else {
									if (sub.length() > 0) sum += Integer.parseInt(sub);
									sub = "";
								}		
						}
						if (sub.length() > 0) sum += Integer.parseInt(sub);
						sub = "";
					}
				}
			}
			wr.write(Integer.toString(sum));
		}catch (FileNotFoundException e) {
			System.err.println("File not found: " + e.getMessage());
		}catch (IOException e) {
            System.err.println("ERROR" + e.getMessage());
		}	
	}	
}	