<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*, model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
   <head>
      <meta charset="UTF-8" />
      <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="stylesheet" type="text/css" href="../css/style.css" />
      <link rel="stylesheet" type="text/css" href="../css/style_login.css" />
      <link href='http://fonts.googleapis.com/css?family=Open+Sans+Condensed:700,300|Montserrat' rel='stylesheet' type='text/css' />
   </head>
   <body>
   	<!--top bar -->
            <div class="codrops-top">
                <a href="../quiz/create-quiz.jsp">Create Quiz</a>
                <a href="../social/compose-mail.html">Compose </a>
                <span class="right">
                <a href="../site/profile.jsp">Profile</a>
				<a href="../site/admin.jsp">Admin</a>
				<a href="../social/inbox.jsp">Inbox</a>
                </span>
                <div class="clr"></div>
            </div><!--/ Codrops top bar -->
      <div class="container_main">
      
         <section class="main">
            <h2 class="cs-text" id="cs-text">Quizness</h2>
         </section>
         <div style="float:left">
         	<!-- Start Nav Structure -->	
				<form action="/SearchServlet" method="post">
				<input type="name" name="search" placeholder="Search for quizzes, friends, etc."></input>
				</form>
			</div>
			
			<div style="float:right">
			<p>Popular Quizzes</p> 
			<p>Recently Created Quizzes</p>
			<p>Quizzes You've Taken Recently</p>
			<p>Quizzes You've Taken Recently</p>
			<p>Recent Achievements</p>
			<p>Messages</p>
			
			
			<h1>Challenges</h1>
			<%
			int numChallenges = 0;
			if(user.hasPendingChallenges()) {
			ArraList<Challenge> challenges = user.getPendingChallenges();	
			numChallenges = challenges.size();
			for(int i = 0; i < numChallenges; i++) {
				String from = numChallenges.get(i).getChallenger().getUserName();
				int quizID = numChallenges.get(i).getQuizID();
				Quiz challengeQuiz = Quiz.getQuiz(quizID).getTitle();
			%>
			<!-- TODO link to challenger's profile -->
			<p><%=from %> has challenged you to take <a href=../quiz-summary?quiz-id=<%=quizID%>"><%=challengeQuiz %></a></p>
			
			<h1>Friend Requests</h1>
			<%
			int numReqs = 0;
			if(user.hasPendingFriendRequests()) {
			ArrayList<FriendRequest> reqs = user.getFriendRequests();
			numReqs = reqs.size();
			for(int i = 0; i < numReqs; i++) {
				String from = reqs.get(i).getFrom();
			%>
			<!-- TODO link to friend's profile -->
			<p><%=from %> has requested your friendship!</p>
			<%}}%>
			
			<h1>Friend Activity</h1>
			<h6>Recently Created by Friends</h6>
			<%
			ArrayList<Integer> recentlyCreatedByFriends = Quiz.getRecentlyCreatedByFriends(5, userID);
			for(int i = 0; i < 5; i++) {
				Quiz q = Quiz.getQuiz(recentlyCreatedByFriends.get(i));
				String quizName = q.getTitle();
				int creatorID = q.getCreatorID();
				String creator = User.getUser(creatorID);
			%>
			<p><a href="../profile?user=<%=creatorID%>"><%=creator %></a> + " created " + <%=quizName %></p>
			<%}%>
			<h6>Recently Taken by Friends</h6>
			<%
			ArrayList<Integer> recentlyTakenByFriends = Quiz.getRecentlyTakenByFriends(5, userID);
			for(int i = 0; i < 5; i++) {
				Quiz q = Quiz.getQuiz(recentlyTakenByFriends.get(i));
				String quizName = q.getTitle();
				int creatorID = q.getCreatorID();
				String creator = User.getUser(creatorID);
			%>
			<p><a href="../profile?user=<%=creatorID%>"><%=creator %></a> + " took " + <%=quizName %></p>
			<%}%>
			
		 </div>
      </div>	
      <style>
      .link {
       color:#fffff;
       font-weight:bold;
       text-align:center;
      }
      
      .links {
      padding-left:40px;
      }
      </style>
      <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
      <script type="text/javascript" src="../js/jquery.lettering.js"></script>
      <script>
         $(document).ready(function() {
         	$("#cs-text").lettering().children('span').wrap('<span />');
         });
      </script>
   </body>
</html>