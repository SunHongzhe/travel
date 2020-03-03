/**
 表单验证
 */
String.prototype.trim = function () {
    return this.replace(/^\s+/, "").replace(/\s+$/, "");
}

// 用户名验证
function checkUsername() {
    /*var flag = false;
    var reg = /^[a-z][a-z0-9]{5}$/;
    var username = $("#username").val();
    username = username.trim(); // 去除前后空白
    var nameError = $("#usernameError");
    if (username == "") {
        nameError.html("账号不不能为空");
        flag = false;
    } else if (!reg.test(username)) {
        nameError.html("账号不符合规则");
        flag = false;
    } else {
        nameError.css("visibility","visible");
        nameError.html("");
        flag = true;
    }
    return flag;*/
    var reg = /^[a-z][a-z0-9]{5,15}$/;
    var username = $("#username").val();
    username = username.trim();
    var flag = reg.test(username);
    if (flag) {
        $.get("user/findUsername",{username:username},function (data) {
            if (data.flag) {
                $("#username").css("border", "");
                $("#errorMsg").html("用户名可用");
                $("#usernameRight").css("visibility","visible");
                $("#userNameFlag").val("true")
            } else {
                $("#errorMsg").html(data.errorMsg);
                $("#username").css("border", "1px solid red");
                $("#userNameFlag").val("false")
            }
        });
    } else {
        $("#errorMsg").html("账号需由6-16位的字母和数字组成");
        $("#username").css("border", "1px solid red");
        $("#userNameFlag").val("false")
    }
}

// 验证密码
function checkPassword() {
    /* var flag = false;
     var password = $("#password").val();
     password = password.trim();
     var passwordError = $("#passwordError");
     var reg = /^\w{6}$/;
     if (password == "") {
         passwordError.html("密码不能为空");
         flag = false;
     } else if (!reg.test(password)) {
         passwordError.html("密码长度需大于6位");
         flag = false;
     } else {
         passwordError.html("");
         flag = true;
     }
     return flag;*/

    var reg = /^\w{6}\w+$/;
    var password = $("#password").val();
    password = password.trim();
    var flag = reg.test(password);
    if (flag) {
        $("#password").css("border", "");
        $("#errorMsg").html("请妥善保管好你的密码");
        $("#passwordRight").css("visibility","visible");
    } else {
        $("#errorMsg").html("密码不能少于6位");
        $("#password").css("border", "1px solid red");
    }
    return flag;
}

// 验证密码
function checkRepeat() {
    /* var flag = false;
     var password = $("#password").val();
     var repeat = $("#repeat").val();
     repeat = repeat.trim();
     var repeatError = $("#repeatError");
     if (repeat == "") {
         repeatError.html("验证密码不能为空");
         flag = false;
     } else if (password != repeat) {
         repeatError.html("验证密码错误");
         flag = false;
     } else {
         repeatError.html("");
         flag = true;
     }
     return flag;*/


    var password = $("#password").val();
    var repeat = $("#repeat").val();
    repeat = repeat.trim();
    var flag = (repeat == password);
    if (flag) {
        $("#repeat").css("border", "");
        $("#errorMsg").html("密码正确");
        $("#repeatRight").css("visibility","visible");
    } else {
        $("#repeat").css("border", "1px solid red");
        $("#errorMsg").html("密码不一致,请重新输入");
    }
    return flag;
}

// 验证email
function checkEmail() {
    /*var flag = false;
    var email = $("#email").val();
    email = email.trim();
    var reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
    var emailError = $("#emailError");
    if (email == "") {
        emailError.html("email不能为空");
        flag = false;
    } else if (!reg.test(email)) {
        emailError.html("email格式不合法");
        flag = false;
    } else {
        emailError.html("");
        flag = true;
    }
    return flag;*/

    var reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
    var email = $("#email").val();
    email = email.trim();
    var flag = reg.test(email);
    if (flag) {
        $("#email").css("border", "");
        $("#errorMsg").html("请确保邮箱可用");
        $("#emailRight").css("visibility","visible");
    } else {
        $("#email").css("border", "1px solid red");
        $("#errorMsg").html("邮箱格式不符合要求");
    }
    return flag;
}

