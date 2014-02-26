<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*" %>
<%
User currUser = (User) session.getAttribute("currentUser");//user ID should be
//int currQuizID = Integer.parseInt(request.getParameter("quiz_id"));
//Quiz currtQuiz = Quiz.getQuiz(currQuizID);
//if(!currUser.isAdmin() && currQuiz.getUserID() != currUser.getUserID()) return;
%>

<body>
	<form method="post" action="quiz/servlet/CreateServlet">
		<table cellspacing="3" cellpadding="3" border="0">
			<tr>
				<th>Quiz Title</th>
				<td><input name="name" type="text" size="60" value="" /></td>
			</tr>
			<tr>
			<tr>Category</tr>
					<select name="quiz-category">
						<option value="general">General</option>
						<option value="science">Science and Technology</option>
						<option value="trivia">Trivia</option>
						<option value="social">Social</option>
						<option value="educational">Educational</option>
					</select>
					</tr>
			<tr>
				<th>Description</th>
				<td><textarea name="description" style="width:500px;height:150px"></textarea></td>
			</tr>
			<tr>
				<th>Multiple Pages</th>
				<td><input type="checkbox" name="multiple_pages" value="1"> Many pages.</td>
			</tr>
			<tr>
				<th>Random Questions</th>
				<td><input type="checkbox" name="random_questions" value="1"> Random order.</td>
			</tr>
			<tr>
				<th>Immediate Correction</th>
				<td><input type="checkbox" name="immediate_correction" value="1"> Grade immediately.</td>
			</tr>
			<tr>
				<th><input type="hidden" name="quiz_id" value=""/>
				<input type="hidden" name="user intent" value="create quiz"/>
				<select name="question-type" id="question-type">
						<option value="0" selected></option>
								  <option value="question-answer">Question-Answer</option>
								  <option value="question-answer2">Question-Answer Test</option>
								  <option value="picture-response">Picture-Response</option>
								  <option value="multiple-answer">Multiple Answer</option>
								  <option value="fill-in-blanks">Fill-in-the-Blank</option>
								  <option value="multiple-choice">Multiple Choice</option>
								  <option value="multiple-choice-multiple-answer">Multi-Choice-Multi-Answer</option>
								  <option value="matching">Matching</option>
								  <option value="auto-generated">Auto-Generated</option>
								  <option value="graded-question">Graded Question</option>
					</select>
				<td><input type="button" id="add-question" value="Add Question"></input></td> <!-- Forward to type-specific creation template -->
				<td><input type="submit" value="Create Quiz"></td> <!-- Store new quiz in database -->
			</tr>
		</table>
		<!-- Question section -->
		<%
		//get List<Questions>
		// for q: list
		//print question text
		%>
	</form>
</body>
<script>
var button = document.getElementById("add-question");
button.addEventListener("click", function() {
	var type = document.getElementById("question-type");
	var value = type.options[type.selectedIndex].value;
	if(value == 0) {
		alert("Please choose a question type");
	} else if(value =="question-answer" || value == "picture-response" || value == "multiple-answer"){
		window.location = "../Quizness/questionCreation/question-answer.jsp";
	} else if (value == "multiple-choice" || value == "multiple-choice-multiple-answer") {
		window.location = "../Quizness/questionCreation/multiple-choice.jsp";
	} else if (value == "multiple-choice" || value == "question-answer2") {//test - Lloyd
		window.location = "../Quizness/questionCreation/question-answer2.jsp";
	} else {
		window.location = "../Quizness/questionCreation/" + value + ".jsp";
	}
	});
</script>


