drop table t_role_menus;
create table t_role_menus (
 roleMenusId int(11) not null auto_increment,
 roleCode varchar(50),
 menusId text,
 primary key(roleMenusId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table t_role_menus add unique (roleCode);