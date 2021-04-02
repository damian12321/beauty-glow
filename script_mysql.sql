DROP DATABASE  IF EXISTS `beauty_glow`;

CREATE DATABASE  IF NOT EXISTS `beauty_glow`;
USE `beauty_glow`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `password` char(80) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `phone_number` varchar(50) DEFAULT NULL,
  `date_of_birth` DATE NOT NULL,
  `active` boolean DEFAULT FALSE,
  `validation_key` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ;

INSERT INTO `user` (email,password,first_name,last_name,phone_number,date_of_birth,active,validation_key)
VALUES 
('damianjurus@wp.pl','$2a$10$n7LuMBb83Txm6WeOBdx7SeLx1ZjyUj8uNBP932ReDA.6W.uhJcq7e','Damian','Juruś','732799789','1994-06-24',1,'222222');

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ;

INSERT INTO `role` (name)
VALUES 
('ROLE_ADMIN'),('ROLE_CUSTOMER');

DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK_ROLE_idx` (`role_id`),
  CONSTRAINT `FK_USER_05` FOREIGN KEY (`user_id`) 
  REFERENCES `user` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_ROLE` FOREIGN KEY (`role_id`) 
  REFERENCES `role` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

SET FOREIGN_KEY_CHECKS = 1;
INSERT INTO `users_roles` (user_id,role_id)
VALUES 
(1, 1),
(1, 2);

CREATE TABLE `user_form` (
  `user_id` int NOT NULL,
  `question1` boolean NOT NULL,
  `question2` boolean NOT NULL,
  `question3` boolean NOT NULL,
  `question4` boolean NOT NULL,
  `question5` boolean NOT NULL,
  `question6` boolean NOT NULL,
  `question7` boolean NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `FK_USERFORM_idx` (`user_id`),
  CONSTRAINT `FK_USER_06` FOREIGN KEY (`user_id`) 
  REFERENCES `user` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;
  
INSERT INTO `user_form` (user_id,question1,question2,question3,question4,question5,question6,question7)
VALUES(1,0,0,0,0,0,0,0);

CREATE TABLE `treatment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `duration` int NOT NULL,
  `cost` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ;

INSERT INTO `treatment` (name,duration,cost)
VALUES 
('Zabieg numer 1',30,100),
('Zabieg numer 2',15,200),
('Zabieg numer 3',30,300),
('Zabieg numer 4',45,50),
('Zabieg numer 5',15,100),
('Zabieg numer 6',90,400);

CREATE TABLE `users_treatments` (
`id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `treatment_id` int NOT NULL,
  `status` VARCHAR(20) NOT NULL,
  `date` DATETIME default null,
  PRIMARY KEY (`id`),
  KEY `FK_TREATMENT_idx` (`treatment_id`),
  CONSTRAINT `FK_USER_07` FOREIGN KEY (`user_id`) 
  REFERENCES `user` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_TREATMENT` FOREIGN KEY (`treatment_id`) 
  REFERENCES `treatment` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

SET FOREIGN_KEY_CHECKS = 1;
INSERT INTO `users_treatments` (user_id,treatment_id,status,date)
VALUES 
(1, 1,'planned','1994-06-24 15:30:00'),
(1, 2,'cancelled','1994-06-24 12:30:00'),
(1, 3,'cancelled','1994-06-24 12:00:00'),
(1, 5,'finished','1994-06-24 16:00:00'),
(1, 4,'planned','1994-06-24 09:30:00'),
(1, 6,'finished','1994-06-24 12:30:00');