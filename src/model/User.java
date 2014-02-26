package model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

	private int userID;
	private boolean isAdmin;
	private ArrayList<Quiz> quizzesMade;
	private ArrayList<QuizTry> quizzesTried;
	
	public User(int userID, boolean isAdmin){
		this.userID = userID;
		this.isAdmin = isAdmin;
		this.quizzesMade = new ArrayList<Quiz>();
		this.quizzesTried = new ArrayList<QuizTry>();
	}
	
	public void addTry(QuizTry quizTry){
		int index = quizzesTried.indexOf(quizTry);
		if (index == -1){
			quizzesTried.add(quizTry);
		} else {
			quizzesTried.set(index, quizTry);
		}
	}
	
	public boolean isAdmin(){
		return isAdmin;
	}
	
	public int getUserID(){
		return userID;
	}
	
	public void makeQuiz(Quiz quiz){
		quizzesMade.add(quiz);
	}
	
	public void deleteQuiz(Quiz quiz){
		quizzesMade.remove(quiz);
	}
}
