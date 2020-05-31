/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : 127.0.0.1:3306
 Source Schema         : ag_auth_v1

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 01/12/2019 14:55:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token`  (
  `token_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authentication` blob NULL,
  `refresh_token` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`authentication_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for oauth_approvals
-- ----------------------------
DROP TABLE IF EXISTS `oauth_approvals`;
CREATE TABLE `oauth_approvals`  (
  `userId` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `clientId` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `scope` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `expiresAt` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `lastModifiedAt` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0000-00-00 00:00:00'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `resource_ids` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_secret` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `scope` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorized_grant_types` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `web_server_redirect_uri` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorities` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `access_token_validity` int(11) NULL DEFAULT NULL,
  `refresh_token_validity` int(11) NULL DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `autoapprove` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('test', NULL, '$2a$10$Y31Y7qEwjVowsCEqTdPWieJwa7BVEavfUksfTXTRFAFn1bjKrMS.O', 'all', 'password,authorization_code', NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for oauth_client_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_token`;
CREATE TABLE `oauth_client_token`  (
  `token_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`authentication_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code`  (
  `code` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authentication` blob NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token`  (
  `token_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_authority
-- ----------------------------
DROP TABLE IF EXISTS `sys_authority`;
CREATE TABLE `sys_authority`  (
  `oid` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_id` bigint(20) NOT NULL COMMENT '父级',
  `order_num` int(10) NOT NULL COMMENT '角色标签',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `expression` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `add_user_id` bigint(20) NULL DEFAULT NULL,
  `add_user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `add_date_time` datetime(0) NULL DEFAULT NULL,
  `opt_user_id` bigint(20) NULL DEFAULT NULL,
  `opt_user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `opt_date_time` datetime(0) NULL DEFAULT NULL,
  `del_flg` tinyint(1) NOT NULL DEFAULT 0 COMMENT '数据有效标识 0-有效 1-无效',
  PRIMARY KEY (`oid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_authority
-- ----------------------------
INSERT INTO `sys_authority` VALUES (1, 1, 1, '1', '1', '1', 1110, 'admin', '2019-11-21 17:32:44', 1110, 'admin', '2019-11-21 17:33:19', 0);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `oid` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) NOT NULL COMMENT '父节点oid',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `href` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'href',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '登陆类型',
  `param` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数',
  `order_num` int(10) NOT NULL DEFAULT 0 COMMENT '序号',
  `add_user_id` bigint(20) NULL DEFAULT NULL,
  `add_user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `add_date_time` datetime(0) NULL DEFAULT NULL,
  `opt_user_id` bigint(20) NULL DEFAULT NULL,
  `opt_user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `opt_date_time` datetime(0) NULL DEFAULT NULL,
  `del_flg` tinyint(1) NOT NULL COMMENT '数据有效标识 0-有效 1-无效',
  PRIMARY KEY (`oid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2702 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (2402, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:16', 0);
INSERT INTO `sys_menu` VALUES (2403, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:16', 0);
INSERT INTO `sys_menu` VALUES (2404, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:16', 0);
INSERT INTO `sys_menu` VALUES (2405, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:16', 0);
INSERT INTO `sys_menu` VALUES (2406, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:16', 0);
INSERT INTO `sys_menu` VALUES (2407, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:16', 0);
INSERT INTO `sys_menu` VALUES (2408, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:16', 0);
INSERT INTO `sys_menu` VALUES (2409, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:16', 0);
INSERT INTO `sys_menu` VALUES (2410, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:16', 0);
INSERT INTO `sys_menu` VALUES (2411, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:16', 0);
INSERT INTO `sys_menu` VALUES (2412, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:16', 0);
INSERT INTO `sys_menu` VALUES (2413, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:16', 0);
INSERT INTO `sys_menu` VALUES (2414, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:16', 0);
INSERT INTO `sys_menu` VALUES (2415, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:16', 0);
INSERT INTO `sys_menu` VALUES (2416, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:16', 0);
INSERT INTO `sys_menu` VALUES (2417, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:16', 0);
INSERT INTO `sys_menu` VALUES (2418, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:16', 0);
INSERT INTO `sys_menu` VALUES (2419, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:16', 0);
INSERT INTO `sys_menu` VALUES (2420, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:16', 0);
INSERT INTO `sys_menu` VALUES (2421, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:16', 0);
INSERT INTO `sys_menu` VALUES (2422, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:16', 0);
INSERT INTO `sys_menu` VALUES (2423, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:16', 0);
INSERT INTO `sys_menu` VALUES (2424, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:16', 0);
INSERT INTO `sys_menu` VALUES (2425, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2426, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2427, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2428, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2429, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2430, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2431, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2432, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2433, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2434, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2435, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2436, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2437, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2438, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2439, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2440, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2441, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2442, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2443, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2444, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2445, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2446, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2447, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2448, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2449, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2450, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2451, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2452, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2453, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2454, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2455, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2456, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2457, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2458, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2459, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2460, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2461, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2462, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2463, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2464, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2465, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2466, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2467, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2468, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2469, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2470, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2471, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2472, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2473, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2474, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2475, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2476, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2477, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2478, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2479, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2480, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2481, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2482, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2483, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2484, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2485, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2486, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2487, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2488, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2489, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2490, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2491, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2492, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2493, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2494, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2495, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2496, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2497, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2498, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2499, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2500, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2501, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2502, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2503, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2504, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2505, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2506, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2507, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2508, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2509, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2510, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2511, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2512, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2513, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2514, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2515, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2516, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2517, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2518, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2519, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2520, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2521, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2522, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2523, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2524, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2525, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2526, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2527, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2528, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2529, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2530, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2531, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2532, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2533, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2534, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2535, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2536, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2537, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2538, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2539, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2540, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2541, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2542, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2543, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2544, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2545, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2546, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2547, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2548, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2549, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2550, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2551, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2552, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2553, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2554, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2555, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2556, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2557, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2558, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2559, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2560, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2561, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2562, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2563, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2564, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2565, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2566, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2567, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2568, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2569, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2570, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2571, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2572, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2573, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2574, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2575, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2576, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2577, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2578, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2579, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2580, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2581, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2582, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2583, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2584, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2585, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2586, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2587, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2588, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2589, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2590, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2591, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2592, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2593, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2594, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2595, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2596, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2597, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2598, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2599, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2600, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);
INSERT INTO `sys_menu` VALUES (2601, 0, 'ssssssssa', '', '', '', 1, 0, 'anonymous', '2019-11-28 17:57:33', 0, 'anonymous', '2019-11-28 18:24:17', 0);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `oid` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) NOT NULL COMMENT '父级',
  `label` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色标签',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `remake` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `add_user_id` bigint(20) NULL DEFAULT NULL,
  `add_user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `add_date_time` datetime(0) NULL DEFAULT NULL,
  `opt_user_id` bigint(20) NULL DEFAULT NULL,
  `opt_user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `opt_date_time` datetime(0) NULL DEFAULT NULL,
  `del_flg` tinyint(1) NOT NULL COMMENT '数据有效标识 0-有效 1-无效',
  PRIMARY KEY (`oid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `oid` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号名',
  `password` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `usable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '可用状态： 0 - 不可用, 1 - 可用',
  `login_type` tinyint(1) NOT NULL DEFAULT -1 COMMENT '登陆类型',
  `user_type` tinyint(1) NOT NULL DEFAULT -1 COMMENT '账号类型',
  `page_limit` int(10) NOT NULL DEFAULT 20 COMMENT '分页行数 默认20',
  `user_nick` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `sex` tinyint(1) NOT NULL DEFAULT 0 COMMENT '性别 0 - 男 1- 女',
  `telephone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `email_address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `add_user_id` bigint(20) NULL DEFAULT NULL,
  `add_user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `add_date_time` datetime(0) NULL DEFAULT NULL,
  `opt_user_id` bigint(20) NULL DEFAULT NULL,
  `opt_user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `opt_date_time` datetime(0) NULL DEFAULT NULL,
  `del_flg` tinyint(1) NOT NULL DEFAULT 0 COMMENT '数据有效标识 0-有效 1-无效',
  PRIMARY KEY (`oid`) USING BTREE,
  UNIQUE INDEX `index_un_id_user_user_name`(`user_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$Y31Y7qEwjVowsCEqTdPWieJwa7BVEavfUksfTXTRFAFn1bjKrMS.O', 1, -1, -1, 20, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
