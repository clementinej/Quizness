<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*" import="java.util.*" %>
<head>
   <link rel="stylesheet" type="text/css" href="../../css/style_login.css" />
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
 		<div class="content" id="question_wrapper"> </div>
		<h3>Solution</h3>
		<p><%=solution%></p>
		<div class="content" id="answer_wrapper"> </div>

  	 	<div>
		<input type="button" onclick="edit_question();" value="Edit Question" />
  	 	<input type="button" onclick="edit_answer();" value="Edit Answer" />
  	 	<input type="submit" value="Delete Question"></input>
		<div>
		<input type="submit" value="Back to Quiz"></input>
		<input type="submit" value="Save"></input></div>
      </div>
      </form>
   </div>
   <script>
   function edit_question() {
	    document.getElementById('question_wrapper').innerHTML += '<span>Revised Question Text: <input type="text"></span>\r\n';
	}
   function edit_answer() {
	    document.getElementById('answer_wrapper').innerHTML += '<span>Revised Solution Text: <input type="text"></span>\r\n';
	}
   </script>
</body>