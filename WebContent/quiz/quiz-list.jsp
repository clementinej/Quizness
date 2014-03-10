<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="model.*, java.util.*" %>
    <%@ page errorPage="../site/404.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz List</title>
<link rel="stylesheet" type="text/css" href="../css/style.css" />
<link rel="stylesheet" type="text/css" href="../css/style_login.css" />
</head>
<body>
 <div class="container_main">
 <section class="main">
            <h2 class="cs-text" id="cs-text">Quizlist</h2>
         </section>
         <div class="container">
<% 
int numQuizzes = Site.getTotalNumberOfQuizzes();
String quiz = "Title";
ArrayList<String> dummy = new ArrayList<String>();
dummy.add(quiz);
dummy.add(quiz);
dummy.add(quiz);
dummy.add(quiz);
dummy.add(quiz);
dummy.add(quiz);
dummy.add(quiz);
dummy.add(quiz);
dummy.add(quiz);
dummy.add(quiz);

//for(int i = 0; i < numQuizzes; i++) { 
	//Quiz q = Quiz.getQuiz(i);
	for(int i = 0; i < dummy.size(); i++) {
		
%>
	<!-- <p><a href="show-quiz?quiz-id=<=q.getQuizID() %>"><.getTitle()%></a></p><br> -->
	<h1><a href="/quiz/show-quiz.jsp"><%=dummy.get(i) %></a></h1>
<%} %>
</div></div>
      <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
      <script type="text/javascript" src="../js/jquery.lettering.js"></script>
      <script>
         $(document).ready(function() {
         	$("#cs-text").lettering().children('span').wrap('<span />');
         });
      </script>
</body>
</html>