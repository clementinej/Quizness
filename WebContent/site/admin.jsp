<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*, java.util.*" %>
<%--<%@ page errorPage="../site/404.jsp" %> --%>

<%	
User currentUser = (User) session.getAttribute("current user");
System.out.println("is admin" + currentUser.isAdmin());
if (!currentUser.isAdmin()){
	System.out.println("redirecting to login page");
	response.sendRedirect("../home.jsp");
}
System.out.println("The current user is: " + currentUser.getUserName());
java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat();
int numQuizzes = Site.getTotalNumberOfQuizzes();
int numUsers = Site.getTotalNumberOfUsers();
int numTags = Site.getTotalNumberTags();
int numQuizzesTaken = Site.getTotalNumberQuizzesTaken();

int numFriends = Site.getNumFriendships();
int numMsgsSent = Site.getNumMsgs();
int numChallenges = Site.getNumChallenges();
int numFriendReqs = Site.getNumReqs();
%>

<head>
    <link rel="stylesheet" type="text/css" href="../css/admin.css" />
      <meta charset="UTF-8" />
      <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="stylesheet" type="text/css" href="../css/style.css" />
      <link rel="stylesheet" type="text/css" href="../css/style_login.css" />
      <link href='http://fonts.googleapis.com/css?family=Open+Sans+Condensed:700,300|Montserrat' rel='stylesheet' type='text/css' />
    <style>
         .link {
         color:#fffff;
         font-weight:bold;
         text-align:center;
         }
         .links {
         padding-left:40px;
         }
         .header-link {
		padding-left:50px;
		font-weight:bold;	
		}
		.right {
		color:#fffff;
		float:right;
		margin-right:25px;
		font-weight:bold;
		font-size:100%;
		}
		
		#admin-title {
		padding-top:20px;
		padding-left:20px;
		}
		
      </style>
</head>
<body>
  <!--top bar -->
      <div class="top">
         <span class="header-link"><a href="../create-quiz.jsp">Create Quiz</a></span>
         <span class="header-link"><a href="../social/compose-mail.jsp">Compose </a></span>
         <span class="header-link"><a href="../profile.jsp">Profile</a></span>
         <span class="header-link"><a href="../site/admin.jsp">Admin</a></span>
         <span class="header-link"><a href="../inbox.jsp">Inbox</a></span>
          <span class="header-link"><a href="../site/search.jsp">Search</a></span>
      </div>

  <div>
      <div id="admin-title"><h2>Admin Page</h2></div>
      </div>
      <script>
         $(document).ready(function() {
         	$("#cs-text").lettering().children('span').wrap('<span />');
         });
      </script>
		<div class ="inline">
			<div class="title-bar"  style="margin-top:20px">Activity Summary</div>
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
