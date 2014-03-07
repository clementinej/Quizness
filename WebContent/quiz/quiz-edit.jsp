<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="model.*, java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
User user = (User) session.getAttribute("current user");
int questionID = Integer.parseInt(request.getParameter("question id"));
Question question = Question.getQuestion(questionID);
int quizID = question.getQuizID();
Quiz quiz = Quiz.getQuiz(quizID);
if(!user.isAdmin() && quiz.getCreatorID() != user.getUserID()) return;
String questionType = question.getQuestionType();
request.setAttribute("question", question);

%>

<body>
	<form method="post" action="quiz/edit/EditQuestion">
		<input type="hidden" name="question-id" value="<%=questionID%>">
		<a href="questionGeneration/<%=questionType %>.jsp"></a>
	</form>
</body>
