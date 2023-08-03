/*
 Navicat Premium Data Transfer

 Source Server         : 本地mysql
 Source Server Type    : MySQL
 Source Server Version : 80033 (8.0.33)
 Source Host           : localhost:3306
 Source Schema         : questionnaire

 Target Server Type    : MySQL
 Target Server Version : 80033 (8.0.33)
 File Encoding         : 65001

 Date: 03/08/2023 16:30:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for answersheet
-- ----------------------------
DROP TABLE IF EXISTS `answersheet`;
CREATE TABLE `answersheet`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `userId` bigint NULL DEFAULT NULL,
  `userAccount` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `questionID` int NULL DEFAULT NULL,
  `surveyId` int NULL DEFAULT NULL,
  `selectChoices` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of answersheet
-- ----------------------------
INSERT INTO `answersheet` VALUES (1, 4, 'nene', 94, 2, '1');
INSERT INTO `answersheet` VALUES (2, 4, 'nene', 95, 2, '1,2');
INSERT INTO `answersheet` VALUES (3, 5, 'dingzhen', 94, 2, '2');
INSERT INTO `answersheet` VALUES (4, 5, 'dingzhen', 95, 2, '1');
INSERT INTO `answersheet` VALUES (5, 3, 'wangyuan', 96, 3, '4');
INSERT INTO `answersheet` VALUES (6, 3, 'wangyuan', 97, 3, '1,2,3');

-- ----------------------------
-- Table structure for choices
-- ----------------------------
DROP TABLE IF EXISTS `choices`;
CREATE TABLE `choices`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '选项id',
  `questionId` int NOT NULL COMMENT '对应着题目表',
  `isChoose` int NULL DEFAULT NULL COMMENT '0-未选，1-已选',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '选项描述',
  `totalTimes` int NULL DEFAULT NULL COMMENT '选择的次数，用于分析',
  `destination` int NULL DEFAULT NULL COMMENT '用于题目的跳转',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 240 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of choices
-- ----------------------------
INSERT INTO `choices` VALUES (229, 94, NULL, '电子烟', NULL, 0);
INSERT INTO `choices` VALUES (230, 94, NULL, '传统香烟', NULL, 0);
INSERT INTO `choices` VALUES (231, 95, NULL, '电子顶针', NULL, 0);
INSERT INTO `choices` VALUES (232, 95, NULL, '颠佬王源', NULL, 0);
INSERT INTO `choices` VALUES (233, 96, NULL, '顶针', NULL, 0);
INSERT INTO `choices` VALUES (234, 96, NULL, 'dingzhen', NULL, 0);
INSERT INTO `choices` VALUES (235, 96, NULL, '丁真', NULL, 0);
INSERT INTO `choices` VALUES (236, 96, NULL, '坤坤', NULL, 0);
INSERT INTO `choices` VALUES (237, 97, NULL, 'I got somke', NULL, 0);
INSERT INTO `choices` VALUES (238, 97, NULL, 'zood', NULL, 0);
INSERT INTO `choices` VALUES (239, 97, NULL, '烟distance', NULL, 0);

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `projectName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '项目名称',
  `projectDescription` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '项目描述',
  `userId` bigint NOT NULL DEFAULT 1 COMMENT '用户id？？',
  `createBy` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
  `createTime` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updateBy` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '最新更新者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES (1, '项目一', '计算机视觉', 1, 'admin', '2023-06-27 17:08:53', '2023-06-27 17:08:53', 'admin');
INSERT INTO `project` VALUES (2, '项目二', '操作系统', 1, 'admin', '2023-06-27 17:09:08', '2023-06-27 17:09:08', 'admin');
INSERT INTO `project` VALUES (3, '项目三', '数值分析', 1, 'admin', '2023-06-27 17:09:26', '2023-06-27 17:09:26', 'admin');
INSERT INTO `project` VALUES (4, '项目四', '计算机网络', 1, 'admin', '2023-06-27 17:09:42', '2023-06-27 17:09:42', 'admin');
INSERT INTO `project` VALUES (5, '项目五', '数据库', 1, 'admin', '2023-06-27 17:13:48', '2023-06-27 17:13:48', 'admin');

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '题目id',
  `surveyId` int NOT NULL COMMENT '对应的问卷id',
  `questionDescription` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '问题描述',
  `questionType` int NULL DEFAULT NULL COMMENT '类型 0-单选，1-多选',
  `totalTimes` int NULL DEFAULT NULL COMMENT '一共被回答的次数，用于统计',
  `ans` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户的答案',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `surveyId`(`surveyId` ASC) USING BTREE,
  INDEX `id`(`id` ASC, `surveyId` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 98 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES (94, 2, '你抽什么香烟', 0, NULL, NULL);
INSERT INTO `question` VALUES (95, 2, '你支持谁', 1, NULL, NULL);
INSERT INTO `question` VALUES (96, 3, '请问你最喜欢的说唱歌手是谁', 0, NULL, NULL);
INSERT INTO `question` VALUES (97, 3, '你最喜欢的说唱歌曲是什么', 1, NULL, NULL);

-- ----------------------------
-- Table structure for survey
-- ----------------------------
DROP TABLE IF EXISTS `survey`;
CREATE TABLE `survey`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '问卷id',
  `surveyType` int NOT NULL DEFAULT 0 COMMENT '问卷类型 0-正常问卷 1-限时问卷 2-限次问卷 3-面向公众问卷',
  `surveyName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '问卷名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '问卷描述',
  `finishUserId` bigint NOT NULL DEFAULT 0 COMMENT '完成问卷用户的id',
  `surveyStatus` int NOT NULL DEFAULT 0 COMMENT '问卷状态，0-未发布，1-发布进行中，2-已完成且结束，3-已超时未完成',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '查看问卷的详细地址',
  `canFinishTime` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '能完成的时间（分钟）',
  `totalTimes` int NULL DEFAULT 1 COMMENT '一共能够完成几次',
  `nowTimes` int NULL DEFAULT 0 COMMENT '现在完成了几次',
  `canFinishUserId` bigint NULL DEFAULT NULL COMMENT '能完成的用户id',
  `createTime` timestamp NOT NULL COMMENT '创建时间',
  `updateTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleteTime` datetime NULL DEFAULT NULL COMMENT '删除时间',
  `isDelete` int NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of survey
-- ----------------------------
INSERT INTO `survey` VALUES (1, 0, '关于电子烟的调查', '遗！悟！（遗憾终生最终悔悟）', 0, 0, NULL, NULL, 1, 0, NULL, '2023-08-03 10:46:59', '2023-08-03 10:59:44', NULL, 1);
INSERT INTO `survey` VALUES (2, 0, '关于电子烟的调查', '遗！悟！（遗憾终生最终悔悟）', 0, 0, NULL, NULL, 1, 0, NULL, '2023-08-03 10:59:44', '2023-08-03 10:59:44', NULL, 0);
INSERT INTO `survey` VALUES (3, 1, '关于说唱圈音乐喜好的调查', '当下最流行的音乐，你了解么？', 0, 0, NULL, '15', 1, 0, NULL, '2023-08-03 15:47:12', '2023-08-03 15:47:12', NULL, 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `userAccount` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登陆账号',
  `avatarUrl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `gender` tinyint NULL DEFAULT 0 COMMENT '性别 0-男 1-女',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `userStatus` int(10) UNSIGNED ZEROFILL NOT NULL DEFAULT 0000000000 COMMENT '用户状态 0-正常',
  `createTime` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `isDelete` tinyint NULL DEFAULT 0 COMMENT '逻辑删除 0-未删除',
  `userRole` tinyint NULL DEFAULT 0 COMMENT '用户角色 0-普通用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '冰雪灬独舞', '8bf75d25716494a5e1ae63de79db741a', 'user', 'https://avatars.githubusercontent.com/u/103118339?v=4', 0, '', '', 0000000000, '2023-06-11 15:06:31', '2023-06-17 15:06:31', 0, 0);
INSERT INTO `user` VALUES (2, '冰雪灬独舞', '8bf75d25716494a5e1ae63de79db741a', 'admin', 'https://avatars.githubusercontent.com/u/103118339?v=4', 0, NULL, NULL, 0000000000, '2023-06-11 15:07:35', '2023-06-11 15:06:31', 0, 1);
INSERT INTO `user` VALUES (3, 'wangyuan', '8bf75d25716494a5e1ae63de79db741a', 'wangyuan', 'https://avatars.githubusercontent.com/u/103118339?v=4', 0, '123', '123', 0000000001, '2023-06-11 15:08:12', '2023-06-11 15:06:31', 0, 0);
INSERT INTO `user` VALUES (4, '冰雪灬独舞', '8bf75d25716494a5e1ae63de79db741a', 'nene', 'https://avatars.githubusercontent.com/u/103118339?v=4', 0, '123', '123', 0000000000, '2023-06-11 15:09:54', '2023-06-11 15:06:31', 0, 0);
INSERT INTO `user` VALUES (5, '瑞克五die', '8bf75d25716494a5e1ae63de79db741a', 'dingzhen', 'https://avatars.githubusercontent.com/u/103118339?v=4', 0, NULL, NULL, 0000000000, '2023-08-03 10:32:15', '2023-08-03 10:32:15', 0, 0);
INSERT INTO `user` VALUES (6, NULL, '8bf75d25716494a5e1ae63de79db741a', 'admin123', NULL, 0, NULL, NULL, 0000000000, '2023-08-03 14:53:25', '2023-08-03 14:53:25', 0, 0);

SET FOREIGN_KEY_CHECKS = 1;
