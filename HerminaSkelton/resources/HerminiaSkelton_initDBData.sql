USE HerminiaSkelton_DB;

INSERT INTO users (username, password) VALUES ('nick', 'nick');
INSERT INTO users (username, password) VALUES ('matt', 'matt');
INSERT INTO users (username, password) VALUES ('juntao', 'juntao');
INSERT INTO users (username, password) VALUES ('andrew', 'andrew');
INSERT INTO users (username, password) VALUES ('jalin', 'jalin');

INSERT INTO topScores (score, username) VALUES (500, 'nick');
INSERT INTO topScores (score, username) VALUES (600, 'matt');
INSERT INTO topScores (score, username) VALUES (700, 'matt');
INSERT INTO topScores (score, username) VALUES (800, 'juntao');
INSERT INTO topScores (score, username) VALUES (900, 'andrew');