<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
   <head>
     <link rel="stylesheet" type="text/css" href="../css/normalize.css" />
      <link rel="stylesheet" type="text/css" href="../css/component.css" />
      <link rel="stylesheet" type="text/css" href="../css/create-question.css" />
   </head>
   <body>
 	<div id="form-main">
 	 <div id="form-div">
         <span class="scroll"></span>
         <h3>Question</h3>
         <form method="post" action="/CreateServlet" class="form" id="form1" >
            <p>Enter either a question in text or a picture in the image url field. If you leave the Image URL
               field blank, no image will be displayed.
            </p>
            <br>
            <table>
               <tr>
                  <th>Question Text</th>
                  <td>
                     <textarea name="question_text" style="width:500px;height:80px;"></textarea>
                  </td>
               </tr>
               <tr>
                  <th>Image URL</th>
                  <td><input name="question_image" type="text" value="" style="width:500px"></td>
               </tr>
            </table>
            <h3>Solutions</h3>
            <table id="contents" class="table1">
               <tr>
                  <th width="500">Solution Text</th>
                  <th>Score</th>
               </tr></br>
               <tbody>
                  <tr>
                     <td><input type="text" name="correct_answer_key" style="width:100%" value=""></td>
                     <td><input type="text" name="correct_answer_score" style="width:100%" value="" /></td>
                  </tr>
               </tbody>
               
               <tfoot>
                  <tr>
                     <td align="center" colspan="2"><a href="#" id="new_option">Add Another Solution</a></td>
                  </tr>
               </tfoot>
            </table>
            <input type="submit" id="button-blue" value="Create Question"/>
         </form>
         </div>
         </div>
         </table>
      </div>
   </body>
   <script type="text/javascript">
      var button = document.getElementById("new_option");
      button.addEventListener("click", function() {
          var body = document.getElementById("contents");
          body.insertAdjacentHTML('beforeend', '<tr>' +
                  '<td><input type="text" name="correct_answer_key" style="width:100%"></td>' +
                  '<td><input type="text" name="correct_answer_score" value="0" style="width:100%" /></td>' +
              '</tr>');
          body.appendChild(newChild);
          });
      
   </script>
</html>