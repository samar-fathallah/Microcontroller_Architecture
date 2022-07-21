public class WB {

	public static String ALUresult;
	public static int read;
	public static int MemToReg;
	public static int PCadd;
	public static String WriteRegister;
	public static Boolean occupied(){
		if(ALUresult!=null)
			return true;
		else 
			return false;
	}
	public static void clear() {
		ALUresult = "";
		WriteRegister = "";
		read = 0;
		MemToReg = 0;
		PCadd = 0;
	}

}
