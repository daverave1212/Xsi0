<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script id = "Dave">
        var serverIPAddress;

        function setupIP(){
            //serverIPAddress = prompt("Enter server's IP", "");
            serverIPAddress = "localhost";
            link = "http://" + serverIPAddress + ":8080/XServer/XServer?";}

    </script>
    <script id = "Andrei">
        function Player(u,p){
            this.user=u;
            this.pass=p;
            this.piece=new String();
            this.board=new String();
            this.turn=0;
            this.status=new String();
        }
        var player=new Player();
        var link;
        var id;

        function buildBoard(v){
            var test=document.getElementById("board");
            console.log(test);
            if(test)
                document.body.removeChild(test);
            var buttons=new Array(9);
            var divB=document.createElement('div');
            var bWi=50;
            var bb=1;
            divB.id="board";
            divB.style.width=3*bWi+6*bb+"px";
            for(let i=0;i<9;i++){
                buttons[i]=document.createElement('button');
                buttons[i].style.width=50+"px";
                buttons[i].style.height="50px";
                buttons[i].style.backgroundColor="grey";
                buttons[i].style.border=bb+"px solid black";
                buttons[i].style.float="left";
                if(player.board[i]!=='N'){
                    buttons[i].innerHTML=player.board[i];
                }
                divB.appendChild(buttons[i]);
                console.log(v+" AS")
                if(v==1){
                    buttons[i].onclick=function(){
                        buttons[i].innerHTML=player.piece;
                        sendMove(i);
                    }
                }
            }
            document.body.appendChild(divB);
        }


        function tableTop(){
            getPiece();
            id=setInterval(function () {
                getTurn();
            },3000);

        }

        function sendMove(i){
            console.log(i+"HHH");
            var move=new XMLHttpRequest();
            var parameters="action=MOVE&username="
                +player.user+"&square="+i;
            move.open("POST", link, true);
            move.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            move.send(parameters);
            move.onload=function() {
                if(move.responseText==="YOUWIN"){
                    player.status="YOUWIN";
                    clearInterval(id);
                    alert('YOU WON!');
                    resetBoard();
                }
                else if(move.responseText==="ENEMYTURN"){
                    player.status="WAIT";
                    getBoard();}
                else if(move.responseText === "YOULOSE"){
                    getBoard();
                    alert("xD this game is only for pro noob")
                }
                console.log(move.responseText);
            }
        }

        function getPiece(){
            var piece=new XMLHttpRequest();
            var parameters="action=MYPIECE&username="+player.user;
            piece.open("POST", link, true);
            piece.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            piece.send(parameters);
            piece.onload=function() {
                var pi=document.createElement('p');
                if(piece.responseText==="X"){
                    player.piece="X";
                    pi.innerHTML="X";
                    console.log("X");
                }
                else if(piece.responseText==="O"){
                    player.piece="O";
                    pi.innerHTML="O";
                    console.log("O");
                }
                else {
                    console.log("NO PIECE");
                }
                document.body.appendChild(pi);
            }
        }

        function getTurn(){
            var turn=new XMLHttpRequest();
            var parameters="action=ISMYTURN&username="+player.user;
            turn.open("POST",link,true);
            turn.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            turn.send(parameters);
            turn.onload=function(){
                if(turn.responseText==="NO"){
                    player.turn=0;
                    console.log("NO");
                    getBoard(0);
                }
                else if(turn.responseText==="YES"){
                    player.turn=1;
                    console.log("YES");
                    getBoard(1);
                }
                else {
                    console.log(turn.responseText);
                }
            }
        }

        function getBoard(v){
            var board=new XMLHttpRequest();
            var parameters="action=GETBOARD&username="+player.user;
            board.open("POST",link,true);
            board.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            board.send(parameters);
            board.onload=function(){
                player.board=board.responseText;
                console.log(player.board);
                buildBoard(v);


            }
        }

        function waiting(){
            var spam=new XMLHttpRequest();
            var parameters="action=ISGAMEREADY&username="+player.user;
            spam.open("POST", link, true);
            spam.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            spam.send(parameters);
            spam.onload=function() {
                if(spam.responseText==="YES"){
                    console.log("E bine spam");
                    clearInterval(id);
                    tableTop();
                }
                else
                    console.log("Not yet");
            }
        }

        function play(){
            var play=document.createElement('button');
            play.innerHTML="Play";
            play.onclick=function(){
                var pl=new XMLHttpRequest();
                var parameters="action=PLAY&username="+player.user;
                pl.open("POST", link, true);
                pl.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                pl.send(parameters);
                pl.onload=function() {
                    if(pl.responseText==="OK"){
                        console.log("E bine play");
                        id=setInterval(waiting,5000,stop);
                    }
                    else
                        console.log("PLAY NU MERGE FFF GRAV");
                }
            }
            document.body.appendChild(play);
        }

        window.onload=function(){
            setupIP();

            var loginButton=document.getElementById('login');
            loginButton.onclick=function(){
                var form=document.getElementsByTagName('form')[0];
                player.user=document.getElementById('user').value;
                player.pass=document.getElementById('pass').value;
                console.log(player.user);
                console.log(player.pass);
                var parameters="username="+player.user+"&password="+player.pass+"&action=LOGIN";
                var loginReq=new XMLHttpRequest();
                loginReq.open("POST", link, true);
                loginReq.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                loginReq.send(parameters);
                loginReq.onload=function() {
                    if(loginReq.responseText==="LOGINACCEPTED"){
                        console.log("E bine");
                        document.body.removeChild(form);
                        document.body.removeChild(loginButton);
                        document.body.removeChild(signUp);
                        play();
                    }
                    else
                        console.log(loginReq.responseText);
                }
            }

            var signUp=document.getElementById('signup');
            signUp.onclick=function(){
                var form=document.getElementsByTagName('form')[0];
                player.user=document.getElementById('user').value;
                player.pass=document.getElementById('pass').value;
                console.log(player.user);
                console.log(player.pass);
                var parameters="username="+player.user+"&password="+player.pass+"&action=REGISTER";
                var loginReq=new XMLHttpRequest();
                loginReq.open("POST",link, true);
                loginReq.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                loginReq.send(parameters);
                loginReq.onload=function() {
                    console.log(loginReq.responseText);
                    if(loginReq.responseText==="REGISTERSUCCESS"){
                        console.log("REGISTER GUD");
                        loginButton.onclick();
                    }
                    else
                        console.log(link+parameters);
                }
            }
        }

    </script>
</head>
<body>
<form>
    Username<br>
    <input id="user" type="text" name="username" value="user"><br>
    Password:<br>
    <input id="pass" type="text" name="password" value="pass"><br><br>
</form>
<button id="login">Login</button>
<button id="signup">Sign Up</button>
</body>
</html>