-- 接收信息表
CREATE TABLE `t_m_sms_deliver` (
  `sms_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '短信ID',
  `msg_id` bigint(20) NOT NULL DEFAULT -1 COMMENT '短信ID',
  `dst_id` varchar(512) NOT NULL DEFAULT '-1' COMMENT '目的地号码',
  `sms_content` varchar(2048) NOT NULL DEFAULT '-1' COMMENT '消息内容',
  `service_id` varchar(512) NOT NULL DEFAULT '-1' COMMENT '业务标识',
  `src_termId` varchar(32) NOT NULL DEFAULT '-1' COMMENT '终端sim卡号',
  `src_termType` varchar(2) NOT NULL DEFAULT '-1' COMMENT '终端sim卡类型',
  `memo` varchar(512) NOT NULL DEFAULT '-1' COMMENT '备注',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(64) NOT NULL DEFAULT '-1' COMMENT '创建人',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_by` varchar(64) NOT NULL DEFAULT '-1' COMMENT '修改人',
  PRIMARY KEY (`sms_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;