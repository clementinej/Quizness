package model;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuizTry implements Serializable {
	private String tryID;
	private int userID;
	private int quizID;
	private Quiz quiz;
	private User user;
	private double elapsedTime;
	private double startTime;
	private ArrayList<String[]> responses;
	private boolean inProgress;
	private double score;
	private Date dateTaken;
	private boolean isPractice;
	
	public QuizTry(String tryID, int userID, int quizID, boolean mode) throws Exception{
		this.tryID = tryID;
		this.userID = userID;
		this.quizID = quizID;
		this.user = ServerConnection.getUser(userID);
		this.quiz = ServerConnection.getQuiz(quizID);

		this.startTime = System.currentTimeMillis();
		this.elapsedTime = 0;
		this.inProgress = true;
		this.score = -1;
		
		//if there is no practice mode available, then isPractice will also be false
		if (quiz.isPracticeMode())
			this.isPractice = mode;
		else{
			this.isPractice = false;
		}
	}
	
	public boolean isPractice(){
		return isPractice;
	}
	
	public ArrayList<Question> getQuestions(int index){
		return null;//quiz.getQuestions(index);
	}
	
	public double getScore(){
		return score;
	}
	
	public Date getDate(){
		return dateTaken;
	}
	
	public void saveProgress(ArrayList<String[]> responses) throws Exception{
		elapsedTime = System.currentTimeMillis() - startTime;
		this.responses = responses;
		User user = ServerConnection.getUser(userID);
		user.addTry(this);
	}
	
	public void gradeQuiz(ArrayList<String[]>responses){
		elapsedTime = System.currentTimeMillis() - startTime;
		this.responses = responses;
		score = quiz.calculateScore(responses);
		if (score > quiz.getHighScore()){
			user.addAchievement(new TheGreatest());
		}
		inProgress = false;
		user.addTry(this);
		checkTryAchievements();
		//dateTaken = new Date();
	}
	
	private void checkTryAchievements(){
		if (user.numQuizzesTaken() == 10)
			user.addAchievement(new QuizMachine());
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
