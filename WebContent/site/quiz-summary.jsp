<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="model.*", import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
User user = session.getAttribute("current user");
//The text description of the quiz. 
int quizID = request.getParameter("quiz-id");
Quiz quiz = Quiz.getQuiz(quizID);
String description = quiz.getDescription();
//The creator of the quiz (hot linked to the creator’s user page). 
User creatorID = quiz.getCreatorID();
String creator = User.getUser(creatorID);
//A list of the user’s past performance on this specific quiz. 
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
%>
<!--A way to initiate taking the quiz. -->
<!--A way to start the quiz in practice mode, if available. -->
<!--A way to start editing the quiz, if the user is the quiz owner. -->
</body>
</html>