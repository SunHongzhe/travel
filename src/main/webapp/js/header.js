$(function () {
    
    // 用户信息展示
    $.get("user/findOne", {}, function (data) {
        if (data.flag) {
            // 登录
            $("#login").css("display","");
            var msg = "欢迎回来," + data.errorMsg;
            $("#span_username").html(msg);
        } else {
            // 未登录
            $("#login_out").css("display","");
        }
    });

    // 分类栏信息请求
    $.get("category/findAll",{},function (data) {
        var lis = '<li class="nav-active"><a href="index.html">首页</a></li>';
        // 遍历数组,拼接字符串
        for (var i = 0; i < data.length; i++) {
            lis += '<li><a href="route_list.html?cid=' + data[i].cid + '">' + data[i].cname + '</a></li>'
        }
        lis += '<li><a href="favoriterank.html">收藏排行榜</a></li>';
        $("#category").html(lis);
    });
    
});

