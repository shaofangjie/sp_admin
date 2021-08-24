SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT,
 `user_name` varchar(100) NOT NULL,
 `password` varchar(255) NOT NULL,
 `nick_name` varchar(100) DEFAULT NULL,
 `role_id` bigint(20) DEFAULT NULL,
 `enabled` tinyint(1) NOT NULL DEFAULT '0',
 `is_lock` tinyint(1) NOT NULL DEFAULT '0',
 `version` bigint(20) NOT NULL,
 `when_created` datetime(6) NOT NULL,
 `when_updated` datetime(6) NOT NULL,
 PRIMARY KEY (`id`),
 UNIQUE KEY `uq_admin_user_name` (`user_name`),
 KEY `ix_admin_user_name` (`user_name`),
 KEY `ix_admin_when_created` (`when_created`),
 KEY `ix_admin_when_updated` (`when_updated`),
 KEY `ix_admin_role_id` (`role_id`),
 CONSTRAINT `fk_admin_role_id` FOREIGN KEY (`role_id`) REFERENCES `admin_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of admin
-- ----------------------------
BEGIN;
INSERT INTO `admin` VALUES (1, 'admin', '465c194afb65670f38322df087f0a9bb225cc257e43eb4ac5a0c98ef5b3173ac', '超级管理员', 1, 1, 1, 1, '2021-08-20 21:02:03.370000', '2021-08-20 21:02:03.370000');
COMMIT;

-- ----------------------------
-- Table structure for admin_resources
-- ----------------------------
DROP TABLE IF EXISTS `admin_resources`;
CREATE TABLE `admin_resources` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`source_pid` bigint(20) DEFAULT NULL,
`source_type` int(11) NOT NULL DEFAULT '0',
`iconfont` varchar(100) DEFAULT NULL,
`source_name` varchar(100) NOT NULL,
`source_url` varchar(255) DEFAULT NULL,
`source_function` varchar(255) DEFAULT NULL,
`enabled` tinyint(1) NOT NULL DEFAULT '0',
`source_order` int(11) NOT NULL DEFAULT '0',
`is_lock` tinyint(1) NOT NULL DEFAULT '0',
`version` bigint(20) NOT NULL,
`when_created` datetime(6) NOT NULL,
`when_updated` datetime(6) NOT NULL,
PRIMARY KEY (`id`),
KEY `ix_admin_resources_when_created` (`when_created`),
KEY `ix_admin_resources_when_updated` (`when_updated`),
KEY `ix_admin_resources_source_pid` (`source_pid`),
CONSTRAINT `fk_admin_resources_source_pid` FOREIGN KEY (`source_pid`) REFERENCES `admin_resources` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of admin_resources
-- ----------------------------
BEGIN;
INSERT INTO `admin_resources` VALUES (1, NULL, 0, 'layui-icon-group', '权限管理', NULL, NULL, 1, 1, 1, 1, '2021-08-20 21:02:03.323000', '2021-08-20 21:02:03.323000');
INSERT INTO `admin_resources` VALUES (2, 1, 1, 'layui-icon-right', '管理员管理', '/authority/Admin/index', 'authority.AdminController.index', 1, 1, 1, 1, '2021-08-20 21:02:03.330000', '2021-08-20 21:02:03.330000');
INSERT INTO `admin_resources` VALUES (3, 2, 2, NULL, '管理员列表', '/authority/Admin/list', 'authority.AdminController.list', 1, 1, 1, 1, '2021-08-20 21:02:03.331000', '2021-08-20 21:02:03.331000');
INSERT INTO `admin_resources` VALUES (4, 2, 2, NULL, '添加管理员', '/authority/Admin/add', 'authority.AdminController.add', 1, 2, 1, 1, '2021-08-20 21:02:03.333000', '2021-08-20 21:02:03.333000');
INSERT INTO `admin_resources` VALUES (5, 2, 2, NULL, '修改管理员', '/authority/Admin/edit', 'authority.AdminController.edit', 1, 3, 1, 1, '2021-08-20 21:02:03.334000', '2021-08-20 21:02:03.334000');
INSERT INTO `admin_resources` VALUES (6, 2, 2, NULL, '删除管理员', '/authority/Admin/del', 'authority.AdminController.del', 1, 4, 1, 1, '2021-08-20 21:02:03.335000', '2021-08-20 21:02:03.335000');
INSERT INTO `admin_resources` VALUES (7, 1, 1, 'layui-icon-right', '权限角色', '/authority/AdminRole/index', 'authority.AdminRoleController.index', 1, 1, 1, 1, '2021-08-20 21:02:03.336000', '2021-08-20 21:02:03.336000');
INSERT INTO `admin_resources` VALUES (8, 7, 2, NULL, '权限角色列表', '/authority/AdminRole/list', 'authority.AdminRoleController.list', 1, 1, 1, 1, '2021-08-20 21:02:03.338000', '2021-08-20 21:02:03.338000');
INSERT INTO `admin_resources` VALUES (9, 7, 2, NULL, '添加权限角色', '/authority/AdminRole/add', 'authority.AdminRoleController.add', 1, 1, 1, 1, '2021-08-20 21:02:03.340000', '2021-08-20 21:02:03.340000');
INSERT INTO `admin_resources` VALUES (10, 7, 2, NULL, '修改权限角色', '/authority/AdminRole/edit', 'authority.AdminRoleController.edit', 1, 1, 1, 1, '2021-08-20 21:02:03.344000', '2021-08-20 21:02:03.344000');
INSERT INTO `admin_resources` VALUES (11, 7, 2, NULL, '删除权限角色', '/authority/AdminRole/del', 'authority.AdminRoleController.del', 1, 1, 1, 1, '2021-08-20 21:02:03.345000', '2021-08-20 21:02:03.345000');
INSERT INTO `admin_resources` VALUES (12, 1, 1, 'layui-icon-right', '权限资源', '/authority/AdminResources/index', 'authority.AdminResourcesController.index', 1, 2, 1, 1, '2021-08-20 21:02:03.347000', '2021-08-20 21:02:03.347000');
INSERT INTO `admin_resources` VALUES (13, 12, 2, NULL, '权限资源列表', '/authority/AdminResources/list', 'authority.AdminResourcesController.list', 1, 2, 1, 1, '2021-08-20 21:02:03.348000', '2021-08-20 21:02:03.348000');
INSERT INTO `admin_resources` VALUES (14, 12, 2, NULL, '添加权限资源', '/authority/AdminResources/add', 'authority.AdminResourcesController.add', 1, 3, 1, 1, '2021-08-20 21:02:03.349000', '2021-08-20 21:02:03.349000');
INSERT INTO `admin_resources` VALUES (15, 12, 2, NULL, '修改权限资源', '/authority/AdminResources/edit', 'authority.AdminResourcesController.edit', 1, 4, 1, 1, '2021-08-20 21:02:03.350000', '2021-08-20 21:02:03.350000');
INSERT INTO `admin_resources` VALUES (16, 12, 2, NULL, '删除权限资源', '/authority/AdminResources/del', 'authority.AdminResourcesController.del', 1, 5, 1, 1, '2021-08-20 21:02:03.351000', '2021-08-20 21:02:03.351000');
COMMIT;

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`role_name` varchar(100) NOT NULL,
`is_lock` tinyint(1) NOT NULL DEFAULT '0',
`version` bigint(20) NOT NULL,
`when_created` datetime(6) NOT NULL,
`when_updated` datetime(6) NOT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `uq_admin_role_role_name` (`role_name`),
KEY `ix_admin_role_when_created` (`when_created`),
KEY `ix_admin_role_when_updated` (`when_updated`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of admin_role
-- ----------------------------
BEGIN;
INSERT INTO `admin_role` VALUES (1, '超级管理员', 1, 1, '2021-08-20 21:02:03.353000', '2021-08-20 21:02:03.353000');
COMMIT;

-- ----------------------------
-- Table structure for admin_role_resources
-- ----------------------------
DROP TABLE IF EXISTS `admin_role_resources`;
CREATE TABLE `admin_role_resources` (
`resource_id` bigint(20) NOT NULL,
`role_id` bigint(20) NOT NULL,
PRIMARY KEY (`resource_id`,`role_id`),
KEY `ix_admin_role_resources_admin_resources` (`resource_id`),
KEY `ix_admin_role_resources_admin_role` (`role_id`),
CONSTRAINT `fk_admin_role_resources_admin_resources` FOREIGN KEY (`resource_id`) REFERENCES `admin_resources` (`id`),
CONSTRAINT `fk_admin_role_resources_admin_role` FOREIGN KEY (`role_id`) REFERENCES `admin_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of admin_role_resources
-- ----------------------------
BEGIN;
INSERT INTO `admin_role_resources` VALUES (1, 1);
INSERT INTO `admin_role_resources` VALUES (2, 1);
INSERT INTO `admin_role_resources` VALUES (3, 1);
INSERT INTO `admin_role_resources` VALUES (4, 1);
INSERT INTO `admin_role_resources` VALUES (5, 1);
INSERT INTO `admin_role_resources` VALUES (6, 1);
INSERT INTO `admin_role_resources` VALUES (7, 1);
INSERT INTO `admin_role_resources` VALUES (8, 1);
INSERT INTO `admin_role_resources` VALUES (9, 1);
INSERT INTO `admin_role_resources` VALUES (10, 1);
INSERT INTO `admin_role_resources` VALUES (11, 1);
INSERT INTO `admin_role_resources` VALUES (12, 1);
INSERT INTO `admin_role_resources` VALUES (13, 1);
INSERT INTO `admin_role_resources` VALUES (14, 1);
INSERT INTO `admin_role_resources` VALUES (15, 1);
INSERT INTO `admin_role_resources` VALUES (16, 1);
COMMIT;


SET FOREIGN_KEY_CHECKS = 1;
