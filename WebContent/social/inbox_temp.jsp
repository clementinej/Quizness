<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@ page import="model.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
   <head>
      <meta charset="UTF-8" />
      <title>Inbox</title>
      <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="stylesheet" type="text/css" href="../css/normalize.css" />
      <link rel="stylesheet" type="text/css" href="../css/inbox.css" />
      <script src="../js/modernizr-2.6.2.min.js"></script>
   </head>
      <body>
      
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
	  %>
	
      <div class="container">
         <section class="color-1">
            <nav class="cl-effect-14">
               <a href="challenges.jsp">Challenges</a>
               <a href="messages.html">Messages</a>
               <a href="friend-requests.jsp">Friend Requests</a>
            </nav>
         </section>
      </div>
      <!-- /container -->
      <div id="compose">
      <!--TODO CHANGE TO POST TO COMPOSE-MAIL.JSP-->
         <form method="post" action="compose-mail.jsp">
            <input type="submit" value="Compose" />
         </form>
      </div>
      <hr id="inbox"/>
      <div id="num-messages">
         <h4> You've got no messages. Zero. None.</h4>
      </div>
      <form method="post" action="MessageServlet">
         <div id="table-container">
            <table id="table">
               <tbody>
               
               <% 
				for (int i = 0; i < numNotes; i++){
					int messageID = notes.get(i); 
					currNote = (Note) Message.getMessage(messageID);
					int fromID = currNote.getFromID();
					
			
					//int fromUserID = currNote.getFromID();
						%>
		                  <tr>
		                     <!-- DISPLAY UNREAD LINKS IN BOLD -->
		                     <td align="left" width="7%">
		                        <input type="checkbox" id="checkbox<%=i%>" name="check" value="<%=messageID%>">
		                     </td>
		                     <!-- LINK TO SENDER'S PROFILE -->
		                     <td align="left" width="25%"><a href="user/profile.jsp?user=<%=fromID%>"><font><b>From</b></font></a></td>
		                     <!-- LINK TO MESSAGE -->
		                     <td align="left" width="50%"><a href="../social/read-mail.jsp?msg_id=<%=messageID%>"><font color><b><%= currNote.getSubject() %></b></font></a></td>
		                     <td align="right" width="20%"><b>10:00AM</b></td>
		                  </tr>
                  		<%
					}
               	%>
                  
                  
               </tbody>
            </table>
         </div>
         <div>
            <span id="footer">
               <b>Selected Messages :</b>
               <select name="update_type">
                  <option value="delete">Delete Messages</option>
                  <option value="read">Mark as Read</option>
                  <option value="unread">Mark as Unread</option>
               </select>
               <input type="hidden" name="quiz_id" value="s">
               <input type="submit" name="inbox_update" value="Update">
            </span>
         </div>
      </form>
   </body>
</html>