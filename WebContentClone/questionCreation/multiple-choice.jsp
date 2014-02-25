<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
   <link rel="stylesheet" type="text/css" href="../css/normalize.css" />
      <link rel="stylesheet" type="text/css" href="../css/component.css" />
      <link rel="stylesheet" type="text/css" href="../css/create-question.css" />
</head>
<div id="form-main">
  <div id="form-div">
    <form class="form" id="form1" method="post" action="/CreateServlet">
      
      <!-- When the user is done creating a question, submit should post to the CreateServlet, 
	which adds the new question object to the List<Question> associated with the quiz -->
<h3>Question</h3>
<h5>Enter your question below.</h5>
<table>
	<tr>
		<th>Enter text:</th>
		<td>
			<textarea name="question_text" style="width:600px;height:80px;"></textarea>
		</td>
	</tr>
	<tr>
		<th>Multiple Selections</th>
		<td>
			<p><input value="1" type="checkbox" name="multi_select">
			This will mean using check boxes instead of radio buttons.</p>
		</td>
	</tr>
</table>
<h3>Correct Answers</h3>
<table id="correct_contents" width="700" cellspacing="6" cellpadding="6" border="0">
	<tr>
		<th width="500">Correct Options</th>
		<th>Score</th>
	</tr>
	<tbody>
		<tr>
			<td><input type="text" name="correct_answer_key" style="width:100%" value=""></td>
			<td><input type="text" name="correct_answer_score" style="width:100%" value="" /></td>
		</tr>
	</tbody>
	<tfoot>
		<tr>
			<td align="center" colspan="3"><a href="#" id="new_option">Add Another Correct Answer</a></td>
		</tr>
	</tfoot>
</table>
<h3>Wrong Answers</h3>
<table id="incorrect_contents" width="700" cellspacing="6" cellpadding="6" border="0">
	<tr>
		<th width="500">Incorrect Options</th>
		<th>Score</th>
	</tr>
	<tbody>
		<tr>
			<td><input type="text" name="incorrect_answer_key" style="width:100%" value=""></td>
			<td><input type="text" name="incorrect_answer_score" style="width:100%" value="" /></td>
		</tr>
	</tbody>
	<tfoot>
		<tr>
			<td align="center" colspan="3"><a href="#" id="new_wrong_answer" >Add Another Wrong Answer</a></td>
		</tr>
	</tfoot>
</table>
<input type="submit" id="button-blue" value="Create Question"></form>
  </div></div>
<script type="text/javascript">

var button = document.getElementById("new_option");
button.addEventListener("click", function() {
	var body = document.getElementById("correct_contents");
	body.insertAdjacentHTML('beforeend', '<tr>' +
			'<td><input type="text" name="correct_answer_key" style="width:100%"></td>' +
			'<td><input type="text" name="correct_answer_score" value="1" style="width:100%" /></td>' +
		'</tr>');
	body.appendChild(newChild);
	});

var button = document.getElementById("new_wrong_answer");
button.addEventListener("click", function() {
	var body = document.getElementById("incorrect_contents");
	body.insertAdjacentHTML('beforeend','<tr>' +
			'<td><input type="text" name="incorrect_answer_key" style="width:100%"></td>' +
			'<td><input type="text" name="incorrect_answer_score" value="0" style="width:100%" /></td>' +
			'</tr>');
	body.appendChild(newChild);
	});

	</script>