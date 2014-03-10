package tests;

import model.Inbox;
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
		User user27 = User.getUser(27); 
		User user31 = User.getUser(31); 
		Inbox inbox27 = Inbox.getInbox(27);
		Inbox inbox31 = Inbox.getInbox(31); 

		inbox27.reset();
		inbox31.reset();
		user27.resetFriends();
		user31.resetFriends();
		ServerConnection.close(); 
	}

}
