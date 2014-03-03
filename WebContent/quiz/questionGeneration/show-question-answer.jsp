<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="model.*" import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<%
// Get type-specific question object
	int currQuizID = Integer.parseInt(request.getParameter("quiz_id"));
	Quiz currQuiz = Quiz.getQuiz(currQuizID); 
	Question qr = currQuiz.getNextQuestion(); //this should pop, previous call to get type should peek
	String questionText = qr.getQuestion();
%>

<p><%=questionText %></p><br>
<input id="user_answer"></input>
<button id="next">Next Question</button>

<script>
var button = document.getElementById("next");
button.addEventListener("click", function() {
	var type = document.getElementById("question-type"); //somehow get the next question type from the java code
	var value = type.options[type.selectedIndex].value;
	if(value =="question-answer" || value == "picture-response" || value == "multiple-answer"){
		window.location = "/Quizness/quiz/questionGeneration/show-question-answer.jsp";
	} else if (value == "multiple-choice" || value == "multiple-choice-multiple-answer") {
		window.location = "/Quizness/quiz/questionGeneration/show-multiple-choice.jsp";
	} else {
		window.location = "/Quizness/quiz/questionGeneration/show-" + value + ".jsp";
	}
	});
</script>
</body>
</html>