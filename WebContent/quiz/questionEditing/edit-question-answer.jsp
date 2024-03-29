<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*" %>
<%@page import="java.util.*" %>
<%@page import="java.lang.*" %>
<%-- <%@ page errorPage="../site/404.jsp" %>--%>
<head>
   <link rel="stylesheet" type="text/css" href="/Quizness/css/style_login.css" />
</head>

<%
//place holders
String currQuestionText = "Current Text";
String currSolution = "Current Solution";
int currScore = 6;
	Question q = null;
	int currQuizID = -2;
	int qIndex = Integer.parseInt(request.getParameter("question_index"));

   if(request.getParameter("e") != null) {
	   boolean debug = false;
	   User user = (User) session.getAttribute("current user");
	   currQuizID = 59;//defualt to debugging quiz
	   System.out.println(request.getParameter("quiz_id"));
	   if(!debug) {
		   currQuizID = Integer.parseInt(request.getParameter("quiz_id"));
	   }
	   System.out.println(request.getParameter("question_index"));
	   
	   Quiz currQuiz = Quiz.getQuiz(currQuizID);
	   if(!user.isAdmin() && currQuiz.getCreatorID() != user.getUserID()) return;//should redirect

	   ArrayList<Question> questionList = currQuiz.getNonRandomQuestions(); 
	   q = questionList.get(qIndex);
	   System.out.println("Question Edit: Setup...");
   } else {
	   ArrayList<Question> questionList = (ArrayList<Question>) session.getAttribute("question list");
	   q = questionList.get(qIndex);
   }
   String questionText = q.getQuestion();
   double points = q.getMaxPoints();
   System.out.println(questionText);
   ArrayList<Set<String>> answers = q.getAnswer();


   %>
<body>
   <div class="container">
      <form method="post" action="/Quizness/EditServlet" id="signup">
         <div class="header">
            <h3>Edit Question</h3>
            <p>Click "Save" to keep your edits or "Back" to cancel.</p>
         </div>
         <div class="sep"></div>
         <div class="inputs">
         
      <%if(request.getParameter("error") != null) %> <h3> Question Format Error.  Please Try Again.</h3>

         
            <div>
               <input type="name" name="question_text" value="<%=questionText%>" style="width:100%" placeholder="<%=currQuestionText%>">
            </div>
            <h3>Solutions</h3>
            <table id="contents" class="inputs">
               <th style="width:400px">Solution Text</th>
               <th>Score</th>
               <tbody>
     <% //showAllAnswers()
     for(int i = 0; i < answers.size(); i++) {
    	Set<String> oneAnswerSet = answers.get(i);	 
     	Iterator<String> iter = oneAnswerSet.iterator();
     	int curSynonym = 0;
     	while(iter.hasNext()) {
     		String oneAnswer = iter.next(); 
     		System.out.println(oneAnswer);
     %>          
                  <tr>
                     <td><input type="name" name="correct_answer_text" value="<%=oneAnswer%>" style="width:100%" placeholder="<%=currSolution%>"></td>
     <% 
     		if(curSynonym == 0) {
     			
     %>          
                     <td><input type="name" name="correct_answer_score" value="<%=points%>" style="width:100%" placeholder="<%=currScore%>"/></td>
     <% 
     		curSynonym++;
     		}
    	}
     }
     %>
                  </tr>
               </tbody>
               <tfoot>
                  <tr>
                     <td align="center" class="terms"><a href="#" id="new_synonym">Add A Synonym</a></td>
                  </tr>
               </tfoot>
            </table>
            <input name="intent" type="hidden" value="edit question"/>
            <input name="question type" type="hidden" value="1"/>
            <input name="question_index" type ="hidden" value="<%=qIndex%>"/>
            <input name="quiz_id" type="hidden" value="<%=currQuizID%>"/>
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
      			'<td><input type="name" name="correct_answer_text" style="width:100%" placeholder="Synonym"></td>' +
      			'</tr>');
      	body.appendChild(newChild);
      	});   
   </script>
</body>