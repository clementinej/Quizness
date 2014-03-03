<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*" import="java.util.*" %>
<head>
   <link rel="stylesheet" type="text/css" href="../../css/style_login.css" />
</head>
<body>
<%
// Get type-specific question object
	int quizID = Integer.parseInt(request.getParameter("quiz_id"));
	Quiz currQuiz = Quiz.getQuiz(quizID); 
	Question question = currQuiz.nextQuestion();
	String questionText = question.getQuestion();
	//print questionText
	ArrayList<Set<String>> answers = question.getAnswer();
	int numBlanks = answers.size();
	for(int i = 0; i < numBlanks; i++) {
		// "options for blank # [blank]"
		for (String answer: answers.get(i)) {
			//print checkbox for answer
		}
	}
%>
   <div class="container">
   <form method="post" action="../CreateServlet" id="signup">
      <div class="header">
         <h3>Question [question number]</h3>
         <h2><%=currQuiz.getTitle() %></h2>
         <p>Click "next" when you're happy with your answer.</p>
      </div>
      <div class="sep"></div>
      <div class="inputs">
 		<p><%=questionText %></p><br>
		<p>Solution Text</p>
		<button id="next">Next Question</button>
      </div>
      </form>
   </div>
</body>
<script>
var button = document.getElementById("next");
button.addEventListener("click", function() {
	var type = document.getElementById("question-type"); //somehow get the type of the next question
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