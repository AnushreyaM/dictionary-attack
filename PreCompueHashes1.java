import java.security.*;
import java.io.*;
import java.util.*;
import java.lang.StringBuilder;
import javax.xml.bind.DatatypeConverter;

class PreComputeHashes1 {
	
	// hashes_dict contains all the hashed values of words from the dictionary
	public static Map<String, String> hashes_dict = new HashMap<String, String>();
	//all non-salted 
	public static Map<String, String> non_salted_passwords = new HashMap<String, String>();
	//all salted passwords from password.txt
    	public static Map<String, String> salted_passwords = new HashMap<String, String>();
	//stores only salts
    	public static Map<String, String> salted_passwords_salts = new HashMap<String, String>();
	// salted_hashes_dict contains all the salted hashed values of words from the dictionary,salts taken from the password list
    	public static Map<String, String> salted_hashes_dict = new HashMap<String, String>();
    			
		
		public static String bytesToHex(byte[] b) {
				  char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
					                 '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
				  StringBuffer buf = new StringBuffer();
				  for (int j=0; j<b.length; j++) {
					 buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
					 buf.append(hexDigit[b[j] & 0x0f]);
				  }
				  return buf.toString();
			   }
	
			//This method takes a string, computes its SHA-1 hash, 
			//and converts it into HEX using the bytesToHex method
			public static String stringToSha1(String input) throws Exception {
				//Setup a MessageDigest for SHA1 
				//System.out.println(input);
				MessageDigest md = MessageDigest.getInstance("SHA1");
				md.reset();
		
				//Setup the MessageDigest with our input string
				md.update(input.getBytes("UTF-8"));
				
				//Convert the string's digest to HEX
				String sha1 = bytesToHex(md.digest());
			   // System.out.println(sha1);
				return sha1;
			}
			
		//This method takes a byte array holding a salt and a string input
			//and returns the concatenated salt || input in byte array format
			public static byte[] concatenate_salt_with_string(byte[] salt, String input) throws Exception {
				//Convert input string to bytes
				byte[] input_byte = input.getBytes("UTF-8");
				//Create byte array sufficiently large
				byte[] concatenated = new byte[salt.length + input_byte.length];
				//Insert the salt first
				System.arraycopy(salt, 0, concatenated, 0, salt.length);
				//Insert the input string converted to bytes
				System.arraycopy(input_byte, 0, concatenated, salt.length, input_byte.length);
				//Return the concatenated salt and string in a byte array
				return concatenated;
			}

			//This method takes a string, a salt, computes its salted SHA-1 hash, 
			//and converts it into HEX using the bytesToHex method
			public static String stringToSha1_salted(byte[] salt, String input) throws Exception {
				//Setup a MessageDigest for SHA1 
				MessageDigest md = MessageDigest.getInstance("SHA1");
				md.reset();
		
				//Use the concatenate_salt_with_string method to concatenate the salt with the input
				byte[] concatenated = concatenate_salt_with_string(salt, input);
		
				//Setup the MessageDigest with our input string
				md.update(concatenated);
				
				//Convert the string's digest to HEX
				String sha1 = bytesToHex(md.digest());
				return sha1;
			}
			
	public static void salted_and_unsalted() throws Exception{
	
		//Notify the user the program is starting.
			//System.out.println("Let's get things started.");
		
			//Load the provided password file into stream and buffer
		    File passwords_file = new File("password.txt");
			FileInputStream password_stream = new FileInputStream(passwords_file);
			BufferedReader password_buffer = new BufferedReader(new InputStreamReader(password_stream));
			
			//We parse the buffer to extract user account names and passwords
			String password_file_line = null;
			while ((password_file_line = password_buffer.readLine()) != null) {
				String[] splited = password_file_line.split("\\s+");
				//System.out.println(Arrays.toString(splited)) ;
				
				//First case: password hashed with no salt
				if(splited.length == 3){
					non_salted_passwords.put(splited[0], splited[2]);
				}
				
				//Second case: password hashed with a salt
				else{
					salted_passwords.put(splited[0], splited[3]);
					salted_passwords_salts.put(splited[0], splited[2]);
				}
			}
			 
			populate() ;
		
	}
	
	// we populate the hashes_dict and salted_hashes_dict
	public static void populate() throws Exception{
	
	
	//Load the provided Dictionary into stream and buffer
        File fin = new File("english.0");
	FileInputStream fis = new FileInputStream(fin);
	Iterator non_salted_passwords_it = non_salted_passwords.entrySet().iterator();
    	
    	//Construct BufferedReader from InputStreamReader
    	BufferedReader br = new BufferedReader(new InputStreamReader(fis));
     
    	//We parse the buffer to populate the hashmaps which store the final hashed value of every word
    	String line = null;
    	while ((line = br.readLine()) != null) {
    		
        	String reversed_line = new StringBuilder(line).reverse().toString();
        	String line_without_vowels = line.replaceAll("[AEIOUaeiou]", "");
		
		Iterator salted_passwords_salts_it = salted_passwords_salts.entrySet().iterator();
		
		//to populate salted hashes of every word in the dictionary , line reversed and without vowels
    		while (salted_passwords_salts_it.hasNext()) {
    			Map.Entry pair = (Map.Entry)salted_passwords_salts_it.next();
        		byte[] account_password_hash_salt = DatatypeConverter.parseHexBinary(pair.getValue().toString());
        			
        		salted_hashes_dict.put(stringToSha1_salted(account_password_hash_salt,line),line);
        			
    	        	salted_hashes_dict.put(stringToSha1_salted(account_password_hash_salt,reversed_line),reversed_line);
    	        	
    	        	salted_hashes_dict.put(stringToSha1_salted(account_password_hash_salt,line_without_vowels),line_without_vowels);
    		}
    	        
		//to populate unsalted hashes , line reversed and without vowels
    	        hashes_dict.put(stringToSha1(line),line);
    	        //salted_hashes_dict.put(stringToSha1_salted(account_password_hash_salt,line),line);
    	        
    	        //We test if the password matches a reversed dictionary entry

    	        hashes_dict.put(stringToSha1(reversed_line),reversed_line);
    	        //salted_hashes_dict.put(stringToSha1_salted(account_password_hash_salt,reversed_line),reversed_line);
    	        
    	        //We test if the password matches a dictionary entry without its vowels

    	        hashes_dict.put(stringToSha1(line_without_vowels),line_without_vowels);
    	        //salted_hashes_dict.put(stringToSha1_salted(account_password_hash_salt,line_without_vowels),line_without_vowels);
    	        
    	        
    	        
    	  }
    		
	
}

	public static void main(String[] args) throws Exception{
	
		//populate() ;
		salted_and_unsalted() ;
    		
    }
}
