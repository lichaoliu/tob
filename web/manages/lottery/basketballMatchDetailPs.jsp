<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>蓝球场次详情</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="Cache-Control" content="max-age=0" forua="true"/>
    <meta http-equiv="Cache-Control" content="no-cache" forua="true"/>
    <meta http-equiv="Cache-Control" content="must-revalidate" forua="true"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/validator.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/page.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/page.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/manages.css">
    <link href="${pageContext.request.contextPath}/css/kjgg.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        body, td, th {
            font-family: Arial, "Times New Roman", "宋体";
        }
    </style>
    <script type="text/javascript">
        function windowClose() {
            window.close();
        }
        <%
        	String id=request.getParameter("id");
        %>
        var	map=null;
        $(function(){
        	$.ajax({
			   type: "POST",
			   url: "/manages/issueManagesServlet",
			   dataType:"json",
			   data: "action=basketballDetailPs&id="+<%=id%>,
			   async: false,
			   success: function(msg){
				   var json=msg;
				   map=json;
			   }
			});
			$('#title').html('竞彩篮球赛事'+map.basketball.issue+map.basketball.week+map.basketball.sn+'赛事详情');
			$('#issue').html(map.basketball.issue);
			$('#number').html(map.basketball.week+map.basketball.sn);
			$('#mainTeam').html(map.basketball.mainTeam);
			$('#guestTeam').html(map.basketball.guestTeam);
			$('#matchName').html(map.basketball.matchName);
			$('#letBall').html(map.basketball.letBall);
			$('#endTime').html(parseTime(map.basketball.endTime));
			$('#endFuShiTime').html(parseTime(map.basketball.endFuShiTime));
			$('#endOperator').html(issueSatu(map.basketball.endOperator));
			$('#operatorsAward').html(getOperatorsAward(map.basketball.operatorsAward));
			$('#result').html(matchResult(map));
			if(0 ==map.basketball.endOperator){
				$('#winOrNega').html(getWinOrNega(map));
				$('#letWinOrNega').html(getLetWinOrNega(map));
				$('#bigOrLittle').html(getBigOrLittle(map));
				$('#winNegaDiff').html(getWinNegaDiff(map));
			}else{
				$('#winOrNega').parent().hide();
				$('#letWinOrNega').parent().hide();
				$('#bigOrLittle').parent().hide();
				$('#winNegaDiff').parent().hide();
			}
        });
        //胜分差赔率
        function getWinNegaDiff(map){
        	var str='主胜';
        	if(map.basketball.winNegaDiffM1T5Sp != null){
        		str+=map.basketball.winNegaDiffM1T5Sp;
        		str+='/';
        	}
        	if(map.basketball.winNegaDiffM21T25Sp != null){
        		str+=map.basketball.winNegaDiffM21T25Sp;
        		str+=' ';
        	}
        	str+='客胜';
        	if(map.basketball.winNegaDiffG1T5Sp != null){
        		str+=map.basketball.winNegaDiffG1T5Sp;
        		str+='/';
        	}
        	if(map.basketball.winNegaDiffG21T25Sp != null){
        		str+=map.basketball.winNegaDiffG21T25Sp;
        	}
        	return(str);
        }
        //大小分赔率
        function getBigOrLittle(map){
        	var str='大';
        	if(map.basketball.bigSp != null){
        		str+=map.basketball.bigSp;
        		str+=' ';
        	}
        	var str='小';
        	if(map.basketball.littleSp != null){
        		str+=map.basketball.littleSp;
        		str+=' ';
        	}
        	return(str);
        }
        //让球赔率
        function getLetWinOrNega(map){
        	var str='让负';
        	if(map.basketball.letNegaSp != null){
        		str+=map.basketball.letNegaSp;
        		str+=' ';
        	}
        	str+='让胜';
        	if(map.basketball.letWinSp != null){
        		str+=map.basketball.letWinSp;
        		str+=' ';
        	}
        	return(str);
        }
        //胜负赔率
        function getWinOrNega(map){
        	var str='';
        	if(map.basketball.negaSp != null){
        		str+='负';
        		str+=map.basketball.negaSp;
        	}
        	if(map.basketball.flatSp != null){
        		str+='胜';
        		str+=map.basketball.winSp;
        	}
        	return(str);
        }
        //比赛结果
        function matchResult(map){
        	var str='';
        	str+='上半场比分:';
        	if(map.basketball.mainTeamHalfScore != null){
        		str+=map.basketball.mainTeamHalfScore;
        		str+=':';
        		str+=map.basketball.guestTeamHalfScore;
        	}
        	str+='<br/>';
        	str+='全场比分:';
        	if(map.basketball.mainTeamScore != null){
        		str+=map.basketball.mainTeamScore;
        		str+=':';
        		str+=map.basketball.guestTeamScore;
        	}
        	str+='<br/>';
        	str+='胜负:';
        	str+=map.sfResult;
        	if(map.sfFloatAmount != null){
        		str+=' 浮动奖金';
        		str+=map.sfFloatAmount;
        		str+='元';
        	}
        	str+='<br/>';
        	str+='让分胜负:';
        	str+=map.rfsfResult;
        	if(map.rfsfFloatAmount != null){
        		str+=' 浮动奖金';
        		str+=map.rfsfFloatAmount;
        		str+='元';
        	}
        	str+='<br/>';
        	str+='胜分差:';
        	if(map.sfcResult !=null){
        		str+=map.sfcResult;
        	}
        	str+='<br/>';
        	str+='大小分';
        	if(map.dxfResult != null){
        		str+=map.dxfResult;
        		str+=',';
        	}
        	if(map.dxfFloatAmount != null){
        		str+=' 浮动奖金';
        		str+=map.dxfFloatAmount;
        		str+='元';
        	}
        	return(str)
        }
        //彩期状态
        function issueSatu(str){
        	if(0==str){
        		return("销售中");
        	}else if(1==str){
        		return("期结");
        	}else if(2==str){
        		return("已取消");
        	}
        	
        }
        //算奖状态
        function getOperatorsAward(str){
        	if(0==str){
        		return("未算奖");
        	}else if(1==str){
        		return("算奖中");
        	}else if(2==str){
        		return("已算奖");
        	}
        }
        function parseTime(time){
        	var milliseconds=Date.parse(time);
        	var date=new Date();
        	date.setTime(milliseconds);
        	var str='';
        	str+=date.toLocaleString();
        	return(str);
        }
    </script>
