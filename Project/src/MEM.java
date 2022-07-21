public class MEM {
	public static String ALUresult;
	public static String ReadData2;
	public static int zeroflag;
	public static int negflag;
	public static int jump;
	public static String BranchAddressResult;
	public static String jumpAddressResult;
	public static String WriteRegister;
	public static int MemWrite;
	public static int MemRead;
	public static int MemToReg;
	public static int PCSrc;
	public static int PCadd;
	public static Boolean occupied(){
		if(ALUresult!=null)
			return true;
		else 
			return false;
	}
	public static void clear() {
		ALUresult = "";
		ReadData2 = "";
		zeroflag = 0;
		negflag = 0;
		jump = 0;
		BranchAddressResult = "";
		jumpAddressResult = "";
		WriteRegister="";
		MemWrite = 0;
		MemRead = 0;
		MemToReg = 0;
		PCSrc = 0;
		PCadd = 0;
		;
	}
}
