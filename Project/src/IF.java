
public class IF {
public static String inst=null;
public static int PCadd=0;
public static Boolean occupied(){
	if(inst!=null)
		return true;
	else 
		return false;
}
public static void clear(){
	inst=null;
	PCadd=0;
}
}
