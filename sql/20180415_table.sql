UPDATE `mercury`.`t_m_menu` SET `menu_url`='#' WHERE `menu_id`='4';
INSERT INTO `mercury`.`t_m_menu` (`menu_id`, `parent_id`, `menu_name`, `menu_url`, `valid`, `memo`, `created_time`, `created_by`, `updated_time`, `updated_by`) VALUES ('8', '4', '我的卡片', '/fe/card/my', '1', '-1', '2018-03-31 19:26:22', '-1', '2018-03-31 19:26:22', '-1');
INSERT INTO `mercury`.`t_m_menu` (`menu_id`, `parent_id`, `menu_name`, `menu_url`, `valid`, `memo`, `created_time`, `created_by`, `updated_time`, `updated_by`) VALUES ('9', '4', '待续费卡', 'fe/card/pay', '1', '-1', '2018-03-31 19:26:22', '-1', '2018-03-31 19:26:22', '-1');
INSERT INTO `mercury`.`t_m_menu` (`menu_id`, `parent_id`, `menu_name`, `menu_url`, `valid`, `memo`, `created_time`, `created_by`, `updated_by`) VALUES ('10', '4', '短信统计', 'fe/card/sms', '1', '-1', '2018-03-31 19:26:22', '-1', '-1');
INSERT INTO `mercury`.`t_m_menu_ref` (`id`, `menu_id`, `user_id`) VALUES ('8', '8', '1');
INSERT INTO `mercury`.`t_m_menu_ref` (`id`, `menu_id`, `user_id`) VALUES ('9', '9', '1');
INSERT INTO `mercury`.`t_m_menu_ref` (`id`, `menu_id`, `user_id`) VALUES ('10', '10', '1');


-- 卡信息-base表
CREATE TABLE `t_m_sim_base` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sim_id` bigint(20) NOT NULL DEFAULT -1 COMMENT '卡号',
  `iccid` varchar(48) NOT NULL DEFAULT '-1' COMMENT 'ICCID',
  `imsi` varchar(48) NOT NULL DEFAULT '-1' COMMENT 'imsi',
  `communication` varchar(64) NOT NULL DEFAULT '-1' COMMENT '运营商',
  `suit_id` bigint(32) NOT NULL DEFAULT '-1' COMMENT '套餐类型',
  `suit_name` varchar(64) NOT NULL DEFAULT '-1' COMMENT '套餐展示名称',
  `flow_total` decimal(9,4) NOT NULL DEFAULT '-1' COMMENT '总流量',
  `flow_use` decimal(9,4) NOT NULL DEFAULT '-1' COMMENT '使用流量',
  `sms_use` bigint(20) NOT NULL DEFAULT '-1' COMMENT '消耗短信',
  `limit_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '预计到期日期',

  `activate_status` varchar(2) NOT NULL DEFAULT '-1' COMMENT '激活状态,0激活，1未激活',
  `activate_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '激活日期',
  `over_flow` varchar(2) NOT NULL DEFAULT '-1' COMMENT '超流量,0未超流量，1超流量',
  `over_sms` varchar(2) NOT NULL DEFAULT '-1' COMMENT '超短信,0未超短信，1超短信',
  `flag_expire` varchar(2) NOT NULL DEFAULT '-1' COMMENT '已过期标记，0未过期，1过期',
  `flag_near_expire` varchar(2) NOT NULL DEFAULT '-1' COMMENT '将过期，0未将过期,大于7天，1将过期，小于7天',
  `status_deliver` varchar(2) NOT NULL DEFAULT '-1' COMMENT '开关机状态，0开机，1，关机',
  `status_online` varchar(2) NOT NULL DEFAULT '-1' COMMENT '在线状态，0在线，1离线',
  `status_arrearage` varchar(2) NOT NULL DEFAULT '-1' COMMENT '是否欠费，0欠费，1正常',
  `supplier` bigint(20) NOT NULL DEFAULT -1 COMMENT '供应商',
  `status` varchar(2) NOT NULL DEFAULT '-1' COMMENT '状态，0整除，1不正常',


  `memo` varchar(512) NOT NULL DEFAULT '-1' COMMENT '备注',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(64) NOT NULL DEFAULT '-1' COMMENT '创建人',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_by` varchar(64) NOT NULL DEFAULT '-1' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;