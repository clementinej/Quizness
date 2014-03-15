package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Review {

	public Review(){
		
	}
	
	public static ArrayList<Integer> getReviews(int quizID) throws SQLException{
		ArrayList<Integer> reviewIDs = new ArrayList<Integer>();
		Connection con = ServerConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT id FROM reviews WHERE quizID = ? ORDER BY dateCreated DESC");
		ps.setInt(1, quizID);
		ResultSet rs = ps.executeQuery();
		while (rs.next()){
			reviewIDs.add(rs.getInt(1));
		}
		return reviewIDs;
	}
	
	public static String getReview(int reviewID) throws SQLException{
		Connection con = ServerConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT review FROM reviews WHERE id = ?");
		ps.setInt(1, reviewID);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) return rs.getString(1);
		return null;
	}
	public static Integer getRanking(int reviewID) throws SQLException{
		Connection con = ServerConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT ranking FROM reviews WHERE id = ?");
		ps.setInt(1, reviewID);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) return rs.getInt(1);
		return null;
	}
	public static String getReviewerName(int reviewID) throws Exception{
		Connection con = ServerConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT reviewerID FROM reviews WHERE id = ?");
		ps.setInt(1, reviewID);
		ResultSet rs = ps.executeQuery();
		if (rs.next()){
			int userID = rs.getInt(1);
			User user = ServerConnection.getUser(userID);
			return user.getUserName();
		}
		return null;
	}
	public static int getAverageRanking(int quizID) throws SQLException{
		Connection con = ServerConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT ranking FROM reviews WHERE quizID = ? ");
		ps.setInt(1, quizID);
		ResultSet rs = ps.executeQuery();
		double total = 0;
		double count = 0;
		while (rs.next()){
			total += rs.getInt(1);
			count++;
		}
		if (count == 0)
			return 0;
		return (int) (total/count);
	}
	
}
