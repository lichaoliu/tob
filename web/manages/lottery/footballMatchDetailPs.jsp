<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>足球场次详情</title>
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
			   data: "action=footballDetailPs&id="+<%=id%>,
			   async: false,
			   success: function(msg){
				   var json=msg;
				   map=json;
			   }
			});
			$('#title').html('竞彩足球赛事'+map.football.issue+map.football.week+map.football.sn+'赛事详情');
			$('#issue').html(map.football.issue);
			$('#number').html(map.football.week+map.football.sn);
			$('#mainTeam').html(map.football.mainTeam);
			$('#guestTeam').html(map.football.guestTeam);
			$('#matchName').html(map.football.matchName);
			$('#letBall').html(map.football.letBall);
			$('#endTime').html(parseTime(map.football.endTime));
			$('#endFuShiTime').html(parseTime(map.football.endFuShiTime));
			$('#endOperator').html(issueSatu(map.football.endOperator));
			$('#operatorsAward').html(getOperatorsAward(map.football.operatorsAward));
			$('#result').html(matchResult(map));
			if(0 == map.football.endOperator){
				$('#winOrNega').html(getWinOrNega(map));
				$('#spfWinOrNega').html(getSpfWinOrNega(map));
				$('#score').html(getScore(map));
				$('#totalGoal').html(getTotalGoal(map));
				$('#halfCourt').html(getHalfCourt(map));
			}else{
				$('#winOrNega').parent().hide();
				$('#spfWinOrNega').parent().hide();
				$('#score').parent().hide();
				$('#totalGoal').parent().hide();
				$('#halfCourt').parent().hide();
			}
        });
        //半场胜平负赔率
        function getHalfCourt(map){
        	var str='';
        	if(map.football.halfCourtFFSp != null){
        		str+=map.football.halfCourtFFSp;
        		str+='/';
        	}
        	if(map.football.halfCourtFSSp != null){
        		str+=map.football.halfCourtFSSp;
        		str+='/';
        	}
        	if(map.football.halfCourtPSSp != null){
        		str+=map.football.halfCourtPSSp;
        		str+='/';
        	}
        	if(map.football.halfCourtSSSp != null){
        		str+=map.football.halfCourtSSSp;
        	}
        	return(str);
        }
        //总进球数赔率
        function getTotalGoal(map){
        	var str='';
        	if(map.football.totalGoal0Sp != null){
        		str+=map.football.totalGoal0Sp;
        		str+='/';
        	}
        	if(map.football.totalGoal3Sp != null){
        		str+=map.football.totalGoal3Sp;
        		str+='/';
        	}
        	if(map.football.totalGoal5Sp != null){
        		str+=map.football.totalGoal5Sp;
        		str+='/';
        	}
        	if(map.football.totalGoal7Sp != null){
        		str+=map.football.totalGoal7Sp;
        	}
        	return(str);
        }
        //比分赔率
        function getScore(map){
        	var str='';
        	if(map.football.scoreFQTSp != null){
        		str+=map.football.scoreFQTSp;
        		str+='/';
        	}
        	if(map.football.score10Sp != null){
        		str+=map.football.score10Sp;
        		str+='/';
        	}
        	if(map.football.score25Sp != null){
        		str+=map.football.score25Sp;
        		str+='/';
        	}
        	if(map.football.score50Sp != null){
        		str+=map.football.score50Sp;
        	}
        	return(str);
        }
        //胜平负赔率
        function getSpfWinOrNega(map){
        	var str='';
        	if(map.football.spfNegaSp != null){
        		str+='负负';
        		str+=map.football.spfNegaSp;
        		str+='/';
        	}
        	if(map.football.spfFlatSp != null){
        		str+='胜平';
        		str+=map.football.spfFlatSp;
        		str+='/';
        	}
        	if(map.football.spfWinSp != null){
        		str+='负胜';
        		str+=map.football.spfWinSp;
        	}
        	return(str);
        }
        //让球胜平负赔率
        function getWinOrNega(map){
        	var str='';
        	if(map.football.negaSp != null){
        		str+='负负';
        		str+=map.football.negaSp;
        		str+='/';
        	}
        	if(map.football.flatSp != null){
        		str+='胜平';
        		str+=map.football.flatSp;
        		str+='/';
        	}
        	if(map.football.winSp != null){
        		str+='负胜';
        		str+=map.football.winSp;
        	}
        	return(str);
        }
        //比赛结果
        function matchResult(map){
        	var str='';
        	str+='上半场比分:';
        	if(map.football.mainTeamHalfScore != null){
        		str+=map.football.mainTeamHalfScore;
        		str+=':';
        		str+=map.football.guestTeamHalfScore;
        	}
        	str+='<br/>';
        	str+='全场比分:';
        	if(map.football.mainTeamScore != null){
        		str+=map.football.mainTeamScore;
        		str+=':';
        		str+=map.football.guestTeamScore;
        	}
        	str+='<br/>';
        	str+='胜平负:';
        	str+=map.spfResult;
        	if(map.spfFloatAmount != null){
        		str+=' 浮动奖金';
        		str+=map.spfFloatAmount;
        		str+='元';
        	}
        	str+='<br/>';
        	str+='总进球数:';
        	str+=map.zjqsResult;
        	if(map.zjqsFloatAmount != null){
        		str+=' 浮动奖金';
        		str+=map.zjqsFloatAmount;
        		str+='元';
        	}
        	str+='<br/>';
        	str+='半全场胜平负:';
        	str+=map.bqcspfResult;
        	if(map.zjqsFloatAmount != null){
        		str+=' 浮动奖金';
        		str+=map.zjqsFloatAmount;
        		str+='元';
        	}
        	str+='<br/>';
        	str+='比分:';
        	str+=map.bfResult;
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
                    <td align="right" class="bold">让球胜平负赔率:</td>
                    <td id='winOrNega'></td>
                </tr>
                <tr>
                    <td align="right" class="bold">胜平负赔率:</td>
                    <td id='spfWinOrNega'></td>
                </tr>
                <tr>
                    <td align="right" class="bold">比分赔率:</td>
                    <td id='score'></td>
                </tr>
                <tr>
                    <td align="right" class="bold">总进球数赔率:</td>
                    <td id='totalGoal'></td>
                </tr>
                <tr>
                    <td align="right" class="bold">半场胜平负赔率:</td>
                    <td id='halfCourt'></td>
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