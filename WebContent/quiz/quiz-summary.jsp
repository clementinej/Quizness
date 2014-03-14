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
User user = (User) session.getAttribute("current user");
//The text description of the quiz. 

int quizID = Integer.parseInt(request.getParameter("quiz_id"));
String orderBy = request.getParameter("orderBy"); 
Quiz quiz = Quiz.getQuiz(quizID);

String description = quiz.getDescription();

//The creator of the quiz (hot linked to the creator's profile). 
User creator = User.getUser(quiz.getCreatorID());
String creatorName = creator.getUserName();

//A list of the user's past performance on this specific quiz. 

//Consider allowing the user to order this by date, by percent correct, and by amount of time the quiz took. 
ArrayList<Integer> triesByDate = QuizSummary.getPerformanceByDate(user.getUserID(), quizID, 10);
ArrayList<Integer> triesByScore = QuizSummary.getPerformanceByScore(user.getUserID(), quizID, 10);
ArrayList<Integer> triesByTime = QuizSummary.getPerformancyByTime(user.getUserID(), quizID, 10);
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
double userHighScore = 0.0;
if(triesByScore.size() > 0) {
	userHighScore = QuizTry.getTry(triesByScore.get(0)).getScore();
}

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

//should return the USER'S LAST five quiz tries 
ArrayList<QuizTry> lastTriesUser = new ArrayList<QuizTry>(); 
ArrayList<Integer> triesUser = triesByDate;

if(orderBy != null){
	if(orderBy.equals("date")){
		triesUser = triesByDate; 
	}
	if(orderBy.equals("percentage")){
		triesUser = triesByScore; 
	}
	if(orderBy.equals("time")){
		triesUser = triesByTime;
	}
}

