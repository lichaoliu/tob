function page(page, pageSize, pageTotal, start, end) {
    var outStr = "";
    outStr += "<div class=\"zj_turnpage\">";

    outStr += "<span class=\"zj_pageinfo\">每页<em>" + pageSize + "</em>条</span>";
    outStr += "<span class=\"zj_pageinfo\">共<em>" + pageTotal + "</em>页</span>";
    outStr += "<span class=\"zj_pagenum\">";
    if (parseInt(page) <= 1) {
        outStr += "<a class=\"zj_disabled\" href=\"#\">首页</a>";
        outStr += "<input class=\"zj_pre\" type=\"button\"/>";
    } else {
        outStr += "<a href=\"javascript:pageClick('1');\" class=\"zj_firstpage\">首页</a>";
        outStr += "<input onclick=\"pageClick('" + (parseInt(page) - 1) + "')\" class=\"zj_pre\" type=\"button\"/>";
    }

    for (var i = parseInt(start); i <= parseInt(end); i++) {
        if (i == parseInt(page)) {
            outStr += "<a href=\"#\" class=\"zj_currentpage\">" + i + "</a>";
        } else {
            outStr += "<a href=\"javascript:pageClick('" + i + "');\">" + i + "</a>";
        }
    }

    if (parseInt(page) >= parseInt(pageTotal)) {
        outStr += "<input class=\"zj_next\" type=\"button\"/>";
        outStr += "<a class=\"zj_disabled\" href=\"#\">末页</a>";
    } else {
        outStr += "<input onclick=\"pageClick('" + (parseInt(page) + 1) + "')\" class=\"zj_next\" type=\"button\"/>";
        outStr += "<a href=\"javascript:pageClick('" + parseInt(pageTotal) + "');\" class=\"zj_lastpage\">末页</a>";
    }
    outStr += "</span>";
    outStr += "<span class=\"zj_backto\">&nbsp;&nbsp;转到 <input type=\"text\" value=\"" + parseInt(page) + "\" class=\"zj_btnum\" name=\"page\"> 页 </span>";
    outStr += "<input type=\"button\" onclick=\"onToPage()\" class=\"zj_qdbutt\" value=\"确定\">";
    outStr += "</div>";
    outStr += "<span class=\"blank10\"></span>";

    outStr += "<script type=\"text/javascript\">";
    outStr += "function pageClick(no){";
    outStr += "document.form1.page.value = no;";
    outStr += "document.form1.submit();";
    outStr += "}";
    outStr += "function onToPage(){";
    outStr += "document.form1.submit();";
    outStr += "}";
    outStr += "</script>";
    return outStr;
}