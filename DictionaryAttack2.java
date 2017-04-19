import java.security.*;
import java.io.*;
import java.util.*;
import java.lang.StringBuilder;
import javax.xml.bind.DatatypeConverter;

public class DictionaryAttack2 extends Thread {
	
	
	public void run() {
	
    	//We first iterate through the non salted passwords
    	Iterator non_salted_passwords_it = PreComputeHashes1.non_salted_passwords.entrySet().iterator();
    	//System.out.println(PreComputeHashes1.non_salted_passwords);
    	while (non_salted_passwords_it.hasNext()) {
    			//We extract the key,value pair from the HashTable entry
    			Map.Entry pair = (Map.Entry)non_salted_passwords_it.next();
    	        String account_name = pair.getKey().toString(); 
    	        String account_password_hash = pair.getValue().toString();

    	        if (PreComputeHashes1.hashes_dict.containsKey(account_password_hash))
    	        	System.out.println(account_name+"'s password is '"+PreComputeHashes1.hashes_dict.get(account_password_hash)+"'");
    	        
    		}
     

    	//Notify the user our program is done running.
    	System.out.println("The program terminated.");
	}
}
