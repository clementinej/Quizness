<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*, java.util.*" %>
<%--<%@ page errorPage="../site/404.jsp" %> --%>

<%
User currentUser = (User) session.getAttribute("current user");
if (!currentUser.isAdmin()){
	RequestDispatcher dispatch = request.getRequestDispatcher("index.html"); 
	dispatch.forward(request, response);
}
System.out.println("The current user is: " + currentUser.getUserName());
java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat();
int numQuizzes = 5;//Site.getTotalNumberOfQuizzes();
int numUsers = 5;//Site.getTotalNumberOfUsers();
int numTags = 5;//Site.getTotalNumberTags();
int numQuizzesTaken = 5;//Site.getTotalNumberQuizzesTaken();

int numFriends = 5;//Site.getNumFriendships();
int numMsgsSent = 5;//Site.getNumMsgs();
int numChallenges = 5;//Site.getNumChallenges();
int numFriendReqs = 5;//Site.getNumReqs();
%>

<head>
   <link rel="stylesheet" type="text/css" href="../css/style_login.css" />
</head>
<body>

  <div class="container_main">
         <section class="main">
            <h2 class="cs-text" id="cs-text">Bosspage</h2>
         </section>
      </div>
      <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
      <script type="text/javascript" src="../js/jquery.lettering.js"></script>
      <script>
         $(document).ready(function() {
         	$("#cs-text").lettering().children('span').wrap('<span />');
         });
      </script>

		<a class="manage" href="admin.jsp#edit_user" id="top">Manage Users</a><a class="manage" href="admin.jsp#edit_quiz">Manage Quizzes</a><br><br>

		<div class ="inline">
			<div class="title-bar">Activity Summary</div>
				<table style="width:435px;margin-top:4px">
					<tr>
						<td align="left" width="47%" class="content">Quizzes</td>
						<td align="left" width="6%"></td>
						<td align="left" width="47%" class="content">Social</td>
					</tr>
				</table>

			<div style="width:425px;">
			<table cellpadding="5" cellspacing="5" border="0" width="100%">
				<tbody>
					<tr>
						<td class="col1"><%=numUsers %></td>
						<td class="col2">Users</td>
						<td class="col3"></td>
						<td class="col1"><b><%=numFriends %></b></td>
						<td class="col2">Friendships</td>
					</tr>	
					<tr>
						<td class="col1"><b><%=numQuizzes %> </b></td>
						<td class="col2">Quizzes</td>
						<td class="col3"></td>
						<td class="col1"><b><%=numFriendReqs %></b></td>
						<td class="col2">Friend Requests</td>
					</tr>
					<tr>
						<td class="col1"><b><%=numQuizzesTaken%> </b></td>
						<td class="col2">Quiz Tries</td>
						<td class="col3"></td>
						<td class="col1"><b><%=numMsgsSent %></b></td>
						<td align="left" width="38%">Messages Sent</td>
					</tr>
					<tr>
						<td class="col1"><b><%=numTags %> </b></td>
						<td class="col2">Tags</td>
						<td class="col3"></td>
						<td class="col1"><b><%=numChallenges %></b></td>
						<td class="col2">Challenges</td>
					</tr>
				</tbody>
			</table>
			</div>
		</div>

		<div class="announce">
			<div class="border">
			<h3>Post a New Announcement</h3>
			</div>
			<form action="../NewAnnouncementServlet" method="post" class="form-1">
				<div class="title"><input class="announce-title" type="text" name="subject" placeholder="Give your announcement a title" /></div>
				<div class="title"><input id="announce-text" name="body"></input></div>
				<!-- <div class="title"><input type="submit" value="Post Announcement!" /></div> -->
				<input id="submit" type="submit" value="Announce!"/>
				
				<!--  <a id="submit" href="home.html" value="Post Announcement">ANNOUNCE!</a> -->
			</form>
		</div>
</body>
</html>
