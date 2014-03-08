<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*" %>
<%@page import="java.util.*" %>

<head>
   <link rel="stylesheet" type="text/css" href="../../css/style_login.css" />
</head>
<%
   //User user = (User) session.getAttribute("current user");
   //int currQuizID = Integer.parseInt(request.getParameter("quiz_id"));
   //Quiz currQuiz = Quiz.getQuiz(currQuizID);
   //if(!user.isAdmin() && currQuiz.getCreatorID() != user.getUserID()) return;
   String currQuestionTitle = "curr title";
   String currQuestionText = "curr text";
   String currSolution = "curr solution";
   int currScore = 6;
   %>
<body>
   <div class="container">
      <form method="post" action="../../EditServlet" id="signup">
         <div class="header">
            <h3>Edit Question</h3>
            <p>Click "Save" to keep your edits or "Back" to cancel.</p>
         </div>
         <div class="sep"></div>
         <div class="inputs">
            <div>
               <input type="name" name="title" style="width:500px;height:50px" placeholder="<%=currQuestionTitle%>">
            </div>
            <div>
               <input type="name" name="question_text" style="width:500px;height:50px" placeholder="<%=currQuestionText%>">
            </div>
            <h3>Solutions</h3>
            <table id="contents" class="inputs">
               <th style="width:400px">Solution Text</th>
               <th>Score</th>
               <tbody>
                  <tr>
                     <td><input type="name" name="correct_answer_text" style="width:100%" placeholder="<%=currSolution%>"></td>
                     <td><input type="name" name="correct_answer_score" style="width:100%" placeholder="<%=currScore%>"/></td>
                  </tr>
               </tbody>
               <tfoot>
                  <tr>
                     <td align="center" class="terms"><a href="#" id="new_synonym">Add A Synonym</a></td>
                  </tr>
               </tfoot>
            </table>
            <input name="intent" type="hidden" value="edit question"/>
            <input name="question type" type="hidden" value="2"/>
            <input id="submit" name="save" type="submit" value="Save Changes">
            <input id="submit" name="delete" type="submit" value="Delete Question">			
         </div>
      </form>
   </div>
   <script type="text/javascript">
      var syn_button = document.getElementById("new_synonym");
      syn_button.addEventListener("click", function() {
      	var body = document.getElementById("contents");
      	body.insertAdjacentHTML('beforeend',	'<tr>' +
      			'<td><input type="name" name="correct_syn_text" style="width:100%" placeholder="Synonym"></td>' +
      			'<td><input type="name" name="correct_syn_score" style="width:100%" /></td>' +
      			'</tr>');
      	body.appendChild(newChild);
      	});   
   </script>
</body>