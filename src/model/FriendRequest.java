package model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class FriendRequest extends Message implements Serializable {
	
	private boolean accepted;
	private String fromUserName;
	private String toUserName;
	
	public FriendRequest(int fromID, int  toID, String subject, String body){
		this.fromID = fromID;
		this.toID = toID;
		this.unread = true;
		this.subject = subject;
		this.body = body; 
		this.sentAt = new Date(); 
		this.viewed = false;
		this.accepted = false; 
		this.messageType = "friendRequest";
	}

	public String getFrom() throws Exception {
		return User.getUser(fromID).getUserName();
	}
	
	public String getTo() throws Exception {
		return User.getUser(toID).getUserName();
	}
	
	// Accept the friend request and update the database
	public void accept(int userID) throws Exception{
		if(userID == toID){
			accepted = true; 
		}
		updateFriendGraph(fromID, toID);
		addFriends();
	}
	
	// Update the user's internal friend list
	private void addFriends() throws Exception{
		User fromUser = User.getUser(fromID);
		User toUser = User.getUser(toID);
		fromUser.addFriend(toUser);
		toUser.addFriend(fromUser);
	}
	
	// Return the status of this request
	public boolean isAccepted(){
		return accepted; 
	}
	
	// Add the friends to the database
	private void updateFriendGraph(int fromID, int toID) throws Exception {
		Connection con = ServerConnection.getConnection();
		String query = "INSERT INTO friendships VALUE(?, ?)";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, fromID);
		ps.setInt(2, toID);
		ps.executeUpdate(); 
	}
}
