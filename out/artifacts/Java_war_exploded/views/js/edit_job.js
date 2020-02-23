
$(function() {

    // 编辑考核
    $(document).on('click', '.fa-edit', function(){

        $('#job_list').fadeOut(function() {
            $('#edit_job').fadeIn();
        });

        let taskId = $(this).parent().parent().find('td').eq(0).text();
        //console.log(taskId);
        let url = `/api/administrators/tasks/${taskId}`;
        $.ajax({
            url: url,
            type: "GET",
            data: {},
            dataType: 'json',
            contentType: "application/json",

            success: function(data) {
                $("input[name='job_name']").val(data.data.taskName);
                $("input[name='job_deadline']").val(data.data.deadline);
                $('#job_explain').val(data.data.taskContent);
            }
        });

        let job_btn = $("#job_btn");
        job_btn.click(function(){

            //console.log(taskId);
            let data = {
                taskId: taskId,
                taskName: $("input[name='job_name']").val(),
                taskContent: $('#job_explain').val(),
                deadline: $("input[name='job_deadline']").val(),
            }

            let json_data = JSON.stringify(data);
            $.ajax({
                url: "/api/administrators/tasks",
                type: "PATCH",      //这里看看到时候会不会有问题
                data: json_data,
                dataType: 'json',
                contentType: "application/json",

                success: function(data) {
                    if(data.info == "OK") {
                        alert("修改完成");
                    }
                    else {
                        console.log(data.info);
                    }
                }
            });
        });

        let da = new Date();
        let y = da.getFullYear();
        let m = (da.getMonth() + 1).toString().padStart(2, "0");
        let d = da.getDate().toString().padStart(2, "0");
        let job_ddl = $("input[name='job_deadline']");
        job_ddl.attr("min", y+"-"+m+"-"+d);

    });


    // 返回考核列表
    let back = $("#eback");
    back.click(function(){
        $('#edit_job').fadeOut(function() {
            $('#job_list').load("job_list.html");
            $('#job_list').fadeIn();
        });
    });
});