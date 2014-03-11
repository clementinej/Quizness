package control;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import model.*;

public class QuiznessAccountManager {

	private String salt;
	
	/*
	 *  Creates Patrick and Molly as admins
	 */
	public QuiznessAccountManager() throws Exception {//initialize with two accounts
		salt = "@#$%@#FERYS^%#$YSEAH#$E73452WE@#%3#$";
		createNewAccount("Patrick", "patrick@stanford.edu" ,"1234", "", "", true);//add emails
		createNewAccount("Molly", "molly@dog.com","FloPup", "", "", true);
	}

	/*
	 *  Returns a true upon successful password creation.
	 *  Returns false if the username is already taken.
	 *  Adds a grain of salt to the password for extra security before hashing.
	 */
	public boolean createNewAccount(String userName, String email, String pw, String newLocation, String newAboutMe, boolean isAdmin) throws Exception {
		boolean accountFree = false;	
		System.out.println("In account manager, createNewAccount.");
		if(User.emailIsAvailable(email)) { //read from database			
			System.out.println("createNewAccount: db read successful");
			String unencryptedPlusSalt = pw + salt;
			String hashedPW = generateHash(unencryptedPlusSalt);
			String aboutMe = newAboutMe;
			String location = newLocation;
			User newUser = new User(isAdmin, userName, hashedPW, email, aboutMe, location);
			model.ServerConnection.addUser(newUser);
			accountFree = true;
		} else System.out.println("AccountManager: Email "+ email +" was not available.");
		return accountFree;
	}
	
	/*
	 * Will return true if the login and pw and hash function is in stored within the account manager.
	 * login string can either be an email or a username.
	 */
	public boolean validLogin(String login, String pw) throws SQLException {
		boolean validLogin = false;		
		if(!User.emailIsAvailable(login)) { //read from database
			System.out.println("validLogin: db read successful");
			String unencryptedPlusSalt = pw + salt;
			String hashedPW = generateHash(unencryptedPlusSalt);
			String stored_pw = "";
			stored_pw = User.getPasswordHashFromEmail(login);
			if(stored_pw != null){
				if(stored_pw.equals(hashedPW)){//if password is correct
					validLogin = true;
				}
			}
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
		if(manager.createNewAccount("Lloyd2", "lglucin@stanford.edu","ILikeCats29", "","", true));
			System.out.println("Successfully Created New Account");
		if(manager.validLogin("Lloyd2", "ILikeCats29"))
			System.out.println("Successful Login");
		if(manager.validLogin("lglucin@stanford.edu", "ILikeCats29"))
			System.out.println("Successful Login From email");
		if(manager.validLogin("Patrick", "1234"))
			System.out.println("Successful Login");
	}
}
