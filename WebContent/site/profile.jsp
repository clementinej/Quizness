<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@ page import="model.*" %>
<%@ page import="java.util.*" %>
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
   

//   User u = (User)session.getAttribute("currUser");
   model.User u = new model.User(false, "Gene Oetomo", "gene", "goetomo@stanford.edu", "my name is gene", "stanford");
   String name = u.getUserName();
   int numQuizzesTaken = u.numQuizzesTaken();
//   int highScore = u.getHighScore();
   int numFriends = u.getFriends().size();
   String aboutMe = u.getAboutMe(); // Allow user to add this as easy extension?
   String location = u.getLocation();
	ArrayList<Achievement> achievements = u.getAchievements();
   ArrayList<User> friends = u.getFriends();
//   String highScore = u.getHighScore();
   
   // COMMENT OUT THESE PLACEHOLDERS. ALL THE FIELDS SHOULD BY DYNAMICALLY 
   // POPULATED BY THE DATA ABOVE
// 	String name = "Clementine Jacoby";
//	int numQuizzesTaken = 187;
	int highScore = 490;
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
                  <p>Your friends - 106 total</p>
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
            boolean myProfile = true;
            boolean pendingRequests = true;
            boolean alreadyFriends = true;
            %>
         <section id="left">
            <div class="gcontent">
               <% if(myProfile==false) {
                  if(pendingRequests==false) {
                  %>
               <div class="head">
                  <h1>Friend Requests</h1>
               </div>
               <div class="boxy">
                  <p>People who wanna be your friends</p>
               </div>
               <%
                  }} 
               		if(myProfile==false){
                  	if(alreadyFriends==false) {%>
               <div class="head">
                  <h1>You're not friends. Wanna Be?</h1>
               </div>
               <div class="boxy">
                  <input id="submit" type="submit" value="Let's Be Friends!">
               </div>
               <%}else if(alreadyFriends==true) {%>
            	     <div class="head">
                  <h1>You and <%=name%> are friends!</h1>
               </div>
 
                  <%}} %>
            </div>
         </section>
      </div>
   </body>
</html>