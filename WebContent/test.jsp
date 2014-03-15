<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Insert title here</title>
 <head>
    <script type="text/javascript">
        function InsertHTML (select) {//gets select
            var selected = select.selectedIndex;
            var where = select.options[selected].text;
            var relTo = document.getElementById ("relTo");
            var htmlToInsert = "The position is <b>" + where + "</b>.";

            if (relTo.insertAdjacentHTML) {        // Internet Explorer, Opera, Google Chrome and Safari
                relTo.insertAdjacentHTML (where, htmlToInsert);
            }
            else {
                alert ("Your browser does not support the insertAdjacentHTML method!");
            }
        }
    </script>
</head>
<body>
    <div id="relTo" style="width:300px; background:#e0d0a0;">
        Answer 1
    </div>
    <br /><br />
    Change the selected item to insert HTML formatted text relative to the destination element.
    <select onchange="InsertHTML (this);">
        <option>beforeBegin</option>
        <option>afterBegin</option>
        <option>beforeEnd</option>
        <option selected="selected">afterEnd</option>
    </select>
</body>
</html>