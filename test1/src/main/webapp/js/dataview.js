var PaperByYear = echarts.init(document.getElementById("PaperByYear"), "walden");
setTimeout(function() {
    option = {
        title: {
            text: '年度发文量'
        },
        legend: {},
        tooltip: {
            trigger: 'axis',
            showContent: false
        },
        dataset: {
            source: [
                ["作者", "2016", "2017", "2018", "2019", "2020"],
                ["孙杨", "23", "11", "29", "33", "29"],
                ["陈宇", "10", "13", "17", "33", "27"],
                ["冯玉", "25", "25", "36", "22", "29"],
                ["郑伟", "22", "11", "33", "31", "25"],
                ["王伟", "10", "20", "35", "30", "25"],
                ["李娜", "10", "18", "34", "25", "30"],
                ["吴燕", "18", "12", "33", "27", "24"],
                ["钱强", "15", "16", "18", "21", "26"],
                ["赵华", "14", "15", "15", "19", "34"],
            ]
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
        series: [{
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
                    formatter: '{b}: {@2016} ({d}%)'
                },
                encode: {
                    itemName: '作者',
                    value: '2016',
                    tooltip: '2016'
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
    PaperByYear.setOption(option);
});

//作者发文量
authors = ['赵华', '钱强', '孙俪', '吴燕', '王伟', '李娜', '郑伟', '冯玉', '陈宇', '孙杨'];
authorpapers = [125, 100, 137, 122, 220, 210, 133, 99, 100, 223];
var PaperByAuthor = echarts.init(document.getElementById("PaperByAuthor"), "walden");
var option1;
option1 = {
    // visualMap: {
    //     //分段型视觉映射组件
    //     type: 'piecewise',
    //     max: 250,
    //     min: 0,
    //     inRange: {
    //         symbolSize: [0, 200],
    //         //color: ['red', 'green', 'blue']
    //     },
    //     outOfRange: {
    //        // color: ['pink']
    //     }
    // },  
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
        //left: 'left'
    },
    // legend: {
    //     data: ['发文量']
    // },
    xAxis: {
        data: authorpapers,
        axisLabel: {
            show: true,
            textStyle: {
                color: 'black',
                //fontSize: 18
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
        data: authors,
        axisLabel: {
            show: true,
            textStyle: {
                color: 'black',
                //fontSize: 18
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
    // color: {
    //     type: 'linear',
    //     x: 0,
    //     y: 0,
    //     x2: 1,
    //     y2: 0,
    //     colorStops: [{
    //         offset: 0,
    //         color: '#3A5FCD'
    //     }, {
    //         offset: 1,
    //         color: '#FF8C00'
    //     }],
    //     global: false
    // },
    
    tooltip: {
        show: true,
        //formatter: '{b}：{c}篇'
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
        data: authorpapers,
        name: '发文量'
    }]
};
PaperByAuthor.setOption(option1);

var PaperByAuthorpie = echarts.init(document.getElementById("PaperByAuthorpie"), "walden");
var optionPaperByAuthorPie;
optionPaperByAuthorPie = {
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
        data: authors
    },
    roseType: 'radius',
    series: [{
        name: '发文量',
        type: 'pie',
        radius: ['30%', '60%'],
        center: ['50%', '50%'],
        data: [{
                value: 130,
                name: '赵华'
            },
            {
                value: 140,
                name: '钱强'
            },
            {
                value: 150,
                name: '孙俪'
            },
            {
                value: 180,
                name: '吴燕'
            },
            {
                value: 220,
                name: '王伟'
            },
            {
                value: 210,
                name: '李娜'
            },
            {
                value: 133,
                name: '郑伟'
            },
            {
                value: 99,
                name: '冯玉'
            },
            {
                value: 100,
                name: '陈宇'
            },
            {
                value: 223,
                name: '孙杨'
            }
        ],
        emphasis: {
            itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
        }
    }]
};
PaperByAuthorpie.setOption(optionPaperByAuthorPie);

var nodes = [{
        name: '神经网络',
        symbolSize: 12
    },
    {
        name: '信息管理系统',
        symbolSize: 8
    },
    {
        name: '在线教学',
        symbolSize: 11
    },
    {
        name: '物联网',
        symbolSize: 18
    },
    {
        name: '计算机教育',
        symbolSize: 11
    },
    {
        name: '现代化运用',
        symbolSize: 18
    },
    {
        name: '智能化建筑',
        symbolSize: 4
    },
    {
        name: 'CV',
        symbolSize: 13
    },
    {
        name: '粒子群',
        symbolSize: 9
    }
];
var keywords = nodes.map(function(node) {
    return node.name;
});
var keywordsnum = nodes.map(function(node) {
    return node.symbolSize;
});
console.log(keywords);
var keyword = echarts.init(document.getElementById("keyword"));
var option3 = {
    tooltip: {},
    title: {
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
        data: [{
                name: '数据可视化',
                value: 40180,
            }, {
                name: '服务器',
                value: 21207
            }, {
                name: '数据库',
                value: 19014
            },
            {
                name: '卷积神经网络',
                value: 17288
            },
            {
                name: '深度学习',
                value: 17132
            }, {
                name: '图像处理',
                value: 15880
            }, {
                name: '特征提取',
                value: 15819
            }, {
                name: '操作系统',
                value: 15507
            }, {
                name: '数据挖掘',
                value: 14705
            }, {
                name: '数据库服务器',
                value: 14324
            }, {
                name: '像素点',
                value: 13903
            }, {
                name: '网络安全',
                value: 12909
            }, {
                name: 'Web',
                value: 12725
            }, {
                name: 'Server',
                value: 12049
            }, {
                name: '客户端',
                value: 11982
            }, {
                name: '服务器端',
                value: 10412
            }, {
                name: '字段名',
                value: 9903
            }, {
                name: '防火墙',
                value: 9739
            }, {
                name: '分类器',
                value: 9269
            }, {
                name: '应用程序',
                value: 9245
            }, {
                name: '功能模块',
                value: 8790,
            }, {
                name: '注意力机制',
                value: 8318
            }, {
                name: '神经网络',
                value: 8276
            },
            {
                name: '图像分割',
                value: 7963
            },
            {
                name: '云计算',
                value: 7733
            }, {
                name: '支持向量机',
                value: 7611
            }, {
                name: '数据类型',
                value: 7047
            }, {
                name: '浏览器',
                value: 7003
            }, {
                name: '电子标签',
                value: 6892
            }, {
                name: '用例图',
                value: 6738
            }, {
                name: '小波变换',
                value: 6697
            }, {
                name: '测试用例',
                value: 6284
            }, {
                name: '卷积核',
                value: 6085
            }, {
                name: '数据库表',
                value: 6031
            }, {
                name: 'Linux',
                value: 5897
            }, {
                name: '嵌入式系统',
                value: 5852
            }, {
                name: '处理器',
                value: 5741
            }, {
                name: 'CPU',
                value: 5715
            }, {
                name: 'ASP',
                value: 5631
            }, {
                name: '目标检测',
                value: 5588
            },
        ]
    }],
}
keyword.setOption(option3);

var rador = echarts.init(document.getElementById('rador'));

option11 = {
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
        indicator: [{
                name: '计算机图形学',
                max: 10
            },
            {
                name: '计算机视觉',
                max: 10
            },
            {
                name: '人工智能',
                max: 10
            },
            {
                name: '计算机系统结构',
                max: 10
            },
            {
                name: '生物信息学',
                max: 10
            },
            {
                name: '车联网',
                max: 10
            }
        ]
    },
    series: [{
        type: 'radar',
        data: [{
                name: "孙杨",
                value: [3, 2, 4, 1, 6, 7]
            },
            {
                name: "陈宇",
                value: [3, 2, 3, 9, 2, 8]
            },
            {
                name: "冯玉",
                value: [9, 3, 2, 7, 8, 1]
            },
            {
                name: "郑伟",
                value: [9, 8, 8, 3, 3, 6]
            },
            {
                name: "王伟",
                value: [8, 5, 1, 3, 4, 5]
            },
            {
                name: "李娜",
                value: [2, 3, 9, 3, 3, 6]
            },
            {
                name: "吴燕",
                value: [2, 3, 4, 6, 7, 7]
            },
            {
                name: "钱强",
                value: [4, 5, 1, 9, 7, 5]
            },
            {
                name: "赵华",
                value: [4, 7, 9, 7, 9, 2]
            },
            {
                name: "孙俪",
                value: [5, 6, 7, 7, 6, 4]
            }
        ]
    }]
};
rador.setOption(option11);
var thisEharts = echarts.init(document.getElementById('polar'));

$.getJSON('./json/page6.json', function(json) {
    thisEharts.setOption({
        title: json.titleData,
        legend: json.legendData,
        polar: json.polarData,
        tooltip: json.tooltipData,
        radiusAxis: json.raduisAxisData,
        angleAxis: json.angleAxisData,
        series: json.seriesList,
    })
})
//作者合作网络

//关键词热点
var nodes = [{
        name: '神经网络',
        symbolSize: 50
    },
    {
        name: '深度学习',
        symbolSize: 30
    },
    {
        name: '人工智能',
        symbolSize: 40
    },
    {
        name: '对抗神经网络',
        symbolSize: 35
    },
    {
        name: '图形学',
        symbolSize: 25
    },
    {
        name: '模型渲染',
        symbolSize: 20
    },
    {
        name: 'AI',
        symbolSize: 44
    },
    {
        name: 'CV',
        symbolSize: 53
    },
    {
        name: '粒子群',
        symbolSize: 32
    },
    {
        name: '高等数学',
        symbolSize: 22
    },
    {
        name: '数学分析',
        symbolSize: 14
    }
];
//var KeywordMap = echarts.init(document.getElementById('KeywordMap'),'macarons');
var optionforkeymap = {
    backgroundColor: 'black',
    title: {
        text: '关键词热点',
        left: 'center'
    },
    tooltip: {},
    animationDurationUpdate: 1500,
    animationEasingUpdate: 'quinticInOut',
    color: ['rgb(60, 220, 220)',
        'rgba(60, 220, 220,.8)',
        'rgba(60, 220, 220,.6)',
        'rgba(60, 220, 220,.4)',
        'rgba(60, 220, 220,.2)',
        'rgb(110, 235, 235)',
        'rgba(110, 235, 235,.8)',
        'rgba(110, 235, 235,.6)',
        'rgba(110, 235, 235,.4)',
        'rgba(110, 235, 235,.2)',
        'rgb(195, 255, 255)',
        'rgba(195, 255, 255,.8)',
        'rgba(195, 255, 255,.6)',
        'rgba(195, 255, 255,.4)',
        'rgba(195, 255, 255,.2)',
        'rgb(255, 255, 255)',
        'rgba(255, 255, 255,.8)',
        'rgba(255, 255, 255,.6)',
        'rgba(255, 255, 255,.4)',
        'rgba(255, 255, 255,.2)',
    ],
    series: [{
        colorBy: 'data',
        type: 'graph',
        layout: 'force',
        force: {
            repulsion: 1000,
            edgeLength: 50
        },
        
        edgeSymbol: ['none', 'arrow'],
        edgeSymbolSize: [0, 10],
        data: nodes,
        draggable: true,
        roam: true,
        label: {
            show: true,
            position: 'top'
        },
        emphasis: {
            lineStyle: {
                width: 10
            }
        }
    }]
};

var nodesofanthor = [{
        name: '赵华',
        symbolSize: 100
    },
    {
        name: '钱强',
        symbolSize: 80
    },
    {
        name: '孙俪',
        symbolSize: 80
    },
    {
        name: '吴燕',
        symbolSize: 80
    },
    {
        name: '王伟',
        symbolSize: 60
    },
    {
        name: '李娜',
        symbolSize: 20
    },
    {
        name: '郑伟',
        symbolSize: 40
    },
    {
        name: '冯玉',
        symbolSize: 60
    },
    {
        name: '陈宇',
        symbolSize: 40
    },
    {
        name: '孙杨',
        symbolSize: 60
    }
];

var links = [{
        source: '赵华',
        target: '钱强'
    },
    {
        source: '赵华',
        target: '孙俪'
    },
    {
        source: '赵华',
        target: '吴燕'
    },
    {
        source: '钱强',
        target: '孙杨'
    },
    {
        source: '钱强',
        target: '孙俪'
    },
    {
        source: '钱强',
        target: '王伟'
    },
    {
        source: '孙俪',
        target: '吴燕'
    },
    {
        source: '吴燕',
        target: '王伟'
    },
    {
        source: '吴燕',
        target: '郑伟'
    },
    {
        source: '孙俪',
        target: '钱强'
    },
    {
        source: '王伟',
        target: '李娜'
    },
    {
        source: '孙杨',
        target: '冯玉'
    },
    {
        source: '郑伟',
        target: '冯玉'
    },
    {
        source: '钱强',
        target: '陈宇'
    },
    {
        source: '冯玉',
        target: '陈宇'
    },
    {
        source: '孙俪',
        target: '孙杨'
    }
];
var AuthorLink = echarts.init(document.getElementById('AuthorLink'), 'macarons');

var optionforauthorlink = {
    title: {
        text: '作者合作网络',
        left: 'center'
    },
    
    // edgeSymbol: ['none', 'arrow'],
    // edgeSymbolSize: [0, 10],
    // edgeLabel: {
    //     fontSize: 10
    // },
    
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
        data: nodesofanthor,
        links: links,
        roam: true,
        label: {
            show: true,
            //position: 'top'
        },
        emphasis: {
            lineStyle: {
                width: 10
            }
        }
    }]
};

AuthorLink.setOption(optionforauthorlink);
var inslink = echarts.init(document.getElementById('inslink'));

// 指定图表的配置项和数据
var optionins = {
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
        data: [{
                name: '吉林大学计算机学院',
                symbolSize: 100,
                itemStyle: {
                    color: '#c23531'
                }
            },
            {
                name: '北京大学计算机学院',
                symbolSize: 80,
                itemStyle: {
                    color: 'grey'
                }
            },
            {
                name: '清华大学计算机学院',
                symbolSize: 80,
                itemStyle: {
                    color: 'orange'
                }
            },
            {
                name: '复旦大学计算机学院',
                symbolSize: 33,
                itemStyle: {
                    color: '#a23521'
                }
            },
            {
                name: '南京大学软件学院',
                symbolSize: 80,
                itemStyle: {
                    color: 'pink'
                }
            },
            {
                name: '中国人民大学高瓴人工智能学院',
                symbolSize: 80,
                itemStyle: {
                    color: 'green'
                }
            },
            {
                name: '吉林大学软件学院',
                symbolSize: 80,
                itemStyle: {
                    color: '#2f4554'
                }
            },
            {
                name: '重庆大学计算机学院',
                itemStyle: {
                    color: '#61a0a8'
                }
            },
            {
                name: '东北师范大学计算机学院',
                itemStyle: {
                    color: '#d48265'
                }
            },
            {
                name: '美国波特兰州立大学工程与计算机科学学院',
                itemStyle: {
                    color: '#91c7ae'
                }
            }
        ],
        links: [{
                source: '吉林大学软件学院',
                target: '吉林大学计算机学院',
                lineStyle: {
                    color: '#c23531'
                }
            },
            {
                source: '吉林大学软件学院',
                target: '北京大学计算机学院',
                lineStyle: {
                    color: '#c23531'
                }
            },
            {
                source: '吉林大学软件学院',
                target: '北京大学计算机学院',
                lineStyle: {
                    color: '#c23531'
                }
            },
            {
                source: '中国人民大学高瓴人工智能学院',
                target: '北京大学计算机学院',
                lineStyle: {
                    color: '#c23531'
                }
            }, {
                source: '中国人民大学高瓴人工智能学院',
                target: '吉林大学软件学院',
                lineStyle: {
                    color: '#c23531'
                }
            }, 
            {
                source: '清华大学计算机学院',
                target: '北京大学计算机学院',
                lineStyle: {
                    color: '#c23531'
                }
            }, 
            {
                source: '复旦大学计算机学院',
                target: '北京大学计算机学院',
                lineStyle: {
                    color: '#c23531'
                }
            },
            {
                source: '南京大学软件学院',
                target: '东北师范大学计算机学院',
                lineStyle: {
                    color: '#c23531'
                }
            },
            {
                source: '吉林大学软件学院',
                target: '重庆大学计算机学院',
                lineStyle: {
                    color: '#c23531'
                }
            },
            {
                source: '吉林大学计算机学院',
                target: '吉林大学软件学院',
                lineStyle: {
                    color: '#c23531'
                }
            },
            {
                source: '吉林大学计算机学院',
                target: '美国波特兰州立大学工程与计算机科学学院',
                lineStyle: {
                    color: '#2f4554'
                }
            },
            {
                source: '吉林大学计算机学院',
                target: '东北师范大学计算机学院',
                lineStyle: {
                    color: '#61a0a8'
                }
            },
            {
                source: '吉林大学计算机学院',
                target: '东北师范大学计算机学院',
                lineStyle: {
                    color: '#d48265'
                }
            }
        ]
    }]
};

// 使用刚指定的配置项和数据显示图表。
inslink.setOption(optionins);

// 指定图表的配置项和数据
var page5Echarts = echarts.init(document.getElementById("journal"));

$.getJSON("json/page5.json", function(json) {
    console.log(json);
    page5Echarts.setOption({
        title: json.titleData,
        legend: json.legendData,
        series: json.seriesList,
    })
})

// 使用刚指定的配置项和数据显示图表。