<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%-- <%@ page errorPage="../site/404.jsp" %>--%>
<html lang="en">
   <head>
      <meta charset="UTF-8" />
      <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link href="../css/default.css" rel="stylesheet" type="text/css"  />
      <link href="../css/hexaflip.css" rel="stylesheet" type="text/css">
      <link href="../css/results.css" rel="stylesheet" type="text/css">
   </head>

   <%
 	  System.out.println("About to user check");
      //Get the user
      User user = (User)session.getAttribute("current user");
      System.out.println("Got Current User: " + user.getUserName());
      
      //Get the quiz try by id
      int quizTryID = Integer.parseInt(request.getParameter("quiz try id"));
  	  QuizTry quizTry = QuizTry.getTry(quizTryID);
  	System.out.println("QuizTry: "+ quizTry.getTryID());
    		  
      //Only let users see their own quiz results
      if(quizTry.getUserID() != user.getUserID() && !user.isAdmin()) {
    	  System.out.println(quizTry.getUserID() + " != "+ user.getUserID());
    	  System.out.println("results.jsp: Only let users see their own quiz results");
     	return;
      }
      //Only let users see completed quizes
      if(quizTry.isInProgress()) {
    	  System.out.println("Only let users see completed quizes");
   	  	return;
      }
      System.out.println("User Checking is good");
      %>
   <body>
      <!--top bar -->
      <div class="top">
      	<span class="header-link"><a href="../home.jsp">Home</a></span>
         <span class="header-link"><a href="../create-quiz.jsp">Create Quiz</a></span>
         <span class="header-link"><a href="../social/compose-mail.jsp">Compose </a></span>
         <span class="header-link"><a href="../social/profile.jsp">Profile</a></span>
         <span class="header-link"><a href="../site/admin.jsp">Admin</a></span>
         <span class="header-link"><a href="../inbox.jsp">Inbox</a></span>
          <span class="header-link"><a href="../site/search.jsp">Search</a></span>
      </div>
      <div class="container">
         <header class="clearfix">
         </header>
         <div class="main">
            <div id="cubes" class="demo"></div>
         </div>
         <form method="post" action="/SaveResults">
            
      <%/* GRADING
         * --------------
         * First chunk of code represents multi-page quiz grading. We get the quizResponses by grabbing
         * from request parameters and store them in the quizResponses ArrayList.  And then pass that in
         * to the quizTry to grade.
         */
         if(request.getParameter("multi-page") == null) {//single-page display
	          ArrayList<String[]> quizResponses = getQuizResponses(request);
              quizTry.gradeQuiz(quizResponses);
          } else {//multi-page-grading
          	ArrayList<String[]> readyResponses = (ArrayList<String[]>)session.getAttribute("ready responses");
           	//debugMultiplePageGrading(readyResponses);
           	quizTry.gradeQuiz(readyResponses);
           	readyResponses = new ArrayList<String[]>();
           	session.setAttribute("ready responses", readyResponses);
          }              
           
      	double score = quizTry.getScore();
        double time = quizTry.getTime()/1000;
        ArrayList<String[]> responses = quizTry.getResponses();
        int quizID = quizTry.getQuizID();
        double userAverageScore = QuizResult.getUserAverageScore(user.getUserID(), quizID);
        //double userAverageTime = QuizResult.getAverageTimeByUser(user.getUserID(), quizID);
        // int userNumTries = QuizResult.getNumTriesByUser(user.getUserID(), quizID);
        // Date userLastTry = QuizResult.getLastTryByUser(user.getUserID(), quizID);
              
        double averageScore = QuizResult.getAllAverageScore(quizID);
        //double averageTime = QuizResult.getAverageTime(quizID);
        //int numTries = History.getNumTries(quizID);
        //int numTriesToday = History.getNumTriesToday(quizID);
              
        // double friendsAverageScore = QuizResult.getFriendsAverageScore(user.getUserID(), quizID);
        //double friendsAverageTime = getFriendsAverageTime();%>
             
            <div id="score">
             <h2>You scored <%=score %></h2>
             <p>It took you <%=time %> seconds to complete this quiz</p><br>
               
            <h1>Your History</h1>
            <p>Your average score on this quiz is <%=userAverageScore%></p>
            <p>On average, this quiz takes you <%--=userAverageTime --%> seconds to complete </p>
            <p>You've taken this quiz <%--=userNumTries --%> times </p>
            <p>You last took this quiz on <%--=userLastTry --%></p>
            
            <h1>Quiz History</h1>
            <p>On average, users score <%=averageScore %> on this quiz.</p>
            <p>On average, this quiz takes users <%--=averageTime --%> seconds to complete.</p>
            <p>This quiz has been taken <%--=numTries --%> times.</p>
            <p>This quiz has been taken <%--=numTriesToday --%> times today.</p>
            </div>
         </form>
        <%--
         <%
         for(int i = 0; i < responses.size(); i++) {
         %>
         <p><%=responses %></p>
         <%} %>
          --%> 
      </div>
      <script src="../js/hexaflip.js"></script>
      <script src="../js/results.js"></script>
   </body>
</html>

<%!/*
    * gets quiz responses from request
    */
private ArrayList<String[]> getQuizResponses(HttpServletRequest request) {
    ArrayList<String[]> quizResponses = new ArrayList<String[]>();
	Map<String, String[]> map = request.getParameterMap();
    Iterator<String> iter = map.keySet().iterator();
    while(iter.hasNext()) {
    	String currentQuestion = iter.next();
    	if(!currentQuestion.contains("answer")) {}
    	else {
    		String [] responseForOneQuestion = map.get(currentQuestion);
    		//---debugging----//
    		System.out.println("Responses:" + responseForOneQuestion[0]);
			//---------------//
    		quizResponses.add(responseForOneQuestion);
    	}	
    }
    return quizResponses;
}%>

<%!//for debugging
private void debugMultiplePageGrading(ArrayList<String[]> readyResponses) {
	System.out.println("Nresponses:" + readyResponses.size());
	String responsesArrayFormat[] = new String[readyResponses.size()];
	for(int i = 0; i < readyResponses.size(); i++) {
		responsesArrayFormat[i] = readyResponses.get(i)[0];
	}
}%>