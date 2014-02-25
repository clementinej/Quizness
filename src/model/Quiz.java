package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Quiz {
	
	private int quizID;
	private int creatorID;
	private ArrayList<Question> questions; 
	
	public Quiz(int quizID, ArrayList<Question> questions){
		
	}
	
	public int getQuizID(){
		return quizID;
	}
	
	public Question getQuestion(int index){
		return questions.get(index);
	}
	
	static Quiz getQuiz(int quizID) throws ClassNotFoundException, SQLException, IOException{
		return ServerConnection.getQuiz(quizID);
	}
	
	static void addQuiz(Quiz quiz){
		ServerConnection.addQuiz(quiz.getQuizID(), quiz);
	}

	
}
