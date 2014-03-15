package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.sql.*;

public class User implements Serializable {
	
	/**
	 * nicolez@stanford.edu
	 */
	private static final long serialVersionUID = -7722606251820079129L;
	public static final String MYSQL_USERNAME = "ccs108wang8";
	public static final String MYSQL_PASSWORD = "vohpaifa";
	public static final String MYSQL_DATABASE_SERVER = "mysql-user.stanford.edu";
	public static final String MYSQL_DATABASE_NAME = "c_cs108_wang8";

	private boolean isAdmin;
	private boolean isBanned;
	private int userID;
	private String userName;
	private String pw;
	private String email;
	private int numQuizzesTaken;
	private ArrayList<Quiz> quizzesMade;
	private ArrayList<QuizTry> quizzesTried;
	private ArrayList<User> friendsList;
	private ArrayList<Achievement> achievements;
	private ArrayList<Integer> achievementKeys;
	private ArrayList<FriendRequest> friendRequests;
	private String aboutMe;
	private String location;
	private int highScore;
	private String picture;
	private boolean visToFriends;
	private boolean visToAll;

	//constructor
	public User(boolean isAdmin, String userName, String pw, String email, String aboutMe, String location, String picture){
		this.isAdmin = isAdmin;
		this.isBanned = false;
		this.pw = pw;
		this.userName = userName;
		this.userID = -1;
		this.email = email;
		this.quizzesMade = new ArrayList<Quiz>();
		this.quizzesTried = new ArrayList<QuizTry>();
		this.friendsList = new ArrayList<User>();
		this.achievementKeys = new ArrayList<Integer>();
		this.achievements = new ArrayList<Achievement>();
		this.friendRequests = new ArrayList<FriendRequest>();
		numQuizzesTaken = 0;
		this.aboutMe = aboutMe;
		this.location = location;
		this.highScore = -1;
		this.picture = picture;
		visToFriends = true;
		visToAll = true;
	}

	
	
/*
 * Achievements section. Adds the achievement if it doesn't already exist
 */
	public void addAchievement(Achievement achievement) throws Exception{
		int key = achievement.getKey();
		if (!achievementKeys.contains(key)){
			achievementKeys.add(key);
			achievements.add(achievement);
		}
		ServerConnection.updateUser(this);
	}
	
	public ArrayList<Achievement> getAchievements(){
		return achievements;
	}
	
	
/*
 * QuizTry Section
 */
	//adds the current QuizTry to the user's history. if the QuizTry already existed and was
	//saved but unfinished, it just replaces it with the new one
	public void addTry(QuizTry quizTry){
		int index = quizzesTried.indexOf(quizTry);
		if (index == -1){
			quizzesTried.add(quizTry);
		} else {
			quizzesTried.set(index, quizTry);
		}
	}
	
	public void deleteTry(QuizTry quizTry){
		if (quizzesTried.contains(quizTry)){
			quizzesTried.remove(quizTry);
		}
	}
	public void makeQuiz(Quiz quiz) throws Exception{
		quizzesMade.add(quiz);
	}
	
	public void deleteQuiz(Quiz quiz){
		quizzesMade.remove(quiz);
	}

	public int numQuizzesTaken() {
		return numQuizzesTaken;
	}
	
	
	
	
/*Everything friend related
 * getFriends
 * getNumFriends
 * addFriend
 * deleteFriend
 * getFriendRequests
 * hasPendingFriendRequests
 * 
 * STATIC: createFriendRequest
 */
	public ArrayList<User> getFriends() throws Exception {
		String query = "SELECT toID FROM friendships WHERE fromID = " + this.userID; 
		Connection con = ServerConnection.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		ArrayList<User> users = new ArrayList<User>(); 
		while(rs.next()){
			users.add(User.getUser(rs.getInt(1)));
		}
		return users;
	}
	
	public int getNumFriends() throws Exception{
		String query = "SELECT COUNT(*) fROM friendships WHERE fromID = " + this.userID; 
		Connection con = ServerConnection.getConnection();
		ResultSet rs = con.prepareStatement(query).executeQuery(); 
		while(rs.next()){
			return rs.getInt(1); 
		}
		return -1; 
	}
	
	// Depreciated
	// Update the user model in the database when a new friend is added
	public void addFriend(User friend) throws Exception{
		friendsList.add(friend);
		ServerConnection.updateUser(this);
	}
	
	public void deleteFriend(User friend){
		friendsList.remove(friend);
	}

	
	public void deleteFriendRequest(FriendRequest request){
		friendRequests.remove(request);
	}
	
	public void addFriendRequest(FriendRequest request){
		if (!friendRequests.contains(request)){
			friendRequests.add(request);
		}
	}
	
	public ArrayList<FriendRequest> getFriendRequests(){
		return friendRequests;
	}
	
	public boolean hasPendingFriendRequests(){
		if (friendRequests.size() > 0)
			return true;
		return false;
	}
	
	

	
	
/*Random functions that return the stored variable of User 
 * 
 */
	public void setVisibilityToAll(boolean visibility){
		visToAll = visibility;
	}
	public void setVisibilityToFriends(boolean visibility){
		visToAll = visibility;
	}
	
