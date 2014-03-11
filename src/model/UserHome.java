package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserHome {

	// Return the top x number of quizzes
	public static ArrayList<Integer> getTopQuizzes(int num) {
		try {
		String query = "SELECT id FROM quizzes ORDER BY numTimesTaken DESC LIMIT " + num;
		return executeQuery(query); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<Integer>();
	}
	

	// Return x number of recently created quizzes
	public static ArrayList<Integer> getRecentlyCreatedQuizzes(int num) throws Exception{
		String query = "SELECT id FROM quizzes ORDER BY dateCreated DESC LIMIT " + num;
		return executeQuery(query); 
	}
	
	// Return x number of their own recently played quizzes
	public static ArrayList<Integer> getRecentlyPlayedQuizzes(int num, int userID) throws Exception{
		String query = "SELECT DISTINCT quizID FROM quizTries WHERE userID = " + userID 
				+ " ORDER BY dateCreated DESC LIMIT " + num;
		return executeQuery(query);
	}
	
	// Return x number of recently created quizzes by this user
	public static ArrayList<Integer> getRecentlyCreatedQuizzesByUser(int num, int userID) throws Exception{
		String query = "SELECT id FROM quizzes WHERE creatorID = " + userID + 
				" ORDER BY dateCreated DESC LIMIT " + num;
		return executeQuery(query); 
	}
	
	// Return x number of recently created quizzes by this user
	public static ArrayList<Integer> getRecentlyCreatedByFriends(int num, int userID) throws Exception{
		Connection con = ServerConnection.getConnection();
		String query = "SELECT id FROM quizzes INNER JOIN friendships ON quizzes.creatorID = friendships.toID "
				+ "WHERE friendships.fromID = " + userID
				+ " ORDER BY dateCreated DESC LIMIT " + num; 
		PreparedStatement ps = con.prepareStatement(query);
		return executeQuery(query);
	}
	 
	// Return x num of quizzes recently taken by friends
	public static ArrayList<Integer> getRecentlyTakenByFriends(int num, int userID) throws Exception{
		Connection con = ServerConnection.getConnection();
		String query = "SELECT quizID FROM quizTries INNER JOIN friendships ON quizTries.userID = friendships.toID "
				+ "WHERE fromID = " + userID
				+ " ORDER BY dateCreated DESC LIMIT " + num; 
		PreparedStatement ps = con.prepareStatement(query);
		return executeQuery(query);
	}
	
	// Execute the given query, throws exception=
	private static ArrayList<Integer> executeQuery(String query) throws Exception{
		Connection con = ServerConnection.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ps.executeQuery();
		return resultSetToArray(ps.getResultSet()); 
	}
	
	// Return a set of IDs from a ResultSet
	private static ArrayList<Integer> resultSetToArray(ResultSet rs) throws Exception{
		ArrayList<Integer> result = new ArrayList<Integer>();
		while(rs.next()){
			result.add(rs.getInt(1)); 
		}
		return result; 
	}
}
