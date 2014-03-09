<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, model.*" %>
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
      </style>
   </head>
   <body><
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
         //numMessages = inbox.getNumNotes();
               
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
    	numQuizzesTakenByFriends = recentlyTakenByFriends.size();
		%>
         
        <!--top bar -->
      <div class="top">
         <span class="header-link"><a href="create-quiz.jsp">Create Quiz</a></span>
         <span class="header-link"><a href="social/compose-mail.html">Compose </a></span>
         <span class="header-link"><a href="social/profile.jsp">Profile</a></span>
         <span class="header-link"><a href="site/admin.jsp">Admin</a></span>
         <span class="header-link"><a href="social/inbox.jsp">Inbox</a></span>
         <span class="right">Welcome to Quizness, <%=name %></span>
      </div>
      <div class="container_main">
         <section class="main">
            <h2 class="cs-text" id="cs-text">Quizness</h2>
         </section>
         <div style="float:left">
		<div style="float:right">
            
            <h1>Popular Quizzes</h1>
			<%
  			 if(numPopularQuizzes != 0) {
  				if(numPopularQuizzes > 5) {
   					for(int i = 0; i < 5; i++) {
   						Quiz topQuiz = Quiz.getQuiz(topQuizzes.get(i));
   						int topQuizID = topQuizzes.get(i);
  			%>
			<p><a href="../quiz/show-quiz.jsp?quiz-id=<%=topQuizID%>"><%=topQuiz.getTitle()%></a></p>
			<%	
   					} // end for loop
   				} else {
   					for(int i = 0; i < numPopularQuizzes; i++) {
   						Quiz topQuiz = Quiz.getQuiz(topQuizzes.get(i));
   						int topQuizID = topQuizzes.get(i);
   			%>
   			<p><a href="../quiz/show-quiz.jsp?quiz-id=<%=topQuizID%>"><%=topQuiz.getTitle()%></a></p>
   					<% 
   					} // end for loop
   				} // end else
  			 } // end if
   				%>
   			<h1>New Quizzes</h1>
   			<%
   			if(numNewQuizzes != 0) {
   				if(numNewQuizzes > 5) {
   					for(int i = 0; i < 5; i++) {
   						Quiz newQuiz = Quiz.getQuiz(newQuizzes.get(i));
   						int newQuizID = newQuizzes.get(i);
  			%>
			<p><a href="../quiz/show-quiz.jsp?quiz-id=<%=newQuizID%>"><%=newQuiz.getTitle()%></a></p>
			<%
					} // end for loop
   				} else {
   					for(int i = 0; i < numNewQuizzes; i++) {
   						Quiz newQuiz = Quiz.getQuiz(newQuizzes.get(i));
   						int newQuizID = newQuizzes.get(i);
   			%>
   			<p><a href="../quiz/show-quiz.jsp?quiz-id=<%=newQuizID%>"><%=newQuiz.getTitle()%></a></p>
   			<%
   					}
   				} // end else 
   			}  // end if
   			%>
   			 			   			
   			<h1>Quizzes You've Taken Recently</h1>
			<%
   			if(numUserTakenQuizzes != 0) {
   				if(numUserTakenQuizzes > 5) {
   					for(int i = 0; i < 5; i++) {
   						int quizID = userRecentQuizzesTaken.get(i);
   						Quiz takenQuiz = Quiz.getQuiz(quizID);
   			%>
			<p><a href="../quiz/quiz-list.jsp?quiz-id=<%=quizID%>"><%=takenQuiz.getTitle()%></a></p>
   			<%
   					} // end for loop
   				} else {
   					for(int i = 0; i < numUserTakenQuizzes; i++) {
   						int quizID = userRecentQuizzesTaken.get(i);
   						Quiz takenQuiz = Quiz.getQuiz(quizID); 
   			%>
   			<p><a href="../quiz/quiz-list.jsp?quiz-id=<%=quizID%>"><%=takenQuiz.getTitle()%></a></p>
   			<% 
   					}
   				}
   			}
   			%>
   			
   					
			<h1>Quizzes You've Created Recently</h1>
			<%
   			if(numRecentQuizzes !=0) {
   				if(numRecentQuizzes > 5) {
   					for(int i = 0; i < 5; i++) {
   						int quizID = userRecentQuizzes.get(i);
   						Quiz createdQuiz = Quiz.getQuiz(quizID);
  			%>
			<p><a href="../quiz/quiz-list.jsp?quiz-id=<%=quizID%>"><%=createdQuiz.getTitle()%></a></p>
   			<%
   					} // end for loop
   				} else {
   					for(int i = 0; i < numRecentQuizzes; i++) {
   						int quizID = userRecentQuizzes.get(i);
   						Quiz createdQuiz = Quiz.getQuiz(quizID);
   			%>
   			<p><a href="../quiz/quiz-list.jsp?quiz-id=<%=quizID%>"><%=createdQuiz.getTitle()%></a></p>
   			<% 
   					}
   				}
   			}
   			%>
   				<h1>Recent Achievements</h1>
			<%
   			if(numAcheivements != 0) {
   				if(numAcheivements > 5) {
   					for(int i = 0; i < 5; i++) {
   						Achievement a = achievements.get(i);
   			%>
			<p>You earned <%=a.getDescription()%></p>
			<%
					} // end for loop
   				} else {
   					for(int i = 0; i < numAcheivements; i++) {
   						Achievement a = achievements.get(i);
   			%>
   			<p>You earned <%=a.getDescription()%></p>
   			<% 
   					}
   				}
   			}
   			%>
   			
   			<h1>Messages</h1>
			<%
	   		if(numMessages !=0) {
	   			if(numMessages > 1) {
	  		 %>
			<p>You have <%=numMessages %> new messages! <a href="../inbox/user?id=<%=userID %>">Go to your inbox.</a></p>
			<%
				} else {
			%>
			<p>You have a new message! <a href="../inbox/user?id=<%=userID %>">Go to your inbox.</a></p>	
			<% 	
				}
	   		}
			%>
			
			<h1>Challenges</h1>
			<%
	   		if(numChallenges != 0) {
	   			if(numChallenges < 5) {
	   				for(int i = 0; i < numChallenges; i++) {
	   				String from = challenges.get(i).getChallenger().getUserName();
	   				int quizID = challenges.get(i).getQuizID();
	   				String challengeQuizTitle = Quiz.getQuiz(quizID).getTitle();
	   		%>
			<p><%=from %> has challenged you to take <a href=../quiz-summary?quiz-id=<%=quizID%>"><%=challengeQuizTitle %></a></p>
			<% 
					} // end for loop
				} else {
					for(int i = 0; i < 5; i++) {
						String from = challenges.get(i).getChallenger().getUserName();
						int quizID = challenges.get(i).getQuizID();
						String challengeQuizTitle = Quiz.getQuiz(quizID).getTitle();
			%>
			<p><%=from %> has challenged you to take <a href=../quiz-summary?quiz-id=<%=quizID%>"><%=challengeQuizTitle %></a></p>
					<% 
					}
				}
			}
			%>
					<h1>Friend Requests</h1>
			<%
	   		if(numReqs != 0) {
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
			
			<h1>Friend Activity</h1>
			<h6>Recently Created by Friends</h6>
			<%
			if(numFriendCreations != 0) {
				if(numFriendCreations > 5) {
	   				for(int i = 0; i < 5; i++) {
	   					Quiz q = Quiz.getQuiz(recentlyCreatedByFriends.get(i));
	   					String quizName = q.getTitle();
	   					int creatorID = q.getCreatorID();
	   					String creator = User.getUser(creatorID).getUserName();
	   		%>
			<p><a href="../profile?user=<%=creatorID%>"><%=creator %></a> + " created " + <%=quizName %></p>
			<%
					} // end for loop
				} else {
					for(int i = 0; i < numFriendCreations; i++) {
						Quiz q = Quiz.getQuiz(recentlyCreatedByFriends.get(i));
						String quizName = q.getTitle();
						int creatorID = q.getCreatorID();
						String creator = User.getUser(creatorID).getUserName();
			%>
			<p><a href="../profile?user=<%=creatorID%>"><%=creator %></a> + " created " + <%=quizName %></p>
			<% 
					} // end for loop
				} // end else
			} // end if
			%>
  	
  				<h6>Recently Taken by Friends</h6>
			<%
			if(numQuizzesTakenByFriends != 0) {
				if(numQuizzesTakenByFriends > 5) {
					for(int i = 0; i < 5; i++) {
	   					Quiz q = Quiz.getQuiz(recentlyTakenByFriends.get(i));
	   					String quizName = q.getTitle();
	   					int creatorID = q.getCreatorID();
	   					String creator = User.getUser(creatorID).getUserName(); 
	   		%>
			<p><a href="../profile?user=<%=creatorID%>"><%=creator %></a> + " took " + <%=quizName %></p>
			<%
					} // end for loop
				} else {
					for(int i = 0; i < numQuizzesTakenByFriends; i++) {
						Quiz q = Quiz.getQuiz(recentlyTakenByFriends.get(i));
						String quizName = q.getTitle();
						int creatorID = q.getCreatorID();
						String creator = User.getUser(creatorID).getUserName();
						%>
			<p><a href="../profile?user=<%=creatorID%>"><%=creator %></a> + " took " + <%=quizName %></p>
						<% 
					} // end for loop
				} // end else 
			} // end if 
			%>
	        </div>
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