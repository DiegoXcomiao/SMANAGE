drop table t_user;
create table t_user (
 userId int(11) not null auto_increment,
 code varchar(50),
 name varchar(256),
 roleCode text,
 roleName text,
 nodeCode varchar(50),
 nodeName varchar(1024),
 password varchar(256),
 verifyCode varchar(256),
 telno varchar(50),
 picpath varchar(256),
 enabled varchar(1),
 primary key(userId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table t_user add unique (code);