var authorLink = echarts.init(document.getElementById('AuthorLink'), 'macarons');
var authorLinkRequest;
if (window.XMLHttpRequest){
    authorLinkRequest = new XMLHttpRequest();
}
else{
    authorLinkRequest = new ActiveXObject("Microsoft.XMLHTTP");
}
authorLinkRequest.open("GET", "http://localhost:8080/test1/AuthorLink", true);
authorLinkRequest.responseType = 'json';
authorLinkRequest.send();

var auLinks;
var auNodeSize;

authorLinkRequest.onreadystatechange = function(){
    if(authorLinkRequest.readyState == 4 && authorLinkRequest.status == 200){
        var LinksAndSize =  authorLinkRequest.response;
        auLinks = LinksAndSize.link;
        auNodeSize = LinksAndSize.size;
        var optionForAuthorLink = {
            title: {
                text: '作者合作网络',
                left: 'center'
            },

            tooltip: {},
            animationDurationUpdate: 1500,
            animationEasingUpdate: 'quinticInOut',
            series: [{
                type: 'graph',
                layout: 'force',
                force: {
                    repulsion: 1001,
                    edgeLength: 150
                },
                color: 'mediumslateblue',
                data: auNodeSize,
                links: auLinks,
                roam: true,
                label: {
                    show: true,
                },
                emphasis: {
                    lineStyle: {
                        width: 10
                    }
                }
            }]
        };

        authorLink.setOption(optionForAuthorLink);
    }
}