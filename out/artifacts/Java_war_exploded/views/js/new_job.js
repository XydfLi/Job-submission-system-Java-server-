
$(function(){

    let job_name = $("#job_name");
    let job_deadline = $("#job_deadline");
    let job_explain = $("#job_explain");

    let da = new Date();
    let y = da.getFullYear();
    let m = (da.getMonth() + 1).toString().padStart(2, "0");
    let d = da.getDate().toString().padStart(2, "0");
    let time = y+"-"+m+"-"+d;
    job_deadline.attr("min", time);
    
    let job_btn = $("#job_btn");
    job_btn.click(function(){

        if(job_name.val() == "" || job_deadline.val() == "") {
            return;
        }

        let data = {
            taskName: job_name.val(),
            taskContent: job_explain.val(),
            startDate: time,
            deadline: job_deadline.val(),
        }

        let json_data = JSON.stringify(data);
        //console.log(json_data);
        
        $.ajax({
            url: "/api/administrators/tasks",
            type: "POST",
            data: json_data,
            dataType: 'json',
            contentType: "application/json",

            success: function(data) {
                if(data.status == 200) {
                    alert("成功建立考核！考核的ID为：" + data.data);
                    job_name.val("");
                    job_deadline.val("");
                    job_explain.val("");
                }
            }
        });
    });

});