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
-- Dumping data for table `auth_role`,
--
INSERT INTO `auth_role` (id, created_at, updated_at, description) VALUES
  #providerType
  ('providerType_r','2017-06-22 22:27:17','2017-06-22 22:27:17', 'providerType read '),
  ('providerType_g','2017-06-22 22:27:17','2017-06-22 22:27:17', 'providerType get '),
  ('providerType_u','2017-06-22 22:27:17','2017-06-22 22:27:17', 'providerType cup '),

  #provider
  ('provider_r','2017-06-22 22:27:17','2017-06-22 22:27:17', 'provider read '),
  ('provider_g','2017-06-22 22:27:17','2017-06-22 22:27:17', 'provider get '),
  ('provider_u','2017-06-22 22:27:17','2017-06-22 22:27:17', 'provider cup '),

  #invoice
  ('invoice_r','2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoice read '),
  ('invoice_g','2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoice get '),
  ('invoice_u','2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoice cup '),

  #unit
  ('unit_r','2017-06-22 22:27:17','2017-06-22 22:27:17', 'unit read '),
  ('unit_g','2017-06-22 22:27:17','2017-06-22 22:27:17', 'unit get '),
  ('unit_u','2017-06-22 22:27:17','2017-06-22 22:27:17', 'unit cup '),

  #itemType
  ('itemType_r','2017-06-22 22:27:17','2017-06-22 22:27:17', 'itemType read '),
  ('itemType_g','2017-06-22 22:27:17','2017-06-22 22:27:17', 'itemType get '),
  ('itemType_u','2017-06-22 22:27:17','2017-06-22 22:27:17', 'itemType cup '),

  #lot
  ('lot_r','2017-06-22 22:27:17','2017-06-22 22:27:17', 'lot read '),
  ('lot_g','2017-06-22 22:27:17','2017-06-22 22:27:17', 'lot get '),
  ('lot_u','2017-06-22 22:27:17','2017-06-22 22:27:17', 'lot cup '),

   #farm
  ('farm_r','2017-06-22 22:27:17','2017-06-22 22:27:17', 'farm read '),
  ('farm_g','2017-06-22 22:27:17','2017-06-22 22:27:17', 'farm get '),
  ('farm_u','2017-06-22 22:27:17','2017-06-22 22:27:17', 'farm cup '),

  #purity
  ('purity_r','2017-06-22 22:27:17','2017-06-22 22:27:17', 'purity read '),
  ('purity_g','2017-06-22 22:27:17','2017-06-22 22:27:17', 'purity get '),
  ('purity_u','2017-06-22 22:27:17','2017-06-22 22:27:17', 'purity cup '),

  #invoiceDetailPurity
  ('invoiceDetailPurity_r','2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoiceDetailPurity read '),
  ('invoiceDetailPurity_g','2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoiceDetailPurity get '),
  ('invoiceDetailPurity_u','2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoiceDetailPurity cup '),

  #invoiceDetail
  ('invoiceDetail_r','2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoiceDetail read '),
  ('invoiceDetail_g','2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoiceDetail get '),
  ('invoiceDetail_u','2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoiceDetail cup '),

   #store
  ('store_r','2017-06-22 22:27:17','2017-06-22 22:27:17', 'store read '),
  ('store_g','2017-06-22 22:27:17','2017-06-22 22:27:17', 'store get '),
  ('store_u','2017-06-22 22:27:17','2017-06-22 22:27:17', 'store cup ');



