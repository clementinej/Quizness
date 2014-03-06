<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="model.*"%>
    <%@page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Summary</title>
      <link rel="stylesheet" type="text/css" href="../css/style_login.css" />

</head>
<body>
<%--
User user = (User) session.getAttribute("current user");
//The text description of the quiz. 
int quizID = Integer.parseInt(request.getParameter("quiz id"));
Quiz quiz = Quiz.getQuiz(quizID);
String description = quiz.getDescription();
//The creator of the quiz (hot linked to the creator's profile). 
User creator = User.getUser(quiz.getCreatorID());
//A list of the user's past performance on this specific quiz. 
//Consider allowing the user to order this by date, by percent correct, and by amount of time the quiz took. 
Set<QuizTry> history = user.getHistory(quizID);
//A list of the highest performers of all time. 
ArrayList<User> topPerformers = History.getTopPerformersByQuiz(quizID);
//A list of top performers in the last day.
ArrayList<User> topToday = History.getTopPerformersToday(quizID);
//A list showing the performance of recent test takers (both good and bad). 
ArrayList<QuizTry> recentTries = History.getTries(today);
//Summary statistics on how well all users have performed on the quiz. 
Summary summary = quiz.getStatistics();
--%>

<% 
String creator = "Clementine";
int userHighScore = 10;
int highScore = 20;
int lastScore = 30;
ArrayList<QuizTry> lastTriesUser = new ArrayList<QuizTry>(); //should return the user's last five quiz tries 
ArrayList<QuizTry> recentTries = new ArrayList<QuizTry>(); // should return the last five tries on this quiz
ArrayList<QuizTry> bestTries = new ArrayList<QuizTry>(); // should return the last five tries on this quiz
ArrayList<QuizTry> bestTriesToday = new ArrayList<QuizTry>(); // should return the last five tries on this quiz

int numTries = 9;
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
         
         <!-- We also need to display like, summary statistics, i.e. the average score on the quiz, average amount of time it took, number of people who've taken it. -->
         
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