<?php
// 本类由系统手动生成，仅供测试用途
class IconAction extends Action {

    public function index(){
    	$this->display();
    }
    
	//上传头像
	public function uploadImg(){

		import('ORG.Net.UploadFile');
		$upload = new UploadFile();						// 实例化上传类
		$upload->maxSize = 1*1024*1024;					//设置上传图片的大小
		$upload->allowExts = array('jpg','png','gif');	//设置上传图片的后缀
		$upload->uploadReplace = true;					//同名则替换
		$upload->saveRule = 'uniqid';					//设置上传头像命名规则(临时图片),修改了UploadFile上传类
		//完整的头像路径

		$upload->savePath = tmp_upload.'/';
		if(!$upload->upload()) {						// 上传错误提示错误信息
			$this->ajaxReturn('',$upload->getErrorMsg(),0,'json');
		}else{											// 上传成功 获取上传文件信息
			$info =  $upload->getUploadFileInfo();
			$temp_size = getimagesize(tmp_upload.'/'.$info[0]['savename']);
			if($temp_size[0] < 100 || $temp_size[1] < 100){//判断宽和高是否符合头像要求
				$this->ajaxReturn(0,'图片宽或高不得小于100px！',0,'json');
			}
			$this->ajaxReturn(__ROOT__.''.substr(tmp_upload,1).'/'.$info[0]['savename'],$info,1,'json');
		}
	}
	//裁剪并保存用户头像
	public function cropImg(){
		//图片裁剪数据
		$params = $this->_post();						//裁剪参数
		if(!isset($params) && empty($params)){
			return;
		}

		//要保存的图片
		$tmp_path = tmp_upload.'/'.$params['save_name'];
// 		echo tmp_upload;
		import('ORG.Util.Image.ThinkImage');
		$Think_img = new ThinkImage(THINKIMAGE_GD); 
		//裁剪原图
		$Think_img->open($tmp_path)->crop($params['w'],$params['h'],$params['x'],$params['y'])->save($tmp_path);
		//生成缩略图
		$Think_img->open($tmp_path)->thumb(100,100, 1)->save(path_upload.'-100/'.$params['save_name']);
		$Think_img->open($tmp_path)->thumb(60,60, 1)->save(path_upload.'-60/'.$params['save_name']);
		$Think_img->open($tmp_path)->thumb(30,30, 1)->save(path_upload.'-30/'.$params['save_name']);
		$data['picName'] = $params['save_name'];
		$this->ajaxReturn($data);
	}
}