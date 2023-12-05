var keyword = echarts.init(document.getElementById("keyword"));
var keywordRequest;
if (window.XMLHttpRequest)
{
    keywordRequest = new XMLHttpRequest();
}
else
{
    keywordRequest = new ActiveXObject("Microsoft.XMLHTTP");
}
keywordRequest.open("GET", "http://localhost:8080/test1/WorldCloud", true);

keywordRequest.responseType = 'json';
keywordRequest.send();

var dk;

keywordRequest.onreadystatechange = function(){
    if(keywordRequest.readyState == 4 && keywordRequest.status == 200){
        dk = keywordRequest.response;

        dk.sort(function(a, b){
            return b.value - a.value;
        });

        var len = Math.min(50, dk.length);
        dk = dk.slice(0, len);

        var OptionKeyword = {
            tooltip: {},
            title:{
                text: '关键词词云'
            },
            series: [{
                type: 'wordCloud',
                gridSize: 2,
                sizeRange: [12, 50],
                rotationRange: [0, 0],
                shape: 'circle',
                width: 600,
                height: 400,
                drawOutOfBound: true,
                textStyle: {
                    color: function() {
                        return 'rgb(' + [
                            Math.round(Math.random() * 255),
                            Math.round(Math.random() * 255),
                            Math.round(Math.random() * 255)
                        ].join(',') + ')';
                    }
                },
                emphasis: {
                    textStyle: {
                        shadowBlur: 10,
                        shadowColor: '#333'
                    }
                },
                data: dk
            }],
        }
        keyword.setOption(OptionKeyword);
    }
}

