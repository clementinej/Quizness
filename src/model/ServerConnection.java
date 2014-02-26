package model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
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
	
	private Connection con; 
	
	public ServerConnection(){
		open();
	}
	
	//Create a new connection to the database
	public Connection open(){
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
	
	// Convert an quiz object into byte[]
	private byte[] convertToByteArray(Quiz quiz) throws Exception{
		 ByteArrayOutputStream bos = new ByteArrayOutputStream();
	     ObjectOutputStream oos = new ObjectOutputStream(bos);
	     oos.writeObject(quiz);
	     return bos.toByteArray();
	}
	
	// Convert an user object into byte[]
	private byte[] convertToByteArray(User user) throws Exception{
		 ByteArrayOutputStream bos = new ByteArrayOutputStream();
	     ObjectOutputStream oos = new ObjectOutputStream(bos);
	     oos.writeObject(user);
	     return bos.toByteArray();
	}
	
	// Convert byte[] into an object
	private Object convertToObject(ResultSet rs, String objectType) throws Exception{
		 InputStream s = rs.getBlob(objectType).getBinaryStream();
	     ObjectInputStream ois = new ObjectInputStream(s);
	   	 return ois.readObject();
	}
	
	// Add an user to the database
	public void addUser(int userID, User user) throws Exception {
		String query = "INSERT INTO users VALUES(?,?)";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, userID);
		ps.setBytes(2, convertToByteArray(user));
		ps.executeUpdate();
	}
	
	// Remove the user from the database
	public void removeUser(int userID) throws Exception{
		PreparedStatement ps = con.prepareStatement("DELETE FROM users WHERE userID = ?");
		ps.setInt(1, userID);
		ps.executeQuery();
	}
	
	// Return an user from the database given the userID
	public User getUser(int userID) throws Exception {
		PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE userID = ?");
		ps.setInt(1, userID);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return (User) convertToObject(rs, "user");
	}
	
	// Add a quiz to the database
	public void addQuiz(int quizID, Quiz quiz) throws Exception {
		String query = "INSERT INTO quizzes (quizID, quiz) VALUES(?, ?)";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, quizID); 
		ps.setBytes(2, convertToByteArray(quiz));
		ps.executeUpdate();
	}
	
	// Remove a quiz from the database
	public void removeQuiz(int quizID) throws Exception{
		PreparedStatement ps = con.prepareStatement("DELETE FROM quizzes WHERE quizID = ?");
		ps.setInt(1, quizID);
		ps.executeQuery();
	}
	
	// Return a quiz from the database given the quizID
	public Quiz getQuiz(int quizID) throws Exception {
		PreparedStatement ps = con.prepareStatement("SELECT * FROM quizzes WHERE quizID = ?");
		ps.setInt(1, quizID);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return (Quiz) convertToObject(rs, "quiz");
	}
	
	//Close the connection
	public void close() throws SQLException{
		if(!con.isClosed()){
			con.close();
		}
	}
	
}