// 姓名验证(不为空)
function checkName() {
    /*var flag = false;
    var reg =  /^[\u4e00-\u9fa5]{2,6}$/;
    var name = $("#name").val();
    name = name.trim(); // 去除前后空白
    var nameError = $("#nameError");
    if (name == "") {
        nameError.html("姓名不能为空");
        flag = false;
    } else if (!reg.test(name)) {
        nameError.html("姓名必须由2-6个中文组成");
        flag = false;
    } else {
        nameError.html("");
        flag = true;
    }
    return flag;*/

    var reg = /^[\u4e00-\u9fa5]{2,6}$/;
    var name = $("#name").val();
    name = name.trim();
    var flag = reg.test(name);
    if (flag) {
        $("#name").css("border", "");
        $("#errorMsg").html("姓名可用");
        $("#nameRight").css("visibility","visible");
    } else {
        $("#name").css("border", "1px solid red");
        $("#errorMsg").html("请填写2-6位汉字");
    }
    return flag;
}

// 验证手机号
function checkTelephone() {
    /*var flag = false;
    var telephone = $("#telephone").val();
    telephone = telephone.trim();
    var reg = /^1[3456789]\d{9}$/;
    var telephoneError = $("#telephoneError");
    if (telephone == "") {
        telephoneError.html("手机号不能为空");
        flag = false;
    } else if (!reg.test(telephone)) {
        telephoneError.html("手机号不合法");
        flag = false;
    } else {
        telephoneError.html("");
        flag = true;
    }
    return flag;*/

    var reg = /^1[3456789]\d{9}$/;
    var telephone = $("#telephone").val();
    telephone = telephone.trim();
    var flag = reg.test(telephone);
    if (flag) {
        $("#telephone").css("border", "");
        $("#errorMsg").html("手机号可用");
        $("#telephoneRight").css("visibility","visible");
    } else {
        $("#telephone").css("border", "1px solid red");
        $("#errorMsg").html("手机号格式不正确");
    }
    return flag;
}

// 验证出生日期(不为空)
function checkBirthday() {
    /*var flag = false;
    var birthday = $("#birthday").val();
    birthday = birthday.trim();
    var birthdayError = $("#birthdayError");
    if (birthday == "") {
        birthdayError.html("生日不能为空");
        flag = false;
    } else {
        birthdayError.html("");
        flag = true;
    }
    return flag;*/

    var birthday = $("#birthday").val();
    birthday = birthday.trim();
    if (birthday != "") {
        $("#birthday").css("border", "");
        $("#errorMsg").html("加油,你是最胖的");
        $("#birthdayRight").css("visibility","visible");
    } else {
        $("#birthday").css("border", "1px solid red");
        $("#errorMsg").html("生日不能为空");
    }
    return birthday != "";
}

// 验证出生日期(不为空)
function checkCheck() {
    /*var flag = false;
    var check = $("#check").val();
    check = check.trim();
    var checkError = $("#checkError");
    if (check == "") {
        checkError.html("验证码不能为空");
        flag = false;
    } else {
        checkError.html("");
        flag = true;
    }
    return flag;*/

    var check = $("#check").val();
    check = check.trim();
    if (check != "") {
        $("#check").css("border", "");
    } else {
        $("#check").css("border", "1px solid red");
        $("#errorMsg").html("验证码不能为空");
    }
    return check != "";
}


// 输入验证
$(function () {
    $("#username").blur(checkUsername);
    $("#password").blur(checkPassword);
    $("#repeat").blur(checkRepeat);
    $("#email").blur(checkEmail);
    $("#name").blur(checkName);
    $("#telephone").blur(checkTelephone);
    $("#check").blur(checkCheck);
    $("#birthday").blur(checkBirthday)
});

// 提交验证
/*$(function () {
    $("#registerForm").submit(function () {
        if (checkUsername() && checkPassword() && checkRepeat() &&
            checkEmail() && checkName() && checkTelephone() &&
            checkCheck() && checkBirthday()) {
            $.post("user/*", $(this).serialize(), function (data) {
                
                        
            });
        }
    return false;
});*/


function clearUsernameError() {
    var usernameError = $("#usernameError");
    usernameError.css("color", "green");
    usernameError.html("以字母开头,只包含字母和数字,长度为6-16位")
}

function clearPasswordError() {
    var passwordError = $("#passwordError");
    passwordError.css("color", "green");
    passwordError.html("密码大于6位");

}

function clearEmailError() {
    var emailError = $("#emailError");
    emailError.css("color", "green");
    emailError.html("请填写有效邮箱用于激活");
}

function clearNameError() {
    var nameError = $("#nameError");
    nameError.css("color", "green");
    nameError.html("姓名需为2-6位汉字");
}

function clearTelephoneError() {
    var telephoneError = $("#telephoneError");
    telephoneError.css("color", "green");
    telephoneError.html("填写有效手机号");
}

function clearBirthdayError() {
    var birthdayError = $("#birthdayError");
    birthdayError.css("color", "green");
    birthdayError.html("生日不能为空");
}
