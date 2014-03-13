package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class QuizSummary {
	
	// Return x number of performances on this specific quiz, order by date (QUIZTRY)
	public static ArrayList<Integer> getPerformanceByDate(int userID, int quizID, int num) throws Exception{
		String query = "SELECT id FROM quizTries WHERE userID = " + userID
				+ " AND quizID = " + quizID + " ORDER BY dateCreated DESC LIMIT " + num;
		return executeQuery(query);
	}
	
	// Return x number of performances on this specific quiz, order by score (QUIZTRY)
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
	
	// Return x number of top performers on this specific quiz (QUIZTRY)
	public static ArrayList<Integer> getAllPerformance(int quizID, int num) throws Exception{
		String query = "SELECT id FROM quizTries WHERE quizID = " + quizID 
				+ " ORDER BY score DESC LIMIT " + num;
		return executeQuery(query);
	}
	
	// Return x number of top performers within y number of days (QUIZTRY)
	public static ArrayList<Integer> getAllPerformanceWithDays(int quizID, int num, int numOfDays) throws Exception{
		String query = "SELECT id FROM quizTries WHERE quizID = " + quizID
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
	
	public static double getAllAverageScore(int quizID) throws Exception {
		String query = "SELECT AVG(score)  FROM quizTries WHERE quizID = " + quizID;
		PreparedStatement ps = ServerConnection.getConnection().prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) return rs.getDouble(1);
		else return -1.0; 
	}
	
	public static double getAllAverageTimeSpent(int quizID) throws Exception {
		String query = "SELECT AVG(timeSpent)  FROM quizTries WHERE quizID = " + quizID;
		PreparedStatement ps = ServerConnection.getConnection().prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) return rs.getDouble(1)/1000;
		else return -1.0; 
	}	
	
	public static int getAllNumOfTimesTakenToday(int quizID) throws Exception {
		String query = "SELECT COUNT(*) FROM quizTries WHERE quizID = " + quizID
		+ " AND dateCreated >= NOW() - INTERVAL " + 1 + " DAY ";
		PreparedStatement ps = ServerConnection.getConnection().prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) return rs.getInt(1);
		else return -1; 
	}
	
	public static int getAllNumOfTimesTaken(int quizID) throws Exception {
		String query = "SELECT COUNT(*) FROM quizTries WHERE quizID = " + quizID;
		PreparedStatement ps = ServerConnection.getConnection().prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) return rs.getInt(1);
		else return -1; 
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
