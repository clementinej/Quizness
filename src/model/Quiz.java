package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class Quiz {
	
	
	public Quiz(){
		
	}
	
	static Quiz getQuiz(int quizID) throws ClassNotFoundException, SQLException, IOException{
		return ServerConnection.getQuiz(quizID);
	}
	
	static void addQuiz(Quiz quiz){
		
	}
}
