SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `m_luckydraw`
-- ----------------------------
DROP TABLE IF EXISTS `m_luckydraw`;
CREATE TABLE `m_luckydraw` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cust_id` int(11) NOT NULL,
  `product_id` int(11) DEFAULT NULL,
  `totals` int(11) NOT NULL,
  `item1` varchar(50) DEFAULT NULL,
  `item1_count` int(11) DEFAULT NULL,
  `item2` varchar(50) DEFAULT NULL,
  `item2_count` int(11) DEFAULT NULL,
  `item3` varchar(50) DEFAULT NULL,
  `item3_count` int(11) DEFAULT NULL,
  `item4` varchar(50) DEFAULT NULL,
  `item4_count` int(11) DEFAULT NULL,
  `item5` varchar(50) DEFAULT NULL,
  `item5_count` int(11) DEFAULT NULL,
  `item6` varchar(50) DEFAULT NULL,
  `item6_count` int(11) DEFAULT NULL,
  `validate_dttm` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `m_luckydraw`
-- ----------------------------
BEGIN;
INSERT INTO `m_luckydraw` VALUES ('26', '123', null, '50', '一等奖', '3', '二等奖', '5', '三等奖', '10', '纪念奖', '20', '', '0', '', '0', '2016-12-31 23:23:59'), ('27', '456', null, '100', '特等奖', '1', '一等奖', '1', '二等奖', '2', '三等奖', '3', '四等奖', '4', '五等奖', '5', '2016-12-31 23:23:59');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;



SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `m_luckydraw_log`
-- ----------------------------
DROP TABLE IF EXISTS `m_luckydraw_log`;
CREATE TABLE `m_luckydraw_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` varchar(50) NOT NULL,
  `luckydraw_id` int(11) NOT NULL,
  `prize` varchar(50) NOT NULL COMMENT '奖项名称',
  `kick_id` varchar(10) NOT NULL COMMENT '验证码',
  `kick_dttm` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`,`kick_dttm`,`kick_id`,`sid`,`prize`)
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
