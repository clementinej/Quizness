package model;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class QuizTry implements Serializable {
	private static final long  serialVersionUID = -7112426021380951797L;
	public static final String MYSQL_USERNAME = "ccs108wang8";
	public static final String MYSQL_PASSWORD = "vohpaifa";
	public static final String MYSQL_DATABASE_SERVER = "mysql-user.stanford.edu";
	public static final String MYSQL_DATABASE_NAME = "c_cs108_wang8";
	
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
	
	public QuizTry(int userID, int quizID, boolean practice, boolean random) throws Exception{
		this.user = ServerConnection.getUser(userID);
		this.quiz = ServerConnection.getQuiz(quizID);
		this.tryID = -1;
		this.userName = user.getUserName();
		this.userID = user.getUserID();
		this.quizID = quizID;
		this.index = 0;
		this.questions = quiz.getQuestions();
		this.responses = new ArrayList<String[]>();
		if (quiz.hasRandomMode()){
			if (random)
				shuffleQuestions();
		}
		this.startTime = System.currentTimeMillis();
		this.elapsedTime = 0;
		this.inProgress = true;
		this.score = 0;
		
		//if there is no practice mode available, then isPractice will also be false
		if (quiz.hasPracticeMode())
			this.isPractice = practice;
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
	
	public boolean hasNext() {
		return index != questions.size();
	}
	
	public Question getNextQuestion() throws Exception{
		index ++;
		ServerConnection.updateQuizTry(this);
		return questions.get(index - 1);
	}
	
	public double getScore(){
		return score;
	}

	
	public void saveProgress(ArrayList<String[]> responses) throws Exception{
		elapsedTime = System.currentTimeMillis() - startTime;
		this.responses = responses;//TODO: maybe store the responses internally
		User user = ServerConnection.getUser(userID);
		user.addTry(this);
		ServerConnection.updateQuizTry(this);
	}
	
	public void gradeQuiz(ArrayList<String[]>responses) throws Exception{
		elapsedTime = System.currentTimeMillis() - startTime;
		this.responses = responses;
		score = quiz.calculateScore(responses);
		if (score > quiz.getMaxScore()){
			user.addAchievement(new TheGreatest());
		}
		inProgress = false;//TODO:what about automatic grading?
		user.addTry(this);
		checkTryAchievements();
		dateTaken = new Date();
		ServerConnection.updateQuizTry(this);
	}

	//for multipage where responses are stored in this object
	public void gradeQuiz() throws Exception {
		elapsedTime = System.currentTimeMillis() - startTime;
		score = quiz.calculateScore(responses);
		if (score > quiz.getMaxScore()){
			user.addAchievement(new TheGreatest());
		}
		inProgress = false;//TODO:what about automatic grading?
		user.addTry(this);
		checkTryAchievements();
		dateTaken = new Date();
		ServerConnection.updateQuizTry(this);
	}
	
	private void checkTryAchievements(){
		if (user.numQuizzesTaken() == 10)
			user.addAchievement(new QuizMachine());
	}
	
	public boolean isInProgress(){
		return inProgress;
	}
	
	public void setToDone() throws Exception {
		inProgress = false;
	  	ServerConnection.updateQuizTry(this);
//		Connection con = ServerConnection.getConnection();
//		Statement stmt = con.createStatement();
//		stmt.executeQuery("USE " + MYSQL_DATABASE_NAME);
//		ResultSet rs = stmt.executeUpdate("SELECT * FROM users WHERE username = \""+ userName + "\"");
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
	
	public int getQuestionNum() {
		return index;
	}
	
	public void setID(int tryID){
		this.tryID = tryID;
	}
	
}
