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
	
	public int getID(){
		return this.inboxID; 
	}
	
	// Get the id of the user
	public int getUserID(){
		return this.userID;
	}
	
	// change int to actual object
	
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
		ServerConnection.updateInbox(this);
	}
	
	public void addChallenge(int messageID) throws Exception {
		challenges.add(messageID);
		ServerConnection.updateInbox(this);
	}
	
	public void addFriendRequest(int messageID) throws Exception {
		requests.add(messageID);
		ServerConnection.updateInbox(this);
	}
	
	public boolean removeNote(int messageID) throws Exception {
		int removed = notes.remove(messageID);
		ServerConnection.updateInbox(this);
		if(removed == messageID) return true; 
		return false; 
	}
	
	public boolean removeChallenge(int messageID) throws Exception {
		int removed = challenges.remove(messageID);
		ServerConnection.updateInbox(this);
		if(removed == messageID) return true; 
		return false; 
	}
	
	public boolean removeFriendRequest(int messageID) throws Exception {
		int removed = requests.remove(messageID);
		ServerConnection.updateInbox(this);
		if(removed == messageID) return true; 
		return false; 
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
	
	public boolean hasPendingRequestFrom(int fromID) throws Exception{
		int num = requests.size(); 
		for(int i = 0; i < num; i++){
			FriendRequest request = (FriendRequest) Message.getMessage(requests.get(i));
			if(request.fromID == fromID) return true;
		}
		return false;
	}
	
	// Return the inbox for this specific user
	public static Inbox getInbox(int userID) throws Exception {
		return ServerConnection.getInboxWithUserID(userID); 
	}
	
	public void reset() throws Exception{
		challenges.clear();
		requests.clear();
		notes.clear();
		ServerConnection.updateInbox(this);
	}
}
