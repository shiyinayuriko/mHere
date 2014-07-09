<?php
// 调试模式, 上线时候关闭
define('APP_DEBUG', true);
// 应用名称
// define('APP_NAME', 'Lemon');
// 应用根路径
// define('APP_ROOT_PATH', realpath('.') . '/');
// 应用相对路径
define('APP_PATH', './'.'xFudan'.'/');
// define('APP_PATH', '../../xFudan/');
define('path_upload', './uploadPic/pic');
define('tmp_upload','./uploadPic/tmp');
require '/ThinkPHP/ThinkPHP.php';