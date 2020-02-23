
$(function(){

    // 考核详细页
    $(document).on('click', '.fa-info-circle', function(){
        $('#job_list').fadeOut(function() {
            $('#job_detail').fadeIn();
        });

        let taskId = $(this).parent().parent().find('td').eq(0).text();
        //console.log(taskId);
        let url = `/api/administrators/assignments/${taskId}`
        $.ajax({
            url: url,
            type: "GET",
            data: {},
            dataType: 'json',
            contentType: "application/json",

            success: function(data) {
                let page_num = $('#hpage_num').text();
                let total_num = data.data.count;
                $('#htotal_cnt').text(total_num);
                let page_cnt = Math.ceil(total_num / 9);  //一页九条，总页数
    
                function state(num) {
                    if(num == 1) {
                        return "审核中";
                    }
                    else if(num == 2) {
                        return "通过";
                    }
                    else {
                        return "未通过";
                    }
                }
                let table = $('#hjob');
                function page_show(cur_page, tot_page, tot) {
                    if(cur_page == tot_page) {
                        for(let i = (cur_page - 1) * 9;i <= tot - 1;i++) {
                            let tr = 
                            `<tr>
                                <td><input type="checkbox"></td>
                                <td class="hnum">${data.data.assignmentList[i].accountId}</td>
                                <td class="hname">${data.data.assignmentList[i].name}</td>
                                <td class="hup_time">${data.data.assignmentList[i].subTime}</td>
                                <td class="hstate" title="双击修改状态">${state(data.data.assignmentList[i].mark)}</td>
                                </td>
                                <td class="hoperate">
                                    <i class="fa fa-download fa-lg fa-fw" title="下载"></i>
                                </td>
                            </tr>`
                            table.append(tr);
                        }
                    }
                    else {
                        for(let i = (cur_page - 1) * 9;i <= cur_page * 9 - 1;i++) {
                            let tr = 
                            `<tr>
                                <td><input type="checkbox"></td>
                                <td class="hnum">${data.data.assignmentList[i].accountId}</td>
                                <td class="hname">${data.data.assignmentList[i].name}</td>
                                <td class="hup_time">${data.data.assignmentList[i].subTime}</td>
                                <td class="hstate" title="双击修改状态">${state(data.data.assignmentList[i].mark)}</td>
                                <td class="hoperate">
                                    <i class="fa fa-download fa-lg fa-fw" title="下载"></i>
                                </td>
                            </tr>`
                            table.append(tr);
                        }
                    }
                }
    
                page_show(page_num, page_cnt, total_num);
                if(page_num == page_cnt) {
                    $('#hpre_page').attr("disabled", true);
                    $('#hnext_page').attr("disabled", true);
                }
                else {
                    $('#hpre_page').attr("disabled", true);
                }
                
                $('#hpre_page').click(function() {
                    if(page_num == 2) {
                        $('#hpre_page').attr("disabled", true);
                        $('#hnext_page').attr("disabled", false);
                    }
                    else {
                        $('#hpre_page').attr("disabled", false);
                        $('#hnext_page').attr("disabled", false);
                    }   
                    page_num--;
                    $('#hpage_num').text(page_num);
                    $("#hjob tr:not(:first)").remove(); 
                    page_show(page_num, page_cnt, total_num);
                });
    
                $('#hnext_page').click(function() {
                    if(page_num == page_cnt - 1) {
                        $('#hpre_page').attr("disabled", false);
                        $('#hnext_page').attr("disabled", true);
                    }
                    else {
                        $('#hpre_page').attr("disabled", false);
                        $('#hnext_page').attr("disabled", false);
                    }
                    page_num++;
                    $('#hpage_num').text(page_num);
                    $("#hjob tr:not(:first)").remove(); 
                    page_show(page_num, page_cnt, total_num);
                });
            }
        });
        

        // 全选
        let check = $("#check input[type='checkbox']");
        function can_check(){

            let have = $("tr").length;
            if(have == 1){
                check.attr("disabled", true);
            }
            else{

                check.attr("disabled", false);
                check.click(function(){

                    if(check.prop("checked")){
                        $("input[type='checkbox']").prop("checked", true);
                    }
                    else{
                        $("input[type='checkbox']").prop("checked", false);
                    }
                });
                
                
            }
        }
        can_check();


        // 批量删除
        $("#delt").click(function(){
            check.prop("checked", false);
            let checked = $("input[type='checkbox']:checked");
            let num = checked.length;
            //console.log(num);

            let id = new Array();
            checked.each(function() {
                id.push($(this).parent().parent().find('td').eq(1).text());
            });

            let data = {
                count: num,
                taskId: taskId,
                accountIdList: id,
            }

            let json_data = JSON.stringify(data);
            $.ajax({
                url: "/api/administrators/assignments",
                type: "PUT",
                data: json_data,
                dataType: 'json',
                contentType: "application/json",

                success: function(data) {
                    console.log(data.info);
                    let cnt = $('#htotal_cnt').text();
                    $('#htotal_cnt').text(cnt-num);
                    checked.parent().parent().remove();
                    //$("#hjob tr:not(:first)").remove();
                    //$('#job_list').load("job_list.html");
                }
            });
        });


        // 双击改变作业状态
        $(document).on('dblclick', '.hstate', function() {
            let p = $(this);
            let select = 
            `<select>
                <option value="审核中" selected>审核中</option>
                <option value="通过">通过</option>
                <option value="未通过">未通过</option>
            </select>`

            if(p.text() == "通过") {
                select = 
                `<select>
                    <option value="审核中">审核中</option>
                    <option value="通过" selected>通过</option>
                    <option value="未通过">未通过</option>
                </select>`
            }
            else if(p.text() == "未通过") {
                select = 
                `<select>
                    <option value="审核中">审核中</option>
                    <option value="通过">通过</option>
                    <option value="未通过" selected>未通过</option>
                </select>`
            }
            $(this).html(select);
            $('select').trigger('focus');
            $('select').blur(function() {
                let new_p = $('select').val();
                p.text(new_p);

                let mark = 1;
                if(new_p == "通过") {
                    mark = 2;
                }
                else if(new_p == "未通过") {
                    mark = 3;
                }

                let accountId = p.parent().find('td').eq(1).text();
                let f_url = `/api/administrators/assignments/${taskId}/${accountId}/${mark}`;
                //console.log(f_url);
                $.ajax({
                    url: f_url,
                    type: "PUT",
                    data: {},
                    dataType: 'json',
                    contentType: "application/json",

                    success: function(data) {
                        console.log(data.info);
                    }
                });
            });
        });


        // 下载文件
        $(document).on('click', '.fa-download', function() {
            let num = $(this).parent().parent().find('td').eq(1).text();
            let down_url = `/api/assignments/download/${taskId}/${num}`;
            
            let a = $("<a>");
            a.attr("href", down_url);
            a[0].click();
        });


        // 返回考核列表
        let back = $("#hback");
        back.click(function(){
            $('#job_detail').fadeOut(function() {
                $('#job_list').fadeIn();
                $('#job_list').load("job_list.html");
                $("#hjob tr:not(:first)").remove(); 
            });
        });
    });
});