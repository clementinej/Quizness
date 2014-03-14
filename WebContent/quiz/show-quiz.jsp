<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*" %>
<%@page import="java.util.*" %>
<%@page import="java.io.*" %>
<%@ page errorPage="../site/404.jsp"%>
<head>
   <link rel="stylesheet" type="text/css" href="/Quizness/css/style_login.css" />
</head>
<% 
	checkIfUserIsLoggedIn(request, response);
%>

<% boolean debug = false;%>
<%--
url parameters:
for multipage
quiz_id
quiz try id
answer

 --%>


<%
    User user = (User) session.getAttribute("current user");
  	int quizID = 79;//debugging default
    if(!debug) {
   	  quizID = Integer.parseInt(request.getParameter("quiz_id"));
    }
	Quiz quiz = getCurrQuiz(quizID);
  	boolean multiPage = quiz.hasMultiplePages(); 	
 	 	
 	//--------------SINGLE PAGE DISPLAY SECTION----------------//
 	 	
 	/**
 	 * The single-page implementation creates a new QuizTry, and sets it in the database.  It then goes on
 	 * to print out the title, description, and all questions.
 	 */
    if(!multiPage) { 
    	System.out.println("single-page");
 		QuizTry qTry = new QuizTry(user.getUserID(), quizID, quiz.hasPracticeMode(), quiz.hasRandomMode());
  		int tryID = ServerConnection.addQuizTry(qTry);
		%>
	<body>
  	  <div class="container">
  		 <form method="post" action="results.jsp" id="signup">
         <div class="header">
         	<h3>Let's Get Started!</h3>
        	<p>Best of luck</p>
         </div>
    	 <div class="sep"></div>
     	 <div class="inputs">
		<h4><%=quiz.getTitle()%> </h4>
		<p><%=quiz.getDescription()%></p>
   	    <input type="hidden" name="quiz try id" value="<%=qTry.getTryID()%>"/>
		<%
		ArrayList<Question> questions = quiz.getQuestions();//gets random questions
   	    		
   	    /*
   	     * Print out all questions
   	     */
		for(int qIndex = 0; qIndex < questions.size(); qIndex++) {	
			%><br>
		   	<input type="hidden" name="question index" value=<%=qIndex%>/>
			<p><%=qIndex + 1%>.<%
			Question q = qTry.getNextQuestion();
			switch(q.getQuestionType()) {
			case 1: //Question Response
				%>
				<%=q.getQuestion()%></p>
				<p><input type="text" name="answer<%=qIndex%>" /></p>
				<%--<jsp:include page="questionGeneration/show-question-answer.jsp" />--%><%
				break;
			case 2: //Fill-In-The-Blank
				int blankIndex = q.getQuestion().indexOf('_');
				int lastBlankIndex = q.getQuestion().lastIndexOf('_');
				String beforeBlank = q.getQuestion().substring(0, blankIndex);
				String afterBlank = q.getQuestion().substring(lastBlankIndex + 1);
				%>
				<%=beforeBlank%><input type="text" name="answer<%=qIndex%>"/><%=afterBlank%></p>
				<%--<jsp:include page="questionGeneration/show-fill-in-blanks.jsp" />--%><%
				break;
			case 3:	//Multiple Choice				
				ArrayList<String> options = new ArrayList<String>();
				options = getOptions(q);				
				%>
				<%=q.getQuestion()%></p>
					<%
					for(String option : options) {
					%>
				<div class="checkboxy">
	            <input type="checkbox" name="answer<%=qIndex%>" value="<%=option %>"> <%=option %></div>
       				<%
					}
					%>
				<%--<jsp:include page="questionGeneration/show-multiple-choice.jsp" />--%><%
				break;
			case 4: //Picture Response%>
				<img src="<%=q.getQuestion()%>" height="300" width="300">
				<p><input type="text" name="answer<%=qIndex%>" /></p>
				<%break;
			}
		}	
		qTry.setToDone();%> 
	 <input type="submit" class="blue-button" name="submit" value="Grade Me!"/>
	 </div>
	 </form>
	  <%
	  	
	  	
		  //----------------------MULTPAGE SECTION---------------------------//
      } else {%>
      <div class="container">
      <form method="post" action="show-quiz.jsp" id="signup"><%
    	QuizTry qTry = null;
    	int quizTryID = -1;
    	if(request.getParameter("quiz try id") != null) {
  			quizTryID = Integer.parseInt(request.getParameter("quiz try id"));
		 	qTry = ServerConnection.getQuizTry(quizTryID);
    	} else {
     		qTry = new QuizTry(user.getUserID(), quizID, quiz.hasPracticeMode(), quiz.hasRandomMode());
      		quizTryID = ServerConnection.addQuizTry(qTry);
    	}
%>
        <div class="header">
        <input type="hidden" name="quiz_id" value="<%=quizID %>"/>
         	<h3>Let's Get Started!</h3>
        	<p>Best of luck</p>
        </div>
    	<div class="sep"></div>
     	<div class="inputs">
	    <h4><%=quiz.getTitle()%> </h4>
		<p><%=quiz.getDescription()%></p><%
		System.out.println("Multipage");
		
		
    	
    	if(request.getParameter("answer") != null) {
  			String previousAnswer = request.getParameter("answer");
  			//for questions which only require one answer from the user only
  			String [] array = new String[1];
  			array[0] = previousAnswer;
		 	ArrayList<String[]> responses = (ArrayList<String[]>) session.getAttribute("ready responses");
		 	responses.add(array);
		 	
		 	//checkCurrentResponses
		 	for(String[] one : responses) {
		 		System.out.println("Current Answers: " + one[0]);
		 	}
		 	session.setAttribute("ready responses", responses);
		 	
			if(quiz.hasImmediateCorrection()) { 
				int lastQuestionIndex = qTry.getQuestionNum() - 1;
				ArrayList<Question> questionList = qTry.getQuestions();
				Question question = questionList.get(lastQuestionIndex);
				double pointsFromLastAnswer = question.getPoints(array);
				if(pointsFromLastAnswer > 0) {
					System.out.println("IC: Correct!");
					QuestionHandler.forwardToCorrectPage(quizID, quizTryID, request, response);
					return;
				} else {
					System.out.println("IC: Incorrect! :(");
					QuestionHandler.forwardToIncorrectPage(quizID, quizTryID, request, response);
					return;
				}
	  		}
	
    	}
    	
  		//if quiz is done, go to results
   		if(!qTry.hasNext()) {
   			System.out.println("done");
   			qTry.setToDone();%>
   			<input type="hidden" name="multi-page" value="signal"/>
   			<%RequestDispatcher dispatch = request.getRequestDispatcher("results.jsp?multi-page=signal"); 
   			dispatch.forward(request, response);
   			return;
   		}
    	
		int qIndex = qTry.getQuestionNum();   	 	
   	 	Question q = qTry.getNextQuestion();
   	 	
		int nextQuestionType = q.getQuestionType();
		switch(q.getQuestionType()) {
		case 1: %>
			<p>Question <%=" " + q.getQuestion()%></p>
			<p><input type="name" name="answer" /></p>
			<input type="submit" class="blue-button" name="submit" value="next"/>
			<%--<jsp:include page="questionGeneration/show-question-answer.jsp" />--%><%
			break;
		case 2:
			int blankIndex = q.getQuestion().indexOf('_');
			int lastBlankIndex = q.getQuestion().lastIndexOf('_');
			String beforeBlank = q.getQuestion().substring(0, blankIndex);
			String afterBlank = q.getQuestion().substring(lastBlankIndex + 1);%>
			<%=beforeBlank%><input type="text" name="answer"/><%=afterBlank%></p>
			<input type="submit" class="blue-button" name="submit" value="next"/>
			<%--<jsp:include page="questionGeneration/show-fill-in-blanks.jsp" />--%><%
			break;
		case 3:
			//get wrong choices
			//mix answer in wrong choices
			ArrayList<String> options = new ArrayList<String>();
			options = getOptions(q);			
			%>
			<%=q.getQuestion()%></p><%
			for(String option : options) {%>
				<div class="checkboxy">
	        	<input type="checkbox" name="answer" value="<%=option %>"> <%=option %></div>
       		<%}%>
			<input type="submit" class="blue-button" name="submit" value="next"/>
			<%--<jsp:include page="questionGeneration/show-multiple-choice.jsp" />--%><%
			break;
		case 4:
			%>
			<img src="<%=q.getQuestion()%>" height="300" width="300">
			<p><input type="text" name="answer" /></p>
			<input type="submit" class="blue-button" name="submit" value="next"/>
			<%--<jsp:include page="questionGeneration/show-picture-response.jsp" />--%><%
			break;
		}%>
	   	<input type="hidden" name="quiz try id" value="<%=quizTryID%>"/>
	   	<input type="hidden" name="quiz-id" value="<%=quizID%>"/>	
     <%}%>
 	
 	</div>
    </form>
      </div>
    
