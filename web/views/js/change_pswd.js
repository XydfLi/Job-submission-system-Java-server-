
$(function(){

    let o_pswd = $("input[name='o_pswd']");
    let n_pswd = $("input[name='n_pswd']");
    let rn_pswd = $("input[name='rn_pswd']");

    n_pswd.blur(function() {
        if(n_pswd.val() != rn_pswd.val()) {
            $("#error_same").fadeIn();
        }
        else if(n_pswd.val() == rn_pswd.val()) {
            $("#error_same").fadeOut();
        }
    });

    rn_pswd.blur(function() {
        if(n_pswd.val() != rn_pswd.val()) {
            $("#error_same").fadeIn();
        }
        else if(n_pswd.val() == rn_pswd.val()) {
            $("#error_same").fadeOut();
        }
    });

    $("#pswd_btn").click(function() {
        if(o_pswd.val() == "" || n_pswd.val() == "" || $("#error_same").is(':visible') || $("#error_wrong").is(':visible')) {
            return;
        }

        let data = {
            oldPassword: $("input[name='o_pswd']").val(),
            newPassword: $("input[name='n_pswd']").val(),
        }
        //console.log(json_data);

        let json_data = JSON.stringify(data);
        $.ajax({
            url: "/api/users",
            type: "PUT",
            data: json_data,
            dataType: 'json',
            contentType: "application/json",

            success: function(data) {
                if(data.status == 200) {
                    $("#page").hide();
                    $("#change").fadeIn(3000).fadeOut(1000, function() {
                        $("#page").fadeIn();
                        $(".input_pswd").val("");
                    });
                }
            }
        });
    });
});