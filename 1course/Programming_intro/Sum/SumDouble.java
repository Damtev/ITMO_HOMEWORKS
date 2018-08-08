public class SumDouble{
	public static void main(String[] args){
		double sum=0;
		String buf,sub;
		int len=15;
		for (int i=0;i<args.length;i++){
			buf=args[i].trim();
			while (buf.length()!=0){
				len=buf.length()-1;
				if (!Character.isWhitespace(buf.charAt(len))){
					if (len>0){
						while (!Character.isWhitespace(buf.charAt(len))){
							if (len>0)
								len--;
								else
									break;
						}
						if (!Character.isWhitespace(buf.charAt(len)))
							sub=buf.substring(len);
							else
								sub=buf.substring(len+1);
						sum+=Double.parseDouble(sub);
						buf=buf.substring(0,len);
					}
						else{
							sub=buf.substring(len);
							sum+=Double.parseDouble(sub);
							buf=buf.substring(0,len);
						}	
				}
					else{
						if (len>0)
							buf=buf.substring(0,len);
							else
								break;
					}		
			}
		}	
		System.out.println(sum);	
	}	
}	