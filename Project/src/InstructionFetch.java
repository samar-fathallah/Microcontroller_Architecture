
public class InstructionFetch {
	public static Boolean empty;
	public InstructionFetch(){
		empty=false;
	}
	public static void InstFetch(int PCadd){
		System.out.println("Instruction is being fetched");
		//fetch the instruction in cache
         System.out.println("PC NAWW:"+PCadd);
		String ins=Cache.FindCacheInst(PCadd);
	
		PCadd=PCadd+4;
		System.out.println("Next PC:"+PCadd);
		
		IF.inst=ins;
		IF.PCadd=PCadd;
		
	}
	

	public static void ProgCount(){
		CPU.PCadd=CPU.PCadd+4;
	}
	


}
