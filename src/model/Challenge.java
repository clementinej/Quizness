package model;

import java.util.Date;

public class Challenge extends Message {
	
	private boolean accepted; 
	private int quizID;
	
	public Challenge(int fromID, int toID, int quizID, String subject, String body){
		this.fromID = fromID;
		this.toID = toID; 
		this.unread = true;
		this.subject = subject;
		this.body = body; 
		this.sentAt = new Date(); 
		this.viewed = false;
		this.accepted = false; 
		this.quizID = quizID; 
	}
	
	// Return the quizID of this challenge
	public int getQuizID(){
		return quizID; 
	}
	
	// Accept the challenge
	public void accept(){
		accepted = true; 
	}
	
}
