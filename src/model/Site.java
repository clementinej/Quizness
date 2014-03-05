package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Site {

	public static int getTotalNumberOfQuizzes() throws Exception{
		return getCount("quizzes");
	}
	
	public static int getTotalNumberOfUsers() throws Exception{
		return getCount("users");
	}
	
	public static int getTotalNumberTags(){
		return 0;
	}
	
	public static int getTotalNumberQuizzesTaken() throws Exception{
		int count = 0;
		Statement st = ServerConnection.getConnection().createStatement();
		ResultSet rs = st.executeQuery("SELECT COUNT(DISTINCT quizID) FROM quizTries");
		while(rs.next()) rs.getInt(1);
		return count; 
	}
	
	public static int getNumFriendships() throws Exception{
		return getCount("friendships"); 
	}
	
	private static int getCount(String tableName) throws Exception{
		int count = 0;
		Statement st = ServerConnection.getConnection().createStatement();
		ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM " + tableName);
		while(rs.next()) rs.getInt(1);
		return count; 
	}
	
	
}
