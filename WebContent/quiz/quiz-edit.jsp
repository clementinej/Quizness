<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="model.*, java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
User user = (User) session.getAttribute("current user");
int questionIndex = Integer.parseInt(request.getParameter("question index"));
Quiz quiz = (Quiz) session.getAttribute("current quiz");
if(!user.isAdmin() && quiz.getCreatorID() != user.getUserID()) return;
ArrayList<Question> questions = quiz.getQuestions();
%>

<body>
	<form method="post" action="quiz/edit/EditQuestion">
	<%
	for(int i = 0; i < questions.size(); i++) {
		Question q = questions.get(i);
		int type = q.getQuestionType();
		String text = q.getQuestion();
	%>
		<a href="questionGeneration/<%=type %>.jsp"></a>
		<p><%=text %></p>
		<%}%>
	</form>
</body>
