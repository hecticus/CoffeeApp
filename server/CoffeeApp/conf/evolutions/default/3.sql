# --- !Ups
--
-- Dumping data for table `config`
--
LOCK TABLES `config` WRITE;
/*!40000 ALTER TABLE `config` DISABLE KEYS */;
/*!40000 ALTER TABLE `config` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `user`
--
LOCK TABLES `auth_user` WRITE;
/*!40000 ALTER TABLE `auth_user` DISABLE KEYS */;
INSERT INTO `auth_user` ( id, email, password, archived, last_login, created_at,  updated_at) VALUES
(2,'yenny.fung@hecticus.com','yenny',0,NULL,'2017-06-22 22:27:17','2017-06-22 22:27:17'),
(3,'han@han.com','han',0,NULL,'2017-06-22 22:27:17','2017-06-22 22:27:17'),
(4,'leia@leia.com','leia',0,NULL,'2017-06-22 22:27:17','2017-06-22 22:27:17'),
(5,'chewie@chewie.com','chewie',0,NULL,'2017-06-22 22:27:17','2017-06-22 22:27:17'),
(6,'marwin@hecticus.com','1234',0,NULL,'2017-06-22 22:27:17','2017-06-22 22:27:17'),
(7,'gabriel.perez@hecticus.com','gabriel',0,NULL,'2017-06-22 22:27:17','2017-06-22 22:27:17'),
(8,'hnmhernandez@gmail.com','1234',0,NULL,'2017-06-22 22:27:17','2017-06-22 22:27:17'),
(9,'brayan.mendoza@hecticus.com','ingeniero',0,NULL,'2017-06-22 22:27:17','2017-06-22 22:27:17'),
(10,'ana@ana.com','ana',0,NULL,'2017-06-22 22:27:17','2017-06-22 22:27:17'),
(11,'shamuel.manrrique@hecticus.com','root',0,NULL,'2017-06-22 22:27:17','2017-06-22 22:27:17');
/*!40000 ALTER TABLE `auth_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `auth_role`
--
INSERT INTO `auth_role` (id, created_at, updated_at, description) VALUES
  ('Operador','2017-06-22 22:27:17','2017-06-22 22:27:17', 'Operador '),
  ( 'Admin','2017-06-22 22:27:17','2017-06-22 22:27:17', 'Admin '),
  ( 'Finanzas','2017-06-22 22:27:17','2017-06-22 22:27:17', 'Finanzas ');

--
-- Dumping data for table `user_auth_group`
--
INSERT INTO `auth_user_auth_role` (auth_user_id,auth_role_id) VALUES
  ('2','Operador'),
  ('3','Finanzas'),
  ('4','Finanzas'),
  ('5','Operador'),
  ('6','Admin'),
  ('7','Operador'),
  ('8','Finanzas'),
  ('9','Finanzas'),
  ('10','Operador'),
  ('11','Admin');

INSERT INTO `auth_group` ( id, created_at, updated_at, description) VALUES
  ('SuperUser','2017-06-22 22:27:17','2017-06-22 22:27:17', 'Super Poder '),
  ('Basic','2017-06-22 22:27:17','2017-06-22 22:27:17', 'Loggin Basic '),
  ('Reports','2017-06-22 22:27:17','2017-06-22 22:27:17', 'Reportes '),
  ('Orders','2017-06-22 22:27:17','2017-06-22 22:27:17', 'Ordenes ');

INSERT INTO `auth_user_auth_group` ( auth_user_id, auth_group_id)VALUES
  ('6','SuperUser'),
  ('10','Basic'),
  ('9','Reports'),
  ('11','SuperUser');




# --- !Downs
SET FOREIGN_KEY_CHECKS = 0;


TRUNCATE auth_user;

TRUNCATE auth_role;

TRUNCATE auth_user_auth_role;

TRUNCATE auth_group;

TRUNCATE auth_user_auth_group;

SET FOREIGN_KEY_CHECKS = 1;

