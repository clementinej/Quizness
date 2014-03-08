package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Inbox implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8390975011058743111L;
	private int userID;
	private int inboxID;
	private ArrayList<Integer> requests; 
	private ArrayList<Integer> notes; 
	private ArrayList<Integer> challenges; 
	
	public Inbox(int userID){
		this.userID = userID;
		this.requests = new ArrayList<Integer>();
		this.notes = new ArrayList<Integer>();
		this.challenges = new ArrayList<Integer>(); 
	}
	
	// Set the ID of the inbox
	public void setID(int inboxID){
		this.inboxID = inboxID;
	}
	
	// Get the id of the user
	public int getUserID(){
		return this.userID;
	}
	
	public ArrayList<Integer> getRequests(){
		return requests;
	}
	
	public ArrayList<Integer> getNotes(){
		return notes; 
	}
	
	public ArrayList<Integer> getChallenges(){
		return challenges; 
	}
	
	// Add an existing Message to this Inbox
	public void addNote(int messageID) throws Exception {
		
		notes.add(messageID);
	}
	
	public void addChallenge(int messageID) throws Exception {
		challenges.add(messageID);
	}
	
	public void addFriendRequest(int messageID) throws Exception {
		requests.add(messageID);
	}
		
	// Return a message object given the ID
	public Message getMessage(int messageID) throws Exception{
		if(challenges.contains(messageID) || requests.contains(messageID) || notes.contains(messageID)){
			return ServerConnection.getMessage(messageID);
		} else return null; 
	}
	
	// Return the number of friend requests
	public int getNumFriendReqs(){
		return requests.size();
	}
	
	// Return the number of new messages
	public int getNumNotes(){
		return notes.size(); 
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