</head>

<body>
<table width="900" border="0" align="center" cellpadding="0" cellspacing="0" class="table table1">
    <tr>
        <td class="title" id='title'>
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <table width="100%" border="0" cellpadding="4" cellspacing="0" class="seach_none">
                <tr>
                    <td align="right" class="bold">比赛日期:</td>
                    <td id='issue'>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">赛事编号:</td>
                    <td id='number'></td>
                </tr>
                <tr>
                    <td align="right" class="bold">主队:</td>
                    <td id='mainTeam'>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">客队:</td>
                    <td id='guestTeam'>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">联赛:</td>
                    <td id='matchName'>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">预计开赛时间:</td>
                    <td id='endTime'>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">预计截止投注时间:</td>
                    <td id='endFuShiTime'>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">彩期状态:</td>
                    <td id='endOperator'>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">算奖状态:</td>
                    <td id='operatorsAward'>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">让分:</td>
                    <td id='letBall'>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">比赛结果</td>
                    <td id='result'>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">胜负赔率:</td>
                    <td id='winOrNega'></td>
                </tr>
                <tr>
                    <td align="right" class="bold">胜分差赔率:</td>
                    <td id='winNegaDiff'></td>
                </tr>
                <tr>
                    <td align="right" class="bold">让球胜负赔率:</td>
                    <td id='letWinOrNega'></td>
                </tr>
                <tr>
                    <td align="right" class="bold">大小分赔率:</td>
                    <td id='bigOrLittle'></td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="button" class="submit" value="关闭" style="width: 64px; border: none"
                               onclick="javascript:window.close();"/>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</div>
</body>
</html>