<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*" %>
<%@page import="java.util.*" %>
<%@page import="java.io.*" %>
<%@ page errorPage="../site/404.jsp" %>
<head>
   <link rel="stylesheet" type="text/css" href="../css/style_login.css" />
</head>
<% 
	checkIfUserIsLoggedIn(request, response);
%>

<% boolean debug = false;%>

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
  	int currQuizID = 79;//debugging default
    if(!debug) {
   	  currQuizID = Integer.parseInt(request.getParameter("quiz-id"));
    }
	Quiz currQuiz = getCurrQuiz(currQuizID);
  	boolean multiPage = currQuiz.hasMultiplePages();
 	 	
 	 	
 	//--------------SINGLE PAGE DISPLAY SECTION----------------//
 	 	
 	/**
 	 * The single-page implementation creates a new QuizTry, and sets it in the database.  It then goes on
 	 * to print out the title, description, and all questions.
 	 */
    if(!multiPage) { 
    	System.out.println("single-page");
 		QuizTry qTry = new QuizTry(currUser.getUserID(), currQuizID, currQuiz.hasPracticeMode(), currQuiz.hasRandomMode());
  		int tryID = ServerConnection.addQuizTry(qTry);
		%>
		<h4><%=currQuiz.getTitle()%> </h4>
		<p><%=currQuiz.getDescription()%></p>
   	    <input type="hidden" name="quiz try id" value="<%=qTry.getTryID()%>"/>
		<%
		ArrayList<Question> questions = currQuiz.getQuestions();
   	    		
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
		qTry.setToDone();
	  	ServerConnection.updateQuizTry(qTry);%> 
	 <input type="submit" name="submit" value="Grade Me!"/><%
	  
		
	  	
	  	
		  //----------------------MULTPAGE SECTION---------------------------//
      } else {
    	System.out.println("Multipage");
  		int quizTryID = Integer.parseInt(request.getParameter("quiz try id"));
	 	QuizTry qTry = ServerConnection.getQuizTry(quizTryID);
   	 		
  		//if quiz is done, go to results
   		if(!qTry.hasNext()) {
   			qTry.setToDone();
   			RequestDispatcher dispatch = request.getRequestDispatcher("results.jsp"); 
   			dispatch.forward(request, response);
   			return;
   		}
    	 		
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
			<p><input type="text" name="answer<%=qIndex%>" /></p>
			<input type="submit" name="submit" value="next"/>
			<%--<jsp:include page="questionGeneration/show-question-answer.jsp" />--%><%
			break;
		case 2:
			int blankIndex = questionText.indexOf('_');
			int lastBlankIndex = questionText.lastIndexOf('_');
			String beforeBlank = questionText.substring(0, blankIndex);
			String afterBlank = questionText.substring(lastBlankIndex + 1);
			%>
			<%=beforeBlank%><input type="text" name="answer<%=qIndex%>"/><%=afterBlank%></p>
			<input type="submit" name="submit" value="next"/>
			<%--<jsp:include page="questionGeneration/show-fill-in-blanks.jsp" />--%><%
			break;
		case 3:
			//get wrong choices
			//mix answer in wrong choices
			ArrayList<String> options = new ArrayList<String>();
			options = getOptions(q);			
			%>
			<%=questionText%></p>
				<%
			for(String option : options) {
				%>
				<div class="checkboxy">
	        	<input type="checkbox" name="answer<%=qIndex%>" value="<%=option %>"> <%=option %></div>
       			<%
			}
			%>
			<input type="submit" name="submit" value="next"/>
			<%--<jsp:include page="questionGeneration/show-multiple-choice.jsp" />--%><%
			break;
		case 4:
			%>
			<img src="<%=questionText%>" height="300" width="300">
			<p><input type="text" name="answer<%=qIndex%>" /></p>
			<input type="submit" name="submit" value="next"/>
			<%--<jsp:include page="questionGeneration/show-picture-response.jsp" />--%><%
			break;
		}
      }  	 	
 	%>
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


<%! 
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
