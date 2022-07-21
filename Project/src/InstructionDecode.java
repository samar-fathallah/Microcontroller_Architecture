public class InstructionDecode  implements Runnable{
	public static Boolean empty;
	public static int RegDst = 0;
	public static int RegWrite = 0;
	public static int ALUSrc = 0;
	public static int PCSrc = 0;
	public static int MemRead = 0;
	public static int MemWrite = 0;
	public static int MemToReg = 0;
	public static String SignExtend;
	public static String JumpAddress="";;
	public static String ReadData1="";
	public static String ReadData2="";
	public static String Writereg="";
	public static String ALUop="";
	public static int shiftAm;
	public static int jump = 0;
	public static int shift = 0;
public InstructionDecode(){
	empty=true;
}
public void run(){
	if(IF.occupied()){
		System.out.println("thread1 running");
		InstDecode();
	}
}
	public static void InstDecode() {
		String inst=IF.inst;
		int PCadd=IF.PCadd;
		System.out.println("Instruction is being decoded");
		System.out.println("inst:"+IF.inst);
		ALUop = inst.substring(0, 2);
		ContUnit(inst.substring(0, 2), inst.substring(2, 5));
		SignExtend = SignExtend(inst.substring(11, 16));
		if (jump == 0) {
			int rs = Execute.parse(inst.substring(5, 8));
			ReadData1 = Integer.toBinaryString(DataPath.Rfile[rs]);
			System.out.println("ReadData1:" + DataPath.Extend(ReadData1));
			if (shift == 1) {
				ReadData2 = inst.substring(8, 13);
				System.out.println("rs: for shift " + inst.substring(8, 13));
			} else {
				int rt = Execute.parse(inst.substring(8, 11));
				System.out.println("rs: " + inst.substring(5, 8) + "\n"
						+ "rt: " + inst.substring(8, 11));
				ReadData2 = Integer.toBinaryString(DataPath.Rfile[rt]);
				System.out.println("ReadData2:" + DataPath.Extend(ReadData2));
			}
			if (RegDst == 0) {
				Writereg = inst.substring(5, 8);
				System.out.println("rd:DC");
			}
			if (RegDst == 1) {
				Writereg = inst.substring(13, 16);
				System.out.println("rd:" + Writereg);
			}
		} else {
			System.out.println("Instruction is Jump");
			JumpAddress = inst.substring(5, 16);
			System.out.println("JumpAddress :"+JumpAddress );
		}
		
		AssignPipelineRegisters(inst.substring(2,5),PCadd);
		IF.clear();
	}

	public static void ContUnit(String type, String code) {
		// two bit TYPE
		// three bit CODE
		if (type.equals("00")) {
			// r-type
			RegDst = 1;
			RegWrite = 1;
			MemToReg = 1;
			ALUSrc = 0;
			PCSrc = 0;
			MemRead = 0;
			MemWrite = 0;
			jump = 0;
			shift = 0;
			PrintSignals();
		}
		if (type.equals("01")) {
			// immediate
			if (code.equals("011")) {
				// lw
				RegWrite = 1;
				ALUSrc = 1;
				MemRead = 1;
				MemToReg = 0;
				RegDst = 0;
				PCSrc = 0;
				MemWrite = 0;
				jump = 0;
				shift = 0;
				PrintSignals();
			}
			if (code.equals("100")) {
				// sw
				RegDst = -1;
				ALUSrc = 1;
				MemWrite = 1;
				MemToReg = -1;
				RegWrite = 0;
				MemRead = 0;
				PCSrc = 0;
				jump = 0;
				shift = 0;
				PrintSignals();
			} else {
				// addi,slti,andi
				RegDst = 0;
				ALUSrc = 1;
				MemWrite = 0;
				MemToReg = 1;
				RegWrite = 1;
				MemRead = 0;
				PCSrc = 0;
				jump = 0;
				shift = 0;
				PrintSignals();
			}

		}
		if (type.equals("10")) {
			if (code.equals("010")) {
				// jump
				RegDst = -1;
				ALUSrc = -1;
				MemWrite = 0;
				MemToReg = -1;
				RegWrite = 0;
				MemRead = 0;
				PCSrc = 1;
				jump = 1;
				shift = 0;
				PrintSignals();
			} else {
				// beg,blt
				RegDst = -1;
				PCSrc = 1;
				MemToReg = -1;
				RegWrite = 0;
				ALUSrc = 0;
				MemRead = 0;
				MemWrite = 0;
				jump = 0;
				shift = 0;
				PrintSignals();
			}

		}
		if (type.equals("11")) {
			// shift
			RegDst = 1;
			RegWrite = 1;
			MemToReg = 1;
			ALUSrc = 0;
			PCSrc = 0;
			MemRead = 0;
			MemWrite = 0;
			jump = 0;
			shift = 1;
			PrintSignals();
		}

	}

	public static String SignExtend(String st) {
		String res = "";
		if (st.charAt(0) == ('1')) {
			res = "11111111111" + st;
		} else {
			res = "00000000000" + st;
		}
		return res;
	}

	public static void PrintSignals() {
		System.out.println("RegDst: " + RegDst);
		System.out.println("RegWrite: " + RegWrite);
		System.out.println("ALUSrc: " + ALUSrc);
		System.out.println("PCSrc: " + PCSrc);
		System.out.println("MemRead: " + MemRead);
		System.out.println("MemWrite: " + MemWrite);
		System.out.println("MemToReg: " + MemToReg);
		System.out.println("Shift: " + shift);
		System.out.println("Jump: " + jump);

	}

	public static void AssignPipelineRegisters(String inst,int PCadd) {
		EXC.code= inst;
		EXC.RegDst = RegDst;
		EXC.RegWrite = RegWrite;
		EXC.ALUSrc = ALUSrc;
		EXC.PCSrc = PCSrc;
		EXC.MemRead = MemRead;
		EXC.MemWrite = MemWrite;
		EXC.MemToReg = MemToReg;
		EXC.SignExtend = SignExtend;
		EXC.JumpAddress = JumpAddress;
		EXC.ReadData1 = ReadData1;
		EXC.ReadData2 = ReadData2;
		EXC.Writereg = Writereg;
		EXC.ALUop = ALUop;
		EXC.shiftAm = shiftAm;
		EXC.jump = jump;
		EXC.shift = shift;
		EXC.PCadd=PCadd;
	}
	

	public static void main(String[] args) {
		

}
}