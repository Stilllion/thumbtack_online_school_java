DROP DATABASE IF EXISTS ttschool;
CREATE DATABASE `ttschool`; 
USE `ttschool`;

CREATE TABLE trainee (
  id INT(11) NOT NULL AUTO_INCREMENT,
  firstName VARCHAR(50) NOT NULL,
  lastName VARCHAR(50) NOT NULL,
  rating INT(11) NOT NULL,
  PRIMARY KEY (id),
  
  KEY firstName (firstName),
  KEY lastName (lastName)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE school (
  id INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  year INT(11) NOT NULL,
  PRIMARY KEY (id),
  
  KEY name (name),
  KEY year (year)
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
