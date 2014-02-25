package model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
			con = DriverManager.getConnection("jdbc:mysql://" + server, account, password);
			return con;
		} catch (SQLException e){
			e.printStackTrace();
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
		}
		return null;	
	}
	
	// Convert an quiz object into byte[]
	private static byte[] convertToByteArray(Quiz quiz) throws IOException{
		 ByteArrayOutputStream bos = new ByteArrayOutputStream();
	     ObjectOutputStream oos = new ObjectOutputStream(bos);
	     oos.writeObject(quiz);
	     return bos.toByteArray();
	}
	
	// Convert an user object into byte[]
	private static byte[] convertToByteArray(User user) throws IOException{
		 ByteArrayOutputStream bos = new ByteArrayOutputStream();
	     ObjectOutputStream oos = new ObjectOutputStream(bos);
	     oos.writeObject(user);
	     return bos.toByteArray();
	}
	
	// Convert byte[] into a quiz object
	private static Quiz convertToQuiz(ResultSet rs) 
			throws IOException, SQLException, ClassNotFoundException{
		 ByteArrayInputStream bis = new ByteArrayInputStream(rs.getBytes("quiz"));
	     ObjectInputStream ois = new ObjectInputStream(bis);
	     return (Quiz) ois.readObject();
	}
	
	// Convert byte[] into a user object
	private static User convertToUser(ResultSet rs) 
			throws IOException, SQLException, ClassNotFoundException{
		 ByteArrayInputStream bis = new ByteArrayInputStream(rs.getBytes("user"));
	     ObjectInputStream ois = new ObjectInputStream(bis);
	     return (User) ois.readObject();
	}
	
	// Add an user to the database
	public static void addUser(int userID, User user) 
			throws SQLException, NumberFormatException, IOException {
		String query = "INSERT INTO users VALUES(?,?)";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setObject(Integer.parseInt(userID), convertToByteArray(user));
		ps.executeUpdate();
	}
	
	// Return an user from the database given the userID
	public static User getUser(int userID) throws SQLException, ClassNotFoundException, IOException{
		String query = "SELECT * FROM users WHERE userID = " + userID +"\"";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		return convertToUser(rs);
		
	}
	
	// Remove the user from the database
	public static void removeUser(int userID){
		
	}
	
	// Add a quiz to the database
	public static void addQuiz(int quizID, Quiz quiz){
		
	}
	
	// Remove a quiz from the database
	public static void removeQuiz(int quizID){
		
	}
	
	// Return a quiz from the database given the quizID
	public static Quiz getQuiz(int quizID) throws SQLException, ClassNotFoundException, IOException{
		String query = "SELECT * FROM quizzes WHERE quizID = " + quizID +"\"";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		return convertToQuiz(rs);
	}
	
	//Close the connection
	public static void close() throws SQLException{
		if(!con.isClosed()){
			con.close();
		}
	}
	
}
