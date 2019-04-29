------------------------------------------------------------------->
DROP TABLE IF
 EXISTS Items;

CREATE TABLE IF NOT EXISTS Items(
  id BIGINT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
  name          VARCHAR (80) NOT NULL,
  status        VARCHAR(20) NOT NULL
);

INSERT INTO Items (name,status)
 VALUES ('item1','READY');
INSERT INTO Items (name,status)
 VALUES ('item2','STEADY');
INSERT INTO Items (name,status)
 VALUES ('item3','GO');
------------------------------------------------------------------->

DROP TABLE IF
 EXISTS Users;

DROP TABLE IF
 EXISTS Role;

------------------------------------------------------------------->

CREATE TABLE IF NOT EXISTS Role(
  id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  name VARCHAR(20) NOT NULL
);

INSERT INTO Role (name)
 VALUES ('ADMINISTRATOR');

INSERT INTO Role (name)
 VALUES ('CUSTOMER');
------------------------------------------------------------------->
CREATE TABLE IF NOT  EXISTS Users(
  id BIGINT  AUTO_INCREMENT NOT NULL PRIMARY KEY,
  username      VARCHAR (70) NOT NULL,
  password      VARCHAR(100) NOT NULL,
  email VARCHAR(20),
  role_id BIGINT NOT NULL,
  FOREIGN KEY (role_id) REFERENCES Role(id),
  UNIQUE (email)
);

INSERT INTO Users VALUES(
NULL,'users','$2a$12$zJY/A7F1kTTv.eXYIOsEv.2ebIglNI9nUa/S4Tsp6mCQe0f9itG2W','kazimir.ru',
(SELECT r.id FROM Role r WHERE r.name = 'CUSTOMER'));

INSERT INTO Users VALUES(
NULL,'admin','$2a$12$24/cinhv/Kwi5wBzKlijt.T55RlDiLJtVIdAzSmB4drxCWKOQ4fBG','bortnik.ru',
(SELECT r.id FROM Role r WHERE r.name = 'ADMINISTRATOR'));


------------------------------------------------------------------->