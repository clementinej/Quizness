<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="model.*, java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head> 
   <link rel="stylesheet" type="text/css" href="../css/style_login.css" />

</head>
<%
User user = (User) session.getAttribute("current user");
int questionIndex = 0;//Integer.parseInt(request.getParameter("question index"));
Quiz quiz = Quiz.getQuiz(59);//(Quiz) session.getAttribute("current quiz");
//if(!user.isAdmin() && quiz.getCreatorID() != user.getUserID()) return;
ArrayList<Question> questions = quiz.getQuestions();
%>

<body>
	<div class="container">
	<form method="post" action="quiz/edit/EditQuestion" id="signup">
	<%
	for(int i = 0; i < questions.size(); i++) {
		Question q = questions.get(i);
		int type = q.getQuestionType();
		
		String redirectTo = "";
		switch(type) {
		case 1://question-response
			redirectTo = "questionEditing/edit-question-answer.jsp";
		case 2://fill-in-the-blank
			redirectTo = "questionEditing/edit-fill-in-the-blanks.jsp";
		case 3://multiple choice
			redirectTo = "questionEditing/edit-multiple-choice.jsp";
		case 4://picture response
			redirectTo = "questionEditing/edit-picture-response.jsp";
		}
		
		String text = q.getQuestion();
	%>
		<p><%=text %></p>
		<input type="hidden" name="question_type" value="<%=type%>"/>
		<a href="<%=redirectTo%>">Edit Question</a>
		<%}%>
	</form>
	</div>
</body>
