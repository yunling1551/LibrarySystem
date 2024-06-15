/*
 Navicat Premium Data Transfer

 Source Server         : 果果
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : books

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 04/06/2024 02:04:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_book
-- ----------------------------
DROP TABLE IF EXISTS `t_book`;
CREATE TABLE `t_book`  (
  `bid` int(11) NOT NULL AUTO_INCREMENT COMMENT '图书信息的ID',
  `book_name` varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书名称',
  `author` varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作者',
  `num` int(11) NULL DEFAULT NULL COMMENT '库存',
  `press` varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出版社',
  `tid` int(11) NULL DEFAULT NULL COMMENT '类型ID',
  `times` int(11) NULL DEFAULT NULL COMMENT '被借阅次数',
  PRIMARY KEY (`bid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_book
-- ----------------------------
INSERT INTO `t_book` VALUES (1, '三国演义', '罗贯中', 9, '人民出版社', 1, 2);
INSERT INTO `t_book` VALUES (2, 'java从入门到放弃', '高斯林', 10, '秃头出版社', 3, 0);
INSERT INTO `t_book` VALUES (3, 'C语言程序设计', '李建秋', 19, '中国矿业大学出版社', 3, 10);
INSERT INTO `t_book` VALUES (4, '盗墓笔记', '南派三叔', 48, '上海文化出版社', 4, 5);
INSERT INTO `t_book` VALUES (5, '白夜行', '东野圭吾', 20, '南海出版社', 5, 3);
INSERT INTO `t_book` VALUES (6, '葫芦娃', '葫芦兄弟', 0, '上海文化出版社', 6, 5);

-- ----------------------------
-- Table structure for t_history
-- ----------------------------
DROP TABLE IF EXISTS `t_history`;
CREATE TABLE `t_history`  (
  `hid` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录表ID',
  `uid` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `name` varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `account` varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户账号',
  `bid` int(11) NULL DEFAULT NULL COMMENT '图书ID',
  `book_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书名称',
  `begin_time` datetime(0) NULL DEFAULT NULL COMMENT '借书时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '还书时间',
  `status` int(2) NULL DEFAULT NULL COMMENT '借阅状态，1为正在借阅，2 已经还书',
  PRIMARY KEY (`hid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_history
-- ----------------------------
INSERT INTO `t_history` VALUES (1, 2, '张三', 'zhangsan', 1, '三国演义', '2022-08-23 10:00:24', '2022-09-02 10:00:24', 2);
INSERT INTO `t_history` VALUES (2, 2, '张三', 'zhangsan', 3, 'C语言程序设计', '2022-08-23 10:04:36', '2022-09-10 10:04:36', 1);
INSERT INTO `t_history` VALUES (3, 3, '李四', 'lisi', 4, '盗墓笔记', '2022-08-23 10:11:04', '2022-09-22 10:11:04', 1);

-- ----------------------------
-- Table structure for t_problem
-- ----------------------------
DROP TABLE IF EXISTS `t_problem`;
CREATE TABLE `t_problem`  (
  `pid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `uid` int(11) NULL DEFAULT NULL COMMENT '反馈人ID',
  `title` varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `page` varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '页面',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '内容',
  `link` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '0 未解决  1已解决',
  `time` datetime(0) NULL DEFAULT NULL COMMENT '反馈时间',
  PRIMARY KEY (`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_problem
-- ----------------------------
INSERT INTO `t_problem` VALUES (1, 2, '8-23', '登陆页', '不知道，就想反馈', '18600000001', '1', '2022-08-23 09:20:15');

-- ----------------------------
-- Table structure for t_type
-- ----------------------------
DROP TABLE IF EXISTS `t_type`;
CREATE TABLE `t_type`  (
  `tid` int(11) NOT NULL AUTO_INCREMENT COMMENT '类型主键ID',
  `type_name` varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书类型名称',
  PRIMARY KEY (`tid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_type
-- ----------------------------
INSERT INTO `t_type` VALUES (1, '历史类');
INSERT INTO `t_type` VALUES (2, '文学类');
INSERT INTO `t_type` VALUES (3, '编程类');
INSERT INTO `t_type` VALUES (4, '玄幻小说');
INSERT INTO `t_type` VALUES (5, '国外经典');
INSERT INTO `t_type` VALUES (6, '少儿漫画');
INSERT INTO `t_type` VALUES (7, '秘籍类');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `uid` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户表主键ID',
  `account` varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号',
  `name` varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `password` varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机',
  `times` int(11) NULL DEFAULT NULL COMMENT '借阅量',
  `lend_num` int(11) NULL DEFAULT NULL COMMENT '可借阅天数',
  `max_num` int(11) NULL DEFAULT NULL COMMENT '最大可借数量',
  `role` int(1) NULL DEFAULT NULL COMMENT '角色 1用户 2管理员',
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'admin', '管理员', '4QrcOUm6Wau+VuBX8g+IPg==', '18600000001', 0, 0, 0, 2);
INSERT INTO `t_user` VALUES (2, 'zhangsan', '张三', '4QrcOUm6Wau+VuBX8g+IPg==', '18600000002', 7, 10, 19, 1);
INSERT INTO `t_user` VALUES (3, 'lisi', '李四', '4QrcOUm6Wau+VuBX8g+IPg==', '18600000003', 3, 30, 18, 1);
INSERT INTO `t_user` VALUES (4, 'wangwu', '王五', '4QrcOUm6Wau+VuBX8g+IPg==', '18600000004', 0, 15, 10, 1);
INSERT INTO `t_user` VALUES (5, 'zhaoliu', '赵六', '4QrcOUm6Wau+VuBX8g+IPg==', '18600000005', 10, 11, 10, 2);
INSERT INTO `t_user` VALUES (6, 'sunqi', '孙琦', '4QrcOUm6Wau+VuBX8g+IPg==', '18600000007', 1, 15, 10, 1);

SET FOREIGN_KEY_CHECKS = 1;
