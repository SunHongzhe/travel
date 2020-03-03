/*
    分页栏效果
    <li><a href="">首页</a></li>
    <li class="threeword"><a href="#">上一页</a></li>
    <li><a href="#">1</a></li>
    <li class="threeword"><a href="javascript:;">下一页</a></li>
    <li class="threeword"><a href="javascript:;">末页</a></li>
*/

/*
    数据展示
    <div class="col-md-3">
        <a href="route_detail.html">
            <img src="images/collection_pic2.jpg" alt="">
            <div class="has_border">
                <h3>新西兰-南北岛9日游，纯玩不进店</h3>
                <div class="price">网付价<em>￥</em><strong>17599</strong><em>起</em></div>
            </div>
        </a>
    </div>
 */

function load(currentPage) {
    $.get("favorite/myFavorite", {currentPage:currentPage}, function (pb) {
        // 解析pageBean数据，展示到页面上

        // 1 分页工具条展示
        // 1.1 展示总页码和总记录数
        $("#totalPage").html(pb.totalPage);
        $("#totalCount").html(pb.totalCount);
        var lis = "";
        // 1.2 展示首页和上一页
        // 计算上一页页码
        prePageNum = currentPage - 1;
        if (prePageNum < 1) {
            prePageNum = 1;
        }
        // 添加隐藏样式
        if (currentPage <= 1 || currentPage == null) {
            // 隐藏样式
            var firstPage = '<li style="display: none" onclick="javascript:load(' + 1 + ')" class="threeword"><a href="javascript:void(0);">首页</a></li>';
            var prePage = '<li style="display: none" onclick="javascript:load(' + prePageNum + ')" class="threeword"><a href="javascript:void(0)">上一页</a></li>';
            lis += firstPage;
            lis += prePage;
        } else {
            // 不隐藏样式
            var firstPage = '<li id="firstPage" onclick="javascript:load(' + 1 + ')" class="threeword"><a href="javascript:void(0);">首页</a></li>';
            var prePage = '<li id="prePage" onclick="javascript:load(' + prePageNum + ')" class="threeword"><a href="javascript:void(0)">上一页</a></li>';
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
                li = '<li class="curPage" onclick="javascript:load(' + i + ')"><a href="javascript:void(0)">'+ i +'</a></li>';
            } else {
                // 创建页码的li
                li = '<li onclick="javascript:load(' + i + ')"><a href="javascript:void(0)">'+ i +'</a></li>';
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
            var nextPage = '<li style="display: none" onclick="javascript:load(' + nextPageNum + ')" class="threeword"><a href="javascript:void(0)">下一页</a></li>';
            var lastPage = '<li style="display: none"  onclick="javascript:load(' + pb.totalPage + ')"><a href="javascript:void(0);">末页</a></li>';
            lis += nextPage;
            lis += lastPage;
        } else {
            var nextPage = '<li onclick="javascript:load(' + nextPageNum + ')" class="threeword"><a href="javascript:void(0)">下一页</a></li>';
            var lastPage = '<li onclick="javascript:load(' + pb.totalPage + ')"><a href="javascript:void(0);">末页</a></li>';
            lis += nextPage;
            lis += lastPage;
        }
        // 将分页信息写入页面
        $("#pageNum").html(lis);

        // 2 列表数据展示
        var route_divs = "";
        // 遍历集合获取数据
        for (var i = 0; i < pb.list.length; i++) {
            // 获取每条数据
            var route = pb.list[i];
            // 定义数据展示div
            var div = '<div class="col-md-3">\n' +
                '        <a href="route_detail.html?rid=' + route.rid + '">\n' +
                '            <img src="' + route.rimage + '" alt="">\n' +
                '            <div class="has_border">\n' +
                '                <h3>' + route.rname + '</h3>\n' +
                '                <div class="price">网付价<em>￥</em><strong>' + route.price + '</strong><em>起</em></div>\n' +
                '            </div>\n' +
                '        </a>\n' +
                '    </div>';
            route_divs += div;
        }
        // 3 添加到页面展示
        $("#myFavorite").html(route_divs)
        // 定位到页面顶部
        window.scrollTo(0,0);
    });
    
}















































































