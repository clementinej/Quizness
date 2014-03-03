<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*" %>
<head>
   <link rel="stylesheet" type="text/css" href="../css/style_login.css" />
</head>
<%
   User currUser = (User) session.getAttribute("current user");
   int currQuizID = Integer.parseInt(request.getParameter("quiz_id"));
   Quiz currQuiz = Quiz.getQuiz(currQuizID);
   if(!currUser.isAdmin() && currQuiz.getCreatorID() != currUser.getUserID()) return;
%>
<body>
   <div class="container">
   <form method="post" action="../CreateServlet" id="signup">
      <div class="header">
         <h3>Let's Get Started!</h3>
         <p>Best of luck</p>
      </div>
      <div class="sep"></div>
      <div class="inputs">
      <%
 	String quizIdAsString = request.getParameter("quiz_id");
	int quizID = Integer.parseInt(quizIdAsString);
 	Quiz currQuiz = Quiz.getQuiz(quizID);
	
	String title = currQuiz.getTitle();
	boolean timed = currQuiz.getTimed();

	Queue questions = currQuiz.getQuestions();
 	Question q = questions.peek();
 	QuestionType next_question_type = q.getType();
 	boolean multiPage = currQuiz.getDisplay();
 	if(multiPage == true) {
 		%>
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
 		<button id="start-button">Start Quiz!</button>
 		<%--
 		if(type == "multiple-choice") {
 			 redirect to question page if multipage
 	 	} else if(type == "question-answer") {
 	 		
 	 	} else if(type == "fill-in-blanks") {
 	 		
 	 	} else {
 	 		// render some error page
 	 	}
 		
 	} else {
		get question type, insert html snippet that displays correct type
 	}
 	// assume multipage for now
 
	 --%>
      </div>
      </form>
   </div>
</body>
<script>
   var button = document.getElementById("add-question");
   button.addEventListener("click", function() {
   	var type = document.getElementById("question-type");
   	var value = type.options[type.selectedIndex].value;
   	if(value == 0) {
   		alert("Please choose a question type");
   	} else if(value =="question-answer" || value == "picture-response" || value == "multiple-answer"){
   		window.location = "/Quizness/quiz/questionCreation/question-answer.jsp";
   	} else if (value == "multiple-choice" || value == "multiple-choice-multiple-answer") {
   		window.location = "/Quizness/quiz/questionCreation/multiple-choice.jsp";
   	} else {
   		window.location = "/Quizness/quiz/questionCreation/" + value + ".jsp";
   	}
   	});
</script>