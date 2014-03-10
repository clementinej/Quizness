<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@ page import="model.*" %>
<%@ page import="java.util.*" %>
<%@ page errorPage="../site/404.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
   <head>
      <meta charset="utf-8" />
      <title>Profile Page</title>
      <link rel="stylesheet" type="text/css" href="../css/global.css" />
   </head>
   <body>
   <%
   // COMMENT THIS IN. EVERYTHING SHOULD WORK. ALSO REMEMBER TO COMMENT IN THE 
   // MODEL IMPORT AT THE TOP OF THE PAGE. THAT'S THE BIT THAT I CAN'T COMPILE
   
	
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
   
   //model.User u = new model.User(false, "Gene Oetomo", "gene", "goetomo@stanford.edu", "my name is gene", "stanford");
   String name = user.getUserName();
   Inbox inbox = Inbox.getInbox(userID);
   int numQuizzesTaken = user.numQuizzesTaken();
   int numFriends = user.getFriends().size();
   String aboutMe = user.getAboutMe(); // Allow user to add this as easy extension?
   String location = user.getLocation();
   ArrayList<Achievement> achievements = user.getAchievements();
   ArrayList<User> friends = user.getFriends();
   String highScore = user.getHighScore();
   
   // COMMENT OUT THESE PLACEHOLDERS. ALL THE FIELDS SHOULD BY DYNAMICALLY 
   // POPULATED BY THE DATA ABOVE
// 	String name = "Clementine Jacoby";
//	int numQuizzesTaken = 187;
//	int highScore = 490;
//	String numFriends = "24";
//   String location = "San Francisco, CA"; // Allow user to add this as easy extension?
//	String aboutMe = "I figured we could add this as an extension easily.";
//	ArrayList<String> achievements = new ArrayList<String>();
//	achievements.add("acheived!");
//	achievements.add("acheived!");
//	achievements.add("acheived!");
//	ArrayList<String> friends = new ArrayList<String>();
//	friends.add("Gene O.");
//	friends.add("Lloyd L.");
//	friends.add("Tony W."); 

   %>
      <nav>
         <ul id="n" class="clearfix">
            <li><a href="#">Profile</a></li>
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
                     <li class="nobrdr">Last Quiz Taken<strong><%=numFriends%></strong></li>
                  </ul>
               </div>
            </div>
            <h1>About Me:</h1>
            <p><%=aboutMe%></p>
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
                  <span><a href="#">See all...</a></span>
               </div>
            </div>
            <div class="gcontent">
               <div class="head">
                  <h1>Friends List</h1>
                  <div class="inputs"></div>
               </div>
               <div class="boxy">
                  <p>Your friends - <%=numFriends%> total</p>
                  <div class="friendslist clearfix">
                  <% for(User f: friends) { %>
                     <div class="friend">
                        <span class="friendly"><a href="#"><%=f.getUserName()%></a></span>
                     </div>
                     <%} %>
                  </div>
                  <span><a href="#">See all...</a></span>
               </div>
            </div>
         </section>
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
            if(myProfile == false && currUser.getFriends().contains(user)) alreadyFriends = true; 
            
            if(myProfile == false && inbox.hasPendingRequestFrom(currUserID)) requestSent = true;  
            %>
         <section id="left">
            <div class="gcontent">
               <% if(myProfile == true) {
                  	if(pendingRequests == true) {
                %>
               <div class="head">
                  <h1>Friend Requests</h1>
               </div>
               <div class="boxy">
                  <p>People who wanna be your friends</p>
                  <!-- TODO: Display a List of friend requests -->
               </div>
               <%
                  }} 
               
               	if(myProfile == false){
                  	if(alreadyFriends == false && requestSent == false) {%>
               		<div class="head">
                  		<h1>You're not friends. Wanna Be?</h1>
               		</div>
               		<form method="post" action = "../FriendRequestServlet">
               			<div class="boxy">
               				<input type ="hidden" name="toID" value="<%=userID%>">
                  			<input id="submit" type="submit" value="Let's Be Friends!">
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
               	<form method="post" action="../social/compose-mail.jsp">
               	<div class ="boxy">
               		<input type ="hidden" name="messageType" value="note">
               		<input type ="hidden" name="recipient" value=<%=userID%>>
     				<input id="submit" type="submit" value="Send a Note!">
     			</div>
     			</form>
     			<%} %>
     			
     			<% if(myProfile == false && currUser.isAdmin()){ %>
               	<form method="post" action="../DeleteUserServlet">
               	<div class ="boxy">
               		<input type ="hidden" name="userID" value=<%=userID%>>
     				<input id="submit" type="submit" value="Delete this user">
     			</div>
     			</form>
     			<%} %>
            </div>
         </section>
      </div>
   </body>
</html>