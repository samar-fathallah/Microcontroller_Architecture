public class Execute   implements Runnable {
	static int zeroflag = 0;
	static int negflag = 0;
	static int ALUresult = 0;
	static String BranchAddressResult;
	static String JumpAddress;
   static Boolean empty;
   public Execute(){
	   empty=true;
   }
   public void run(){
	   if(EXC.occupied()){
		   System.out.println("thread2 running");
		   execute();
	   }
   }
	public static void execute() {

		String ALUOp = EXC.ALUop;
		String code = EXC.code;
		int ALUSrc = EXC.ALUSrc;
		int jump = EXC.jump;
		String Rd1 = EXC.ReadData1;
		String Rd2 = EXC.ReadData2;
		String SignExtend = EXC.SignExtend;
		int PCadd = EXC.PCadd;
		System.out.println("Instruction is being Executed");
		String Control = ALUControl(ALUOp, code);
		if (ALUSrc == 1) {
			ALUresult = ALUEvaluator(Control, Execute.parse(Rd1),
					Execute.parse(SignExtend));
			System.out.println("ALU result/Address: " + Integer.toBinaryString(ALUresult));
			System.out.println("zeroflag:" + zeroflag);
		}  if (ALUSrc == 0) {
			ALUresult = ALUEvaluator(Control, Execute.parse(Rd1),
					Execute.parse(Rd2));
			System.out.println("ALU result/Address: " + Integer.toBinaryString(ALUresult));
			System.out.println("zeroflag:" + zeroflag);
		}
		JumpAddress = JumpAddress(EXC.JumpAddress, EXC.PCadd);
		BranchAddressResult = BranchAddress(SignExtend, PCadd);
		if(jump==1){
		System.out.println("JumpAddress :"+JumpAddress);}
		else{
		System.out.println("branchAddress: "
				+ DataPath.Extend(BranchAddressResult));
		}
		
		AssignRegisters();
		EXC.clear();
	}

	public static String shift2(String st) {
		int y = Execute.parse(st) << 2;
		if (Integer.toBinaryString(y).length() < 16)
			return DataPath.Extend(Integer.toBinaryString(y));
		else {
			int diff = Integer.toBinaryString(y).length() - st.length();
			return Integer.toBinaryString(y).substring(diff);
		}
	}

	public static String BranchAddress(String Sign, int PCadd) {
		// Sign 5-bits
		String t = "";
		int s = parse(shift2(Sign)) + PCadd;
		t = Integer.toBinaryString(s);
		return DataPath.Extend(t);
	}

	public static int parse(String st) {
		double j = 0;
		for (int i = 0; i < st.length() && !st.isEmpty(); i++) {
			if (st.charAt(i) == '1') {
				j = j + Math.pow(2, st.length() - 1 - i);
			}

		}
		return (int) j;
	}

	public static String JumpAddress(String JA, int PCadd) {
		// JA 11 bits
		// shift the JA 2 bits left
		int y = Execute.parse(JA) << 2;
		String shift = Integer.toBinaryString(y);
		while (shift.length() < JA.length())
			shift += '0';
		String tmp = DataPath.Extend(Integer.toBinaryString(PCadd));
		String pc = tmp.substring(0, 5);
		String res = pc + shift;
		return res;
	}

	public static String ALUControl(String type, String code) {
		if (type.equals("00")) {
			if (code.equals("000"))
				// sub
				return "0110";
			if (code.equals("001"))
				// add
				return "0010";
			if (code.equals("010"))
				// mult
				return "1100";
			if (code.equals("011"))
				// or
				return "0001";
		}
		if (type.equals("01")) {
			if (code.equals("000"))
				// addi
				return "0010";
			if (code.equals("001"))
				// slti
				return "0111";
			if (code.equals("010"))
				// andi
				return "0000";
			if (code.equals("011"))
				// lw
				return "0010";
			if (code.equals("100"))
				// sw
				return "0010";
		}
		if (type.equals("10")) {
			if (code.equals("010"))
				// j
				return "jump";
			else
				// beq,blt
				return "0110";
		}
		if (type.equals("11")) {
			if (code.equals("000"))
				// shiftl
				return "1110";
			else
				return "1111";
		}
		return "";
	}

	public static void AssignRegisters() {
		MEM.ALUresult = Integer.toBinaryString(ALUresult);
		MEM.ReadData2 = EXC.ReadData2;
		MEM.zeroflag = zeroflag;
		MEM.negflag = negflag;
		MEM.jump = EXC.jump;
		MEM.BranchAddressResult = BranchAddressResult;
		MEM.jumpAddressResult = JumpAddress;
		MEM.MemWrite = EXC.MemWrite;
		MEM.MemRead = EXC.MemRead;
		MEM.PCSrc = EXC.PCSrc;
        MEM.PCadd=EXC.PCadd;
        MEM.WriteRegister=EXC.Writereg;
	}

	public static int ALUEvaluator(String Op, int Operand1, int Operand2) {
		// String out="";
		int o = 0;
		if (Op.equals("0000")) {
			o = ANDOp(Operand1, Operand2);
		}
		if (Op.equals("0001")) {
			o = OROp(Operand1, Operand2);
		}
		if (Op.equals("0010")) {
			o = addOp(Operand1, Operand2);
		}
		if (Op.equals("0110")) {
			o = subOp(Operand1, Operand2);
		}
		if (Op.equals("0111")) {
			o = sltOp(Operand1, Operand2);
		}
		if (Op.equals("1110")) {
			o = SHIFTLOp(Operand1, Operand2);
		}
		if (Op.equals("1111")) {
			o = SHIFTROp(Operand1, Operand2);
		}
		if (Op.equals("1100")) {
			o = MULTOp(Operand1, Operand2);
		}

		// out=DataPath.Extend(Integer.toBinaryString(o));
		return o;
	}

	public static int MULTOp(int Op1, int Op2) {
		int output = Op1 * Op2;
		zeroflag = 0;
		return output;
	}

	public static int SHIFTLOp(int Op1, int Op2) {
		int output = Op1 << Op2;
		if (output == 0) {
			zeroflag = 1;
		}
		return output;
	}

	public static int SHIFTROp(int Op1, int Op2) {
		int output = Op1 >> Op2;
		if (output == 0) {
			zeroflag = 1;
		}
		return output;
	}

	public static int ANDOp(int Op1, int Op2) {
		int output = (Op1 & Op2);
		if (output == 0) {
			zeroflag = 1;
		}
		return output;
	}

	public static int OROp(int Op1, int Op2) {
		int output = (Op1 | Op2);
		if (output == 0) {
			zeroflag = 1;
		}
		return output;
	}

	public static int addOp(int Op1, int Op2) {
		int output = Op1 + Op2;
		if (output == 0) {
			zeroflag = 1;
		}
		return output;
	}

	public static int subOp(int Op1, int Op2) {
		int output = Op1 - Op2;
		if (output == 0) {
			zeroflag = 1;
		}
		if (output < 0) {
			negflag = 1;
		}
		return output;
	}

	public static int sltOp(int Op1, int Op2) {
		int output = (Op1 < Op2) ? 1 : 0;
		if (output == 0) {
			zeroflag = 1;
		}
		return output;
	}

}
