package model;

import java.util.Date;

public class Message {
		public int id;
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
		public boolean isUnread() {
			return unread;
		}
		public void setUnread(boolean unread) {
			this.unread = unread;
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
		public int fromID;
		public int toID;
		public boolean unread;
		public String subject;
		public String body;
		public Date sentAt;
		public boolean viewed;
		
		public Date getSentAt() {
			return sentAt;
		}
		public void setSentAt(Date sentAt) {
			this.sentAt = sentAt;
		}
		public void markAsRead() {
			viewed = true;
		}
		public boolean getViewed() {
			return viewed;
		}
}
