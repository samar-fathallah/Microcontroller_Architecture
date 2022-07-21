public class EXC {
	public static String code;
	public static int RegDst = 0;
	public static int RegWrite = 0;
	public static int ALUSrc = 0;
	public static int PCSrc = 0;
	public static int MemRead = 0;
	public static int MemWrite = 0;
	public static int MemToReg = 0;
	public static String SignExtend;
	public static String JumpAddress;
	public static String ReadData1;
	public static String ReadData2;
	public static String Writereg;
	public static String ALUop;
	public static int shiftAm;
	public static int jump = 0;
	public static int shift = 0;
	public static int PCadd;
	public static Boolean occupied(){
		if(code!=null)
			return true;
		else 
			return false;
	}
	public static void clear() {
		code = "";
		RegDst = 0;
		RegWrite = 0;
		ALUSrc = 0;
		PCSrc = 0;
		MemRead = 0;
		MemWrite = 0;
		MemToReg = 0;
		SignExtend = "";
		JumpAddress = "";
		ReadData1 = "";
		ReadData2 = "";
		Writereg = "";
		ALUop = "";
		shiftAm = 0;
		jump = 0;
		shift = 0;
		PCadd = 0;
	}
}
