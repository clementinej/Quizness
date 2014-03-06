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
String lastTry = "Aug. 15th";
int numTries = 9;
%>

<div class="container">
   <form method="post" action="../../InitQuizServlet??" id="signup">
      <div class="header">
         <h3>Quiz Summary</h3>
         <p>Take, edit, or practice the quiz.</p>
      </div>
      <div class="sep"></div>
      <div class="inputs">
         <h3>Quiz Title</h3>
         <p>Description</p><br>
         <p><b>Created by</b> <%=creator %></p>
         
         <br><h3>High Scores</h3>
         <p>Your high score on this quiz is <%=userHighScore %></p>
         <p>The all time best score on this quiz is <%=highScore %> </p>
         
         <br><h3>History</h3>
         <p>The last person to take this quiz scored <%=lastScore %></p>
         <p>You last took this quiz on <%=lastTry %></p>
         <p><%=numTries %> people have taken this quiz</p>
         
               
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