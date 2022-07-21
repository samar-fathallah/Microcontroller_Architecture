public class MemoryAccess implements Runnable{
	public static int read;
	public static Boolean empty;
	public MemoryAccess(){
		empty=true;
	}
	public void run(){
		if(MEM.occupied()){
			System.out.println("thread3 running");
			MemAccess();
		}
	}
	public static void MemAccess() {
		String ALUresult = MEM.ALUresult;
		String Readdata = MEM.ReadData2;
		int ZF = MEM.zeroflag;
		int neg = MEM.negflag;
		int jump = MEM.jump;
		String BranchAddress = MEM.BranchAddressResult;
		String JumpAddress = MEM.jumpAddressResult;
		int Memwrite = MEM.MemWrite;
		int Memread = MEM.MemRead;
		int Branch = MEM.PCSrc;
		int PCadd=MEM.PCadd;
		System.out.println("Memory/Cache is being accessed Successfully");
		// value in rt
		if (Memwrite == 1) {
			Cache.WriteCacheData(ALUresult, Readdata);
			Memwrite = 0;
			System.out.println("MemoryAccess Successfully");
		}
		if (Memread == 1) {
			// check cache first
			read= Cache.FindCacheData(ALUresult, Readdata);
			// write it into register
			//WriteBack.Writetoregister(ALUresult,read);
			Memread = 0;
			System.out.println("Memory read : "
					+ DataPath.Extend(Integer.toBinaryString(read)));
		}
		else{
	
			read=Execute.parse(Readdata);
		}
		if (Branch == 1 && ZF == 1) {
			// for beq
			PCadd = Execute.parse(BranchAddress);
		}
		if (Branch == 1 && neg == 1) {
			// for blt
			PCadd = Execute.parse(BranchAddress);
		}
		if (jump == 1) {
			// for jump
			PCadd = Execute.parse(JumpAddress);
		}
		AssignRegisters();
		MEM.clear();
	}
	public static void AssignRegisters(){
		WB.ALUresult=MEM.ALUresult;
		WB.MemToReg=MEM.MemToReg;
		WB.read=read;
		WB.WriteRegister=MEM.WriteRegister;
	}
	

//	public static void MemAccess(String JumpAddress, int jump) {
//		if (jump == 1) {
//			// for jump
//			PCadd = Execute.parse(JumpAddress);
//		}
//   }
	}

