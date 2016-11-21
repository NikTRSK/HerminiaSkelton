DROP DATABASE if exists HerminiaSkelton_DB;

CREATE DATABASE HerminiaSkelton_DB;

USE HerminiaSkelton_DB;

CREATE TABLE users (
  userID int(11) primary key not null auto_increment,
  username varchar(50) not null,
  password varchar(50) not null, 
  score1 int(11),
  score2 int(11),
  score3 int(11),
  score4 int(11),
  score5 int(11)
);