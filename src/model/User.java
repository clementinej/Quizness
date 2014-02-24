package model;

public class User {

	private String userID;
	private boolean isAdmin;
	
	public User(String userID, boolean isAdmin){
		this.userID = userID;
		this.isAdmin = isAdmin;
	}
	
	public boolean isAdmin(){
		return isAdmin;
	}
	
	public String getUserID(){
		return userID;
	}
}
