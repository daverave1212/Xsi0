function Player(u,p){
   this.user=u;
   this.pass=p;
   this.piece=new String();
   this.board=new String();
}
var player=new Player();
var link="http://5.12.112.34:8080/XServer/XServer?";
var id;

function buildBoard(){
   var buttons=new Array(9);
   var divB=document.createElement('div');
   divB.id="board";
   for(var i=0;i<9;i++){
      console.log(i);
      buttons[i]=document.createElement('button');
      buttons[i].width="50px";
      buttons[i].height="50px";
      buttons[i].style.backgroundColor="grey";
      buttons[i].style.border="1px solid black";
      buttons[i].style.float="left";
      //if(player.board[i]!=='N'){
         //buttons[i].innerHTML=player.board[i];
      //}
      if(i%3==0){
         divB.appendChild(document.createElement('br'));
         console.log[i];
      }
      divB.appendChild(buttons[i]);
      //buttons[i].onclick=function(){}
   }
   document.body.appendChild(divB);
   console.log("END OF BOARD");
}


function tableTop(){
   getPiece();
   getTurn();
   getBoard();
}

function getPiece(){
   var piece=new XMLHttpRequest();
   var parameters="action=MYPIECE&username="+player.user;
   piece.open("POST", link, true);
   piece.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
   piece.send(parameters);
   piece.onload=function() {
      if(piece.responseText==="X"){
         player.piece="X";
         console.log("X");
      }
      else if(piece.responseText==="O"){
         player.piece="O";
         console.log("O");
      }
      else {
         console.log("WTF NO PIECE");
      }
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
      }
      else if(turn.responseText==="YES"){
         player.turn=1;
         console.log("YES");
      }
      else {
         console.log(turn.responseText+"WTF");
      }
   }
}

function getBoard(){
   var board=new XMLHttpRequest();
   var parameters="action=GETBOARD&username="+player.user;
   board.open("POST",link,true);
   board.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
   board.send(parameters);
   board.onload=function(){
      player.board=board.responseText;
      console.log(player.board);
      buildBoard();
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
            play();
         }
          else
            alert("Login incorect");
      }
   }
}
