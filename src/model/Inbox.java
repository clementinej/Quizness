package model;

public class Inbox {

	
	public Message getMessage(int messageID){
		return new Message(); 
	}
	
	public int numFriendReqs(int userID){
		return 0;
	}
	
	public int numNewMessages(int userID){
		return 0;
	}
	
	public int  numChallenges(int userID){
		return 0; 
	}
}
