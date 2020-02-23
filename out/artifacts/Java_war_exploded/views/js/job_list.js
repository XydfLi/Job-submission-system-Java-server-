
$(function(){

    $("#job tr:not(:first)").remove(); 
    // 分页
    $.ajax({
        url: "/api/administrators/tasks",
        type: "GET",
        data: {},
        dataType: 'json',
        contentType: "application/json",

        success: function(data) {
            let page_num = $('#page_num').text();
            let total_num = data.data.count;
            $('#total_cnt').text(total_num);
            let page_cnt = Math.ceil(total_num / 9);  //一页九条，总页数

            let da = new Date();
            let y = da.getFullYear();
            let m = (da.getMonth() + 1).toString().padStart(2, "0");
            let d = da.getDate().toString().padStart(2, "0");
            let time = y+"-"+m+"-"+d;
            time = Date.parse(time);

            let table = $('#job');
            function page_show(cur_page, tot_page, tot) {
                if(cur_page == tot_page) {
                    for(let i = (cur_page - 1) * 9;i <= tot - 1;i++) {
                        let ddl = Date.parse(data.data.taskWithNumberList[i].deadline);
                        let text = "进行中";
                        if(time - ddl > 0) {
                            text = "结束";
                        }

                        let tr = 
                        `<tr>
                            <td class="id">${data.data.taskWithNumberList[i].taskId}</td>
                            <td class="name">${data.data.taskWithNumberList[i].taskName}</td>
                            <td class="begin_time">${data.data.taskWithNumberList[i].startDate}</td>
                            <td class="ddl">${data.data.taskWithNumberList[i].deadline}</td>
                            <td class="state">${text}</td>
                            <td class="num">${data.data.taskWithNumberList[i].number}</td>
                            <td class="operate">
                                <i class="fa fa-edit fa-lg fa-fw" title="编辑"></i>
                                <i class="fa fa-info-circle fa-lg fa-fw" title="详细"></i>
                                <i class="fa fa-trash-o fa-lg fa-fw" title="删除"></i>
                            </td>
                        </tr>`
                        table.append(tr);
                    }
                }
                else {
                    for(let i = (cur_page - 1) * 9;i <= cur_page * 9 - 1;i++) {
                        let ddl = Date.parse(data.data.taskWithNumberList[i].deadline);
                        let text = "进行中";
                        if(time - ddl > 0) {
                            text = "结束";
                        }

                        let tr = 
                        `<tr>
                            <td class="id">${data.data.taskWithNumberList[i].taskId}</td>
                            <td class="name">${data.data.taskWithNumberList[i].taskName}</td>
                            <td class="begin_time">${data.data.taskWithNumberList[i].startDate}</td>
                            <td class="ddl">${data.data.taskWithNumberList[i].deadline}</td>
                            <td class="state">${text}</td>
                            <td class="num">${data.data.taskWithNumberList[i].number}</td>
                            <td class="operate">
                                <i class="fa fa-edit fa-lg fa-fw" title="编辑"></i>
                                <i class="fa fa-info-circle fa-lg fa-fw" title="详细"></i>
                                <i class="fa fa-trash-o fa-lg fa-fw" title="删除"></i>
                            </td>
                        </tr>`
                        table.append(tr);
                    }
                }
            }

            page_show(page_num, page_cnt, total_num);
            if(page_num == page_cnt) {
                $('#pre_page').attr("disabled", true);
                $('#next_page').attr("disabled", true);
            }
            else {
                $('#pre_page').attr("disabled", true);
            }
            
            $('#pre_page').click(function() {
                if(page_num == 2) {
                    $('#pre_page').attr("disabled", true);
                    $('#next_page').attr("disabled", false);
                }
                else {
                    $('#pre_page').attr("disabled", false);
                    $('#next_page').attr("disabled", false);
                }   
                page_num--;
                $('#page_num').text(page_num);
                $("#job tr:not(:first)").remove(); 
                page_show(page_num, page_cnt, total_num);
            });

            $('#next_page').click(function() {
                if(page_num == page_cnt - 1) {
                    $('#pre_page').attr("disabled", false);
                    $('#next_page').attr("disabled", true);
                }
                else {
                    $('#pre_page').attr("disabled", false);
                    $('#next_page').attr("disabled", false);
                }
                page_num++;
                $('#page_num').text(page_num);
                $("#job tr:not(:first)").remove(); 
                page_show(page_num, page_cnt, total_num);
            });
        }
    });


    // 删除考核
    $(document).on('click', '.fa-trash-o', function(){

        let parent = $(this).parent().parent();
        let id = parent.find("td").eq(0).text();
        //console.log(id);

        let url = `/api/administrators/tasks/${id}`;
        $.ajax({
            url: url,
            type: "DELETE",
            data: {},
            dataType: 'json',
            contentType: "application/json",

            success: function(data) {
                if(data.status == 200) {
                    console.log(data.info);
                    let num = $('#total_cnt').text();
                    $('#total_cnt').text(num-1);
                    parent.remove();
                    $("#job tr:not(:first)").remove();
                    $('#job_list').load("job_list.html");
                }
            }
        });
    });
});