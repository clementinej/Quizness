<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*, java.util.*" %>
<%@ page errorPage="../site/404.jsp" %>
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
   <meta charset="UTF-8" />
   <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <link rel="stylesheet" type="text/css" href="../css/admin.css" />
   <link rel="stylesheet" type="text/css" href="../css/style.css" />
   <link rel="stylesheet" type="text/css" href="../css/style_login.css" />
   <link href='http://fonts.googleapis.com/css?family=Open+Sans+Condensed:700,300|Montserrat' rel='stylesheet' type='text/css' />
</head>
<body>
   <!--top bar -->
   <div class="top">
      <span class="header-link"><a href="../home.jsp">Home</a></span>
      <span class="header-link"><a href="../create-quiz.jsp">Create Quiz</a></span>
      <span class="header-link"><a href="../social/compose-mail.jsp">Compose </a></span>
      <span class="header-link"><a href="../profile.jsp">Profile</a></span>
      <span class="header-link"><a href="../site/admin.jsp">Admin</a></span>
      <span class="header-link"><a href="../inbox.jsp">Inbox</a></span>
      <span class="header-link"><a href="../reported-quizzes.jsp">Reported Quizzes</a></span>
      <span class="header-link"><a href="../site/search.jsp">Search</a></span>
      <span class="header-link"><a href="LogoutServlet">Log Off</a></span>
   </div>
   <div>
      <div id="admin-title">
         <h1>Admin Page</h1>
      </div>
   </div>
   <script>
      $(document).ready(function() {
      	$("#cs-text").lettering().children('span').wrap('<span />');
      });
   </script>
   <div class ="inline">
      <div class="title-bar">Site Activity</div>
      <table class="admin-table">
         <tr>
            <td class="admin-subheader" class="content">Quizzes</td>
            <td class="space"></td>
            <td class="admin-subheader" class="content">Social</td>
         </tr>
      </table>
      <div class="admin-table">
         <table id="site-activity">
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
                  <td class="col2">Messages Sent</td>
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