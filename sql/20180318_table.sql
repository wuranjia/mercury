--1.创建db
create schema mercury default CHARACTER  set utf8 COLLATE  utf8_general_ci;

--2.创建用户并赋权
create user 'user1'@'%' identified by '[123456]';--创建用户

grant select,insert,update,delete,create on mercury.* to user1;

flush PRIVILEGES;

--3.建表
/*用户ID
	上级客户
	登录名
	登录密码
	支付密码
	用户名称
	联系方式
	公司名称
	常用地址
	备注
	创建时间
	创建人
	修改时间
	修改人*/
-- 用户信息表
CREATE TABLE `t_m_user` (
  `user_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `parent_id` bigint(20) NOT NULL DEFAULT -1 COMMENT '上级客户',
  `user_name` varchar(32) NOT NULL DEFAULT '-1' COMMENT '用户名称',
  `login_pwd` varchar(512) NOT NULL DEFAULT '-1' COMMENT '登录密码',
  `pay_pwd` varchar(512) NOT NULL DEFAULT '-1' COMMENT '支付密码',
  `phone` varchar(64) NOT NULL DEFAULT '-1' COMMENT '联系方式',
  `company` varchar(512) NOT NULL DEFAULT '-1' COMMENT '公司名称',
  `address` varchar(512) NOT NULL DEFAULT '-1' COMMENT '常用地址',
  `memo` varchar(512) NOT NULL DEFAULT '-1' COMMENT '备注',
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(64) NOT NULL DEFAULT '-1' COMMENT '创建人',
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_by` varchar(64) NOT NULL DEFAULT '-1' COMMENT '修改人',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000000000 DEFAULT CHARSET=utf8;

-- 用户心跳表
/*
	心跳ID
	用户ID
	心跳时间
	rest行为
	类型0-登录 1-登出

	规则：
		最新一条类型为0，校验心跳时间距离当前时间
			大于30min，需要登录
			小于30min，继续访问

		最新一条类型为1，需要登录
*/

CREATE TABLE `t_m_user_heartbeat` (
  `hb_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '心跳ID',
  `user_id` bigint(20) NOT NULL DEFAULT -1 COMMENT '用户ID',
  `hb_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '心跳时间',
  `call_url` varchar(512) NOT NULL DEFAULT '-1' COMMENT '请求路径',
  `hb_type` int(1) NOT NULL DEFAULT -1 COMMENT '心跳类型，0登录、1登出',
  PRIMARY KEY (`hb_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- 1.菜单信息
/*

	菜单ID
	上级菜单
	菜单名称
	菜单url
	菜单是否有效
	备注
	创建时间
	创建人
	修改时间
	修改人
*/
CREATE TABLE `t_m_menu` (
  `menu_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `parent_id` bigint(20) NOT NULL DEFAULT -1 COMMENT '上级客户',
  `menu_name` varchar(32) NOT NULL DEFAULT '-1' COMMENT '菜单名称',
  `menu_url` varchar(512) NOT NULL DEFAULT '-1' COMMENT '菜单路径',
  `valid` int(1) NOT NULL DEFAULT -1 COMMENT '1.有效、0.无效',
  `memo` varchar(512) NOT NULL DEFAULT '-1' COMMENT '备注',
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(64) NOT NULL DEFAULT '-1' COMMENT '创建人',
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_by` varchar(64) NOT NULL DEFAULT '-1' COMMENT '修改人',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

/*
	2.用户菜单关系信息
	菜单ID
	用户ID*/
CREATE TABLE `t_m_menu_ref` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `menu_id` bigint(20) NOT NULL DEFAULT -1 COMMENT '菜单ID',
  `user_id` bigint(20) NOT NULL DEFAULT -1 COMMENT '用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;


