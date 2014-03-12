<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@ page import="model.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%-- <%@ page errorPage="../site/404.jsp" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
   <head>
     	<title>Inbox</title>
      <meta charset="UTF-8" />
      <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="stylesheet" type="text/css" href="css/style.css" />
      <link rel="stylesheet" type="text/css" href="css/inbox.css" />
      <script src="js/modernizr-2.6.2.min.js"></script>
      <style>
         .header-link {
	padding-left:50px;
	font-weight:bold;	
	}
      </style>
   </head>
      <body>
            <!--top bar -->
      <div class="top">
      	<span class="header-link"><a href="home.jsp">Home</a></span>
         <span class="header-link"><a href="create-quiz.jsp">Create Quiz</a></span>
         <span class="header-link"><a href="social/compose-mail.jsp">Compose </a></span>
         <span class="header-link"><a href="profile.jsp">Profile</a></span>
         <span class="header-link"><a href="site/admin.jsp">Admin</a></span>
         <span class="header-link"><a href="inbox.jsp">Inbox</a></span>
          <span class="header-link"><a href="site/search.jsp">Search</a></span>
      </div>
      
      <%
		// Get the current user
		User u = (User) session.getAttribute("current user");
		Inbox inbox = Inbox.getInbox(u.getUserID());
		
		// Get the number of messages by type
		int numRequests = inbox.getNumFriendReqs();
		int numNotes = inbox.getNumNotes();
		int numChallenges = inbox.getNumChallenges();
		
		// Get the messages
		ArrayList<Integer> requests = inbox.getRequests(); 
		ArrayList<Integer> notes = inbox.getNotes();
		ArrayList<Integer> challenges = inbox.getChallenges();
		FriendRequest currRequest;
		Note currNote;
		Challenge currChallenge; 
		
		// Get the type of the message to display
		String displayType = request.getParameter("displayType"); 
		boolean dispNotes = true; 
		boolean dispChallenges = true; 
		boolean dispRequests = true; 
		if(displayType != null){
			if(!displayType.equals("note")) dispNotes = false; 
			if(!displayType.equals("challenge")) dispChallenges = false;
			if(!displayType.equals("request")) dispRequests = false; 
		}
	  %> 
	
      <div class="container">
         <section class="color-1">
            <nav class="cl-effect-14">
             	<a href="inbox.jsp">All Mail</a>
               <a href="inbox.jsp?displayType=challenge">Challenges</a>
               <a href="inbox.jsp?displayType=note">Notes</a>
               <a href="inbox.jsp?displayType=request">Friend Requests</a>
            </nav>
         </section>
      </div>
      <div id="compose">
         <form method="post" action="../Quizness/social/compose-mail.jsp">
            <input type="submit" value="Compose" />
         </form>
      </div>
      <hr id="inbox"/>
      <div id="num-messages">
      <%if(numRequests == 0 && numNotes == 0 && numChallenges == 0) { %>
         <h4> You've got no messages. Zero. None.</h4>
      <%} %>
      </div>
      <form method="post" action="MessageServlet">
         <div id="table-container">
            <table id="table">
               <tbody>
               
               <% 
               // DISPLAY NOTES
				for (int i = 0; i < numNotes && dispNotes; i++){
					int messageID = notes.get(i); 
					currNote = (Note) Message.getMessage(messageID); 
					int fromID = currNote.getFromID();
					Date sentAt = currNote.getSentAt();
					User user = User.getUser(fromID); 
					boolean viewed = currNote.getViewed();
					if(user != null){
				%>
		                  <tr>
		                     <!-- DISPLAY UNREAD LINKS IN BOLD -->
		                     <td align="left" width="7%">
		                        <input type="checkbox" id="checkbox<%=i%>" name="check" value="<%=messageID%>">
		                     </td>
		                     <!-- LINK TO SENDER'S PROFILE -->
		                     <td align="left" width="25%"><a href="profile.jsp?id=<%=fromID%>"><font><b><%=user.getUserName()%></b></font></a></td>
		                     <!-- LINK TO MESSAGE -->
		                     <td align="left" width="50%"><a href="social/read-mail.jsp?msg_id=<%=messageID%>"><font color><b><%= currNote.getSubject() %></b></font></a></td>
		                     <td align="right" width="30%"><b><%=sentAt%></b></td>
		                  </tr>
                  		<%
					}
				}
               	%>
               	
               	<%
               	// DISPLAY REQUESTS
				for (int i = 0; i < numRequests && dispRequests; i++){
					int messageID = requests.get(i); 
					currRequest = (FriendRequest) Message.getMessage(messageID); 
					int fromID = currRequest.getFromID();
					Date sentAt = currRequest.getSentAt();
					User user = User.getUser(fromID); 
					boolean viewed = currRequest.getViewed();
					if(user != null){
				%>
		                  <tr>
		                     <!-- DISPLAY UNREAD LINKS IN BOLD -->
		                     <td align="left" width="7%">
		                        <input type="checkbox" id="checkbox<%=i%>" name="check" value="<%=messageID%>">
		                     </td>
		                     <!-- LINK TO SENDER'S PROFILE -->
		                     <td align="left" width="25%"><a href="profile.jsp?id=<%=fromID%>"><font><b><%=user.getUserName()%></b></font></a></td>
		                     <!-- LINK TO MESSAGE -->
		                     <td align="left" width="50%"><a href="social/read-mail.jsp?msg_id=<%=messageID%>"><font color><b><%= currRequest.getSubject() %></b></font></a></td>
		                     <td align="right" width="30%"><b><%=sentAt%></b></td>
		                  </tr>
                  		<%
					}
				}
               	%>
               	
               	<%
               	// DISPLAY CHALLENGES
				for (int i = 0; i < numChallenges && dispChallenges; i++){
					int messageID = challenges.get(i); 
					currChallenge = (Challenge) Message.getMessage(messageID); 
					int fromID = currChallenge.getFromID();
					Date sentAt = currChallenge.getSentAt();
					User user = User.getUser(fromID); 
					boolean viewed = currChallenge.getViewed();
					if(user != null){
				%>
		                  <tr>
		                     <!-- DISPLAY UNREAD LINKS IN BOLD -->
		                     <td align="left" width="7%">
		                        <input type="checkbox" id="checkbox<%=i%>" name="check" value="<%=messageID%>">
		                     </td>
		                     <!-- LINK TO SENDER'S PROFILE -->
		                     <td align="left" width="25%"><a href="profile.jsp?id=<%=fromID%>"><font><b><%=user.getUserName()%></b></font></a></td>
		                     <!-- LINK TO MESSAGE -->
		                     <td align="left" width="50%"><a href="social/read-mail.jsp?msg_id=<%=messageID%>"><font color><b><%= currChallenge.getSubject() %></b></font></a></td>
		                     <td align="right" width="30%"><b><%=sentAt%></b></td>
		                  </tr>
                  		<%
					}
				}
               	%>
                  
                  
               </tbody>
            </table>
         </div>
         <div>
            <span id="footer">
               <%--<b>Selected Messages :</b>
               <select name="update_type">
                  <option value="delete">Delete Messages</option>
                  <option value="read">Mark as Read</option>
                  <option value="unread">Mark as Unread</option>
                  <input type="hidden" name="quiz_id" value="s">
               		<input type="submit" name="inbox_update" value="Update">
               </select> --%>
               <form method = "post" action = "DeleteMessageServlet">
               		<input type="hidden" name="quiz_id" value="s">
               		<input type="submit" name="inbox_update" value="Delete Seleted Messages">
               </form>
            </span>
         </div>
      </form>
   </body>
</html>