</body>


<%-- Private Functions --%>
<%!
private void checkIfUserIsLoggedIn(HttpServletRequest request,
	HttpServletResponse response) throws ServletException, IOException {
	HttpSession session = request.getSession();
	User currUser = (User) session.getAttribute("current user");
	if(currUser == null) {
		RequestDispatcher dispatch = request.getRequestDispatcher("/Quizness/LoginServlet"); 
		dispatch.forward(request, response); 	
	}
}
%>
<%!
/* 
 * Grabs the current quiz given a quizID
 */
Quiz getCurrQuiz(int quizID) throws IOException {
	Quiz quiz = null;
	try {
		quiz = Quiz.getQuiz(quizID); 
	} catch (Exception e) {
		e.printStackTrace();
	}
	return quiz;
}	
%>


<%! 
/*
 * For multiple choice questions.  Gets all options that will appear next to the check boxes and puts them
 * in an ArrayList<String>.
 */
private ArrayList<String> getOptions(Question q) {
	ArrayList<String> options = new ArrayList<String>();
	MultipleChoice mc = (MultipleChoice) q;
	ArrayList<Set<String>> answer = q.getAnswer();
	Iterator<String> iter = answer.get(0).iterator();
	String correctAnswer = iter.next();
	System.out.println(correctAnswer);
	System.out.println(mc.getAnswer());
	String [] wrongChoices = mc.getChoices();
	System.out.println(wrongChoices);
	
	for(int c = 0; c < wrongChoices.length; c++) {
		options.add(wrongChoices[c]);
	}
	Random generator = new Random();
	int r = generator.nextInt(wrongChoices.length + 1);
	options.add(r, correctAnswer);
	
	return options;
}
%>


