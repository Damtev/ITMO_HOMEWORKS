import java.util.Scanner;
//import java.util.ArrayList;
public class ReverseTranspose{
	public static void main(String[] args){
	Scanner scan=new Scanner(System.in);
	//String[] outer=new String[100000];
	String outer;
	String[] inter=new String[100000];
	//int[] ints=new int[100000];
	int[] intbuf=new int[100000];
	int[][] ints= new int[100000][100000];
	/*ArrayList <String> outer=new ArrayList<String>();
	ArrayList <String> inter=new ArrayList<String>();
	ArrayList <Integer> ints=new ArrayList<Integer>();*/
	int out=0;
	int in=0;
	//int inin=0;
	int[] in=new int[100000];
	//int j=0;
	while (scan.hasNextLine()){
		//outer[out]=scan.nextLine();
		outer=scan.nextLine();
		int beg=0;
		int end=0;
		if (outer.length()>1){
			outer=outer.trim();
			while (end<outer.length()){
				char ch=outer.charAt(end);
				while(ch!=' ' && end<outer.length()){
					end++;
					if (end<outer.length()) ch=outer.charAt(end);
				}	
					if (end<outer.length()){
						if (ch==' ') {
							inter[in]=outer.substring(beg,end);
							intbuf[in]=Integer.parseInt(inter[in]);;
							in++;
							//j++;
							end++;
							beg=end;
						}
					}
					if (end==outer.length()){
						inter[in]=outer.substring(beg,end);
						intbuf[in]=Integer.parseInt(inter[in]);;
					}	
			}
			/*outer[out]="";
			for (int q=j;q>=0;q--){
				inter[in-j]=Integer.toString(ints[q]);
				outer[out]+=inter[in-j]+" ";
			}
			outer[out]=outer[out].trim();*/
			for (int q=0;q<in;q++)
				ints[out][q]=intbuf[q];
		}
		out++;
		inin++;
		//j=0;
	}
	/*for (int z=out-1;z>=0;z--)
		System.out.println(outer[z]);*/
	for (int stolb=0;stolb<in;stolb++){
		for (int str=0;str<out;str++){
			System.out.print(ints[str][stolb]);
		}
		System.out.println();
	}	
	}
}	