package model;

import java.util.ArrayList;

public class Inbox {
	
	private int userID;
	private ArrayList<Integer> requests; 
	private ArrayList<Integer> messages; 
	private ArrayList<Integer> challenges; 
	
	public Inbox(int userID){
		this.userID = userID;
		this.requests = new ArrayList<Integer>();
		this.messages = new ArrayList<Integer>();
		this.challenges = new ArrayList<Integer>(); 
	}
	
	// Add an existing Message to this Inbox, using its ID
	public void addMessage(int messageID) throws Exception{
		Message message = getMessage(messageID);
		if(message instanceof Note){
			messages.add(messageID);
		}
		if(message instanceof Challenge){
			challenges.add(messageID);
		}
		if(message instanceof FriendRequest){
			requests.add(messageID);
		}
	}
	
	// Add an existing Message to this Inbox, using the object
	public void addMessage(Message message) throws Exception{
		int messageID = message.getId();
		if(message instanceof Note){
			messages.add(messageID);
		}
		if(message instanceof Challenge){
			challenges.add(messageID);
		}
		if(message instanceof FriendRequest){
			requests.add(messageID);
		}
	}
		
	// Return a message object given the ID
	public Message getMessage(int messageID) throws Exception{
		if(challenges.contains(messageID) || requests.contains(messageID) || messages.contains(messageID)){
			return ServerConnection.getMessage(messageID);
		} else return null; 
	}
	
	// Return the number of friend requests
	public int numFriendReqs(int userID){
		if(userID == this.userID) return requests.size();
		else return -1; 
	}
	
	// Return the number of new messages
	public int numNewMessages(int userID){
		if(userID == this.userID) return messages.size();
		else return -1; 
	}
	
	// Return the number of challenges
	public int  numChallenges(int userID){
		if(userID == this.userID) return challenges.size();
		else return -1; 
	}
}
