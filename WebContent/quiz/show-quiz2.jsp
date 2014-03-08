<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*" %>
<%@page import="java.util.*" %>
<head>
   <link rel="stylesheet" type="text/css" href="../css/style_login.css" />
</head>
<%
	//coming from some link where the quiz id is passed in as a param.
	//gets the current user, gets current quiz ID.
	
   User currUser = (User) session.getAttribute("current user");
   int currQuizID = Integer.parseInt(request.getParameter("quiz_id"));
   Quiz currQuiz = Quiz.getQuiz(currQuizID);

   
   if(currUser == null  || currQuiz.getCreatorID() == currUser.getUserID()) return;
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
	String title = currQuiz.getTitle();
	String description = currQuiz.getDescription();
	//boolean timed = currQuiz.getTimed();//extension to worry about later

	ArrayList<Question> questions = currQuiz.getQuestions();
	for(int i = 0; i < questions.size(); i++) {
 		Question q = questions.get(i);
 		String next_question_type = q.getQuestionType();
 		boolean multiPage = currQuiz.hasMultiplePages();
 		if(multiPage == true) {
 				
 				
 				
 				
 				
 				
 				
 				
 				
 		} else {
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
 		<%
 			if(type == "multiple-choice") {
 		 		//redirect to question page if multipage
 	 		} else if(type == "question-answer") {
 	 			//redirect to question-answer
 		 	} else if(type == "fill-in-blanks") {
 				//redirect to fill-in-blanks
 	 		} else {//add the rest
 	 			// render some error page
	 		}
 		}
	}
 	// assume multipage for now
 
	 %>
      </div>
      </form>
   </div>
</body>
<script>
	//what?
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