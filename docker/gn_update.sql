/*
 Navicat Premium Data Transfer

 Source Server         : ucloud
 Source Server Type    : MySQL
 Source Server Version : 50650
 Source Host           : 106.75.115.170:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50650
 File Encoding         : 65001

 Date: 27/01/2021 22:09:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for base_air_route
-- ----------------------------
DROP TABLE IF EXISTS `base_air_route`;
CREATE TABLE `base_air_route`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `airline` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '航司',
  `dep_airport` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '出发地',
  `arr_airport` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '目的地',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '生效日期 yyyy-mm-dd，新增时默认取当天',
  `end_time` datetime(0) NULL DEFAULT '2099-12-31 00:00:00' COMMENT '失效日期 yyyy-mm-dd，新增时默认取2099-12-31',
  `version` int(12) NOT NULL DEFAULT 0 COMMENT '版本控制',
  `remark` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注说明',
  `sort_seq` int(10) NULL DEFAULT 0 COMMENT '优先排序序号',
  `status` tinyint(2) NULL DEFAULT 1 COMMENT '状态：0-正常,1-暂停(挂起),99-无效(删除)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `create_user_id` bigint(20) NULL DEFAULT 0 COMMENT '创建人ID',
  `create_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `update_user_id` bigint(20) NULL DEFAULT 0 COMMENT '修改人ID',
  `update_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UIDX_MAR_AIR_ARIPORT`(`airline`, `dep_airport`, `arr_airport`) USING BTREE,
  INDEX `IDX_MAR_AIRLINE`(`airline`) USING BTREE,
  INDEX `IDX_MAR_DEP_ARIPORT`(`dep_airport`) USING BTREE,
  INDEX `IDX_MAR_ARR_ARIPORT`(`arr_airport`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 144 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '政策基础航司航线' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of base_air_route
-- ----------------------------
INSERT INTO `base_air_route` VALUES (1, 'KA', 'JFL', 'BJS', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 0, '2020-11-20 11:03:41', '2020-11-24 11:32:27', 1, 'admin', 1, 'admin');
INSERT INTO `base_air_route` VALUES (2, 'TG', 'TYO', 'TAO', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-11-20 11:04:10', '2020-11-24 11:30:10', 1, 'admin', 1, 'admin');
INSERT INTO `base_air_route` VALUES (3, 'CX', 'RARAE', 'CKK', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-11-20 14:12:03', '2020-11-24 11:22:41', 1, 'admin', 1, 'admin');
INSERT INTO `base_air_route` VALUES (4, 'TG', 'TRT3', '43R', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 0, '2020-11-20 14:13:56', '2020-11-20 14:15:43', 1, 'admin', 1, 'admin');
INSERT INTO `base_air_route` VALUES (9, 'KK', 'JFL', 'BJS', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-11-21 13:44:41', '2020-11-21 15:12:17', 1, 'admin', 1, 'admin');
INSERT INTO `base_air_route` VALUES (10, 'TT', 'TYO', 'TAO', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-11-21 13:44:41', '2020-11-21 15:12:17', 1, 'admin', 1, 'admin');
INSERT INTO `base_air_route` VALUES (11, 'CC', 'MKK', 'CKK', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-11-21 13:44:41', '2020-11-21 15:12:12', 1, 'admin', 1, 'admin');
INSERT INTO `base_air_route` VALUES (12, 'KQ', 'JFL', 'BJS', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-11-21 13:52:52', '2020-11-21 15:12:12', 1, 'admin', 1, 'admin');
INSERT INTO `base_air_route` VALUES (22, 'KL', 'BKK', 'NKL', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-11-23 15:06:08', '2020-11-24 11:22:32', 1, 'admin', 1, 'admin');
INSERT INTO `base_air_route` VALUES (34, '7C', 'TYO', 'UVL', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 0, '2020-11-25 09:01:39', '2020-11-25 09:34:41', 1, 'admin', 1, 'admin');
INSERT INTO `base_air_route` VALUES (45, 'TG', 'YVR', 'HTH', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-11-25 09:04:04', '2020-11-25 09:34:41', 1, 'admin', 1, 'admin');
INSERT INTO `base_air_route` VALUES (46, '9C', 'BJS', 'HKG', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-11-25 09:22:39', '2020-12-02 17:43:30', 1, 'admin', 1, 'admin');
INSERT INTO `base_air_route` VALUES (51, '7C', 'BJS', 'HKG', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-11-25 09:35:40', '2020-11-25 11:04:17', 1, 'admin', 1, 'admin');
INSERT INTO `base_air_route` VALUES (59, 'FL', 'BJS', 'HKG', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-11-25 09:38:21', '2020-12-02 17:43:30', 1, 'admin', 1, 'admin');
INSERT INTO `base_air_route` VALUES (61, 'CX', 'HKGYR', 'WWZ', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-11-25 09:51:51', '2020-12-02 17:43:30', 1, 'admin', 1, 'admin');
INSERT INTO `base_air_route` VALUES (64, 'TG', 'YVR', 'YYU', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-11-25 09:51:51', '2020-12-02 17:43:30', 1, 'admin', 1, 'admin');
INSERT INTO `base_air_route` VALUES (66, '9C', 'FUK', 'BJS', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-11-25 16:22:54', '2020-12-02 17:43:30', 1, 'admin', 1, 'admin');
INSERT INTO `base_air_route` VALUES (67, '', '', '', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-11-26 13:28:28', '2020-12-02 17:09:01', 1, 'admin', 1, 'admin');
INSERT INTO `base_air_route` VALUES (68, '9C', 'FUK', 'HKF', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-11-26 14:26:22', '2020-12-02 17:43:30', 1, 'admin', 1, 'admin');
INSERT INTO `base_air_route` VALUES (89, 'HB', 'TYJ', 'WEC', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-12-02 16:54:11', '2020-12-02 17:09:09', 1, 'admin', 1, 'admin');
INSERT INTO `base_air_route` VALUES (140, 'KA', 'HKG', 'BJS', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 0, '2020-12-15 10:07:09', '2020-12-15 10:07:09', 1, 'admin', 1, 'admin');
INSERT INTO `base_air_route` VALUES (141, 'CX', 'HKG', 'WWZ', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 0, '2020-12-15 10:07:09', '2020-12-15 10:07:09', 1, 'admin', 1, 'admin');
INSERT INTO `base_air_route` VALUES (142, 'JL', 'HGF', 'TAO', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 0, '2020-12-15 10:07:09', '2020-12-15 10:07:09', 1, 'admin', 1, 'admin');
INSERT INTO `base_air_route` VALUES (143, 'TG', 'BJS', 'HKG', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 0, '2020-12-15 10:07:09', '2020-12-15 10:07:09', 1, 'admin', 1, 'admin');

-- ----------------------------
-- Table structure for base_cabin
-- ----------------------------
DROP TABLE IF EXISTS `base_cabin`;
CREATE TABLE `base_cabin`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `airline` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '航司',
  `cabin_grade` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '舱等',
  `cabin` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '子舱位',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '生效日期 yyyy-mm-dd，新增时默认取当天',
  `end_time` datetime(0) NULL DEFAULT '2099-12-31 00:00:00' COMMENT '失效日期 yyyy-mm-dd，新增时默认取2099-12-31',
  `version` int(12) NOT NULL DEFAULT 0 COMMENT '版本控制',
  `remark` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注说明',
  `sort_seq` int(10) NULL DEFAULT 0 COMMENT '优先排序序号',
  `status` tinyint(2) NULL DEFAULT 1 COMMENT '状态：0-正常,1-暂停(挂起),99-无效(删除)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `create_user_id` bigint(20) NULL DEFAULT 0 COMMENT '创建人ID',
  `create_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `update_user_id` bigint(20) NULL DEFAULT 0 COMMENT '修改人ID',
  `update_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `IDX_MBC_ACG`(`airline`, `cabin_grade`) USING BTREE,
  INDEX `IDX_MBC_AIRLINE`(`airline`) USING BTREE,
  INDEX `IDX_MBC_CABIN_GRADE`(`cabin_grade`) USING BTREE,
  INDEX `IDX_MBC_CABIN`(`cabin`(255)) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 176 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '政策基础舱位' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of base_cabin
-- ----------------------------
INSERT INTO `base_cabin` VALUES (20, 'CX', 'B', 'J/OP/A/B/Y', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-11-26 13:55:43', '2020-12-05 15:05:18', 1, 'admin', 1, 'admin');
INSERT INTO `base_cabin` VALUES (21, 'KG', 'F', 'U/M/Y', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-11-26 13:55:44', '2020-12-05 15:05:18', 1, 'admin', 1, 'admin');
INSERT INTO `base_cabin` VALUES (22, 'JK', 'P', 'B/M/C/D', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-11-26 13:55:44', '2020-12-02 17:16:40', 1, 'admin', 1, 'admin');
INSERT INTO `base_cabin` VALUES (23, 'CX', 'F', 'U/L/V/J', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-11-26 13:55:44', '2020-12-02 17:16:40', 1, 'admin', 1, 'admin');
INSERT INTO `base_cabin` VALUES (24, 'KA', 'F', 'U/M', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-11-26 13:55:44', '2020-12-02 17:16:40', 1, 'admin', 1, 'admin');
INSERT INTO `base_cabin` VALUES (27, 'TG', 'F', 'U/M', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-11-26 13:55:44', '2020-12-02 17:16:40', 1, 'admin', 1, 'admin');
INSERT INTO `base_cabin` VALUES (68, 'TG', 'C', 'A/S/D/F/G/H/J/K/L/U', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 0, '2020-12-03 14:18:49', '2020-12-28 09:46:38', 1, 'admin', 1, 'admin');
INSERT INTO `base_cabin` VALUES (69, 'TG', 'B', 'A/S/D/F/G/H/J/K/L/U', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 0, '2020-12-03 14:18:49', '2020-12-28 09:46:35', 1, 'admin', 1, 'admin');
INSERT INTO `base_cabin` VALUES (70, 'K1', 'U', 'W1/Z2', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-12-03 14:18:49', '2020-12-05 15:05:15', 1, 'admin', 1, 'admin');
INSERT INTO `base_cabin` VALUES (71, 'K2', 'U', 'W1/Z3', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-12-03 14:18:49', '2020-12-05 15:05:15', 1, 'admin', 1, 'admin');
INSERT INTO `base_cabin` VALUES (72, 'K3', 'U', 'W1/Z4', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-12-03 14:18:49', '2020-12-05 15:05:15', 1, 'admin', 1, 'admin');
INSERT INTO `base_cabin` VALUES (73, 'K4', 'U', 'W1/Z5', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-12-03 14:18:50', '2020-12-05 15:05:15', 1, 'admin', 1, 'admin');
INSERT INTO `base_cabin` VALUES (74, 'K5', 'U', 'W1/Z6', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-12-03 14:18:50', '2020-12-05 15:05:15', 1, 'admin', 1, 'admin');
INSERT INTO `base_cabin` VALUES (75, 'K6', 'U', 'W1/Z7', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-12-03 14:18:50', '2020-12-05 15:05:15', 1, 'admin', 1, 'admin');
INSERT INTO `base_cabin` VALUES (76, 'K7', 'U', 'W1/Z8', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-12-03 14:18:50', '2020-12-05 15:05:15', 1, 'admin', 1, 'admin');
INSERT INTO `base_cabin` VALUES (77, 'K8', 'U', 'W1/Z9', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-12-03 14:18:50', '2020-12-05 15:05:15', 1, 'admin', 1, 'admin');
INSERT INTO `base_cabin` VALUES (78, 'K9', 'U', 'W1/Z10', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-12-03 14:18:50', '2020-12-05 15:05:15', 1, 'admin', 1, 'admin');
INSERT INTO `base_cabin` VALUES (79, 'KR', 'U', 'W1/Z11', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-12-03 14:18:50', '2020-12-05 15:05:15', 1, 'admin', 1, 'admin');
INSERT INTO `base_cabin` VALUES (80, 'KF', 'U', 'W1/Z12', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 99, '2020-12-03 14:18:50', '2020-12-05 15:05:15', 1, 'admin', 1, 'admin');
INSERT INTO `base_cabin` VALUES (122, 'KA', 'U', 'W1/Z2', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 0, '2020-12-05 15:04:58', '2020-12-28 09:46:33', 1, 'admin', 1, 'admin');
INSERT INTO `base_cabin` VALUES (123, 'KL', 'Y', 'C3', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 0, '2020-12-05 15:04:58', '2020-12-28 09:46:28', 1, 'admin', 1, 'admin');
INSERT INTO `base_cabin` VALUES (124, 'KA', 'B', 'K/Z/V/L', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 0, '2020-12-05 15:04:58', '2020-12-28 09:46:26', 1, 'admin', 1, 'admin');
INSERT INTO `base_cabin` VALUES (125, 'KL', 'O', 'O44', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 0, '2020-12-05 15:04:58', '2020-12-05 15:16:50', 1, 'admin', 1, 'admin');
INSERT INTO `base_cabin` VALUES (126, 'LK', 'O', 'O445', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 0, '2020-12-05 15:04:58', '2020-12-28 09:46:20', 1, 'admin', 1, 'admin');
INSERT INTO `base_cabin` VALUES (168, 'ER', 'O', 'O447', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 0, '2020-12-05 15:16:50', '2020-12-28 09:46:16', 1, 'admin', 1, 'admin');
INSERT INTO `base_cabin` VALUES (169, 'WE', 'O', 'O44', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 0, '2020-12-05 15:16:50', '2020-12-28 09:46:13', 1, 'admin', 1, 'admin');
INSERT INTO `base_cabin` VALUES (173, '3U', 'M', 'W1/A/B/C/K/H/F/A/Z/UC/F/K', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 0, '2020-12-17 09:38:23', '2020-12-28 09:46:11', 1, 'admin', 1, 'admin');
INSERT INTO `base_cabin` VALUES (174, 'QW', 'F', 'Z/H/H1/B1/B/W/Q/F/P', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 0, '2020-12-17 11:29:26', '2020-12-28 09:46:08', 1, 'admin', 1, 'admin');
INSERT INTO `base_cabin` VALUES (175, 'QW', 'M', 'P/U/V/L/Y', NULL, '2099-12-31 00:00:00', 1, NULL, 0, 0, '2020-12-17 11:30:41', '2020-12-28 09:46:05', 1, 'admin', 1, 'admin');

-- ----------------------------
-- Table structure for site_config
-- ----------------------------
DROP TABLE IF EXISTS `site_config`;
CREATE TABLE `site_config`  (
  `ota_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'OTA平台代码 CTRIP-携程 FLIGGY-飞猪 QUNAR-去哪儿 LY-同程 WAP133-航班管家 JIULVXING-就旅行 MEITUAN-美团',
  `ota_code_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'OTA平台中文',
  `ota_site_code` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'OTA站点代码',
  `ota_site_code_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'OTA站点中文',
  `api_account_info` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '{`api_user_name`,`api_password`,`api_api_key`,`api_channel_id`,`api_result_url`,`do_main_url`}',
  `ota_max_policy` int(11) NULL DEFAULT NULL COMMENT '最大推送的政策数量单位 万',
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `version` int(12) NOT NULL DEFAULT 0 COMMENT '版本控制',
  `remark` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注说明',
  `sort_seq` int(10) NULL DEFAULT 0 COMMENT '优先排序序号',
  `status` tinyint(2) NULL DEFAULT 0 COMMENT '状态：0-正常,1-暂停(挂起),99-无效(删除)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `create_user_id` bigint(20) NULL DEFAULT 0 COMMENT '创建人ID',
  `create_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `update_user_id` bigint(20) NULL DEFAULT 0 COMMENT '修改人ID',
  `update_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UIDX_MSC_OTA_CODE_SITE_CODE`(`ota_code`, `ota_site_code`) USING BTREE,
  INDEX `IDX_MSC_OTA_CODE`(`ota_code`) USING BTREE,
  INDEX `IDX_MSC_OTA_SITE_CODE`(`ota_site_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '政策站点配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of site_config
-- ----------------------------
INSERT INTO `site_config` VALUES ('CTRIP', '携程', 'CT-MLPT', '莫林普通', '[{\"apiUrl\":\"https://exchdata.ctrip.com/Flight-Proxy-TradeQuickAPI/api/json\",\"apiUserName\":\"外部测试1\",\"apiPassword\":\"Ctrip@2014Test\"}]', NULL, 3, 0, NULL, 0, 0, NULL, '2020-12-08 10:56:48', 0, NULL, 0, 'admin');
INSERT INTO `site_config` VALUES ('CTRIP', '携程', 'CT-XKLXW', '逍客旅行网', 'CT-XKLXW', NULL, 4, 0, NULL, 0, 0, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `site_config` VALUES ('CTRIP', '携程', 'CT-XKFS', '逍客飞宿', 'CT-XKFS', NULL, 5, 0, NULL, 0, 0, NULL, '2020-12-23 15:34:03', 0, NULL, 0, 'admin');
INSERT INTO `site_config` VALUES ('CTRIP', '携程', 'CT-DFXY', '东方祥云', 'CT-DFXY', NULL, 6, 0, NULL, 0, 1, NULL, '2020-12-23 16:02:07', 0, NULL, 0, 'admin');
INSERT INTO `site_config` VALUES ('CTRIP', '携程', 'CT-SZML', '深圳莫林', 'CT-SZML', NULL, 7, 0, NULL, 0, 1, NULL, '2020-12-17 16:13:13', 0, NULL, 0, 'admin');
INSERT INTO `site_config` VALUES ('CTRIP', '携程', 'CT-XSZML', '新深圳莫林', 'CT-XSZML', NULL, 8, 0, NULL, 0, 1, NULL, '2020-12-17 16:13:18', 0, NULL, 0, 'admin');
INSERT INTO `site_config` VALUES ('CTRIP', '携程', 'CT-MLFX', '莫林分销', 'CT-MLFX', NULL, 9, 0, NULL, 0, 0, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `site_config` VALUES ('FLIGGY', '飞猪', 'FL-MLHF', '腾邦莫林航服', 'FL-MLHF', NULL, 10, 0, NULL, 0, 0, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `site_config` VALUES ('FLIGGY', '飞猪', 'FL-MLSL', '腾邦莫林商旅', 'FL-MLSL', NULL, 11, 0, NULL, 0, 0, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `site_config` VALUES ('FLIGGY', '飞猪', 'FL-XKHK', '逍客航空', 'FL-XKHK', NULL, 12, 0, NULL, 0, 0, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `site_config` VALUES ('FLIGGY', '飞猪', 'FL-DFXY', '东方祥云航空', 'FL-DFXY', NULL, 13, 0, NULL, 0, 0, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `site_config` VALUES ('QUNAR', '去哪儿', 'QU-XKTTS', '逍客TTS', '[{\"apiUrl\":\"http://xkw.trade.qunar.com/tts/interface/policy.jsp\",\"apiUserName\":\"ttt\",\"apiPassword\":\"yyyy2131\"}]', NULL, 14, 0, NULL, 0, 1, NULL, '2020-12-21 13:45:00', 0, NULL, 0, 'admin');
INSERT INTO `site_config` VALUES ('QUNAR', '去哪儿', 'QU-XYTTS', '祥云TTS', '[{\"apiUserName\":\"ttt\",\"apiPassword\":\"yyyy2131\"}]', NULL, 15, 0, NULL, 0, 1, NULL, '2020-12-21 15:43:19', 0, NULL, 0, 'admin');
INSERT INTO `site_config` VALUES ('QUNAR', '去哪儿', 'QU-XKFX', '逍客分销', 'QU-XKFX', NULL, 16, 0, NULL, 0, 0, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `site_config` VALUES ('QUNAR', '去哪儿', 'QU-JQLFX', '金麒麟分销', '[{\"apiUrl\":\"http://xkw.trade.qunar.com/tts/interface/policy.jsp\",\"apiUserName\":\"ttt\",\"apiPassword\":\"yyyy2131\"}]', NULL, 17, 0, NULL, 0, 0, NULL, '2020-12-22 14:19:39', 0, NULL, 0, 'admin');
INSERT INTO `site_config` VALUES ('LY', '同程', 'LY-MLHK', '莫林航空', 'LY-MLHK', NULL, 18, 0, NULL, 0, 0, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `site_config` VALUES ('LY', '同程', 'LY-MLSL', '莫林商旅', 'LY-MLSL', NULL, 19, 0, NULL, 0, 0, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `site_config` VALUES ('LY', '同程', 'LY-XKSL', '逍客商旅', 'LY-XKSL', NULL, 20, 0, NULL, 0, 0, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `site_config` VALUES ('LY', '同程', 'LY-DFXY', '东方祥云', 'LY-DFXY', NULL, 21, 0, NULL, 0, 0, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `site_config` VALUES ('JIULVXING', '就旅行', 'JI-DBML', '腾邦莫林', 'JI-DBML', NULL, 22, 0, NULL, 0, 0, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `site_config` VALUES ('MEITUAN', '美团', 'ME-XKSL', '逍客商旅', 'ME-XKSL', NULL, 23, 0, NULL, 0, 0, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `site_config` VALUES ('WAP133', '航管', 'WA-MLHK', '莫林航空', 'WA-MLHK', 1, 24, 0, NULL, 0, 0, NULL, '2020-12-05 16:01:51', 0, NULL, 0, 'admin');

-- ----------------------------
-- Table structure for site_rules_switch
-- ----------------------------
DROP TABLE IF EXISTS `site_rules_switch`;
CREATE TABLE `site_rules_switch`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `parameter_key` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '键',
  `parameter_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数名称',
  `parameter_value` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '值',
  `remark` varchar(600) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注说明',
  `status` tinyint(2) NOT NULL DEFAULT 1 COMMENT '状态：0-无效(删除),1-有效(正常),2-暂停',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `create_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '创建人ID',
  `create_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `update_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '修改人ID',
  `update_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `group_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对规则开关进行分组',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '站点规则开关' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of site_rules_switch
-- ----------------------------
INSERT INTO `site_rules_switch` VALUES (1, 'CT001', '携程一部开关', 'TRUE', NULL, 0, '2020-10-01 22:02:45', '2020-10-01 22:02:45', 1, 'admin', 1, 'admin', 'OTA_SITE_SWITCH_CT001');
INSERT INTO `site_rules_switch` VALUES (3, 'OTA-12', '携程一部中转连接过滤规则开关', 'TRUE', NULL, 0, '2020-10-01 22:28:00', '2020-10-01 22:28:00', 1, 'admin', 1, 'admin', 'OTA_SITE_SWITCH_CT001');
INSERT INTO `site_rules_switch` VALUES (4, 'OTA-10', '携程一部中转时间过滤', 'TRUE', NULL, 0, '2020-10-01 22:28:49', '2020-10-01 22:28:49', 1, 'admin', 1, 'admin', 'OTA_SITE_SWITCH_CT001');
INSERT INTO `site_rules_switch` VALUES (5, 'OTA-13', '携程一部限制共享航司规则开关', 'TRUE', NULL, 0, '2020-10-01 22:29:51', '2020-10-01 22:29:51', 1, 'admin', 1, 'admin', 'OTA_SITE_SWITCH_CT001');
INSERT INTO `site_rules_switch` VALUES (6, 'OTA-14', '携程一部限制混合航司规则开关', 'TRUE', NULL, 0, '2020-10-01 22:31:30', '2020-10-01 22:31:30', 1, 'admin', 1, 'admin', 'OTA_SITE_SWITCH_CT001');
INSERT INTO `site_rules_switch` VALUES (7, 'OTA-16', '携程一部是否共享航司为空规则开关', 'TRUE', NULL, 0, '2020-10-01 22:32:58', '2020-10-01 22:32:58', 1, 'admin', 1, 'admin', 'OTA_SITE_SWITCH_CT001');
INSERT INTO `site_rules_switch` VALUES (8, 'OTA-9', '携程一部限制中转次数规则开关', 'TRUE', NULL, 0, '2020-10-01 22:34:49', '2020-10-01 22:34:49', 1, 'admin', 1, 'admin', 'OTA_SITE_SWITCH_CT001');
INSERT INTO `site_rules_switch` VALUES (9, 'OTA-33', '携程一部航司航线黑名单规则开关', 'TRUE', NULL, 0, '2020-10-01 22:36:14', '2020-10-01 22:36:14', 1, 'admin', 1, 'admin', 'OTA_SITE_SWITCH_CT001');
INSERT INTO `site_rules_switch` VALUES (10, '1A', '1A开关', 'TRUE', NULL, 0, '2020-10-01 22:41:07', '2020-10-01 22:41:07', 1, 'admin', 1, 'admin', 'GDS_SWITCH');
INSERT INTO `site_rules_switch` VALUES (11, '1G', '1G开关', 'TRUE', NULL, 0, '2020-10-01 22:41:52', '2020-10-01 22:41:52', 1, 'admin', 1, 'admin', 'GDS_SWITCH');
INSERT INTO `site_rules_switch` VALUES (12, 'GDS-7', 'GDS航线黑名单规则开关', 'TRUE', NULL, 0, '2020-10-01 22:43:19', '2020-10-01 22:43:19', 1, 'admin', 1, 'admin', 'GDS_SWITCH');
INSERT INTO `site_rules_switch` VALUES (13, 'GDS-11', 'GDS限制方案数规则开关', 'TRUE', NULL, 0, '2020-10-01 22:44:09', '2020-10-01 22:44:09', 1, 'admin', 1, 'admin', 'GDS_SWITCH');
INSERT INTO `site_rules_switch` VALUES (14, 'GDS-4', 'GDS航线航司黑名单规则开关', 'TRUE', NULL, 0, '2020-10-01 22:45:02', '2020-10-01 22:45:02', 1, 'admin', 1, 'admin', 'GDS_SWITCH');
INSERT INTO `site_rules_switch` VALUES (15, 'GDS-30', 'GDS航线航司白名单规则开关', 'TRUE', NULL, 0, '2020-10-01 22:46:05', '2020-10-01 22:46:05', 1, 'admin', 1, 'admin', 'GDS_SWITCH');
INSERT INTO `site_rules_switch` VALUES (16, 'GDS-28', 'GDS航司指定GDS来源规则开关', 'TRUE', NULL, 0, '2020-10-01 22:46:59', '2020-10-01 22:46:59', 1, 'admin', 1, 'admin', 'GDS_SWITCH');
INSERT INTO `site_rules_switch` VALUES (17, 'GDS-18', 'GDS航司舱位黑名单时间规则开关', 'TRUE', NULL, 0, '2020-10-01 22:47:53', '2020-10-01 22:47:53', 1, 'admin', 1, 'admin', 'GDS_SWITCH');
INSERT INTO `site_rules_switch` VALUES (18, 'CURRENCY', '携程一部币种', 'CNY', NULL, 0, '2020-10-02 01:49:17', '2020-10-02 01:49:17', 1, 'admin', 1, 'admin', 'OTA_SITE_SWITCH_CT001');
INSERT INTO `site_rules_switch` VALUES (21, 'GDS-25', 'GDS缓存生效时间规则开关配置', 'TRUE', NULL, 0, '2020-10-04 11:15:02', '2020-10-04 11:15:02', 1, 'admin', 1, 'admin', 'GDS_SWITCH');
INSERT INTO `site_rules_switch` VALUES (23, 'OTA-24', '限制缓存时间', 'TRUE', NULL, 0, '2020-10-04 11:52:23', '2020-10-04 11:52:23', 1, 'admin', 1, 'admin', 'OTA_SITE_SWITCH_CT001');
INSERT INTO `site_rules_switch` VALUES (24, 'OTA-22', 'OTA-限制请求超时规则开关', 'TRUE', '暂时没用到', 0, '2020-10-04 12:28:33', '2020-10-04 12:28:33', 1, 'admin', 1, 'admin', 'OTA_SITE_SWITCH_CT001');
INSERT INTO `site_rules_switch` VALUES (25, 'OTA-26', 'OTA-限制止损规则开关', 'TRUE', NULL, 0, '2020-10-04 12:31:50', '2020-10-04 12:31:50', 1, 'admin', 1, 'admin', 'OTA_SITE_SWITCH_CT001');
INSERT INTO `site_rules_switch` VALUES (27, 'OTA-35', 'OTA航线GDS选择规则开关', 'TRUE', NULL, 0, '2020-10-04 12:36:32', '2020-10-04 12:36:32', 1, 'admin', 1, 'admin', 'OTA_SITE_SWITCH_CT001');
INSERT INTO `site_rules_switch` VALUES (30, 'CURRENCY', '携程二部币种', 'CNY', NULL, 0, '2020-10-12 13:30:34', '2020-10-12 13:30:34', 1, 'admin', 1, 'admin', 'OTA_SITE_SWITCH_CT002');

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `dict_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(4) NULL DEFAULT 0 COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典数据表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '性别男');
INSERT INTO `sys_dict_data` VALUES (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '性别女');
INSERT INTO `sys_dict_data` VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '性别未知');
INSERT INTO `sys_dict_data` VALUES (4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '显示菜单');
INSERT INTO `sys_dict_data` VALUES (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '隐藏菜单');
INSERT INTO `sys_dict_data` VALUES (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '正常状态');
INSERT INTO `sys_dict_data` VALUES (7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '停用状态');
INSERT INTO `sys_dict_data` VALUES (8, 1, '正常', '0', 'sys_job_status', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '正常状态');
INSERT INTO `sys_dict_data` VALUES (9, 2, '暂停', '1', 'sys_job_status', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '停用状态');
INSERT INTO `sys_dict_data` VALUES (10, 1, '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '默认分组');
INSERT INTO `sys_dict_data` VALUES (11, 2, '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统分组');
INSERT INTO `sys_dict_data` VALUES (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统默认是');
INSERT INTO `sys_dict_data` VALUES (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统默认否');
INSERT INTO `sys_dict_data` VALUES (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '通知');
INSERT INTO `sys_dict_data` VALUES (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '公告');
INSERT INTO `sys_dict_data` VALUES (16, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '正常状态');
INSERT INTO `sys_dict_data` VALUES (17, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '关闭状态');
INSERT INTO `sys_dict_data` VALUES (18, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '新增操作');
INSERT INTO `sys_dict_data` VALUES (19, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '修改操作');
INSERT INTO `sys_dict_data` VALUES (20, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '删除操作');
INSERT INTO `sys_dict_data` VALUES (21, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '授权操作');
INSERT INTO `sys_dict_data` VALUES (22, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '导出操作');
INSERT INTO `sys_dict_data` VALUES (23, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '导入操作');
INSERT INTO `sys_dict_data` VALUES (24, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '强退操作');
INSERT INTO `sys_dict_data` VALUES (25, 10, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '1', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '生成操作');
INSERT INTO `sys_dict_data` VALUES (26, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '清空操作');
INSERT INTO `sys_dict_data` VALUES (27, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '正常状态');
INSERT INTO `sys_dict_data` VALUES (28, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '停用状态');
INSERT INTO `sys_dict_data` VALUES (29, 1, '用户管理-账号初始密码', '123456', 'init_password', NULL, NULL, 'Y', '0', 'admin', '2020-08-15 09:19:49', 'admin', '2020-08-15 09:20:01', '用户管理-账号初始密码');
INSERT INTO `sys_dict_data` VALUES (31, 2, '中转连接过滤', 'OTA-12', 'direct_rule_type', NULL, NULL, 'N', '0', 'admin', '2020-08-29 10:21:12', 'admin', '2020-08-29 10:21:12', NULL);
INSERT INTO `sys_dict_data` VALUES (32, 1, '中转时间过滤', 'OTA-10', 'direct_rule_type', NULL, NULL, 'N', '0', 'admin', '2020-08-29 10:22:35', 'admin', '2020-08-29 10:22:35', NULL);
INSERT INTO `sys_dict_data` VALUES (33, 3, '限制共享航司', 'OTA-13', 'direct_rule_type', NULL, NULL, 'N', '0', 'admin', '2020-08-29 12:03:15', 'admin', '2020-08-29 12:05:21', NULL);
INSERT INTO `sys_dict_data` VALUES (34, 5, '限制混合航司', 'OTA-14', 'direct_rule_type', NULL, NULL, 'N', '0', 'admin', '2020-08-29 12:15:32', 'admin', '2020-08-29 12:16:48', NULL);
INSERT INTO `sys_dict_data` VALUES (35, 6, '是否共享航司为空', 'OTA-16', 'direct_rule_type', NULL, NULL, 'N', '0', 'admin', '2020-08-29 12:48:30', 'admin', '2020-08-29 12:50:13', NULL);
INSERT INTO `sys_dict_data` VALUES (36, 4, '限制中转次数', 'OTA-9', 'direct_rule_type', NULL, NULL, 'N', '0', 'admin', '2020-08-29 13:02:12', 'admin', '2020-08-29 13:02:12', NULL);
INSERT INTO `sys_dict_data` VALUES (37, 7, '航司航线黑名单', 'OTA-33', 'direct_rule_type', NULL, NULL, 'N', '0', 'admin', '2020-08-29 14:21:21', 'admin', '2020-08-29 14:21:21', NULL);
INSERT INTO `sys_dict_data` VALUES (38, 0, '航线黑名单', 'GDS-7', 'direct_rule_gds', NULL, NULL, 'N', '0', 'admin', '2020-10-01 11:33:58', 'admin', '2020-10-01 11:33:58', NULL);
INSERT INTO `sys_dict_data` VALUES (39, 1, '限制方案数', 'GDS-11', 'direct_rule_gds', NULL, NULL, 'N', '0', 'admin', '2020-10-01 11:35:09', 'admin', '2020-10-01 11:35:09', NULL);
INSERT INTO `sys_dict_data` VALUES (40, 2, '航线航司黑名单', 'GDS-4', 'direct_rule_gds', NULL, NULL, 'N', '0', 'admin', '2020-10-01 11:36:11', 'admin', '2020-10-01 11:36:11', NULL);
INSERT INTO `sys_dict_data` VALUES (41, 3, '航线航司白名单', 'GDS-30', 'direct_rule_gds', NULL, NULL, 'N', '0', 'admin', '2020-10-01 11:37:26', 'admin', '2020-10-01 11:37:26', NULL);
INSERT INTO `sys_dict_data` VALUES (42, 4, '航司指定GDS来源', 'GDS-28', 'direct_rule_gds', NULL, NULL, 'N', '0', 'admin', '2020-10-01 11:38:27', 'admin', '2020-10-01 11:38:27', NULL);
INSERT INTO `sys_dict_data` VALUES (43, 5, '航司舱位黑名单时间', 'GDS-18', 'direct_rule_gds', NULL, NULL, 'N', '0', 'admin', '2020-10-01 11:39:46', 'admin', '2020-10-01 11:39:46', NULL);
INSERT INTO `sys_dict_data` VALUES (44, 0, '不限', '3', 'direct_policy_permit', NULL, NULL, 'N', '0', 'admin', '2020-10-13 22:04:53', 'admin', '2020-10-14 11:08:41', NULL);
INSERT INTO `sys_dict_data` VALUES (45, 1, '是', '1', 'direct_policy_permit', NULL, NULL, 'N', '0', 'admin', '2020-10-13 22:05:12', 'admin', '2020-10-14 11:08:49', NULL);
INSERT INTO `sys_dict_data` VALUES (46, 2, '否', '2', 'direct_policy_permit', NULL, NULL, 'N', '0', 'admin', '2020-10-13 22:05:37', 'admin', '2020-10-14 11:08:56', NULL);
INSERT INTO `sys_dict_data` VALUES (47, 0, '单程', '1', 'sibe_trip_type', NULL, NULL, 'N', '0', 'admin', '2020-10-14 11:38:36', 'admin', '2020-10-14 11:38:36', NULL);
INSERT INTO `sys_dict_data` VALUES (48, 1, '往返', '2', 'sibe_trip_type', NULL, NULL, 'N', '0', 'admin', '2020-10-14 11:38:54', 'admin', '2020-10-14 11:38:54', NULL);
INSERT INTO `sys_dict_data` VALUES (49, 2, '不限', '3', 'sibe_trip_type', NULL, NULL, 'N', '0', 'admin', '2020-10-14 11:39:21', 'admin', '2020-10-14 11:39:21', NULL);
INSERT INTO `sys_dict_data` VALUES (50, 1, '公布运价', '1', 'sibe_price_type', NULL, NULL, 'N', '0', 'admin', '2020-10-14 11:57:36', 'admin', '2020-10-14 11:57:36', NULL);
INSERT INTO `sys_dict_data` VALUES (51, 2, '私有运价', '2', 'sibe_price_type', NULL, NULL, 'N', '0', 'admin', '2020-10-14 11:57:54', 'admin', '2020-10-14 11:58:07', NULL);
INSERT INTO `sys_dict_data` VALUES (52, 0, '不限', '3', 'sibe_price_type', NULL, NULL, 'N', '0', 'admin', '2020-10-14 11:58:31', 'admin', '2020-10-14 11:58:31', NULL);
INSERT INTO `sys_dict_data` VALUES (53, 0, '普通产品', '1', 'sibe_product_type', NULL, NULL, 'N', '0', 'admin', '2020-10-14 13:08:46', 'admin', '2020-10-14 13:08:46', NULL);
INSERT INTO `sys_dict_data` VALUES (54, 1, 'K位产品', '2', 'sibe_product_type', NULL, NULL, 'N', '0', 'admin', '2020-10-14 13:09:19', 'admin', '2020-10-14 13:09:19', NULL);
INSERT INTO `sys_dict_data` VALUES (55, 0, '携程一部', 'CT001', 'sibe_site_name', NULL, NULL, 'N', '0', 'admin', '2020-10-14 17:37:11', 'admin', '2020-10-14 17:37:11', NULL);
INSERT INTO `sys_dict_data` VALUES (56, 1, '携程二部', 'CT002', 'sibe_site_name', NULL, NULL, 'N', '0', 'admin', '2020-10-14 17:37:40', 'admin', '2020-10-14 17:37:40', NULL);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `is_frame` int(1) NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1079 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 1, 'system', NULL, 1, 'M', '0', '0', '', 'system', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统管理目录');
INSERT INTO `sys_menu` VALUES (2, '基础数据', 0, 2, 'basic', NULL, 1, 'M', '0', '0', NULL, 'dict', 'admin', '2020-08-18 14:56:42', 'admin', '2020-08-18 14:56:51', '直连管理目录');
INSERT INTO `sys_menu` VALUES (4, '若依官网', 0, 4, 'http://ruoyi.vip', NULL, 0, 'M', '0', '0', '', 'guide', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '若依官网地址');
INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', 1, 'C', '0', '0', 'system/user/list', 'user', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '用户管理菜单');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', 1, 'C', '0', '0', 'system/role/list', 'peoples', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '角色管理菜单');
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', 1, 'C', '0', '0', 'system/menu/list', 'tree-table', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '菜单管理菜单');
INSERT INTO `sys_menu` VALUES (105, '系统参数配置', 1, 6, 'dict', 'system/dict/data', 1, 'C', '0', '0', 'system/dict/list', 'dict', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '字典管理菜单');
INSERT INTO `sys_menu` VALUES (1001, '用户查询', 100, 1, '', '', 1, 'F', '0', '0', 'system/user/list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1002, '用户新增', 100, 2, '', '', 1, 'F', '0', '0', 'system/user/add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1003, '用户修改', 100, 3, '', '', 1, 'F', '0', '0', 'system/user/edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1004, '用户删除', 100, 4, '', '', 1, 'F', '0', '0', 'system/user/remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1005, '用户导出', 100, 5, '', '', 1, 'F', '0', '0', 'system/user/export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1006, '用户导入', 100, 6, '', '', 1, 'F', '0', '0', 'system/user/import', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1007, '重置密码', 100, 7, '', '', 1, 'F', '0', '0', 'system/user/resetPwd', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1008, '角色查询', 101, 1, '', '', 1, 'F', '0', '0', 'system/role/list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1009, '角色新增', 101, 2, '', '', 1, 'F', '0', '0', 'system/role/add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1010, '角色修改', 101, 3, '', '', 1, 'F', '0', '0', 'system/role/edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1011, '角色删除', 101, 4, '', '', 1, 'F', '0', '0', 'system/role/remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1012, '角色导出', 101, 5, '', '', 1, 'F', '0', '0', 'system/role/export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1013, '菜单查询', 102, 1, '', '', 1, 'F', '0', '0', 'system/menu/list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1014, '菜单新增', 102, 2, '', '', 1, 'F', '0', '0', 'system/menu/add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1015, '菜单修改', 102, 3, '', '', 1, 'F', '0', '0', 'system/menu/edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1016, '菜单删除', 102, 4, '', '', 1, 'F', '0', '0', 'system/menu/remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1026, '系统参数查询', 105, 1, '#', '', 1, 'F', '0', '0', 'system/dict/list', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1027, '系统参数新增', 105, 2, '#', '', 1, 'F', '0', '0', 'system/dict/add', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1028, '系统参数修改', 105, 3, '#', '', 1, 'F', '0', '0', 'system/dict/edit', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1029, '系统参数删除', 105, 4, '#', '', 1, 'F', '0', '0', 'system/dict/remove', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1041, '航司航线配置', 2, 2, 'base_air_route', 'manual/basic/baseAirRoute', 1, 'C', '0', '0', '', 'code', 'admin', NULL, 'admin', NULL, '');
INSERT INTO `sys_menu` VALUES (1055, '舱位舱等配置', 2, 4, 'base_cabin', 'manual/basic/baseCabin', 1, 'C', '0', '0', NULL, 'people', 'admin', NULL, 'admin', NULL, '');
INSERT INTO `sys_menu` VALUES (1060, 'OTA站点配置', 2, 5, 'ota_site', 'manual/basic/siteConfig', 1, 'C', '0', '0', NULL, 'peoples', 'admin', NULL, 'admin', NULL, '');
INSERT INTO `sys_menu` VALUES (1063, '定时任务', 0, 4, 'http://106.75.115.170:8091/xxl-job-admin/', NULL, 0, 'M', '0', '0', NULL, 'job', 'admin', NULL, 'admin', NULL, '');
INSERT INTO `sys_menu` VALUES (1064, '平台管理', 0, 3, 'ota_manage', NULL, 1, 'M', '0', '0', NULL, 'slider', 'admin', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1065, '携程一部', 1064, 1, 'ctrip_ct001', 'manual/template/view', 1, 'M', '0', '0', NULL, 'swagger', 'admin', NULL, 'admin', NULL, '');
INSERT INTO `sys_menu` VALUES (1066, '细节政策', 1065, 1, 'policy_info', 'manual/policy/policyInfo', 1, 'C', '0', '0', 'manual/policy_info/list', 'list', 'admin', NULL, 'admin', NULL, '');
INSERT INTO `sys_menu` VALUES (1067, '全局政策', 1065, 2, 'policy_global', 'manual/policy/policyGlobal', 1, 'C', '0', '0', 'manual/policy_global/list', 'list', 'admin', NULL, 'admin', NULL, '');
INSERT INTO `sys_menu` VALUES (1068, '站点规则', 1065, 3, 'ota_rule', 'manual/policy/otaRule', 1, 'C', '0', '0', 'manual/policy/ota_rule', 'list', 'admin', NULL, 'admin', NULL, '');
INSERT INTO `sys_menu` VALUES (1069, '飞猪金牌', 1064, 2, 'fliggy_fljp', 'manual/template/view', 1, 'M', '0', '0', NULL, 'color', 'admin', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1070, '细节政策', 1069, 1, 'policy_info', 'manual/policy/policyInfo', 1, 'C', '0', '0', 'manual/policy_info/list', 'color', 'admin', NULL, 'admin', NULL, '');
INSERT INTO `sys_menu` VALUES (1071, '全局政策', 1069, 2, 'policy_global', 'manual/policy/policyGlobal', 1, 'C', '0', '0', 'manual/policy_global/list', 'color', 'admin', NULL, 'admin', NULL, '');
INSERT INTO `sys_menu` VALUES (1072, '站点规则', 1069, 3, 'ota_rule', 'manual/policy/otaRule', 1, 'C', '0', '0', 'manual/policy/ota_rule', 'color', 'admin', NULL, 'admin', NULL, '');
INSERT INTO `sys_menu` VALUES (1078, '退改签配置', 1065, 3, 'resign_config', 'manual/policy/resignConfig', 1, 'C', '0', '0', 'manual/resign_config/list', 'shopping', 'admin', NULL, 'admin', NULL, '');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '系统管理员', 'admin', 1, '1', '0', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统管理员');
INSERT INTO `sys_role` VALUES (2, '普通角色', 'common', 2, '2', '0', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2020-07-31 14:10:47', '普通角色');
INSERT INTO `sys_role` VALUES (3, 'gwk12', 'ggggg', 5, '1', '0', '0', 'ry', NULL, 'admin', NULL, 'gfh');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 100);
INSERT INTO `sys_role_menu` VALUES (2, 101);
INSERT INTO `sys_role_menu` VALUES (2, 102);
INSERT INTO `sys_role_menu` VALUES (2, 105);
INSERT INTO `sys_role_menu` VALUES (2, 1001);
INSERT INTO `sys_role_menu` VALUES (2, 1002);
INSERT INTO `sys_role_menu` VALUES (2, 1003);
INSERT INTO `sys_role_menu` VALUES (2, 1004);
INSERT INTO `sys_role_menu` VALUES (2, 1005);
INSERT INTO `sys_role_menu` VALUES (2, 1006);
INSERT INTO `sys_role_menu` VALUES (2, 1007);
INSERT INTO `sys_role_menu` VALUES (2, 1008);
INSERT INTO `sys_role_menu` VALUES (2, 1009);
INSERT INTO `sys_role_menu` VALUES (2, 1010);
INSERT INTO `sys_role_menu` VALUES (2, 1011);
INSERT INTO `sys_role_menu` VALUES (2, 1012);
INSERT INTO `sys_role_menu` VALUES (2, 1013);
INSERT INTO `sys_role_menu` VALUES (2, 1026);
INSERT INTO `sys_role_menu` VALUES (2, 1027);
INSERT INTO `sys_role_menu` VALUES (2, 1028);
INSERT INTO `sys_role_menu` VALUES (2, 1029);
INSERT INTO `sys_role_menu` VALUES (2, 1040);
INSERT INTO `sys_role_menu` VALUES (2, 1041);
INSERT INTO `sys_role_menu` VALUES (2, 1042);
INSERT INTO `sys_role_menu` VALUES (2, 1043);
INSERT INTO `sys_role_menu` VALUES (2, 1044);
INSERT INTO `sys_role_menu` VALUES (2, 1045);
INSERT INTO `sys_role_menu` VALUES (2, 1046);
INSERT INTO `sys_role_menu` VALUES (2, 1047);
INSERT INTO `sys_role_menu` VALUES (2, 1052);
INSERT INTO `sys_role_menu` VALUES (2, 1064);
INSERT INTO `sys_role_menu` VALUES (2, 1065);
INSERT INTO `sys_role_menu` VALUES (2, 1066);
INSERT INTO `sys_role_menu` VALUES (2, 1067);
INSERT INTO `sys_role_menu` VALUES (3, 1);
INSERT INTO `sys_role_menu` VALUES (3, 2);
INSERT INTO `sys_role_menu` VALUES (3, 100);
INSERT INTO `sys_role_menu` VALUES (3, 101);
INSERT INTO `sys_role_menu` VALUES (3, 102);
INSERT INTO `sys_role_menu` VALUES (3, 1001);
INSERT INTO `sys_role_menu` VALUES (3, 1003);
INSERT INTO `sys_role_menu` VALUES (3, 1005);
INSERT INTO `sys_role_menu` VALUES (3, 1007);
INSERT INTO `sys_role_menu` VALUES (3, 1008);
INSERT INTO `sys_role_menu` VALUES (3, 1011);
INSERT INTO `sys_role_menu` VALUES (3, 1013);
INSERT INTO `sys_role_menu` VALUES (3, 1048);
INSERT INTO `sys_role_menu` VALUES (3, 1054);
INSERT INTO `sys_role_menu` VALUES (3, 1055);
INSERT INTO `sys_role_menu` VALUES (3, 1057);
INSERT INTO `sys_role_menu` VALUES (3, 1058);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最后登陆IP',
  `login_date` datetime(0) NULL DEFAULT NULL COMMENT '最后登陆时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 103, 'admin', '若依', '00', 'ry@163.com', '15888888888', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '管理员');
INSERT INTO `sys_user` VALUES (2, 105, 'ry', '若依', '00', 'ry@qq.com', '15666666666', '1', '', '', '0', '0', '127.0.0.1', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', 'admin', '2020-07-31 14:11:48', '测试员');
INSERT INTO `sys_user` VALUES (3, NULL, 'gwk123', 'gwk', '00', '632697105@qq.com', '15625281551', '2', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '', NULL, 'admin', NULL, 'admin', NULL, '哈哈哈123');
INSERT INTO `sys_user` VALUES (4, NULL, 'yyy', 'yyy', '00', '678@qq.com', '15685854477', '0', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '', NULL, 'ry', NULL, 'ry', NULL, NULL);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);
INSERT INTO `sys_user_role` VALUES (3, 2);
INSERT INTO `sys_user_role` VALUES (4, 3);

SET FOREIGN_KEY_CHECKS = 1;
