/*
SQLyog Ultimate v8.71 
MySQL - 5.6.32-log 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `tb_publisher_share` (
	`share_id` int (10),
	`share_title` varchar (96),
	`share_content` blob ,
	`share_image` blob ,
	`share_time` datetime ,
	`update_time` datetime ,
	`login_plat` int (11),
	`share_userId` int (11)
); 
insert into `tb_publisher_share` (`share_id`, `share_title`, `share_content`, `share_image`, `share_time`, `update_time`, `login_plat`, `share_userId`) values('1',NULL,'发布共享','123456','2017-10-19 14:45:05',NULL,'1','1');
insert into `tb_publisher_share` (`share_id`, `share_title`, `share_content`, `share_image`, `share_time`, `update_time`, `login_plat`, `share_userId`) values('2',NULL,'发布测试共享','123456','2017-10-19 14:46:41',NULL,'1','1');
insert into `tb_publisher_share` (`share_id`, `share_title`, `share_content`, `share_image`, `share_time`, `update_time`, `login_plat`, `share_userId`) values('4',NULL,'12313','https://www.huanyoutec.com:443/cofC/carousel/20171019152039eae28.png,https://www.huanyoutec.com:443/cofC/carousel/201710191520420de84.png,https://www.huanyoutec.com:443/cofC/carousel/20171019152045d643e.png,https://www.huanyoutec.com:443/cofC/carousel/20171019152048e5089.png,https://www.huanyoutec.com:443/cofC/carousel/20171019152050211b4.png','2017-10-19 15:20:53',NULL,'1','1');
insert into `tb_publisher_share` (`share_id`, `share_title`, `share_content`, `share_image`, `share_time`, `update_time`, `login_plat`, `share_userId`) values('5',NULL,'wfawrefawrfa','http://localhost:8081/cofC/goodsImage/201710191710398a057.jpg','2017-10-19 17:10:42',NULL,'1','1');
insert into `tb_publisher_share` (`share_id`, `share_title`, `share_content`, `share_image`, `share_time`, `update_time`, `login_plat`, `share_userId`) values('6',NULL,'测试后台添加共享','http://localhost:8081/cofC/goodsImage/201710191713348bb3f.jpg','2017-10-19 17:13:36',NULL,'1','1');
