<?php
class EventAction extends Action {
    public function getEventDetail($eid){
    	$event = getEventDetail($eid);
    	if($event){
    		$attendance = getAttendance($eid);
    		
    		$members = getGroupMember($event['event_group']);
    		$event['event_group'] = getGroupInfo($event['event_group']);
    		
    		$event['group_member_num'] = count($members);
    		$event['group_member_attendance'] = count($attendance);
    		
    		$ret['event_detail'] = $event;
    		$ret['event_attendance'] = $attendance;
    		$ret['success'] = true ;
    	}else{
    		$ret['success'] = false;
    		$ret['reason'] = 'No such Event';
    	}		
    	$this -> ajaxReturn($ret,'JSON');
    }

	public function creatEvent($gid){
		if(!isAdmin($gid,session('user_id'))){
			$ret['success'] = false;
			$ret['reason'] = 'No Privileges';
		}else{
			$data['group_admin']=session('user_id');
			$data['event_group']=$gid;
			$data['event_name']=I('post.name');
			$data['event_describe']=I('post.describe');
			$data['event_start_time']=I('post.start_time');
			//TODO check starttime
			$data['event_duration']=I('post.duration');
			$data['event_ispublic']=I('post.ispublic',null);
			if($data['event_ispublic']==null || ($data['event_ispublic'] !='public'&& $data['event_ispublic'] != 'private'))
				$data['event_ispublic'] = 'public';
			
			$Event = M('event');
			$eid = $Event -> add($data);
			if($eid){
				$ret['success'] = true;
				$ret['event_id'] = $eid;
			}else{
				$ret['success'] = false;
				$ret['reason'] = 'creat error';
			}
		}
		
		$this -> ajaxReturn($ret,'JSON');
	}	
	
	public function editEvent($eid){
		$data = getEventDetail($eid);
		if(!$data){
			$ret['success'] = false;
			$ret['reason'] = 'No such event';
		}elseif(!isAdmin($data['event_group'],session('user_id'))){
			$ret['success'] = false;
			$ret['reason'] = 'No Privileges';
		}else{
			$data['event_name']=I('post.name');
			$data['event_describe']=I('post.describe');
			$data['event_start_time']=I('post.start_time');
			//TODO check starttime
			$data['event_duration']=I('post.duration');
			$data['event_ispublic']=I('post.ispublic',null);
			if($data['event_ispublic']==null || ($data['event_ispublic'] !='public'&& $data['event_ispublic'] != 'private'))
				$data['event_ispublic'] = 'public';
			
			$Event = M('event');
			$num = $Event -> save($data);
			if($num !== false ){
				$ret['success'] = true;
				$ret['event_id'] = $eid;
			}else{
				$ret['success'] = false;
				$ret['reason'] = 'creat error';
			}
		}
		
		$this -> ajaxReturn($ret,'JSON');
	}
	
	public function attendEventPrivate($attendCode){
		$eid = S($attendCode);
		$event = getEventDetail($eid);
		if(!ispublicEvent($eid) && isMember(session('user_id'),$event['event_group'])){
			$data['event_id'] = $eid;
			$data['user_id'] = session('user_id');
			$Attendance = M('event_attendance');
			//TODO isstart
				
			if($Attendance -> add($data)){
				$ret['success'] = true;
				$ret['event_id'] = $eid;
			}else{
				$ret['success'] = false;
				$ret['reason'] = 'eid error or alread attend';
			}
		}else{
			$ret['success'] = false;
			$ret['reason'] = 'no member or public';
		}
		$this -> ajaxReturn($ret,'JSON');
	}
	
	public function attendEventPublic($eid){
		$event = getEventDetail($eid);
		if(ispublicEvent($eid) && isMember(session('user_id'),$event['event_group'])){
			$data['event_id'] = $eid;
			$data['user_id'] = session('user_id');
			$Attendance = M('event_attendance');
			//TODO isstart
			
			if($Attendance -> add($data)){
				$ret['success'] = true;
				$ret['event_id'] = $eid;
			}else{
				$ret['success'] = false;
				$ret['reason'] = 'eid error or alread attend';
			}	
		}else{
				$ret['success'] = false;
				$ret['reason'] = 'no member or not public';
		}
		
		$this -> ajaxReturn($ret,'JSON');
	}
}