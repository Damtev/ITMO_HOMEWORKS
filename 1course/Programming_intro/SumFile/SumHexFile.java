import java.io.*;

public class SumHexFile {
	public static void main (String[] args) throws IOException {
		int sum = 0;
		String sub = "";
		String line;
		String hex = "0x";
		try (BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(args[0]),
		"UTF-8"));BufferedWriter wr=new BufferedWriter(new OutputStreamWriter(new FileOutputStream
		(args[1]), "UTF-8"))) {
			if (br.ready()) {
				while ((line = br.readLine()) != null) {
					line = line.toLowerCase();
					String[] buf = line.split("\\s+");
					for (int i = 0;i < buf.length;i++) {			
						for (int j=0;j < buf[i].length();j++) {
							if (!Character.isWhitespace(buf[i].charAt(j))) {
								sub += buf[i].charAt(j);
							}	
								else {
									if (sub.length() > 0) {
										if (sub.indexOf(hex) != -1) { 
											sub = sub.replaceAll(hex, "");
											if (sub.length() > 8) sub = sub.substring(sub.length() - 8);
											sum += Integer.parseUnsignedInt(sub,16);
										}
										else sum += Integer.parseInt(sub,10);
										sub = "";
									}
								}		
						}
						if (sub.length() > 0) {
								if (sub.indexOf(hex) != -1) { 
									sub = sub.replaceAll(hex, "");
									if (sub.length() > 8) sub = sub.substring(sub.length() - 8);
										sum += Integer.parseUnsignedInt(sub,16);
								}
							else sum += Integer.parseInt(sub,10);
							sub = "";
						}		
					}
				}
			}
			wr.write(String.valueOf(sum));
		}catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("No names of files: " + e.getMessage());
		}catch (FileNotFoundException e) {
			System.err.println("File not found: " + e.getMessage());
		}catch (NumberFormatException e) {
			System.err.println("Invalid input: " + e.getMessage());
		}catch (IOException e) {
            System.err.println("ERROR" + e.getMessage());
		}	
	}	
}	