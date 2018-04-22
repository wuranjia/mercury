-- 消息信息表
CREATE TABLE `t_m_sms` (
  `sms_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '短信ID',
  `user_id` bigint(20) NOT NULL DEFAULT -1 COMMENT '发送用户ID',
  `receive_info` varchar(2048) NOT NULL DEFAULT '-1' COMMENT '接收方信息',
  `sms_content` varchar(2048) NOT NULL DEFAULT '-1' COMMENT '消息内容',
  `sms_other` varchar(512) NOT NULL DEFAULT '-1' COMMENT '消息其他信息，参数之类',
  `status` int(2) NOT NULL DEFAULT -1 COMMENT '1.发送中、2.发送成功、3.发送失败',
  `memo` varchar(512) NOT NULL DEFAULT '-1' COMMENT '备注',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(64) NOT NULL DEFAULT '-1' COMMENT '创建人',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_by` varchar(64) NOT NULL DEFAULT '-1' COMMENT '修改人',
  PRIMARY KEY (`sms_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;