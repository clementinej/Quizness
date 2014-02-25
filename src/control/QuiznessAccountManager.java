package control;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class QuiznessAccountManager {

	Map<String, String> accounts;
	private String salt;
	
	/*
	 *  
	 */
	public QuiznessAccountManager() {//initialize with two accounts
		accounts = new HashMap<String, String>();
		salt = "@#$%@#FERYS^%#$YSEAH#$E73452WE@#%3#$";
		createNewAccount("Patrick", "1234");
		createNewAccount("Molly", "FloPup");
	}

	/*
	 *  Returns a true upon successful password creation.
	 *  Returns false if the username is already taken.
	 *  Adds a grain of salt to the password for extra security before hashing.
	 */
	public boolean createNewAccount(String userName, String pw) {
		boolean accountFree = false;
		if(!accounts.containsKey(userName)) {//if there account is free add it
			String unencryptedPlusSalt = pw + salt;
			String hashedPW = generateHash(unencryptedPlusSalt);
			accounts.put(userName, hashedPW);
			accountFree = true;
		}		
		return accountFree;
	}
	
	/*
	 * will return true if the userName and pw and hash function is in stored within the account manager.
	 */
	public boolean validLogin(String userName, String pw) {
		boolean validLogin = false;
		if(accounts.containsKey(userName)) {//if username is stored in database
			String unencryptedPlusSalt = pw + salt;
			String hashedPW = generateHash(unencryptedPlusSalt);
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
		

	/*
	 * For testing purposes
	 */
	public static void main(String[] args) {
		QuiznessAccountManager manager = new QuiznessAccountManager();
		System.out.println("Start");
		if(manager.createNewAccount("Lloyd", "ILikeCats29"));
			System.out.println("Successfully Created New Account");
		if(manager.validLogin("Lloyd", "ILikeCats29"))
			System.out.println("Successful Login");
		if(manager.validLogin("Patrick", "1234"))
			System.out.println("Successful Login");
	}

}
