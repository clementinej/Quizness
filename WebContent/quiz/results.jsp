<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
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
        user.incrementQuizzesTaken();
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
         <span class="header-link"><a href="../profile.jsp">Profile</a></span>
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
                 
               //Datetime formatter
               SimpleDateFormat formatter = (SimpleDateFormat) session.getAttribute("time formatter"); 
               
               double score = quizTry.getScore();
               user.checkHighScore(score);
               double time = quizTry.getTime()/1000;
               ArrayList<String[]> responses = quizTry.getResponses();
               int quizID = quizTry.getQuizID();
               double userAverageScore = QuizResult.getUserAverageScore(user.getUserID(), quizID);
               double userAverageTime = QuizResult.getUserAverageTimeSpent(user.getUserID(), quizID);
               int userNumTries = QuizResult.getUserNumOfTimesTaken(user.getUserID(), quizID);
               
               Date userLastTryDate = null;
               if(QuizResult.getLastDateTaken(quizID, user.getUserID()) != -1){
               QuizTry lastTry = QuizTry.getTry(QuizResult.getLastDateTaken(quizID, user.getUserID()));
               userLastTryDate = lastTry.getDate();
               }
                    
               double averageScore = QuizResult.getAllAverageScore(quizID);
               double averageTime = QuizResult.getAllAverageTimeSpent(quizID);
               int numTries = QuizSummary.getAllNumOfTimesTaken(quizID);
               int numTriesToday =  QuizSummary.getAllNumOfTimesTakenToday(quizID);
                    
               double friendsAverageScore = QuizResult.getFriendsAverageScore(user.getUserID(), quizID);
               double friendsAverageTime = QuizResult.getFriendsAverageTimeSpent(user.getUserID(), quizID);%>
            <div id="score">
               <h1>You scored <%=NumberConverter.toString(score) %></h1>
               <p>It took you <%=time %> seconds to complete this quiz</p>
               <br>
            </div>
            <div id="quiz-info">
            <div class="quiz-info">
               <h2>Your History</h2>
               <p>Your average score on this quiz is <%=NumberConverter.toString(userAverageScore)%></p>
               <p>On average, this quiz takes you <%=userAverageTime%> seconds. </p>
               <p>You've taken this quiz <%=userNumTries%> times </p>
               <p>You last took this quiz on <%=formatter.format(userLastTryDate)%></p>
            </div>
            <div  class="quiz-info">
               <h2>Quiz History</h2>
               <p>On average, users score <%=NumberConverter.toString(averageScore)%> on this quiz.</p>
               <p>On average, this quiz takes users <%=averageTime%> seconds.</p>
               <p>This quiz has been taken <%=numTries%> times.</p>
               <p>This quiz has been taken <%=numTriesToday%> times today.</p>
            </div>
            <div  class="quiz-info">
               <h2>Friend's History</h2>
               <p>On average, your friends score <%=NumberConverter.toString(friendsAverageScore)%></p>
               <p>On average, this quiz takes your friends <%=friendsAverageTime %> seconds.</p>
            </div>
            </div>
         </form>
         <div id="ranking-panel">
            <form id="review" method="post" action="../ReviewServlet" class="button">
               <h2>Review this Quiz!</h2>
               <input id="subject" class="inputs" type="text" name="review" value="Add a short review and click save!">
               <div><span class="rating">
                  <input type="radio" class="rating-input"
                     id="1-star" name="rank" value="5-star"/>
                  <label for="1-star" class="rating-star"></label>
                  <input type="radio" class="rating-input"
                     id="2-star" name="rank" value="4-star"/>
                  <label for="2-star" class="rating-star"></label>
                  <input type="radio" class="rating-input"
                     id="3-star" name="rank" value="3-star"/>
                  <label for="3-star" class="rating-star"></label>
                  <input type="radio" class="rating-input"
                     id="4-star" name="rank" value="2-star"/>
                  <label for="4-star" class="rating-star"></label>
                  <input type="radio" class="rating-input"
                     id="5-star" name="rank" value="1-star"/>
                  <label for="5-star" class="rating-star"></label>
                  </span>
               </div>
               <input id="save-review" class="blue-button" type="submit" value="Save Review" >
				<input id ="quizID" type="hidden" name="quizID" value=<%=quizTry.getQuizID() %>>
     			<input id="reviewerID" type="hidden" name="reviewerID" value=<%=user.getUserID()%>>            
     			</form>
         </div>
         <div>
		<form method="post" action="../quiz/quiz-summary.jsp?quiz_id=<%=quizID %>"  class="button">
     		<input id="go-to-summary" class="blue-button" type="submit" value="Back to Quiz Summary">
     	</form>
     
     	<form method="post" action="../quiz/show-quiz.jsp?quiz_id=<%=quizID %>" class="button">
     		<input id="take-again" class="blue-button" type="submit" value = "Take this Quiz Again">
     	</form>
     	
     	<form method="post" action="../ReportServlet" class="button">
     		<input id="innapropriate" class="blue-button" type="submit" value="Mark As Inappropriate">
     		<input id="submit" type="hidden" name="quizID" value=<%=quizID%>>
     	</form>
     	</div>
     	</div>
      </div>
      <script src="../js/hexaflip.js"></script>
      <script src="../js/results.js"></script>
      <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript">
         $(':radio').change(
         function(){
         $('.choice').text( this.value + ' stars' );
         });
      </script>
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
