var usernameRequest;
if (window.XMLHttpRequest){
    usernameRequest = new XMLHttpRequest();
}
else{
    usernameRequest = new ActiveXObject("Microsoft.XMLHTTP");
}

usernameRequest.open("GET", "http://localhost:8080/test1/GetName", true);
usernameRequest.send();
console.log('ok1')
usernameRequest.onreadystatechange = function(){
    if(usernameRequest.readyState == 4 && usernameRequest.status == 200){
        var username = usernameRequest.response;
        console.log(username)
        document.getElementsByClassName("text")[0].innerHTML = '欢迎： ' + username;
    }
}