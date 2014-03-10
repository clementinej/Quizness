package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Site {
	
	private static ArrayList<String> announcement = new ArrayList<String>(); 

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
	
	public static int getNumMsgs() throws Exception{
		return getCount("messages", "note"); 
	};
	
	public static int getNumChallenges() throws Exception {
		return getCount("messages", "challenge"); 
	}
	
	public static int getNumReqs() throws Exception {
		return getCount("messages", "friendRequest"); 
	}
	
	private static int getCount(String tablename, String type) throws Exception{
		int count = 0;
		String query = "SELECT COUNT(*) FROM ? WHERE messageType = ?";
		PreparedStatement ps = ServerConnection.getConnection().prepareStatement(query);
		ps.setString(1, tablename);
		ps.setString(2,  type);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) rs.getInt(1);
		return count; 
	}
	
	private static int getCount(String tableName) throws Exception{
		int count = 0;
		Statement st = ServerConnection.getConnection().createStatement();
		ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM " + tableName);
		while(rs.next()) rs.getInt(1);
		return count; 
	}
	
	public static void setAnnouncement(int userID, String update) throws Exception{
		User user = ServerConnection.getUser(userID);
		if(user.isAdmin()){
			announcement.add(update); 
		}
	}
	
	// Return the last num of announcements
	public static ArrayList<String> getAnnouncement(int num){
		if(announcement.size() < num) {
			return announcement;
		} else {
			ArrayList<String> result = new ArrayList<String>(); 
			result.addAll(announcement.size() - num, announcement); 
			return result; 
		} 
	}
}
