<?php
class ImageAction extends Action{
	/**
	 * 二维码
	 * @sample http://phpqrcode.sourceforge.net/examples/index.php
	 * @param string $text
	 */
// 	public function genQrcode($text = 'null',$size) {
// 		Vendor('phpqrcode.qrlib');
// 		QRcode::png($text, false, QR_ECLEVEL_H,$size);
// 	}
	
	public function getGroup($gid,$size = 80){
		Vendor('phpqrcode.qrlib');
		QRcode::png('group'.$gid, false, QR_ECLEVEL_H,$size);
	}
	
	public function getEvent($eid,$time = 600,$size = 80){
		$event = getEventDetail($eid);
		if(!ispublicEvent($eid) && isAdmin($event['event_group'],session('user_id'))){
			$attendCode = md5($eid.time());
			S($attendCode,$eid,$time);
			Vendor('phpqrcode.qrlib');
			QRcode::png('event_private'.$attendCode, false, QR_ECLEVEL_H,$size);
		}else{
			Vendor('phpqrcode.qrlib');
			QRcode::png('event_public'.$eid, false, QR_ECLEVEL_H,$size);
		}
	}
}