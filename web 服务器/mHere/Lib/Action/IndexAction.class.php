<?php
// 本类由系统自动生成，仅供测试用途
class IndexAction extends Action {
    public function index(){
    	$ret = array();
    	$num = I('post.SSIDnum',0);
    	$MHere = M('wifidata');
    	for($i=0;$i<$num;$i++){
    		$ssid = I('post.SSID'.$i,"");
    		$ss = $MHere -> where("SSID = '".$ssid."'") -> select();
    		if(array_merge($ret, $ss)) $ret = array_merge($ret, $ss);
    	}
    	$this->ajaxReturn($ret);
    }
}