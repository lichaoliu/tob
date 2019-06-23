<%@ page contentType="text/html;charset=utf-8" %>

<html>

<head>

    <title>互联网购彩业务管理系统登陆</title>

    <meta http-equiv=Content-Type content="text/html; charset=utf-8">

    <meta http-equiv="Cache-Control" content="max-age=0" forua="true"/>

    <meta http-equiv="Cache-Control" content="no-cache" forua="true"/>

    <meta http-equiv="Cache-Control" content="must-revalidate" forua="true"/>

    <link href="/css/admin.css" type="text/css" rel="stylesheet">

    <style type="text/css">

        body, td, th {
            font-family: Verdana, Arial, Helvetica, sans-serif;
            font-size: 12px;
        }

        html, legend {
            color: #333333;
            background: #fff;
        }

        body, div, dl, dt, dd, ul, ol, li, h1, h2, h3, h4, h5, h6, pre, code, form, fieldset, legend, input, button, textarea, p, blockquote, th, td {
            margin: 0;
            padding: 0;
        }

        input, button, textarea {
            font-family: inherit;
            font-size: inherit;
            font-weight: inherit;
            vertical-align: middle;
            margin-top: 0px;
            margin-bottom: 1px;
        }

        li {
            list-style: none;
        }

        .login_boxjj0 {
            height: 190px;
        }

        .login_boxjj1 {
            height: 34px;
            text-align: left;
            padding-left: 20px;
            color: #CC0000
        }

        .login_boxjj {
            line-height: 22px;
            width: 100%;
            text-align: left;
            padding-left: 7px;
        }

        .login_boxjj li {
            float: left;
            padding-left: 15px;
        }

        .shukuan {
            background: #F7F8FA;
            border-bottom: 1px solid #B3B8BE;
            border-left: 1px solid #808487;
            border-right: 1px solid #B3B8BE;
            border-top: 1px solid #808487;
            width: 95px;
            padding-left: 3px;
            line-height: 18px;
        }

        .shukuan1 {
            background: #F7F8FA;
            border-bottom: 1px solid #B3B8BE;
            border-left: 1px solid #808487;
            border-right: 1px solid #B3B8BE;
            border-top: 1px solid #808487;
            width: 55px;
            padding-left: 3px;
            line-height: 18px;
        }

        .qingchu {
            clear: both;
        }

        .loginbut {
            background: url(/images/but.jpg);
            width: 66px;
            line-height: 23px;
            height: 23px;
            border: none;
        }

        .banquan {
            text-align: center;
            padding-top: 5px;
            color: #666666;
        }


    </style>

    <script type="text/javascript">

        if (top.location != self.location) {

            top.location = self.location;

        }

    </script>

</head>

<body>

<table height="100%" cellspacing=0 cellpadding=0 width="100%" border=0>

    <tr>

        <td align=middle>

            <table width="619" height="284" border="0" align="center" cellpadding="0" cellspacing="0"
                   background="/images/loginbg.jpg">
                <form name="form1" method="post"
                      action="${pageContext.request.contextPath}/managesLogin">

                    <tr>

                        <td height="282" valign="top">
                            <div class="login_boxjj0"></div>
                            <div class="login_boxjj1">${msg}</div>
                            <div class="login_boxjj">
                                <ul>
                                    <li><input type="hidden" value="0" name="adminType"/>
                                        用户名：<input type="text"
                                                   class="shukuan"
                                                   maxlength=30 size=24
                                                   name="adminName">
                                    </li>

                                    <li>密码：<input type="password" class="shukuan" maxlength=30 size=24 name="password">
                                    </li>

                                    <li> 验证码：<input class="shukuan1" maxlength=10 size=10 name="verifyCode"
                                                    type="text"/>
                                        <img
                                                src="${pageContext.request.contextPath}/code.jpg?<%=System.currentTimeMillis() %>"
                                                alt="点击刷新" height="24"
                                                align="absmiddle"
                                                style="cursor:hand"
                                                onclick="this.src='${pageContext.request.contextPath}/code.jpg?'+Math.random();"/>
                                    </li>
                                    <li><input name="提交" type="submit" class="loginbut" value="登录">
                                    </li>
                                    <div class="qingchu"></div>
                                </ul>
                            </div>
                            <div class="banquan">北京中彩汇 版权所有</div>
                        </td>

                    </tr>
                </form>

            </table>

        </td>

    </tr>

</table>

</body>

</html>

