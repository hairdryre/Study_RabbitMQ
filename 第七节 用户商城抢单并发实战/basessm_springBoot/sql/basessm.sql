/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : basessm

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2019-04-28 09:27:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_product
-- ----------------------------
DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productNo` varchar(255) DEFAULT NULL COMMENT '商品编号',
  `total` int(255) DEFAULT NULL COMMENT '库存',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_product
-- ----------------------------
INSERT INTO `t_product` VALUES ('1', 'No123321', '100');

-- ----------------------------
-- Table structure for t_product_record
-- ----------------------------
DROP TABLE IF EXISTS `t_product_record`;
CREATE TABLE `t_product_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productNo` varchar(255) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3773 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_product_record
-- ----------------------------

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `ID` bigint(11) NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(255) DEFAULT NULL,
  `AGE` int(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'jay', '21');
INSERT INTO `t_user` VALUES ('2', 'sunny', '23');
INSERT INTO `t_user` VALUES ('3', 'sia', '20');
INSERT INTO `t_user` VALUES ('4', 'KK', '23');

-- ----------------------------
-- Procedure structure for testWhile
-- ----------------------------
DROP PROCEDURE IF EXISTS `testWhile`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `testWhile`(IN myCount INT(11),OUT result INT(11))
BEGIN
   DECLARE i INT DEFAULT 0 ; -- 定义变量
   WHILE i < myCount DO  -- 符合条件就循环
       INSERT INTO t_sys_param VALUES(NULL,i,i);
       SET i = i + 1 ; -- 计数器+1
   END WHILE;       -- 当不满足条件，结束循环  --分号一定要加！
   SET result = i;  -- 将变量赋值到输出
END
;;
DELIMITER ;
