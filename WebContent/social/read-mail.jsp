<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="model.*" %>
<%@page import="java.util.*" %>
<%--@ page errorPage="../site/404.jsp" --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Read Mail</title>
   <link rel="stylesheet" type="text/css" href="../css/style_login.css" />

</head>
<body>
<%
// Message information
int messageID = Integer.parseInt(request.getParameter("msg_id"));
Message m = Message.getMessage(messageID);
Challenge challenge; 
Quiz quiz; 
String quizTitle =""; 
double highScore = -0.0;

String subject = m.getSubject();
String body = m.getBody();
Date sent = m.getSentAt();
java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat();

// User information
int fromID = m.getFromID();
int toID = m.getToID();
User user = User.getUser(fromID);
String from = user.getUserName(); 

// Determine if this message is acceptable and display an accept/reject button
String messageType = m.getMessageType();
boolean acceptable = false;


if(messageType.equals("challenge")){
	challenge = (Challenge) m;
	int quizID = challenge.getQuizID(); 
	quiz = Quiz.getQuiz(quizID);
	quizTitle = quiz.getTitle();
	acceptable = true;
	highScore = challenge.getChallengerHighScore();
}
if(messageType.equals("friendRequest")){
	m = (FriendRequest) m; 
	acceptable = true; 
}


// Mark this message as read
m.markAsRead(); 

%>

<div class="container">
   <form id="signup" method="post" action="../AcceptMessageServlet">
      <div class="header">
         <h2><%=subject%></h2>
         <h4>From <%=from %></h4>
         <p>Sent at <%=formatter.format(sent) %></p>
         
         <% if(messageType.equals("challenge")){ %>
         <p>Quiz: <%=quizTitle%>>
         <!-- REDIRECT TO QUIZ SUMMARY -->
         <p>Your Opponent's High Score: <%=highScore%>>
         <% } %>
      </div>
      <div class="sep"></div>
      <div class="inputs">
 		<p><%=body %></p><br>
 		<% if(acceptable){ %>
       		<input type ="hidden" name="to_id" value=<%=toID%>>
       		<input type ="hidden" name="msg_id" value=<%=messageID%>>
       		<input type ="hidden" name="messageType" value=<%=messageType%>>
     		<input id="submit" type="submit" name="send_accept" value="Accept">
     		<input id="submit" type="submit" name="send_reject" value="Reject">
     
     	<% } %>

		<a href="../inbox.jsp">Back to Inbox</a>
      </div>
      </form>
       
       
   </div>
</body>
</html>