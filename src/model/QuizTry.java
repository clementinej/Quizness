package model;

import java.util.ArrayList;

public class QuizTry {
	private String tryID;
	private String userID;
	private String quizID;
	private Quiz quiz;
	private double timeElapsed;
	private double startTime;
	private ArrayList<String[]> responses;
	private int index;
	
	public QuizTry(String tryID, String userID, String quizID){
		this.tryID = tryID;
		this.userID = userID;
		this.quizID = quizID;
		this.quiz = Quiz.getQuiz(quizID);
		this.startTime = System.currentTimeMillis();
		this.timeElapsed = 0;
		this.index = 0;
	}
	
	public Question getQuestion(int index){
		Question question =getQuestion(quiz.getQuestion(index));
		this.index = index;
		return question;
	}
	
	public void saveProgress(ArrayList<String[]> responses){
		timeElapsed = System.currentTimeMillis() - startTime;
		this.responses = responses;
		User user = ServerConnection.getUser(userID);
		user.addTry(this);
	}
	
	public void restartTry(){
		startTime = System.currentTimeMillis();
	}
	
	public String getTryID(){
		return tryID;
	}
	
	public String getUserID(){
		return userID;
	}
	
	public String getQuizID(){
		return quizID;
	}
}
