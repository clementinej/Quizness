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
	ArrayList<Set<String>> answers = question.getAnswer();
	int numBlanks = answers.size();
	
	--%>
	
	<%
	//dummydata
	
	int questionID = 3;
	String title = "title";
	String questionText = "question text";
	int numBlanks = 6;
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
		<p>Solutions</p>
	<%
	for(int i = 0; i < numBlanks; i++) {
		// "options for blank # [blank]"
		//for (String answer: answers.get(i)) {
			String solution = "solution" + i;
			%>
			<input type="checkbox" value="<%=solution%>"><%=solution%><br>
			<% 
	}
	
%>
  	 	<div>
  	 	<input type="submit" value="Delete Selected"></input>
  	 	<input type="submit" value="Edit Selected"></input>
  	 	<input type="submit" value="Add A Solution"></input></div>
		<div>
		<input type="submit" value="Back to Quiz"></input>
		<input type="submit" value="Save"></input></div>
      </div>
      </form>
   </div>
</body>