<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*, java.util.*" %>

<%
User currentUser = (User) session.getAttribute("current user");
java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat();
int numQuizzes = Site.getTotalNumberOfQuizzes();
int numUsers = Site.getTotalNumberOfUsers();
int numTags = Site.getTotalNumberTags();
int numQuizzesTaken = Site.getTotalNumberQuizzesTaken();

int numFriends = Site.getNumFriendships();
int numMsgsSent = Site.getNumMsgs();
int numChallenges = Site.getNumChallenges();
int numFriendReqs = Site.getNumReqs();

String user_search = request.getParameter("user_search");
String quiz_search = request.getParameter("quiz_search");

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
			<form action="admin/NewAnnouncementServlet" method="post" class="form-1">
				<div class="title"><input class="announce-title" type="text" name="subject" placeholder="Give your announcement a title" /></div>
				<div class="title"><input id="announce-text" name="body"></input></div>
				<!-- <div class="title"><input type="submit" value="Post Announcement!" /></div> -->
				<a id="submit" href="home.html" value="Post Announcement">ANNOUNCE!</a>
			</form>
		</div>
		<br class="clearBoth"/>
		<a name="edit_user"></a>

		<br>
		<div style="padding-left:20px;padding-right:20px">
			<div class="title-bar">Quick User Edit</div>
			<div style="padding-left:10px;padding-right:10px;margin-bottom:15px;margin-top:15px; position:relative; left:-1px;">
				<form style="display: inline;" action="admin/admin.jsp#edit_user" method="post">
					<input type="text" name="user_search" placeholder="Search by email or name" size="45"> 
					<input class="cute-button" type="submit" name="search" value="Search">
				</form>
			</div>
			<div class="title-bar">
				<span class="title-col1" ><b>User</b></span>
				<span class="title-col1" ><b>Email</b></span>
				<span style="width:18%;display:inline-block"><b>Status</b></span>
				<span style="display:inline-block"><b>Actions</b></span>
			</div>
					<div style="margin-bottom:10px;margin-right:10px;margin-left:10px;">
					<div class="search_user_right">
						<div>
							<form style="display:inline" action="AdminServlet" method="POST">
								<input class="cute-button" type="submit" name="admin_delete" value = "Delete">				
								<input type="hidden" name="user_id" value="user_id">
								<input type="hidden" name="search_query" value="query">
							</form>
						</div>
					</div>
					<div class="search_user_left">
						<div align="left">
						<span class="title-col1"><a href="user/profile.jsp?user=<%=currentUser.getUserID()%>"></a></span>
						<span class="title-col1"><%=currentUser.getEmail()%></span>
							<span class="title-col3">ADMIN</span>
							<span class="title-col3"></span>
						</div>
					</div>
				</div>
		<div class="no_results">
			No Users Matched.
		</div> 
		<br>
		<a name="edit_quiz"></a>

	<%
	// get quizID from search query
	Quiz quiz = Quiz.getQuiz(71);
	int quizID = quiz.getQuizID();
	int creatorID = quiz.getCreatorID();
	String name = User.getUser(creatorID).getUserName();
	Date date = quiz.getDateCreated();
	%>
		<div>
			<div class="title-bar">Quick Quiz Edit</div>
			<div class="search">
				<form action="admin/admin.jsp#edit_quiz" method="post">
					<input type="text" name="quiz_search" placeholder="Search by quiz name or description" size="45"> 
					<input class="cute-button" type="submit" name="search" value="Search">
				</form>
			</div>
			<div class="title-bar">
				<span style="width:32%;display:inline-block"><b>Quiz</b></span>
				<span style="width:27%;display:inline-block"><b>Creator</b></span>
				<span style="width:29%;display:inline-block"><b>Date Created</b></span>
				<span style="display:inline-block"><b>Actions</b></span>
			</div>
					<div style="margin-bottom:10px;margin-right:10px;margin-left:10px;">
					<div class="search_user_right">
						<div>
							<form style="display:inline" action="AdminServlet" method="POST">
								<input class="cute-button" type="submit" name="clear_history" value="Clear">	
								<input class="cute-button" type="submit" name="quiz_delete" value = "Delete">				
								<input type="hidden" name="quiz_id" value="quiz_id">
								<input type="hidden" name="search_query" value="quiz_search">
							</form>
						</div>
					</div>
					<div class="search_user_left">
						<div align="left">
						<span style="width:32%;display:inline-block"><a href="quiz/info.jsp?quiz_id=<%=quizID%>"><%=name%></a></span>
						<span style="width:27%;display:inline-block"><%=name%></span>
						<span style="width:22%;display:inline-block"><%=sdf.format(date)%></span>
						</div>
					</div>
				</div>
		</div>
		<div class="no_results">No Quizzes Matched.</div>
		</div><br>
</body>
</html>
