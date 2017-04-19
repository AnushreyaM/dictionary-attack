import java.security.*;
import java.io.*;
import java.util.*;
import java.lang.StringBuilder;
import javax.xml.bind.DatatypeConverter;


class DictionaryAttack3 extends Thread{

	public void run(){
	
	//We iterate through the salted passwords
    	Iterator salted_passwords_it = PreComputeHashes1.salted_passwords.entrySet().iterator();
    	while (salted_passwords_it.hasNext()) {
    			//We extract the key,value pair from the HashTable entry
    			Map.Entry pair = (Map.Entry)salted_passwords_it.next();
    	        String account_name = pair.getKey().toString(); 
    	        String account_password_hash = pair.getValue().toString();

    	        if (PreComputeHashes1.salted_hashes_dict.containsKey(account_password_hash))
    	        	System.out.println(account_name+"'s password is '"+PreComputeHashes1.salted_hashes_dict.get(account_password_hash)+"'");
    	        
    		}
     

    	//Notify the user our program is done running.
    	System.out.println("The program terminated.");
	
	}
}
