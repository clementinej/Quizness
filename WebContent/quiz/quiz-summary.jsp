<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="model.*"%>
    <%@page import="java.util.*"%>
    <%@page import="java.text.SimpleDateFormat"%>
    <%--<%@ page errorPage="../site/404.jsp" --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Summary</title>
     <link rel="stylesheet" type="text/css" href="../css/style_login.css" />
</head>
<body>
<%
User user = (User) session.getAttribute("current user");
//The text description of the quiz. 

int quizID = Integer.parseInt(request.getParameter("quiz_id"));

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
	
//A list of the highest performers of all time. 
ArrayList<Integer> topQuizTriesTodayIds = QuizSummary.getAllPerformanceWithDays(quizID, 5, 1);
System.out.println("Number of topQuizTries: "+topQuizTriesTodayIds.size());

//A list of quizTry ids for the top 5 performances on the quiz
ArrayList<Integer> topPerformerIds = QuizSummary.getAllPerformance((quizID), 10);

//A list showing the performance of recent test takers (both good and bad). 
ArrayList<Integer> recentTryIds = QuizSummary.getRecentPerformance(quizID, 5);

//Datetime formatter
SimpleDateFormat formatter = (SimpleDateFormat) session.getAttribute("time formatter"); 

//Summary statistics on how well all users have performed on the quiz. 
//Summary quizSummary = Summary.getStatistics(quizID);
%>

<% 
double userHighScore = 0.0;
if(triesByScore.size() > 0) {
	userHighScore = QuizTry.getTry(triesByScore.get(0)).getScore();
}
/*double highScore = 0.0;
if(topPerformerIds.size() > 0) {
	int topID = topPerformerIds.get(0);
	System.out.println(topID);
	QuizTry t = QuizTry.getTry(topID);
	highScore = t.getScore();
}*/
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
for(int i = 0; i < triesByDate.size(); i++) {
	QuizTry t = QuizTry.getTry(triesByDate.get(i));
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

<div class="container" style="float:left;padding-left:100px;">
   <form method="post" action="../../InitQuizServlet??" id="signup">
      <div class="header">
         <h3>Quiz Summary</h3>
         <p>Take, edit, or practice the quiz.</p>
      </div>
      <div class="sep"></div>
      <div class="inputs">
         <h3>Quiz Title</h3>
         <p><%=quiz.getDescription() %></p><br> <!-- TODO -->
         <p><b>Created by </b><a href="../profile.jsp?id=<%=creator.getUserID()%>"><%=creatorName %></a></p>
         
         <!-- Print info on the user's last five tries -->
         <br><h3>Your History</h3>
         <p>Your high score on this quiz is <%=userHighScore %></p>
         <%
         for(int i = 0; i < lastTriesUser.size() && i < 5; i++) {
         %>
         <p><%=formatter.format(lastTriesUser.get(i).getDate()) %> you scored <%=lastTriesUser.get(i).getScore() %> </p>
         <%
         }
         %>
         
         <!-- Print info on the last five tries of this quiz-->
         <br><h3>Recent Activity</h3>
         <%
         for(int i = 0; i < recentTries.size() && i < 5; i++) {
         %>
         <p><%=formatter.format(recentTries.get(i).getDate()) %> <%=User.getUser(recentTries.get(i).getUserID()).getUserName()%> scored <%=recentTries.get(i).getScore() %> </p>
         <%
         }
         %>
         
      </div>	
   </form>
   </div>
   
    <!-- Print info on the top scores on this quiz-->
   <div class="container" style="float:left;padding-left:200px;">
      <form method="post" action="" id="signup">
      <div class="header">
         <h3>Top Scores</h3>
      </div>
      <div class="sep"></div>
      <div class="inputs">
      	<br><h3>Top Scores Of All Time</h3>
		 <%
         for(int i = 0; i < bestTries.size() && i < 5; i++) {
         %>
         <p><%=formatter.format(bestTries.get(i).getDate()) %> <%=User.getUser(bestTries.get(i).getUserID()).getUserName()%> scored <%=bestTries.get(i).getScore() %> </p>
         <%
         }
         %>
         
         <br><h3>Top Scores Today</h3>
		 <%
         for(int i = 0; i < bestTriesToday.size() && i < 5; i++) {
         %>
         <p><%=formatter.format(bestTriesToday.get(i).getDate()) %> <%=User.getUser(bestTriesToday.get(i).getUserID()).getUserName()%> scored <%=bestTriesToday.get(i).getScore() %> </p>
         <%
         }
         %>
         </form>
         
         <!-- We also need to display summary statistics, i.e. the average score on the quiz, 
         average amount of time it took, number of people who've taken it. -->
         <br><a href="show-quiz.jsp?quiz_id=<%=quiz.getQuizID() %>"">Take Quiz!</a>
         
         <% if(quiz.hasPracticeMode()) {%>
         <!--A way to start the quiz in practice mode, if available. -->
         <br><a href="show-quiz.jsp?quiz_id=<%=quiz.getQuizID() %>"">Practice Quiz</a>
         <%} %>
         
         <% if(user.getUserID() == quiz.getCreatorID()) { %>
         <!--A way to start editing the quiz, if the user is the quiz owner. -->
         <br><a href="../quiz-edit.jsp?quiz_id=<%=quiz.getQuizID() %>">Edit Quiz</a>
         <%} %>
         
         <!-- A way to challenge another user to this quiz -->
         <br><a href="../social/compose-mail.jsp?quiz_id=<%=quiz.getQuizID() %>&top_score=<%=userHighScore %>">Challenge a friend!</a>
         
          </form>
          <% if(user.isAdmin()){ %>
          <form method="post" action="../DeleteQuizServlet">
            <input type ="hidden" name="quiz_id" value=<%=quizID%>>
     		<input id="submit" type="submit" value="Delete Quiz">
     		<% } %>
     		
     	</form>
     	<% if (user.isAdmin()){ %>
     	<form method = "post" action = "../ClearQuizHistoryServlet">
     		<input type = "hidden" name = "quiz_id" value="<%=quizID%>"/>
     		<input id = "submit" type = "submit" value = "Clear Quiz History">
     		<% } %>
     	</form>
      </div>	
   </div>
</body>
</html>