package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class QuizSummary {
	
	// Return x number of performances on this specific quiz, order by date 
	public static ArrayList<Integer> getPerformanceByDate(int userID, int quizID, int num) throws Exception{
		String query = "SELECT id FROM quizTries WHERE userID = " + userID
				+ " AND quizID = " + quizID + " ORDER BY dateCreated DESC LIMIT " + num;
		return executeQuery(query);
	}
	
	// Return x number of performances on this specific quiz, order by score 
	public static ArrayList<Integer> getPerformanceByScore(int userID, int quizID, int num) throws Exception{
		String query = "SELECT id FROM quizTries WHERE userID = " + userID 
				+ " AND quizID = " + quizID + " ORDER BY score DESC LIMIT " + num;
		return executeQuery(query);
	}
	
	// Return x number of performances on this specific quiz, order by time spent (QUIZTRY) 
	public static ArrayList<Integer> getPerformancyByTime(int userID, int quizID, int num) throws Exception{
		String query = "SELECT id FROM quizTries WHERE userID = " + userID
				+ " AND quizID = " + quizID + " ORDER BY timeSpent DESC LIMIT " + num;
		return executeQuery(query);
	}
	
	// Return x number of top performers on this specific quiz (USER)
	public static ArrayList<Integer> getTopPerformers(int quizID, int num) throws Exception{
		String query = "SELECT DISTINCT userID FROM quizTries WHERE quizID = " + quizID 
				+ " ORDER BY score DESC LIMIT " + num;
		return executeQuery(query);
	}
	
	// Return x number of top performers within y number of days (USER)
	public static ArrayList<Integer> getTopPerformers(int quizID, int num, int numOfDays) throws Exception{
		String query = "SELECT DISTINCT userID FROM quizTries WHERE quizID = " + quizID
				+ " AND dateCreated >= NOW() - INTERVAL " + numOfDays + " DAY "
				+ " ORDER BY score DESC LIMIT " + num;
		return executeQuery(query);
	}
	
	// Return the performance of recent test takers (QUIZTRY)
	public static ArrayList<Integer> getRecentPerformance(int quizID, int num) throws Exception{
		String query = "SELECT id FROM quizTries WHERE quizID = " + quizID
				+ " ORDER BY score DESC LIMIT " + num;
		return executeQuery(query);
	}
	
	// Return a set of IDs from a ResultSet
	private static ArrayList<Integer> resultSetToArray(ResultSet rs) throws Exception{
		ArrayList<Integer> result = new ArrayList<Integer>();
		while(rs.next()){
			result.add(rs.getInt(1)); 
		}
		return result; 
	}
	
	// Execute the given query, throws exception=
	private static ArrayList<Integer> executeQuery(String query) throws Exception{
		Connection con = ServerConnection.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ps.executeQuery();
		return resultSetToArray(ps.getResultSet()); 
	}
	
	public void getStatistics(){
	// TODO Provide methods for getting summary statistics 
	}
}
