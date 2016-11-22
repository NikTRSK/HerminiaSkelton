DROP DATABASE if exists HerminiaSkelton_DB;

CREATE DATABASE HerminiaSkelton_DB;

USE HerminiaSkelton_DB;

CREATE TABLE users (
  userID int(11) primary key not null auto_increment,
  username varchar(50) not null,
  password varchar(50) not null
);

CREATE TABLE topScores (
  scoreID int(11) primary key not null auto_increment,
  score int(11),
  username varchar(11)
);