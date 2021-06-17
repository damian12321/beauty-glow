INSERT INTO role (name) VALUES ('ROLE_ADMIN'),('ROLE_CUSTOMER');
INSERT INTO `user` (email,password,first_name,last_name,phone_number,date_of_birth,active,validation_key)
VALUES
('admin','$2a$10$n7LuMBb83Txm6WeOBdx7SeLx1ZjyUj8uNBP932ReDA.6W.uhJcq7e','Damian','Juruś','732799789','1994-06-24',1,'222222'),
('customer','$2y$12$cq7K0l79BhMjuO9qaTXiduiv/S3o6NPwaOt50JKHYY/PM4237M/VG','Name','Last Name','732799789','1994-06-24',1,'222222');;
INSERT INTO `user_form` (user_id,question1,question2,question3,question4,question5,question6,question7)
VALUES(1,0,0,0,0,0,0,0);
INSERT INTO `treatment` (name,duration,cost)
VALUES
('Zabieg numer 1',30,100),
('Zabieg numer 2',15,200),
('Zabieg numer 3',30,300),
('Zabieg numer 4',45,50),
('Zabieg numer 5',15,100),
('Zabieg numer 6',90,400);
INSERT INTO `users_roles` (user_id,role_id)
VALUES
(1, 1),
(1, 2),
(2, 2);;
INSERT INTO `users_treatments` (user_id,treatment_id,status,date)
VALUES
(2, 1,'planned','2021-07-24 15:30:00'),
(2, 2,'cancelled','1994-06-24 12:30:00'),
(2, 3,'cancelled','2022-06-24 12:00:00'),
(2, 5,'finished','1994-06-24 16:00:00'),
(2, 4,'planned','2022-06-24 09:30:00'),
(2, 6,'finished','1994-06-24 12:30:00');