/*
 Navicat Premium Data Transfer

 Source Server         : LocalMySql
 Source Server Type    : MySQL
 Source Server Version : 50623
 Source Host           : localhost
 Source Database       : iscan

 Target Server Type    : MySQL
 Target Server Version : 50623
 File Encoding         : utf-8

 Date: 07/05/2016 22:18:06 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `cust_tile_sh_table`
-- ----------------------------
DROP TABLE IF EXISTS `cust_tile_sh_table`;
CREATE TABLE `cust_tile_sh_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key_t` varchar(10) DEFAULT NULL,
  `value_t` varchar(5000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `cust_tile_sh_table`
-- ----------------------------
BEGIN;
INSERT INTO `cust_tile_sh_table` VALUES ('1', 'cjs', '<a href=\"#\"><img src=\"images/cjs/1.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/cjs/2.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/cjs/3.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/cjs/4.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/cjs/5.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/cjs/6.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/cjs/7.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/cjs/8.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/cjs/9.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/cjs/10.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/cjs/11.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/cjs/12.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/cjs/13.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/cjs/14.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/cjs/15.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/cjs/16.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/cjs/17.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/cjs/18.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/cjs/19.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/cjs/20.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/cjs/21.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/cjs/22.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/cjs/23.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/cjs/24.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/cjs/25.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/cjs/26.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/cjs/27.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/cjs/28.jpg\" border=\"0\" /></a>'), ('2', 'dz', '<a href=\"#\"><img src=\"images/dz/1.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/dz/2.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/dz/3.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/dz/4.jpg\" border=\"0\" /></a>'), ('3', 'msk', '<a href=\"#\"><img src=\"images/msk/1.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/msk/2.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/msk/3.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/msk/4.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/msk/5.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/msk/6.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/msk/7.jpg\" border=\"0\" /></a>'), ('4', 'qz', '<a href=\"#\"><img src=\"images/qz/1.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/qz/2.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/qz/3.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/qz/4.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/qz/5.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/qz/6.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/qz/7.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/qz/8.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/qz/9.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/qz/10.jpg\" border=\"0\" /></a>'), ('5', 'sn', '<a href=\"#\"><img src=\"images/sn/1.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/sn/2.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/sn/3.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/sn/4.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/sn/5.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/sn/6.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/sn/7.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/sn/8.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/sn/9.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/sn/10.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/sn/11.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/sn/12.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/sn/13.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/sn/14.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/sn/15.jpg\" border=\"0\" /></a>\n<a href=\"#\"><img src=\"images/sn/16.jpg\" border=\"0\" /></a>');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
