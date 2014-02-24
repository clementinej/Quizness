package control;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class AccountManager {
	Map<String, String> accounts;
	
	public AccountManager() {//initialize with two accounts
		accounts = new HashMap<String, String>();
		accounts.put("Patrick", "1234");
		accounts.put("Molly", "FloPup");
	}

	/*
	 *  Returns a true upon successful password creation.
	 *  Returns false if the username is already taken.
	 */
	public boolean createNewAccount(String userName, String pw) {
		boolean accountFree = false;
		if(!accounts.containsKey(userName)) {//if there account is free add it
			String hashedPW = generateHash(pw);
			accounts.put(userName, hashedPW);
			accountFree = true;
		}		
		return accountFree;
	}
	
	/*
	 * 
	 */
	public boolean validLogin(String userName, String pw) {
		boolean validLogin = false;
		if(accounts.containsKey(userName)) {//if username is stored in database
			String hashedPW = generateHash(pw);
			String stored_pw = accounts.get(userName);
			if(stored_pw.equals(hashedPW))//if password is correct
				validLogin = true;
			else //if incorrect password
				validLogin = false;
		}
		return validLogin;
	}
	
	
	/*
	 *  Generates a hash given a password string
	 */
	private static String generateHash(String word) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		byte[] mdbytes = word.getBytes();
        mdbytes = md.digest(mdbytes);
  
        String hashedText = hexToString(mdbytes);
		return hashedText;
	}
	
	
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	

}
