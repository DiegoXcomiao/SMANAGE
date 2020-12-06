drop table t_login;
create table t_login (
 loginId int(11) not null auto_increment,
 userCode varchar(50),
 state varchar(1),
 primary key(loginId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table t_login add unique (userCode);