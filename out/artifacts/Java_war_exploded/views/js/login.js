
$(document).ready(function(){
    
    let login = $("#btn-fl");
    let register = $("#btn-fr");
    let login_begin = $("#login-begin");
    let login_btn = $("#login-btn");
    let login_page = $("#login-page");
    let register_page = $("#register-page");
    let close = $(".close");

    let login_name = $("#login_name");
    let login_pswd = $("#login_pswd");
    let register_name = $("#register_name");

    // 确认密码
    let pswd = $("#pswd");
    let rpswd = $("#rpswd");

    //点击登陆
    login.click(function() {
        login_begin.slideUp();
        login_btn.slideUp(function() {
            login_page.slideDown('slow');
        });
    });

    //点击注册
    register.click(function() {
        login_begin.slideUp();
        login_btn.slideUp(function() {
            register_page.slideDown('slow');
        });
    });

    //点击返回初始页
    close.click(function() {
        if(login_page.is(':visible')) {
            login_name.val("");
            login_pswd.val("");
            login_page.slideUp(function(){
                login_begin.slideDown('slow');
                login_btn.slideDown('slow');
            });
        }
        else {
            register_name.val("");
            pswd.val("");
            rpswd.val("");
            register_page.slideUp(function() {
                login_begin.slideDown('slow');
                login_btn.slideDown('slow');
            });
        }
        
    });

    //确认密码
    rpswd.blur(function() {
        
        if(pswd.val() != rpswd.val()) {
            $("#error_same").fadeIn();
        }
        else if(pswd.val() == rpswd.val()) {
            $("#error_same").fadeOut();
        }

    });
    pswd.blur(function() {

        if(pswd.val() != rpswd.val()) {
            $("#error_same").fadeIn();
        }
        else if(pswd.val() == rpswd.val()) {
            $("#error_same").fadeOut();
        }

    });

    //跳转页面
    //登陆
    $("#login_btn").click(function() {

        if(login_name.val() == "" || login_pswd.val() == "") {
            return;
        }

        let data = {
            accountId: login_name.val(),
            passWord: login_pswd.val(),
        }
        //console.log(json_data);
    
        let json_data = JSON.stringify(data);
        $.ajax({
            url: "/api/users/login",
            type: "POST",
            data: json_data,
            dataType: 'json',
            contentType: "application/json",

            success: function(data) {
                if(data.data == 0) {
                    alert(data.info);
                }
                else {
                    $(location).attr("href", "back.html");
                }
            }
        });
    });

    //注册
    $("#register_btn").click(function() {

        if($("#error_same").is(':visible') || register_name.val() == "" || pswd.val() == "") {
            return;
        }

        let re_type = /^\d+$/;
        if(!re_type.test(register_name.val())) {
            alert("账号只能包含数字！");
            return;
        }

        let data = {
            accountId: register_name.val(),
            passWord: pswd.val(),
        }
        //console.log(json_data);
    
        let json_data = JSON.stringify(data);
        $.ajax({
            url: "/api/users/students",
            type: "POST",
            data: json_data,
            dataType: 'json',
            contentType: "application/json",

            success: function(data) {
                if(data.info == "OK") {
                    $(location).attr("href", "back.html");//跳转到后台
                }
                else {
                    alert(data.info);
                }
            }
        });
    });
});
