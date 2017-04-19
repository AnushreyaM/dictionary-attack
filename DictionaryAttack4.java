import java.util.*;

class DictionaryAttack4 {

	public static void main(String[] args) throws InterruptedException{
	
		try{
    		PreComputeHashes1.salted_and_unsalted();
    	}
    	catch (Exception e){
    	}
	
		final long startTime = System.currentTimeMillis();
		DictionaryAttack2 d1 = new DictionaryAttack2() ;
		DictionaryAttack3 d2 =  new DictionaryAttack3() ;
		d1.start() ;
		d2.start() ;
		d1.join();
		d2.join();
		final long endTime = System.currentTimeMillis();
		System.out.println("Time taken : "+(endTime-startTime));
	
	}

}
