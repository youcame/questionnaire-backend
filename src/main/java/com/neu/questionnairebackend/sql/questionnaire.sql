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

 Date: 14/10/2023 16:51:19
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
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of answersheet
-- ----------------------------
INSERT INTO `answersheet` VALUES (21, 2, 'admin', 109, 7, '2');
INSERT INTO `answersheet` VALUES (22, 2, 'admin', 110, 7, '1');
INSERT INTO `answersheet` VALUES (23, 6, 'nene1', 109, 7, '1');
INSERT INTO `answersheet` VALUES (24, 6, 'nene1', 110, 7, '1');

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
) ENGINE = InnoDB AUTO_INCREMENT = 260 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of choices
-- ----------------------------
INSERT INTO `choices` VALUES (257, 109, NULL, '包假', NULL, 0);
INSERT INTO `choices` VALUES (258, 109, NULL, '1！5！', NULL, 0);
INSERT INTO `choices` VALUES (259, 110, NULL, '不看mygo导致的', NULL, 0);
INSERT INTO `choices` VALUES (260, 110, NULL, '没mygo看导致的', NULL, 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES (1, '项目一', '计算机视觉', 1, 'admin', '2023-06-27 17:08:53', '2023-06-27 17:08:53', 'admin');
INSERT INTO `project` VALUES (2, '项目二', '操作系统', 1, 'admin', '2023-06-27 17:09:08', '2023-06-27 17:09:08', 'admin');
INSERT INTO `project` VALUES (3, '项目三', '数值分析', 1, 'admin', '2023-06-27 17:09:26', '2023-06-27 17:09:26', 'admin');
INSERT INTO `project` VALUES (4, '项目四', '计算机网络', 1, 'admin', '2023-06-27 17:09:42', '2023-06-27 17:09:42', 'admin');

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
) ENGINE = InnoDB AUTO_INCREMENT = 110 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES (109, 7, '顶针？', 0, NULL, NULL);
INSERT INTO `question` VALUES (110, 7, '5！1？', 0, NULL, NULL);

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
                           `projectId` int NULL DEFAULT NULL COMMENT '属于的项目Id',
                           `canFinishTime` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '能完成的时间（分钟）',
                           `totalTimes` int NULL DEFAULT 1 COMMENT '一共能够完成几次',
                           `nowTimes` int NULL DEFAULT 0 COMMENT '现在完成了几次',
                           `canFinishUserId` bigint NULL DEFAULT NULL COMMENT '能完成的用户id',
                           `createTime` timestamp NOT NULL COMMENT '创建时间',
                           `updateTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           `deleteTime` datetime NULL DEFAULT NULL COMMENT '删除时间',
                           `isDelete` int NOT NULL DEFAULT 0 COMMENT '逻辑删除',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of survey
-- ----------------------------
INSERT INTO `survey` VALUES (7, 1, 'test', 'it is a test ', 0, 0, NULL, 3, '15', 1, 0, NULL, '2023-10-12 19:29:51', '2023-10-14 16:46:47', NULL, 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'wangyuan', '8bf75d25716494a5e1ae63de79db741a', 'wangyuan', '', 0, '', '', 0000000000, '2023-06-11 15:06:31', '2023-06-17 15:06:31', 0, 1);
INSERT INTO `user` VALUES (2, 'admin', '8bf75d25716494a5e1ae63de79db741a', 'admin', '', 0, NULL, NULL, 0000000000, '2023-06-11 15:07:35', '2023-06-11 15:06:31', 0, 1);
INSERT INTO `user` VALUES (3, 'ch1cken', '8bf75d25716494a5e1ae63de79db741a', 'ch1cken', '', 0, '123', '123', 0000000001, '2023-06-11 15:08:12', '2023-06-11 15:06:31', 0, 1);
INSERT INTO `user` VALUES (4, '冰雪灬独舞', '8bf75d25716494a5e1ae63de79db741a', 'nene', '', 0, '123', '123', 0000000000, '2023-06-11 15:09:54', '2023-06-11 15:06:31', 0, 1);
INSERT INTO `user` VALUES (5, 'dingzhen', '8bf75d25716494a5e1ae63de79db741a', 'dingzhen', NULL, 0, NULL, NULL, 0000000000, '2023-06-26 19:12:32', '2023-06-26 19:12:32', 0, 1);

SET FOREIGN_KEY_CHECKS = 1;
