<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*" %>
<%@page import="java.util.*" %>
<%@page import="java.io.IOException" %>
<head>
   <link rel="stylesheet" type="text/css" href="../css/style_login.css" />
</head>
<% 
	checkIfUserIsLoggedIn(request, response);
%>

<% boolean debug = true;%>

<body>
   <div class="container">
   <form method="post" action="results.jsp" id="signup">
      <div class="header">
         <h3>Let's Get Started!</h3>
         <p>Best of luck</p>
      </div>
      <div class="sep"></div>
      <div class="inputs">

      <%
      
   	  	User currUser = (User) session.getAttribute("current user");
    	int currQuizID = 49;//debugging default
 	    if(!debug) {
    	  currQuizID = Integer.parseInt(request.getParameter("quiz_id"));
  	    }
		Quiz currQuiz = getCurrQuiz(currQuizID);
 	 	boolean multiPage = currQuiz.hasMultiplePages();
   	 	  if(!multiPage) { 		 	
  			QuizTry qTry = new QuizTry(currUser.getUserID(), currQuizID, currQuiz.hasPracticeMode(), currQuiz.hasRandomMode());
  			int tryID = ServerConnection.addQuizTry(qTry);
			%>
			<h4><%=currQuiz.getTitle()%> </h4>
			<p><%=currQuiz.getDescription()%></p>
	   	   <input type="hidden" name="quiz try id" value="<%=qTry.getTryID()%>"/>
			<%
			ArrayList<Question> questions = currQuiz.getQuestions();			
			for(int qIndex = 0; qIndex < questions.size(); qIndex++) {	
				//get question materials
				%><br><%
				Question q = questions.get(qIndex);
				int nextQuestionType = q.getQuestionType();
				String questionText = q.getQuestion(); 
				ArrayList<Set<String>> answers = q.getAnswer();
				System.out.println("next question is: "+nextQuestionType);
			   	%>
			   	<input type="hidden" name="question index" value=<%=qIndex%>/>
			   	<%
				//Print out question number
				int num = qIndex + 1;
				%><p><%=num%>.<%	
				switch(nextQuestionType) {
				case 1: 
					%>
					<%=questionText%></p>
					<p><input type="text" name="answer<%=qIndex%>" /></p>
					<%--<jsp:include page="questionGeneration/show-question-answer.jsp" />--%><%
					break;
				case 2:
					int blankIndex = questionText.indexOf('_');
					int lastBlankIndex = questionText.lastIndexOf('_');
					String beforeBlank = questionText.substring(0, blankIndex);
					String afterBlank = questionText.substring(lastBlankIndex + 1);
					%>
					<%=beforeBlank%><input type="text" name="answer<%=qIndex%>"/><%=afterBlank%></p>
					<%--<jsp:include page="questionGeneration/show-fill-in-blanks.jsp" />--%><%
					break;
				case 3:
					//get wrong choices
					//mix answer in wrong choices
					MultipleChoice mc = (MultipleChoice) q;
					ArrayList<Set<String>> answer = q.getAnswer();
					Iterator<String> iter = answer.get(0).iterator();
					String correctAnswer = iter.next();
					System.out.println(correctAnswer);
					System.out.println(mc.getAnswer());
					String [] wrongChoices = mc.getChoices();
					System.out.println(wrongChoices);
					
					ArrayList<String> options = new ArrayList<String>();
					for(int c = 0; c < wrongChoices.length; c++) {
						options.add(wrongChoices[c]);
					}
					Random generator = new Random();
					int r = generator.nextInt(wrongChoices.length + 1);
					options.add(r, correctAnswer);					
					%>
					<%=questionText%></p>
						<%
						for(String option : options) {
						%>
					<div class="checkboxy">
		            <input name="checky" type="checkbox" name="multiple_pages" value="1"> <%=option %></div>
	       				<%
						}
						%>
					<%--<jsp:include page="questionGeneration/show-multiple-choice.jsp" />--%><%
					break;
				case 4:
					%>
					<img src="<%=questionText%>" height="300" width="300">
					<p><input type="text" name="answer<%=qIndex%>" /></p>
					<%--<jsp:include page="questionGeneration/show-picture-response.jsp" />--%><%
					break;
				}
			}	
			qTry.setToDone();
		  	ServerConnection.updateQuizTry(qTry);
		  
		  
		  
		  
		  
		  
		  //--------------multipage-------------
		  
		  
		  
		  
   	 	  } else {
   	 		int quizTryID = Integer.parseInt(request.getParameter("quiz try id"));
   	 		QuizTry qTry = ServerConnection.getQuizTry(quizTryID);
   	 		ArrayList<Question> questions = currQuiz.getQuestions();
   	 		
   	 		int qIndex = qTry.getQuestionNum();
   	 		Question q = questions.get(qIndex);
   	 				
			int nextQuestionType = q.getQuestionType();
			String questionText = q.getQuestion(); 
			ArrayList<Set<String>> answers = q.getAnswer();
			switch(nextQuestionType) {
			case 1: 
				%>
				<%=questionText%></p>
				<p><input type="text" name="answer" /></p>
				<input type="submit" name="submit" value="next"/>
				<%--<jsp:include page="questionGeneration/show-question-answer.jsp" />--%><%
				break;
			case 2:
				int blankIndex = questionText.indexOf('_');
				int lastBlankIndex = questionText.lastIndexOf('_');
				String beforeBlank = questionText.substring(0, blankIndex);
				String afterBlank = questionText.substring(lastBlankIndex + 1);
				%>
				<%=beforeBlank%><input type="text" name="answer"/><%=afterBlank%></p>
				<input type="submit" name="submit" value="next"/>
				<%--<jsp:include page="questionGeneration/show-fill-in-blanks.jsp" />--%><%
				break;
			case 3:
				//get wrong choices
				//mix answer in wrong choices
				MultipleChoice mc = (MultipleChoice) q;
				ArrayList<Set<String>> answer = q.getAnswer();
				Iterator<String> iter = answer.get(0).iterator();
				String correctAnswer = iter.next();
				System.out.println(correctAnswer);
				System.out.println(mc.getAnswer());
				String [] wrongChoices = mc.getChoices();
				System.out.println(wrongChoices);
				
				ArrayList<String> options = new ArrayList<String>();
				for(int c = 0; c < wrongChoices.length; c++) {
					options.add(wrongChoices[c]);
					}
				Random generator = new Random();
				int r = generator.nextInt(wrongChoices.length + 1);
				options.add(r, correctAnswer);					
				%>
				<%=questionText%></p>
					<%
				for(String option : options) {
					%>
				<div class="checkboxy">
	            <input name="checky" type="checkbox" name="multiple_pages" value="1"> <%=option %></div>
       				<%
					}
					%>
					<input type="submit" name="submit" value="next"/>
				<%--<jsp:include page="questionGeneration/show-multiple-choice.jsp" />--%><%
				break;
			case 4:
				%>
				<img src="<%=questionText%>" height="300" width="300">
				<p><input type="text" name="answer" /></p>
				<input type="submit" name="submit" value="next"/>
				<%--<jsp:include page="questionGeneration/show-picture-response.jsp" />--%><%
				break;
			}
   	   }
   	 	
 		%>
 		<input type="submit" name="submit" value="Grade Me!"/>

      </div>
      </form>
   </div>
</body>

<%!
private void checkIfUserIsLoggedIn(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
	HttpSession session = request.getSession();
	User currUser = (User) session.getAttribute("current user");
	if(currUser == null) {
		RequestDispatcher dispatch = request.getRequestDispatcher("../../LoginServlet"); 
		dispatch.forward(request, response); 	
	}
}
%>
<%!
Quiz getCurrQuiz(int currQuizID) throws IOException {
	Quiz currQuiz = null;
	try {
		currQuiz = Quiz.getQuiz(currQuizID); 
	} catch (Exception e) {
		e.printStackTrace();
	}
	return currQuiz;
}	
%>

 			<%-- 
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
 			--%>
 			
 <%--
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
 --%>
 			
