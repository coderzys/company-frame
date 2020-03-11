/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : test11

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2020-02-23 20:12:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `dept_no` varchar(18) DEFAULT NULL COMMENT '部门编号',
  `name` varchar(300) DEFAULT NULL COMMENT '部门名称',
  `pid` varchar(64) NOT NULL COMMENT '父级id',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态(1:正常；0:弃用)',
  `relation_code` varchar(3000) DEFAULT NULL COMMENT '为了维护更深层级关系(规则：父级关系编码+自己的编码)',
  `dept_manager_id` varchar(64) DEFAULT NULL COMMENT '部门经理user_id',
  `manager_name` varchar(255) DEFAULT NULL COMMENT '部门经理名称',
  `phone` varchar(20) DEFAULT NULL COMMENT '部门经理联系电话',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(4) DEFAULT '1' COMMENT '是否删除(1未删除；0已删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('06e8066d-fbb9-4904-b71a-e75fc06b19a6', 'YXD0000006', '总经理办公室', '0', '1', 'YXD0000006', null, '小霍', '13866666666', '2020-01-07 19:33:24', null, '1');
INSERT INTO `sys_dept` VALUES ('34843160-cfe7-48a4-8e4d-c54653217636', 'YXD0000008', '人事部', '06e8066d-fbb9-4904-b71a-e75fc06b19a6', '1', 'YXD0000006YXD0000008', null, '小刘', '13866666666', '2020-01-07 19:36:19', '2020-01-08 12:20:41', '1');
INSERT INTO `sys_dept` VALUES ('7610b9c4-9348-4102-afc3-0e598216ccb6', 'YXD0000007', '行政部', '06e8066d-fbb9-4904-b71a-e75fc06b19a6', '1', 'YXD0000006YXD0000007', null, '小庄', '13866666667', '2020-01-07 19:34:49', '2020-02-22 16:28:02', '0');
INSERT INTO `sys_dept` VALUES ('ba3155ef-d59d-4f56-8c8d-39de61c72033', 'YXD0000001', '外勤部', 'da5b02b4-1fb1-4907-aaf3-3fa617f875a8', '1', 'YXD0000006YXD0000007YXD0000009YXD0000001', null, '张全蛋', '1111111111', '2020-02-20 22:14:18', '2020-02-22 16:27:02', '0');
INSERT INTO `sys_dept` VALUES ('da5b02b4-1fb1-4907-aaf3-3fa617f875a8', 'YXD0000009', '后勤部', '7610b9c4-9348-4102-afc3-0e598216ccb6', '1', 'YXD0000006YXD0000007YXD0000009', null, '小黄', '13899999999', '2020-01-08 11:28:17', '2020-02-22 16:28:02', '0');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户id',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `time` int(11) DEFAULT NULL COMMENT '响应时间',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `code` varchar(64) DEFAULT NULL COMMENT '菜单权限编码',
  `name` varchar(300) DEFAULT NULL COMMENT '菜单权限名称',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(如：sys:user:add)',
  `url` varchar(100) DEFAULT NULL COMMENT '访问地址URL',
  `method` varchar(10) DEFAULT NULL COMMENT '资源请求类型',
  `pid` varchar(64) DEFAULT NULL COMMENT '父级菜单权限名称',
  `order_num` int(11) DEFAULT '0' COMMENT '排序',
  `type` tinyint(4) DEFAULT NULL COMMENT '菜单权限类型(1:目录;2:菜单;3:按钮)',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态1:正常 0：禁用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(4) DEFAULT '1' COMMENT '是否删除(1未删除；0已删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('013095aa-0f4d-4c32-b30e-229a587e52ad', 'btn-dept-add', '新增部门权限', 'sys:dept:add', '/api/dept/add', 'POST', '8f393e44-b585-4875-866d-71f88fea9659', '100', '3', '1', '2020-01-08 15:44:18', null, '1');
