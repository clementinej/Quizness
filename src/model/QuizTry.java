package model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuizTry {
	private String tryID;
	private int userID;
	private int quizID;
	private Quiz quiz;
	private double timeElapsed;
	private double startTime;
	private ArrayList<String[]> responses;
	private int index;
	
	public QuizTry(String tryID, int userID, int quizID) throws ClassNotFoundException, SQLException, IOException{
		this.tryID = tryID;
		this.userID = userID;
		this.quizID = quizID;
		this.quiz = Quiz.getQuiz(quizID);
		this.startTime = System.currentTimeMillis();
		this.timeElapsed = 0;
		this.index = 0;
	}
	
	public Question getQuestion(int index){
		Question question = quiz.getQuestion(index);
		this.index = index;
		return question;
	}
	
	public void saveProgress(ArrayList<String[]> responses) throws SQLException, ClassNotFoundException, IOException{
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
	
	public int getUserID(){
		return userID;
	}
	
	public int getQuizID(){
		return quizID;
	}
}
