package model;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Quiz implements Serializable{
	
	private int quizID;
	private int creatorID;
	private int createTime; 
	private List<Question> questions; 
	private String description; 
	
	
	public Quiz(int quizID, ArrayList<Question> questions){
		this.quizID = quizID;
		this.questions = questions;
	}
	
	public int getQuizID(){
		return quizID;
	}
	
	public Question getQuestion(int index){
		return questions.get(index);
	}
	
	
	public void getTopQuizzes(){
		
	}
	
	public void getRecentlyPlayedQuizzes(){
		
	}
	
	public void getRecentlyCreatedQuizzes(){
		
	}
	
	public void getHighScore(){
		
	}
}
