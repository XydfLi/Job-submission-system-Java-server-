
$(function(){

    //显示时间
    function change_time(){
        let da = new Date();
        let y = da.getFullYear();
        let mo = (da.getMonth() + 1).toString().padStart(2, "0");
        let d = da.getDate().toString().padStart(2, "0");
        let time = y+'/'+mo+'/'+d+"<br >&nbsp;&nbsp;&nbsp;星期"+"日一二三四五六".charAt(da.getDay());

        let date = $("#date");
        date.html(time);
    }
    change_time();

    let show_tag = $("#show_tag");
    let hide_tag = $("#hide_tag");

    //防止事件冒泡
    //显示二级菜单
    show_tag.click(function(event){
        event = event || window.event;
        if(event.stopPropagation){
            event.stopPropagation();
            //停止DOM
        }
        else{
            event.cancelBubble = true;
            //停止IE
        }
        show_tag.hide();
        hide_tag.show();
        $("#second_title").show();
    });

    //隐藏二级菜单
    hide_tag.click(function(event){
        event = event || window.event;
        if(event.stopPropagation){
            event.stopPropagation();
            //停止DOM
        }
        else{
            event.cancelBubble = true;
            //停止IE
        }
        show_tag.show();
        hide_tag.hide();
        $("#second_title").hide();
    });

    //点击空白处收起二次菜单
    $(document).click(function(){
        show_tag.show();
        hide_tag.hide();
        $("#second_title").hide();
    });

    //退出登录
    $('#login_out').click(function() {
        let json_data = {};
        
        $.ajax({
            url: "/api/users/logout",
            type: "DELETE",
            data: json_data,
            dataType: "json",
            contentType: "application/json",

            success: function() {
                $(location).attr("href", "login.html");
            }
        });
    });
});