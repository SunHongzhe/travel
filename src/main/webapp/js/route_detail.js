// 获取线路详细信息
function load(rid) {
    $.get("route/findOne", {rid:rid}, function (route) {
        // 解析数据填充html
        $("#rname").html(route.rname);
        $("#routeIntroduce").html(route.routeIntroduce);
        $("#sname").html(route.seller.sname);
        $("#consphone").html(route.seller.consphone);
        $("#address").html(route.seller.address);
        $("#price").html("¥" + route.price);
        // 设置收藏次数
        $("#favoriteCount").html("已收藏" + route.count + "次");

        // 图片展示
        var dtStr ='<img alt="" class="big_img" src="' + route.routeImgList[0].bigPic + '">';
        $("#dt").html(dtStr);
        var ddStr = "<a class=\"up_img up_img_disable\"></a>";
        // 遍历数组展示图片
        for (var i = 0; i < route.routeImgList.length; i++) {
            var aStr = "";
            if (i < 4 ) {
                aStr = '<a title="picture" class="little_img" data-bigpic="' + route.routeImgList[i].bigPic + '">\n' +
                       '  <img src="' + route.routeImgList[i].smallPic + '">\n' +
                       '</a>';
            } else {
                aStr = '<a title="picture" class="little_img" data-bigpic="' + route.routeImgList[i].bigPic + '" style="display:none;">\n' +
                       '  <img src="' + route.routeImgList[i].smallPic  + '">\n' +
                       '</a>';
            }
            ddStr += aStr;
        }
        ddStr += "<a class=\"down_img down_img_disable\" style=\"margin-bottom: 0;\"></a>";
        $("#dd").html(ddStr);
        goImg();
    });
}

// 判断用户是否收藏
function isFavorite(rid) {
    $.get("favorite/isFavorite", {rid:rid}, function (flag) {
        var a;
        if (flag) {
            // 用户已收藏
            // 设置按钮样式 <a class="btn already" onclick="delete()"><i class="glyphicon glyphicon-heart" style="color:red;">取消收藏</i></a>
            a = '<a class="btn already" onclick="delFavorite()"><i class="glyphicon glyphicon-heart" style="color: red;"></i>取消收藏</a>' +
                '<span id="favoriteCount"></span>';
            $("#favorite").html(a);
        } else {
            // 用户没有收藏或用户未登录
            a = '<a class="btn" onclick="addFavorite();"><i id="heart" class="glyphicon glyphicon-heart"></i>点击收藏</a>' +
                '<span id="favoriteCount"></span>';
            $("#favorite").html(a);
        }
    });
}

// 用户点击添加收藏按钮触发的方法
function add(rid) {
    // 先判断用户是否登录
    $.get("user/findOne", {}, function (data) {
        if (data.flag) {
            // 用户已登录
            $.get("favorite/addFavorite", {rid:rid}, function () {
                // 代码刷新页面
                location.reload();
            });
        } else {
            // 用户未登录
            alert("您尚未登录,请登录");
            location.href = "login.html";
        }
    });
}

// 用户点击取消收藏按钮触发的方法
function del(rid) {
    $.get("favorite/delFavorite", {rid:rid}, function () {
        // 代码刷新页面
        location.reload();
    });
    
}


// 焦点图效果
function goImg() {
    //点击图片切换图片
    $('.little_img').on('mousemove', function() {
        $('.little_img').removeClass('cur_img');
        var big_pic = $(this).data('bigpic');
        $('.big_img').attr('src', big_pic);
        $(this).addClass('cur_img');
    });
    //上下切换
    var picindex = 0;
    var nextindex = 4;
    $('.down_img').on('click',function(){
        var num = $('.little_img').length;
        if((nextindex + 1) <= num){
            $('.little_img:eq('+picindex+')').hide();
            $('.little_img:eq('+nextindex+')').show();
            picindex = picindex + 1;
            nextindex = nextindex + 1;
        }
    });
    $('.up_img').on('click',function(){
        var num = $('.little_img').length;
        if(picindex > 0){
            $('.little_img:eq('+(nextindex-1)+')').hide();
            $('.little_img:eq('+(picindex-1)+')').show();
            picindex = picindex - 1;
            nextindex = nextindex - 1;
        }
    });
}

//自动轮播方法
function auto_play() {
    var cur_index = $('.prosum_left dd').find('a.cur_img').index();
    cur_index = cur_index - 1;
    var num = $('.little_img').length;
    var max_index = 3;
    if ((num - 1) < 3) {
        max_index = num - 1;
    }
    if (cur_index < max_index) {
        var next_index = cur_index + 1;
        var big_pic = $('.little_img:eq(' + next_index + ')').data('bigpic');
        $('.little_img').removeClass('cur_img');
        $('.little_img:eq(' + next_index + ')').addClass('cur_img');
        $('.big_img').attr('src', big_pic);
    } else {
        var big_pic = $('.little_img:eq(0)').data('bigpic');
        $('.little_img').removeClass('cur_img');
        $('.little_img:eq(0)').addClass('cur_img');
        $('.big_img').attr('src', big_pic);
    }
}
