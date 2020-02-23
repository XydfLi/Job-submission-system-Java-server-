
$(function () {

    $('#submit_btn').click(function () {

        if ($('#input_id').val() == "") {
            return;
        }

        let taskId = $('#input_id').val();
        $.ajax({
            url: `/api/students/tasks/${taskId}`,
            type: "GET",
            data: {},
            dataType: 'json',
            contentType: "application/json",

            success: function (data) {
                if (data.info == "OK") {
                    $('#content_name').text(data.data.taskName);
                    $('#content_explain_text').text(data.data.taskContent);

                    $('#login_id').fadeOut(function () {
                        $('#content').fadeIn();
                    });
                }
                else {
                    alert(data.info);
                }
            }
        });
    });

    $('#content_file').change(function(){
        let file = $('#file').get(0).files[0];
        if(file != undefined) {
            $('#tips').text(file.name);
        }            
    });

    $('#upload').click(function () {
        let user_name = $('#user_name');
        let user_number = $('#user_number');
        let file = $('#file').get(0).files[0];

        if (user_name.val() == "" || user_number.val() == "" || file == undefined) {
            return;
        }

        let re_length = /^\d{9}$/;
        if(!re_length.test(user_number.val())) {
            alert("学号格式错误！");
            return;
        }

        let point = file.name.lastIndexOf(".");
        let file_type = file.name.substr(point);
        //console.log(file_type);
        
        if(file_type != ".zip") {
            alert("文件格式错误！");
            return;
        }

        let taskId = $('#input_id').val();
        let accountId = user_number.val();

        let form_data = new FormData();
        form_data.append("file", file);

        let url = `/api/assignments/upload/${taskId}/${accountId}`
        console.log(url);
        $.ajax({
            url: url,
            type: "POST",
            data: form_data,
            contentType: false,
            processData: false,

            success: function(data) {
                if(data.status == 200) {
                    console.log(data.info);
                }

                $('#content').fadeOut(function () {
                    $('#success').fadeIn();
                });
            }
        });
    });

    $('#back_btn').click(function () {
        $('#success').fadeOut(function () {
            $('#login_id').fadeIn();
            location.reload();
        });
    });
});