var insLink = echarts.init(document.getElementById('insLink'));
var insLinkRequest;
if (window.XMLHttpRequest){
    insLinkRequest = new XMLHttpRequest();
}
else{
    insLinkRequest = new ActiveXObject("Microsoft.XMLHTTP");
}

insLinkRequest.open("GET", "http://localhost:8080/test1/insLink", true);
insLinkRequest.responseType = 'json';
insLinkRequest.send();

var insLinks;
var insData;

insLinkRequest.onreadystatechange = function(){
    if(insLinkRequest.readyState == 4 && insLinkRequest.status == 200){
        var linkAndSize = insLinkRequest.response;
        insData = linkAndSize.size;
        insLinks = linkAndSize.link;
        var optionIns = {
            title: {
                text: '机构合作网络',
                left: 'center'
            },
            tooltip: {},
            animationDurationUpdate: 1500,
            animationEasingUpdate: 'quinticInOut',
            series: [{
                type: 'graph',
                layout: 'force',
                symbolSize: 50,
                roam: true,
                label: {
                    show: true
                },
                edgeSymbol: ['none', 'arrow'],
                edgeSymbolSize: [0, 10],
                edgeLabel: {
                    fontSize: 10
                },
                force: {
                    repulsion: 1000,
                    edgeLength: 100
                },
                data: insData,
                links: insLinks

            }]
        };
        insLink.setOption(optionIns);
    }
}