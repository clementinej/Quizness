<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*, model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Questions</title>
</head>
<body>

<%
ArrayList<Question> questionList = (ArrayList<Question>) session.getAttribute("question list");	
session.setAttribute("question list", questionList);

for(int i = 0; i < questionList.size(); i++) {
	Question q = questionList.get(i);
	String text = q.getQuestion();
	ArrayList<Set<String>> solution = q.getAnswer();
%>
	<p><%=i + 1%>.</p>
	<p>Question Text: "<%=text%>"</p>
	<p>
	<% 
	//for each solution
	for(int solIndex = 0; solIndex < solution.size(); solIndex++) {
	%>
	Solution: 
	<%
		Set<String> oneSolution = solution.get(solIndex);
		//for each synonym of each solution
		Iterator<String> iter = oneSolution.iterator();
		while(iter.hasNext()) {
			String s = iter.next();
			%>
			"<%=s%>"<%if(iter.hasNext())%>,
			<%
		}
	}
	%>
<%	
}
%>
	
	</p>

<input type="submit" id="submit" value="Back" href="create-quiz.jsp">Back</input>
</body>
</html>