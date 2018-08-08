import java.util.ArrayList;
import java.io.*;

class Scanner {
	 
    BufferedReader br;

    Scanner() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }
	
    boolean hasNextLine() throws IOException {
        return br.ready();
    }

    String nextLine() throws IOException {
        return br.readLine();
    }

    void close() throws IOException {
        br.close();
    }
}

public class ReverseMin {
	
	public static void main (String[] args) throws IOException {
		ArrayList<Integer> row = new ArrayList<>(10);
		ArrayList<Integer> col = new ArrayList<>(10);
		ArrayList<String> lines = new ArrayList<>(10);
		ArrayList<Integer> count_col = new ArrayList<>(10);
		int n = 0;
		Scanner scan = new Scanner();
		try {
			while (scan.hasNextLine()) {
				lines.add(scan.nextLine());
				String[] buf = lines.get(n).split(" ");
				int len = buf.length;
				count_col.add(len);
				n++;
				if (buf[0].length() != 0) {
					int min_row = Integer.parseInt(buf[0]);
					for (int i = 0; i < buf.length; i++) {
						min_row = Math.min(min_row, Integer.parseInt(buf[i]));
						if (col.isEmpty()) col.add(Integer.parseInt(buf[i]));
						else {
							if (col.size() >= buf.length) col.set(i, Math.min(Integer.parseInt(buf[i]), col.get(i)));
							else {
								col.set(0, Math.min(Integer.parseInt(buf[0]), col.get(0)));
								for (int j = col.size(); j < buf.length; j++)
									col.add(Integer.parseInt(buf[j]));
							}	
						}	
					}
					row.add(min_row);
				}
					else row.add(0);
			}
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < count_col.get(i); j++) {
					if (lines.get(i).length() != 0) System.out.print(Math.min(row.get(i), col.get(j)) + " ");
				}			
				System.out.println();
			}	
		}catch (IOException e) {
			System.err.println("ERROR" + e.getMessage());
		}finally {
			scan.close();
		}	
	}	
}	