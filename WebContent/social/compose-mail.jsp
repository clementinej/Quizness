<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.*" %>
<%--@ page errorPage="../site/404.jsp" --%>
<head>
  <meta charset="UTF-8" />
      <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="stylesheet" type="text/css" href="../css/style.css" />
      <link rel="stylesheet" type="text/css" href="../css/style_login.css" />
      <link href='http://fonts.googleapis.com/css?family=Open+Sans+Condensed:700,300|Montserrat' rel='stylesheet' type='text/css' />
   <style>
   .message-body{
   	margin-top:20px;
   	width:500px;
   	height:150px;
   	font-family: 'Lato', Calibri, Arial, sans-serif;
    font-size: 18px;
   }
    .message-subject{
    padding-top:20px;
   	font-family: 'Lato', Calibri, Arial, sans-serif;
    font-size: 18px;
   }
   .styled-select select {
   width: 268px;
   padding: 5px;
   font-size: 16px;
   line-height: 1;
   border: 1;
   border-radius: 1;
   height: 34px;
   }
   .header-link {
	padding-left:50px;
	font-weight:bold;	
	}
   </style>
</head>
<%

// CHALLENGER INFO
User user = (User) session.getAttribute("current user");
ArrayList<User> friends = (ArrayList<User>)user.getFriends();
//System.out.print("friends" + friends.get(0).getUserName());
String userName = user.getUserName();
String quizID = request.getParameter("quiz_id"); 
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
	<div class="container">
	<form id="signup" method="post" action="../MessageServlet">
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
				<%
				System.out.print("to id" + toID); 
				if(toID == -1) { %>	
					<div class="styled-select">
					<select name="type" id="friends">
						<option>Select a friend</option>
					<% for (int i = 0; i < friends.size(); i++) { 
						User f = friends.get(i);
						String to = f.getUserName();
					%>
						 <option value="<%=to%> "><%=to%></option>
						 <input id="id" name = "id" type="hidden" value="<%=f.getUserID()%>"/>
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
					<input id="email" name= "email" type="name" style="width:300px" value="<%=recipientInfo%>"/>
					<input id="id" name = "id" type="hidden" value="<%=toID%>"/>
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
	</div><br>
</body>
<script>
   var button = document.getElementById("send-button");
   button.addEventListener("click", function() {
	   	var type = document.getElementById("friends");
	   	var value = type.options[type.selectedIndex].value;
	   	if(value == 0) {
	   		alert("Please choose a question type");
	   	}
	   	});
</script>
