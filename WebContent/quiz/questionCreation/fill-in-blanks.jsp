<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
   <link rel="stylesheet" type="text/css" href="../../css/normalize.css" />
      <link rel="stylesheet" type="text/css" href="../../css/component.css" />
      <link rel="stylesheet" type="text/css" href="../../css/create-question.css" />
</head>
<div id="form-main">
  <div id="form-div">
<form class="form" id="form1" action="CreateServlet" method="post"> 
<h3>Question</h3>
<table width="700" cellspacing="3" cellpadding="3" border="0">
	<tr>
		<th>Question Text</th>
		<td>
			<textarea name="question_text" style="width:500px;height:80px;"></textarea>
		</td>
	</tr>
</table>
<h3>Solutions</h3>
<table id="tblOptions" width="700" cellspacing="6" cellpadding="6" border="0">
	<thead>
		<tr>
			<th>Blank #</th>
			<th width="400">Solution Text</th>
			<th>Score</th>
		</tr>
	</thead>
	<tbody id="contents">
<tr>
	<td><input type="text" name="correct_answer_blank" style="width:100%" value=""></td>
	<td><input type="text" name="correct_answer_key" style="width:100%" value=""></td>
	<td><input type="text" name="correct_answer_score" style="width:100%" value="" /></td>
</tr>
	</tbody>
	<tfoot>
		<tr>
			<td align="center" colspan="3"><a href="#" class="add_new_option" id="new_option">Add New Option</a></td>
		</tr>
				<tr>
			<td align="center" colspan="3"><a href="#" class="add_new_option" id="new_synonym">Add New Synonym</a></td>
		</tr>
	</tfoot>
</table>
<input id="button-blue" type="submit" value="Create Question">
</form></div></div>
<script type="text/javascript">

var button = document.getElementById("new_option");
button.addEventListener("click", function() {
	var body = document.getElementById("contents");
	body.insertAdjacentHTML('beforeend',	'<tr>' +
			'<td><input type="text" name="correct_answer_blank" value="1" style="width:100%"></td>' + 
			'<td><input type="text" name="correct_answer_key" style="width:100%"></td>' +
			'<td><input type="text" name="correct_answer_score" value="1" style="width:100%" /></td>' +
			'</tr>');
	body.appendChild(newChild);
	});
	
var syn_button = document.getElementById("new_synonym");
syn_button.addEventListener("click", function() {
	var body = document.getElementById("contents");
	body.insertAdjacentHTML('beforeend',	'<tr>' +
			'<td><input type="text" name="correct_syn_blank" value="1" style="width:100%"></td>' + 
			'<td><input type="text" name="correct_syn_key" style="width:100%"></td>' +
			'<td><input type="text" name="correct_syn_score" value="1" style="width:100%" /></td>' +
			'</tr>');
	body.appendChild(newChild);
	});
</script>