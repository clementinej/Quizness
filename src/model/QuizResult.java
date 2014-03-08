package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuizResult {
	
	public static ArrayList<Integer> getUserPerformance(int userID, int quizID, int num) throws Exception{
		return QuizSummary.getPerformanceByScore(userID, quizID, num);
	}
	
	public static ArrayList<Integer> getAllPerformance(int quizID, int num) throws Exception{
		return QuizSummary.getTopPerformers(quizID, num);
	}
	
	public static ArrayList<Integer> getFriendPerformance(int quizID, int userID, int num) throws Exception{
		Connection con = ServerConnection.getConnection();
		String query = "SELECT id FROM quizTries INNER JOIN friendships USING (toID) WHERE fromID = " + userID
				+ " ORDER BY score DESC LIMIT" + num; 
		PreparedStatement ps = con.prepareStatement(query);
		ps.executeUpdate();
		return executeQuery(query);
	}
	
	// Return the average score of this user
	//TODO: Fix AND operator, it simply doesn't work.
	public static double getUserAverageScore(int userID, int quizID) throws Exception{
		String query = "SELECT AVG(score)  FROM quizTries WHERE userID = \"" + userID
				+ "\" AND WHERE quizID = \"" + quizID+ "\"";
		System.out.println("getUserAverageScore: " + query);
		PreparedStatement ps = ServerConnection.getConnection().prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) return rs.getDouble(1);
		else return -1.0; 
	}
	
	// Return the average score of all users
	public static double getAllAverageScore(int quizID) throws Exception {
		String query = "SELECT AVG(score)  FROM quizTries WHERE quizID = " + quizID;
		PreparedStatement ps = ServerConnection.getConnection().prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) return rs.getDouble(1);
		else return -1.0; 
	}
	
	// Return the average score of this user's friends
	public static double getFriendsAverageScore(int userID, int quizID) throws Exception {
		String query = "SELECT AVG(score) FROM quizTries WHERE quizID = \"" + quizID
				+ "\" INNER JOIN friendships USING (toID) WHERE fromID = \"" + userID +"\"";
		System.out.println(query);
		PreparedStatement ps = ServerConnection.getConnection().prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) return rs.getDouble(1);
		else return -1.0; 
	}
	
	// TODO: timespent for user, everyone, user's friend average time
	// TODO: number of attempts for user, everyone, user's friend average attempts
	
	// Execute the given query, throws exception=
	private static ArrayList<Integer> executeQuery(String query) throws Exception{
		Connection con = ServerConnection.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ps.executeUpdate();
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
	// average performance specifications
}
