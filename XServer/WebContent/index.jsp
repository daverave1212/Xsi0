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
	var parameters = document.getElementById("in").value;
	alert("Submitting");
    var http = new XMLHttpRequest();
    http.open("POST", "http://localhost:8080/XServer/XServer?" + parameters, true);
    http.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    var params = "search=" + "HELLOIMAPARAMETEUR"; // probably use document.getElementById(...).value
    http.send(params);
    http.onload = function() {
        alert(http.responseText);
    }
}

function Login() {
	var parameters = document.getElementById("username").value;
    var http = new XMLHttpRequest();
    http.open("POST", "http://localhost:8080/XServer/XServer?username=" + parameters + "&action=LOGIN&password=any", true);
    http.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    var params = "search=" + "HELLOIMAPARAMETEUR"; // probably use document.getElementById(...).value
    http.send(params);
    http.onload = function() {
        alert(http.responseText);
    }
}

function Play() {
	var parameters = document.getElementById("username").value;
    var http = new XMLHttpRequest();
    http.open("POST", "http://localhost:8080/XServer/XServer?username=" + parameters + "&action=PLAY&password=any", true);
    http.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    var params = "search=" + "HELLOIMAPARAMETEUR"; // probably use document.getElementById(...).value
    http.send(params);
    http.onload = function() {
        alert(http.responseText);
    }
}

function IsGameReady() {
	var parameters = document.getElementById("username").value;
    var http = new XMLHttpRequest();
    http.open("POST", "http://localhost:8080/XServer/XServer?username=" + parameters + "&action=ISGAMEREADY&password=any", true);
    http.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    var params = "search=" + "HELLOIMAPARAMETEUR"; // probably use document.getElementById(...).value
    http.send(params);
    http.onload = function() {
        alert(http.responseText);
    }
}

function MyPiece() {
	var parameters = document.getElementById("username").value;
    var http = new XMLHttpRequest();
    http.open("POST", "http://localhost:8080/XServer/XServer?username=" + parameters + "&action=MYPIECE&password=any", true);
    http.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    var params = "search=" + "HELLOIMAPARAMETEUR"; // probably use document.getElementById(...).value
    http.send(params);
    http.onload = function() {
        alert(http.responseText);
    }
}

function IsMyTurn() {
	var parameters = document.getElementById("username").value;
    var http = new XMLHttpRequest();
    http.open("POST", "http://localhost:8080/XServer/XServer?username=" + parameters + "&action=ISMYTURN&password=any", true);
    http.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    var params = "search=" + "HELLOIMAPARAMETEUR"; // probably use document.getElementById(...).value
    http.send(params);
    http.onload = function() {
        alert(http.responseText);
    }
}

function GetBoard() {
	var parameters = document.getElementById("username").value;
    var http = new XMLHttpRequest();
    http.open("POST", "http://localhost:8080/XServer/XServer?username=" + parameters + "&action=GETBOARD&password=any", true);
    http.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    var params = "search=" + "HELLOIMAPARAMETEUR"; // probably use document.getElementById(...).value
    http.send(params);
    http.onload = function() {
        alert(http.responseText);
    }
}

function Who() {
	var parameters = document.getElementById("username").value;
    var http = new XMLHttpRequest();
    http.open("POST", "http://localhost:8080/XServer/XServer?username=" + parameters + "&action=WHO&password=any", true);
    http.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    var params = "search=" + "HELLOIMAPARAMETEUR"; // probably use document.getElementById(...).value
    http.send(params);
    http.onload = function() {
        alert(http.responseText);
    }
}

function Users() {
	var parameters = document.getElementById("username").value;
    var http = new XMLHttpRequest();
    http.open("POST", "http://localhost:8080/XServer/XServer?username=" + parameters + "&action=USERS&password=any", true);
    http.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    var params = "search=" + "HELLOIMAPARAMETEUR"; // probably use document.getElementById(...).value
    http.send(params);
    http.onload = function() {
        alert(http.responseText);
    }
}

function Move() {
	var parameters = document.getElementById("username").value;
    var http = new XMLHttpRequest();
    http.open("POST", "http://localhost:8080/XServer/XServer?username=" + parameters + "&action=MOVE&square=3", true);
    http.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    var params = "search=" + "HELLOIMAPARAMETEUR"; // probably use document.getElementById(...).value
    http.send(params);
    http.onload = function() {
        alert(http.responseText);
    }
}

</script>
<body>
	Username
	<input id = "username" value = "">
	<button onclick = "Login()">Login</button>
	<button onclick = "Play()">Play</button>
	<button onclick = "IsGameReady()">Is Game Ready?</button>
	<button onclick = "MyPiece()">My Piece</button>
	<button onclick = "Who()">Who is my enemy?</button>
	<button onclick = "Users()">Users?</button>
	<button onclick = "IsMyTurn()">Is My Turn?</button>
	<button onclick = "GetBoard()">Get Board</button>
	<button onclick = "Move()">Move 3</button>
</body>
</html>