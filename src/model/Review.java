package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Review {

	private int reviewID;
	private int ranking;
	private String title;
	private String subject;
	private int reviewerID;
	private int quizID;
	
	public Review(int quizID, int reviewerID, int ranking, String title, String subject){
		this.quizID = quizID;
		this.reviewerID = reviewerID;
		this.ranking = ranking;
		this.title = title;
		this.subject = subject;
	}
	
	public void setID(int id){
		reviewID = id;
	}
	
	public int getReviewID(){
		return reviewID;
	}
	public int getQuizID(){
		return quizID;
	}
	public int getUserID(){
		return reviewerID;
	}
	public int getRanking(){
		return ranking;
	}
	public String getTitle(){
		return title;
	}
	public String getSubject(){
		return subject;
	}
	public static double getAverageRank() throws SQLException{
		Connection con = ServerConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT ranking FROM reviews");
		ResultSet rs = ps.executeQuery();
		double total = 0;
		double count = 0;
		while (rs.next()){
			total += rs.getDouble(1);
			count ++;
		}
		return total / count;
	}
	public static ArrayList<Review> getReviews(int quizID) throws Exception{
		Connection con = ServerConnection.getConnection();
		ArrayList<Review> reviews = new ArrayList<Review>();
		PreparedStatement ps = con.prepareStatement("SELECT id FROM reviews WHERE quizID = ?");
		ps.setInt(1, quizID);
		ResultSet rs = ps.executeQuery();
		while (rs.next()){
			Review review = ServerConnection.getReview(rs.getInt(1));
			reviews.add(review);
		}
		return reviews;
	}
	
}
