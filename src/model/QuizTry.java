package model;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuizTry implements Serializable {
	
	private int tryID;
	private int userID;
	private int quizID;
	private String userName;
	private Quiz quiz;
	private User user;
	private double elapsedTime;
	private double startTime;
	private ArrayList<String[]> responses;
	private boolean inProgress;
	private double score;
	private Date dateTaken;
	private boolean isPractice;
	private int index;
	private ArrayList<Question> questions;
	
	public QuizTry(String tryID, int userID, int quizID, boolean mode, boolean random) throws Exception{
		this.user = ServerConnection.getUser(userID);
		this.quiz = ServerConnection.getQuiz(quizID);
		this.tryID = -1;
		this.userName = user.getUserName();
		this.userID = user.getUserID();
		this.quizID = quizID;
		this.index = 0;
		this.questions = quiz.getQuestions();
		
		if (quiz.hasRandomMode()){
			if (random)
				shuffleQuestions();
		}
		this.startTime = System.currentTimeMillis();
		this.elapsedTime = 0;
		this.inProgress = true;
		this.score = -1;
		
		//if there is no practice mode available, then isPractice will also be false
		if (quiz.hasPracticeMode())
			this.isPractice = mode;
		else{
			this.isPractice = false;
		}
	}
	
	private void shuffleQuestions(){
		Collections.shuffle(questions);
	}
	
/*
 * All the "gets" 
 * i.e. getTry, getQuestions, getQuiz, etc	
 */
	public static QuizTry getTry(int quizTryID) throws Exception{
		return ServerConnection.getQuizTry(quizTryID);
	}
	
	public Date getDate(){
		return dateTaken;
	}
	public boolean isPractice(){
		return isPractice;
	}
	
	public ArrayList<Question> getQuestions(){
		return questions;
	}
	
	public Question getNextQuestion(){
		index ++;
		return questions.get(index - 1);
	}
	
	public double getScore(){
		return score;
	}

	
	public void saveProgress(ArrayList<String[]> responses) throws Exception{
		elapsedTime = System.currentTimeMillis() - startTime;
		this.responses = responses;
		User user = ServerConnection.getUser(userName);
		user.addTry(this);
	}
	
	public void gradeQuiz(ArrayList<String[]>responses){
		elapsedTime = System.currentTimeMillis() - startTime;
		this.responses = responses;
		score = quiz.calculateScore(responses);
		if (score > quiz.getMaxScore()){
			user.addAchievement(new TheGreatest());
		}
		inProgress = false;
		user.addTry(this);
		checkTryAchievements();
		dateTaken = new Date();
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
	
	public int getTryID(){
		return tryID;
	}
	
	public int getUserID(){
		return userID;
	}
	
	public int getQuizID(){
		return quizID;
	}
	
	public void setID(int tryID){
		this.tryID = tryID;
	}
}
