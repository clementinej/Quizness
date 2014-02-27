package model;

import java.io.Serializable;
import java.util.ArrayList;
<<<<<<< HEAD
import java.sql.*;
=======
>>>>>>> 207d09290cd4742d8fec6d08be7ea77026d222ae

public class User implements Serializable {

	private int userID;
	private boolean isAdmin;
	private ArrayList<Quiz> quizzesMade;
	private ArrayList<QuizTry> quizzesTried;
	private ArrayList<User> friendsList;
	private ArrayList<Achievements> achievements;
	
	public User(int userID, boolean isAdmin){
		this.userID = userID;
		this.isAdmin = isAdmin;
		this.quizzesMade = new ArrayList<Quiz>();
		this.quizzesTried = new ArrayList<QuizTry>();
		this.friendsList = new ArrayList<User>();
<<<<<<< HEAD
		this.achievements = new ArrayList<Achievement>();
		this.achievementKeys = new ArrayList<Integer>();
	}
	
	public boolean nameIsAvailable(String userName) throws SQLException{
		Connection con = ServerConnection.getConnection();
		PreparedStatement ps;
		ps = con.prepareStatement("SELECT * FROM users WHERE userName = ?");
		ps.setString(1, userName);
		ResultSet rs = ps.executeQuery();
		rs.beforeFirst();
		if (!rs.next()){
			return true;
		}
		return false;
	}
	
	public String getPasswordHash(String userName) throws SQLException{
		Connection con = ServerConnection.getConnection();
		PreparedStatement ps;
		String passwordHash = null;	
		ps = con.prepareStatement("SELECT password FROM users WHERE userName = ?");	
		ps.setString(1, userName);
		ResultSet rs = ps.executeQuery();
		//rs.beforeFirst();
		rs.first();
		passwordHash = rs.getString("password");
		return passwordHash;
	}
	
	public String getUserName(){
		return userName;
	}
	
	public String getPassword(){
		return pw;
	}
	
	public void addAchievement(Achievement achievement){
		int key = achievement.getKey();
		if (!achievementKeys.contains(key)){
			achievementKeys.add(key);
			achievements.add(achievement);
		}
	}
	
	public void setUserID(int userID){
		this.userID = userID;
	}
	
	public User getUser(int userID) throws Exception{
		return ServerConnection.getUser(userID);
=======
>>>>>>> 207d09290cd4742d8fec6d08be7ea77026d222ae
	}
	
	public void addTry(QuizTry quizTry){
		int index = quizzesTried.indexOf(quizTry);
		if (index == -1){
			quizzesTried.add(quizTry);
		} else {
			quizzesTried.set(index, quizTry);
		}
	}
	
	public void addFriend(User friend){
		friendsList.add(friend);
	}
	
	public boolean isAdmin(){
		return isAdmin;
	}
	
	public int getUserID(){
		return userID;
	}
	
	public void makeQuiz(Quiz quiz){
		quizzesMade.add(quiz);
	}
	
	public void deleteQuiz(Quiz quiz){
		quizzesMade.remove(quiz);
	}
}
