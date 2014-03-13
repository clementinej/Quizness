<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@ page import="model.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page errorPage="../site/404.jsp"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
   <head>
     	<title>Inbox</title>
      <meta charset="UTF-8" />
      <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="stylesheet" type="text/css" href="css/style.css" />
      <link rel="stylesheet" type="text/css" href="css/inbox.css" />
      <script src="js/modernizr-2.6.2.min.js"></script>

   </head>
      <body>
            <!--top bar -->
      <div class="top">
      	<span class="header-link"><a href="home.jsp">Home</a></span>
         <span class="header-link"><a href="create-quiz.jsp">Create Quiz</a></span>
         <span class="header-link"><a href="social/compose-mail.jsp">Compose </a></span>
         <span class="header-link"><a href="profile.jsp">Profile</a></span>
         <span class="header-link"><a href="site/admin.jsp">Admin</a></span>
         <span class="header-link"><a href="inbox.jsp">Inbox</a></span>
          <span class="header-link"><a href="site/search.jsp">Search</a></span>
      </div>
      
      <%
		// Get the reported quizzes
		ArrayList<Integer> reportedQuizzes = Quiz.getReported();
      int numReported = reportedQuizzes.size();
	  %> 
	
      <div class="container">
         <section class="color-1">
            <nav class="cl-effect-14">
             	<a href="reported-quizzes.jsp">Reported Quizzes</a>
            </nav>
         </section>
      </div>
      <hr id="inbox"/>
      <div id="num-messages">
      <%if(numReported == 0) { %>
         <h4>There are no reported quizzes</h4>
      <%} %>
      </div>
      <form method="post" action="RemoveReportedQuizServlet">
         <div id="table-container">
            <table id="table">
               <tbody>
               
               <% 
				for (int i = 0; i < numReported; i++){
					int quizID = reportedQuizzes.get(i);
					Quiz quiz = Quiz.getQuiz(quizID);
					int creatorID = quiz.getCreatorID();
					User creator = User.getUser(creatorID);
					String creatorEmail = creator.getEmail();
				%>
		                  <tr>
		                     <!-- DISPLAY UNREAD LINKS IN BOLD -->
		                     <td class="leftalign">
		                        <input type="checkbox" id="checkbox<%=i%>" name="check" value="<%=quizID%>">
		                     </td>
		                     <!-- LINK TO SENDER'S PROFILE -->
		                     <td class="center-subject"><a href="profile.jsp?id=<%=creatorID%>"><font><b><%=creatorEmail%></b></font></a></td>
		                     <!-- LINK TO MESSAGE -->
		                     <td class="center-message"><a href="quiz/quiz-summary.jsp?quiz_id=<%=quizID%>"><font color><b><%=quiz.getTitle()%></b></font></a></td>

		                  </tr>
                  		<%
				}
               	%>
               </tbody>
            </table>
         </div>
         <input type="submit" name="remove" value="Remove Quizzes" />
         <input type="submit" name="keep" value="Keep Quizzes" />
      </form>
   </body>
</html>