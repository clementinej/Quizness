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
	<div><%=quiz.getTitle() %></div>
	<div><%=quiz.getDescription() %></div>
	<form action="" method="post" id="title-edit">
		<a href="javascript: onclick=addTitle();"> Edit Title</a>
	</form>
	<form action="" method="post" id="description-edit">
		<a href="javascript: onclick=addDescription();"> Edit Description</a>
	</form>
	
	<%
	for(int i = 0; i < questions.size(); i++) {
		Question q = questions.get(i);
		int type = q.getQuestionType();
		String text = q.getQuestion();
	%>
		<p><%=text %></p>
		<a href="questionEdit/<%=type %>.jsp">Edit Question</a>
		<%}%>

	
	 <form method="post" action="CreateServlet" id="signup">
	  <table>
            <tr>
               <th>
                  <input type="hidden" name="quiz_id" value=""/>
                  <input type="hidden" name="intent" value="create quiz"/>
                  <select name="question-type" id="question-type">
                     <option value="0" selected></option>
                     <option value="question-answer">Question-Answer</option>
                     <option value="fill-in-blanks">Fill-in-the-Blank</option>
                      <option value="multiple-choice">Multiple Choice</option>
                     <option value="picture-response">Picture-Response</option>
                     <option value="multiple-answer">Multiple Answer</option>
                     <option value="multiple-choice-multiple-answer">Multi-Choice-Multi-Answer</option>
                     <option value="matching">Matching</option>
                     <option value="auto-generated">Auto-Generated</option>
                     <option value="graded-question">Graded Question</option>
                  </select>
            </tr>
         </table>
	 <input type="button" id="add-question" value="Add A Question"></input> 
	</form>
</body>
<script>
   var button = document.getElementById("add-question");
   button.addEventListener("click", function() {
	   	var type = document.getElementById("question-type");
	   	var value = type.options[type.selectedIndex].value;
	   	if(value == 0) {
	   		alert("Please choose a question type");
	   	} else if(value =="question-answer") {
	   		window.location = "/Quizness/quiz/questionCreation/question-answer.jsp?question-type=1";
	   	} else if(value == "picture-response")  {
	   		window.location = "/Quizness/quiz/questionCreation/picture-response.jsp?question-type=4";
	   	} else if(value == "multiple-answer"){
	   		window.location = "/Quizness/quiz/questionCreation/question-answer.jsp?question-type=5";
	   	} else if (value == "multiple-choice") {
	   		window.location = "/Quizness/quiz/questionCreation/multiple-choice.jsp?question-type=3";
	   	} else if(value == "multiple-choice-multiple-answer") {
	   		window.location = "/Quizness/quiz/questionCreation/multiple-choice.jsp?question-type=6";
	   	} else {
	   		window.location = "/Quizness/quiz/questionCreation/" + value + ".jsp";
	   	}
	   	});
   
   function addTitle(){
	   var field = document.createElement('input');
	   field.setAttribute("id", "new-title");
	   document.getElementById('title-edit').appendChild(field);
	}
   function addDescription(){
	   var field = document.createElement('input');
	   field.setAttribute("id", "new-description");
	   document.getElementById('description-edit').appendChild(field);
	}
</script>