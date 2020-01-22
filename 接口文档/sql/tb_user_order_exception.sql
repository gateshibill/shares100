/*
Navicat MySQL Data Transfer

Source Server         : 飞看-测试
Source Server Version : 50632
Source Host           : 182.61.34.125:3306
Source Database       : db_cofc

Target Server Type    : MYSQL
Target Server Version : 50632
File Encoding         : 65001

Date: 2017-10-24 12:35:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_user_order_exception`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_order_exception`;
CREATE TABLE `tb_user_order_exception` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) DEFAULT NULL COMMENT '订单异常类型, 具体看代码里对应的实体类',
  `content` varchar(500) DEFAULT NULL COMMENT '异常描述',
  `order_id` int(11) DEFAULT NULL COMMENT '订单id',
  `userid` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tb_user_order_exception
-- ----------------------------
