
package Scanner;
import java.io.*;
import java.util.ArrayList;
/*public class Scanner {
	Scanner() {
		BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
	}	
	static String s;
	static char ch;
	static int i = -1;
	static boolean hasNextInt() throws IOException {
		BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
		try {
			ch = (char) br.read();
		}catch (IOException e) {
			System.err.println("ERROR : " + e.getMessage());
		}finally {
			if (Character.isDigit(ch)) return true;
				else return false;
		}
	}
	static int nextInt() throws IOException {
		BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
		try {
			if (Scanner.hasNextInt()) ch = (char) br.read();
		}catch (IOException e) {
			System.err.println("ERROR : " + e.getMessage());	
		//else return null;
		}finally {
			return (int) ch;
		}
	}	
	static boolean hasNextLine() throws IOException {
		BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
		try {
			s = br.readLine();
		}catch (IOException e) {
			System.err.println("ERROR : " + e.getMessage());	
		}finally {
			if (s != null) return true;
				else return false;
		}	
	}
	static String nextLine() throws IOException {
		BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
		try {
			if (Scanner.hasNextLine()) s = br.readLine();
		//else return null;
		}catch (IOException e) {
			System.err.println("ERROR : " + e.getMessage());
		}finally {
			return s;
		}		
	}	
}*/	

public class ReverseTranspose{
	public static void main(String[] args){
		Scanner scan=new Scanner();	
		ArrayList<ArrayList<Integer>> ints = new ArrayList<>();
		int n = 0;
		int maxlen = 1;;
		while (scan.hasNextLine()){
			String[] buf = scan.nextLine().split(" ");
			n++;
			ints.add(new ArrayList<Integer>(buf.length));
			if (buf[0].length() == 0) continue;
			maxlen = Math.max(maxlen, buf.length);
			for (int i = 0; i < buf.length; i++)
				ints.get(n - 1).add(Integer.parseInt(buf[i]));
		}
		for (int j=0;j<maxlen;j++){	
			for (int i=0;i<n;i++)
				if(ints.size()!=0 && j<ints.get(i).size()) System.out.print(ints.get(i).get(j)+" ");
			System.out.println();
		}	
	}
}