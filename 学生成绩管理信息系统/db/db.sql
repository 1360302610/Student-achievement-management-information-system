CREATE TABLE `course` (
  `cno` varchar(15) NOT NULL,
  `cname` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`cno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8


CREATE TABLE `grade` (
  `cno` varchar(15) NOT NULL,
  `sno` varchar(15) NOT NULL,
  `core` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`sno`,`cno`),
  KEY `grade_ibfk_2` (`cno`),
  CONSTRAINT `grade_ibfk_1` FOREIGN KEY (`sno`) REFERENCES `student` (`sno`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `grade_ibfk_2` FOREIGN KEY (`cno`) REFERENCES `course` (`cno`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8


CREATE TABLE `student` (
  `sno` varchar(15) NOT NULL,
  `sname` varchar(10) DEFAULT NULL,
  `classes` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`sno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `user` (
  `username` varchar(15) NOT NULL,
  `password` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8