-- 信息展示列表
CREATE TABLE `t_m_sms_view` (
  `sms_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '短信ID',
  `sms_content` varchar(2048) NOT NULL DEFAULT '-1' COMMENT '消息内容',
  `service_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '业务标识，接收、发送',
  `sim_no` varchar(32) NOT NULL DEFAULT '-1' COMMENT '终端sim卡号',
  `memo` varchar(512) NOT NULL DEFAULT '-1' COMMENT '备注',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(64) NOT NULL DEFAULT '-1' COMMENT '创建人',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_by` varchar(64) NOT NULL DEFAULT '-1' COMMENT '修改人',
  PRIMARY KEY (`sms_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE `mercury`.`t_m_sim_base`
ADD UNIQUE INDEX `sim_id` (`sim_id` ASC),
ADD INDEX `iccid` (`iccid` ASC),
ADD INDEX `imsi` (`imsi` ASC);

ALTER TABLE `mercury`.`t_m_sim_base`
ADD COLUMN `open_flag` VARCHAR(2) BINARY NOT NULL DEFAULT '-1' COMMENT '是否开卡' AFTER `updated_by`,
ADD COLUMN `open_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开卡日期' AFTER `open_flag`,
ADD COLUMN `flow_use_month` DECIMAL(9,4) NOT NULL DEFAULT 0.0000 COMMENT '当月消耗流量' AFTER `open_date`;
