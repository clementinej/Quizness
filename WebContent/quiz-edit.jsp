<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="model.*, control.*, java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head> 
   <link rel="stylesheet" type="text/css" href="css/style_login.css" />

</head>
<%
//Login.notLoggedIn(request,response);
boolean debug = false;
User user = (User) session.getAttribute("current user");
Quiz quiz = Quiz.getQuiz(79);
if(!debug) {
	quiz = Quiz.getQuiz(Integer.parseInt(request.getParameter("quiz_id")));
}
if(!user.isAdmin() && quiz.getCreatorID() != user.getUserID()) return;
ArrayList<Question> questions = quiz.getNonRandomQuestions();
%>

<body>

<div class="container">

	<div id="quizedit">
	<h1>Edit Quiz Info</h1>
	
	<form action="EditServlet" method="post" id="title-edit">
	Title
	<p><input name="title" type="name" value="<%=quiz.getTitle() %>"/></p>		
	
	<br>

	Description
	<p><input type="name" name="description" value="<%=quiz.getDescription() %>"/></p>
	<input id="save-button" type="submit" value="Save Quiz Info" name="quiz_info"/>
	<input type="hidden" name="quiz_id" type="name" value="<%=quiz.getQuizID()%>"/>
	</form>
	</div>

	<form method="post" action="quiz/edit/EditQuestion" id="questionedit">
	<input type="hidden" name="quiz_id" value="<%=quiz.getQuizID()%>"/>
	<h2>Edit Questions</h2>
	<%
	for(int i = 0; i < questions.size(); i++) {
		Question q = questions.get(i);
		int type = q.getQuestionType();
		System.out.println(i);
		String redirectTo = "";
		switch(type) {
		case 1://question-response
			redirectTo = "quiz/questionEditing/edit-question-answer.jsp?question_index="+i+"&quiz_id="+quiz.getQuizID();
			break;
		case 2://fill-in-the-blank
			redirectTo = "quiz/questionEditing/edit-fill-in-blanks.jsp?question_index="+i+"&quiz_id="+quiz.getQuizID();
			break;
		case 3://multiple choice
			redirectTo = "quiz/questionEditing/edit-multiple-choice.jsp?question_index="+i+"&quiz_id="+quiz.getQuizID();
			break;
		case 4://picture response
			redirectTo = "quiz/questionEditing/edit-picture-response.jsp?question_index="+i+"&quiz_id="+quiz.getQuizID();
			break;
		}
		
		String text = q.getQuestion();
	if(type != 4) {%>
	<p><%=text %></p>
	<%} else {%>
	<p><img src="<%=text %>" height="100" width="100"></p>
	<%}%>
		<a href="<%=redirectTo%>">Edit Question</a>
		<br>
	<%}%>
	

	</form>

	<div>
	<form method="post" action="CreateServlet" id="addquestion">
	  <table>
            <tr>
               <th>
               		<h2>Add Questions</h2>
                  <input type="hidden" name="quiz_id" value="<%=quiz.getQuizID()%>"/>
                  <input type="hidden" name="intent" value="add to existing quiz"/>
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
         <div id="add-question-button">
	 <input type="button" id="add-question" value="Add A Question"></input>
	 </div> 
	</form>

	</div>
	</div>
	</div>
</body>
<script>

   var button = document.getElementById("add-question");
   button.addEventListener("click", function() {
	   	var type = document.getElementById("question-type");
	   	var value = type.options[type.selectedIndex].value;
	   	if(value == 0) {
	   		alert("Please choose a question type");
	   	} else if(value =="question-answer") {
	   		window.location = "/Quizness/quiz/questionCreation/question-answer.jsp?intent=add to existing quiz&quizID="+<%=quiz.getQuizID()%>;
	   	} else if(value == "picture-response")  {
	   		window.location = "/Quizness/quiz/questionCreation/picture-response.jsp?intent=add to existing quiz&quizID="+<%=quiz.getQuizID()%>;
	   	} else if(value == "multiple-answer"){
	   		window.location = "/Quizness/quiz/questionCreation/question-answer.jsp?question-type=5&intent=add to existing quiz";
	   	} else if (value == "multiple-choice") {
	   		window.location = "/Quizness/quiz/questionCreation/multiple-choice.jsp?intent=add to existing quiz&quizID="+<%=quiz.getQuizID()%>;
	   	} else if(value == "multiple-choice-multiple-answer") {
	   		window.location = "/Quizness/quiz/questionCreation/multiple-choice.jsp?question-type=6&intent=add to existing quiz&quizID="+<%=quiz.getQuizID()%>;
	   	} else {
	   		window.location = "/Quizness/quiz/questionCreation/" + value + ".jsp?intent=add to existing quiz&quizID="+<%=quiz.getQuizID()%>;
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
	
</body>
