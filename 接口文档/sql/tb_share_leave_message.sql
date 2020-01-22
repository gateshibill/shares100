/*
SQLyog Ultimate v8.71 
MySQL - 5.6.32-log 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `tb_share_leave_message` (
	`message_id` int (11),
	`message_desc` varchar (768),
	`user_id` int (11),
	`share_id` int (11),
	`message_time` datetime 
); 