INSERT INTO `sys_permission` VALUES ('03dc1cbf-ef44-4326-a7dc-10fe16d22dad', 'btn-log-list', '查询日志列表权限', 'sys:log:list', '/api/log/list', 'POST', '0545d9d1-c82c-44c2-85e5-0b6ac4042515', '100', '3', '1', '2020-01-08 16:12:14', null, '1');
INSERT INTO `sys_permission` VALUES ('0545d9d1-c82c-44c2-85e5-0b6ac4042515', '', '日志管理', '', '/view/logs', '', '4caeb861-354c-45f6-98b4-59f486beb511', '100', '2', '1', '2020-01-08 13:57:12', null, '1');
INSERT INTO `sys_permission` VALUES ('145cb90b-d205-40f6-8a2d-703f41ed1feb', 'btn-user-delete', '删除用户权限', 'sys:user:delete', '/api/user/delete', 'DELETE', '9ce621a0-ee2c-4cf6-b7bd-012a1a01ba63', '100', '3', '1', '2020-01-08 15:42:50', null, '1');
INSERT INTO `sys_permission` VALUES ('15bb6aff-2e3b-490a-a21f-0167ae0ebc0d', 'btn-log-delete', '删除日志权限', 'sys:log:delete', '/api/log/delete', 'DELETE', '0545d9d1-c82c-44c2-85e5-0b6ac4042515', '100', '3', '1', '2020-01-08 16:12:53', null, '1');
INSERT INTO `sys_permission` VALUES ('24b7b13c-f00f-4e6b-a221-fe2d780e4d4f', 'btn-user-api', '接口管理', 'sys:user:api', '/swagger-ui.html', 'GET', '4caeb861-354c-45f6-98b4-59f486beb511', '97', '2', '1', '2020-01-08 14:28:56', '2020-02-22 21:03:02', '1');
INSERT INTO `sys_permission` VALUES ('27acf73b-2fcb-451b-bdc4-e11e5ab41e2a', 'btn-dept-delete', '删除部门权限', 'sys:dept:delete', '/api/dept/delete/*', 'DELETE', '8f393e44-b585-4875-866d-71f88fea9659', '100', '3', '1', '2020-01-08 15:45:52', null, '1');
INSERT INTO `sys_permission` VALUES ('290c0240-0914-487c-b4e9-6635bf5ebfec', '', '菜单权限管理', '', '/view/menus', 'GET', '346df872-8964-4455-8afd-ffa6308fb18a', '99', '2', '1', '2020-01-06 21:55:59', '2020-01-08 09:10:59', '1');
INSERT INTO `sys_permission` VALUES ('2ae13993-9501-46d5-8473-fe45fee57f3b', 'btn-user-add', '新增用户权限', 'sys:user:add', '/api/user/add', 'POST', '9ce621a0-ee2c-4cf6-b7bd-012a1a01ba63', '100', '3', '1', '2020-01-08 15:40:36', null, '1');
INSERT INTO `sys_permission` VALUES ('2eeaa020-74d5-4c4b-9849-2cf4bd68fed9', 'btn-role-update', '更新角色权限', 'sys:role:update', '/api/role/update', 'PUT', 'c198d1cb-ad4d-4001-9375-9ec8ee04053d', '100', '3', '1', '2020-01-08 16:09:55', null, '1');
INSERT INTO `sys_permission` VALUES ('2f9a3f67-6ef3-4eac-b9a1-c0e898718d0c', 'btn-permission-delete', '删除菜单权限', 'sys:permission:delete', '/api/permission/delete/*', 'DELETE', '290c0240-0914-487c-b4e9-6635bf5ebfec', '100', '3', '1', '2020-01-08 15:48:37', '2020-02-21 20:56:04', '1');
INSERT INTO `sys_permission` VALUES ('346df872-8964-4455-8afd-ffa6308fb18a', '', '组织管理', '', '', '', '0', '100', '1', '1', '2020-01-06 21:53:55', null, '1');
INSERT INTO `sys_permission` VALUES ('390ded0e-9f48-40a7-a841-791c203f22ae', 'btn-permission-list', '查询菜单权限列表权限', 'sys:permission:list', '/api/permission/list', 'POST', '290c0240-0914-487c-b4e9-6635bf5ebfec', '100', '3', '1', '2020-01-08 15:46:36', null, '1');
INSERT INTO `sys_permission` VALUES ('39313e6a-14ed-4224-a91e-ef6a10ba54cd', 'btn-dept-list', '查询部门信息列表权限', 'sys:dept:list', '/api/dept/list', 'POST', '8f393e44-b585-4875-866d-71f88fea9659', '100', '3', '1', '2020-01-08 15:43:36', null, '1');
INSERT INTO `sys_permission` VALUES ('41412d6d-d86a-4cfd-9aa7-d51f9ced0dfe', '', '测试删除', '', '', '', '346df872-8964-4455-8afd-ffa6308fb18a', '100', '1', '1', '2020-01-08 09:30:53', '2020-01-08 09:31:01', '0');
INSERT INTO `sys_permission` VALUES ('47697e92-e199-4420-a2c2-09ec1b08cb9d', 'btn-user-list', '查询用户信息列表权限', 'sys:user:list', '/api/users/list', 'POST', '9ce621a0-ee2c-4cf6-b7bd-012a1a01ba63', '100', '3', '1', '2020-01-08 15:39:55', null, '1');
INSERT INTO `sys_permission` VALUES ('4caeb861-354c-45f6-98b4-59f486beb511', '', '系统管理', '', '', '', '0', '98', '1', '1', '2020-01-08 13:55:56', null, '1');
INSERT INTO `sys_permission` VALUES ('65734896-90c5-4b48-b9e8-dee47a74a297', 'btn-role-delete', '删除角色权限', 'sys:role:delete', '/api/role/delete/*', 'DELETE', 'c198d1cb-ad4d-4001-9375-9ec8ee04053d', '100', '3', '1', '2020-01-08 16:11:22', null, '1');
INSERT INTO `sys_permission` VALUES ('7141c2e9-6d50-46b6-94e8-100466b7249f', 'btn-user-sql', 'SQL监控', 'sys:user:sql', '/druid/sql.html', 'GET', '4caeb861-354c-45f6-98b4-59f486beb511', '96', '2', '1', '2020-01-08 14:30:01', '2020-02-22 21:03:25', '1');
INSERT INTO `sys_permission` VALUES ('84b9b525-aa44-4b16-9900-adca26115a37', 'btn-role-add', '新增角色权限', 'sys:role:add', '/api/role/add', 'POST', 'c198d1cb-ad4d-4001-9375-9ec8ee04053d', '100', '3', '1', '2020-01-08 15:50:00', null, '1');
INSERT INTO `sys_permission` VALUES ('8f393e44-b585-4875-866d-71f88fea9659', '', '部门管理', '', '/view/depts', '', '346df872-8964-4455-8afd-ffa6308fb18a', '97', '2', '1', '2020-01-07 18:28:31', null, '1');
INSERT INTO `sys_permission` VALUES ('90b3be91-5e9d-42f8-81fb-0c9ef3014faa', 'btn-role-detail', '角色详情权限', 'sys:role:detail', '/api/role/*', 'GET', 'c198d1cb-ad4d-4001-9375-9ec8ee04053d', '100', '3', '1', '2020-01-08 16:10:32', null, '1');
INSERT INTO `sys_permission` VALUES ('9ce621a0-ee2c-4cf6-b7bd-012a1a01ba63', '', '用户管理', '', '/view/users', '', '346df872-8964-4455-8afd-ffa6308fb18a', '96', '2', '1', '2020-01-07 19:49:37', '2020-01-08 10:01:38', '1');
INSERT INTO `sys_permission` VALUES ('b7348d63-c4d3-406d-9e46-543346674275', 'btn-dept-update', '更新部门信息权限', 'sys:dept:update', '/api/dept/update', 'PUT', '8f393e44-b585-4875-866d-71f88fea9659', '100', '3', '1', '2020-01-08 15:44:59', null, '1');
INSERT INTO `sys_permission` VALUES ('bb5ca869-0303-4fc0-b067-936cba7d1cc8', 'btn-permission-update', '更新菜单权限', 'sys:permission:update', '/api/permission/update', 'PUT', '290c0240-0914-487c-b4e9-6635bf5ebfec', '100', '3', '1', '2020-01-08 15:47:56', null, '1');
INSERT INTO `sys_permission` VALUES ('c198d1cb-ad4d-4001-9375-9ec8ee04053d', '', '角色管理', '', '/view/roles', '', '346df872-8964-4455-8afd-ffa6308fb18a', '100', '2', '1', '2020-01-06 22:33:55', '2020-01-08 09:13:30', '1');
INSERT INTO `sys_permission` VALUES ('d41c2bc3-454c-4f62-84fe-97825d5cf8a7', 'btn-user-update-role', '赋予用户角色权限', 'sys:user:role:update', '/api/user/role/update', 'PUT', '9ce621a0-ee2c-4cf6-b7bd-012a1a01ba63', '100', '3', '1', '2020-01-08 15:41:20', null, '1');
INSERT INTO `sys_permission` VALUES ('d60faf3e-9a72-49d5-b02d-a67bfeff07fa', 'btn-user-update', '列表更新用户信息权限', 'sys:user:update', '/api/user/update', 'PUT', '9ce621a0-ee2c-4cf6-b7bd-012a1a01ba63', '100', '3', '1', '2020-01-08 15:42:06', null, '1');
INSERT INTO `sys_permission` VALUES ('de8e2328-4313-477e-9644-3ca93799cc76', 'btn-role-add', '角色管理-新增角色', 'sys:role:add', '/api/role/add', 'POST', 'c198d1cb-ad4d-4001-9375-9ec8ee04053d', '100', '3', '1', '2020-01-08 15:28:09', '2020-01-08 15:29:31', '0');
INSERT INTO `sys_permission` VALUES ('e136cc74-9817-4ef1-b181-8f1afd7e102c', 'btn-permission-add', '新增菜单权限', 'sys:permission:add', '/api/permission/add', 'POST', '290c0240-0914-487c-b4e9-6635bf5ebfec', '100', '3', '1', '2020-01-08 15:47:16', null, '1');
INSERT INTO `sys_permission` VALUES ('f9f4d9f4-a2f5-430c-9f2d-6c8e650a8c39', 'btn-role-list', '查询角色列表权限', 'sys:role:list', '/api/role/list', 'POST', 'c198d1cb-ad4d-4001-9375-9ec8ee04053d', '100', '3', '1', '2020-01-08 15:49:20', null, '1');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `description` varchar(300) DEFAULT NULL,
  `status` tinyint(4) DEFAULT '1' COMMENT '状态(1:正常0:弃用)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(4) DEFAULT '1' COMMENT '是否删除(1未删除；0已删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('0d768668-bb73-4119-97c5-83c92e574495', 'dev', '开发环境角色，拥有组织管理的权限', '1', '2020-02-20 19:04:22', '2020-02-22 20:47:15', '1');
INSERT INTO `sys_role` VALUES ('b125dbb8-459e-409f-bd1f-d17966568eda', 'administrator', '超级管理员，拥有系统所有权限', '1', '2020-01-06 23:37:45', '2020-01-08 15:31:57', '1');
INSERT INTO `sys_role` VALUES ('fc674bde-29ee-4160-bd97-00761357f019', 'test', '测试角色，拥有系统管理的权限', '1', '2020-01-07 21:19:04', '2020-02-22 23:27:39', '1');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `role_id` varchar(64) DEFAULT NULL COMMENT '角色id',
  `permission_id` varchar(64) DEFAULT NULL COMMENT '菜单权限id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('04090248-59d5-4909-99a8-25b71bcefb42', 'b125dbb8-459e-409f-bd1f-d17966568eda', '39313e6a-14ed-4224-a91e-ef6a10ba54cd', '2020-01-08 15:31:57');
INSERT INTO `sys_role_permission` VALUES ('07e593cb-b238-4bcc-b35c-090de648ee87', 'b125dbb8-459e-409f-bd1f-d17966568eda', '290c0240-0914-487c-b4e9-6635bf5ebfec', '2020-01-08 15:31:57');
INSERT INTO `sys_role_permission` VALUES ('090661f8-7ab3-43ad-86ed-e81413ce1123', 'fc674bde-29ee-4160-bd97-00761357f019', '7141c2e9-6d50-46b6-94e8-100466b7249f', '2020-02-22 23:27:39');
INSERT INTO `sys_role_permission` VALUES ('140c4dbf-114c-4a8e-8dae-710ac28f38ea', 'b125dbb8-459e-409f-bd1f-d17966568eda', '2ae13993-9501-46d5-8473-fe45fee57f3b', '2020-01-08 15:31:57');
INSERT INTO `sys_role_permission` VALUES ('1bb73b1b-d50b-4e01-b322-9949787c4aba', '0d768668-bb73-4119-97c5-83c92e574495', 'bb5ca869-0303-4fc0-b067-936cba7d1cc8', '2020-02-22 20:47:15');
INSERT INTO `sys_role_permission` VALUES ('1c310548-7ccf-40b9-b169-0d821c40d524', 'b125dbb8-459e-409f-bd1f-d17966568eda', '90b3be91-5e9d-42f8-81fb-0c9ef3014faa', '2020-01-08 15:31:57');
INSERT INTO `sys_role_permission` VALUES ('2140e30f-a719-4a7d-a40b-6faf965fe2c3', '0d768668-bb73-4119-97c5-83c92e574495', '39313e6a-14ed-4224-a91e-ef6a10ba54cd', '2020-02-22 20:47:15');
INSERT INTO `sys_role_permission` VALUES ('2522ef2c-369a-469f-bb45-68d3c0317e4a', 'b125dbb8-459e-409f-bd1f-d17966568eda', 'e136cc74-9817-4ef1-b181-8f1afd7e102c', '2020-01-08 15:31:57');
INSERT INTO `sys_role_permission` VALUES ('286b2c86-8f00-4529-ab5a-a9dcbe84eadc', 'fc674bde-29ee-4160-bd97-00761357f019', '03dc1cbf-ef44-4326-a7dc-10fe16d22dad', '2020-02-22 23:27:39');
INSERT INTO `sys_role_permission` VALUES ('2927d1fe-a4c8-45c4-a3de-66d0142c4523', 'b125dbb8-459e-409f-bd1f-d17966568eda', 'd41c2bc3-454c-4f62-84fe-97825d5cf8a7', '2020-01-08 15:31:57');
INSERT INTO `sys_role_permission` VALUES ('2cbfb0cc-a3a1-4ae8-af94-bd886713c5fe', 'fc674bde-29ee-4160-bd97-00761357f019', '15bb6aff-2e3b-490a-a21f-0167ae0ebc0d', '2020-02-22 23:27:39');
INSERT INTO `sys_role_permission` VALUES ('31d041e9-7e00-4e8f-901d-576ad66ade34', 'b125dbb8-459e-409f-bd1f-d17966568eda', '2f9a3f67-6ef3-4eac-b9a1-c0e898718d0c', '2020-01-08 15:31:57');
INSERT INTO `sys_role_permission` VALUES ('364c8d50-6edf-4a0b-b5ed-a1e4be1c0e0c', 'b125dbb8-459e-409f-bd1f-d17966568eda', '24b7b13c-f00f-4e6b-a221-fe2d780e4d4f', '2020-01-08 15:31:57');
INSERT INTO `sys_role_permission` VALUES ('3fc20ea6-a2c6-4bdb-847e-3a83a11a2961', 'b125dbb8-459e-409f-bd1f-d17966568eda', '145cb90b-d205-40f6-8a2d-703f41ed1feb', '2020-01-08 15:31:57');
INSERT INTO `sys_role_permission` VALUES ('43f62782-1e9a-43e4-9a63-1d2fe95e96a9', 'fc674bde-29ee-4160-bd97-00761357f019', '4caeb861-354c-45f6-98b4-59f486beb511', '2020-02-22 23:27:39');
INSERT INTO `sys_role_permission` VALUES ('4457275c-66b0-4cf8-9981-540d73095438', '0d768668-bb73-4119-97c5-83c92e574495', '27acf73b-2fcb-451b-bdc4-e11e5ab41e2a', '2020-02-22 20:47:15');
INSERT INTO `sys_role_permission` VALUES ('448b5a65-42c9-48a3-8a3e-615eb5cdb1dc', 'b125dbb8-459e-409f-bd1f-d17966568eda', '8f393e44-b585-4875-866d-71f88fea9659', '2020-01-08 15:31:57');
INSERT INTO `sys_role_permission` VALUES ('45e77de7-808f-4916-9273-338cb6d6606d', 'fc674bde-29ee-4160-bd97-00761357f019', '24b7b13c-f00f-4e6b-a221-fe2d780e4d4f', '2020-02-22 23:27:39');
INSERT INTO `sys_role_permission` VALUES ('496ba5e5-7250-4e11-b5ec-1ad9a5ef533d', '0d768668-bb73-4119-97c5-83c92e574495', '8f393e44-b585-4875-866d-71f88fea9659', '2020-02-22 20:47:15');
INSERT INTO `sys_role_permission` VALUES ('4bb0b32c-cf11-4d4d-9372-f7222380a1e8', '0d768668-bb73-4119-97c5-83c92e574495', 'c198d1cb-ad4d-4001-9375-9ec8ee04053d', '2020-02-22 20:47:15');
INSERT INTO `sys_role_permission` VALUES ('526d063b-3ef0-40c7-8880-ff580e009ba0', 'b125dbb8-459e-409f-bd1f-d17966568eda', 'c198d1cb-ad4d-4001-9375-9ec8ee04053d', '2020-01-08 15:31:57');
INSERT INTO `sys_role_permission` VALUES ('5601b594-549d-4e25-914a-0341593f1b0b', 'b125dbb8-459e-409f-bd1f-d17966568eda', '84b9b525-aa44-4b16-9900-adca26115a37', '2020-01-08 15:31:57');
INSERT INTO `sys_role_permission` VALUES ('5b860e04-0f0d-4f31-bdc2-059539c07859', 'b125dbb8-459e-409f-bd1f-d17966568eda', '9ce621a0-ee2c-4cf6-b7bd-012a1a01ba63', '2020-01-08 15:31:57');
INSERT INTO `sys_role_permission` VALUES ('692af1e4-576f-44ef-932c-b8dc39693d09', '0d768668-bb73-4119-97c5-83c92e574495', 'd41c2bc3-454c-4f62-84fe-97825d5cf8a7', '2020-02-22 20:47:15');
INSERT INTO `sys_role_permission` VALUES ('6a0256d8-9509-48e1-a2e5-a394faeb652f', 'b125dbb8-459e-409f-bd1f-d17966568eda', '03dc1cbf-ef44-4326-a7dc-10fe16d22dad', '2020-01-08 15:31:57');
INSERT INTO `sys_role_permission` VALUES ('6c5459b1-8bbf-43ea-940f-b0cabee7452b', '0d768668-bb73-4119-97c5-83c92e574495', '290c0240-0914-487c-b4e9-6635bf5ebfec', '2020-02-22 20:47:15');
INSERT INTO `sys_role_permission` VALUES ('6d9a5208-0a3c-4e49-8b5c-4e075d5dd07e', '0d768668-bb73-4119-97c5-83c92e574495', '346df872-8964-4455-8afd-ffa6308fb18a', '2020-02-22 20:47:15');
INSERT INTO `sys_role_permission` VALUES ('77a95086-d196-402e-9363-14e66ae5b01f', 'b125dbb8-459e-409f-bd1f-d17966568eda', '013095aa-0f4d-4c32-b30e-229a587e52ad', '2020-01-08 15:31:57');
INSERT INTO `sys_role_permission` VALUES ('88e3646d-65b0-41ae-af9c-bfe3480cd0fc', '0d768668-bb73-4119-97c5-83c92e574495', 'e136cc74-9817-4ef1-b181-8f1afd7e102c', '2020-02-22 20:47:15');
INSERT INTO `sys_role_permission` VALUES ('89c3f1b3-9ca5-4764-a4ee-ee514b895f14', 'b125dbb8-459e-409f-bd1f-d17966568eda', '0545d9d1-c82c-44c2-85e5-0b6ac4042515', '2020-01-08 15:31:57');
INSERT INTO `sys_role_permission` VALUES ('8ba8b35f-371a-4913-8b90-8b940aa99327', 'b125dbb8-459e-409f-bd1f-d17966568eda', '27acf73b-2fcb-451b-bdc4-e11e5ab41e2a', '2020-01-08 15:31:57');
INSERT INTO `sys_role_permission` VALUES ('8d957152-a275-4f1e-a65a-0c1d6d083b8d', 'b125dbb8-459e-409f-bd1f-d17966568eda', '346df872-8964-4455-8afd-ffa6308fb18a', '2020-01-08 15:31:57');
INSERT INTO `sys_role_permission` VALUES ('93e6e9ec-6296-409d-b424-889db695df92', 'b125dbb8-459e-409f-bd1f-d17966568eda', '65734896-90c5-4b48-b9e8-dee47a74a297', '2020-01-08 15:31:57');
INSERT INTO `sys_role_permission` VALUES ('9b0d6463-3c8c-4dd8-8c44-ee8e95c27900', '0d768668-bb73-4119-97c5-83c92e574495', '013095aa-0f4d-4c32-b30e-229a587e52ad', '2020-02-22 20:47:15');
INSERT INTO `sys_role_permission` VALUES ('9db74eaa-ff2c-4239-ba10-123bee138698', 'b125dbb8-459e-409f-bd1f-d17966568eda', '7141c2e9-6d50-46b6-94e8-100466b7249f', '2020-01-08 15:31:57');
INSERT INTO `sys_role_permission` VALUES ('a11711d6-cf63-4813-8998-9e17ad377fcf', '0d768668-bb73-4119-97c5-83c92e574495', '84b9b525-aa44-4b16-9900-adca26115a37', '2020-02-22 20:47:15');
INSERT INTO `sys_role_permission` VALUES ('a18e390e-eb93-48c8-aa2b-f98922841e6e', '0d768668-bb73-4119-97c5-83c92e574495', '90b3be91-5e9d-42f8-81fb-0c9ef3014faa', '2020-02-22 20:47:15');
INSERT INTO `sys_role_permission` VALUES ('a3988c61-039b-4949-b0b3-33e3178eda34', 'b125dbb8-459e-409f-bd1f-d17966568eda', '2eeaa020-74d5-4c4b-9849-2cf4bd68fed9', '2020-01-08 15:31:57');
INSERT INTO `sys_role_permission` VALUES ('a5fbc470-da7d-4b5e-bc95-0a003f7548bd', 'b125dbb8-459e-409f-bd1f-d17966568eda', '390ded0e-9f48-40a7-a841-791c203f22ae', '2020-01-08 15:31:57');
INSERT INTO `sys_role_permission` VALUES ('ab2ecf67-2dab-49b8-8079-4644fd4fa63f', '0d768668-bb73-4119-97c5-83c92e574495', 'b7348d63-c4d3-406d-9e46-543346674275', '2020-02-22 20:47:15');
INSERT INTO `sys_role_permission` VALUES ('b9f9b1f6-e74d-4e2e-81b0-d5f6e0a34deb', 'b125dbb8-459e-409f-bd1f-d17966568eda', 'b7348d63-c4d3-406d-9e46-543346674275', '2020-01-08 15:31:57');
INSERT INTO `sys_role_permission` VALUES ('be41d718-8409-46bb-92f6-819b98bb7fc5', 'b125dbb8-459e-409f-bd1f-d17966568eda', 'f9f4d9f4-a2f5-430c-9f2d-6c8e650a8c39', '2020-01-08 15:31:57');
INSERT INTO `sys_role_permission` VALUES ('c4d70d4f-df35-48db-a8e6-984bb016c3a1', 'b125dbb8-459e-409f-bd1f-d17966568eda', '47697e92-e199-4420-a2c2-09ec1b08cb9d', '2020-01-08 15:31:57');
INSERT INTO `sys_role_permission` VALUES ('c973a71c-d1c1-4c57-8e41-c857ee0b0ed6', '0d768668-bb73-4119-97c5-83c92e574495', '2eeaa020-74d5-4c4b-9849-2cf4bd68fed9', '2020-02-22 20:47:15');
INSERT INTO `sys_role_permission` VALUES ('c9c86f8a-f6d8-4e76-bb6d-62963d0e3f1f', '0d768668-bb73-4119-97c5-83c92e574495', '145cb90b-d205-40f6-8a2d-703f41ed1feb', '2020-02-22 20:47:15');
INSERT INTO `sys_role_permission` VALUES ('d1db53e8-e877-43b2-8526-5e85f546f043', 'b125dbb8-459e-409f-bd1f-d17966568eda', '4caeb861-354c-45f6-98b4-59f486beb511', '2020-01-08 15:31:57');
INSERT INTO `sys_role_permission` VALUES ('dd082751-bb56-4228-9ae6-5b062da3fb22', 'b125dbb8-459e-409f-bd1f-d17966568eda', '15bb6aff-2e3b-490a-a21f-0167ae0ebc0d', '2020-01-08 15:31:57');
INSERT INTO `sys_role_permission` VALUES ('deba5a6a-2122-49bc-b0be-ec3bfd1a4326', 'fc674bde-29ee-4160-bd97-00761357f019', '0545d9d1-c82c-44c2-85e5-0b6ac4042515', '2020-02-22 23:27:39');
INSERT INTO `sys_role_permission` VALUES ('e01a3fbd-d79b-4a5a-af1f-f0899246394b', '0d768668-bb73-4119-97c5-83c92e574495', 'f9f4d9f4-a2f5-430c-9f2d-6c8e650a8c39', '2020-02-22 20:47:15');
INSERT INTO `sys_role_permission` VALUES ('e152ae95-4d63-4fcd-a893-7da2b5b27a8a', '0d768668-bb73-4119-97c5-83c92e574495', '65734896-90c5-4b48-b9e8-dee47a74a297', '2020-02-22 20:47:15');
INSERT INTO `sys_role_permission` VALUES ('ed8fa726-ab70-48df-bc30-5aa871b74c31', '0d768668-bb73-4119-97c5-83c92e574495', 'd60faf3e-9a72-49d5-b02d-a67bfeff07fa', '2020-02-22 20:47:15');
INSERT INTO `sys_role_permission` VALUES ('ee7938fa-c73d-436b-8a31-b511ee6b6fe5', '0d768668-bb73-4119-97c5-83c92e574495', '2ae13993-9501-46d5-8473-fe45fee57f3b', '2020-02-22 20:47:15');
INSERT INTO `sys_role_permission` VALUES ('f337606d-8c29-46ec-b0b0-36904a9c6be7', 'b125dbb8-459e-409f-bd1f-d17966568eda', 'd60faf3e-9a72-49d5-b02d-a67bfeff07fa', '2020-01-08 15:31:57');
INSERT INTO `sys_role_permission` VALUES ('f399113a-cd8f-4fb7-a56c-f59b4560d1a6', '0d768668-bb73-4119-97c5-83c92e574495', '2f9a3f67-6ef3-4eac-b9a1-c0e898718d0c', '2020-02-22 20:47:15');
INSERT INTO `sys_role_permission` VALUES ('f408458b-8636-468a-bc61-3453e4884251', '0d768668-bb73-4119-97c5-83c92e574495', '9ce621a0-ee2c-4cf6-b7bd-012a1a01ba63', '2020-02-22 20:47:15');
INSERT INTO `sys_role_permission` VALUES ('f70ce027-3e19-482f-8dec-828f2ef834ea', '0d768668-bb73-4119-97c5-83c92e574495', '47697e92-e199-4420-a2c2-09ec1b08cb9d', '2020-02-22 20:47:15');
INSERT INTO `sys_role_permission` VALUES ('f7ac2256-d3b7-43fa-a6b4-0caff31f7471', 'b125dbb8-459e-409f-bd1f-d17966568eda', 'bb5ca869-0303-4fc0-b067-936cba7d1cc8', '2020-01-08 15:31:57');
INSERT INTO `sys_role_permission` VALUES ('fe865888-b958-45a6-aeba-c7b60ba7d22a', '0d768668-bb73-4119-97c5-83c92e574495', '390ded0e-9f48-40a7-a841-791c203f22ae', '2020-02-22 20:47:15');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(64) NOT NULL COMMENT '用户id',
  `username` varchar(50) NOT NULL COMMENT '账户名称',
  `salt` varchar(20) DEFAULT NULL COMMENT '加密盐值',
  `password` varchar(200) NOT NULL COMMENT '用户密码密文',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `dept_id` varchar(64) DEFAULT NULL COMMENT '部门id',
  `real_name` varchar(60) DEFAULT NULL COMMENT '真实名称',
  `nick_name` varchar(60) DEFAULT NULL COMMENT '昵称',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱(唯一)',
  `status` tinyint(4) DEFAULT '1' COMMENT '账户状态(1.正常 2.锁定 )',
  `sex` tinyint(4) DEFAULT '1' COMMENT '性别(1.男 2.女)',
  `deleted` tinyint(4) DEFAULT '1' COMMENT '是否删除(1未删除；0已删除)',
  `create_id` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_id` varchar(64) DEFAULT NULL COMMENT '更新人',
  `create_where` tinyint(4) DEFAULT '1' COMMENT '创建来源(1.web 2.android 3.ios )',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('fcf34b56-a7a2-4719-9236-867495e74c31', 'admin', 'c1f4a8a78e7d4186ac0b', '79533cf45e1cb76fa2ff58fead3ea319', '13287911665', '06e8066d-fbb9-4904-b71a-e75fc06b19a6', '张全蛋', '张全蛋', '1256050739@qq.com', '1', '1', '1', 'fcf34b56-a7a2-4719-9236-867495e74c31', 'fcf34b56-a7a2-4719-9236-867495e74c31', '3', '2019-09-22 19:38:05', '2020-02-22 19:42:15');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户id',
  `role_id` varchar(64) DEFAULT NULL COMMENT '角色id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('76951c25-9c4a-4da4-9c71-6587c81b26b7', 'fcf34b56-a7a2-4719-9236-867495e74c31', 'b125dbb8-459e-409f-bd1f-d17966568eda', '2020-01-08 15:32:24');
