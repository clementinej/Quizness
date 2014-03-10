package model;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
		/**
	 * 
	 */
	private static final long serialVersionUID = -1157902140039424209L;
		public int id;
		public int fromID;
		public int toID;
		public boolean unread;
		public String subject;
		public String body;
		public Date sentAt;
		public boolean viewed;
		public String messageType; 
		
		// Add a message to the database
		public static int addMessage(Message message) throws Exception{
			return ServerConnection.addMessage(message);
		}
		
		// Return a message from the database
		public static Message getMessage(int messageID) throws Exception{
			return ServerConnection.getMessage(messageID);
		}
		public String getMessageType(){
			return messageType; 
		}
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getFromID() {
			return fromID;
		}
		public void setFromID(int fromID) {
			this.fromID = fromID;
		}
		public int getToID() {
			return toID;
		}
		public void setToID(int toID) {
			this.toID = toID;
		}
		public String getSubject() {
			return subject;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}
		public String getBody() {
			return body;
		}
		public void setBody(String body) {
			this.body = body;
		}
		
		public Date getSentAt() {
			return sentAt;
		}
		public void setSentAt(Date sentAt) {
			this.sentAt = sentAt;
		}
		public void markAsRead() {
			viewed = true;
		}
		public void markAsUnread(){
			viewed = false; 
		}
		public boolean getViewed() {
			return viewed;
		}
}
