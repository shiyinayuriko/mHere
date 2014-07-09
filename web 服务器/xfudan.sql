-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2014 年 02 月 05 日 11:44
-- 服务器版本: 5.5.24-log
-- PHP 版本: 5.3.13

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `xfudan`
--

-- --------------------------------------------------------

--
-- 表的结构 `xfudan_event`
--

CREATE TABLE IF NOT EXISTS `xfudan_event` (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `event_group` int(11) NOT NULL,
  `event_name` varchar(50) NOT NULL,
  `event_describe` text NOT NULL,
  `event_start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `event_duration` int(11) NOT NULL DEFAULT '0',
  `event_ispublic` enum('public','private') NOT NULL DEFAULT 'public',
  PRIMARY KEY (`event_id`),
  KEY `event_group` (`event_group`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xfudan_event_attendance`
--

CREATE TABLE IF NOT EXISTS `xfudan_event_attendance` (
  `event_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `attendance_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`event_id`,`user_id`),
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `event_id` (`event_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- --------------------------------------------------------

--
-- 表的结构 `xfudan_group`
--

CREATE TABLE IF NOT EXISTS `xfudan_group` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT,
  `group_admin` int(11) NOT NULL,
  `group_name` varchar(50) NOT NULL,
  `group_describe` text NOT NULL,
  `group_max_member` int(11) NOT NULL DEFAULT '0',
  `group_ispublic` enum('public','private') NOT NULL DEFAULT 'public',
  PRIMARY KEY (`group_id`),
  KEY `group_admin` (`group_admin`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- 表的结构 `xfudan_group_member`
--

CREATE TABLE IF NOT EXISTS `xfudan_group_member` (
  `user_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`group_id`),
  KEY `user_account` (`user_id`) USING BTREE,
  KEY `group_id` (`group_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- --------------------------------------------------------

--
-- 表的结构 `xfudan_user_account`
--

CREATE TABLE IF NOT EXISTS `xfudan_user_account` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_login_id` varchar(20) NOT NULL,
  `user_password` varchar(20) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_login_id` (`user_login_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `xfudan_user_extra_info`
--

CREATE TABLE IF NOT EXISTS `xfudan_user_extra_info` (
  `user_id` int(11) NOT NULL,
  `user_info_type` enum('user_nickname') NOT NULL,
  `user_info_value` text NOT NULL,
  KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- --------------------------------------------------------

--
-- 表的结构 `xfudan_user_info`
--

CREATE TABLE IF NOT EXISTS `xfudan_user_info` (
  `user_id` int(11) NOT NULL,
  `user_name` varchar(100) NOT NULL,
  `user_stutId` varchar(20) NOT NULL,
  `user_gender` enum('male','female') NOT NULL DEFAULT 'male',
  PRIMARY KEY (`user_id`),
  KEY `user_studentId` (`user_stutId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

--
-- 限制导出的表
--

--
-- 限制表 `xfudan_event`
--
ALTER TABLE `xfudan_event`
  ADD CONSTRAINT `xfudan_event_ibfk_1` FOREIGN KEY (`event_group`) REFERENCES `xfudan_group` (`group_id`);

--
-- 限制表 `xfudan_event_attendance`
--
ALTER TABLE `xfudan_event_attendance`
  ADD CONSTRAINT `xfudan_event_attendance_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `xfudan_user_account` (`user_id`),
  ADD CONSTRAINT `xfudan_event_attendance_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `xfudan_event` (`event_id`);

--
-- 限制表 `xfudan_group`
--
ALTER TABLE `xfudan_group`
  ADD CONSTRAINT `xfudan_group_ibfk_1` FOREIGN KEY (`group_admin`) REFERENCES `xfudan_user_account` (`user_id`);

--
-- 限制表 `xfudan_group_member`
--
ALTER TABLE `xfudan_group_member`
  ADD CONSTRAINT `xfudan_group_member_ibfk_2` FOREIGN KEY (`group_id`) REFERENCES `xfudan_group` (`group_id`),
  ADD CONSTRAINT `xfudan_group_member_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `xfudan_user_account` (`user_id`);

--
-- 限制表 `xfudan_user_extra_info`
--
ALTER TABLE `xfudan_user_extra_info`
  ADD CONSTRAINT `xfudan_user_extra_info_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `xfudan_user_account` (`user_id`);

--
-- 限制表 `xfudan_user_info`
--
ALTER TABLE `xfudan_user_info`
  ADD CONSTRAINT `xfudan_user_info_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `xfudan_user_account` (`user_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
