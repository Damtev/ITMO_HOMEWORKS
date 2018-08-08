import java.util.Scanner;
//import java.util.ArrayList;
public class Reverse{
	public static void main(String[] args){
	Scanner scan=new Scanner(System.in);
	String[] outer=new String[100000];
	String[] inter=new String[100000];
	int[] ints=new int[100000];
	//ArrayList <String> outer=new ArrayList<String>();
	//ArrayList <String> inter=new ArrayList<String>();
	//ArrayList <Integer> ints=new ArrayList<Integer>();
	int out=0;
	int in=0;
	int j=0;
	while (scan.hasNextLine()){
		outer[out]=scan.nextLine();
		int beg=0;
		int end=0;
		if (outer[out].length()>1){
			outer[out]=outer[out].trim();
			while (end<outer[out].length()){
				char ch=outer[out].charAt(end);
				while(ch!=' ' && end<outer[out].length()){
					end++;
					if (end<outer[out].length()) ch=outer[out].charAt(end);
				}	
					if (end<outer[out].length()){
						if (ch==' ') {
							inter[in]=outer[out].substring(beg,end);
							ints[j]=Integer.parseInt(inter[in]);;
							in++;
							j++;
							end++;
							beg=end;
						}
					}
					if (end==outer[out].length()){
						inter[in]=outer[out].substring(beg,end);
						ints[j]=Integer.parseInt(inter[in]);;
					}	
			}
			outer[out]="";
			for (int q=j;q>=0;q--){
				inter[in-j]=Integer.toString(ints[q]);
				outer[out]+=inter[in-j]+" ";
			}
			outer[out]=outer[out].trim();
		}
		out++;
		in=0;
		j=0;
	}
	for (int z=out-1;z>=0;z--)
		System.out.println(outer[z]);
	}
}	