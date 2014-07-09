function c(){
    	
				$('.dropdown-menu').toggleClass("m1");		
    	};
  	function c1(id){
    	
				$('div ul li#'+id).toggleClass("active");		
    	};

	function submit(id1, id2)
	{
		
		var html1 = document.getElementById(id1).value;
		var html2 = document.getElementById(id2).value;
		$.ajax({
        	url: "http://10.131.228.215/xFudan.php/User/login",
        	dataType: "JSON",
        	type: "POST",
        	data:{login_id:html1,password:html2},
        	success: function a1(data)
        	{
        		if(data.success != true)
        		{
        			alert("username: " + html1 + "\n" + "password: " + html2 + "\n" + (data.reason));
        		}
        		else
        		{
        			window.location.href = "main.html";
        		}
        	}
		});
	}
	
	
	function search_event(event_id)
	{
		$.ajax({
        	url: "http://10.131.228.215/xFudan.php/Event/getEventDetail",
        	dataType: "JSON",
        	type: "GET",
        	data:{eid:event_id},
        	success: function a1(data)
        	{
        		if(data.success == true)
        		{
        			var html = "<tr><td>名称：</td><td>" + data.event_detail.event_name + "</td></tr>";
    				html += ("<tr><td>时间：</td><td>" + data.event_detail.event_start_time + "</td></tr>");
    				html += ("<tr><td>时长：</td><td>" + (data.event_detail.event_duration/60000) + "分钟</td></tr>");
    				html += ("<tr><td>地点：</td><td>" + "空" + "</td></tr>");
    				html += ("<tr><td>负责人：</td><td>" + data.event_detail.event_group.group_admin.user_name + "</td></tr>");
    				html += ("<tr><td>情况统计：</td><td>" + data.event_detail.group_member_attendance + "/" + data.event_detail.group_member_num + "</td></tr>");
    				html += ("<tr><td colspan='2'>描述：</td></tr>");
    				html += ("<tr><td colspan='2'><p style='overflow:hidden;display:block;word-break:break-all;'>" + data.event_detail.event_describe + "</p></td></tr>");
    				var table_html = ("<table id = 'event_detail_table' style='width:100%;border:2px solid #666666;' class='table table-striped'><tbody>" + html + "</tbody></table>");
    				
        			document.getElementById("search_event_describe").innerHTML = table_html;
            		document.getElementById("click_search_image").innerHTML = "<img style='width:180px;height:180px;'src='http://10.131.228.215/xFudan.php/Image/getEvent?eid=" + event_id + "'/>";
        			document.getElementById("search_larger_barcode").innerHTML = "<img style='width:300px;height:300px;margin-left:40px;' src='http://10.131.228.215/xFudan.php/Image/getEvent?eid=" + event_id + "'/>";
        		}
        		else
        		{
        			window.alert("该活动不存在！");
        		}
        		
        	}
		});
	}
	
	
	window.onload = show_announcement;
	function show_announcement()
	{
		if(document.URL.split("?").length > 1)
		{
			if((document.URL.split("?")[1]).split("=").length > 1)
			{
				$("#inner_login_box").removeClass("login-box2");
				$("#inner_login_box").addClass("login-box1");
				$("#login_table").removeClass("login-box-table1");
				$("#login_table").addClass("login-box-table2");
				$("#inner_login_container").removeClass("full-height1");
				$("#inner_login_container").addClass("full-height2");
				$("#inner_navbar_construct").removeClass("navbar_construct1");
				$("#inner_navbar_construct").addClass("navbar_construct2");
				$("#outer_navbar_construct").removeClass("login-container1");
				$("#outer_navbar_construct").addClass("login-container2");
				$("#limit-width").removeClass("limit-width1");
				$("#limit-width").addClass("limit-width2");
				
				$("body").removeClass("body-image2");
				$("body").addClass("body-image1");
				search_event((document.URL.split("?")[1]).split("=")[1]);
			}
		}
		else
		{
			$("#show_event_announcement").css("display","none");
		}
	}
	
	
function regist(){
		
		var var_login_id = document.getElementById("login_id_register").value;
		var var_password = document.getElementById("password_register").value;
		var var_name = document.getElementById("name_register").value;
		var var_password_check = document.getElementById("password_check_register").value;
		var var_studentID = document.getElementById("studentID_register").value;
		var var_gender =$('input:radio[name="gender"]:checked').val();
		var var_nickname = document.getElementById("nickname_register").value;
		if(var_login_id != "" && var_password != "" && (var_password_check == var_password))
		{
		$.ajax({
			url: "http://10.131.228.215/xFudan.php/User/register",
    		dataType: "JSON",
    		type: "POST",
    		data:{login_id:var_login_id,password:var_password,name:var_name,student_id:var_studentID,gender:var_gender,nickname:var_nickname},
    		success: function a1(data)
    		{
    			if(data.success == true)
    			{
    				window.location.href = "main.html";
    			}
    			else
    			{
    				alert("申请表存在不正确项或用户名被注册！");
    			}
    		}
		});
		}
		else if(var_password_check != var_password)
		{
			alert("要求两次输入相同密码！");
		}
		else
		{
			alert('用户名或密码不能为空！');
		}
	}
	