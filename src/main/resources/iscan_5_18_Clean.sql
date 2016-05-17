/*
Navicat MySQL Data Transfer

Source Server         : yyyyf
Source Server Version : 50173
Source Host           : localhost:3306
Source Database       : iscan

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2016-05-17 17:28:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for agency_user
-- ----------------------------
DROP TABLE IF EXISTS `agency_user`;
CREATE TABLE `agency_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(35) DEFAULT NULL,
  `hold_amount` int(11) DEFAULT NULL,
  `lst_login_date` varchar(20) DEFAULT NULL,
  `active_flag` int(11) DEFAULT NULL,
  `province` varchar(10) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `telno` varchar(11) DEFAULT NULL,
  `address` varchar(30) DEFAULT NULL,
  `name` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of agency_user
-- ----------------------------
INSERT INTO `agency_user` VALUES ('1', 'jiangsu', 'X03MO1qnZdYdgyfeuILPmQ==', '1000000', null, '1', '江苏', '南京', '13851483034', '南京', '李先生');

-- ----------------------------
-- Table structure for m_factory_order
-- ----------------------------
DROP TABLE IF EXISTS `m_factory_order`;
CREATE TABLE `m_factory_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_content` varchar(200) DEFAULT NULL,
  `receive_person` varchar(20) DEFAULT NULL,
  `telno` varchar(18) DEFAULT NULL,
  `receive_address` varchar(50) DEFAULT NULL,
  `comment` varchar(100) DEFAULT NULL,
  `send_flag` varchar(2) DEFAULT NULL,
  `send_to_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of m_factory_order
-- ----------------------------
INSERT INTO `m_factory_order` VALUES ('24', '客户:123订购价格为258的柚子:1箱,客户地址是:123,客户的电话是:213,客户备注内容: 123。', '123', '213', '123', ' 123', 'Y', '0');
INSERT INTO `m_factory_order` VALUES ('25', '客户:订购价格为258的柚子:1箱,客户地址是:,客户的电话是:,客户备注内容: 。', '', '', '', ' ', 'Y', '1');
INSERT INTO `m_factory_order` VALUES ('26', '客户:订购价格为258的柚子:1箱,客户地址是:,客户的电话是:,客户备注内容: 。', '', '', '', ' ', 'Y', '0');
INSERT INTO `m_factory_order` VALUES ('27', '客户:订购价格为258的柚子:1箱,客户地址是:,客户的电话是:,客户备注内容: 。', '', '', '', ' ', 'Y', '0');

-- ----------------------------
-- Table structure for m_product_show_info
-- ----------------------------
DROP TABLE IF EXISTS `m_product_show_info`;
CREATE TABLE `m_product_show_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `product_id` varchar(40) DEFAULT NULL,
  `batch_no` varchar(10) DEFAULT NULL,
  `produce_date` varchar(15) DEFAULT NULL,
  `produce_address` varchar(40) DEFAULT NULL,
  `sell_area` varchar(15) DEFAULT NULL,
  `sell_author` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of m_product_show_info
-- ----------------------------
INSERT INTO `m_product_show_info` VALUES ('4', '1', '6ebe7af5-437c-4999-9a1c-84181089889b', 'ttt', '2015-12-08', '中国', '中国1', '南京经销商');

-- ----------------------------
-- Table structure for m_user_account
-- ----------------------------
DROP TABLE IF EXISTS `m_user_account`;
CREATE TABLE `m_user_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `account` int(11) DEFAULT NULL,
  `currency` decimal(10,2) DEFAULT NULL,
  `qr_total_user` varchar(30) DEFAULT NULL,
  `scan_total_user` varchar(30) DEFAULT NULL,
  `warning_qr_code_no` varchar(30) DEFAULT NULL,
  `user_vistor_report` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of m_user_account
-- ----------------------------
INSERT INTO `m_user_account` VALUES ('1', '1', '757869', '859.00', '1000', '500', '15', null);

-- ----------------------------
-- Table structure for m_user_account_opt
-- ----------------------------
DROP TABLE IF EXISTS `m_user_account_opt`;
CREATE TABLE `m_user_account_opt` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) DEFAULT NULL,
  `account_purchase` int(10) DEFAULT NULL,
  `current_left` decimal(10,2) DEFAULT NULL,
  `update_time` varchar(20) DEFAULT NULL,
  `reason` varchar(10) DEFAULT NULL,
  `account_consume` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of m_user_account_opt
-- ----------------------------
INSERT INTO `m_user_account_opt` VALUES ('1', '1', '100', '800.00', '2015-11-29 16:07:57', '充值', null);
INSERT INTO `m_user_account_opt` VALUES ('2', '1', '100', '880.00', '2015-11-29 16:10:20', '充值', null);
INSERT INTO `m_user_account_opt` VALUES ('3', '1', '100', '870.00', '2015-11-29 16:10:29', '充值', null);
INSERT INTO `m_user_account_opt` VALUES ('9', '1', '100', '860.00', '2015-11-29 16:20:51', '充值', null);
INSERT INTO `m_user_account_opt` VALUES ('11', '1', '100', '849.00', '2015-11-29 16:21:04', '充值', null);
INSERT INTO `m_user_account_opt` VALUES ('12', '1', null, '859.00', '2015-11-29 16:52:48', '二维码生产', '10');
INSERT INTO `m_user_account_opt` VALUES ('13', '1', null, '859.00', '2015-11-29 17:08:42', '二维码生产', '10');
INSERT INTO `m_user_account_opt` VALUES ('14', '1', null, '859.00', '2015-11-29 17:19:05', '二维码生产', '5');
INSERT INTO `m_user_account_opt` VALUES ('15', '1', null, '859.00', '2015-11-29 17:19:55', '二维码生产', '5');
INSERT INTO `m_user_account_opt` VALUES ('16', '1', null, '859.00', '2015-11-29 17:20:54', '二维码生产', '3');
INSERT INTO `m_user_account_opt` VALUES ('17', '1', null, '859.00', '2015-12-04 23:57:27', '二维码生产', '10');
INSERT INTO `m_user_account_opt` VALUES ('18', '1', null, '859.00', '2015-12-05 00:03:53', '二维码生产', '10');
INSERT INTO `m_user_account_opt` VALUES ('19', '1', null, '859.00', '2015-12-05 00:29:57', '二维码生产', '1');
INSERT INTO `m_user_account_opt` VALUES ('20', '1', null, '859.00', '2015-12-05 00:34:49', '二维码生产', '1');
INSERT INTO `m_user_account_opt` VALUES ('21', '1', null, '859.00', '2015-12-06 21:38:26', '二维码生产', '1');
INSERT INTO `m_user_account_opt` VALUES ('22', '1', null, '859.00', '2015-12-06 23:06:45', '二维码生产', '1');
INSERT INTO `m_user_account_opt` VALUES ('23', '1', null, '859.00', '2015-12-06 23:08:48', '二维码生产', '1');
INSERT INTO `m_user_account_opt` VALUES ('24', '1', null, '859.00', '2015-12-12 14:46:09', '二维码生产', '2');
INSERT INTO `m_user_account_opt` VALUES ('25', '1', null, '859.00', '2015-12-12 22:47:29', '二维码生产', '2');
INSERT INTO `m_user_account_opt` VALUES ('26', '1', null, '859.00', '2015-12-12 23:44:02', '二维码生产', '1');
INSERT INTO `m_user_account_opt` VALUES ('27', '1', null, '859.00', '2015-12-13 13:02:48', '二维码生产', '2');
INSERT INTO `m_user_account_opt` VALUES ('28', '1', null, '859.00', '2015-12-13 23:29:00', '二维码生产', '2');
INSERT INTO `m_user_account_opt` VALUES ('29', '1', null, '859.00', '2015-12-13 23:29:17', '二维码生产', '2');
INSERT INTO `m_user_account_opt` VALUES ('30', '1', null, '859.00', '2015-12-16 13:29:27', '二维码生产', '1');
INSERT INTO `m_user_account_opt` VALUES ('31', '1', null, '859.00', '2015-12-16 13:29:59', '二维码生产', '1');
INSERT INTO `m_user_account_opt` VALUES ('32', '1', null, '859.00', '2015-12-16 13:51:37', '二维码生产', '1');
INSERT INTO `m_user_account_opt` VALUES ('33', '1', null, '859.00', '2015-12-16 23:53:14', '二维码生产', '1');
INSERT INTO `m_user_account_opt` VALUES ('34', '1', null, '859.00', '2015-12-16 23:55:41', '二维码生产', '2');
INSERT INTO `m_user_account_opt` VALUES ('35', '1', null, '859.00', '2015-12-17 00:01:40', '二维码生产', '1');
INSERT INTO `m_user_account_opt` VALUES ('36', '1', null, '859.00', '2015-12-17 00:01:49', '二维码生产', '2');
INSERT INTO `m_user_account_opt` VALUES ('37', '1', null, '859.00', '2015-12-17 00:03:39', '二维码生产', '2');
INSERT INTO `m_user_account_opt` VALUES ('38', '1', null, '859.00', '2015-12-17 00:05:46', '二维码生产', '1');
INSERT INTO `m_user_account_opt` VALUES ('39', '1', null, '859.00', '2015-12-17 00:05:52', '二维码生产', '3');
INSERT INTO `m_user_account_opt` VALUES ('40', '1', null, '859.00', '2015-12-17 09:45:28', '二维码生产', '2');
INSERT INTO `m_user_account_opt` VALUES ('41', '1', null, '859.00', '2015-12-17 14:19:36', '二维码生产', '1');
INSERT INTO `m_user_account_opt` VALUES ('42', '1', null, '859.00', '2015-12-17 14:46:32', '二维码生产', '2');
INSERT INTO `m_user_account_opt` VALUES ('43', '1', null, '859.00', '2015-12-17 14:46:48', '二维码生产', '3');
INSERT INTO `m_user_account_opt` VALUES ('44', '1', null, '859.00', '2015-12-17 23:14:01', '二维码生产', '1');
INSERT INTO `m_user_account_opt` VALUES ('45', '1', null, '859.00', '2015-12-22 23:03:27', '二维码生产', '100');
INSERT INTO `m_user_account_opt` VALUES ('46', '1', null, '859.00', '2015-12-22 23:04:45', '二维码生产', '100');
INSERT INTO `m_user_account_opt` VALUES ('47', '1', null, '859.00', '2015-12-22 23:24:25', '二维码生产', '50000');
INSERT INTO `m_user_account_opt` VALUES ('48', '1', null, '859.00', '2015-12-22 23:24:28', '二维码生产', '50000');
INSERT INTO `m_user_account_opt` VALUES ('49', '1', null, '859.00', '2015-12-22 23:24:28', '二维码生产', '50000');
INSERT INTO `m_user_account_opt` VALUES ('50', '1', null, '859.00', '2015-12-22 23:29:06', '二维码生产', '1000');
INSERT INTO `m_user_account_opt` VALUES ('51', '1', null, '859.00', '2015-12-22 23:30:12', '二维码生产', '10000');
INSERT INTO `m_user_account_opt` VALUES ('52', '1', null, '859.00', '2015-12-22 23:32:51', '二维码生产', '50000');
INSERT INTO `m_user_account_opt` VALUES ('53', '1', null, '859.00', '2015-12-22 23:53:46', '二维码生产', '10000');
INSERT INTO `m_user_account_opt` VALUES ('54', '1', null, '859.00', '2015-12-22 23:55:32', '二维码生产', '10000');
INSERT INTO `m_user_account_opt` VALUES ('55', '1', null, '859.00', '2015-12-23 22:10:49', '二维码生产', '9999');
INSERT INTO `m_user_account_opt` VALUES ('56', '1', null, '859.00', '2015-12-26 00:06:07', '二维码生产', '1');
INSERT INTO `m_user_account_opt` VALUES ('57', '1', null, '859.00', '2015-12-26 13:18:50', '二维码生产', '1');
INSERT INTO `m_user_account_opt` VALUES ('58', '1', null, '859.00', '2015-12-26 13:21:59', '二维码生产', '1');
INSERT INTO `m_user_account_opt` VALUES ('59', '1', null, '859.00', '2015-12-26 13:25:11', '二维码生产', '1');
INSERT INTO `m_user_account_opt` VALUES ('60', '1', null, '859.00', '2015-12-26 13:26:42', '二维码生产', '1');
INSERT INTO `m_user_account_opt` VALUES ('61', '1', null, '859.00', '2015-12-26 13:28:48', '二维码生产', '12');
INSERT INTO `m_user_account_opt` VALUES ('62', '1', null, '859.00', '2015-12-26 13:29:59', '二维码生产', '1');
INSERT INTO `m_user_account_opt` VALUES ('63', '1', null, '859.00', '2015-12-26 13:31:02', '二维码生产', '1');
INSERT INTO `m_user_account_opt` VALUES ('64', '1', null, '859.00', '2015-12-26 13:32:34', '二维码生产', '2');
INSERT INTO `m_user_account_opt` VALUES ('65', '1', null, '859.00', '2015-12-26 13:34:56', '二维码生产', '1');
INSERT INTO `m_user_account_opt` VALUES ('66', '1', null, '859.00', '2015-12-26 15:23:19', '二维码生产', '50');
INSERT INTO `m_user_account_opt` VALUES ('67', '1', null, '859.00', '2015-12-26 19:41:35', '二维码生产', '3');
INSERT INTO `m_user_account_opt` VALUES ('68', '1', null, '859.00', '2015-12-26 19:42:11', '二维码生产', '1');
INSERT INTO `m_user_account_opt` VALUES ('69', '1', null, '859.00', '2015-12-26 19:45:02', '二维码生产', '1');
INSERT INTO `m_user_account_opt` VALUES ('70', '1', null, '859.00', '2015-12-26 19:46:37', '二维码生产', '1');
INSERT INTO `m_user_account_opt` VALUES ('71', '1', null, '859.00', '2015-12-26 19:47:18', '二维码生产', '1');
INSERT INTO `m_user_account_opt` VALUES ('72', '1', null, '859.00', '2015-12-26 19:51:57', '二维码生产', '1');
INSERT INTO `m_user_account_opt` VALUES ('73', '1', null, '859.00', '2015-12-26 19:54:01', '二维码生产', '1');
INSERT INTO `m_user_account_opt` VALUES ('74', '1', null, '859.00', '2015-12-26 21:59:18', '二维码生产', '20');
INSERT INTO `m_user_account_opt` VALUES ('75', '1', null, '859.00', '2015-12-26 22:23:25', '二维码生产', '100');
INSERT INTO `m_user_account_opt` VALUES ('76', '1', null, '859.00', '2015-12-30 13:54:53', '二维码生产', '100');
INSERT INTO `m_user_account_opt` VALUES ('77', '1', null, '859.00', '2016-01-01 09:33:46', '二维码生产', '20');
INSERT INTO `m_user_account_opt` VALUES ('78', '1', null, '859.00', '2016-01-01 14:33:45', '二维码生产', '3');
INSERT INTO `m_user_account_opt` VALUES ('79', '1', null, '859.00', '2016-03-17 23:17:07', '二维码生产', '1');
INSERT INTO `m_user_account_opt` VALUES ('80', '1', null, '859.00', '2016-03-17 23:17:41', '二维码生产', '2');
INSERT INTO `m_user_account_opt` VALUES ('81', '1', null, '859.00', '2016-03-19 23:35:15', '二维码生产', '10');
INSERT INTO `m_user_account_opt` VALUES ('82', '1', null, '859.00', '2016-03-20 00:12:38', '二维码生产', '10');
INSERT INTO `m_user_account_opt` VALUES ('83', '1', null, '859.00', '2016-03-20 00:13:23', '二维码生产', '10');
INSERT INTO `m_user_account_opt` VALUES ('84', '1', null, '859.00', '2016-03-20 00:18:12', '二维码生产', '10');
INSERT INTO `m_user_account_opt` VALUES ('85', '1', null, '859.00', '2016-03-20 00:19:15', '二维码生产', '10');
INSERT INTO `m_user_account_opt` VALUES ('86', '1', null, '859.00', '2016-04-08 17:40:46', '二维码生产', '10');
INSERT INTO `m_user_account_opt` VALUES ('87', '1', null, '859.00', '2016-04-20 09:20:55', '二维码生产', '10');
INSERT INTO `m_user_account_opt` VALUES ('88', '1', null, '859.00', '2016-04-22 09:43:21', '二维码生产', '100');
INSERT INTO `m_user_account_opt` VALUES ('89', '1', null, '859.00', '2016-04-22 13:52:37', '二维码生产', '100');
INSERT INTO `m_user_account_opt` VALUES ('90', '1', null, '859.00', '2016-04-22 13:52:43', '二维码生产', '300');
INSERT INTO `m_user_account_opt` VALUES ('91', '1', null, '859.00', '2016-04-23 13:47:50', '二维码生产', '10');
INSERT INTO `m_user_account_opt` VALUES ('92', '1', null, '859.00', '2016-04-23 13:48:59', '二维码生产', '5');
INSERT INTO `m_user_account_opt` VALUES ('93', '1', null, '859.00', '2016-04-28 15:38:44', '二维码生产', '100');
INSERT INTO `m_user_account_opt` VALUES ('94', '1', null, '859.00', '2016-04-28 15:44:33', '二维码生产', '10');
INSERT INTO `m_user_account_opt` VALUES ('95', '1', null, '859.00', '2016-05-17 15:32:33', '二维码生产', '100');
INSERT INTO `m_user_account_opt` VALUES ('96', '1', null, '859.00', '2016-05-17 16:08:43', '二维码生产', '10');

-- ----------------------------
-- Table structure for m_user_advice_template
-- ----------------------------
DROP TABLE IF EXISTS `m_user_advice_template`;
CREATE TABLE `m_user_advice_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `template_name` varchar(30) DEFAULT NULL,
  `template_label` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of m_user_advice_template
-- ----------------------------
INSERT INTO `m_user_advice_template` VALUES ('1', '1', 'a', '通用模版');
INSERT INTO `m_user_advice_template` VALUES ('2', '1', 't', '桃子模版');
INSERT INTO `m_user_advice_template` VALUES ('3', '1', 'j', '酒类模版');
INSERT INTO `m_user_advice_template` VALUES ('4', '1', 'y', '柚子模版');

-- ----------------------------
-- Table structure for m_user_category
-- ----------------------------
DROP TABLE IF EXISTS `m_user_category`;
CREATE TABLE `m_user_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `category_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of m_user_category
-- ----------------------------
INSERT INTO `m_user_category` VALUES ('14', '1', '国产黄酒');
INSERT INTO `m_user_category` VALUES ('31', '1', '国产白酒');
INSERT INTO `m_user_category` VALUES ('32', '1', '白酒');
INSERT INTO `m_user_category` VALUES ('33', '1', '蜜柚');
INSERT INTO `m_user_category` VALUES ('34', '1', '水蜜桃');
INSERT INTO `m_user_category` VALUES ('35', '1', '洋河');
INSERT INTO `m_user_category` VALUES ('36', '1', '烟');
INSERT INTO `m_user_category` VALUES ('37', '1', '红酒');
INSERT INTO `m_user_category` VALUES ('38', '1', '石斛');

-- ----------------------------
-- Table structure for m_user_params
-- ----------------------------
DROP TABLE IF EXISTS `m_user_params`;
CREATE TABLE `m_user_params` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) DEFAULT NULL,
  `param_value` varchar(50) DEFAULT NULL,
  `remarks1` varchar(50) DEFAULT NULL,
  `remarks2` varchar(50) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of m_user_params
-- ----------------------------
INSERT INTO `m_user_params` VALUES ('1', 'ud', '产品生产时间', null, null, '1');
INSERT INTO `m_user_params` VALUES ('2', 'sl', '经销商信息', null, null, '1');
INSERT INTO `m_user_params` VALUES ('3', 'lqd', '上次查询时间', null, null, '1');

-- ----------------------------
-- Table structure for m_user_product
-- ----------------------------
DROP TABLE IF EXISTS `m_user_product`;
CREATE TABLE `m_user_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `product_id` varchar(100) NOT NULL,
  `relate_batch` varchar(30) DEFAULT NULL,
  `qrcode_total_no` int(11) DEFAULT NULL,
  `update_time` varchar(30) DEFAULT NULL,
  `advice_temp` varchar(20) DEFAULT NULL,
  `batch_params` varchar(255) DEFAULT NULL,
  `sellArthor` varchar(50) DEFAULT NULL,
  `lottery_info` varchar(250) DEFAULT NULL,
  `sellPrice` varchar(9) DEFAULT NULL,
  `productAddress` varchar(100) DEFAULT NULL,
  `level_desc` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of m_user_product
-- ----------------------------
INSERT INTO `m_user_product` VALUES ('54', '1', 'r6C3FZxH', '20151201nj', '10', '2015-12-26 21:58:18', 'default', '[\"ud\",\"sl\",\"lqd\"]', '南京德盛水果批发', '一等奖:0-1&二等奖:1-40&三等奖:40-50|10', '80', null, null);
INSERT INTO `m_user_product` VALUES ('56', '1', 'koBeSs8E', '01', '10', '2015-12-30 13:54:45', 'default', '[\"ud\",\"sl\",\"lqd\"]', '江苏苏酒集团', null, '80', null, null);
INSERT INTO `m_user_product` VALUES ('57', '1', 'EWJxztRq', '201601', '10', '2016-03-19 23:35:06', 'default', '[\"ud\",\"sl\",\"lqd\"]', '南京富旺果业有限公司', null, '80', null, null);
INSERT INTO `m_user_product` VALUES ('58', '1', 'cHn0Jtyj', '201601', '10', '2016-04-08 17:40:27', 'default', '[\"ud\",\"sl\",\"lqd\"]', '南京一二三', null, '80', null, null);
INSERT INTO `m_user_product` VALUES ('59', '1', 'r6C3FZxH', '201602', '10', '2016-04-20 09:19:42', 'a', '[\"ud\",\"sl\",\"lqd\"]', '南京永隆水果批发有限公司', null, '80', null, null);
INSERT INTO `m_user_product` VALUES ('61', '1', '78UcndWR', '20160422001', '400', '2016-04-22 13:52:25', 'a', '[]', '', null, '80', null, null);
INSERT INTO `m_user_product` VALUES ('62', '1', '78UcndWR', '20160422002', '0', '2016-04-22 13:53:03', 'a', '[]', '', null, '80', null, null);
INSERT INTO `m_user_product` VALUES ('63', '1', 't0so23lS', '201601', '15', '2016-04-23 13:47:05', 'a', '[\"ud\",\"sl\",\"lqd\"]', '南京某某烟草公司', null, '80', null, null);
INSERT INTO `m_user_product` VALUES ('64', '1', 'rbYoDIQy', 'A01', '110', '2016-04-28 15:38:33', 'a', '[\"sl\"]', '南京天雅商贸有限公司', '一等奖:0-1&二等奖:1-40&三等奖:40-50|10', '80', null, null);
INSERT INTO `m_user_product` VALUES ('65', '1', '0vQMCsjZ', '1001', '110', '2016-05-17 15:32:21', 'a', '[\"lv\"]', '', null, '599', null, '特级');

-- ----------------------------
-- Table structure for m_user_product_meta
-- ----------------------------
DROP TABLE IF EXISTS `m_user_product_meta`;
CREATE TABLE `m_user_product_meta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(30) DEFAULT NULL,
  `product_category` varchar(30) DEFAULT NULL,
  `qrcode_total_no` int(11) DEFAULT NULL,
  `update_time` varchar(20) DEFAULT NULL,
  `product_desc` varchar(50) DEFAULT NULL,
  `user_id` varchar(11) DEFAULT NULL,
  `product_id` varchar(20) DEFAULT NULL,
  `advice_temp` varchar(20) DEFAULT NULL,
  `product_address` varchar(100) DEFAULT NULL,
  `tel_no` varchar(11) DEFAULT NULL,
  `product_factory` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of m_user_product_meta
-- ----------------------------
INSERT INTO `m_user_product_meta` VALUES ('28', '蜜柚1号', '蜜柚', '20', '2015-12-26 21:56:14', '蜜柚一号品种', '1', 'r6C3FZxH', 'default', '江西井冈山', '11', '江西井冈山');
INSERT INTO `m_user_product_meta` VALUES ('30', '扳倒井酒', '国产白酒', '10', '2015-12-30 13:54:13', '扳倒井酒', '1', 'koBeSs8E', 'default', '山东', '11', '山东');
INSERT INTO `m_user_product_meta` VALUES ('31', '阳山水蜜桃', '水蜜桃', '10', '2016-03-19 23:32:37', '无锡水蜜桃', '1', 'EWJxztRq', 'default', '无锡', '11', '无锡');
INSERT INTO `m_user_product_meta` VALUES ('32', '海之蓝', '洋河', '10', '2016-04-08 17:39:40', '白酒900ML', '1', 'cHn0Jtyj', 'default', '工厂', '11', '工厂');
INSERT INTO `m_user_product_meta` VALUES ('33', '测试产品', '水蜜桃', '500', '2016-04-22 09:40:58', '水蜜桃 for test', '1', '78UcndWR', 'default', '无锡', '11', '无锡');
INSERT INTO `m_user_product_meta` VALUES ('34', '红南京', '烟', '15', '2016-04-23 13:45:58', '南京卷烟厂', '1', 't0so23lS', 'default', '南京', '11', '南京');
INSERT INTO `m_user_product_meta` VALUES ('35', '罗纳河谷进口干红', '红酒', '110', '2016-04-28 15:37:42', '罗纳河谷进口干红', '1', 'rbYoDIQy', 'default', '法国', '11', '法国');
INSERT INTO `m_user_product_meta` VALUES ('36', '霍山石斛', '石斛', '110', '2016-05-17 15:31:20', '霍山石斛', '1', '0vQMCsjZ', 'default', '安徽霍山', '15801922887', '霍山县万林石斛合作社');

-- ----------------------------
-- Table structure for m_user_qrcode
-- ----------------------------
DROP TABLE IF EXISTS `m_user_qrcode`;
CREATE TABLE `m_user_qrcode` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `product_id` varchar(40) DEFAULT NULL,
  `product_batch` varchar(40) DEFAULT NULL,
  `qr_query_string` varchar(120) NOT NULL,
  `query_times` int(11) DEFAULT NULL,
  `query_date` varchar(20) DEFAULT NULL,
  `active_flag` varchar(3) DEFAULT NULL,
  `create_date` varchar(20) DEFAULT NULL,
  `query_match` varchar(30) DEFAULT NULL,
  `vistor_ip_addr` varchar(18) DEFAULT NULL,
  `vistor_phy_addr` varchar(25) DEFAULT NULL,
  `ip_check_flag` varchar(2) DEFAULT NULL,
  `website_query_times` int(11) DEFAULT NULL,
  `website_query_date` varchar(20) DEFAULT NULL,
  `cache_flag` char(1) DEFAULT NULL,
  `lottery_flag` char(1) DEFAULT NULL,
  `lottery_desc` varchar(14) DEFAULT NULL,
  `get_lottery_flag` char(1) DEFAULT NULL,
  `delete_flag` char(1) DEFAULT NULL,
  `lottery_check_flag` char(1) DEFAULT NULL,
  `pass_code` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=809 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of m_user_qrcode
-- ----------------------------
INSERT INTO `m_user_qrcode` VALUES ('14', '1', 'r6C3FZxH', '20151201nj', 'http://a.315kc.com/m/r/y/i.htm?1458403958781keTwuGZp', '49', '2016-05-14 18:03:430', 'N', '2016-03-20 00:12:38', '1458403958781keTwuGZp', '127.0.0.1', null, 'N', null, null, 'N', 'N', '恭喜你中奖:二等奖', 'N', 'N', 'Y', '1');
INSERT INTO `m_user_qrcode` VALUES ('17', '1', 'r6C3FZxH', '20151201nj', 'http://a.315kc.com/m/r/y/i.htm?1458403958812ZHoJkFl4', '1', '2016-03-21 18:00:265', 'N', '2016-03-20 00:12:38', '1458403958812ZHoJkFl4', '117.136.46.51', null, 'N', null, null, 'Y', 'N', null, 'N', 'N', 'N', '4');
INSERT INTO `m_user_qrcode` VALUES ('18', '1', 'r6C3FZxH', '20151201nj', 'http://a.315kc.com/m/r/y/i.htm?145840395881234S9tpZz', '3', '2016-04-23 17:15:427', 'N', '2016-03-20 00:12:38', '145840395881234S9tpZz', '127.0.0.1', null, 'N', null, null, 'Y', 'N', null, 'N', 'N', 'N', '5');
INSERT INTO `m_user_qrcode` VALUES ('34', '1', 'koBeSs8E', '01', 'http://a.315kc.com/m/r/j/i.htm?1458404292390QN0ZFquZ', '6', '2016-05-01 08:36:849', 'N', '2016-03-20 00:18:12', '1458404292390QN0ZFquZ', '127.0.0.1', null, 'N', null, null, 'Y', 'N', null, 'N', 'N', 'N', '11');
INSERT INTO `m_user_qrcode` VALUES ('37', '1', 'koBeSs8E', '01', 'http://a.315kc.com/m/r/j/i.htm?1458404292390aTrMIm6w', '17', '2016-05-06 22:12:892', 'N', '2016-03-20 00:18:12', '1458404292390aTrMIm6w', '127.0.0.1', null, 'N', null, null, 'Y', 'N', null, 'N', 'N', 'Y', '14');
INSERT INTO `m_user_qrcode` VALUES ('39', '1', 'koBeSs8E', '01', 'http://a.315kc.com/m/r/j/i.htm?1458404292390OF0wd3qQ', '7', '2016-05-17 17:24:697', 'N', '2016-03-20 00:18:12', '1458404292390OF0wd3qQ', '0:0:0:0:0:0:0:1', null, 'N', null, null, 'Y', 'N', null, 'N', 'N', 'Y', '16');
INSERT INTO `m_user_qrcode` VALUES ('44', '1', 'EWJxztRq', '201601', 'http://a.315kc.com/m/r/t/i.htm?1458404355703vnOlUz72', '17', '2016-05-14 20:49:540', 'N', '2016-03-20 00:19:15', '1458404355703vnOlUz72', '127.0.0.1', null, 'N', null, null, 'Y', 'N', null, 'N', 'N', 'Y', '21');
INSERT INTO `m_user_qrcode` VALUES ('54', '1', 'cHn0Jtyj', '201601', 'http://a.315kc.com/m/r/y/i.htm?1460108446771usWHXnNE', '1', '2016-04-08 17:41:474', 'N', '2016-04-08 17:40:46', '1460108446771usWHXnNE', '127.0.0.1', null, 'N', '1', '2016-04-08 17:41:380', 'Y', 'N', null, 'N', 'N', 'N', null);
INSERT INTO `m_user_qrcode` VALUES ('64', '1', 'r6C3FZxH', '201602', 'http://a.315kc.com/m/r/a/i.htm?1461115255661k1gBF8WU', '3', '2016-04-23 16:31:396', 'N', '2016-04-20 09:20:55', '1461115255661k1gBF8WU', '127.0.0.1', null, 'N', null, null, 'Y', 'N', null, 'N', 'N', 'N', null);
INSERT INTO `m_user_qrcode` VALUES ('589', '1', 'rbYoDIQy', 'A01', 'http://a.315kc.com/m/r/a/i.htm?1461829123599rHU6BeTx', '3', '2016-05-14 17:54:180', 'N', '2016-04-28 15:38:43', '1461829123599rHU6BeTx', '127.0.0.1', null, 'N', null, null, 'Y', 'N', '恭喜你中奖:二等奖', 'N', 'N', 'Y', null);
INSERT INTO `m_user_qrcode` VALUES ('591', '1', 'rbYoDIQy', 'A01', 'http://a.315kc.com/m/r/a/i.htm?1461829123614eWitnglR', '5', '2016-05-13 22:57:993', 'N', '2016-04-28 15:38:43', '1461829123614eWitnglR', '127.0.0.1', null, 'N', null, null, 'Y', 'N', '恭喜你中奖:二等奖', 'N', 'N', 'Y', null);
INSERT INTO `m_user_qrcode` VALUES ('593', '1', 'rbYoDIQy', 'A01', 'http://a.315kc.com/m/r/a/i.htm?1461829123614OMwgxYJA', '1', '2016-05-06 09:44:674', 'N', '2016-04-28 15:38:43', '1461829123614OMwgxYJA', '127.0.0.1', null, 'N', null, null, 'Y', 'N', '恭喜你中奖:一等奖', 'N', 'N', 'Y', null);
INSERT INTO `m_user_qrcode` VALUES ('622', '1', 'rbYoDIQy', 'A01', 'http://a.315kc.com/m/r/a/i.htm?14618291236468DYxP9s3', '9', '2016-05-14 16:11:09', 'N', '2016-04-28 15:38:43', '14618291236468DYxP9s3', '127.0.0.1', null, 'N', null, null, 'Y', 'N', null, 'N', 'N', 'N', '99');
INSERT INTO `m_user_qrcode` VALUES ('696', '1', 'rbYoDIQy', 'A01', 'http://a.315kc.com/m/r/a/i.htm?1461829473177d4aFKdS9', '2', '2016-05-04 23:44:699', 'N', '2016-04-28 15:44:33', '1461829473177d4aFKdS9', '127.0.0.1', null, 'N', null, null, 'Y', 'N', '恭喜你中奖:二等奖', 'N', 'N', 'Y', null);
INSERT INTO `m_user_qrcode` VALUES ('699', '1', '0vQMCsjZ', '1001', 'http://a.315kc.com/m/r/a/i.htm?00120160517NUB3FLJz', '2', '2016-05-17 17:24:955', 'N', '2016-05-17 15:32:32', '00120160517NUB3FLJz', '0:0:0:0:0:0:0:1', null, 'N', null, null, 'Y', 'N', null, '', 'N', 'Y', null);

-- ----------------------------
-- Table structure for system_meta_table
-- ----------------------------
DROP TABLE IF EXISTS `system_meta_table`;
CREATE TABLE `system_meta_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `system_message` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_meta_table
-- ----------------------------
INSERT INTO `system_meta_table` VALUES ('2', '系统更新V1.0');

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `a` char(1) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of test
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `query_qrcode_table` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `user_type` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `user_email` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `user_telno` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `user_factory_name` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `user_factory_address` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `user_contact_person_name` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `active_flag` int(1) DEFAULT NULL,
  `agency_id` int(10) DEFAULT NULL,
  `create_date` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `expire_date` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `lottery_ability_flag` char(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', 'X03MO1qnZdYdgyfeuILPmQ==', 'M_USER_QRCODE', 'a类:a:y', '179012331@qq.com', '13911940313', '法国蒙蒂里集团', '法国', '王经理', '1', '1', '2015-12-12', '2016-12-12', 'Y');

-- ----------------------------
-- Table structure for user_statistics
-- ----------------------------
DROP TABLE IF EXISTS `user_statistics`;
CREATE TABLE `user_statistics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_visting_statistic` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_statistics
-- ----------------------------
