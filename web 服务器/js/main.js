	
var base_url = "http://10.131.228.215/";

function showEventDetail(event_id)
	{
		
		$("#modified_event_button").removeClass("m2");
		$.ajax({
        	url: base_url + "xFudan.php/Event/getEventDetail",
        	dataType: "JSON",
        	type:'GET',
        	data:{eid:event_id},
        	success: function (data)
        	{	
        		
        		var html = "<tr><td>名称：</td><td ><span style='width:100px;' class='change-line pull-left' id='event_name_saver'>" +  data.event_detail.event_name + "</span><input type='hidden' id='event_id_saver' value='" + event_id + "'>" + "</td></tr>";
				html += ("<tr><td>时间：</td><td id='event_start_time_saver'>" + data.event_detail.event_start_time + "</td></tr>");
				html += ("<tr><td>时长：</td><td id='event_duration_saver'>" + ((data.event_detail.event_duration) + "").split(".")[0] + "分钟</td></tr>");
				html += ("<tr><td>地点：</td><td id='event_position_saver'>" + "空" + "</td></tr>");
				html += ("<tr><td>负责人：</td><td id='event_admin_saver'>" + data.event_detail.event_group.group_admin.user_name + "</td></tr>");
				html += ("<tr><td>统计：</td><td id='event_attendence_saver'>" + data.event_detail.group_member_attendance + "/" + data.event_detail.group_member_num + "</td></tr>");
				html += ("<tr><td>使用：</td><td id='event_usage_saver'>" + (data.event_detail.event_ispublic=="public"?"公共":"私人") + "</td></tr>");
				html += ("<tr><td colspan='2'>描述：</td></tr>");
				html += ("<tr><td colspan='2'><p class='change-line' id='event_describe_saver'>" + data.event_detail.event_describe + "</p></td></tr>");
				var table_html = ("<table id = 'event_detail_table'  class='table table-striped'><tbody>" + html + "</tbody></table>");
				
				document.getElementById("show_event_detail").innerHTML = table_html;

				document.getElementById("show-attendence-ul").innerHTML = "<thead><tr><td>姓名</td><td>学号</td><td>时间</td></tr></thead><tbody>";
				for(var i = 0;data.event_attendance[i] != null;i++){
				document.getElementById("show-attendence-ul").innerHTML += ("<tr><td>" + data.event_attendance[i].user_name + "</td><td>" + data.event_attendance[i].user_stutId + "</td><td>" + data.event_attendance[i].attendance_time + "</td></tr>");
				
				}
				document.getElementById("show-attendence-ul").innerHTML += "</tbody>";
					
        	}
		});
		document.getElementById("modified_event_button").innerHTML = "<a href='#'  onClick='modified_event_info()'><img src='picture/modified.png' style='margin-top:10px;'/></a>";
	}
	
	function getProfile()
    {
    	$.ajax({
        	url: base_url + "xFudan.php/User/getMyProfile",
        	type:"GET",
        	dataType: "JSON",
        	success: function (data)
        	{		
        		var html = ("<tr><td><img  class='img-rounded' style='width:100px;height:100px;border:2px solid #999999' src='http://10.131.228.215/uploadPic/tmp/" + data.user_icon + "'/></td></tr>");
        		html += ("<tr><td>姓名：</td><td id='profile_name_saver'>" + data.user_name + "</td></tr>");
        		html += ("<tr><td>学号：</td><td id='profile_stuID_saver'>" + data.user_stutId + "</td></tr>");
        		html += ("<tr><td>性别：</td><td id='profile_sex_saver'>" + data.user_gender + "</td></tr>");
        		html += ("<tr><td>昵称：</td><td id='profile_nickname_saver'>" + (data.user_nickname == 'undefined'?null:data.user_nickname) + "</td><input id='profile_icon_saver' type='hidden' value='" + data.user_icon + "'></tr>");
        		html += ("<tr><td colspan='2'><button class='btn btn-primary' onClick='modified_profile();pppop(&quot;modified_profile_Popup&quot;,&quot;modified_profile_Contact&quot;)'>修改信息</button></td></tr>");
        		document.getElementById("myProfile").innerHTML = html;
        		
        	}
		});
    }
	
	
	function modified_profile()
	{
	
     var profile_name = document.getElementById("profile_name_saver").innerHTML;
     var profile_stuID = document.getElementById("profile_stuID_saver").innerHTML;
     var profile_sex = document.getElementById("profile_sex_saver").innerHTML;
     var profile_nickname = document.getElementById("profile_nickname_saver").innerHTML;
     var profile_icon = document.getElementById("profile_icon_saver").value;
     document.getElementById("get_profile_name").value = profile_name;
     document.getElementById("get_profile_stuID").value = profile_stuID;
     document.getElementById("get_profile_sex").value = profile_sex;
     document.getElementById("get_profile_nickname").value = profile_nickname;
     document.getElementById("get_profile_upload_path").value = profile_icon;
     document.getElementById("buttonID_saver").value = "get_profile_upload_path";
	}
	
	function post_profile_edit()
	{
		var profile_name = document.getElementById("get_profile_name").value;
	    var profile_stuID = document.getElementById("get_profile_stuID").value;
	    var profile_sex = document.getElementById("get_profile_sex").value;
	    var profile_nickname = document.getElementById("get_profile_nickname").value;
	    var profile_icon = document.getElementById("get_profile_upload_path").value;
	    
		$.ajax({
        	url: base_url + "xFudan.php/User/editUserInfo",
        	dataType: "JSON",
        	type:"POST",
        	data:{
        		name:profile_name,
        		student_id:profile_stuID,
        		gender:profile_sex,
        		nickname:profile_nickname,
        		icon:profile_icon
        		},
        	success: function (data)
        	{	
        		
        	}
		});
		alert("修改成功！");
		dis('modified_profile_Popup','modified_profile_Contact');
	}
	
	
	
	function showGroupList()
	{
		
		$.ajax({
        	url: base_url + "xFudan.php/Group/getGroupList",
        	dataType: "JSON",
        	type:"GET",
        	success: function (data)
        	{	
        		var i = 0;
        		document.getElementById("display-list-group").innerHTML = "";
        		if(data.groups[i] != undefined){
        		for(i = 0;data.groups[i].group_name != null;i++){
        		document.getElementById("display-list-group").innerHTML += ("<li style='width:161px;margin-left:-40px;padding-top:3px;padding-bottom:2px;' id='group" + data.groups[i].group_id + "'  onmouseup='show_selected(&quot;" + 'group' + data.groups[i].group_id + "&quot;);getGroupInfo_EventList(&quot;" + data.groups[i].group_id + "&quot;)'><img id='list_group_image_" + data.groups[i].group_id + "' src='http://10.131.228.215/uploadPic/pic-30/" + data.groups[i].group_icon + "' class='pull-left' style='width:20px;height:20px;margin-left:15px;'/><span class='change-line group-title-hidden'>" + data.groups[i].group_name + "</span></li>");
				}}
        	}
		});
		
	}
	window.onload = showGroupList;
	
	function getGroupInfo_EventList(group_id)
	{
		$("#create_event_button").removeClass("m2");
		$("#modified_group_button").removeClass("m2");
		$.ajax({
        	url: base_url + "xFudan.php/Group/getGroupDetail",
        	dataType: "JSON",
        	type:'GET',
        	data:{gid:group_id},
        	success: function (data)
        	{
        				document.getElementById("show_barcode").innerHTML = "";
						document.getElementById("show-attendence-ul").innerHTML = "";
						document.getElementById("show_event_detail").innerHTML = "";
						var html = "<tr><td style='width:90px'>分组名称：</td><td  ><span style='width:95px;' class='change-line pull-left' id='group_name_saver'>" + data.group_detail.group_name + "</span><a id='alink' class='pull-right' href='#'  onClick='pppop(&quot;image_group_Popup&quot;,&quot;image_group_Contact&quot;)' title='点击查看分组二维码' data-content='内容'><img class='show_group_barcode' src='picture/barcode.png'/ ></a></td></tr>";
						document.getElementById("group-select").innerHTML = "<input type='text' class='form-control' style='width:290px;' value='" + data.group_detail.group_name + "' disabled='disabled'/><input id='hidden_group_id' type='hidden' value='" + data.group_detail.group_id + "'/>";
        				html += ("<tr><td>最大人数：</td><td id='group_max_member_saver'>" + data.group_detail.group_member_num + "/" + data.group_detail.group_max_member + "</td></tr>");
						html += ("<tr><td colspan='2'>分组描述：</td></tr><tr><td colspan='2' style='text-indent:2em;'><p class='change-line' id='group_describe_saver'>" + data.group_detail.group_describe + "</p></td><input id='group_icon_path_saver' type='hidden' value='" + data.group_detail.group_icon + "'/></tr>");
						var to = ("<table id='group_info' style='width:100%;border:1px solid #eeeeee'  class='table table-striped'><tbody>" + html + "</body></table>");
						document.getElementById("event-list-display").innerHTML = to;
						document.getElementById("event-list-display").innerHTML += ("<h4>活动列表：</h4><ul id='display-list-event' style=''>");
						document.getElementById("image_larger_barcode").innerHTML = "<img src='http://10.131.228.215/xFudan.php/Image/getGroup?gid=" + group_id + "' style='width:300px;height:300px;margin-left:35px;'>";
						for(var i = 0;data.group_event[i] != null;i++)
						{	
							document.getElementById("display-list-event").innerHTML += ("<li class='event-title-hidden'style='margin-left:-40px;padding-top:3px;padding-bottom:2px;' id='event" + (data.group_event[i].event_id) + "'  onClick='show_selected(&quot;" + 'event' + (data.group_event[i].event_id) + "&quot;);show_event_image(&quot;" + (data.group_event[i].event_id) + "&quot;,600);showEventDetail(&quot;" + (data.group_event[i].event_id) + "&quot;);'><a href='#'>" + (data.group_event[i].event_name) + "</a></li>");
						}
						document.getElementById("event-list-display").innerHTML += "</ul>";
						
						if(data.group_event[0] != undefined)
						{
							showEventDetail(data.group_event[0].event_id);
							show_event_image(data.group_event[0].event_id, 600);
							show_selected("event" + data.group_event[0].event_id);
						}
						
						
        	}
		});
		document.getElementById("modified_group_button").innerHTML = "<a href='#' onClick='modified_group_info();'><img src='picture/modified.png' style='margin-top:10px;'/></a>";
		
		
	}
	
	
	function modified_group_info()
	{
		
		var group_id = document.getElementById("hidden_group_id").value;
		var group_name = document.getElementById("group_name_saver").innerHTML;
		var group_max_member = document.getElementById("group_max_member_saver").innerHTML.split("/")[1];
		var group_describe = document.getElementById("group_describe_saver").innerHTML;
		
		var group_icon = document.getElementById("group_icon_path_saver").value;
		
		document.getElementById("buttonID_saver").value = "group_modified_on_save";
		var html = "<tr><td style='width:90px'>分组名称：</td><td><input id='edit_group_name_saver' type='text' class='form-control' value='" + group_name + "'/></td></tr>";
		html += ("<tr><td>最大人数：</td><td><input id='edit_group_max_member_saver' type='number' class='form-control'  value='" + group_max_member + "'/></td></tr>");
		html += ("<tr><td colspan='2'>分组描述：</td></tr><tr><td colspan='2'><textarea id='edit_group_describe_saver' class='form-control' col='2' maxlength=100   ></textarea></td></tr>");
		html += ("<tr><td><button id='group_modified_on_save' class='btn btn-success'  data-toggle='modal' data-target='#myModal'  onClick='reload_iframe(&quot;myModal_iframe&quot;)'>上传图片</button></td><td><button class='btn btn-primary pull-right'  style='margin-right:10px;' onClick='post_group_edit()'>保存</button></td></tr>");
		document.getElementById("group_info").innerHTML = html;
		document.getElementById("upload_image_path").value = group_icon;
		document.getElementById("modified_group_button").innerHTML = "<button class='btn btn-default' onClick='getGroupInfo_EventList(&quot;" + group_id + "&quot;)' style='margin-top:10px;'>取消</button>";
		$("#edit_group_describe_saver").val(group_describe);
	}
	
	function post_group_edit()
	{
		var group_id = document.getElementById("hidden_group_id").value;
		var edit_group_name_saver = document.getElementById("edit_group_name_saver").value;
		var edit_group_max_member_saver = document.getElementById("edit_group_max_member_saver").value;
		var edit_group_describe_saver = document.getElementById("edit_group_describe_saver").value;
		var edit_upload_image_path = document.getElementById("upload_image_path").value;
		
		$.ajax({
        	url: base_url + "xFudan.php/Group/editGroup",
        	dataType: "JSON",
        	type:'POST',
        	data:{
        		gid:group_id,
        		name:edit_group_name_saver,
        		max_member:edit_group_max_member_saver,
        		describe:edit_group_describe_saver,
        		icon:edit_upload_image_path
        	},
        	success: function (data)
        	{
        		$.ajax({
        			url: base_url + "xFudan.php/Group/getGroupDetail",
                	dataType: "JSON",
                	type:'GET',
                	data:{gid:group_id},
                	success: function (datam)
                	{
                		
                		document.getElementById("list_group_image_" + group_id).src=base_url + "uploadPic/pic-100/" + datam.group_detail.group_icon;
                		$("#group" + group_id + " span").html(datam.group_detail.group_name);
                		getGroupInfo_EventList(group_id);
                	}
        		});
        	}
		});
		
	}
	
	function modified_event_info()
	{
		var event_id = document.getElementById("event_id_saver").value;
		var event_name = document.getElementById("event_name_saver").innerHTML;
		var event_start_time = document.getElementById("event_start_time_saver").innerHTML;
		
		
		var html_inner = "<input id = 'edit_event_start_time_saver' class='form-control' type='text' value='" + event_start_time + "' style='width:110px;'/>" +
		"<span class='input-group-addon'>" +
		"<span class='glyphicon glyphicon-th'></span></span>";
		var html_outer = "<div class='form-group' style='height:20px;'>" +
		"<div class='input-group date form_datetime col-md-5' data-date='2013-09-16T05:25:07Z' data-data-format='yyyy-mm-dd hh:mm:ss' data-link-field='dtp_input3'>" + html_inner + 
		"</div><input type='hidden' id='dtp_input3' value='' /><br/></div><input id='event_id_saver' type='hidden' value='" + event_id + "'/>";
		
		var event_duration = document.getElementById("event_duration_saver").innerHTML.split("分钟")[0];
		var event_position = document.getElementById("event_position_saver").innerHTML;
		var event_describe = document.getElementById("event_describe_saver").innerHTML;
		var event_admin = document.getElementById("event_admin_saver").innerHTML;
		var event_attendence = document.getElementById("event_attendence_saver").innerHTML;
		var event_isPublic = document.getElementById("event_usage_saver").innerHTML;
		var html = "<tr><td width='70'>名称：</td><td>" + "<input id='edit_event_name_saver' type='text' class='form-control' value='" + event_name + "'/>" + "</td></tr>";
		html += ("<tr><td>时间：</td><td>" + html_outer + "</td></tr>");
		html += ("<tr><td>时长：</td><td>" + "<input id='edit_event_duration_saver' type='number' class='form-control' value='" + event_duration + "'/>" + "</td></tr>");
		html += ("<tr><td>地点：</td><td>" + "<input id='edit_event_position_saver' type='text' class='form-control' value='" + event_position + "'/>" + "</td></tr>");
		html += ("<tr><td>使用：</td><td>" + "<select id='edit_event_ispublic_saver' class='form-control'><option value='public'>公共</option><option value='private'>私人</option></select>" + "</td></tr>");
		html += ("<tr><td>负责人：</td><td id='event_admin_saver'>" + event_admin + "</td></tr>");
		html += ("<tr><td>统计：</td><td id='event_attendence_saver'>" + event_attendence + "</td></tr>");
		html += ("<tr><td colspan='2'>描述：</td></tr>");
		html += ("<tr><td colspan='2'><textarea col='2' id='edit_event_describe_saver' class='form-control'  maxlength=100></textarea></td></tr>");
		html += ("<tr><td colspan='2' ><button class='btn btn-primary pull-right' onClick='post_event_edit();'>保存</button></td></tr>");
		document.getElementById("event_detail_table").innerHTML = html;
		document.getElementById("modified_event_button").innerHTML = "<button class='btn btn-default' onClick='showEventDetail(&quot;" + event_id + "&quot;)' style='margin-top:10px;'>取消</button>";
		$("#edit_event_ispublic_saver option[value='" + (event_isPublic=="公共"?"public":"private") + "']").selected();
		$('.form_datetime').datetimepicker({language:  'zh-CN',weekStart: 1,todayBtn:  1,autoclose: 1,todayHighlight: 1,startView: 2,forceParse: 0,showMeridian: 1});
		$("#edit_event_describe_saver").val(event_describe);
	}
	
	function post_event_edit()
	{
		
		var event_id = document.getElementById("event_id_saver").value;
		var edit_event_name_saver = document.getElementById("edit_event_name_saver").value;
		var edit_event_start_time_saver = document.getElementById("edit_event_start_time_saver").value;
		var edit_event_duration_saver = document.getElementById("edit_event_duration_saver").value;
		var edit_event_position_saver = document.getElementById("edit_event_position_saver").value;
		var edit_event_ispublic_saver = document.getElementById("edit_event_ispublic_saver").value;
		var edit_event_describe_saver = $("#edit_event_describe_saver").val();
		
		
		
		$.ajax({
        	url: base_url + "xFudan.php/Event/editEvent",
        	dataType: "JSON",
        	type:'POST',
        	data:{
        		eid:event_id,
        		name:edit_event_name_saver,
        		start_time:edit_event_start_time_saver,
        		duration:edit_event_duration_saver,
        		ispublic:edit_event_ispublic_saver,
        		describe:edit_event_describe_saver
        	},
        	success: function (data)
        	{
        		if(data.success == true)
        		{
        			alert("修改成功");
            		showEventDetail(event_id);
        		}
        		
        		
        	}
		});
	}
	
	
	function modified_on_save()
	{
		//alert(document.getElementById("buttonID_saver").value);
	}
	
	function getGroupInfo_EventList1(group_id)
	{
		$("#create_event_button").removeClass("m2");
		$("#modified_group_button").removeClass("m2");
		$.ajax({
        	url: base_url + "xFudan.php/Group/getGroupDetail",
        	dataType: "JSON",
        	type:'GET',
        	data:{gid:group_id},
        	success: function (data)
        	{
        				//document.getElementById("show_barcode").innerHTML = "";
        		document.getElementById("show-attendence-ul").innerHTML = "";
				document.getElementById("show_event_detail").innerHTML = "";
				var html = "<tr><td style='width:90px'>分组名称：</td><td ><span style='width:100px;' class='change-line pull-left' id='group_name_saver'>" + data.group_detail.group_name + "</span><a id='alink' class='pull-right' href='#'  onClick='pppop(&quot;image_group_Popup&quot;,&quot;image_group_Contact&quot;)' title='点击查看分组二维码' data-content='内容'><img class='show_group_barcode' src='picture/barcode.png'/ ></a></td></tr>";
				document.getElementById("group-select").innerHTML = "<input type='text' class='form-control' style='width:290px;' value='" + data.group_detail.group_name + "' disabled='disabled'/><input id='hidden_group_id' type='hidden' value='" + data.group_detail.group_id + "'/>";
				html += ("<tr><td>最大人数：</td><td id='group_max_member_saver'>" + data.group_detail.group_member_num + "/" + data.group_detail.group_max_member + "</td></tr>");
				html += ("<tr><td colspan='2'>分组描述：</td></tr><tr><td colspan='2' style='text-indent:2em;'><p class='change-line' id='group_describe_saver'>" + data.group_detail.group_describe + "</p></td><input id='group_icon_path_saver' type='hidden' value='" + data.group_detail.group_icon + "'/></tr>");
				var to = ("<table id='group_info' style='width:100%;border:1px solid #eeeeee'  class='table table-striped'><tbody>" + html + "</body></table>");
				document.getElementById("event-list-display").innerHTML = to;
				document.getElementById("event-list-display").innerHTML += ("<h4>活动列表：</h4><ul id='display-list-event' style=''>");
				document.getElementById("image_larger_barcode").innerHTML = "<img src='http://10.131.228.215/xFudan.php/Image/getGroup?gid=" + group_id + "' style='width:300px;height:300px;margin-left:35px;'>";
				for(var i = 0;data.group_event[i] != null;i++)
				{	
					document.getElementById("display-list-event").innerHTML += ("<li style='margin-left:-40px;padding-top:3px;padding-bottom:2px;' id='event" + (data.group_event[i].event_id) + "'  onClick='show_selected(&quot;" + 'event' + (data.group_event[i].event_id) + "&quot;);show_event_image(&quot;" + (data.group_event[i].event_id) + "&quot;,600);showEventDetail(&quot;" + (data.group_event[i].event_id) + "&quot;);'><a href='#'>" + (data.group_event[i].event_name) + "</a></li>");
				}
				document.getElementById("event-list-display").innerHTML += "</ul>";
				
				if(data.group_event[0] != undefined)
				{
					showEventDetail(data.group_event[0].event_id);
					show_event_image(data.group_event[0].event_id, 600);
					show_selected("event" + data.group_event[0].event_id);
				}
				
						
        	}
		});
		
		document.getElementById("modified_group_button").innerHTML = "<a href='#' onClick='modified_group_info();'><img src='picture/modified.png' style='margin-top:10px;'/></a>";
		
		
	}
	
	function show_selected(showID)
	{
		$("#" + showID).addClass('li-active');
		
		if($("#" + showID).siblings().hasClass('li-active'))
		{
			$("#" + showID).siblings().removeClass('li-active');
		}
	}
	
	
	function reload_iframe(iframe_id)
	{
		document.getElementById(iframe_id).setAttribute("src", document.getElementById(iframe_id).src);
	}
	
	function show_event_image(event_id, event_valid_time)
	{
		if(event_valid_time != null)
		{
			var barcode = "<h5>二维码：</h5><a onClick='pppop(&quot;backgroundPopup3&quot;,&quot;popupContact3&quot;)'><img style='height:185px;width:185px;margin-left:20px;border:1px solid #eeeeee;cursor:pointer;' src='http://10.131.228.215/xFudan.php/Image/getEvent?eid=" + event_id + "&time=" + event_valid_time + "'/></a>";
    		
    		document.getElementById("show_barcode").innerHTML = barcode;
    		document.getElementById("larger_barcode").innerHTML = "<img src='http://10.131.228.215/xFudan.php/Image/getEvent?eid=" + event_id + "&time=" + event_valid_time + "' style='height:300px;width:300px;margin-left:35px;'/>";
    		
		}
		else
		{
			var barcode = "<h5>二维码：</h5><a onClick='pppop(&quot;backgroundPopup3&quot;,&quot;popupContact3&quot;)'><img style='height:185px;width:185px;margin:0 auto;border:1px solid #eeeeee;cursor:pointer;' src='http://10.131.228.215/xFudan.php/Image/getEvent?eid=" + event_id + "'/></a>";
    		
    		document.getElementById("show_barcode").innerHTML = barcode;
    		document.getElementById("larger_barcode").innerHTML = "<img src='http://10.131.228.215/xFudan.php/Image/getEvent?eid=" + event_id + "' style='height:300px;width:300px;margin-left:35px;'/>";
		}
        		
	}
	
	function c(){
    	
		$('.dropdown-menu').toggleClass("m1");		
};
function c1(id){

		$('div ul li#'+id).toggleClass("active");		
};






