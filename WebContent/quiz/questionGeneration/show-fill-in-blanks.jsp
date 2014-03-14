<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*" import="java.util.*" %>
<%@ page errorPage="../site/404.jsp" %>
<head>
   <link rel="stylesheet" type="text/css" href="../../css/style_login.css" />
</head>
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