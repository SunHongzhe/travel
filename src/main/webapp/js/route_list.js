/*
    分页栏展示
    <li><a href="">首页</a></li>
    <li class="threeword"><a href="#">上一页</a></li>
    <li class="curPage"><a href="#">1</a></li>
    <li class="threeword"><a href="javascript:;">下一页</a></li>
    <li class="threeword"><a href="javascript:;">末页</a></li>

*/

/*
    列表数据展示
    <li>
        <div class="img"><img src="images/04-search_03.jpg" alt=""></div>
        <div class="text1">
            <p>【减100元 含除夕/春节出发】广州增城三英温泉度假酒店/自由行套票</p>
            <br/>
            <p>1-2月出发，网付立享￥1099/2人起！爆款位置有限，抢完即止！</p>
        </div>
        <div class="price">
            <p class="price_num">
                <span>&yen;</span>
                <span>1299</span>
                <span>起</span>
            </p>
            <p><a href="route_detail.html">查看详情</a></p>
        </div>
    </li>
*/

function load(cid,currentPage,rname) {
    // 发送ajax请求，请求route/pageQuery,传递cid
    $.get("route/pageQuery",{cid:cid,currentPage:currentPage,rname:rname},function(pb) {
        // 解析pageBean数据，展示到页面上

        // 1 分页工具条展示
        // 1.1 展示总页码和总记录数
        $("#totalPage").html(pb.totalPage);
        $("#totalCount").html(pb.totalCount);
        // 定义输出变量
        var lis = "";
        // 1.2 展示首页和上一页
        // 计算上一页页码
        var prePageNum = currentPage - 1;
        if (prePageNum <=0 ) {
            prePageNum = 1;
        }
        // 添加隐藏样式
        if (currentPage <= 1 || currentPage == null) {
            var firstPage = '<li style="display: none" onclick="javascript:load(' + cid + ',1,\'' + rname + '\')" class="threeword"><a href="javascript:void(0);">首页</a></li>';
            var prePage = '<li style="display: none" onclick="javascript:load(' + cid + ', ' + prePageNum + ', \'' + rname + '\')" class="threeword"><a href="javascript:void(0)">上一页</a></li>';
            lis += firstPage;
            lis += prePage;
        } else {
            var firstPage = '<li id="firstPage" onclick="javascript:load(' + cid + ',1, \'' + rname + '\')" class="threeword"><a href="javascript:void(0);">首页</a></li>';
            var prePage = '<li id="prePage" onclick="javascript:load(' + cid + ', ' + prePageNum + ', \'' + rname + '\')" class="threeword"><a href="javascript:void(0)">上一页</a></li>';
            lis += firstPage;
            lis += prePage;
        }
        // 1.3 展示分页页码
        // 定义开始位置begin,结束位置end
        var begin = 0;
        var end = 0;
        // 一共展示10页页码
        if (pb.totalPage < 10) {
            // 总页码不够10页
            begin = 1;
            end = pb.totalPage;
        } else {
            // 总页码超过10页
            begin = pb.currentPage - 5;
            end = pb.currentPage + 4;
            // 控制前后溢出
            if (begin < 1) {
                begin = 1;
                end = 10;
            }
            if (end > pb.totalPage) {
                end = pb.totalPage;
                begin = end - 9;
            }
        }
        // 遍历展示页码
        for (var i = begin; i <= end; i++) {
            var li;
            // 判断当前页码是否等于i
            if (pb.currentPage == i) {
                // 创建页码的li,添加选中样式
                li = '<li class="curPage" onclick="javascript:load('+ cid + ', ' + i + ', \'' + rname + '\')"><a href="javascript:void(0)">'+ i +'</a></li>';
            } else {
                // 创建页码的li
                li = '<li onclick="javascript:load('+ cid + ', ' + i + ', \'' + rname + '\')"><a href="javascript:void(0)">'+ i +'</a></li>';
            }
            lis += li;
        }
        // 1.4 展示末页和下一页
        // 计算下一页页码
        var nextPageNum = pb.currentPage + 1;
        if (nextPageNum > pb.totalPage) {
            nextPageNum = pb.totalPage;
        }
        // 添加隐藏样式
        if (currentPage >= pb.totalPage) {
            var lastPage = '<li style="display: none"  onclick="javascript:load(' + cid + ', ' + pb.totalPage + ', \'' + rname + '\')"><a href="javascript:void(0);">末页</a></li>';
            var nextPage = '<li style="display: none" onclick="javascript:load(' + cid + ', ' + nextPageNum + ', \'' + rname + '\')" class="threeword"><a href="javascript:void(0)">下一页</a></li>';
            lis += nextPage;
            lis += lastPage;
        } else {
            var lastPage = '<li onclick="javascript:load(' + cid + ', ' + pb.totalPage + ', \'' + rname + '\')"><a href="javascript:void(0);">末页</a></li>';
            var nextPage = '<li onclick="javascript:load(' + cid + ', ' + nextPageNum + ', \'' + rname + '\')" class="threeword"><a href="javascript:void(0)">下一页</a></li>';
            lis += nextPage;
            lis += lastPage;
        }
        // 将分页信息写入页面
        $("#pageNum").html(lis);


        // 2 列表数据展示
        var route_lis = "";
        // 遍历集合获取数据
        for (var i = 0; i < pb.list.length; i++) {
            // 获取每条数据
            var route = pb.list[i];
            // 定义数据展示li
            var li = '<li>\n' +
                '        <div class="img"><img src="' + route.rimage + '" style="width: 299px;"></div>\n' +
                '        <div class="text1">\n' +
                '            <p>' + route.rname + '</p>\n' +
                '            <br/>\n' +
                '            <p>' + route.routeIntroduce + '</p>\n' +
                '        </div>\n' +
                '        <div class="price">\n' +
                '            <p class="price_num">\n' +
                '                <span>&yen;</span>\n' +
                '                <span>' + route.price + '</span>\n' +
                '                <span>起</span>\n' +
                '            </p>\n' +
                '            <p><a href="route_detail.html?rid=' + route.rid +'">查看详情</a></p>\n' +
                '        </div>\n' +
                '    </li>';
            route_lis += li;
        }

        // 3 添加到页面展示
        $("#route").html(route_lis)
        // 定位到页面顶部
        window.scrollTo(0,0);
    });
}





















































































