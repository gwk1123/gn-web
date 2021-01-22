/*
 Navicat Premium Data Transfer

 Source Server         : ucloud
 Source Server Type    : MySQL
 Source Server Version : 50650
 Source Host           : 106.75.115.170:3306
 Source Schema         : ry-vue

 Target Server Type    : MySQL
 Target Server Version : 50650
 File Encoding         : 65001

 Date: 22/01/2021 21:29:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
INSERT INTO `sys_menu` VALUES (2, '基础数据管理', 0, 2, 'basic', NULL, 1, 'M', '0', '0', NULL, 'dict', 'admin', '2020-08-18 14:56:42', 'admin', '2020-08-18 14:56:51', '直连管理目录');
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
INSERT INTO `sys_menu` VALUES (1040, 'GDS配置', 2, 1, 'gds', 'direct/basic/gds', 1, 'C', '0', '0', '', 'code', 'admin', '2020-08-18 15:37:30', 'admin', '2020-08-18 15:37:33', '');
INSERT INTO `sys_menu` VALUES (1041, 'PCC配置', 2, 2, 'gds_pcc', 'direct/basic/gdsPcc', 1, 'C', '0', '0', '', 'code', 'admin', NULL, 'admin', NULL, '');
INSERT INTO `sys_menu` VALUES (1042, 'PCC配置添加', 1041, 1, '', NULL, 1, 'F', '0', '0', 'direct/gds_pcc/add', '#', 'admin', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1043, 'PCC配置编辑', 1041, 2, '', NULL, 1, 'F', '0', '0', 'direct/gds_pcc/edit', '#', 'admin', NULL, 'admin', NULL, '');
INSERT INTO `sys_menu` VALUES (1044, 'PCC配置删除', 1041, 3, '', NULL, 1, 'F', '0', '0', 'direct/gds_pcc/remove', '#', 'admin', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1045, 'GDS配置新增', 1040, 1, '', NULL, 1, 'F', '0', '0', 'direct/gds/add', '#', 'admin', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1046, 'GDS配置编辑', 1040, 2, '', NULL, 1, 'F', '0', '0', 'direct/gds/edit', '#', 'admin', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1047, 'GDS配置删除', 1040, 3, '', NULL, 1, 'F', '0', '0', 'direct/gds/remove', '#', 'admin', NULL, 'admin', NULL, '');
INSERT INTO `sys_menu` VALUES (1048, '机场配置', 2, 3, 'all_airports', 'direct/basic/allAirports', 1, 'C', '0', '0', NULL, 'color', 'admin', NULL, 'admin', NULL, '');
INSERT INTO `sys_menu` VALUES (1049, '机场配置新增', 1048, 1, '', NULL, 1, 'F', '0', '0', 'direct/all_airports/add', '#', 'admin', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1050, '机场配置编辑', 1048, 2, '', NULL, 1, 'F', '0', '0', 'direct/all_airports/edit', '#', 'admin', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1051, '机场配置删除', 1048, 3, '', NULL, 1, 'F', '0', '0', 'direct/all_airports/remove', '#', 'admin', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1052, 'GDS配置查询', 1040, 0, '', NULL, 1, 'F', '0', '0', 'direct/gds/list', '#', 'admin', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1053, 'PCC配置查询', 1041, 0, '', NULL, 1, 'F', '0', '0', 'direct/gds_pcc/list', '#', 'admin', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1054, '机场配置查询', 1048, 0, '', NULL, 1, 'F', '0', '0', 'direct/all_airports/list', '#', 'admin', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1055, 'OTA平台配置', 2, 4, 'ota', 'direct/basic/ota', 1, 'C', '0', '0', NULL, 'people', 'admin', NULL, 'admin', NULL, '');
INSERT INTO `sys_menu` VALUES (1056, 'OTA配置新增', 1055, 1, '', NULL, 1, 'F', '0', '0', 'direct/ota/add', '#', 'admin', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1057, 'OTA配置查询', 1055, 0, '', NULL, 1, 'F', '0', '0', 'direct/ota/list', '#', 'admin', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1058, 'OTA配置编辑', 1055, 2, '', NULL, 1, 'F', '0', '0', 'direct/ota/edit', '#', 'admin', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1059, 'OTA配置删除', 1055, 3, '', NULL, 1, 'F', '0', '0', 'direct/ota/remove', '#', 'admin', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1060, 'OTA站点配置', 2, 5, 'ota_site', 'direct/basic/otaSite', 1, 'C', '0', '0', NULL, 'peoples', 'admin', NULL, 'admin', NULL, '');
INSERT INTO `sys_menu` VALUES (1061, '航线路由', 1076, 6, 'route_config', 'direct/basic/routeConfig', 1, 'C', '0', '0', NULL, 'guide', 'admin', NULL, 'admin', NULL, '');
INSERT INTO `sys_menu` VALUES (1062, '航线路由查询', 1061, 0, '', NULL, 1, 'F', '0', '0', 'direct/route_config/list', '#', 'admin', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1063, '定时任务', 0, 4, 'http://106.75.115.170:8091/xxl-job-admin/', NULL, 0, 'M', '0', '0', NULL, 'job', 'admin', NULL, 'admin', NULL, '');
INSERT INTO `sys_menu` VALUES (1064, '平台管理', 0, 3, 'ota_manage', NULL, 1, 'M', '0', '0', NULL, 'slider', 'admin', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1065, '携程一部', 1064, 1, 'ctrip_ct001', 'direct/template/view', 1, 'M', '0', '0', NULL, 'swagger', 'admin', NULL, 'admin', NULL, '');
INSERT INTO `sys_menu` VALUES (1066, '细节政策', 1065, 1, 'policy_info', 'direct/policy/policyInfo', 1, 'C', '0', '0', 'direct/policy_info/list', 'list', 'admin', NULL, 'admin', NULL, '');
INSERT INTO `sys_menu` VALUES (1067, '全局政策', 1065, 2, 'policy_global', 'direct/policy/policyGlobal', 1, 'C', '0', '0', 'direct/policy_global/list', 'list', 'admin', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1068, '站点规则', 1065, 3, 'ota_rule', 'direct/policy/otaRule', 1, 'C', '0', '0', 'direct/policy/ota_rule', 'list', 'admin', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1069, '飞猪金牌', 1064, 2, 'fliggy_fljp', 'direct/template/view', 1, 'M', '0', '0', NULL, 'color', 'admin', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1070, '细节政策', 1069, 1, 'policy_info', 'direct/policy/policyInfo', 1, 'C', '0', '0', 'direct/policy_info/list', 'color', 'admin', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1071, '全局政策', 1069, 2, 'policy_global', 'direct/policy/policyGlobal', 1, 'C', '0', '0', 'direct/policy_global/list', 'color', 'admin', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1072, '站点规则', 1069, 3, 'ota_rule', 'direct/policy/otaRule', 1, 'C', '0', '0', 'direct/policy/ota_rule', 'color', 'admin', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1073, 'GDS规则', 0, 1, 'gds', NULL, 1, 'M', '0', '0', NULL, 'drag', 'admin', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1074, 'GDS规则配置', 1073, 1, 'gds_rule', 'direct/gds/gdsRule', 1, 'C', '0', '0', NULL, 'drag', 'admin', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1075, '开关数据配置', 1, 6, 'switch_data', 'direct/gds/siteRulesSwitch', 1, 'C', '0', '0', NULL, 'chart', 'admin', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1076, '直连管理', 0, 3, 'direct', NULL, 1, 'M', '0', '0', NULL, 'chart', 'admin', NULL, '', NULL, '');
INSERT INTO `sys_menu` VALUES (1077, '本地可视化', 1076, 0, 'view', 'direct/gds/gdsSearch', 1, 'C', '0', '0', '', 'search', 'admin', NULL, 'admin', NULL, '');
INSERT INTO `sys_menu` VALUES (1078, '退改签配置', 1065, 3, 'resign_config', 'direct/policy/resignConfig', 1, 'C', '0', '0', 'direct/resign_config/list', 'shopping', 'admin', NULL, 'admin', NULL, '');

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
