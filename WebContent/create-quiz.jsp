<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*, java.util.*" %>
<%@ page errorPage="../site/404.jsp" %>
<head>
   <link rel="stylesheet" type="text/css" href="css/style_login.css" />
</head>
<body>
   <div class="container">
   <form method="post" action="CreateServlet" id="signup">
      <div class="header">
         <h3>Create a Quiz!</h3>
         <p>Stump brains, change lives.</p>
      </div>
      <div class="sep"></div>
      <div class="inputs">
         <div id="quiz_title" class="terms">
            <input type="name" name="title" placeholder="Title" autofocus/>
         </div>
         <div>
            <label class="terms" style="padding:0px 25px;">Category</label>
            <select id="quiz-category">
               <option value="general">General</option>
               <option value="science">Science and Technology</option>
               <option value="trivia">Trivia</option>
               <option value="social">Social</option>
               <option value="educational">Educational</option>
            </select>
         </div>
         <div><br>
            <input type="name" name="description" style="width:500px;height:50px" placeholder="Description of your quiz"></input>
         </div>
         <div class="checkboxy">
            <input type="checkbox" name="selected" value="multiple_pages"><label class="terms">Show quiz on multiple pages?</label>
         </div>
         <div class="checkboxy">
            <input type="checkbox" name="selected" value="random_questions"><label class="terms">Display questions in random order?</label>
         </div>
         <div class="checkboxy">
            <input type="checkbox" name="selected" value="immediate_correction"><label class="terms">Display answers immediately?</label>
         </div>
         <br>
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
         <br><input type="button" id="add-question" value="Add A Question"></input> <!-- Forward to type-specific creation template -->
         <br><input id="submit" type="submit" value="Create Quiz!"><!-- Store new quiz in database -->
         <!-- Question section -->
         <div style="margin-top:20px;">
         <h3>Questions</h3>
         <%
         ArrayList<Question> questionList = (ArrayList<Question>) session.getAttribute("question list");	
         if(questionList.size() == 0) {
         	%>
         	<label class="terms">You haven't written any questions yet!</label>
         	<%
         } else {
         	for(int i = 0; i < questionList.size(); i++) {
         		Question q = questionList.get(i);
         		String text = q.getQuestion();
         %>
         <br>
         	<label class="terms"><%=i + 1%>. "<%=text%>"</label>
         	<% 
         	//for each solution
         	for(int solIndex = 0; solIndex < questionList.size(); solIndex++) {
         	%>
         	<br><%
         	}
         }
      }
     %>
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
	   	} else if(value =="question-answer") {
	   		window.location = "/Quizness/quiz/questionCreation/question-answer.jsp?question-type=1&intent=add question";
	   	} else if(value == "picture-response")  {
	   		window.location = "/Quizness/quiz/questionCreation/picture-response.jsp?question-type=4&intent=add question";
	   	} else if(value == "multiple-answer"){
	   		window.location = "/Quizness/quiz/questionCreation/question-answer.jsp?question-type=5&intent=add question";
	   	} else if (value == "multiple-choice") {
	   		window.location = "/Quizness/quiz/questionCreation/multiple-choice.jsp?question-type=3&intent=add question";
	   	} else if(value == "multiple-choice-multiple-answer") {
	   		window.location = "/Quizness/quiz/questionCreation/multiple-choice.jsp?question-type=6&intent=add question";
	   	} else {
	   		window.location = "/Quizness/quiz/questionCreation/" + value + ".jsp?question-type=2&intent=add question";
	   	}
	   	});
</script>