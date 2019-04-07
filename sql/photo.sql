-- auto-generated definition
create table photo
(
  id         bigint auto_increment
    primary key,
  name       varchar(50) not null,
  photo_data longblob    null
);

