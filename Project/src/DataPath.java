import java.util.ArrayList;

//import java.util.ArrayList;

public class DataPath {
    public static int[] Rfile;
    public static int[] Mainmemory;
	public DataPath() {
		//16 16bit general purpose registers
		Rfile=new int[16];
		Rfile[0]=5;
		Rfile[1]=5;
		Mainmemory=new int[2048];
		for (int i = 2; i < Rfile.length; i++) {
			Rfile[i] = 0;
			 // storing zeros in registerfile
		}

	}
	//load all instructions in cache
	public static void LoadP(ArrayList<String> p) {
		for (int i = 0; i < p.size(); i++) {
			if (p.get(i).length() < 32) {
				System.out.print("Sorry Cant load instruction");
			} else {
				Cache.InstCache[i]=p.get(i);
			}
		}
		System.out.println("loaded successfully");

	}

	public static String shifty(String st){
		int s= Integer.parseUnsignedInt(st,2)<< 2;
		if(Integer.toBinaryString(s).length()>16){
			return Integer.toBinaryString(s).substring(0,16);
		}
		else return Integer.toBinaryString(s);
	}
	public static String Extend(String st){
		String res="";
		for(int i=st.length();i<16;i++){
			res+=0;
		}
		res+=st;
			return res;
	}


}
