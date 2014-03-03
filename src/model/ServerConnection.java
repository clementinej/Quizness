package model;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import com.mysql.jdbc.Statement;

public class ServerConnection {
	static String account = MyDBInfo.MYSQL_USERNAME;
	static String password = MyDBInfo.MYSQL_PASSWORD;
	static String server = MyDBInfo.MYSQL_DATABASE_SERVER;
	static String database = MyDBInfo.MYSQL_DATABASE_NAME;
	
	private static Connection con;
	
	//Create a new connection to the database
	public static Connection open(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://" + server + "/" +  database, account, password);
			return con;
		} catch (SQLException e){
			e.printStackTrace();
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
		}
		return null;	
	}
	
	// Return the Connection object
	public static Connection getConnection(){
		return con;
	}
	
	// Convert an quiz object into byte[]
	private static byte[] convertToByteArray(Object obj) throws Exception{
		 ByteArrayOutputStream bos = new ByteArrayOutputStream();
	     ObjectOutputStream oos = new ObjectOutputStream(bos);
	     oos.writeObject(obj);
	     return bos.toByteArray();
	}
	/*
	// Convert an user object into byte[]
	private static byte[] convertToByteArray(User user) throws Exception{
		 ByteArrayOutputStream bos = new ByteArrayOutputStream();
	     ObjectOutputStream oos = new ObjectOutputStream(bos);
	     oos.writeObject(user);
	     return bos.toByteArray();
	}
	
	// Convert an quizTry object into byte[]
	private static byte[] convertToByteArray(QuizTry quizTry) throws Exception{
		 ByteArrayOutputStream bos = new ByteArrayOutputStream();
	     ObjectOutputStream oos = new ObjectOutputStream(bos);
	     oos.writeObject(quizTry);
	     return bos.toByteArray();
	}
	*/
	
	// Convert byte[] into an object
	private static Object convertToObject(ResultSet rs, String objectType) throws Exception{
		 InputStream s = rs.getBlob(objectType).getBinaryStream();
	     ObjectInputStream ois = new ObjectInputStream(s);
	   	 return ois.readObject();
	}
	
	// Add an user to the database
	// Return the ID if success
	// Otherwise return -1;
	public static int addUser(User user) throws Exception {
		String query = "INSERT INTO users (username, password, user, email) VALUES(?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, user.getUserName());
		ps.setString(2, user.getPassword());
		System.out.println(user.getPassword());
		ps.setBytes(3, convertToByteArray((Object)user));
		ps.setString(4,  user.getEmail());
		System.out.println(query);
		ps.executeUpdate();
		return getGeneratedKey(ps);
	}
	
	/* Move to the User object
	 Remove the user from the database
	public static void removeUser(int userID) throws Exception{
		PreparedStatement ps = con.prepareStatement("DELETE FROM users WHERE id = ?");
		ps.setInt(1, userID);
		ps.executeQuery();
	}
	*/
	
	// Return an User object from the database given the username
	public static User getUser(String userName) throws Exception {
		PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE username = ?", Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, userName);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) return (User) convertToObject(rs, "user");
		return null; 
	}
	
	// Add a quiz to the database
	public static int addQuiz(Quiz quiz) throws Exception {
		String query = "INSERT INTO quizzes (quiz, numTimesTaken, dateCreated) VALUES(?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS); 
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		ps.setBytes(1, convertToByteArray((Object)quiz));
		ps.setInt(2, quiz.getNumOfTimesPlayed());
		ps.setTimestamp(3, timestamp);
		ps.executeUpdate();
		
		return getGeneratedKey(ps);
	}
	
	// Return a quiz from the database given the quizID
	public static Quiz getQuiz(int quizID) throws Exception {
		PreparedStatement ps = con.prepareStatement("SELECT * FROM quizzes WHERE id = ?", Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, quizID);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return (Quiz) convertToObject(rs, "quiz");
	}
	
	// Add a quizTry to the database and return the auto generated key
	public static int addQuizTry(QuizTry quizTry) throws Exception {
		String query = "INSERT INTO quizTries (quizTry, userID, quizID, score, timeSpent, dateCreated) VALUES(?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		Date date = new Date();
		ps.setBytes(1,  convertToByteArray((Object)quizTry));
		ps.setInt(2, quizTry.getUserID());
		ps.setInt(3, quizTry.getQuizID());
		ps.setDouble(4, quizTry.getScore());
		ps.setDouble(5, quizTry.getTime());
		ps.setTimestamp(6, new Timestamp(date.getTime()));
		ps.executeUpdate();
		
		return getGeneratedKey(ps);
	}
	
	// Add a message to the database and return the auto generated key 
	public static int addMessage(Message message) throws Exception {
		String query = "INSERT INTO messages (fromID, toID, message) VALUE (?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, message.fromID);
		ps.setInt(2, message.toID);
		ps.setBytes(3,  convertToByteArray((Object)message));
		ps.executeUpdate();
		
		return getGeneratedKey(ps); 
	}
	
	// Return a message from the database given the messageID
	public static Message getMessage(int messageID) throws Exception {
		PreparedStatement ps = con.prepareStatement("SELECT * FROM messages WHERE id = ?", Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, messageID);
		ResultSet rs = ps.executeQuery();
		rs.next(); 
		return (Message) convertToObject(rs, "message");
	}
	
	// Add a Inbox  to the database and return the auto generated key 
		public static int addInbox(Inbox inbox) throws Exception {
			String query = "INSERT INTO inboxes (numRequests, numMessages, numChallenges, inbox) VALUE (?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, 0);
			ps.setInt(2, 0);
			ps.setInt(3, 0);
			ps.setBytes(4,  convertToByteArray((Object)inbox));
			ps.executeUpdate();
			return getGeneratedKey(ps); 
		}
		
	// Return a message from the database given the messageID
	public static Inbox getInboxWithInboxID(int inboxID) throws Exception {
		PreparedStatement ps = con.prepareStatement("SELECT * FROM inboxes WHERE id = ?", Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, inboxID);
		ResultSet rs = ps.executeQuery();
		rs.next(); 
		return (Inbox) convertToObject(rs, "inbox");
	}
	
	// Return a message from the database given the messageID
	public static Inbox getInboxWithUserID(int userID) throws Exception {
		PreparedStatement ps = con.prepareStatement("SELECT * FROM inboxes WHERE userID = ?", Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, userID);
		ResultSet rs = ps.executeQuery();
		rs.next(); 
		return (Inbox) convertToObject(rs, "inbox");
	}
	
	// Return a quizTry from the database given the quizID
	public static QuizTry getQuizTry(int quizTryID) throws Exception {
		PreparedStatement ps = con.prepareStatement("SELECT * FROM quizTries WHERE quizTryID = ?", Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, quizTryID);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return (QuizTry) convertToObject(rs, "quizTry");
	}
	
	//Close the connection
	public static void close() throws SQLException{
		if(!con.isClosed()){
			con.close();
		}
	}
	
	// Return the auto generated key
	private static int getGeneratedKey(PreparedStatement ps) throws Exception{
		ResultSet keySet  = ps.getGeneratedKeys();		
		if(keySet.next()) {
			return keySet.getInt(1); 
		} else return -1; 
	}
}
