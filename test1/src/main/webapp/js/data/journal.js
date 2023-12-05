var Journal = echarts.init(document.getElementById("Journal"));

var JournalRequest;
if (window.XMLHttpRequest){
    JournalRequest = new XMLHttpRequest();
}
else{
    JournalRequest = new ActiveXObject("Microsoft.XMLHTTP");
}

JournalRequest.open("GET", "http://localhost:8080/test1/Journal", true);
JournalRequest.responseType = 'json';
JournalRequest.send();

var JournalData;

JournalRequest.onreadystatechange = function(){
    if(JournalRequest.readyState == 4 && JournalRequest.status == 200){
        JournalData = JournalRequest.response;

        JournalData.sort(function(a, b){
            return b.value - a.value;
        });

        var len = Math.min(10, JournalData.length);
        JournalData = JournalData.slice(0, len);

        var JournalOption = {
                title : {
            		text :"期刊发文比",
            		textStyle :{
            			"color":"black",
            			"fontWeight":"bolder",
            			"fontFamily":"KaiTi",
            			"fontSize":"20"
            		},
            		textAlign:"center",
            		left :"50%"
            	},
            	legend :{
            		show:true,
            		orient:"vertical",
            		left:"0px",
            		top:"+80px",
            		height:"300",
            		width:"80"
            	},
            	series:{
                		type:"pie",
                		radius:['30%', '60%'],
                		top:"+50px",
                		left:"+150px",
                		height:"300",
                		data :JournalData
                }
        }
        Journal.setOption(JournalOption);
    }
}