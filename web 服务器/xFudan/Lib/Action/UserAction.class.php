<?php
class UserAction extends Action{
	public function login(){
		$login_id = I('post.login_id');
		$password = I('post.password');
	
		$Account = M('user_account');
		$user = $Account -> where("user_login_id = '".$login_id."' AND user_password = '".$password."'")
		-> field('user_id')->find();
	
		if($user){
			session("user_id",$user["user_id"]);
			$ret['success'] = true;
		}else{
			$ret['success'] = false;
			$ret['reason'] = 'Error password';
		}
	
		$this -> ajaxReturn($ret,'JSON');
	}
	
	public function register(){
		$data['user_password'] = I('post.password',null);
		$data['user_login_id'] = I('post.login_id',null);
	
		$Account = M('user_account');
		$userId = $Account ->add($data);
		if($userId){
			$UserInfo = M('user_info');
				
			$user['user_id']=$userId;
			$user['user_name']=I('post.name');
			$user['user_stutId']=I('post.student_id');
			$user['user_gender']=I('post.gender',null);
			if($user['user_gender']==null || ($user['user_gender']!='male' && $user['user_gender']!='female'))
					$user['user_gender']='male';
			$UserInfo -> add($user);
			
			$UserExtra = M('user_extra_info');
			$extradata['user_id']=$userId;
			$extradata['user_info_type'] = 'user_nickname';
			$extradata['user_info_value'] = I('post.nickname',null);
			if($extradata['user_info_value']) $UserExtra -> add($extradata);
			
			$ret['success'] = true;
			$ret['user_id'] = $userId;
			session("user_id",$userId);
		}else{
			$ret['success'] = false;
			$ret['reason'] = 'Error login_id';
		}
		$this -> ajaxReturn($ret,'JSON');
	}

	public function editUserInfo(){
		if(session("user_id")){
			$user = getUserProfile(session("user_id"));
			$user['user_name']=I('post.name');
			$user['user_stutId']=I('post.student_id');
			$user['user_gender']=I('post.gender',null);
			$user['user_icon']=I('post.icon',"default.png");
				
			if($user['user_gender']==null || ($user['user_gender']!='male' && $user['user_gender']!='female'))
				$user['user_gender']='male';

			$UserInfo = M('user_info');
			$num = $UserInfo -> save($user);
			if($num === false ){
				$ret['success'] = false;
				$ret['reason'] = 'creat error';
			}
			
			$UserExtra = M('user_extra_info');
			$extradata = $UserExtra -> where("user_id = '".session("user_id")."' AND user_info_type = 'user_nickname'");
			$extradata['user_info_value'] = I('post.nickname',null);
			if($extradata['user_info_value']) $UserExtra -> save($extradata);
				
			$ret['success'] = true;
			$ret['user_id'] = session("user_id");
		}else{
			$ret['success'] = false;
			$ret['reason'] = 'Error user_id';
		}
		$this -> ajaxReturn($ret,'JSON');
	}
	
	public function getProfile($uid){
		if(inSameGroup($uid,session("user_id"))){
			$ret = getUserProfile($uid);
			$ret['success'] = true ;
		}else{
			$ret['success'] = false;
			$ret['reason'] = 'No Privileges';
		}
		$this -> ajaxReturn($ret,'JSON');
	}
	
	public function getMyProfile(){
		$this->getProfile(session("user_id"));
	}
}