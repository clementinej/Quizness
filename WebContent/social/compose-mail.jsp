<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.*" %>
<%@ page errorPage="../site/404.jsp" %>
<head>
   <link rel="stylesheet" type="text/css" href="../css/style_login.css" />
</head>
<%

// CHALLENGER INFO
User user = (User) session.getAttribute("current user");
ArrayList<User> friends = (ArrayList<User>)user.getFriends();
String userName = user.getUserName();
String messageType = request.getParameter("messageType");

// VARIABLE SET UP
int toID = 0;
double topScore = 0.0;
int challengeID = -1;
String challengeeInfo ="";
String challengeSubject="";
String challengeName="";
String challenge="";

// CHALLENGEE INFO
if(messageType != null) {
if(messageType.equals("challenge")) {
	String toString = request.getParameter("recipient");
	toID = -1;
	challengeeInfo = "";
	if (toString != null) {
		toID = Integer.parseInt(toString);
		User to = User.getUser(toID);
		challengeeInfo = to.getUserName() + "<" + to.getEmail() + ">";
	}

// CHALLENGE INFO
	topScore = -1.0;
	if (messageType.equals("challenge")) {
		topScore = Double.parseDouble(request.getParameter("top_score"));
		challengeID = Integer.parseInt(request.getParameter("quiz_id"));
		Quiz ch = Quiz.getQuiz(challengeID);
		String title = ch.getTitle();
		challengeSubject = user.getUserName() + " has challenged you to take " + title + "!";
		challenge = user.getUserName() + "'s highest score was: " + topScore + ". But we're sure you can do better." + 
					"Show " + userName + " that you mean Quizness by beating their score.";
	}
}
}
%>

<body>

	<div class="container">
	<form id="signup" method="post" action="MessageServlet">
	<% if (challengeID != -1) { %>
	<div class="header">
	<h3>Send a Challenge</h3>
	</div>
	<% } else { %>
	<div class="header"><h3>Compose New Message</h3></div>
	<% } %>
		<table>
			<tr>
				<th align="left" width="10%">To </th>
				<td align="left">
				<% if (toID == -1) { %>	

					<input id="email" name="email" type="name" style="width:300px" readonly/>
					<input id="id" name = "id" type="hidden" value=""/>
					<select name="type" id="friends">
						<option>Select a friend</option>
					<% for (int i = 0; i < friends.size(); i++) { 
						User f = friends.get(i);
					%>
						 <option value="<%=userName%> "><%=userName%></option>
					<% } %>
					</select>
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
					<input id="email" name= "email" type="name" style="width:300px" value="<%=challengeeInfo%>"/>
					<input id="id" name = "id" type="hidden" value="<%=toID%>"/>
				<% } %>
				</td>
			</tr>
			<tr>
				<th align="left" width="10%">Subject </th>
				<% if (challengeID != -1) { %>
					<td align="left"><%=challengeSubject%><input name="subject" type="hidden" value="<%=challengeSubject%>"></td>
				<% } else { %>
					<td align="left"><input name="subject" type="name" style="width:500px" value=""/></td>
				<% } %>
			</tr>
			<tr>
				<th align="left" width="10%">Body </th>
				<% if (challengeID != -1) { %>
					<td><textarea name="body"><%=challenge%></textarea></td>
				<% } else { %>
					<td><textarea name="body" style="width:500px;height:150px">Enter your message here.</textarea></td>
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
	</form></div><br>

</body>
