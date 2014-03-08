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
      <style>
         .link {
         color:#fffff;
         font-weight:bold;
         text-align:center;
         }
         .links {
         padding-left:40px;
         }
         .header-link {
		padding-left:50px;
		font-weight:bold;	
		}
		.right {
		float:right;
		margin-right:25px;
		font-weight:bold;
		font-size:200%;
		}
      </style>
   </head>
   <body>
      <%
         User u = (User) session.getAttribute("current user");
         int userID = u.getUserID();
         String name = u.getUserName();
         /*
         // ARE THERE ANY POPULAR QUIZZES?
         int numPopularQuizzes = 0;
         ArrayList<Integer> topQuizzes = UserHome.getTopQuizzes(5);
         numPopularQuizzes = topQuizzes.size();
         
         // ARE THERE ANY NEW QUIZZES?
         int numNewQuizzes = 0;
         ArrayList<Integer> newQuizzes = UserHome.getRecentlyCreatedQuizzes(5);
         numNewQuizzes = newQuizzes.size();
         
         // HAS THE USER TAKEN QUIZZES RECENTLY?
         int numUserTakenQuizzes = 0;
         ArrayList<Integer> userRecentQuizzesTaken = UserHome.getRecentlyPlayedQuizzes(5, userID);
         numUserTakenQuizzes = userRecentQuizzesTaken.size();
         
         // HAS THE USER CREATED QUIZZES RECENTLY?
         int numRecentQuizzes = 0;
         ArrayList<Integer> userRecentQuizzes = UserHome.getRecentlyCreatedQuizzesByUser(5, userID);
         numRecentQuizzes = userRecentQuizzes.size();
         
         // DOES THE USER HAVE ANY ACHEIVEMENTS? 
         int numAcheivements = 0;
         ArrayList<Achievement> achievements = u.getAchievements();
         numAcheivements = achievements.size();
         
         // DOES THE USER HAVE ANY MESSAGES?
         int numMessages = 0;
         Inbox inbox = Inbox.getInbox(userID);
         numMessages = inbox.getNumNotes();
               
         // DOES THE USER HAVE ANY CHALLENGES?
         int numChallenges = 0;
         numChallenges = inbox.getNumChallenges();
         ArrayList<Challenge> challenges = new ArrayList<Challenge>();
         // TODO replace above line with call to getChallenges()
         
         // DOES THE USER HAVE ANY FRIEND REQUESTS?
         int numReqs = 0;
         ArrayList<FriendRequest> reqs = u.getFriendRequests();
    	 numReqs = reqs.size();
    	 
    	 // HAVE FRIENDS CREATED QUIZZES RECENTLY?
    	int numFriendCreations = 0;
    	ArrayList<Integer> recentlyCreatedByFriends = UserHome.getRecentlyCreatedByFriends(5, userID);
    	numFriendCreations = recentlyCreatedByFriends.size();
    	
    	// HAVE FRIENDS TAKEN QUIZZES RECENTLY?
    	int numQuizzesTakenByFriends = 0;
    	ArrayList<Integer> recentlyTakenByFriends = UserHome.getRecentlyTakenByFriends(5, userID);
    	numQuizzesTakenByFriends = recentlyTakenByFriends.size();*/

         %>
      <!--top bar -->
      <div class="top">
         <span class="header-link"><a href="../quiz/create-quiz.jsp">Create Quiz</a></span>
         <span class="header-link"><a href="../social/compose-mail.html">Compose </a></span>
         <span class="header-link"><a href="../social/profile.jsp">Profile</a></span>
         <span class="header-link"><a href="../site/admin.jsp">Admin</a></span>
         <span class="header-link"><a href="../social/inbox.jsp">Inbox</a></span>
         <span class="right">Welcome to Quizness, <%=name %></span>
      </div>
      <!--/ Codrops top bar -->

      <div class="container_main">
         <section class="main">
            <h2 class="cs-text" id="cs-text">Quizness</h2>
         </section>
         <div style="float:left">
            <!-- INSERT SHIT HERE -->
         </div>
      </div>
      <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
      <script type="text/javascript" src="../js/jquery.lettering.js"></script>
      <script>
         $(document).ready(function() {
         	$("#cs-text").lettering().children('span').wrap('<span />');
         });
      </script>
   </body>
</html>