for(int i = 0; i < triesUser.size(); i++) {
	QuizTry t = QuizTry.getTry(triesUser.get(i));
	lastTriesUser.add(t);
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
         <p><%=quiz.getTitle()%></p><br>
         <h3>Description</h3>
         <p><%=quiz.getDescription()%></p><br> 
         <p><b>Created by </b><a href="/Quizness/profile.jsp?id=<%=creator.getUserID()%>"><%=creatorName %></a></p>
         
         <!-- Print info on the user's last five tries -->
         <br><h3>Your History</h3>
         <%
         String score = NumberConverter.toString(userHighScore);
         if(score.equals("0 points")) {
        	 %>
        	 <p>You've never taken this quiz</p>
        	 <% 
         } else {
         %>
         <p>Your high score on this quiz is <%=score%></p>
         <%}
         for(int i = 0; i < lastTriesUser.size() && i < 5; i++) {
         %>
         <p><%=formatter.format(lastTriesUser.get(i).getDate()) %> you scored <%=NumberConverter.toString(lastTriesUser.get(i).getScore()) %> </p>
         <%
         }
         %>
         
	     <select name="orderBy">
	 		<option value="date">Order By Date Taken</option>
			<option value="percentage">Order By Percent Correct</option>
			<option value="time">Order By Time Spent</option>
		 </select>
     		<input type = "hidden" name = "quiz_id" value="<%=quizID%>"/>
     		<input id = "submit" type = "submit" value = "Update"> 
     	
     	       
         <!-- Print info on the last five tries of this quiz-->
         <br><h3>Recent Activity</h3>
         <%
         for(int i = 0; i < recentTries.size() && i < 5; i++) {
        	 int userID = User.getUser(recentTries.get(i).getUserID()).getUserID();
        	 String userName = User.getUser(recentTries.get(i).getUserID()).getUserName(); 
         %>
         <p><%=formatter.format(recentTries.get(i).getDate()) %> 
         	<a href="/Quizness/profile.jsp?id=<%=userID%>"><%=userName%></a> scored <%=NumberConverter.toString(recentTries.get(i).getScore()) %> </p>
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
      
      	<br><h3>Top Scores Of All Time</h3>
		 <%
         for(int i = 0; i < bestTries.size() && i < 5; i++) {
        	 int userID = User.getUser(bestTries.get(i).getUserID()).getUserID();
        	 String userName = User.getUser(bestTries.get(i).getUserID()).getUserName(); 
         %>
         <p><%=formatter.format(bestTries.get(i).getDate()) %><a href="/Quizness/profile.jsp?id=<%=userID%>"><%=" " + userName + " "%></a> scored <%=" " + NumberConverter.toString(bestTries.get(i).getScore()) %> </p>
         <%
         }
         %>
         
         <br><h3>Top Scores Today</h3>
		 <%
         for(int i = 0; i < bestTriesToday.size() && i < 5; i++) {
        	 int userID = User.getUser(bestTriesToday.get(i).getUserID()).getUserID();
        	 String userName = User.getUser(bestTriesToday.get(i).getUserID()).getUserName();
         %>
         <p><%=formatter.format(bestTriesToday.get(i).getDate()) %>
          	<a href="/Quizness/profile.jsp?id=<%=userID%>"><%=userName + " " %></a> scored <%= " " + NumberConverter.toString(bestTriesToday.get(i).getScore()) %> </p>
         <%
         }
         %>
         
         <!-- We also need to display summary statistics, i.e. the average score on the quiz, 
         average amount of time it took, number of people who've taken it. -->
         <br><a href="/Quizness/quiz/show-quiz.jsp?quiz_id=<%=quiz.getQuizID() %>">Take Quiz!</a>
         
         <% if(quiz.hasPracticeMode()) {%>
         <!--A way to start the quiz in practice mode, if available. -->
         <br><a href="/Quizness/quiz/show-quiz.jsp?quiz_id=<%=quiz.getQuizID() %>">Practice Quiz</a>
         <%} %>
         
         <% if(user.getUserID() == quiz.getCreatorID()) { %>
         <!--A way to start editing the quiz, if the user is the quiz owner. -->
         <br><a href="/Quizness/quiz-edit.jsp?quiz_id=<%=quiz.getQuizID() %>">Edit Quiz</a>
         <%} %>
         
         <!-- A way to challenge another user to this quiz -->
         <br><a href="/Quizness/social/compose-mail.jsp?quiz_id=<%=quiz.getQuizID() %>&top_score=<%=userHighScore %>">Challenge a friend!</a>
         
          
          <% if(user.isAdmin()){ %>
          <form method="post" action="/Quizness/DeleteQuizServlet" id="signup">
           <div class="inputs">
            <input type ="hidden" name="quiz_id" value=<%=quizID%>>
     		<input id="submit" type="submit" value="Delete Quiz">
     		</div>
     		<% } %>
     		</form>
     	</form>
      </div>
   	<div class="container" style="float:left;padding-left:80px;">
      <form method="post" action="" id="signup">
      <div class="header">
         <h3>Quiz Statistics</h3>
         <p>Track your progress</p>
      </div>
      <div class="sep"></div>
      
      	<br><h3>Average Time Spent</h3>
      	<p><%=NumberConverter.roundToTwoPlaces(averageTime)%> seconds</p><br>
      	<br><h3>Average Score</h3>
      	<p><%=NumberConverter.toString(averageScore)%></p><br>
      	<br><h3>Total Attempts</h3>
      	<p><%=numAttempts%></p><br>
      	<br><h3>Reviews</h3>
      	<%
      		int numReviews = 0;
      		ArrayList<Integer> reviews = Review.getReviews(quizID);  
      		numReviews = reviews.size();
    		if(numReviews != 0) {%>
    	<h5>Last Review</h5>
      		<p><%=reviews.get(0) %></p>
      		<%} else {%>
      		<h5>Nobody has reviewed this quiz yet.</h5>
      	<%} %>
      	<br><h3>Average Rating</h3>
      	<%
      	int rating = Review.getRanking(user.getUserID()); 
      	if(rating == 0) {
      	%>
      	<h5>Nobody has ranked this quiz yet.</h5>
      	<%}else { %>
      	<h5><%=rating %></h5>
      	<%} %>
      	
      		            	<% if (user.isAdmin()){ %>
     	<form method="post" action="/Quizness/ClearQuizHistoryServlet" id="signup">
     		<input type="hidden" name="quiz_id" value="<%=quizID%>"/>
     		<div class="inputs">
     		<input style="width:200px;" id="submit" type="submit" value="Clear Quiz History">
     		<% } %>
     		</div>
     	</form>
	 </form>
      </div>

     
</body>
</html>