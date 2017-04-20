import java.util.*;

class DictionaryAttack4 {

	public static void main(String[] args) throws InterruptedException{
	
	//populate salted and non-salted passwords 
	try{
    		PreComputeHashes1.salted_and_unsalted();
    	}
    	catch (FileNotFoundException e){
		//do something
    	}
	
		//start timer
		final long startTime = System.currentTimeMillis();
		// initiliase two threads for cracking salted and non-salted passwords
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
