/*
Navicat MySQL Data Transfer

Source Server         : 飞看-正式
Source Server Version : 50536
Source Host           : 120.25.205.74:3306
Source Database       : db_cofc2

Target Server Type    : MYSQL
Target Server Version : 50536
File Encoding         : 65001

Date: 2017-10-24 12:37:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_user_order`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_order`;
CREATE TABLE `tb_user_order` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `object_id` int(11) DEFAULT NULL COMMENT '对象id，可能是商品、任务等，由order_type决定',
  `objects` text COMMENT 'object的json对象数组',
  `multi_id` varchar(300) DEFAULT NULL COMMENT '产品包存多个id',
  `order_type` int(11) DEFAULT NULL COMMENT '0商品 1任务',
  `user_id` int(11) DEFAULT NULL COMMENT '对应于用户表tb_user_common',
  `number` int(11) DEFAULT NULL,
  `total_fee` double DEFAULT NULL COMMENT '总价',
  `real_fee` double DEFAULT NULL COMMENT '实付价',
  `order_status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `login_plat` int(11) DEFAULT NULL COMMENT '订单平台【1.商会，2.百享园，3.飞看云】',
  `consignee` varchar(255) DEFAULT NULL COMMENT '收货人',
  `phone` varchar(255) DEFAULT NULL COMMENT '收货人的手机号码',
  `address` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user_order
-- ----------------------------