function create_group()
{
	var var_group_name = document.getElementById("group_name").value;
	var var_group_max_member = document.getElementById("group_max_member").value;
	if(var_group_max_member < 0)
	{
		alert("人数不能小于零！");
	}
	else
	{
	var var_group_describe = document.getElementById("group_describe").value;
	if(var_group_name != "")
	{
	$.ajax({
		url: base_url + "xFudan.php/Group/createGroup",
		dataType: "JSON",
		type: "POST",
		data:{
			name:var_group_name,
			max_member:var_group_max_member,
			describe:var_group_describe
			},
		success: function a1(data)
		{
			window.location.href = "main.html";
		}
	});
	}
	else
	{
		alert('分组名称不能为空！');
	}
	}
}



function create_event()
{
	
	if(document.getElementById("hidden_group_id") == null)
	{
		alert("请选择组别！");
	}
	else if(document.getElementById("create_event_start_time").value == "" || document.getElementById("create_event_end_time").value == "")
	{
		alert("请选择开始或结束日期！");
	}
	else{
	var var_group_id = document.getElementById("hidden_group_id").value;
	var var_create_event_name = document.getElementById("create_event_name").value;
	var var_create_event_start_time = document.getElementById("create_event_start_time").value;
	var var_create_event_end_time = document.getElementById("create_event_end_time").value;
	var start_time_millis = Date.parse(var_create_event_start_time);
	var end_time_millis = Date.parse(var_create_event_end_time);
	var var_create_event_duration = (end_time_millis - start_time_millis) / 60000;
	var var_create_event_usage = $('input:radio[name="event_usage"]:checked').val();
	var var_create_event_describe = document.getElementById("create_event_describe").value;
	
	var var_create_valid_time = document.getElementById("create_valid_time").value;
	//highlight
	var return_eventID = 0;
	
	if(var_create_event_name != "" && var_create_event_duration > 0)
	{
	$.ajax({
		url: base_url + "xFudan.php/Event/creatEvent",
		dataType: "JSON",
		type: "POST",
		data:{	gid:var_group_id,
				name:var_create_event_name,
				describe:var_create_event_describe,
				start_time:var_create_event_start_time,
				duration:var_create_event_duration,
				ispublic:var_create_event_usage
				},
		success: function a1(data)
		{
			getGroupInfo_EventList1(var_group_id);
			show_event_image(data.event_id, var_create_valid_time);
			showEventDetail(data.event_id);
		}
	});
	}
	else if(var_create_event_duration <= 0)
	{
		alert("时间反了哦~");
	}
	else
	{
		alert('活动名称不能为空！');
	}
	}
}





