package model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuizTry {
	private String tryID;
	private int userID;
	private int quizID;
	private Quiz quiz;
	private double elapsedTime;
	private double startTime;
	private ArrayList<String[]> responses;
	private boolean inProgress;
	private double score;
	
	public QuizTry(String tryID, int userID, int quizID) throws ClassNotFoundException, SQLException, IOException{
		this.tryID = tryID;
		this.userID = userID;
		this.quizID = quizID;
		this.quiz = Quiz.getQuiz(quizID);
		this.startTime = System.currentTimeMillis();
		this.elapsedTime = 0;
		this.inProgress = true;
		this.score = -1;
	}
	
	public Question getQuestion(int index){
		return quiz.getQuestion(index);
	}
	
	public void saveProgress(ArrayList<String[]> responses) throws SQLException, ClassNotFoundException, IOException{
		elapsedTime = System.currentTimeMillis() - startTime;
		this.responses = responses;
		User user = ServerConnection.getUser(userID);
		user.addTry(this);
	}
	
	public void gradeQuiz(ArrayList<String[]>responses){
		elapsedTime = System.currentTimeMillis() - startTime;
		this.responses = responses;
		score = quiz.calculateScore(responses);
		inProgress = false;
		
	}
	
	public boolean isInProgress(){
		return inProgress;
	}
	
	public ArrayList<String[]> getResponses(){
		return responses;
	}
	public void restartTry(){
		startTime = System.currentTimeMillis();
	}
	
	public double getTime(){
		return elapsedTime;
	}
	
	public String getTryID(){
		return tryID;
	}
	
	public int getUserID(){
		return userID;
	}
	
	public int getQuizID(){
		return quizID;
	}
}
