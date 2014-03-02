package model;

import java.util.Date;

public class Note extends Message {

	public Note(int fromID, int toID, String subject, String body){
		this.fromID = fromID;
		this.toID = toID; 
		this.unread = true;
		this.subject = subject;
		this.body = body; 
		this.sentAt = new Date(); 
		this.viewed = false;
	}
	
	
}
