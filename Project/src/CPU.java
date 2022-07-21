import java.util.ArrayList;

public class CPU {
	public static int PCadd;
	public static void runy(ArrayList<String> p) throws InterruptedException{
		new DataPath();
		new Cache();
		LoadP(p);
		int Clk = 0;
		int pc=0;
		InstructionDecode d=new InstructionDecode();
		Execute e= new Execute();
		MemoryAccess m= new MemoryAccess();
		WriteBack w=new WriteBack();
		Thread t1=new Thread(d);
		Thread t2=new Thread(e);
		Thread t3=new Thread(m);
		Thread t4=new Thread(w);
		for(int i=0;i<Cache.InstCache.length;i++) {
			 pc=i*4;
			System.out.println("clock now: " + i);
			InstructionFetch.InstFetch(pc);
			if(!IF.occupied()){
				System.out.println("Your Processor is done executing all your program");
				break;
			}
			else{
				if(IF.occupied())
					InstructionDecode.InstDecode();
				if(EXC.occupied())
					Execute.execute();
				if(MEM.occupied())
					MemoryAccess.MemAccess();
				if(WB.occupied())
					WriteBack.Writeback();
			
			}
		    
		}
	}

	public static void LoadP(ArrayList<String> p) {
		for (int i = 0; i < p.size(); i++) {
			if (p.get(i).length() < 16) {
				System.out.print("Sorry Cant load instruction");
			} else {
				Cache.InstCache[i] = p.get(i);
			}
		}
		System.out.println("loaded successfully");

	}

	public static void main(String[] args) throws InterruptedException {
		ArrayList<String> p = new ArrayList<String>();
		String inst = "0100000000100001";
		p.add(inst);
		inst = "0100000000100001";
		p.add(inst);
		inst = "0000100000100011";
		p.add(inst);
		
		inst = "1100000000011111";
		p.add(inst);
		inst = "1001000001110000";
		p.add(inst);
		runy(p);
		

	}
}

// //inst="0100000000100001";
// inst = "0000100000100011";
// //inst="1001000000000000";
// //inst="1100000000011111";

// System.out.println(DataPath.Rfile[i]);
// }
// String inst = "1001000000000000";
// // inst="0100000000100001";
// inst = "0000100000100011";
// // inst="1001000000000000";
// // inst="1100000000011111";
// String jumpAddressResult = "";
// if (jump == 1) {
// jumpAddressResult = Execute.JumpAddress(inst.substring(5),
// DataPath.PCadd);
// //MemoryAccess.MemAccess(jumpAddressResult, InstructionDecode.jump);
// System.out.println("jump Address: " + Execute.JumpAddress);
// } else {
// Execute.execute();
// MemoryAccess.MemAccess();
// WriteBack.Writeback();
//
// }
// System.out.println("Register After Instruction");
// for (int i = 0; i < DataPath.Rfile.length; i++) {
// System.out.println(DataPath.Rfile[i]);
// }

