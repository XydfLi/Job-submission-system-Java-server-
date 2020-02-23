//一些mysql操作指令

mysql -u root -p
123456

create database JobSubmissionSystem;

show databases;

use JobSubmissionSystem;

show tables;

create table Account(
accountId char(15) not null,
accountName char(20) not null,
passWord char(15) not null,
identity int not null default 2,
primary key (accountId)
)engine=innodb default charset=utf8;

show tables;

describe Account;

create table Task(
taskId bigint not null auto_increment,
taskName char(20) not null,
taskContent text not null,
startDate datetime not null,
deadline datetime not null,
primary key (taskId)
)engine=innodb default charset=utf8;

show tables;

describe Task;

create table Assignment(
taskId int unsigned not null,
accountId char(15) not null,
jobContent text,
subTime datetime not null,
mark int not null default 1,
primary key(taskId,accountId)
)engine=innodb default charset=utf8;

show tables;

describe Assignment;

insert into account(accountId,accountName,password,identity) values('221801000','学生1','123456',2);
insert into account(accountId,accountName,password,identity) values('000000000','管理员','123456',1);

