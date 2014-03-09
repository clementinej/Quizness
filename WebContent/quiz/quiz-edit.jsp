<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="model.*, java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head> 
   <link rel="stylesheet" type="text/css" href="../css/style_login.css" />

</head>
<%
User user = (User) session.getAttribute("current user");
int questionIndex = Integer.parseInt(request.getParameter("question index"));
Quiz quiz = (Quiz) session.getAttribute("current quiz");
if(!user.isAdmin() && quiz.getCreatorID() != user.getUserID()) return;
ArrayList<Question> questions = quiz.getQuestions();
%>

<body>
	<div class="container">
	<form method="post" action="quiz/edit/EditQuestion" id="signup">
	<%
	for(int i = 0; i < questions.size(); i++) {
		Question q = questions.get(i);
		int type = q.getQuestionType();
		String text = q.getQuestion();
	%>
		<p><%=text %></p>
		<a href="questionEdit/<%=type %>.jsp">Edit Question</a>
		<%}%>
	</form>
	</div>
</body>
