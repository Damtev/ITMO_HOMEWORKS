import java.io.*;
public class SumWTF{
	public static void main(String[] args) throws IOException{
		int sum = 0,len;
		try (BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(args[0]),
		"UTF-8"));BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(new FileOutputStream
		(args[1]), "UTF-8"))) {
			String buf = null;
			String sub = null;
			if ((buf = reader.readLine())!=null) {
				while ((buf = reader.readLine())!=null){
					while (buf.length() != 0){
						len = buf.length() - 1;
						if (!Character.isWhitespace(buf.charAt(len))){
							if (len > 0){
								while (!Character.isWhitespace(buf.charAt(len))){
									if (len > 0)
										len--;
										else
											break;
								}
								if (!Character.isWhitespace(buf.charAt(len)))
									sub = buf.substring(len);
									else
										sub = buf.substring(len + 1);
								sum += Integer.parseInt(sub);
								buf = buf.substring(0,len);
							}
								else{
									sub = buf.substring(len);
									sum += Integer.parseInt(sub);
									buf = buf.substring(0,len);
								}	
						}
							else{
								if (len > 0)
									buf = buf.substring(0,len);
									else
										break;
							}		
					}
					sub = null;
					//len = 15;
				}
			}
			writer.write(Integer.toString(sum));
		}catch (IOException e){
            System.err.println("ERROR" + e.getMessage());
		}
	}
}