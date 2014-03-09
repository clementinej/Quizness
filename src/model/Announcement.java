package model;

public class Announcement {

	private int id;
	private String subject;
	private String body;
	
	public Announcement(String subject, String body){
		this.subject = subject;
		this.body = body;
	}
	
	public void setID(int id){
		this.id = id;
	}
	
	public int getID(){
		return id;
	}
	public String getSubject(){
		return subject;
	}
	public String getBody(){
		return body;
	}
}
