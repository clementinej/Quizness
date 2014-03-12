<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, model.*" %>
<%--@ page errorPage="../site/404.jsp" --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
   <head>
      <meta charset="UTF-8" />
      <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="stylesheet" type="text/css" href="css/style.css" />
      <link rel="stylesheet" type="text/css" href="css/style_login.css" />
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
		color:#fffff;
		float:right;
		margin-right:25px;
		font-weight:bold;
		font-size:100%;
		}
		
		#leftcontent {
		position: absolute;
		left:10px;
		top:50px;
		width:200px;
		}
		#centercontent {
		margin-left: 199px;
		margin-right:199px;
		margin-left: 201px;
		margin-right:201px;
		} 
		
		html>body #centercontent {
		margin-left: 20px;
		margin-right:0px;
		}
		#rightcontent {
		position: absolute;
		right:10px;
		top:50px;
		width:200px;
		}
		#innerleft {
		float:left;
		left:20px;
		top:50px;
		width:400px;
		}
		#innerright {
		float:right;
		right:10px;
		top:50px;
		width:400px;
		}
      </style>
   </head>
   <body>
   <div>
      <%
         User u = (User) session.getAttribute("current user");

         int userID = u.getUserID();
         String name = u.getUserName();
         
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
    	numQuizzesTakenByFriends = recentlyTakenByFriends.size();
		%>
         
        <!--top bar -->
      <div class="top">
         <span class="header-link"><a href="create-quiz.jsp">Create Quiz</a></span>
         <span class="header-link"><a href="social/compose-mail.jsp">Compose </a></span>
         <span class="header-link"><a href="profile.jsp">Profile</a></span>
         <span class="header-link"><a href="site/admin.jsp">Admin</a></span>
         <span class="header-link"><a href="inbox.jsp">Inbox</a></span>
          <span class="header-link"><a href="site/search.jsp">Search</a></span>
         <span class="right">Welcome, <%=name %></span>
      </div>
      <div class="container_main">
         <section class="main">
            <h4 class="cs-text" id="cs-text">Quizness</h4>
         </section>
         	<div id="leftcontent">
