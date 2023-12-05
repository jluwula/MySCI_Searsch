var PaperByYear = echarts.init(document.getElementById("PaperByYear"), "walden");
var paperByYearRequest;
if (window.XMLHttpRequest){
    paperByYearRequest = new XMLHttpRequest();
}
else{
    paperByYearRequest = new ActiveXObject("Microsoft.XMLHTTP");
}

paperByYearRequest.open("GET", "http://localhost:8080/test1/paperByAuthor", true);
paperByYearRequest.responseType = 'json';
paperByYearRequest.send();

paperByYearRequest.onreadystatechange = function(){
    if(paperByYearRequest.readyState == 4 && paperByYearRequest.status == 200){
        var paperByYear = paperByYearRequest.response;

        var paperByYearOption = {
                title: {
                    text: '年度发文量'
                },
                legend: {},
                tooltip: {
                    trigger: 'axis',
                    showContent: false
                },
                dataset: {
                    source: paperByYear
                },
                xAxis: {
                    type: 'category',
                    name: '年份'
                },
                yAxis: {
                    gridIndex: 0,
                    name: '发文量'
                },
                grid: {
                    top: '55%'
                },
                series: [
                    {
                        type: 'line',
                        smooth: true,
                        seriesLayoutBy: 'row',
                        emphasis: {
                            focus: 'series'
                        }
                    },
                    {
                        type: 'line',
                        smooth: true,
                        seriesLayoutBy: 'row',
                        emphasis: {
                            focus: 'series'
                        }
                    },
                    {
                        type: 'line',
                        smooth: true,
                        seriesLayoutBy: 'row',
                        emphasis: {
                            focus: 'series'
                        }
                    },
                    {
                        type: 'line',
                        smooth: true,
                        seriesLayoutBy: 'row',
                        emphasis: {
                            focus: 'series'
                        }
                    }, {
                        type: 'line',
                        smooth: true,
                        seriesLayoutBy: 'row',
                        emphasis: {
                            focus: 'series'
                        }
                    },
                    {
                        type: 'line',
                        smooth: true,
                        seriesLayoutBy: 'row',
                        emphasis: {
                            focus: 'series'
                        }
                    },
                    {
                        type: 'line',
                        smooth: true,
                        seriesLayoutBy: 'row',
                        emphasis: {
                            focus: 'series'
                        }
                    },
                    {
                        type: 'line',
                        smooth: true,
                        seriesLayoutBy: 'row',
                        emphasis: {
                            focus: 'series'
                        }
                    },
                    {
                        type: 'line',
                        smooth: true,
                        seriesLayoutBy: 'row',
                        emphasis: {
                            focus: 'series'
                        }
                    },
                    {
                        radius: ['20%', '40%'],
                        //roseType:'true',
                        type: 'pie',
                        id: 'pie',
                        //radius: '30%',
                        center: ['50%', '30%'],
                        emphasis: {
                            focus: 'self'
                        },
                        label: {
                            formatter: '{b}: {@2023} ({d}%)'
                        },
                        encode: {
                            itemName: '作者',
                            value: '2023',
                            tooltip: '2023'
                        }
                    }
                ]
            };
        PaperByYear.on('updateAxisPointer', function(event) {
                const xAxisInfo = event.axesInfo[0];
                if (xAxisInfo) {
                    const dimension = xAxisInfo.value + 1;
                    PaperByYear.setOption({
                        series: {
                            id: 'pie',
                            label: {
                                formatter: '{b}: {@[' + dimension + ']} ({d}%)'
                            },
                            encode: {
                                value: dimension,
                                tooltip: dimension
                            }
                        }
                    });
                }
        });
        PaperByYear.setOption(paperByYearOption);
    }
}