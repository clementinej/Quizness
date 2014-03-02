package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.sql.*;

public class User implements Serializable {
	
	public static final String MYSQL_USERNAME = "ccs108wang8";
	public static final String MYSQL_PASSWORD = "vohpaifa";
	public static final String MYSQL_DATABASE_SERVER = "mysql-user.stanford.edu";
	public static final String MYSQL_DATABASE_NAME = "c_cs108_wang8";

	private boolean isAdmin;
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
	

	//constructor
	public User(boolean isAdmin, String userName, String pw, String email, String aboutMe, String location){
		this.isAdmin = isAdmin;
		this.pw = pw;
		this.userName = userName;
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
	}

	
	
/*
 * Achievements section. Adds the achievement if it doesn't already exist
 */
	public void addAchievement(Achievement achievement){
		int key = achievement.getKey();
		if (!achievementKeys.contains(key)){
			achievementKeys.add(key);
			achievements.add(achievement);
		}
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
	
	public void makeQuiz(Quiz quiz){
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
	public ArrayList<User> getFriends() {
		return friendsList;
	}
	
	public int getNumFriends(){
		return friendsList.size();
	}
	
	public void addFriend(User friend){
		friendsList.add(friend);
	}
	
	public void deleteFriend(User friend){
		friendsList.remove(friend);
	}
	
	//adds the same friend request to both users
	public static void createFriendRequest(String fromUser, String toUser, String subject, String body) throws Exception{
		FriendRequest request = new FriendRequest(fromUser, toUser, subject, body);
		User userA = ServerConnection.getUser(fromUser);
		User userB = ServerConnection.getUser(toUser);
		userA.addFriendRequest(request);
		userB.addFriendRequest(request);	
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
	
	public static User getUser(String username) throws Exception{
		return ServerConnection.getUser(username);
	}

	public boolean isAdmin(){
		return isAdmin;
	}
	
	public void setUserID(int userID){
		this.userID = userID;
	}
	
	//returns a string of the high score. if there is no score stored, it returns a string
	//saying that the user has not taken any quizzes
	public String getHighScore(){
		if (highScore == -1)
			return "This user hasn't taken any quizzes yet!";
		String score = "" + highScore;
		return score;
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
		ServerConnection.open();
		Connection con = ServerConnection.getConnection();
		System.out.println("Reading from DB in Users");
		Statement stmt = con.createStatement();
		stmt.executeQuery("USE " + MYSQL_DATABASE_NAME);
		ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE username = \""+ userName + "\"");
		rs.beforeFirst();
		if (!rs.next()){
			return true;
		}
		return false;
	}

	
	//opens the database to grab the password hash
	public static String getPasswordHash(String userName) throws SQLException{
		Connection con = ServerConnection.getConnection();
		PreparedStatement ps;
		String passwordHash = null;	
		ps = con.prepareStatement("SELECT password FROM users WHERE username = ?");	
		ps.setString(1, userName);
		ResultSet rs = ps.executeQuery();
		rs.first();
		passwordHash = rs.getString("password");
		return passwordHash;
	}
	
	
	public static int getUserID(String userName) throws SQLException{
		Connection con = ServerConnection.getConnection();
		PreparedStatement ps;
		int id = -1;	
		ps = con.prepareStatement("SELECT id FROM users WHERE username = ?");	
		ps.setString(1, userName);
		ResultSet rs = ps.executeQuery();
		rs.first();
		id = rs.getInt("id");
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
	

}
