<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*" %>
<%-- <%@ page errorPage="../site/404.jsp" %>--%>
<head>
   <link rel="stylesheet" type="text/css" href="/Quizness/css/style_login.css" />
</head>
<body>
   <div class="container">
   <form method="post" action="/Quizness/CreateServlet" id="signup">
      <div class="header">
      
      <%
      String intent = request.getParameter("intent");
      %>
         <h3>Create a Question - Multiple Answers</h3>
         <p>When you're done, click "Add Question" to return to creating your quiz.</p>
      </div>
      <div class="sep"></div>
      <div class="inputs">
      
      <%if(request.getParameter("error") != null) %> <h3> Question Format Error.  Please Try Again.</h3>
      
      	 <div>
                  <input type="name" name="question_text" style="width:500px;height:50px" placeholder="Question Text">
               </div>
         <h3>Solutions</h3>
               <table id="contents" class="inputs">
                  <th width="500">Solution Text</th>
                  <th>Score</th>
                  <tbody>
                     <tr>
                        <td><input id=answer_0 type="name" name="correct_answer_text_0" style="width:100%" placeholder="Solution 1"></td>
                        <td><input id=score_0 type="name" name="correct_answer_score_0" style="width:100%"/></td>
                     </tr>
                  </tbody>
                  <tfoot>
                     <tr>
                     	
                        <td align="center" class="terms"><a href="#" id="new_answer">Add An Answer For Your Solution</a></td>
                     </tr>
                  </tfoot>
               </table>
    <%  if(intent.equals("add to existing quiz")) {
    		int quizID = Integer.parseInt(request.getParameter("quizID"));
    		System.out.println("In question-answer: Quiz = " + quizID);
    %>
    		<input type="hidden" name="quiz_id" value="<%=quizID%>"/>
    <%
      }
      %>
               <input name="intent" type="hidden" value="<%=intent%>"/>
               <input name="question type" type="hidden" value="5"/>
              
               
               
         <br><input id="submit" type="submit" value="Add Question!"><!-- Store new quiz in database -->
      </div>	
   </form>
   </div>
    <script type="text/javascript">
    //synonyms doesn work
 	var numAnswers = 0;    
    var answer_button = document.getElementById("new_synonym");
    answer_button.addEventListener("click", function() {
    	var toInsert = document.getElementById("contents");
			numAnswers++;
			var displayNum = numAnswers + 1;
    	toInsert.insertAdjacentHTML('beforeend',	'<tr>' +
    			'<td><input type="name" name="correct_answer_text_'+numAnswers+'" style="width:100%" placeholder="Solution '+displayNum+'"></td>' +
    			'<td><input type="name" name="correct_answer_score_'+numAnswers+'" style="width:100%" "></td>' +
    			'<br>'+
    			'<td align="center" class="terms"><a href="#" id="new_synonym">Add Synonym</a></td>' +
    			'</tr>' );
    	toInsert.appendChild(newChild);
    	}); 
    </script>
    
    <script type="text/javascript">
     	var numAnswers = 0;    
         var answer_button = document.getElementById("new_answer");
         answer_button.addEventListener("click", function() {
         	var toInsert = document.getElementById("contents");
  			numAnswers++;
  			var displayNum = numAnswers + 1;
         	toInsert.insertAdjacentHTML('beforeend',	'<tr>' +
         			'<td><input type="name" name="correct_answer_text_'+numAnswers+'" style="width:100%" placeholder="Solution '+displayNum+'"></td>' +
         			'<td><input type="name" name="correct_answer_score_'+numAnswers+'" style="width:100%" "></td>' +
         			'</tr>' );
         	toInsert.appendChild(newChild);
         	}); 
         
      </script>
</body>