<!-- where it used to be -->
   			</div>
   				
   			<div id="centercontent">
   			<div id="innerleft"> 
   			<h1>Your Activity</h1>			
	   		<% if(numUserTakenQuizzes == 0 && numRecentQuizzes == 0) {
	   			%>	
	   		<div style="padding-bottom:50px;"><h4>You've had no activity lately. <a href="site/search.jsp">Get in the game!</a></h4>
	   		<%} %>
			<%
   			if(numUserTakenQuizzes != 0) {%>
   				<h2>Quizzes You've Taken</h2><%
   				if(numUserTakenQuizzes > 5) {
   					for(int i = 0; i < 5; i++) {
   						int quizID = userRecentQuizzesTaken.get(i);
   						Quiz takenQuiz = Quiz.getQuiz(quizID);
   			%>
   			  <h2><%=takenQuiz.getTitle()%></h2>
		<p><a href="quiz/quiz-summary.jsp?quiz_id=<%=quizID%>"><%=takenQuiz.getTitle()%></a></p>
   			<%
   					} // end for loop
   				} else {
   					for(int i = 0; i < numUserTakenQuizzes; i++) {
   						int quizID = userRecentQuizzesTaken.get(i);
   						Quiz takenQuiz = Quiz.getQuiz(quizID); 
   			%>
   			<p><a href="quiz/quiz-summary.jsp?quiz_id=<%=quizID%>"><%=takenQuiz.getTitle()%></a></p> 
   			<% 
   					}
   				}
   			}
   			%>
			<%
   			if(numRecentQuizzes !=0) {%>
   				<h2>Your Quiz Creations</h2><% 
   				if(numRecentQuizzes > 5) {
   					for(int i = 0; i < 5; i++) {
   						int quizID = userRecentQuizzes.get(i);
   						Quiz createdQuiz = Quiz.getQuiz(quizID);
  			%>
			<p><a href="quiz/quiz-summary.jsp?quiz_id=<%=quizID%>"><%=createdQuiz.getTitle()%></a></p>
   			<%
   					} // end for loop
   				} else {
   					for(int i = 0; i < numRecentQuizzes; i++) {
   						int quizID = userRecentQuizzes.get(i);
   						Quiz createdQuiz = Quiz.getQuiz(quizID);
   			%>
   			<p><a href="quiz/quiz-summary.jsp?quiz_id=<%=quizID%>"><%=createdQuiz.getTitle()%></a></p>
   			<% 
   					}
   				}
   			}
   			%>
			<%
   			if(numAcheivements != 0) {%>
   	   			<h2>Recent Achievements</h2><% 
   				if(numAcheivements > 5) {
   					for(int i = 0; i < 5; i++) {
   						Achievement a = achievements.get(i);
   			%>
			<p>You earned <%=a.getAchievement()%></p>
			<%
					} // end for loop
   				} else {
   					for(int i = 0; i < numAcheivements; i++) {
   						Achievement a = achievements.get(i);
   			%>
   			<p>You earned <%=a.getAchievement()%></p>
   			<% 
   					}
   				}
   			}
   			%>
   			</div>
   			</div> 
   			
   			<div id="innerleft">
   			<h1>Friend Activity</h1>
   			<% if(numFriendCreations == 0 && numQuizzesTakenByFriends == 0) { 
   			%>
   			<h4>You're friends aren't doing anything. <br><a href="inbox.jsp">Tell them they're whack.</a></h4>
   			<% } %>
			<%
			if(numFriendCreations != 0) {%>
				<h2>Recently Created by Friends</h2><% 
				if(numFriendCreations > 5) {
	   				for(int i = 0; i < 5; i++) {
	   					Quiz q = Quiz.getQuiz(recentlyCreatedByFriends.get(i));
	   					String quizName = q.getTitle();
	   					int creatorID = q.getCreatorID();
	   					String creator = User.getUser(creatorID).getUserName();
	   		%>
			<p><a href="profile.jsp?user=<%=creatorID%>"><%=creator %></a> created <%=quizName %></p>
			<%
					} // end for loop
				} else {
					for(int i = 0; i < numFriendCreations; i++) {
						Quiz q = Quiz.getQuiz(recentlyCreatedByFriends.get(i));
						String quizName = q.getTitle();
						int creatorID = q.getCreatorID();
						String creator = User.getUser(creatorID).getUserName();
			%>
			<p><a href="profile.jsp?user=<%=creatorID%>"><%=creator %></a> created <%=quizName %></p>
			<% 
					} // end for loop
				} // end else
			} // end if
			%>
  	
			<%
			if(numQuizzesTakenByFriends != 0) {%>
				<h2>Recently Taken by Friends</h2><% 
				if(numQuizzesTakenByFriends > 5) {
					for(int i = 0; i < 5; i++) {
	   					Quiz q = Quiz.getQuiz(recentlyTakenByFriends.get(i));
	   					String quizName = q.getTitle();
	   					int creatorID = q.getCreatorID();
	   					String creator = User.getUser(creatorID).getUserName(); 
	   		%>
			<p><a href="profile.jsp?user=<%=creatorID%>"><%=creator %></a> took <%=quizName %></p>
			<%
					} // end for loop
				} else {
					for(int i = 0; i < numQuizzesTakenByFriends; i++) {
						Quiz q = Quiz.getQuiz(recentlyTakenByFriends.get(i));
						String quizName = q.getTitle();
						int creatorID = q.getCreatorID();
						String creator = User.getUser(creatorID).getUserName();
						%>
			<p><a href="profile.jsp?user=<%=creatorID%>"><%=creator %></a> took <%=quizName %></p>
						<% 
					} // end for loop
				} // end else 
			} // end if 
			%>
			</div>
   			</div>
   			 
   			<div id="rightcontent">
			<%
	   		if(numMessages !=0) {%>
	   			<h2>Messages</h2><% 
	   			if(numMessages > 1) {
	  		 %>
			<p>You have <%=numMessages %> new messages! <a href="inbox.jsp">Go to your inbox.</a></p>
			<%
				} else {
			%>
			<p>You have a new message! <a href="inbox.jsp">Go to your inbox.</a></p>	
			<% 	
				}
	   		}
			%>
			
			<%
	   		if(numChallenges != 0) {%>
	   		<h2>Challenges</h2><%
	   			if(numChallenges > 1) {%>
	   			
	   		<p>You have <%=numChallenges %> new challenges! <a href="inbox.jsp">Go to your inbox.</a></p>
			<% } else { %>
				
			<p>You have a new challenge! <a href="inbox.jsp">Go to your inbox.</a></p>	
			<% 	
				}
	   		}
			%>
			
			
			<%
	   		if(numReqs != 0) {%>
	   			<h2>Friend Requests</h2><%	
	   			if(numReqs < 5) {
	   				for(int i = 0; i < numReqs; i++) {
	   					String from = reqs.get(i).getFrom();
	   		%>
			<p><%=from %> has requested your friendship!</p>
			<%
					} // end for loop
	   			} else {
	   				for(int i = 0; i < 5; i++) {
	   					String from = reqs.get(i).getFrom();
	   		%>
	   		<p><%=from %> has requested your friendship!</p>
	   		<% 
	   				} // end for loop
	   			} // end else 
	   		} // end if
	   		%>
	        </div>
	        
	        <div class="innerright">
	        <div id="announcements">
	        <h1>Announcements</h1>
	        <%
	        int numAnnouncements = 0;
	        ArrayList<String> announcements = Announcements.getBody();
	        numAnnouncements = announcements.size();
	        if(numAnnouncements != 0) {
	        	if(numAnnouncements > 5) {
	        		for(int i = 0; i < 5; i++) {
	        			String a = announcements.get(i);
	        			%>
	        			<h4><%=a%></h4>
	        			<%
	        		}
	        	} else {
	        		for(int i = 0; i < numAnnouncements; i++) {
	        			String a = announcements.get(i);
						%>
						<h4><%=a %></h4>
						<% 
	        		}
	        	}
	        }%></div>
	        
	        <div id="popular-quizzes">
	        <% 
  			 if(numPopularQuizzes != 0) {%>
  				<h2>Popular Quizzes</h2><% 
  				if(numPopularQuizzes > 5) {
   					for(int i = 0; i < 5; i++) {
   						Quiz topQuiz = Quiz.getQuiz(topQuizzes.get(i));
   						int topQuizID = topQuizzes.get(i);
  			%>
			<p><a href="quiz/quiz-summary.jsp?quiz_id=<%=topQuizID%>"><%=topQuiz.getTitle()%></a></p>
			<%	
   					} // end for loop
   				} else {
   					for(int i = 0; i < numPopularQuizzes; i++) {
   						Quiz topQuiz = Quiz.getQuiz(topQuizzes.get(i));
   						int topQuizID = topQuizzes.get(i);
   			%>
   			<p><a href="quiz/quiz-summary.jsp?quiz_id=<%=topQuizID%>"><%=topQuiz.getTitle()%></a></p>
   					<% 
   					} // end for loop
   				} // end else
  			 } // end if
   				%></div>
   			
   			<div id="new-quizzes">
   			<%
   			if(numNewQuizzes != 0) {%>
   				<div><h2>New Quizzes</h2><%
   				if(numNewQuizzes > 5) {
   					for(int i = 0; i < 5; i++) {
   						Quiz newQuiz = Quiz.getQuiz(newQuizzes.get(i));
   						int newQuizID = newQuizzes.get(i);
  			%>
			<p><a href="quiz/quiz-summary.jsp?quiz_id=<%=newQuizID%>"><%=newQuiz.getTitle()%></a></p>
			<%
					} // end for loop
   				} else {
   					for(int i = 0; i < numNewQuizzes; i++) {
   						Quiz newQuiz = Quiz.getQuiz(newQuizzes.get(i));
   						int newQuizID = newQuizzes.get(i);
   			%>
   			<p><a href="quiz/quiz-summary.jsp?quiz_id=<%=newQuizID%>"><%=newQuiz.getTitle()%></a></p>
   			<%
   					}
   				} // end else 
   			}  // end if
   			%>
   			</div>
   			
	        </div>
	   </div>
      <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
      <script type="text/javascript" src="js/jquery.lettering.js"></script>
      <script>
         $(document).ready(function() {
         	$("#cs-text").lettering().children('span').wrap('<span />');
         });
      </script>
      </div>
   </body>
</html>