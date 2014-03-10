<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="model.*"%>
    <%@page import="java.util.*"%>
    <%@ page errorPage="../site/404.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Summary</title>
      <link rel="stylesheet" type="text/css" href="../css/style_login.css" />

</head>
<body>
<%
User user = (User) session.getAttribute("current user");
//The text description of the quiz. 

int quizID = Integer.parseInt(request.getParameter("quiz id"));

Quiz quiz = Quiz.getQuiz(quizID);

String description = quiz.getDescription();

//The creator of the quiz (hot linked to the creator's profile). 
User creator = User.getUser(quiz.getCreatorID());

//A list of the user's past performance on this specific quiz. 

//Consider allowing the user to order this by date, by percent correct, and by amount of time the quiz took. 
ArrayList<Integer> triesByDate = QuizSummary.getPerformanceByDate(user.getUserID(), quizID, 10);
ArrayList<Integer> triesByScore = QuizSummary.getPerformanceByScore(user.getUserID(), quizID, 10);
ArrayList<Integer> triesByTime = QuizSummary.getPerformancyByTime(user.getUserID(), quizID, 10);
	
//A list of the highest performers of all time. 
ArrayList<Integer> topPerformerIds = QuizSummary.getTopPerformers(quizID, 5);

//A list of quiz ids for the top 5 performances on the quiz
ArrayList<Integer> topQuizTriesTodayIds = QuizSummary.getTopPerformers(quizID, 5, 1); //TODO

//A list showing the performance of recent test takers (both good and bad). 
ArrayList<Integer> recentTryIds = QuizSummary.getRecentPerformance(quizID, 5);

//Summary statistics on how well all users have performed on the quiz. 
//Summary quizSummary = Summary.getStatistics(quizID);
%>

<% 
double userHighScore = QuizTry.getTry(triesByScore.get(0)).getScore();
double highScore = QuizTry.getTry(topPerformerIds.get(0)).getScore();
double lastScore = QuizTry.getTry(recentTryIds.get(0)).getScore();

//the LAST five quiz tries 
ArrayList<QuizTry> recentTries = new ArrayList<QuizTry>();
for(int i = 0; i < recentTryIds.size(); i++) {
	QuizTry t = QuizTry.getTry(recentTryIds.get(i));
	recentTries.add(t);
}

//should return the USER'S LAST five quiz tries 
ArrayList<QuizTry> lastTriesUser = new ArrayList<QuizTry>(); 
for(int i = 0; i < triesByDate.size(); i++) {
	QuizTry t = QuizTry.getTry(triesByDate.get(i));
	recentTries.add(t);
}

//should return the BEST five tries on this quiz
ArrayList<QuizTry> bestTries = new ArrayList<QuizTry>(); 
for(int i = 0; i < topPerformerIds.size(); i++) {
	QuizTry t = QuizTry.getTry(topPerformerIds.get(i));
	recentTries.add(t);
}

//should return the last five tries on this quiz
ArrayList<QuizTry> bestTriesToday = new ArrayList<QuizTry>(); 
for(int i = 0; i < topQuizTriesTodayIds.size(); i++) {
	QuizTry t = QuizTry.getTry(topQuizTriesTodayIds.get(i));
	recentTries.add(t);
}

%>

<div class="container" style="float:left;padding-left:100px;">
   <form method="post" action="../../InitQuizServlet??" id="signup">
      <div class="header">
         <h3>Quiz Summary</h3>
         <p>Take, edit, or practice the quiz.</p>
      </div>
      <div class="sep"></div>
      <div class="inputs">
         <h3>Quiz Title</h3>
         <p>Description</p><br>
         <p><b>Created by </b><a href="#"><%=creator %></a></p>
         
         <!-- Print info on the user's last five tries -->
         <br><h3>Your History</h3>
         <p>Your high score on this quiz is <%=userHighScore %></p>
         <%
         for(int i = 0; i < 5; i++) {
         %>
         <p>On <%=lastTriesUser.get(i).getDate() %> you scored <%=lastTriesUser.get(i).getScore() %> </p>
         <%
         }
         %>
         
         <!-- Print info on the last five tries of this quiz-->
         <br><h3>Recent Activity</h3>
         <%
         for(int i = 0; i < 5; i++) {
         %>
         <p>At <%=recentTries.get(i).getTime() %> <%=User.getUser(recentTries.get(i).getUserID()).getUserName()%> scored <%=recentTries.get(i).getScore() %> </p>
         <%
         }
         %>
         
      </div>	
   </form>
   </div>
   
    <!-- Print info on the top scores on this quiz-->
   <div class="container" style="float:left;padding-left:200px;">
   <form method="post" action="../../InitQuizServlet??" id="signup">
      <div class="header">
         <h3>Top Scores</h3>
      </div>
      <div class="sep"></div>
      <div class="inputs">
      	<br><h3>Top Scores Of All Time</h3>
		 <%
         for(int i = 0; i < 5; i++) {
         %>
         <p>On <%=bestTries.get(i).getDate() %> <%=User.getUser(bestTries.get(i).getUserID()).getUserName()%> scored <%=bestTries.get(i).getScore() %> </p>
         <%
         }
         %>
         
         <br><h3>Top Scores Today</h3>
		 <%
         for(int i = 0; i < 5; i++) {
         %>
         <p>At <%=bestTriesToday.get(i).getTime() %> <%=User.getUser(bestTriesToday.get(i).getUserID()).getUserName()%> scored <%=bestTriesToday.get(i).getScore() %> </p>
         <%
         }
         %>
         
         <!-- We also need to display summary statistics, i.e. the average score on the quiz, 
         average amount of time it took, number of people who've taken it. -->
         
         <br><input id="submit" type="submit" value="Take Quiz!">
         <!--A way to start the quiz in practice mode, if available. -->
         <br><input id="submit" type="submit" value="Practice Quiz">
         <!--A way to start editing the quiz, if the user is the quiz owner. -->
         <br><input id="submit" type="submit" value="Edit Quiz">
      </div>	
   </form>
   </div>
</body>
</html>