package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Delete {

	public Delete(){
		
	}
	
	public static void deleteQuiz(int quizID) throws Exception{
		Quiz quiz = ServerConnection.getQuiz(quizID);
		
//		ServerConnection.open();
		Connection con = ServerConnection.getConnection();
		
		clearQuizTryHistory(quiz);
		
		PreparedStatement ps = con.prepareStatement("DELETE FROM quizzes WHERE id = ?");
		ps.setInt(1, quizID);
		ps.executeUpdate();
	}
	
	public static void deleteUser(int userID) throws Exception{
		User user = ServerConnection.getUser(userID);
		user.setBanned();
		if (user.isBanned())
			System.out.println( user.getUserName() + " is banned");
		ServerConnection.updateUser(user);
	}
	
	public static void clearQuizTryHistory(Quiz quiz) throws Exception{
//		ServerConnection.open();
		Connection con = ServerConnection.getConnection();
		PreparedStatement ps;
		int quizID = quiz.getQuizID();
		
		ps = con.prepareStatement("SELECT id FROM quizTries WHERE quizID = ?");
		ps.setInt(1, quizID);
		ResultSet rs = ps.executeQuery();
		rs.beforeFirst();
		while (rs.next()){
			QuizTry quizTry = ServerConnection.getQuizTry(rs.getInt(1));
			int userID = quizTry.getUserID();
			User user = ServerConnection.getUser(userID);
			user.deleteTry(quizTry);
			ServerConnection.updateUser(user);
		}
		ps = con.prepareStatement("DELETE FROM quizTries WHERE quizID = ?");
		ps.setInt(1, quizID);
		ps.executeUpdate();
		
	}
}
