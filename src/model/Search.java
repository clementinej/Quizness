package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class Search {
	
	public Search(){
		
	}
	
	public static ArrayList<Quiz> searchQuizzes(boolean popular, String queryString) throws Exception{
		Connection con = ServerConnection.getConnection();
		ArrayList<Quiz> searchedQuizzes = new ArrayList<Quiz>();
	
		if (popular){
			PreparedStatement ps = con.prepareStatement("SELECT id FROM quizzes WHERE title LIKE ? ORDER BY numOfTimesPlayed");
			ps.setString(1, "\"%" + queryString + "\"%");
			ResultSet rs = ps.executeQuery();
			rs.beforeFirst();
			while(rs.next()){
				Quiz quiz = ServerConnection.getQuiz(rs.getInt(1));
				searchedQuizzes.add(quiz);
			}
		} else {
			PreparedStatement ps = con.prepareStatement("SELECT id FROM quizzes WHERE title LIKE ? ORDER BY dateLastPlayed");
			ps.setString(1, "\"%" + queryString + "\"%");
			ResultSet rs = ps.executeQuery();
			rs.beforeFirst();
			while(rs.next()){
				Quiz quiz = ServerConnection.getQuiz(rs.getInt(1));
				searchedQuizzes.add(quiz);
			}	
		}
		return searchedQuizzes;
	}
	
	public static ArrayList<User> searchUsers(String queryString) throws Exception{
		ServerConnection.open();

		Connection con = ServerConnection.getConnection();
		ArrayList<User> searchedUsers = new ArrayList<User>();
		PreparedStatement ps = con.prepareStatement("SELECT id FROM users WHERE username LIKE ?");
		ps.setString(1, "%" + queryString + "%");
		ResultSet rs = ps.executeQuery();
		rs.beforeFirst();
		while(rs.next()){
			int id = rs.getInt(1);
			User user = ServerConnection.getUser(id);
			searchedUsers.add(user);
		}
		return searchedUsers;
	}
	
	public static final int SEARCH_POPULAR = 1;
	public static final int SEARCH_RECENT = 2;

}
