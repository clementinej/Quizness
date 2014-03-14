<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.*" %>
<%@ page errorPage="../site/404.jsp" %>
<head>
  <meta charset="UTF-8" />
      <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="stylesheet" type="text/css" href="../css/style.css" />
      <link rel="stylesheet" type="text/css" href="../css/style_login.css" />
      <link href='http://fonts.googleapis.com/css?family=Open+Sans+Condensed:700,300|Montserrat' rel='stylesheet' type='text/css' />
</head>
<%

// CHALLENGER INFO
User user = (User) session.getAttribute("current user");
ArrayList<User> friends = (ArrayList<User>)user.getFriends();
//System.out.print("friends" + friends.get(0).getUserName());
String userName = user.getUserName();
String quizID = request.getParameter("quiz_id"); 
String toEmail =""; 
String toString = request.getParameter("recipient");
System.out.print("Sending a Message to: " + toString);

// VARIABLE SET UP
int toID = -1;
double topScore = 0.0;
int challengeID = -1;
String recipientInfo ="";
String challengeSubject="";
String challengeName="";
String challenge="";


// RECIPIENT INFO
if(toString != null){
	toID = Integer.parseInt(toString);
	User to = User.getUser(toID);
	toEmail = to.getEmail(); 
	recipientInfo = to.getUserName() + "<" + to.getEmail() + ">";
}

// CHALLENGE INFO
if(quizID != null) {
	topScore = -1.0;
	try {
		topScore = Double.parseDouble(request.getParameter("top_score"));
	} catch (NullPointerException e) {
		topScore = 0.0;
	} catch (NumberFormatException e) {
		topScore = 0.0;
	}
	challengeID = Integer.parseInt(request.getParameter("quiz_id"));
	Quiz ch = Quiz.getQuiz(challengeID);
	String title = ch.getTitle();
	challengeSubject = user.getUserName() + " has challenged you to take " + title + "!";
	challenge = user.getUserName() + "'s highest score was: " + topScore + ". But we're sure you can do better." + 
		" Show " + userName + " that you mean Quizness by beating their score.";
}

%>
<body>
      <!--top bar -->
      <div class="top">
      	<span class="header-link"><a href="../home.jsp">Home</a></span>
         <span class="header-link"><a href="../create-quiz.jsp">Create Quiz</a></span>
         <span class="header-link"><a href="../social/compose-mail.jsp">Compose </a></span>
         <span class="header-link"><a href="../profile.jsp">Profile</a></span>
         <span class="header-link"><a href="../site/admin.jsp">Admin</a></span>
         <span class="header-link"><a href="../inbox.jsp">Inbox</a></span>
          <span class="header-link"><a href="../site/search.jsp">Search</a></span>
      </div>
	<div class="center" style="width:700px; margin-left: auto; margin-right: auto;">
	<% if (friends.size() == 0 && toString == null){ %>
		<form method="post" action="../site/search.jsp">
	     <div class="form-1">
	     <h2 id="no-friends">You can't do that. <br> You have no friends.</h2>
 		<input id="find-friends" class="submit" type="submit" value="Go find some!">
 		</div>
 	</form>
 	<%} else { %>
		<form id="signup" method="post" action="../MessageServlet">
		<% if (challengeID != -1) { %>
		<div class="header">
		<h3>Send a Challenge</h3>
		</div>
		<% } else { %>
		<div class="header"><h3>Compose New Message</h3></div>
		<% } %>
			<table id="compose-mail">
				<tr>
					<th align="left" width="10%">To </th>
					<td align="left">
					<%
					System.out.print("to id" + toID); 
					if(toID == -1) { %>	
					<h3>Select a friend!</h3>
						<div class="styled-select">
						<select name="user_email" id="friends">
						<% 
								for (int i = 0; i < friends.size(); i++) { 
								//System.out.print(friends.size());
								User f = friends.get(i);
								String to = f.getEmail();
						%>
								 <option value="<%=to%> "><%=to%></option>
							 	
								<% } %>
					
						</select>
						</div>
						<script>
							var textBox = document.getElementById('email_field');
							var dropDown = document.getElementById('friend_dropdown');
							var idField = document.getElementById('id_field');
							dropDown.onchange = function() {
								var info = dropDown.value.split(",");
								textBox.value = info[0];
								idField.value = info[1];
							};
						</script>
					<% } else { %>
					<%System.out.print("recipientInfo"); %>
						<input id="email" name= "email" type="name" value="<%=recipientInfo%>"/>
						<input id="user_email" name = "user_email" type="hidden" value="<%=toEmail%>"/>
					<% } %>
					</td>
				</tr>
				<tr>
					<th align="left" width="10%">Subject </th>
					<% if (challengeID != -1) { %>
						<td align="left" class="message-subject"><%=challengeSubject%><input placeholder="subject" name="subject" type="hidden" value="<%=challengeSubject%>"></td>
					<% } else { %>
						<td align="left" class="message-subject"><input name="subject" type="name" style="width:500px" value=""/></td>
					<% } %>
				</tr>
				<tr>
					<th align="left" width="10%">Body </th>
					<% if (challengeID != -1) { %>
						<td><textarea name="body" class="message-body"><%=challenge%></textarea></td>
					<% } else { %>
						<td><textarea name="body" class="message-body">Enter your message here.</textarea></td>
					<% } %>
				</tr>
				<tr>
					<th></th>
					<% if (challengeID != -1) { %>
						<td>
							<input type="hidden" name="quiz_id" value="<%=challengeID%>">
							<input type="hidden" name="high_score" value="<%=topScore %>">
							<input type="submit" name ="send_challenge" value="Send">
							<input type="submit" value="Cancel"/>
						</td>
					<% } else { %>
						<td><input type="submit" name ="send_compose" value="Send">
						<input type="submit" value="Cancel"/></td>
					<% } %>
				</tr>
			</table>
		</form>
	<% } %>
	</div><br>
</body>
