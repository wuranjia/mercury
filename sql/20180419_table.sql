-- sim卡流量消耗，按月统计
ALTER TABLE `mercury`.`t_m_sms_deliver`
CHANGE COLUMN `msg_id` `msg_id` VARCHAR(64) NOT NULL DEFAULT '-1' COMMENT '短信ID' ;

drop table t_m_sms_view;


CREATE TABLE `t_m_sms_view` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sms_id` bigint(20)  NOT NULL default -1 COMMENT '短信ID',
  `sms_content` varchar(2048) NOT NULL DEFAULT '-1' COMMENT '消息内容',
  `service_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '业务标识，接收、发送',
  `sim_no` varchar(32) NOT NULL DEFAULT '-1' COMMENT '终端sim卡号',
  `memo` varchar(512) NOT NULL DEFAULT '-1' COMMENT '备注',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(64) NOT NULL DEFAULT '-1' COMMENT '创建人',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_by` varchar(64) NOT NULL DEFAULT '-1' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8


ALTER TABLE `mercury`.`t_m_sim_base`
ADD COLUMN `cal_day` VARCHAR(45) NULL AFTER `flow_use_month`;

------------



------------

CREATE TABLE `t_m_sim_` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '短信ID',
  `sim_id` varchar(64) NOT NULL DEFAULT '-1' COMMENT 'sim卡号',
  `flow_use` decimal(9,4) NOT NULL DEFAULT '-1' COMMENT '消耗流量',
  `month` varchar(32) NOT NULL DEFAULT '-1' COMMENT '月份 yyyy_mm',
  `cal_day` varchar(32) NOT NULL DEFAULT '-1' COMMENT '日期 yyyy_mm_dd',
  `memo` varchar(512) NOT NULL DEFAULT '-1' COMMENT '备注',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(64) NOT NULL DEFAULT '-1' COMMENT '创建人',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_by` varchar(64) NOT NULL DEFAULT '-1' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- sim卡

