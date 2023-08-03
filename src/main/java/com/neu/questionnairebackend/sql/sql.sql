/*
 Navicat Premium Data Transfer

 Source Server         : 本地-mysql
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : questionnaire

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 03/08/2023 07:45:01
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
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of answersheet
-- ----------------------------
INSERT INTO `answersheet` VALUES (1, 2, 'admin', 9, 6, '1');
INSERT INTO `answersheet` VALUES (2, 2, 'admin', 10, 6, '1,2');
INSERT INTO `answersheet` VALUES (3, 4, 'jingxin', 9, 6, '2');
INSERT INTO `answersheet` VALUES (4, 4, 'jingxin', 10, 6, '1');

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
) ENGINE = InnoDB AUTO_INCREMENT = 224 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of choices
-- ----------------------------
INSERT INTO `choices` VALUES (18, 9, NULL, '50', NULL, 2);
INSERT INTO `choices` VALUES (19, 9, NULL, '70', NULL, 0);
INSERT INTO `choices` VALUES (20, 10, NULL, '90', NULL, 0);
INSERT INTO `choices` VALUES (21, 10, NULL, '60', NULL, 0);
INSERT INTO `choices` VALUES (211, 85, NULL, '1', NULL, 0);
INSERT INTO `choices` VALUES (212, 85, NULL, '1', NULL, 0);
INSERT INTO `choices` VALUES (213, 86, NULL, '2', NULL, 0);
INSERT INTO `choices` VALUES (214, 86, NULL, '2', NULL, 0);
INSERT INTO `choices` VALUES (215, 87, NULL, '3', NULL, 0);
INSERT INTO `choices` VALUES (216, 87, NULL, '3', NULL, 0);
INSERT INTO `choices` VALUES (217, 87, NULL, '3', NULL, 0);
INSERT INTO `choices` VALUES (218, 88, NULL, '4', NULL, 0);
INSERT INTO `choices` VALUES (219, 88, NULL, '4', NULL, 0);
INSERT INTO `choices` VALUES (220, 89, NULL, NULL, NULL, 0);
INSERT INTO `choices` VALUES (221, 89, NULL, NULL, NULL, 0);
INSERT INTO `choices` VALUES (222, 89, NULL, NULL, NULL, 0);
INSERT INTO `choices` VALUES (223, 90, NULL, NULL, NULL, 0);
INSERT INTO `choices` VALUES (224, 90, NULL, NULL, NULL, 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

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
                             INDEX `surveyId`(`surveyId`) USING BTREE,
                             INDEX `id`(`id`, `surveyId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 90 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES (3, 1, '', 0, 1, '');
INSERT INTO `question` VALUES (9, 6, '满意度', 0, NULL, NULL);
INSERT INTO `question` VALUES (10, 6, '满意度111', 1, NULL, NULL);
INSERT INTO `question` VALUES (85, 53, '1', 0, NULL, NULL);
INSERT INTO `question` VALUES (86, 54, '2', 0, NULL, NULL);
INSERT INTO `question` VALUES (87, 55, '3', 0, NULL, NULL);
INSERT INTO `question` VALUES (88, 56, '4', 0, NULL, NULL);
INSERT INTO `question` VALUES (89, 57, NULL, 0, NULL, NULL);
INSERT INTO `question` VALUES (90, 57, NULL, 0, NULL, NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of survey
-- ----------------------------
INSERT INTO `survey` VALUES (1, 0, NULL, NULL, 0, 0, NULL, NULL, 1, 0, NULL, '2023-06-23 15:42:01', '2023-06-23 15:56:39', NULL, 1);
INSERT INTO `survey` VALUES (2, 1, '芝士问卷', '问卷闭嘴', 0, 0, NULL, '15', 1, 0, NULL, '2023-06-23 15:51:03', '2023-06-23 16:01:54', NULL, 1);
INSERT INTO `survey` VALUES (3, 1, '这是一张问卷', '这是这张限时问卷的描述', 0, 0, NULL, '15', 1, 0, NULL, '2023-06-23 16:07:02', '2023-06-23 16:10:07', NULL, 1);
INSERT INTO `survey` VALUES (4, 1, '这是一张限时问卷', '这是问卷的描述', 0, 0, NULL, '15', 1, 0, NULL, '2023-06-23 16:11:35', '2023-06-23 16:21:41', NULL, 1);
INSERT INTO `survey` VALUES (5, 1, '问卷一', '这是一张限时问卷', 0, 0, NULL, '15', 1, 0, NULL, '2023-06-23 16:22:59', '2023-06-23 16:23:11', NULL, 1);
INSERT INTO `survey` VALUES (6, 1, '问卷二', '操作系统课程满意度2', 0, 1, NULL, '15', 1, 0, NULL, '2023-06-23 16:23:11', '2023-06-27 19:24:28', NULL, 0);
INSERT INTO `survey` VALUES (7, 0, '123', '123', 1, 0, '123', '12', 1, 0, 0, '2023-06-23 17:31:45', '2023-06-23 21:15:49', '2023-06-23 17:31:45', 1);
INSERT INTO `survey` VALUES (8, 0, '123', '123', 1, 0, '123', '12', 1, 0, 0, '2023-06-23 17:32:44', '2023-06-23 21:15:58', '2023-06-23 17:32:44', 1);
INSERT INTO `survey` VALUES (9, 3, '暗黑风格问卷', '这是一张暗黑风格问卷', 0, 0, NULL, NULL, 1, 0, NULL, '2023-06-25 14:52:38', '2023-06-25 15:07:16', NULL, 1);
INSERT INTO `survey` VALUES (10, 0, '暗黑问卷', '12345678', 0, 0, NULL, NULL, 1, 0, NULL, '2023-06-25 15:07:43', '2023-06-25 15:08:59', NULL, 1);
INSERT INTO `survey` VALUES (11, 0, '暗黑问卷', '12345678', 0, 0, NULL, NULL, 1, 0, NULL, '2023-06-25 15:09:00', '2023-06-25 15:32:20', NULL, 1);
INSERT INTO `survey` VALUES (12, 3, '暗黑问卷', '12345678', 0, 0, NULL, NULL, 1, 0, NULL, '2023-06-25 15:32:20', '2023-06-25 15:36:09', NULL, 1);
INSERT INTO `survey` VALUES (13, 3, '暗黑问卷', NULL, 0, 0, NULL, NULL, 1, 0, NULL, '2023-06-25 15:38:58', '2023-06-25 15:46:52', NULL, 1);
INSERT INTO `survey` VALUES (14, 3, '暗黑问卷', NULL, 0, 0, NULL, NULL, 1, 0, NULL, '2023-06-25 15:46:52', '2023-06-26 10:17:33', NULL, 1);
INSERT INTO `survey` VALUES (15, 1, '限时', '123', 0, 0, NULL, '15', 1, 0, NULL, '2023-06-26 10:59:02', '2023-06-26 19:16:17', NULL, 1);
INSERT INTO `survey` VALUES (16, 2, '限时', '123', 0, 0, NULL, NULL, 5, 0, NULL, '2023-06-26 10:59:38', '2023-06-26 19:16:10', NULL, 1);
INSERT INTO `survey` VALUES (17, 3, '限时', '123', 0, 0, NULL, NULL, 1, 0, NULL, '2023-06-26 11:00:01', '2023-06-26 19:16:07', NULL, 1);
INSERT INTO `survey` VALUES (18, 0, '普通问卷', '这是描述', 0, 0, NULL, NULL, 1, 0, NULL, '2023-06-26 11:14:10', '2023-06-26 14:53:08', NULL, 1);
INSERT INTO `survey` VALUES (19, 0, '修改后的普通问卷', '这是修改后的描述', 0, 0, NULL, NULL, 1, 0, NULL, '2023-06-26 11:14:56', '2023-06-26 19:16:35', NULL, 1);
INSERT INTO `survey` VALUES (20, 0, '修改后的普通问卷', '这是修改后的描述', 0, 1, NULL, NULL, 1, 0, NULL, '2023-06-26 11:20:00', '2023-06-26 18:42:17', NULL, 1);
INSERT INTO `survey` VALUES (21, 1, '限时问卷', '这是一份限时问卷', 0, 0, NULL, '12', 1, 0, NULL, '2023-06-26 15:02:12', '2023-06-26 15:06:46', NULL, 1);
INSERT INTO `survey` VALUES (22, 2, '1', '1', 0, 0, NULL, NULL, 5, 0, NULL, '2023-06-26 15:03:01', '2023-06-26 15:06:42', NULL, 1);
INSERT INTO `survey` VALUES (23, 1, '限时问卷', '这是一个限时问卷', 0, 0, NULL, '12', 1, 0, NULL, '2023-06-26 15:08:18', '2023-06-26 15:11:38', NULL, 1);
INSERT INTO `survey` VALUES (24, 2, '限次问卷', '123', 0, 0, NULL, NULL, 5, 0, NULL, '2023-06-26 15:09:11', '2023-06-26 15:11:35', NULL, 1);
INSERT INTO `survey` VALUES (25, 3, '自选风格', '123', 0, 0, NULL, NULL, 1, 0, NULL, '2023-06-26 15:10:08', '2023-06-26 15:11:28', NULL, 1);
INSERT INTO `survey` VALUES (26, 4, '123', '12345', 0, 0, NULL, NULL, 1, 0, NULL, '2023-06-26 15:10:58', '2023-06-26 15:11:31', NULL, 1);
INSERT INTO `survey` VALUES (27, 4, NULL, NULL, 0, 0, NULL, NULL, 1, 0, NULL, '2023-06-26 15:13:02', '2023-06-26 15:13:13', NULL, 1);
INSERT INTO `survey` VALUES (28, 1, '限时问卷', '这是限时问卷', 0, 0, NULL, '15', 1, 0, NULL, '2023-06-26 15:16:22', '2023-06-26 18:42:13', NULL, 1);
INSERT INTO `survey` VALUES (29, 2, '限次问卷', '123', 0, 0, NULL, NULL, 5, 0, NULL, '2023-06-26 15:17:24', '2023-06-26 18:42:10', NULL, 1);
INSERT INTO `survey` VALUES (30, 3, '自选风格', '123', 0, 0, NULL, NULL, 1, 0, NULL, '2023-06-26 15:18:14', '2023-06-26 18:42:01', NULL, 1);
INSERT INTO `survey` VALUES (31, 4, '面向群众', '123', 0, 0, NULL, NULL, 1, 0, NULL, '2023-06-26 15:19:10', '2023-06-26 18:42:04', NULL, 1);
INSERT INTO `survey` VALUES (32, 1, '修改后的项目一', '这是一张修改后的限时问卷', 0, 0, NULL, '15', 1, 0, NULL, '2023-06-26 15:30:54', '2023-06-26 15:31:21', NULL, 1);
INSERT INTO `survey` VALUES (33, 1, '修改后的项目一', '这是一张修改后的限时问卷', 0, 0, NULL, '15', 1, 0, NULL, '2023-06-26 15:31:21', '2023-06-26 18:42:07', NULL, 1);
INSERT INTO `survey` VALUES (34, 2, '第二个问卷', '实训好难', 0, 0, NULL, '1', 1, 0, NULL, '2023-06-26 18:47:39', '2023-06-26 19:22:54', NULL, 1);
INSERT INTO `survey` VALUES (35, 2, '第三个问卷', 'I got smoke!', 0, 1, NULL, NULL, 5, 0, NULL, '2023-06-26 18:49:33', '2023-06-26 18:50:49', NULL, 1);
INSERT INTO `survey` VALUES (36, 3, '第三个问卷', 'I got smoke!', 0, 0, NULL, NULL, 1, 0, NULL, '2023-06-26 18:50:50', '2023-06-26 18:52:10', NULL, 1);
INSERT INTO `survey` VALUES (37, 3, '第三个问卷', 'I got it!', 0, 0, NULL, NULL, 1, 0, NULL, '2023-06-26 18:52:11', '2023-06-27 17:11:30', NULL, 1);
INSERT INTO `survey` VALUES (38, 2, '问卷二', '计算机网络调研', 0, 0, NULL, NULL, 3, 0, NULL, '2023-06-26 19:22:55', '2023-06-27 17:36:27', NULL, 1);
INSERT INTO `survey` VALUES (39, 1, '问卷一', '操作系统课程满意度1', 0, 0, NULL, '15', 1, 0, NULL, '2023-06-27 17:16:35', '2023-06-27 21:46:38', NULL, 1);
INSERT INTO `survey` VALUES (40, 2, '问卷二', '计算机网络调研', 0, 0, NULL, NULL, 3, 0, NULL, '2023-06-27 17:36:28', '2023-06-27 17:37:33', NULL, 1);
INSERT INTO `survey` VALUES (41, 0, '计算机', '计算机满意度', 0, 0, NULL, NULL, 1, 0, NULL, '2023-06-27 17:49:05', '2023-06-27 17:49:27', NULL, 1);
INSERT INTO `survey` VALUES (42, 0, '计算机', '计算机满意度', 0, 0, NULL, NULL, 1, 0, NULL, '2023-06-27 17:49:27', '2023-06-27 19:24:47', NULL, 1);
INSERT INTO `survey` VALUES (43, 0, '计算机上', '计算机上的满意度', 0, 0, NULL, NULL, 1, 0, NULL, '2023-06-27 17:53:53', '2023-06-27 17:54:22', NULL, 1);
INSERT INTO `survey` VALUES (44, 0, '计算机上', '计算机上的满意度', 0, 1, NULL, NULL, 1, 0, NULL, '2023-06-27 17:54:22', '2023-06-27 19:32:25', NULL, 1);
INSERT INTO `survey` VALUES (45, 1, '问卷三', '操作系统课程满意度3', 0, 0, NULL, '15', 1, 0, NULL, '2023-06-27 21:29:06', '2023-06-27 21:46:41', NULL, 1);
INSERT INTO `survey` VALUES (46, 1, '问卷4', '操作系统课程满意度8', 0, 0, NULL, '15', 1, 0, NULL, '2023-06-27 21:31:42', '2023-06-27 21:46:44', NULL, 1);
INSERT INTO `survey` VALUES (47, 1, '问卷5', '操作系统课程满意度79', 0, 0, NULL, '15', 1, 0, NULL, '2023-06-27 21:34:28', '2023-06-27 21:46:47', NULL, 1);
INSERT INTO `survey` VALUES (48, 1, '455', '778', 0, 0, NULL, '15', 1, 0, NULL, '2023-06-27 21:47:52', '2023-06-27 21:48:31', NULL, 1);
INSERT INTO `survey` VALUES (49, 1, '1', '1', 0, 0, NULL, '15', 1, 0, NULL, '2023-06-27 21:49:20', '2023-06-27 21:53:30', NULL, 1);
INSERT INTO `survey` VALUES (50, 2, '2', '2', 0, 0, NULL, NULL, 5, 0, NULL, '2023-06-27 21:49:58', '2023-06-27 21:53:33', NULL, 1);
INSERT INTO `survey` VALUES (51, 3, '3', '3', 0, 0, NULL, NULL, 1, 0, NULL, '2023-06-27 21:50:42', '2023-06-27 21:53:36', NULL, 1);
INSERT INTO `survey` VALUES (52, 4, '4', '4', 0, 0, NULL, NULL, 1, 0, NULL, '2023-06-27 21:51:14', '2023-06-27 21:53:38', NULL, 1);
INSERT INTO `survey` VALUES (53, 1, '1', '1', 0, 0, NULL, '15', 1, 0, NULL, '2023-06-27 21:54:16', '2023-06-27 21:54:16', NULL, 0);
INSERT INTO `survey` VALUES (54, 2, '12', '2', 0, 0, NULL, NULL, 5, 0, NULL, '2023-06-27 21:54:51', '2023-06-27 21:54:51', NULL, 0);
INSERT INTO `survey` VALUES (55, 3, '3', '3', 0, 0, NULL, NULL, 1, 0, NULL, '2023-06-27 21:55:28', '2023-06-27 21:55:28', NULL, 0);
INSERT INTO `survey` VALUES (56, 4, '4', '4', 0, 0, NULL, NULL, 1, 0, NULL, '2023-06-27 21:55:54', '2023-06-27 21:55:54', NULL, 0);
INSERT INTO `survey` VALUES (57, 0, NULL, NULL, 0, 0, NULL, NULL, 1, 0, NULL, '2023-06-28 15:19:47', '2023-06-28 15:19:47', NULL, 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'zihao', '8bf75d25716494a5e1ae63de79db741a', 'ZhaoJingyang', '', 0, '', '', 0000000000, '2023-06-11 15:06:31', '2023-06-17 15:06:31', 0, 0);
INSERT INTO `user` VALUES (2, 'admin', '8bf75d25716494a5e1ae63de79db741a', 'admin', '', 0, NULL, NULL, 0000000000, '2023-06-11 15:07:35', '2023-06-11 15:06:31', 0, 1);
INSERT INTO `user` VALUES (3, 'wangyuan', '8bf75d25716494a5e1ae63de79db741a', 'JingXin', '', 0, '123', '123', 0000000001, '2023-06-11 15:08:12', '2023-06-11 15:06:31', 0, 0);
INSERT INTO `user` VALUES (4, '冰雪灬独舞', '8bf75d25716494a5e1ae63de79db741a', 'nene', '', 0, '123', '123', 0000000000, '2023-06-11 15:09:54', '2023-06-11 15:06:31', 1, 0);
INSERT INTO `user` VALUES (5, 'dingzhen', '8bf75d25716494a5e1ae63de79db741a', 'admin1', NULL, 0, NULL, NULL, 0000000000, '2023-06-26 19:12:32', '2023-06-26 19:12:32', 1, 0);

SET FOREIGN_KEY_CHECKS = 1;
