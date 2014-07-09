<?php
class GroupAction extends Action {
    public function getGroupList(){
    	$Group = M('group');
		$groups = $Group -> where("group_admin ='".session("user_id")."'") 
						 -> select();
				
		$ret['success'] = true ;
		$ret['groups'] = $groups;
		$this -> ajaxReturn($ret,'JSON');
    }
    public function getAttendanceGroupList(){
    	$GroupMember = M('group_member');
    	$groupsA = $GroupMember -> where("user_id ='".session("user_id")."'")
    					-> select();
    	$groups = array();
    	foreach ($groupsA as $g){
    		$Group = M('group');
    		$group = $Group -> where("group_id ='".$g['group_id']."'")
    				-> find();
    		$members = getGroupMember($g['group_id']);
    		$group['group_member_num'] = count($members);
    		array_push($groups, $group);
    	}
    	$ret['success'] = true ;
    	$ret['groups'] = $groups;
    	$this -> ajaxReturn($ret,'JSON');
    }
    public function getGroupDetail($gid){
    	if(ispublicGroup($gid) && isMember(session("user_id"),$gid)){
    		$group = getGroupInfo($gid);
    		
    		$members = getGroupMember($gid);
    		$group['group_member_num'] = count($members);
    		
    		$event = getEventList($gid);

    		for ($i=0;$i<count($event);$i++){
    			$event[$i]['isAttendance'] = isAttendance($event[$i]['event_id'], session("user_id"));
    		}
    		
    		$ret['group_detail'] = $group;
    		$ret['group_members'] = $members;
    		$ret['group_event'] = $event;
    		$ret['success'] = true ;
    	}else{
			$ret['success'] = false;
			$ret['reason'] = 'No Privileges or No such Group';
		}
		$this -> ajaxReturn($ret,'JSON');
    }
	
    public function createGroup(){
    	$data['group_admin']=session('user_id');
    	$data['group_name']=I('post.name');
        $data['group_describe']=I('post.describe');
        $data['group_max_member']=I('post.max_member');
        $data['group_ispublic']='public';
//         $data['group_ispublic']=I('post.ispublic',null);
        if($data['group_ispublic']==null || ($data['group_ispublic'] !='public'&& $data['group_ispublic'] != 'private'))
        	$data['group_ispublic'] = 'public';
        
        $Group = M('group');
        $gid = $Group -> add($data);
        if($gid){
        	addMemberToGroup(session('user_id'),$gid);
        	$ret['success'] = true;
			$ret['group_id'] = $gid;
		}else{
			$ret['success'] = false;
			$ret['reason'] = 'Error groupInfo';
		}
		$this -> ajaxReturn($ret,'JSON');        
    }    
    
    public function editGroup($gid){
    	if(!isAdmin($gid,session('user_id'))){
    		$ret['success'] = false;
    		$ret['reason'] = 'No Privileges';
    	}else{
    		$data = getGroupInfo($gid);
    		$data['group_name']=I('post.name');
    		$data['group_describe']=I('post.describe');
    		$data['group_max_member']=I('post.max_member');
    		$data['group_ispublic']='public';
    		//         $data['group_ispublic']=I('post.ispublic',null);
    		if($data['group_ispublic']==null || ($data['group_ispublic'] !='public'&& $data['group_ispublic'] != 'private'))
    			$data['group_ispublic'] = 'public';
    		$data['group_icon']=I('post.icon',"default.png");
    		
    		$Group = M('group');
    		$num = $Group -> save($data);
			if($num !== false){
    			$ret['success'] = true;
    			$ret['group_id'] = $gid;
    		}else{
    			$ret['success'] = false;
    			$ret['reason'] = 'Error groupInfo';
    		}
    	}
		$this -> ajaxReturn($ret,'JSON');        
    }
    
    public function addGroup($gid){
    	if(!ispublicGroup($gid)) {
    		$ret['success'] = false;
    		$ret['reason'] = 'group not public';
    	}elseif(addMemberToGroup(session('user_id'),$gid)){
    		$ret['success'] = true;
    		$ret['group_id'] = $gid;
    	}else{
    		$ret['success'] = false;
    		$ret['reason'] = 'Error group id or already member';
    	}
    	$this -> ajaxReturn($ret,'JSON');
    }
}