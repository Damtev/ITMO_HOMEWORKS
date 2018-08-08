import java.util.*;
public class ReverseTranspose{
	public static void main(String[] args){
		Scanner scan=new Scanner(System.in);	
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