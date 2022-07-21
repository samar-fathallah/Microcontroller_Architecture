
public class Cache {
	
	public static String[]InstCache;
	public static Block[] DataCache;
	public static int hit;
	public static int miss;
	public Cache() {
		this.InstCache=new String[16];
		this.DataCache=new Block[16];
		for(int i=0;i<16;i++){
			DataCache[i]=new Block();
		}
		this.hit = 0;
		this.miss = 0;
	}
	public static void PrintData(){
		for(int i=0;i<DataCache.length;i++){
			System.out.println("Index:"+i);
			System.out.println("Tag: "+DataCache[i].Tag);
			System.out.println("Data: "+Integer.toBinaryString(DataCache[i].data));
			System.out.println("Valid: "+DataCache[i].valid);
			
		}
	}
    public static String FindCacheInst(int PCadd){
    	int actualAdd=PCadd/4;
    	return InstCache[actualAdd];
    }
	public static void WriteCacheData(String address,String ReadData){
		//find the block
		int tag=Execute.parse(address.substring(0,12));
		int index=Execute.parse(address.substring(12,16));
		Block tmp=DataCache[index];
		tmp.data=DataPath.Rfile[Execute.parse(ReadData)];;
		tmp.valid=true;
		tmp.Tag=tag;
		DataPath.Mainmemory[Execute.parse(address)]=Execute.parse(ReadData);
			System.out.println("Written into Cache and Memory");
		}
	public static int FindCacheData(String address,String ReadData){
		int res=0;
		//find the block
		int tag=Execute.parse(address.substring(0,12));
		int index=Execute.parse(address.substring(12,16));
		if(!DataCache[index].valid){
			//System.out.println("FFFFFFFFFFFFFFF"+DataCache[index].valid);
			res=DataPath.Mainmemory[Execute.parse(address)];
			//empty block write data in cache
			miss++;
			DataCache[index].data=DataPath.Mainmemory[Execute.parse(address)];
			DataCache[index].valid=true;
			DataCache[index].Tag=tag;
			System.out.println("miss & cache updated");
		}
		else{
			if(DataCache[index].Tag!=tag){
				//tag not equal write data in cache
				miss++;
				DataCache[index].data=DataPath.Mainmemory[Execute.parse(address)];
				DataCache[index].valid=true;
				DataCache[index].Tag=tag;
				System.out.println("miss tag & cache updated ");
			}
			else{
				
				hit++;
				res=DataCache[index].data;
				return res;
			}
		}
		return res;	
	}


}
