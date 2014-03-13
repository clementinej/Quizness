<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="model.*" %>
<%@ page import="java.util.*" %>
<%@ page errorPage="../site/404.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
   <head>
   <meta charset="UTF-8" />
    	<title>Profile Page</title>
      <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="stylesheet" type="text/css" href="css/style.css" />
      <link rel="stylesheet" type="text/css" href="css/style_login.css" />
      <link href='http://fonts.googleapis.com/css?family=Open+Sans+Condensed:700,300|Montserrat' rel='stylesheet' type='text/css' />
      <link rel="stylesheet" type="text/css" href="css/global.css" />
   </head>
   <body>
   <%
   User currUser = (User) session.getAttribute("current user");
   int currUserID = currUser.getUserID();
 
   String userIDString = request.getParameter("id");   
   int userID; 
   User user; 
   
   if(userIDString != null){
	   userID = Integer.parseInt(request.getParameter("id"));
	   user = (User) User.getUser(userID);
   } else {
	   userID = currUserID;
	   user = currUser; 
   }
   
   String name = user.getUserName();
   Inbox inbox = Inbox.getInbox(userID);
   int numQuizzesTaken = user.numQuizzesTaken();
   int numFriends = user.getFriends().size();
   String aboutMe = user.getAboutMe(); 
   String location = user.getLocation();
   int averageScore = (int) user.getAverageScore();
   ArrayList<Achievement> achievements = user.getAchievements();
   ArrayList<User> friends = user.getFriends();
   int highScore = 0;
   if(!user.getHighScore().equals("This user hasn't taken any quizzes yet!")) {
	   highScore = Integer.parseInt(user.getHighScore());
   }

   %>
      <nav>
         <ul id="n" class="clearfix">
            <li><a href="home.jsp">Home</a></li>
       		<li><a href="create-quiz.jsp">Create Quiz</a></li>	
         	<li><a href="social/compose-mail.jsp">Compose </a></li>
         	<li><a href="profile.jsp">Profile</a></li>
         	<li><a href="site/admin.jsp">Admin</a></li>
         	<li><a href="inbox.jsp">Inbox</a></li>
          	<li><a href="site/search.jsp">Search</a></li>
         </ul>
      </nav>
      <div id="content" class="clearfix">
         <section id="left">
            <div id="userStats" class="clearfix">
               <div class="pic">
                  <a href="#"><img width="150" height="150" /></a>
               </div>
               <div class="data">
                  <h1><%=name%></h1>
                  <h3><%=location%></h3>
                  <div class="sep"></div>
                  <ul class="numbers clearfix">
                     <li>Quizzes Taken<strong><%=numQuizzesTaken%></strong></li>
                     <li>Highest Score<strong><%=highScore%></strong></li>
                     <li class="nobrdr">Average Score<strong><%=averageScore%></strong></li>
                  </ul>
               </div>
            </div>
            <h1>About Me:</h1>
            <p><%=aboutMe%></p>
                 <!-- Show nothing if no friend requests.
            If friend requests, display them.
            If not your profile, display friend request button. -->
         <%
         	// Check if the current user is session user
            boolean myProfile = false;
         
         	// Check if the user has any pending requests
            boolean pendingRequests = false;
            
         	// Check if the session is already friends with the current user
            boolean alreadyFriends = false;
         	
         	// Check if there is a pending request from the session to the the current user
            boolean requestSent = false; 
            
            if(userID == currUserID) myProfile = true; 
            if(inbox.getNumFriendReqs() > 0) pendingRequests = true; 
            
            // Could be buggy
            if(myProfile == false && currUser.isFriendsWith(userID)) alreadyFriends = true; 
            
            if(myProfile == false && inbox.hasPendingRequestFrom(currUserID)) requestSent = true;  
            %>
            <div class="gcontent">
               <% if(myProfile == true) {
                  	if(pendingRequests == true) {
                %>
               <div class="head">
                  <h1>Friend Requests</h1>
               </div>
               <div class="boxy">
                  <p>You have pending friend requests! Go to your inbox.</p>
               </div>
               <%
                  }} 
               
               	if(myProfile == false){
                  	if(alreadyFriends == false && requestSent == false) {%>
               		<div class="head">
                  		<h1>You're not friends. Wanna Be?</h1>
               		</div>
               		<form method="post" action = "FriendRequestServlet">
               			<div class="boxy">
               				<input type ="hidden" name="toID" value="<%=userID%>">
                  			<input id="submit" class="blue-button" type="submit" value="Let's Be Friends!">
               			</div>
               		</form>
               <%} else if(alreadyFriends == true) {%>
            	     <div class="head">
                  		<h1>You and <%=name%> are friends!</h1>
               		</div>
               <%}} %>
               <% if(myProfile == false && requestSent == true){ %>
                	<div class = "head">
                	<h1>You already sent an request to <%=name%>!</h1>
                	</div>
                <%} %>
                
                <% if(myProfile == false){ %>
               	<form method="post" action="social/compose-mail.jsp">
               	<div class="inputs">
               		<input type ="hidden" name="messageType" value="note">
               		<input type ="hidden" name="recipient" value=<%=userID%>>
     				<input id="submit" class="blue-button" type="submit" value="Send a Note!">
     			</div>
     			</form>
     			<%} %>
     			
     			<% if(alreadyFriends == true) {%>
            	<form method="post" action="UnfriendServlet">
               	<div class="inputs">
               		<input type ="hidden" name="userID" value=<%=userID%>>
     				<input id="submit" class="blue-button" type="submit" value="Unfriend this user">
     			</div>
     			</form>
               <% } %>
     			
     			<% if(myProfile == false && currUser.isAdmin()){ %>
               	<form method="post" action="DeleteUserServlet">
               	<div class="inputs">
               		<input type ="hidden" name="userID" value=<%=userID%>>
     				<input id="submit" class="blue-button" type="submit" value="Delete this user">
     			</div>
     			</form>
     			
     			<% if(currUser.isAdmin() && !user.isAdmin()) { %>
     			<form method="post" action="MakeAdminServlet">
               	<div class="inputs">
               		<input type ="hidden" name="user_id" value=<%=userID%>>
     				<input id="submit" class="blue-button" type="submit" value="Make this user an admin">
     			</div>
     			</form>
     			<% } %>
     			<%} %>
            </div>
         </section>
         <section id="right">
            <div class="gcontent">
               <div class="head">
                  <h1>Achievements</h1>
               </div>
               <div class="boxy">
                  <p>Great Job!</p>
                  <div class="badgeCount">
                  <%for(Achievement a: achievements) { %>
                     <p><%=a.getAchievement()%></p>
                     <%} %>

                  </div>
               </div>
            </div>
            <div class="gcontent">
               <div class="head">
                  <h1>Friends List</h1>
                  <div class="inputs"></div>
               </div>
               <div class="boxy">
                  <p>Friends List</p>
                  <div class="friendslist clearfix">
                  <% for(User f: friends) { %>
                     <div class="friend">
                        <span class="friendly"><a href="profile.jsp?id=<%=f.getUserID() %>"><%=f.getUserName()%></a></span>
                     </div>
                     <%} %>
                  </div>
               </div>
            </div>
         </section>
      </div>
   </body>
</html>