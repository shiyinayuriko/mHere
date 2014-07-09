<?php
class IndexAction extends Action {
    public function index(){
//     	echo md5(time());
    	$account = M('user_account');
    	print_r($account->select());
    }
}