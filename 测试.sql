create table spj(a int,b char(8),c String);
insert into spj values('8','aa');
alter table spj add d date;
update spj set a=9 where b='aa';

create table sp(b char(8),a int,c double,d string);
select a,b from sp;
select * from sp;
insert into spj values('a','b','c');
insert into spj values('aa','bb','cc');
insert into sp(a.b) values('4','aa');
insert into sp(a.b) values('4',null);
delete from sp where a='4';
delete from sp where a=4;
delete from sp where b='aa';

drop table spj;
create unique index name on spj(a);
drop index name;




not null primary unique





//数据结构有：表，索引，视图，用户及用户权限
//表的数据结构，用户的数据结构，索引，视图，用户内容
//表内容
//insert,delete,update 更新数据表的内容；更新过程中需要检查更新后的数据表是否会违反参照完整性约束。如果是，则提示违反哪一条完整性约束，并拒绝执行更新操作；如果否，提示数据表更新成功，并说明插入、删除或修改了几个元组。
create table s
(sno char(6),
sname char(10),
status int,
city char(7)
);
create table p
(sno char(6),
pname char(10),
color char(4),
weight int
);
create table j
(jno char(6) key,
jname char(10),
city char(10)
);
create table spj
(sno char(6),
pno char(6),
jno char(6),
qty int
);
create table spj(sno char(6),pno char(6),jno char(6),qty int);
create table spj(sno char(6),pno char(6),qty int,jno char(6));
create table spj(sno char(6) primary key not null unique,pno char(6),qty int,jno char(6));//主键非空唯一性
drop table spj;
help database;//输出所有数据表、视图和索引的信息，同时显示其对象类型
help table spj;//输出数据表中所有属性的详细信息
help view view_spj;//输出视图的定义语句
help index index_spj;输出索引的详细信息
grant             //select insert delete update
revoke  


frm,idb
frm:
	private String field;
	private String type;
	private String cannull;
	private String key;
	private String unique;

create table student
(Sno CHAR(9),
Sname CHAR(8),
Ssex CHAR(2),
Sage SMALLINT,
Sdept CHAR(20)
);







create table info(sname char(15),sno int,ssex char(3),sage int);
create table student(Sno CHAR(9),Sname CHAR(8),Ssex CHAR(2),Sage SMALLINT,Sdept CHAR(20));

insert into student values('201401061329','wsx','男','20','b8');
insert into student values('201401061329','wsx','男','20','b8');
insert into student values('201401061423','马奔','男','20','b10-211');
select * from student;
help table student;

grant all privileges on table student to 1;
grant insert on table student to 1;
grant select on table student to 1;
revoke insert on table student from 1;
revoke select on table student from 1;

delete from student;
delete from student where Sno='201401061329';

update student set Sage='22' where Sno='201401061329'; 

select * from student;
select Sno,Sname from student;
select Sno,Sname from student where Sage='20';
select * from student where Sage='20';

create view name as select Sno from student;










