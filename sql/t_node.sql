DROP TABLE t_node;

CREATE TABLE t_node (
 nodeId int NOT NULL AUTO_INCREMENT,
 code varchar(50),
 name varchar(256),
 PRIMARY KEY (nodeId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into t_node (nodeId, code, name) values (1, '00', '总公司');
insert into t_node (nodeId, code, name) values (2, '0001', '省外公司');
insert into t_node (nodeId, code, name) values (3, '0002', '省内公司');

alter table t_node add unique (code);