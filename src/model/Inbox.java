package model;

import java.util.ArrayList;

public class Inbox {
	
	private int userID;
	private int inboxID;
	private ArrayList<Integer> requests; 
	private ArrayList<Integer> messages; 
	private ArrayList<Integer> challenges; 
	
	public Inbox(int userID){
		this.userID = userID;
		this.requests = new ArrayList<Integer>();
		this.messages = new ArrayList<Integer>();
		this.challenges = new ArrayList<Integer>(); 
	}
	
	// Set the ID of the inbox
	public void setID(int inboxID){
		this.inboxID = inboxID;
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
	public int getNumFriendReqs(){
		return requests.size();
	}
	
	// Return the number of new messages
	public int getNumNewMessages(){
		return messages.size(); 
	}
	
	// Return the number of challenges
	public int getNumChallenges(){
		return challenges.size(); 
	}
	
	// Return the inbox for this specific user
	public static Inbox getInbox(int userID) throws Exception {
		return ServerConnection.getInboxWithUserID(userID); 
	}
}
