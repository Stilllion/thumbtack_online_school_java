DROP DATABASE IF EXISTS ttschool;
CREATE DATABASE `ttschool`; 
USE `ttschool`;

CREATE TABLE trainee (
  id INT(11) NOT NULL AUTO_INCREMENT,
  firstName VARCHAR(50) NOT NULL,
  lastName VARCHAR(50) NOT NULL,
  rating INT(11) NOT NULL,
  groupid INT(11),
  PRIMARY KEY (id),
  
  KEY firstName (firstName),
  KEY lastName (lastName)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE school (
  id INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  year INT(11) NOT NULL,
  PRIMARY KEY (id),
  
  UNIQUE KEY name_year (name, year)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE grouptable (
  id INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  room VARCHAR(50) NOT NULL,
  schoolid INT(11) NOT NULL,
  PRIMARY KEY (id),
  
  FOREIGN KEY (`schoolid`) REFERENCES school (id) ON DELETE CASCADE,
  KEY name (name),
  KEY room (room)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE subject (
  id INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  PRIMARY KEY (id),

  KEY name (name)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


CREATE TABLE group_subject (
  id INT(11) NOT NULL AUTO_INCREMENT,
  groupid INT(11) NOT NULL,
  subjectid INT(11) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY group_subject (groupid, subjectid),
  FOREIGN KEY (groupid) REFERENCES grouptable (id) ON DELETE CASCADE,
  FOREIGN KEY (subjectid) REFERENCES subject (id) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;