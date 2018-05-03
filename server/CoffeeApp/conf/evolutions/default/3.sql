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

INSERT INTO `auth_client_credential` (id, created_at, updated_at, client_id,
                                      client_secret , name, icon, home_page_url,
                                      privacy_policy_url, auth_callback_uri, description ) VALUES
  (1,'2017-05-02 00:00:00','2015-05-02 00:00:00','web_site',NULL,'web site',NULL,NULL,NULL,NULL,NULL),
  (2,'2017-05-02 00:00:00','2015-05-02 00:00:00','android_app',NULL,'android app',NULL,NULL,NULL,NULL,NULL);


INSERT INTO `auth_permission`(id, created_at, updated_at, name, route, description) VALUES
  #farm
  (1,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'farm ', 'controllers.Farms.findById', NULL),
  (2,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'farm ', 'controllers.Farms.findByAll', NULL),
  (3,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'farm ', 'controllers.Farms.findAllSearch', NULL),

  #providerType
  (4,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'providerType ', 'controllers.findById', NULL),
  (5,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'providerType ', 'controllers.ProviderTypes.findAllByAll', NULL),
  (6,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'providerType ', 'controllers.ProviderTypes.getProviderTypesByName', NULL),

  #provider
  (7,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'provider', 'controllers.Providers.findById', NULL),
  (8,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'provider', 'controllers.Providers.findAll', NULL),
  (9,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'provider', 'controller.Providers.findAllSearch', NULL),
  (10,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'provider', 'controllers.ProviderTypes.findAllByAll', NULL),
  (11,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'provider', 'controllers.Providers.getByIdentificationDoc', NULL),
  (12,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'provider', 'controllers.Providers.getProvidersByName', NULL),
  (13,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'provider', 'controllers.Providers.getByTypeProvider', NULL),
  (14,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'provider', 'controllers.Providers.getByNameDocByTypeProvider', NULL),
  (15,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'provider', 'ccontrollers.Providers.create', NULL),
  (16,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'provider', 'controllers.Providers.update', NULL),
  (17,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'provider', 'controllers.Providers.delete', NULL),   #TODO CHECK HAVE TWO
  (18,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'provider', 'controllers.Providers.uploadPhotoProvider', NULL),

  #invoice
  (7,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoice', 'controllers.Invoices.findAll', NULL),
  (8,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoice', 'controllers.Invoices.findAllSearch', NULL), #TODO CHECK HAVE TWO
  (9,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoice', 'controllers.Invoices.getByDateByTypeProvider', NULL),
  (10,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoice', 'controllers.Invoices.getByDateByProviderId', NULL),
  (11,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoice', 'controllers.Invoices.getOpenByProviderId', NULL),
  (12,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoice', 'controllers.Invoices.createReceipt', NULL),
  (13,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoice', 'controllers.Invoices.create', NULL),
  (14,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoice', 'controllers.Invoices.buyHarvestsAndCoffe', NULL),
  (15,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoice', 'controllers.Invoices.updateBuyHarvestsAndCoffe', NULL),
  (16,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoice', 'controllers.Invoices.update', NULL),
  (18,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoice', 'controllers.Invoices.delete', NULL),


  #unit
  (7,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'unit', 'controllers.Units.findById', NULL),
  (8,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'unit', 'controllers.Units.findAll', NULL), #TODO CHECK HAVE TWO
  (9,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'unit', 'controllers.Units.create', NULL),
  (10,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'unit', 'controllers.Units.update', NULL),
  (11,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'unit', 'controllers.Units.delete', NULL),





  (1,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'farm ', 'controllers.Farms.findByAll', NULL);


# --- !Downs
SET FOREIGN_KEY_CHECKS = 0;


TRUNCATE auth_user;

TRUNCATE auth_role;

TRUNCATE auth_user_auth_role;

TRUNCATE auth_group;

TRUNCATE auth_user_auth_group;

TRUNCATE auth_client_credential;

TRUNCATE auth_permission;

SET FOREIGN_KEY_CHECKS = 1;

