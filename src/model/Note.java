package model;

import java.io.Serializable;
import java.util.Date;

public class Note extends Message implements Serializable {

	public Note(int fromID, int toID, String subject, String body){
		this.fromID = fromID;
		this.toID = toID; 
		this.unread = true;
		this.subject = subject;
		this.body = body; 
		this.sentAt = new Date(); 
		this.viewed = false;
		this.messageType = "note";
	}
	
	
}
