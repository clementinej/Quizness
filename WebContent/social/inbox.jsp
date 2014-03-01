<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*, java.util.*" %>
<%
// GET USER INFO
User u = (User) session.getAttribute("currUser");

// GET MESSAGE INFO
int message = Integer.parseInt(request.getParameter("messageID"));
Message m = Inbox.getMessage(message);
m.markAsRead();
String subject = m.getSubject();
String body = m.getBody();
Date timeSent = m.getSentAt();
User from = User.getUser(m.getFromID());

// GET INBOX INFO
int numRequests = Inbox.numFriendReqs(u.getUserID());
int numMessages = Inbox.numNewMessages(u.getUserID());
int numChallenges = Inbox.numChallenges(u.getUserID());

// GET CHALLENGE INFO
Challenge challenge = null;
int challengeQuizID = -1;
if(numChallenges != 0) {
	challengeQuizID = m.getQuizID();
	challenge = Quiz.getQuiz(challengeQuizID);
}
 %>

<body>
	<a href="messaging/messages.jsp">Messages (<%=numMessages%>) </a>
	<a href="messaging/friendRequests.jsp"">Friend Requests (<%=numRequests%>) </a> 
	<div>
		<form method="post" action="compose-mail.jsp">
			<input type="submit" value="Compose" />
		</form>
	</div>	
	<%if (challengeQuizID != -1) { %>
		<h3><%=from%> has challenged you to take <a href="quiz/show-quiz.jsp?quiz_id=<%=challengeQuizID%>"><%=challenge.getTitle()%></a>!</h3>
	<%} else { %>
		<h3><%=subject%></h3>
	<%} %>
	<div>
		<table>
			<tr>
				<th>From </th>
				<td><%=from%></td>
			</tr>
			<tr>
				<th>Sent </th>
				<td><%=timeSent%></td>
			</tr>
		</table>
	</div>
	<div>
		<textarea readonly><%=body%></textarea>
	</div>
	<%if (challengeQuizID != -1) { %>
		<form method="post" action="quiz/show-quiz.jsp?quiz_id=<%=challengeQuizID%>">
		<input type="submit" name ="compose" value="LET'S DO THIS THING!"/>
		</form>
	<%} %>	
	<br>
</body>