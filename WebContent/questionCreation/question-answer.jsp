<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
   <head>
      <link rel="stylesheet" type="text/css" href="../../css/style_login.css" />
   </head>
   <body>
      <div class="container">
         <form method="post" action="../../CreateServlet" class="form" id="form1" >
            <div class="header">
               <h3>Create a Question</h3>
            </div>
            <p>Enter either a question in text or a picture in the image url field. If you leave the Image URL
               field blank, no image will be displayed.
            </p>
            <div class="inputs">
               <div><br>
                  <input type="name" name="question_text" style="width:500px;height:50px" placeholder="Question Text"></input>
               </div>
               <div id="img_url" class="terms">
                  <input type="name" name="question_image" placeholder="Image url" autofocus/>
               </div>
            <h3>Solutions</h3>
            <table id="contents" class="inputs">
               <th width="500">Solution Text</th>
               <th>Score</th>
               <tbody>
                  <tr>
                     <td><input type="name" name="correct_answer_0" style="width:100%" placeholder="Solution"></td>
                     <td><input type="name" name="correct_answer_score" style="width:320%" /></td>
                  </tr>
               </tbody>
               <tfoot>
                  <tr>
                     <td align="center" class="terms"><a href="#" id="new_option">Add Another Solution</a></td>
                  </tr>
                  <tr>
                     <td align="center" class="terms"><a href="#" id="new_synonym">Add A Synonym</a></td>
                  </tr>
               </tfoot>
            </table>
            <input name="intent" type="hidden" value="add question"/>
            <input name="question type" type="hidden" value="question-answer"/>
            <input type="submit" id="button-blue" value="Create Question"/>
         </form>
      </div>
       </div>
   </body>
   <script type="text/javascript">
      var button = document.getElementById("new_option");
      var solutionNumber = 0;
      button.addEventListener("click", function() {
    	  solutionNumber++;
          var body = document.getElementById("contents");
          body.insertAdjacentHTML('beforeend', '<tr>' +
                  '<td><input type="name" name="correct_answer_' + solutionNumber + '" style="width:100%" placeholder="Solution"></td>' +
                  '<td><input type="name" name="correct_answer_score_' + solutionNumber + '" value="0" style="width:320%" /></td>' +
              '</tr>');
          });
      var syn_button = document.getElementById("new_synonym");
      syn_button.addEventListener("click", function() {
      	var body = document.getElementById("contents");
      	body.insertAdjacentHTML('beforeend',	'<tr>' +
      			'<td><input type="name" name="correct_syn_' + solutionNumber + '" style="width:100%" placeholder="Synonym"></td>' +
      			'</tr>');
      	});
      
   </script>
</html>