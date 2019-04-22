-- auto-generated definition
create table file
(
    id     bigint auto_increment
        primary key,
    name   varchar(50)      not null comment '文件名称',
    format varchar(20)      not null comment '文件格式',
    data   longblob         null comment '文件二进制数据',
    size   bigint default 0 null comment '文件大小'
);

