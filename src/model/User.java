package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.sql.*;

public class User implements Serializable {
	
	public static final String MYSQL_USERNAME = "ccs108wang8";
	public static final String MYSQL_PASSWORD = "vohpaifa";
	public static final String MYSQL_DATABASE_SERVER = "mysql-user.stanford.edu";
	public static final String MYSQL_DATABASE_NAME = "c_cs108_wang8";

	private int userID;
	private boolean isAdmin;
	private String userName;
	private String pw;
	private String email;
	private int numQuizzesTaken;
	private ArrayList<Quiz> quizzesMade;
	private ArrayList<QuizTry> quizzesTried;
	private ArrayList<User> friendsList;
	private ArrayList<Achievement> achievements;
	private ArrayList<Integer> achievementKeys;
	
	//constructor
	public User(boolean isAdmin, String userName, String pw, String email){
		this.isAdmin = isAdmin;
		this.pw = pw;
		this.userName = userName;
		this.email = email;
		this.quizzesMade = new ArrayList<Quiz>();
		this.quizzesTried = new ArrayList<QuizTry>();
		this.friendsList = new ArrayList<User>();
		this.achievementKeys = new ArrayList<Integer>();
		numQuizzesTaken = 0;
	}
	

	//genes comment
	public static boolean nameIsAvailable(String userName) throws SQLException{
		ServerConnection.open();
		Connection con = ServerConnection.getConnection();
		PreparedStatement ps;
		System.out.println("Reading from DB in Users");
		Statement stmt = con.createStatement();
		stmt.executeQuery("USE " + MYSQL_DATABASE_NAME);
		ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE username = \""+ userName + "\"");
//
//		ps = con.prepareStatement("SELECT * FROM users WHERE username = \""+ userName + "\"");
//		ps.setString(1, userName);
//		ResultSet rs = 
		rs.beforeFirst();
		if (!rs.next()){
			return true;
		}
		return false;
	}
	
	public ArrayList<User> getFriends() {
		return friendsList;
	}
	
	public static String getPasswordHash(String userName) throws SQLException{
		Connection con = ServerConnection.getConnection();
		PreparedStatement ps;
		String passwordHash = null;	
		ps = con.prepareStatement("SELECT password FROM users WHERE username = ?");	
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
	
	public String getEmail(){
		return email;
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
	
	public static User getUser(int userID) throws Exception{
		return ServerConnection.getUser(userID);
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

	public int numQuizzesTaken() {
		return numQuizzesTaken;
	}
}