INSERT INTO `auth_permission`(id, created_at, updated_at, name, route, description) VALUES

  #providerType
  (4,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'providerType ', 'controllers.ProviderTypes.findById', NULL),
  (5,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'providerType ', 'controllers.ProviderTypes.findAllByAll', NULL),
  (6,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'providerType ', 'controllers.ProviderTypes.getProviderTypesByName', NULL),

  #provider
  (7,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'provider', 'controllers.Providers.findById', NULL),
  (8,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'provider', 'controllers.Providers.findAll', NULL),
  (9,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'provider', 'controller.Providers.findAllSearch', NULL),
  (11,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'provider', 'controllers.Providers.getByIdentificationDoc', NULL),
  (12,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'provider', 'controllers.Providers.getProvidersByName', NULL),
  (13,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'provider', 'controllers.Providers.getByTypeProvider', NULL),
  (14,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'provider', 'controllers.Providers.getByNameDocByTypeProvider', NULL),
  (15,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'provider', 'ccontrollers.Providers.create', NULL),
  (16,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'provider', 'controllers.Providers.update', NULL),
  (17,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'provider', 'controllers.Providers.delete', NULL),   #TODO CHECK HAVE TWO
  (10,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'provider', 'controllers.Providers.deletes', NULL),
  (18,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'provider', 'controllers.Providers.uploadPhotoProvider', NULL),

  #invoice
  (19,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoice', 'controllers.Invoices.findAll', NULL),
  (20,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoice', 'controllers.Invoices.findAllSearch', NULL), #TODO CHECK HAVE TWO
  (21,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoice', 'controllers.Invoices.getByDateByTypeProvider', NULL),
  (22,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoice', 'controllers.Invoices.getByDateByProviderId', NULL),
  (23,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoice', 'controllers.Invoices.getOpenByProviderId', NULL),
  (24,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoice', 'controllers.Invoices.createReceipt', NULL),
  (25,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoice', 'controllers.Invoices.create', NULL),
  (26,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoice', 'controllers.Invoices.buyHarvestsAndCoffe', NULL),
  (27,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoice', 'controllers.Invoices.updateBuyHarvestsAndCoffe', NULL),
  (28,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoice', 'controllers.Invoices.update', NULL),
  (29,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoice', 'controllers.Invoices.delete', NULL),

  #unit
  (30,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'unit', 'controllers.Units.findById', NULL),
  (31,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'unit', 'controllers.Units.findAll', NULL), #TODO CHECK HAVE TWO
  (311,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'unit', 'controllers.Units.create', NULL),
  (32,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'unit', 'controllers.Units.update', NULL),
  (33,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'unit', 'controllers.Units.delete', NULL),

  #itemType
  (34,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'itemType', 'controllers.ItemTypes.findAll', NULL),
  (35,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'itemType', 'controllers.ItemTypes.findAllSearch', NULL), #TODO CHECK HAVE TWO
  (36,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'itemType', 'controllers.ItemTypes.preCreate', NULL),
  (37,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'itemType', 'controllers.ItemTypes.findById', NULL),
  (38,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'itemType', 'controllers.ItemTypes.getByProviderTypeId', NULL),
  (39,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'itemType', 'controllers.ItemTypes.getByNameItemType', NULL),
  (40,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'itemType', 'controllers.ItemTypes.create', NULL),
  (41,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'itemType', 'controllers.ItemTypes.update', NULL),
  (42,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'itemType', 'controllers.ItemTypes.delete', NULL),

  #lot
  (43,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'lot', 'controllers.Lots.findAll', NULL),
  (44,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'lot', 'controllers.Lots.findAllSearch', NULL), #TODO CHECK HAVE TWO
  (45,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'lot', 'controllers.Lots.preCreate', NULL),
  (46,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'lot', 'controllers.Lots.create', NULL),
  (47,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'lot', 'controllers.Lots.update', NULL),
  (48,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'lot', 'controllers.Lots.delete', NULL),
  (49,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'lot', 'controllers.Lots.getByNameLot', NULL),
  (50,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'lot', 'controllers.Lots.getByStatusLot', NULL),
  (51,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'lot', 'controllers.Lots.getByIdFarm', NULL),
  (52,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'lot', 'controllers.Lots.deletes', NULL),

  #farm
  (53,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'farm', 'controllers.Farms.findById', NULL),
  (54,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'farm', 'controllers.Farms.findByAll', NULL),
  (55,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'farm', 'controllers.Farms.findAllSearch', NULL),

  #purity
  (56,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'purity', 'controllers.Purities.findAll', NULL),
  (57,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'purity', 'controllers.Purities.findAllSearch', NULL), #TODO CHECK HAVE TWO
  (58,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'purity', ' controllers.Purities.preCreate', NULL),
  (59,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'purity', 'controllers.Purities.findById', NULL),
  (60,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'purity', 'controllers.Purities.getByNamePurity', NULL),
  (61,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'purity', 'controllers.Purities.getByStatusPurity', NULL),
  (62,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'purity', 'controllers.Purities.create', NULL),
  (63,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'purity', 'controllers.Purities.update', NULL),
  (64,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'purity', 'controllers.Purities.delete', NULL),

  #invoiceDetailPurity
  (65,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoiceDetailPurity', 'controllers.InvoiceDetailPurities.findAll', NULL), #TODO CHECK HAVE TWO
  (651,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoiceDetailPurity', 'controllers.InvoiceDetailPurities.findById', NULL),
  (66,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoiceDetailPurity', 'controllers.InvoiceDetailPurities.create', NULL),
  (67,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoiceDetailPurity', 'controllers.InvoiceDetailPurities.update', NULL),
  (68,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoiceDetailPurity', 'controllers.InvoiceDetailPurities.delete', NULL),

  #invoiceDetail
  (69,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoiceDetail', 'controllers.InvoiceDetails.findAll', NULL),
  (70,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoiceDetail', 'controllers.InvoiceDetails.findAllSearch', NULL), #TODO CHECK HAVE TWO
  (71,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoiceDetail', 'controllers.InvoiceDetails.preCreate', NULL),
  (72,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoiceDetail', 'controllers.InvoiceDetails.create', NULL),
  (73,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoiceDetail', 'controllers.InvoiceDetails.findById', NULL),
  (74,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoiceDetail', 'controllers.InvoiceDetails.findAllByIdInvoice', NULL),
  (75,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoiceDetail', 'controllers.InvoiceDetails.update', NULL),
  (751,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoiceDetail', 'controllers.InvoiceDetails.delete', NULL),
  (76,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoiceDetail', 'controllers.InvoiceDetails.deleteAllByIdInvoiceAndDate', NULL),

  #store
  (77,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'store', 'controllers.Stores.findAll', NULL),
  (78,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'store', 'Controllers.Stores.findAllSearch', NULL), #TODO CHECK HAVE TWO
  (79,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'store', 'controllers.Stores.preCreate', NULL),
  (80,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'store', 'controllers.Stores.findById', NULL),
  (81,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'store', 'controllers.Stores.getByStatusStore', NULL),
  (82,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'store', 'controllers.Stores.create', NULL),
  (83,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'store', 'controllers.Stores.update', NULL),
  (85,'2017-06-22 22:27:17','2017-06-22 22:27:17', 'store', 'controllers.Stores.delete', NULL);


INSERT INTO `auth_group` ( id, created_at, updated_at, description) VALUES
  ('admin','2017-06-22 22:27:17','2017-06-22 22:27:17', 'Super Poder '),
  ('client','2017-06-22 22:27:17','2017-06-22 22:27:17', 'Loggin Basic '),
  ('basic','2017-06-22 22:27:17','2017-06-22 22:27:17', 'Ordenes ');

INSERT INTO `auth_client_credential` (id, created_at, updated_at, client_id,
                                      client_secret , name, icon, home_page_url,
                                      privacy_policy_url, auth_callback_uri, description ) VALUES
  (1,'2017-05-02 00:00:00','2015-05-02 00:00:00','web_site',NULL,'web site',NULL,NULL,NULL,NULL,NULL),
  (2,'2017-05-02 00:00:00','2015-05-02 00:00:00','android_app',NULL,'android app',NULL,NULL,NULL,NULL,NULL);


INSERT INTO `auth_permission_auth_role` (auth_permission_id, auth_role_id) VALUES
  #providerType_type
  ('4','providerType_r'),
  ('5','providerType_r'),
  ('6','providerType_g'),

  #provider
  ('7','provider_r'),
  ('8','provider_r'),
  ('9','provider_r'),
  ('11','provider_g'),
  ('12','provider_g'),
  ('13','provider_g'),
  ('14','provider_g'),
  ('18','provider_g'),
  ('10','provider_u'),
  ('15','provider_u'),
  ('16','provider_u'),
  ('17','provider_u'),

  #invoice
  ('19','invoice_r'),
  ('20','invoice_r'),
  ('22','invoice_g'),
  ('23','invoice_g'),
  ('24','invoice_g'),
  ('21','invoice_g'),
  ('26','invoice_g'),
  ('27','invoice_g'),
  ('25','invoice_u'),
  ('28','invoice_u'),
  ('29','invoice_u'),

  #unit
  ('30','unit_r'),
  ('31','unit_r'),
  ('311','unit_u'),
  ('32','unit_u'),
  ('33','unit_u'),


  #itemType
  ('34','itemType_r'),
  ('35','itemType_r'),
  ('37','itemType_r'),
  ('38','itemType_g'),
  ('39','itemType_g'),
  ('36','itemType_u'),
  ('40','itemType_u'),
  ('41','itemType_u'),
  ('42','itemType_u'),


  #lot
  ('43','lot_r'),
  ('44','lot_r'),
  ('49','lot_g'),
  ('50','lot_g'),
  ('51','lot_g'),
  ('45','lot_u'),
  ('46','lot_u'),
  ('47','lot_u'),
  ('48','lot_u'),
  ('52','lot_u'),

  #farm
  ('53','farm_r'),
  ('54','farm_r'),
  ('55','farm_r'),

  #purity
  ('56','purity_r'),
  ('57','purity_r'),
  ('59','purity_r'),
  ('60','purity_g'),
  ('61','purity_g'),
  ('58','purity_u'),
  ('62','purity_u'),
  ('63','purity_u'),
  ('64','purity_u'),

  #invoiceDetailPurity
  ('65','invoiceDetailPurity_r'),
  ('651','invoiceDetailPurity_r'),
  ('66','invoiceDetailPurity_u'),
  ('67','invoiceDetailPurity_u'),
  ('68','invoiceDetailPurity_u'),

  #invoiceDetail
  ('69','invoiceDetail_r'),
  ('70','invoiceDetail_r'),
  ('73','invoiceDetail_r'),
  ('74','invoiceDetail_r'),
  ('71','invoiceDetail_u'),
  ('72','invoiceDetail_u'),
  ('75','invoiceDetail_u'),
  ('751','invoiceDetail_u'),
  ('76','invoiceDetail_u'),

  #store
  ('77','store_r'),
  ('78','store_r'),
  ('80','store_r'),
  ('81','store_g'),
  ('79','store_u'),
  ('82','store_u'),
  ('83','store_u'),
  ('85','store_u');

--
-- Dumping data for table `user_auth_group`
--
INSERT INTO `auth_role_auth_group` (auth_role_id, auth_group_id) VALUES

  #Group admin
  #providerType
  ('providerType_r', 'admin'),
  ('providerType_g', 'admin'),

  #provider
  ('provider_r', 'admin'),
  ('provider_g', 'admin'),
  ('provider_u', 'admin'),


  #invoice
  ('invoice_r', 'admin'),
  ('invoice_g', 'admin'),
  ('invoice_u', 'admin'),

  #unit
  ('unit_r', 'admin'),
  ('unit_u', 'admin'),

  #itemType
  ('itemType_r', 'admin'),
  ('itemType_g', 'admin'),
  ('itemType_u', 'admin'),

  #lot
  ('lot_r', 'admin'),
  ('lot_g', 'admin'),
  ('lot_u', 'admin'),

  #farm
  ('farm_r', 'admin'),

  #purity
  ('purity_r', 'admin'),
  ('purity_g', 'admin'),
  ('purity_u', 'admin'),

  #invoiceDetailPurity
  ('invoiceDetailPurity_r', 'admin'),
  ('invoiceDetailPurity_u', 'admin'),

  #invoiceDetail
  ('invoiceDetail_r', 'admin'),
  ('invoiceDetail_u', 'admin'),

  #store
  ('store_r', 'admin'),
  ('store_g', 'admin'),
  ('store_u', 'admin'),


  #Group client
  #providerType
  ('providerType_r', 'client'),
  ('providerType_g', 'client'),

  #provider
  ('provider_r', 'client'),
  ('provider_g', 'client'),
  #('provider_u', 'client'),


  #invoice
  ('invoice_r', 'client'),
  ('invoice_g', 'client'),
  #('invoice_u', 'client'),

  #unit
  ('unit_r', 'client'),
  #('unit_u', 'client'),

  #itemType
  ('itemType_r', 'client'),
  ('itemType_g', 'client'),
  #('itemType_u', 'client'),

  #lot
  ('lot_r', 'client'),
  ('lot_g', 'client'),
  #('lot_u', 'client'),

  #farm
  ('farm_r', 'client'),

  #purity
  ('purity_r', 'client'),
  ('purity_g', 'client'),
  #('purity_u', 'client'),

  #invoiceDetailPurity
  ('invoiceDetailPurity_r', 'client'),
  #('invoiceDetailPurity_u', 'client'),

  #invoiceDetail
  ('invoiceDetail_r', 'client'),
  #('invoiceDetail_u', 'client'),

  #store
  ('store_r', 'client'),
  ('store_g', 'client'),
  #('store_u', 'client'),

  #Group basic
  #providerType
  ('providerType_r', 'basic'),
  #('providerType_g', 'basic'),

  #provider
  ('provider_r', 'basic'),
  #('provider_g', 'basic'),
  #('provider_u', 'basic'),


  #invoice
  ('invoice_r', 'basic'),
  #('invoice_g', 'basic'),
  #('invoice_u', 'basic'),

  #unit
  ('unit_r', 'basic'),
  #('unit_u', 'basic'),

  #itemType
  ('itemType_r', 'basic'),
  #('itemType_g', 'basic'),
  #('itemType_u', 'basic'),

  #lot
  ('lot_r', 'basic'),
  #('lot_g', 'basic'),
  #('lot_u', 'basic'),

  #farm
  ('farm_r', 'basic'),

  #purity
  ('purity_r', 'basic'),
  #('purity_g', 'basic'),
  #('purity_u', 'basic'),

  #invoiceDetailPurity
  ('invoiceDetailPurity_r', 'basic'),
  #('invoiceDetailPurity_u', 'basic'),

  #invoiceDetail
  ('invoiceDetail_r', 'basic'),
  #('invoiceDetail_u', 'basic'),

  #store
  ('store_r', 'basic');
  #('store_g', 'basic'),
  #('store_u', 'basic');

--
-- Dumping data for table `user_auth_group`
--
INSERT INTO `auth_user_auth_role` (auth_user_id,auth_role_id) VALUES
  ('6','purity_r'),
  ('3','purity_r'),
  ('5','lot_r'),
  ('6','farm_u'),
  ('6','farm_r'),
  ('11','farm_u');


INSERT INTO `auth_user_auth_group` ( auth_user_id, auth_group_id)VALUES
  ('6','admin'),
  ('11','admin'),
  ('10','basic'),
  ('7','basic'),
  ('9','client'),
  ('4','client');

# --- !Downs
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE auth_user;

TRUNCATE auth_role;

TRUNCATE auth_user_auth_role;

TRUNCATE auth_group;

TRUNCATE auth_user_auth_group;

TRUNCATE auth_client_credential;

TRUNCATE auth_permission;

TRUNCATE auth_permission_auth_role;

TRUNCATE auth_role_auth_group;

SET FOREIGN_KEY_CHECKS = 1;

