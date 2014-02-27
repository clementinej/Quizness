<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<%--
// Get type-specific question object
	int currQuizID = Integer.parseInt(request.getParameter("quiz_id"));
	Quiz currQuiz = getQuiz(currQuizID); 
	QuestionResponse qr = (QuestionResponse)currQuiz.getNextQuestion(); //this should pop, previous call to get type should peek
	String question = qr.getQuestion();
	ArrayList<Set<String>> answers = qr.getAnswer();
	for (Set<String> a: answers) {
		//print checkbox for a
	}
--%>

<p>Question Text</p><br>
<p>Solution Text</p>
<button id="next">Next Question</button>
<select name="question-type" id="question-type">
                     <option value="0" selected></option>
                     <option value="question-answer">Question-Answer</option>
                     <option value="picture-response">Picture-Response</option>
                     <option value="multiple-answer">Multiple Answer</option>
                     <option value="fill-in-blanks">Fill-in-the-Blank</option>
                     <option value="multiple-choice">Multiple Choice</option>
                     <option value="multiple-choice-multiple-answer">Multi-Choice-Multi-Answer</option>
                     <option value="matching">Matching</option>
                     <option value="auto-generated">Auto-Generated</option>
                     <option value="graded-question">Graded Question</option>
                  </select>
</body>

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
</html>