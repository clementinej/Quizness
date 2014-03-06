<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*, java.util.*" %>
<%
// GET USER INFO
User u = (User) session.getAttribute("current user");
Inbox inbox = Inbox.getInbox(u.getUserID());

// GET MESSAGE INFO
int messageID = Integer.parseInt(request.getParameter("messageID"));
Message m = inbox.getMessage(messageID);
m.markAsRead();
String subject = m.getSubject();
String body = m.getBody();
Date timeSent = m.getSentAt();
User from = ServerConnection.getUser(m.getFromID());

// GET INBOX INFO
int numRequests = inbox.getNumFriendReqs();
int numMessages = inbox.getNumNewMessages();
int numChallenges = inbox.getNumChallenges();

// GET CHALLENGE INFO
Challenge challenge = null;
Quiz challengeQuiz = null;
int challengeQuizID = -1;
if(numChallenges != 0) {
	challengeQuizID = m.getId();
	challengeQuiz = Quiz.getQuiz(challengeQuizID);
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
		<h3><%=from%> has challenged you to take <a href="quiz/show-quiz.jsp?quiz_id=<%=challengeQuizID%>"><%=challengeQuiz.getTitle()%></a>!</h3>
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
