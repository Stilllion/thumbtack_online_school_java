DROP DATABASE IF EXISTS ttschool;
CREATE DATABASE `ttschool`; 
USE `ttschool`;

CREATE TABLE trainee (
  id INT(11) NOT NULL AUTO_INCREMENT,
  firstName VARCHAR(50) NOT NULL,
  lastName VARCHAR(50) NOT NULL,
  rating INT(11) NOT NULL,
  PRIMARY KEY (id),
  
  KEY/*INDEX*/ firstName (firstName),
  KEY/*INDEX*/ lastName (lastName)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE school (
  id INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  year INT(11) NOT NULL,
  PRIMARY KEY (id),
  
  KEY/*INDEX*/ name (name),
  KEY/*INDEX*/ year (year)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE grouptable (
  id INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  room VARCHAR(50) NOT NULL,
  PRIMARY KEY (id),
  
  KEY/*INDEX*/ name (name),
  KEY/*INDEX*/ room (room)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE subject (
  id INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  PRIMARY KEY (id),

  KEY/*INDEX*/ name (name)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/* SCHOOL : GROUP (1 : many) */

CREATE TABLE school_group (
  id INT(11) NOT NULL AUTO_INCREMENT,
  schoolid INT(11) NOT NULL,
  groupid INT(11) NOT NULL,
  
  PRIMARY KEY (`id`),
  UNIQUE KEY school_group (schoolid, groupid),
  UNIQUE KEY group_school (groupid, schoolid),
  FOREIGN KEY (`groupid`) REFERENCES grouptable (id) ON DELETE CASCADE,
  FOREIGN KEY (`schoolid`) REFERENCES school (id) ON DELETE CASCADE
  
) ENGINE=INNODB DEFAULT CHARSET=utf8;