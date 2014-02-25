package model;

import java.util.ArrayList;

public class User {

	private String userID;
	private boolean isAdmin;
	private ArrayList<Quiz> quizzesMade;
	
	public User(String userID, boolean isAdmin){
		this.userID = userID;
		this.isAdmin = isAdmin;
		this.quizzesMade = new ArrayList<Quiz>();
	}
	
	public boolean isAdmin(){
		return isAdmin;
	}
	
	public String getUserID(){
		return userID;
	}
	
	public void makeQuiz(Quiz quiz){
		quizzesMade.add(quiz);
	}
	
	public void deleteQuiz(Quiz quiz){
		quizzesMade.remove(quiz);
	}
}
