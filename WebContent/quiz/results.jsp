<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
   <head>
      <meta charset="UTF-8" />
      <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link href="../css/default.css" rel="stylesheet" type="text/css"  />
      <link href="../css/hexaflip.css" rel="stylesheet" type="text/css">
      <link href="../css/results.css" rel="stylesheet" type="text/css">
   </head>
   <%@page import="model.*"%>
   <%@page import="java.util.*"%>
   <%
      //Get the user
      User user = (User)session.getAttribute("currrent user");
      //Get the quiz try by id
      int quizID = Integer.parseInt(request.getParameter("quizTryID"));
  	  QuizTry quizTry = QuizTry.getTry(quizID);
      //Only let users see their own quiz results
      if(quizTry.getUserID() != user.getUserID() && !user.isAdmin()) {
     	return;
      }
      //Only let users see completed quizes
      if(quizTry.isInProgress()) {
   	  	return;
      }
      %>
   <body>
      <div class="container">
         <header class="clearfix">
         </header>
         <div class="main">
            <div id="cubes" class="demo"></div>
         </div>
         <form method="post" action="/SaveResults">
            <%
              double score = quizTry.getScore();
              double time = quizTry.getTime()/1000;
              ArrayList<String[]> responses = quizTry.getResponses();
              
              double userAverageScore = History.getAverageScoreByUser(user.getUserID(), quizID);
              double userAverageTime = History.getAverageTimeByUser(user.getUserID(), quizID);
              int userNumTries = History.getNumTriesByUser(user.getUserID(), quizID);
              Date userLastTry = History.getLastTryByUser(user.getUserID(), quizID);
              
              double averageScore = History.getAverageScore(quizID);
              double averageTime = History.getAverageTime(quizID);
              int numTries = History.getNumTries(quizID);
              int numTriesToday = History.getNumTriesToday(quizID);
           	%>
             
            <div id="score">
               <p>Your score is<%=score %></p>
               <p>It took you <%=time %> seconds to complete this quiz</p>
            </div>
            
            <h1>Your History</h1>
            <p>Your average score on this quiz is<%userAverageScore %></p>
            <p>On average, this quiz takes you <%=userAverageTime %> seconds to complete </p>
            <p>You've taken this quiz <%=userNumTries %> times </p>
            <p>You last took this quiz on <%=userLastTry %></p>
            
            <h1>Quiz History</h1>
            <p>On average, users score <%averageScore %> on this quiz.</p>
            <p>On average, this quiz takes users <%=averageTime %> seconds to complete.</p>
            <p>This quiz has been taken <%=numTries %> times.</p>
            <p>This quiz has been taken <%=numTriesToday %> times today.</p>
         </form>
         
         <%
         for(int i = 0; i < responses.size(); i++) {
         %>
         <p><%=responses %></p>
         <%} %>
      </div>
      <script src="../js/hexaflip.js"></script>
      <script src="../js/results.js"></script>
   </body>
</html>