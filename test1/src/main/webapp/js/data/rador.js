var rador = echarts.init(document.getElementById('rador'));
var radorRequest;
if (window.XMLHttpRequest){
    radorRequest = new XMLHttpRequest();
}
else{
    radorRequest = new ActiveXObject("Microsoft.XMLHTTP");
}

radorRequest.open("GET", "http://localhost:8080/test1/rador", true);
radorRequest.responseType = 'json';
radorRequest.send();
radorRequest.onreadystatechange = function(){
    if(radorRequest.readyState == 4 && radorRequest.status == 200){
        var radorData = radorRequest.response;
        console.log(radorData);
        var RadorOption = {
            title: {
                text: '雷达图'
            },
            legend: {
                show: true,
                orient: "vertical",
                left: "+10px",
                top: "+150px",
            },
            radar: {
                shape: 'circle',
                indicator: radorData.domain
            },
            series: [{
                    type: 'radar',
                    data: radorData.data
                }]
        };

        rador.setOption(RadorOption);

    }
}