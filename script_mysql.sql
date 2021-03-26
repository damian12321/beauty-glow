DROP DATABASE  IF EXISTS `beauty_glow`;

CREATE DATABASE  IF NOT EXISTS `beauty_glow`;
USE `beauty_glow`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` char(80) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ;

INSERT INTO `user` (username,password,first_name,last_name,email)
VALUES 
('admin','$2a$10$OMoLd00UKDA..bf1Hf3bd.P0tvSQMP9o3aeWj3nip39KjjPbuxUi2','Damian','Juruś','damianjurus@wp.pl');

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
