DROP TABLE t_message;

CREATE TABLE t_message (
 messageId int NOT NULL AUTO_INCREMENT,
 message text,
 verifyCode varchar(50),
 telno varchar(50),
 userCode varchar(50),
 PRIMARY KEY (messageId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table t_message add unique (userCode);
