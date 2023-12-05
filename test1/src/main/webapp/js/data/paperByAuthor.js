var PaperByAuthor = echarts.init(document.getElementById("PaperByAuthor"), "walden");
var PaperByAuthorPie = echarts.init(document.getElementById("PaperByAuthorPie"), "walden");

var paperByAuthorRequest;
if (window.XMLHttpRequest){
    paperByAuthorRequest = new XMLHttpRequest();
}
else{
    paperByAuthorRequest = new ActiveXObject("Microsoft.XMLHTTP");
}

paperByAuthorRequest.open("GET", "http://localhost:8080/test1/PaperByAuthor", true);
//paperByAuthorRequest.setRequestHeader('Content-Type', 'application/json');
paperByAuthorRequest.responseType = 'json';
paperByAuthorRequest.send();

var authorAndPaper;

paperByAuthorRequest.onreadystatechange = function(){
    if(paperByAuthorRequest.readyState == 4 && paperByAuthorRequest.status == 200){
        authorAndPaper = paperByAuthorRequest.response;
        var Authors = authorAndPaper.Authors;
        var AuthorPapers = authorAndPaper.SUM;
        var len = Math.min(15, Authors.length);
        Authors = Authors.slice(0, len);
        AuthorPapers = AuthorPapers.slice(0, len);

        var paperData = []

        for(let i = 0; i < len; i++){
            paperData.push({value : AuthorPapers[i], name : Authors[i]});
        }

        var p2aOption = {
             toolbox: {
                 feature: {
                 saveAsImage: {},
                 dataView: {},
                 restore: {}
                 }
             },
             tooltip: {
                 trigger: 'axis',
                 backgroundColor: '#FFF',
                 extraCssText: 'box-shadow: 0 0 3px rgba(0, 0, 0, 0.3)',
                 textStyle: {
                     color: '#000',
                 },
                 axisPointer: {
                     type: 'shadow'
                 }
             },
             title: {
                  text: '作者发文量',
             },
             xAxis: {
                  data: AuthorPapers,
                  axisLabel: {
                     show: true,
                     textStyle: {
                         color: 'black',
                     }
                  },
                  axisLine: {
                      show: false,
                      lineStyle: {
                         color: '#8c8c8c',
                      }
                  },
                  axisTick: {
                      show: false,
                      alignWithLabel: true,
                  },
                  splitLine: {
                      show: true,
                      lineStyle: {
                          color: '#888888',
                      },
                  },
                  type: 'value'
             },
             grid: {
                 top: '4%',
                 left: '2%',
                 right: '2%',
                 bottom: '2%',
                 containLabel: true
             },
             yAxis: {
                 type: 'category',
                 data: Authors,
                 axisLabel: {
                     show: true,
                     textStyle: {
                         color: 'black',
                     }
                 },
                 axisLine: {
                     show: true,
                     lineStyle: {
                         color: '#8c8c8c',
                     }
                 },
                 axisTick: {
                     show: false,
                     alignWithLabel: true,
                 },
                 splitLine: {
                     show: false,
                 },
             },
             tooltip: {
                  show: true,
             },
             series: [{
                 type: 'bar',
                                 barWidth: 20,
                                 emphasis: {
                                     itemStyle: {
                                         color: 'pink'
                                     }
                                 },
                                 itemStyle: {
                                     normal: {
                                         color: function(params) {
                                             var cols = ['#3BDBDB',
                                                 '#6CEBEB',
                                                 '#C2FFFF',
                                             ]
                                             var num = cols.length;
                                             return cols[params.dataIndex % num]
                                         },
                                         barBorderRadius: [10, 10, 10, 10] ,// 设置柱状图上方为圆角，下方为直角
                                         label: {
                                             show: false,
                                             position: 'top',
                                             formatter: function(params) {
                                                 var total = 0;
                                                 var percent = 0;
                                                 y_data.forEach(function(value, index) {
                                                     total += value;
                                                 });
                                                 percent = ((params.value / total) * 100).toFixed(2);
                                                 return '' + params.name + '\n\n' + params.value + '人，' + '' + percent + '%';
                                             },
                                         }
                                     }
                                 },
                                 data: AuthorPapers,
                                 name: '发文量'
                             }]
         };
        PaperByAuthor.setOption(p2aOption);

        var optionPaperByAuthorPie = {
            title: {
                text: '作者发文量饼图',
                left: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b}: {c} ({d}%)'
            },
            legend: {
                orient: 'vertical',
                left: 10,
                data: Authors
            },
            roseType: 'radius',
            series: [{
                name: '发文量',
                type: 'pie',
                radius: ['30%', '60%'],
                center: ['50%', '50%'],
                data: paperData,
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }]
        };
        PaperByAuthorPie.setOption(optionPaperByAuthorPie);
    }
}
