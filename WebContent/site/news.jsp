<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page errorPage="../site/404.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <link rel="stylesheet" type="text/css" href="../css/circularmenu.css" />
      <head>
   </head>
   </head>
   <style>
      #recent-quizzes {
      float:left;
      margin:10px;
      width:30%;
      }
      #top-quizzes {
      float:left;
      margin:10px;
      width:30%;
      }
      #new-quizzes {
      float:left;
      margin:10px;
      width:30%;
      }
   </style>
   <body>
      <div class="container">
         <div class="component">
            <h2>News</h2>
            <!-- Start Nav Structure -->
         </div>
         <!-- End of Nav Structure -->
      </div>
      </div><!-- /container -->
      <div id="recent-quizzes">
      <h2>Recently Created Quizzes</h2>
         <%
            //ArrayList<String> announcements = session.getAttribute("announcements");
            ArrayList<String> recent = new ArrayList<String>();
            recent.add("recent!");
            recent.add("recent!");
            recent.add("recent!");
            if(recent != null) {
            	for(String a: recent) {%>
         <p class="recent-quizzes"><%=a%>
         <p>
            <%}
               }%>
      </div>
      <div id="top-quizzes">
      <h2>Most Popular Quizzes</h2>
         <%
            //ArrayList<String> top = session.getAttribute("top-quizzes");
            ArrayList<String> top = new ArrayList<String>();
            top.add("top!");
            top.add("top!");
            top.add("top!");
            if(top != null) {
            	for(String a: top) {%>
         <p class="top-quizzes"><%=a%>
         <p>
            <%}
               }%>
      </div>
      <div id="new-quizzes">
      <h2>New Quizzes</h2>
         <%
            //ArrayList<String> newQuizzes = session.getAttribute("new-quizzes");
            ArrayList<String> newQuizzes = new ArrayList<String>();
            newQuizzes.add("new!");
            newQuizzes.add("new!");
            newQuizzes.add("new!");
            if(top != null) {
            	for(String a: newQuizzes) {%>
         <p class="new-quizzes"><%=a%>
         <p>
            <%}
               }%>
      </div>
   </body>
</html>