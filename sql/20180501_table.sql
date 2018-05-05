-- 产品信息表

CREATE TABLE `t_m_product` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) NOT NULL DEFAULT '-1' COMMENT '产品名称',
  `flow` decimal(9,4) NOT NULL DEFAULT -1 COMMENT '月流量',
  `type` varchar(32) NOT NULL DEFAULT '-1' COMMENT '类型，1季卡 2年卡',
  `price` decimal(9,4) NOT NULL DEFAULT -1 COMMENT '单价',
  `memo` varchar(512) NOT NULL DEFAULT '-1' COMMENT '备注',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(64) NOT NULL DEFAULT '-1' COMMENT '创建人',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_by` varchar(64) NOT NULL DEFAULT '-1' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `mercury`.`t_m_product`
CHANGE COLUMN `flow` `flow` VARCHAR(32) NOT NULL DEFAULT '-1.0000' COMMENT '月流量' ;


-- 订单信息表
CREATE TABLE `t_m_order` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) NOT NULL DEFAULT '-1' COMMENT '产品名称',
  `product_id` bigint(20) NOT NULL DEFAULT -1 COMMENT '产品ID',
  `num` bigint(20) NOT NULL DEFAULT -1 COMMENT '数量 个',
  `time_long` bigint(20) NOT NULL DEFAULT -1 COMMENT '时长 月',
  `total` decimal(9,4) NOT NULL DEFAULT -1 COMMENT '总价 元',
  `price` decimal(9,4) NOT NULL DEFAULT -1 COMMENT '单价 元',
  `buyer` bigint(20) NOT NULL DEFAULT '-1' COMMENT '购买人ID',
  `seller` bigint(20) NOT NULL DEFAULT '-1' COMMENT '售卖人ID',
  `status` bigint(20) NOT NULL DEFAULT '-1' COMMENT '订单状态  10 已下单、待支付  20 已调用支付接口、支付中  30 已支付成功  40 支付失败、待重试  99 订单取消',
  `trans_num` varchar(30) NOT NULL DEFAULT '-1' COMMENT '物流订单号',
  `trans_person` varchar(30) NOT NULL DEFAULT '-1' COMMENT '物流联系人',
  `trans_phone` varchar(30) NOT NULL DEFAULT '-1' COMMENT '联系人电话',
  `trans_status` varchar(30) NOT NULL DEFAULT '-1' COMMENT '物流状态 10 未出库  20 已出库',
  `trans_address` varchar(512) NOT NULL DEFAULT '-1' COMMENT '收件地址',
  `op_user_name` varchar(512) NOT NULL DEFAULT '-1' COMMENT '经办人',
  `trans_fee` decimal(9,4) NOT NULL DEFAULT '-1' COMMENT '邮费',
  `memo` varchar(512) NOT NULL DEFAULT '-1' COMMENT '备注',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(64) NOT NULL DEFAULT '-1' COMMENT '创建人',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_by` varchar(64) NOT NULL DEFAULT '-1' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 库存
CREATE TABLE `t_m_store` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '订单信息',
  `product_id` bigint(20) NOT NULL DEFAULT -1 COMMENT '产品信息',
  `store_type` varchar(32) NOT NULL DEFAULT '-1' COMMENT '类型，1出库 2入库',
  `card_num` bigint(20) NOT NULL DEFAULT -1 COMMENT '卡片数量',
  `price` decimal(9,4) NOT NULL DEFAULT -1 COMMENT '单价',
  `buyer` bigint(20) NOT NULL DEFAULT '-1' COMMENT '购买人ID',
  `seller` bigint(20) NOT NULL DEFAULT '-1' COMMENT '售卖人ID',
  `total` decimal(9,4) NOT NULL DEFAULT -1 COMMENT '总价',
  `trans_num` varchar(30) NOT NULL DEFAULT '-1' COMMENT '物流订单号',
  `name` varchar(64) NOT NULL DEFAULT '-1' COMMENT '产品名称',
  `flow` decimal(9,4) NOT NULL DEFAULT -1 COMMENT '月流量',
  `product_type` varchar(32) NOT NULL DEFAULT '-1' COMMENT '类型，1季卡 2年卡',
  `memo` varchar(512) NOT NULL DEFAULT '-1' COMMENT '备注',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(64) NOT NULL DEFAULT '-1' COMMENT '创建人',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_by` varchar(64) NOT NULL DEFAULT '-1' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 库存详细
CREATE TABLE `t_m_store_detail` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `store_id` varchar(64) NOT NULL DEFAULT '-1' COMMENT '库存ID',
  `order_id` decimal(9,4) NOT NULL DEFAULT -1 COMMENT '订单Id',
  `sim_id` bigint(20) NOT NULL DEFAULT -1 COMMENT 'sim',
  `iccid` varchar(48) NOT NULL DEFAULT '-1' COMMENT 'iccid',
  `imsi` varchar(48) NOT NULL DEFAULT '-1' COMMENT 'imsi',
  `memo` varchar(512) NOT NULL DEFAULT '-1' COMMENT '备注',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(64) NOT NULL DEFAULT '-1' COMMENT '创建人',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_by` varchar(64) NOT NULL DEFAULT '-1' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;