DROP DATABASE if exists HerminiaSkelton_DB;

CREATE DATABASE HerminiaSkelton_DB;

USE HerminiaSkelton_DB;

CREATE TABLE UserAccount (
  userID int(11) primary key not null auto_increment,
  username varchar(50) not null,
  password varchar(50) not null
);

CREATE TABLE UserScores (
  scoreID int(11) primary key not null auto_increment,
  score1 int(11),
  score2 int(11),
  score3 int(11),
  score4 int(11),
  score5 int(11)
);

CREATE TABLE User (
  'userID' INT NOT NULL,
  'scoreID' INT NOT NULL,
  PRIMARY KEY('userID', 'scoreID')
);

SELECT u.*, s.*
FROM UserAccount u
INNER JOIN User user
ON user.userID = u.userID
INNER JOIN UserScores s
ON s.scoreID = user.scoreID