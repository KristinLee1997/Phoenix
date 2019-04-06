-- auto-generated definition
create table user
(
  user_id   int auto_increment
    primary key,
  user_name varchar(255) not null,
  password  varchar(255) not null,
  phone     varchar(255) not null
);