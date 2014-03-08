package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
