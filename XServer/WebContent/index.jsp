<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
function submitForm() {
	alert("Submitting");
    var http = new XMLHttpRequest();
    http.open("POST", "http://localhost:8080/XServer/XServer", true);
    http.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    var params = "search=" + "HELLOIMAPARAMETEUR"; // probably use document.getElementById(...).value
    http.send(params);
    http.onload = function() {
        alert(http.responseText);
    }
}
</script>
<body>
	this is localhost:8080/XServer
	Ello woldasdasdasd
	<button onclick = "submitForm()">Test Ajax</button>
</body>
</html>