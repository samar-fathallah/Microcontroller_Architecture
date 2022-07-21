public class WriteBack  implements Runnable{
	public static Boolean empty;
	public WriteBack() {
		empty=true;
	}
	public void run(){
		if(WB.occupied()){
			System.out.println("thread4 running");
			Writeback();
		}
	}
public static void Writeback(){
	
	String ALUresult=WB.ALUresult;
	int ReadData=WB.read;
	int MemToreg=WB.MemToReg;
	
	//determine the destination register
	if(MemToreg==0){
		DataPath.Rfile[Execute.parse(WB.WriteRegister)]=Execute.parse(ALUresult);
		Cache.FindCacheData(DataPath.Extend(WB.WriteRegister),DataPath.Extend(ALUresult));
	}
	if(MemToreg==1){
		DataPath.Rfile[Execute.parse(WB.WriteRegister)]
				=ReadData;
		Cache.WriteCacheData(DataPath.Extend(WB.WriteRegister),DataPath.Extend(Integer.toBinaryString(ReadData)));

	}
	System.out.println("WB STAGE");
 WB.clear();
}
//public static void Writetoregister(String ALUresult,int ReadData){
//		DataPath.Rfile[Execute.parse(WB.WriteRegister)]
//				=ReadData;
//
//	System.out.println("WB STAGE");
//}
}