	public void setPicture(String file){
		picture = file;
	}
	
	public String getPicture(){
		return picture;
	}
	public static User getUser(int userID) throws Exception{

		//ServerConnection.open();

		return ServerConnection.getUser(userID);
	}

	public boolean isAdmin(){
		return isAdmin;
	}
	
	public boolean isBanned(){
		return this.isBanned;
	}
	
	public void setBanned(){
		isBanned = true;
	}
	
	public void unBan(){
		isBanned = false;
	}
	
	public void setAdmin(){
		isAdmin = true;
	}
	
	public void incrementQuizzesTaken() throws Exception{
		numQuizzesTaken++;
		ServerConnection.updateUser(this);
		System.out.println("Number of quizzes taken: " + numQuizzesTaken);
	}
	
	//returns a string of the high score. if there is no score stored, it returns a string
	//saying that the user has not taken any quizzes
	public String getHighScore(){
		if (highScore == -1)
			return "This user hasn't taken any quizzes yet!";
		String score = "" + highScore;
		return score;
	}
	
	public void checkHighScore(double score) throws Exception{
		if (score > highScore){
			highScore = (int) score;
			System.out.println("the new high score is " + highScore);
			ServerConnection.updateUser(this);
		}
	}
	
	public String getAboutMe(){
		return aboutMe;
	}
		
	public String getLocation(){
		return location;
	}
	
	//checks the database to see if an entry with the same username already exists
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	
	public static boolean nameIsAvailable(String userName) throws SQLException{
		Connection con = ServerConnection.getConnection();
		Statement stmt = con.createStatement();
		stmt.executeQuery("USE " + MYSQL_DATABASE_NAME);
		ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE username = \""+ userName + "\"");
		rs.beforeFirst();
		//ServerConnection.close();
		if (!rs.next()){
			return true;
		}
		return false;
	}
	
	//opens the database to grab the password hash from email
	public static boolean emailIsAvailable(String email) throws SQLException{
		Connection con = ServerConnection.getConnection();
		Statement stmt = con.createStatement();
		stmt.executeQuery("USE " + MYSQL_DATABASE_NAME);
		ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE email = \""+ email + "\"");
		rs.beforeFirst();
		//ServerConnection.close();
		if (!rs.next()){
			return true;
		}
		return false;
	}

	//opens the database to grab the password hash from username
	public static String getPasswordHashFromUserName(String userName) throws SQLException{
		Connection con = ServerConnection.getConnection();
		PreparedStatement ps;
		String passwordHash = null;	
		ps = con.prepareStatement("SELECT password FROM users WHERE username = ?");	
		ps.setString(1, userName);
		ResultSet rs = ps.executeQuery();
		if(rs.first()) passwordHash = rs.getString("password");
		return passwordHash;
	}
	
	public static String getPasswordHashFromEmail(String email) throws SQLException{
		Connection con = ServerConnection.getConnection();
		PreparedStatement ps;
		String passwordHash = null;	
		ps = con.prepareStatement("SELECT password FROM users WHERE email = ?");	
		ps.setString(1, email);
		ResultSet rs = ps.executeQuery();
		if(rs.first())passwordHash = rs.getString("password");
		return passwordHash;
	}
	
	//
	//public int getUserID() throws SQLException{
	//	return User.getUserID(email);
	//}
	
	public int getUserID(){
		return this.userID;
	}
	
	public void setUserID(int userID){
		this.userID = userID;
	}
	
	//static method.  you must have this otherwise how will you get an id from db?
	public static int getUserID(String email) throws SQLException{
		Connection con = ServerConnection.getConnection();
		PreparedStatement ps;
		int id = -1;	
		ps = con.prepareStatement("SELECT id FROM users WHERE email = ?");
		System.out.println("SELECT id FROM users WHERE email = " + email);
		ps.setString(1, email);
		ResultSet rs = ps.executeQuery();
		rs.first();
		id = rs.getInt("id");
		System.out.println(id);
		return id;
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

	public void resetFriends() throws Exception{
		this.friendsList.clear();
		ServerConnection.updateUser(this);
	}
	
	public boolean isFriendsWith(int userID) throws Exception {
		String query = "SELECT toID FROM friendships WHERE fromID = " + this.userID; 
		Connection con = ServerConnection.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			int toID = rs.getInt(1);
			if(toID == userID) return true;
		}
		return false;
	}
	
	public double getAverageScore() throws Exception{
		String query = "SELECT AVG(score)  FROM quizTries WHERE userID = " + this.userID;
		System.out.println("getUserAverageScore: " + query);
		PreparedStatement ps = ServerConnection.getConnection().prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) return rs.getDouble(1);
		else return -1.0; 
	}
	
	public void removeFriend(int userID) throws Exception {
		String queryA = "DELETE FROM friendships WHERE fromID = " + this.userID
				+ " AND toID = " + userID; 
		String queryB = "DELETE FROM friendships WHERE fromID = " + userID
				+ " AND toID = " + this.userID; 
		Connection con = ServerConnection.getConnection();
		PreparedStatement ps = con.prepareStatement(queryA);
		ps.executeUpdate();
		ps.executeUpdate(queryB);
		ps.executeUpdate();
	}
}
