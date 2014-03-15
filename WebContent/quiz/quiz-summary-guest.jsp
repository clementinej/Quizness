<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page errorPage="../site/404.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
      <title>Quiz Summary</title>
      <link rel="stylesheet" type="text/css" href="/Quizness/css/style_login.css" />
   </head>
   <body>
      <%

int quizID = Integer.parseInt(request.getParameter("quiz_id"));
String orderBy = request.getParameter("orderBy"); 
Quiz quiz = Quiz.getQuiz(quizID);

String description = quiz.getDescription();

//The creator of the quiz (hot linked to the creator's profile). 
User creator = User.getUser(quiz.getCreatorID());
String creatorName = creator.getUserName();

double averageTime = QuizSummary.getAllAverageTimeSpent(quizID);
double averageScore = QuizSummary.getAllAverageScore(quizID);
int numAttempts = QuizSummary.getAllNumOfTimesTaken(quizID);
	
//A list of the highest performers of all time. 
ArrayList<Integer> topQuizTriesTodayIds = QuizSummary.getAllPerformanceWithDays(quizID, 5, 1);
System.out.println("Number of topQuizTries: "+topQuizTriesTodayIds.size());

//A list of quizTry ids for the top 5 performances on the quiz
ArrayList<Integer> topPerformerIds = QuizSummary.getAllPerformance((quizID), 10);

//A list showing the performance of recent test takers (both good and bad). 
ArrayList<Integer> recentTryIds = QuizSummary.getRecentPerformance(quizID, 5);

//Datetime formatter
SimpleDateFormat formatter = (SimpleDateFormat) session.getAttribute("time formatter"); 

%>
      <%
double lastScore = 0.0;
if(recentTryIds.size() > 0) {
	lastScore = QuizTry.getTry(recentTryIds.get(0)).getScore();
}

//the LAST five quiz tries 
ArrayList<QuizTry> recentTries = new ArrayList<QuizTry>();
for(int i = 0; i < recentTryIds.size(); i++) {
	QuizTry t = QuizTry.getTry(recentTryIds.get(i));
	recentTries.add(t);
}

//should return the BEST five tries on this quiz
ArrayList<QuizTry> bestTries = new ArrayList<QuizTry>(); 
for(int i = 0; i < topPerformerIds.size(); i++) {
	QuizTry t = QuizTry.getTry(topPerformerIds.get(i));
	bestTries.add(t);
}

//should return the last five tries on this quiz
ArrayList<QuizTry> bestTriesToday = new ArrayList<QuizTry>(); 
for(int i = 0; i < topQuizTriesTodayIds.size(); i++) {
	QuizTry t = QuizTry.getTry(topQuizTriesTodayIds.get(i));
	bestTriesToday.add(t);
}
%>
      <div class="container" style="float:left;padding-left:20px;">
         <form method="post" action="../quiz/quiz-summary.jsp?quiz_id=<%=quizID %>" id="signup">
            <div class="header">
               <h3>Quiz Summary</h3>
               <p>Take, edit, or practice the quiz.</p>
            </div>
            <div class="sep"></div>
            <div class="inputs">
               <h3>Title</h3>
               <p><%=quiz.getTitle()%></p>
               <br>
               <h3>Description</h3>
               <p><%=quiz.getDescription()%></p>
               <br> 
               <p><b>Created by </b><%=creatorName %></p>
               <!-- Print info on the last five tries of this quiz-->
               <br>
               <h3>Recent Activity</h3>
               <%
                  for(int i = 0; i < recentTries.size() && i < 5; i++) {
                   int userID = User.getUser(recentTries.get(i).getUserID()).getUserID();
                   String userName = User.getUser(recentTries.get(i).getUserID()).getUserName(); 
                  %>
               <p><%=formatter.format(recentTries.get(i).getDate()) %> 
                  <%=userName%> scored <%=NumberConverter.toString(recentTries.get(i).getScore()) %> 
               </p>
               <%
                  }
                  %>
            </div>
         </form>
      </div>
      <!-- Print info on the top scores on this quiz-->
      <div class="container" style="float:left;padding-left:50px;">
         <form method="post" action="" id="signup">
            <div class="header">
               <h3>Top Scores</h3>
               <p>See how you stack up!</p>
            </div>
            <div class="sep"></div>
            <br>
            <h3>Top Scores Of All Time</h3>
            <%
               for(int i = 0; i < bestTries.size() && i < 5; i++) {
                int userID = User.getUser(bestTries.get(i).getUserID()).getUserID();
                String userName = User.getUser(bestTries.get(i).getUserID()).getUserName(); 
               %>
            <p><%=formatter.format(bestTries.get(i).getDate()) %><%=" " + userName + " "%>scored <%=" " + NumberConverter.toString(bestTries.get(i).getScore()) %> </p>
            <%
               }
               %>
            <br>
            <h3>Top Scores Today</h3>
            <%
               for(int i = 0; i < bestTriesToday.size() && i < 5; i++) {
                int userID = User.getUser(bestTriesToday.get(i).getUserID()).getUserID();
                String userName = User.getUser(bestTriesToday.get(i).getUserID()).getUserName();
               %>
            <p><%=formatter.format(bestTriesToday.get(i).getDate()) %>
               <%=userName + " " %> scored <%= " " + NumberConverter.toString(bestTriesToday.get(i).getScore()) %> 
            </p>
            <%
               }
               %>
            <!-- We also need to display summary statistics, i.e. the average score on the quiz, 
               average amount of time it took, number of people who've taken it. -->
            <br><a href="/Quizness/quiz/show-quiz.jsp?quiz_id=<%=quiz.getQuizID() %>">Take Quiz!</a>
         </form>
      </div>
      <div class="container" style="float:left;padding-left:80px;">
         <form method="post" action="" id="signup">
            <div class="header">
               <h3>Quiz Statistics</h3>
               <p>Track your progress</p>
            </div>
            <div class="sep"></div>
            <br>
            <h3>Average Time Spent</h3>
            <p><%=NumberConverter.roundToTwoPlaces(averageTime)%> seconds</p>
            <br>
            <br>
            <h3>Average Score</h3>
            <p><%=NumberConverter.toString(averageScore)%></p>
            <br>
            <br>
            <h3>Total Attempts</h3>
            <p><%=numAttempts%></p>
            <br>
            <br>
            <h3>Reviews</h3>
            <%
               int numReviews = 0;
               ArrayList<Integer> reviews = Review.getReviews(quizID);  
               numReviews = reviews.size();
               if (numReviews > 3) numReviews = 3;
               if(numReviews != 0) {%>
            <h5>Recent Reviews</h5>
            <% for (int i = 0; i < numReviews; i++){%>
            	<% int reviewID = reviews.get(i);
            	int ranking = Review.getRanking(reviewID);
            	String review = Review.getReview(reviewID);
            	String reviewer = Review.getReviewerName(reviewID);%>
            	<p><%=reviewer %> said: <%=review %></p>
            	<p><%=ranking %> stars</p>
            	<p></p>
            <% } %>
            <%} else {%>
            <h5>Nobody has reviewed this quiz yet.</h5>
            <%} %>
            <br>
            <h3>Average Rating</h3>
            <%
               int rating = Review.getAverageRanking(); 
               if(rating == 0) {
               %>
            <h5>Nobody has ranked this quiz yet.</h5>
            <%}else { %>
            <h5><%=rating %></h5>
            <%} %>

         </form>
      </div>
   </body>
</html>