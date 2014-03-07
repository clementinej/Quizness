<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="model.*" import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>   <link rel="stylesheet" type="text/css" href="../../css/style_login.css" />
</head>
<body>
<%--
	int quizID = Integer.parseInt(request.getParameter("quiz id"));
	int questionID = Integer.parseInt(request.getParameter("question id"));
	Quiz quiz = Quiz.getQuiz(quizID); 
	Question question = quiz.getQuestion(questionID);
	String questionText = question.getQuestion();
	String title = quiz.getTitle();
	String solution = question.getAnswer();
	
	--%>
<%
	//dummydata
	
	int questionID = 3;
	String title = "title";
	String questionText = "question text";
	int numBlanks = 6;
	String solution = "solution";
	%>
	<div class="container">
   <form method="post" action="../CreateServlet" id="signup">
      <div class="header">
         <h2><%=title%></h2>
         <h3>Question <%=questionID %></h3>
         <p>Save your answer when you're done editing, or head back to the quiz to cancel.</p>
      </div>
      <div class="sep"></div>
      <div class="inputs">
 		<p><%=questionText %></p><br>
		<h3>Solution</h3>
		<p><%=solution%></p>
	
  	 	<div>
  	 	<input type="submit" value="Edit Question"></input>
  	 	<input type="submit" value="Edit Solution"></input>
		<div>
		<input type="submit" value="Back to Quiz"></input>
		<input type="submit" value="Save"></input></div>
      </div>
      </form>
   </div>
</body>
</html>