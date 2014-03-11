package tests;

import java.sql.Connection;
import java.sql.ResultSet;

import model.Inbox;
import model.Quiz;
import model.ServerConnection;
import model.User;

public class Debugger {
	
	
	private void addUsers(){
		
	}
	
	private void addMessages(){
		
	}
	
	//RUN ONLY IN DEBUGGING MODE

	public static void main(String[] args) throws Exception {
		ServerConnection.open(); 
		
		//User user27 = User.getUser(27); 
		//User user31 = User.getUser(31); 
		//Inbox inbox27 = Inbox.getInbox(27);
		Inbox inbox = Inbox.getInbox(11); 

		inbox.reset();
		//inbox31.reset();
		//user27.resetFriends();
		//user31.resetFriends();
		
		
		
		//Connection con = ServerConnection.getConnection();
		
		//String query = "SELECT id FROM quizzes"; 
		//ResultSet rs = con.prepareStatement(query).getResultSet(); 
		
		//while(rs.next()){
			//Quiz quiz = Quiz.getQuiz(rs.getInt(1));
			//if()
		//}
		//ServerConnection.close(); 
	}

}
