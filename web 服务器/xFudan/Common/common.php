<?php
function inSameGroup($uid,$myuid){
	if($uid == $myuid) return true;
	$model=new Model();
    $ret = $model->table('xfudan_group_member u,xfudan_group_member m')
    	->where("u.user_id='".$uid."' AND m.user_id = '".$myuid."' AND u.group_id = m.group_id")
    	->find();
	return $ret?true:false;
}

function ispublicGroup($gid){
	$Group = M('group');
	$group = $Group -> where("group_id ='".$gid."'")-> find();
	return $group['group_ispublic']=='public';
}

function ispublicEvent($eid){
	$Event = M('event');
	$event = $Event -> where("event_id ='".$eid."'")-> find();
	return $event['event_ispublic']=='public';
}

function isAttendance($eid,$uid){
	$Attendance = M('event_attendance');
	$ret = $Attendance -> where("event_id ='".$eid."' AND user_id = '".$uid."'")-> find();
// 	echo $Attendance -> getLastSQL();
	if($ret) return true ;
		else return false;
}

function isMember($uid,$gid){
	$Group = M('group_member');
	$ret = $Group -> where("user_id='".$uid."' AND group_id = '".$gid."'")
			->find();
	return $ret?true:false;
}

function isAdmin($gid,$myuid){
	$Group = M('group');
	$group = $Group -> where("group_id ='".$gid."'")-> find();
	return $group['group_admin']== $myuid;
}

function getUserProfile($uid){
	$UserInfo = M('user_info');
	$ret = $UserInfo -> where("user_id = '".$uid."'")->find();
		
	$ExtraInfo = M('user_extra_info');
	$profile = $ExtraInfo -> where("user_id = '".$uid."'")
						  -> field('user_info_type,user_info_value')->select();
	foreach ($profile as $r){
		$ret[$r['user_info_type']]=$r['user_info_value'];
	}
	
	$GroupMembe = M('group_membe');
	
	$groups = $GroupMembe -> where("user_id = '".$uid."'") -> select();
	$ret['group_num'] = count($groups);
	
	$Attandance = M('event_attendance');
	$attandance = $Attandance -> where("user_id = '".$uid."'")->select();
	$ret['attandance_num'] = count($attandance);
	
	return $ret;
}

function getGroupInfo($gid){
	$Group = M('group');
	$group = $Group -> where("group_id ='".$gid."'")
					-> find();
	$group['group_admin'] = getUserProfile($group['group_admin']);
	return $group;
}

function getGroupMember($gid){
	$Member = M('group_member');
	$memberlist = $Member -> where("group_id = '".$gid."'") -> select();
	$members = array();
	foreach ($memberlist as $m){
		$userInfo = getUserProfile($m['user_id']);
		array_push($members,$userInfo);
	}
	return $members;
}

function getEventList($gid){
	$Event = M('event');
	$event= $Event -> where("event_group = '".$gid."'") -> select();
	//     		date('Y-m-d H:i:s',strtotime($event[0]['event_start_time'])+3600);
	return $event;
}

function getEventDetail($eid){
	$Event = M('event');
	$event= $Event -> where("event_id = '".$eid."'") -> find();
	//     		date('Y-m-d H:i:s',strtotime($event[0]['event_start_time'])+3600);
	return $event;
}

function getAttendance($eid){
	$Attendance = M('event_attendance');
	$attendancelist = $Attendance -> where("event_id = '".$eid."'") -> select();
	$attendance = array();
	foreach ($attendancelist as $a){
		$userInfo = getUserProfile($a['user_id']);
		$userInfo['attendance_time'] = $a['attendance_time'];
		array_push($attendance,$userInfo);
	}
	return $attendance;
}

function addMemberToGroup($uid,$gid){
	$Member = M('group_member');
	$data['user_id'] = $uid;
	$data['group_id'] = $gid;
	$gre = $Member -> add($data);
	return $gre;
}





