<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@ page import="model.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
   <head>
      <meta charset="utf-8" />
      <title>Profile Page</title>
   </head>
   <body>
	<%
	// Get the current user
	User u = (User) session.getAttribute("current user");
	Inbox inbox = Inbox.getInbox(u.getUserID());
	
	// Get the number of messages by type
	int numRequests = inbox.getNumFriendReqs();
	int numMessages = inbox.getNumNewMessages();
	int numChallenges = inbox.getNumChallenges();
	
	// Get the messages
	ArrayList<FriendRequest> requests = inbox.getRequests();
	ArrayList<Note> notes = inbox.getNotes();
	ArrayList<Challenge> challenges = inbox.getChallenges();
	FriendRequest currRequest;
	Note currNote;
	Challenge currChallenge; 
	%>

	<table border="1" style="width:300px">
	<% 
		for (int i = 0; i < numRequests; i++){
			currRequest = requests.get(i);
			%>
			<tr>
			  <td><%= currRequest.getSubject() %></td>		
			  <td><%= currRequest.getBody() %></td>
			  <td><%= currRequest.unread %></td>
			</tr>
			<%
		}
	%>
	</table>
   </body>
</html>
   


