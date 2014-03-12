<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*, java.util.*" %>
<%--<%@ page errorPage="../site/404.jsp" %>--%>
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
    <form method="post" id="signup"> 
      <%if(request.getParameter("saved") != null) %> <h5>Title and Description Saved Successfully!</h5>
      <%if(request.getParameter("error") != null) %> <h5>Whoops! Make sure you have a title and some questions before submitting!</h5>
      <%String title = (String)session.getAttribute("title");%>
          <input type="name" name="title" value="<%=title%>" placeholder="Title" autofocus/>
         </div>
         <div><br>
      <%String description = (String)session.getAttribute("description");%>
          <input type="name" name="description" value="<%=description%>" placeholder="Description" autofocus/>
         </div>

         <input type="submit" name="save_title_and_description" value="Save Title And Description"/>
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
         </form>
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
        		int type = q.getQuestionType();
         		String redirectTo = "";
        		switch(type) {
        		case 1://question-response
        			redirectTo = "quiz/questionEditing/edit-question-answer.jsp?question_index="+i;
        			break;
        		case 2://fill-in-the-blank
        			redirectTo = "quiz/questionEditing/edit-fill-in-blanks.jsp?question_index="+i;
        			break;
        		case 3://multiple choice
        			redirectTo = "quiz/questionEditing/edit-multiple-choice.jsp?question_index="+i;
        			break;
        		case 4://picture response
        			redirectTo = "quiz/questionEditing/edit-picture-response.jsp?question_index="+i;
        			break;
        		}
         %>
         <br>
         	<label class="terms"><%=i + 1%>. "<%=text%>"</label>
         	<p><a href="<%=redirectTo%>">Edit Question</a></p>
         	
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