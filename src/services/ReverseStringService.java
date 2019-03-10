package services;

public class ReverseStringService implements IService {
	private String string;
	
	public ReverseStringService(String string) {
		this.string = string;
	}
	
	public String getResultOfService() {
		char[] in = string.toCharArray();
	    int begin=0;
	    int end=in.length-1;
	    char temp;
	    while(end>begin){
	        temp = in[begin];
	        in[begin]=in[end];
	        in[end] = temp;
	        end--;
	        begin++;
	    }
	    return new String(in);
	}

	@Override
	public void run() {
		getResultOfService();		
	}
}
