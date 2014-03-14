<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="model.*, java.util.*" %>
    <%@ page errorPage="../site/404.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
      <meta charset="UTF-8" />
      <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="stylesheet" type="text/css" href="css/style.css" />
      <link rel="stylesheet" type="text/css" href="css/style_login.css" />
      <link href='http://fonts.googleapis.com/css?family=Open+Sans+Condensed:700,300|Montserrat' rel='stylesheet' type='text/css' />
      </head>
<body>
    <%
    	boolean loggedIn = true; 
    	int userID = -1;
    	String name = "Guest User"; 
        User user = (User) session.getAttribute("current user");
    	if(user == null){
    		loggedIn = false; 
    	} else {
    		loggedIn = true;
    		userID = user.getUserID();
            name = user.getUserName();
    	}
         
         %>
     <!--top bar -->
      <div class="top">
      	<% if(loggedIn){ %>
         <span class="header-link"><a href="create-quiz.jsp">Create Quiz</a></span>
         <span class="header-link"><a href="social/compose-mail.html">Compose </a></span>
         <span class="header-link"><a href="profile.jsp">Profile</a></span>
         <span class="header-link"><a href="site/admin.jsp">Admin</a></span>
         <span class="header-link"><a href="inbox.jsp">Inbox</a></span>
         
         <% } else { %>
         <span class="header-link"><a href="home-guest.jsp">Home</a></span>
         <span class="header-link"><a href="index.jsp">Log In</a></span>
         <span class="header-link"><a href="create-account.jsp">Create a New Account</a></span>
         <% } %>
         <span class="right">Welcome to Quizness, <%=name %></span>
      </div>
      <div class="container">
      <div id="signup">
      	 <div class="header">       
            <h3>Search Results</h3>
            <p>Click the links below to explore quizzes</p>    
        </div>
          <div class="sep"></div>
          <div class="inputs">
<%
String resultType = (String) session.getAttribute("resultType");
if(resultType.equals("user")) {
	ArrayList<User> users = (ArrayList<User>) session.getAttribute("userResults");
	for(int i = 0; i < users.size(); i++) {
		User u = users.get(i);
		%>
		<p><%=i %>.</p><a href="profile.jsp?id=<%=u.getUserID() %>"><p><%=u.getUserName() %><p></a>
		<% 
	}
} else {
	ArrayList<Quiz> quizzes = (ArrayList<Quiz>) session.getAttribute("quizResults");
	for(int i = 0; i < quizzes.size(); i++) {
		Quiz q = quizzes.get(i);
		%>
		<p><%=i %>.</p><a href="../Quizness/quiz/quiz-summary.jsp?quiz_id=<%=q.getQuizID()%>"><p><%=q.getTitle() %></p></a>
		<% 
	}
}
%>
</div>
</div>
</div>
</body>
</html>