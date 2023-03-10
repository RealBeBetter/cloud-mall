/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : mall_wms

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 21/10/2022 22:51:18
*/
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mall_wms` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `mall_wms`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid` ASC, `branch_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

-- ----------------------------
-- Table structure for wms_purchase
-- ----------------------------
DROP TABLE IF EXISTS `wms_purchase`;
CREATE TABLE `wms_purchase`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `assignee_id` bigint NULL DEFAULT NULL,
  `assignee_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone` char(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `priority` int NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  `ware_id` bigint NULL DEFAULT NULL,
  `amount` decimal(18, 4) NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '????????????' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wms_purchase
-- ----------------------------
INSERT INTO `wms_purchase` VALUES (1, 2, 'fireflynay', '18156475879', 1, 3, 1, 149700.0000, '2020-06-07 00:34:32', '2020-06-07 15:55:06');
INSERT INTO `wms_purchase` VALUES (2, 1, 'admin', '18173516309', 1, 3, 1, 177760.0000, '2020-06-07 00:55:43', '2020-06-07 14:14:47');
INSERT INTO `wms_purchase` VALUES (3, 1, 'admin', '18173516309', 1, 3, 1, 297520.0000, '2020-06-07 13:33:08', '2020-06-07 15:21:43');
INSERT INTO `wms_purchase` VALUES (4, 2, 'fireflynay', '18156475879', 1, 3, 1, 179640.0000, '2020-06-07 14:01:10', '2020-06-07 15:18:35');

-- ----------------------------
-- Table structure for wms_purchase_detail
-- ----------------------------
DROP TABLE IF EXISTS `wms_purchase_detail`;
CREATE TABLE `wms_purchase_detail`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `purchase_id` bigint NULL DEFAULT NULL COMMENT '?????????id',
  `sku_id` bigint NULL DEFAULT NULL COMMENT '????????????id',
  `sku_num` int NULL DEFAULT NULL COMMENT '????????????',
  `sku_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '????????????',
  `ware_id` bigint NULL DEFAULT NULL COMMENT '??????id',
  `status` int NULL DEFAULT NULL COMMENT '??????[0?????????1????????????2???????????????3????????????4????????????]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wms_purchase_detail
-- ----------------------------
INSERT INTO `wms_purchase_detail` VALUES (1, 1, 2, 10, 88880.0000, 1, 3);
INSERT INTO `wms_purchase_detail` VALUES (2, 3, 2, 20, 177760.0000, 1, 3);
INSERT INTO `wms_purchase_detail` VALUES (3, 3, 3, 5, 29940.0000, 1, 3);
INSERT INTO `wms_purchase_detail` VALUES (4, 3, 3, 15, 89820.0000, 1, 3);
INSERT INTO `wms_purchase_detail` VALUES (5, 4, 4, 30, 179640.0000, 1, 3);
INSERT INTO `wms_purchase_detail` VALUES (6, 1, 5, 25, 149700.0000, 1, 3);

-- ----------------------------
-- Table structure for wms_ware_info
-- ----------------------------
DROP TABLE IF EXISTS `wms_ware_info`;
CREATE TABLE `wms_ware_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '?????????',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '????????????',
  `areacode` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '????????????' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wms_ware_info
-- ----------------------------
INSERT INTO `wms_ware_info` VALUES (1, '1?????????', '?????????', '410000');

-- ----------------------------
-- Table structure for wms_ware_order_task
-- ----------------------------
DROP TABLE IF EXISTS `wms_ware_order_task`;
CREATE TABLE `wms_ware_order_task`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint NULL DEFAULT NULL COMMENT 'order_id',
  `order_sn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'order_sn',
  `consignee` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '?????????',
  `consignee_tel` char(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '???????????????',
  `delivery_address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '????????????',
  `order_comment` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '????????????',
  `payment_way` tinyint(1) NULL DEFAULT NULL COMMENT '??????????????? 1:???????????? 2:???????????????',
  `task_status` tinyint NULL DEFAULT NULL COMMENT '????????????',
  `order_body` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '????????????',
  `tracking_no` char(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '????????????',
  `create_time` datetime NULL DEFAULT NULL COMMENT 'create_time',
  `ware_id` bigint NULL DEFAULT NULL COMMENT '??????id',
  `task_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '???????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '???????????????' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wms_ware_order_task
-- ----------------------------
INSERT INTO `wms_ware_order_task` VALUES (1, NULL, '202007101511435951281486241929375746', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (2, NULL, '202007102108315951281576033585246209', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for wms_ware_order_task_detail
-- ----------------------------
DROP TABLE IF EXISTS `wms_ware_order_task_detail`;
CREATE TABLE `wms_ware_order_task_detail`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint NULL DEFAULT NULL COMMENT 'sku_id',
  `sku_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'sku_name',
  `sku_num` int NULL DEFAULT NULL COMMENT '????????????',
  `task_id` bigint NULL DEFAULT NULL COMMENT '?????????id',
  `ware_id` bigint NULL DEFAULT NULL COMMENT '??????id',
  `lock_status` int NULL DEFAULT NULL COMMENT '1-?????????  2-?????????  3-??????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '???????????????' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wms_ware_order_task_detail
-- ----------------------------
INSERT INTO `wms_ware_order_task_detail` VALUES (1, 2, '', 1, 1, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (2, 2, '', 1, 2, 1, 2);

-- ----------------------------
-- Table structure for wms_ware_sku
-- ----------------------------
DROP TABLE IF EXISTS `wms_ware_sku`;
CREATE TABLE `wms_ware_sku`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint NULL DEFAULT NULL COMMENT 'sku_id',
  `ware_id` bigint NULL DEFAULT NULL COMMENT '??????id',
  `stock` int NULL DEFAULT NULL COMMENT '?????????',
  `sku_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'sku_name',
  `stock_locked` int NULL DEFAULT 0 COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sku_id`(`sku_id` ASC) USING BTREE,
  INDEX `ware_id`(`ware_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '????????????' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wms_ware_sku
-- ----------------------------
INSERT INTO `wms_ware_sku` VALUES (3, 2, 1, 50, '?????? HUAWEI P40 Pro+ ??????990 5G  ???????????? ?????????', 0);
INSERT INTO `wms_ware_sku` VALUES (4, 3, 1, 35, '?????? HUAWEI P40 Pro+ ??????990 5G  ???????????? ?????????', 0);
INSERT INTO `wms_ware_sku` VALUES (5, 4, 1, 60, '?????? HUAWEI P40 Pro+ ??????990 5G  ????????? ?????????', 0);
INSERT INTO `wms_ware_sku` VALUES (6, 5, 1, 125, '?????? HUAWEI P40 Pro+ ??????990 5G  ????????? ?????????', 0);

SET FOREIGN_KEY_CHECKS = 1;
