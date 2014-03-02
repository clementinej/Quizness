package control;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import model.*;

public class QuiznessAccountManager {

	//Map<String, String> accounts;//should not need this later, should read from db
	private String salt;
	
	/*
	 *  Creates Patrick and Molly as admins
	 */
	public QuiznessAccountManager() throws Exception {//initialize with two accounts
	//	accounts = new HashMap<String, String>();
		salt = "@#$%@#FERYS^%#$YSEAH#$E73452WE@#%3#$";
		createNewAccount("Patrick", "1234", true);//add emails
		createNewAccount("Molly", "FloPup", true);
	}

	/*
	 *  Returns a true upon successful password creation.
	 *  Returns false if the username is already taken.
	 *  Adds a grain of salt to the password for extra security before hashing.
	 */
	public boolean createNewAccount(String userName, String pw, boolean isAdmin) throws Exception {
		boolean accountFree = false;	
		if(User.nameIsAvailable(userName)) { //read from database			
			String unencryptedPlusSalt = pw + salt;
			String hashedPW = generateHash(unencryptedPlusSalt);
			String email = "";
			User newUser = new User(isAdmin, userName, hashedPW, email);
			model.ServerConnection.addUser(newUser);
			//newUser.setUserID(userName);
			accountFree = true;
		}		
		
		return accountFree;
	}
	
	/*
	 * will return true if the userName and pw and hash function is in stored within the account manager.
	 */
	public boolean validLogin(String userName, String pw) throws SQLException {
		boolean validLogin = false;		
		if(!User.nameIsAvailable(userName)) { //read from database
			System.out.println("read from db");
			String unencryptedPlusSalt = pw + salt;
			String hashedPW = generateHash(unencryptedPlusSalt);
			String stored_pw = User.getPasswordHash(userName);
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
	public static void main(String[] args) throws Exception {
		QuiznessAccountManager manager = new QuiznessAccountManager();
		System.out.println("Start");
		if(manager.createNewAccount("Lloyd", "ILikeCats29", true));
			System.out.println("Successfully Created New Account");
		if(manager.validLogin("Lloyd", "ILikeCats29"))
			System.out.println("Successful Login");
		if(manager.validLogin("Patrick", "1234"))
			System.out.println("Successful Login");
	}

}
