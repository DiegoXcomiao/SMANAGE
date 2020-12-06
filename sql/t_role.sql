drop table t_role;
create table t_role (
 roleId int(11) not null auto_increment,
 code varchar(50),
 name varchar(256),
 primary key(roleId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table t_role add unique (code);