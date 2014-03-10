<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*", import="java.util.*"%>
<%@ page errorPage="../site/404.jsp" %>

<%

User currentUser = (User) session.getAttribute("currUser");
int quizTryID = Integer.parseInt(request.getParameter("quiz-try-id"));
QuizTry currTry = QuizTry.getTry(quizTryID);
if(currTry.getUserID() != currentUser.getUserID()) {
	if(!currentUser.isAdmin()) {
		return;
	}
}
int quizID = currTry.getQuizID();
Quiz currentQuiz = Quiz.getQuiz(quizID);
if(!currTry.isInProgress()) {
	return; // Return if quiz already finished
}

int numQuestions = currentQuiz.getNumQuestions();
Question[] questions = currentQuiz.getQuestions();
%>

<body>
<form method="post" action="/SaveTry">
<%
for(Question q : questions) {
		request.setAttribute("QuizTry", currTry);
		request.setAttribute("QuestionType", q.getQuestionType());
%>
	<div style="padding:10px;margin-bottom:20px;background-color:#f6f6f6">
		<jsp:include page="questionGeneration/${requestScope['QuestionType']}.jsp" />
	</div>
<%
	}
%>
<div>
	<input type="hidden" name="quiz_attempt_id" value="<%=currTry.getTryID()%>">
	<center>
		<input type="submit" name="submit" value="Submit Answers">
		<input type="submit" name="save" value="Save Answers">
	</center>
</div>
</form>
</body>