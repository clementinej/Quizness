<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="model.*" import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<%
	int quizTryID = Integer.parseInt(request.getParameter("quiz try id"));
	int questionIndex = Integer.parseInt(request.getParameter("question index"));
	QuizTry quizTry = QuizTry.getTry(quizTryID); 
	ArrayList<Question> questions = quizTry.getQuestions();
	Question question = questions.get(questionIndex);
	String questionText = question.getQuestion();
	String title = Quiz.getQuiz(quizTry.getQuizID()).getTitle();
	ArrayList<Set<String>> solution = question.getAnswer();
	%>
<%--
	//dummydata
	
	int questionID = 3;
	String title = "title";
	String questionText = "question text";
	int numBlanks = 6;
	String solution = "solution";
	--%>
	<div class="container">
   <form method="post" action="../CreateServlet" id="signup">
      <div class="header">
         <h2><%=title%></h2>
         <h3>Question <%=questionIndex %></h3>
      </div>
      <div class="sep"></div>
      <div class="inputs">
 		<p><%=questionText %></p><br>
		<h3>Solution</h3>
		<input name="user_solution"/>
      </div>
      </form>
   </div>
</body>
</html>