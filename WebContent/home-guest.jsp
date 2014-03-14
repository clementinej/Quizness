<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, model.*" %>
<%@ page errorPage="../site/404.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
   <head>
      <meta charset="UTF-8" />
      <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="stylesheet" type="text/css" href="css/style.css" />
      <link rel="stylesheet" type="text/css" href="css/style_login.css" />
      <link href='http://fonts.googleapis.com/css?family=Open+Sans+Condensed:700,300|Montserrat' rel='stylesheet' type='text/css' />
   </head>
   <body>
   <div>
      <%
         String name = "Guest User";
         
         // ARE THERE ANY POPULAR QUIZZES?
         int numPopularQuizzes = 0;
         ArrayList<Integer> topQuizzes = UserHome.getTopQuizzes(5);
         numPopularQuizzes = topQuizzes.size();
         
         // ARE THERE ANY NEW QUIZZES?
         int numNewQuizzes = 0;
         ArrayList<Integer> newQuizzes = UserHome.getRecentlyCreatedQuizzes(5);
         numNewQuizzes = newQuizzes.size();
         %>
         
        <!--top bar -->
      <div class="top">
         <span class="header-link"><a href="site/search-guest.jsp">Search</a></span>
         <span class="header-link"><a href="index.jsp">Log In</a></span>
         <span class="header-link"><a href="create-account.jsp">Create a New Account</a></span>
         <span class="right">Welcome, <%=name %></span>
      </div>
      <div class="container_main">
         <section class="main">
            <h4 class="cs-text" id="cs-text">Quizness</h4>
         </section>
	   </div>
	   <div id="below" style="margin-bottom:100px;margin:0 auto;width:80%;margin-left:20%;">
   		
			<div style="float:left;width:25%;">
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
	        }%>
   			</div>
   			<div style="float:left;width:25%;">	
	        <% 
  			 if(numPopularQuizzes != 0) {%>
  				<h1>Popular Quizzes</h1><% 
  				if(numPopularQuizzes > 5) {
   					for(int i = 0; i < 5; i++) {
   						Quiz topQuiz = Quiz.getQuiz(topQuizzes.get(i));
   						int topQuizID = topQuizzes.get(i);
  			%>
			<p><a href="quiz/quiz-summary-guest.jsp?quiz_id=<%=topQuizID%>"><%=topQuiz.getTitle()%></a></p>
			<%	
   					} // end for loop
   				} else {
   					for(int i = 0; i < numPopularQuizzes; i++) {
   						Quiz topQuiz = Quiz.getQuiz(topQuizzes.get(i));
   						int topQuizID = topQuizzes.get(i);
   			%>
   			<p><a href="quiz/quiz-summary-guest.jsp?quiz_id=<%=topQuizID%>"><%=topQuiz.getTitle()%></a></p>
   					<% 
   					} // end for loop
   				} // end else
  			 } // end if
   				%>
   			</div>
   			<div style="float:left;width:25%;">
   			<%
   			if(numNewQuizzes != 0) {%>
   				<div><h1>New Quizzes</h1><%
   				if(numNewQuizzes > 5) {
   					for(int i = 0; i < 5; i++) {
   						Quiz newQuiz = Quiz.getQuiz(newQuizzes.get(i));
   						int newQuizID = newQuizzes.get(i);
  			%>
			<p><a href="quiz/quiz-summary.jsp-guest?quiz_id=<%=newQuizID%>"><%=newQuiz.getTitle()%></a></p>
			<%
					} // end for loop
   				} else {
   					for(int i = 0; i < numNewQuizzes; i++) {
   						Quiz newQuiz = Quiz.getQuiz(newQuizzes.get(i));
   						int newQuizID = newQuizzes.get(i);
   			%>
   			<p><a href="quiz/quiz-summary-guest.jsp?quiz_id=<%=newQuizID%>"><%=newQuiz.getTitle()%></a></p>
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