
--
-- This no loaded in dataBase
--
-- Dumping data for table `auth_role`,
--
INSERT INTO `auth_role` (id, created_at, updated_at, description) VALUES
  #providerType
  (1,'providerType_r','2017-06-22 22:27:17','2017-06-22 22:27:17', 'providerType read '),
  (2,'providerType_g','2017-06-22 22:27:17','2017-06-22 22:27:17', 'providerType get '),
  (3,'providerType_u','2017-06-22 22:27:17','2017-06-22 22:27:17', 'providerType cup '),

  #provider
  (4,'provider_r','2017-06-22 22:27:17','2017-06-22 22:27:17', 'provider read '),
  (5,'provider_g','2017-06-22 22:27:17','2017-06-22 22:27:17', 'provider get '),
  (6,'provider_u','2017-06-22 22:27:17','2017-06-22 22:27:17', 'provider cup '),

  #invoice
  (7,'invoice_r','2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoice read '),
  (8,'invoice_g','2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoice get '),
  (9,'invoice_u','2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoice cup '),

  #unit
  (10',unit_r','2017-06-22 22:27:17','2017-06-22 22:27:17', 'unit read '),
  (11,'unit_g','2017-06-22 22:27:17','2017-06-22 22:27:17', 'unit get '),
  (12,'unit_u','2017-06-22 22:27:17','2017-06-22 22:27:17', 'unit cup '),

  #itemType
  (13,'itemType_r','2017-06-22 22:27:17','2017-06-22 22:27:17', 'itemType read '),
  (14,'itemType_g','2017-06-22 22:27:17','2017-06-22 22:27:17', 'itemType get '),
  (15,'itemType_u','2017-06-22 22:27:17','2017-06-22 22:27:17', 'itemType cup '),

  #lot
  (16,'lot_r','2017-06-22 22:27:17','2017-06-22 22:27:17', 'lot read '),
  (17,'lot_g','2017-06-22 22:27:17','2017-06-22 22:27:17', 'lot get '),
  (18,'lot_u','2017-06-22 22:27:17','2017-06-22 22:27:17', 'lot cup '),

   #farm
  (19,'farm_r','2017-06-22 22:27:17','2017-06-22 22:27:17', 'farm read '),
  (20,'farm_g','2017-06-22 22:27:17','2017-06-22 22:27:17', 'farm get '),
  (21,'farm_u','2017-06-22 22:27:17','2017-06-22 22:27:17', 'farm cup '),

  #purity
  (22,'purity_r','2017-06-22 22:27:17','2017-06-22 22:27:17', 'purity read '),
  (23,'purity_g','2017-06-22 22:27:17','2017-06-22 22:27:17', 'purity get '),
  (24,'purity_u','2017-06-22 22:27:17','2017-06-22 22:27:17', 'purity cup '),

  #invoiceDetailPurity
  (25,'invoiceDetailPurity_r','2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoiceDetailPurity read '),
  (26,'invoiceDetailPurity_g','2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoiceDetailPurity get '),
  (27,'invoiceDetailPurity_u','2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoiceDetailPurity cup '),

  #invoiceDetail
  (28,'invoiceDetail_r','2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoiceDetail read '),
  (29,'invoiceDetail_g','2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoiceDetail get '),
  (30,'invoiceDetail_u','2017-06-22 22:27:17','2017-06-22 22:27:17', 'invoiceDetail cup '),

   #store
  (31,'store_r','2017-06-22 22:27:17','2017-06-22 22:27:17', 'store read '),
  (32,'store_g','2017-06-22 22:27:17','2017-06-22 22:27:17', 'store get '),
  (33,'store_u','2017-06-22 22:27:17','2017-06-22 22:27:17', 'store cup ');


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
  (1,'admin','2017-06-22 22:27:17','2017-06-22 22:27:17', 'Super Poder '),
  (2,'client','2017-06-22 22:27:17','2017-06-22 22:27:17', 'Loggin Basic '),
  (3,'basic','2017-06-22 22:27:17','2017-06-22 22:27:17', 'Ordenes ');

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
  #('store_g', 'basic'),
  #('store_u', 'basic'),
  ('store_r', 'basic');
SET FOREIGN_KEY_CHECKS = 0;

-- TRUNCATE auth_role;

-- TRUNCATE auth_permission_auth_role;
--
-- TRUNCATE auth_role_auth_group;
--
-- TRUNCATE auth_user_auth_group;
--
TRUNCATE auth_client_credential;
--
-- TRUNCATE auth_permission;

TRUNCATE auth_user;
--
-- TRUNCATE auth_user_auth_group;
--
-- TRUNCATE auth_user_auth_role;

SET FOREIGN_KEY_CHECKS = 1;


-- # --- !Ups
--
-- Dumping data for table `config`
--
-- LOCK TABLES `config` WRITE;
-- /*!40000 ALTER TABLE `config` DISABLE KEYS */;
-- /*!40000 ALTER TABLE `config` ENABLE KEYS */;
-- UNLOCK TABLES;
INSERT INTO `auth_client_credential` (id, created_at, updated_at, client_id,
                                      client_secret , name, icon, home_page_url,
                                      privacy_policy_url, auth_callback_uri, description ) VALUES
  (1,'2017-05-02 00:00:00','2015-05-02 00:00:00','web_site',NULL,'web site',NULL,NULL,NULL,NULL,NULL),
  (2,'2017-05-02 00:00:00','2015-05-02 00:00:00','android_app',NULL,'android app',NULL,NULL,NULL,NULL,NULL);

--
-- Dumping data for table `user`
--
INSERT INTO `auth_user` ( id, username, email,password, archived, last_login, created_at,  updated_at) VALUES
  (2, 'yenny', 'yenny.fung@hecticus.com','yenny',0,NULL,'2017-06-22 22:27:17','2017-06-22 22:27:17'),
  (3,'han', 'han@han.com','han',0,NULL,'2017-06-22 22:27:17','2017-06-22 22:27:17'),
  (11,'sm','shamuel.manrrique@hecticus.com','root',0,NULL,'2017-06-22 22:27:17','2017-06-22 22:27:17');
--   (4,'leia@leia.com','leia',0,NULL,'2017-06-22 22:27:17','2017-06-22 22:27:17'),
--   (5,'chewie@chewie.com','chewie',0,NULL,'2017-06-22 22:27:17','2017-06-22 22:27:17'),
--   (6,'marwin@hecticus.com','1234',0,NULL,'2017-06-22 22:27:17','2017-06-22 22:27:17'),
--   (7,'gabriel.perez@hecticus.com','gabriel',0,NULL,'2017-06-22 22:27:17','2017-06-22 22:27:17'),
--   (8,'hnmhernandez@gmail.com','1234',0,NULL,'2017-06-22 22:27:17','2017-06-22 22:27:17'),
--   (9,'brayan.mendoza@hecticus.com','ingeniero',0,NULL,'2017-06-22 22:27:17','2017-06-22 22:27:17'),
--   (10,'ana@ana.com','ana',0,NULL,'2017-06-22 22:27:17','2017-06-22 22:27:17'),



--
-- Dumping data for table `user_auth_group`
--
INSERT INTO `auth_user_auth_role` (auth_user_id,auth_role_id) VALUES
  (11,93);
--   (11,94),
--   (11,95);
--   (11,49),
--   (11,56);
--   ('6','purity_r'),
--   ('3','purity_r'),
--   ('5','lot_r'),
--   ('6','farm_u'),
--   ('6','farm_r'),
--   ('11','farm_u'),
--   ('11','purity_r'),
--   ('9','farm_r'),
--   ('5','farm_u');

--
INSERT INTO `auth_user_auth_group` ( auth_user_id, auth_group_id)VALUES
  (11,14),
  (2,15);

-- # --- !Downs
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE auth_client_credential;

TRUNCATE auth_user;


SET FOREIGN_KEY_CHECKS = 1;

--# --- !Ups
INSERT INTO `farms` (id_farm,status_delete, name_farm, status_farm,created_at,updated_at) VALUES
  (1,0,'granja 1',1,'2017-08-24 20:49:58','2017-08-24 20:49:58'),
  (2,1,'granja 2',1,'2017-08-24 20:49:58','2017-08-24 20:49:58'),
  (3,1,'granja 3',1,'2017-09-05 19:58:30','2017-09-05 19:58:30'),
  (4,1,'Cafe de Eleta',0,'2017-10-11 14:36:13','2017-10-11 14:36:13');


INSERT INTO `provider_type` (id_providertype, status_delete, name_providertype, status_providertype, created_at, updated_at) VALUES
  (1,1,'Vendedor',1,'2017-09-06 20:49:27','2017-09-06 20:49:27'),
  (2,1,'Cosechador',1,'2017-09-06 20:49:27','2017-09-06 20:49:27'),
  (3,0,'prueba',1,'2017-09-06 20:49:27','2017-09-06 20:49:27');

INSERT INTO `stores` VALUES
  (1,0,'store 1',1,'2017-09-18 21:03:09','2017-09-18 21:03:09'),
  (2,0,'STORE 22',1,'2017-09-18 17:06:02','2017-09-18 17:07:54');

INSERT INTO `units` VALUES
  (1,0,'libra',1,'2017-09-18 18:50:43','2017-09-18 18:50:43');

INSERT INTO `purities` VALUES
  (1,0,'% Granos Flotes',1,20,'2017-09-18 15:47:15','2017-09-18 15:48:49'),
  (2,0,'% Granos Bocados',1,30,'2017-09-18 15:47:15','2017-09-18 15:47:15'),
  (3,1,'% Granos Eliminados',1,15,'2017-09-18 15:47:15','2017-09-20 18:44:17');

INSERT INTO `lots` VALUES
  (1,0,'LOTE 1','89',89,0,1,89.00,'2017-08-24 20:50:25','2017-10-11 10:34:04'),
  (2,0,'LOTE 2','256252',1233252,0,2,2252.00,'2017-08-24 20:50:25','2017-10-11 10:33:53'),
  (3,0,'LOTE 3','25625',1233253,1,1,5230.00,'2017-08-24 20:50:25','2017-09-19 14:18:28'),
  (4,0,'LOTE 4','89',89,1,2,100.00,'2017-08-24 20:50:25','2017-10-03 15:50:41'),
  (5,1,'LOTE 2','111',111,0,1,111.00,'2017-08-24 20:50:25','2017-10-11 10:32:05'),
  (6,0,'LOTE 6','58',58,0,2,58.00,'2017-08-29 15:26:19','2017-09-19 17:22:58'),
  (7,1,'LOTE 7','258',258,1,2,258.00,'2017-08-29 16:58:32','2017-09-19 17:22:58'),
  (8,1,'LOTE 8','56',56,1,1,56.00,'2017-08-30 17:57:58','2017-09-19 14:19:54'),
  (9,1,'lote 09','123258',123258,1,1,123258.00,'2017-08-30 17:59:53','2017-09-07 15:11:39'),
  (10,0,'#4','25663',369,1,4,897646.00,'2017-08-30 18:17:25','2017-10-13 10:31:57'),
  (11,1,'LOTE 11','123',1212,1,3,121212.00,'2017-09-11 16:55:54','2017-09-11 17:03:52'),
  (12,1,'lote 12','12312',12121,1,2,121212.00,'2017-09-11 16:56:17','2017-09-12 11:30:32'),
  (13,1,'LOTE 01','',126,1,1,126.00,'2017-09-12 11:33:34','2017-09-19 14:17:44'),
  (14,1,'LOTE 5','45',45,1,1,45.00,'2017-09-12 14:17:46','2017-09-20 14:23:05'),
  (15,1,'LOTE 3','12',55,1,1,55.00,'2017-09-12 14:23:57','2017-09-25 16:07:02'),
  (16,1,'LOTE 1','58',89,1,2,89.00,'2017-09-12 16:30:00','2017-09-25 16:07:03'),
  (17,1,'258','5258',44,1,2,77.00,'2017-09-12 16:30:53','2017-09-12 16:31:28'),
  (18,1,'MARWIN','12',12,1,1,200.00,'2017-09-18 11:55:59','2017-09-19 14:20:01'),
  (19,0,'MARWIN','2222',2222,1,3,2222.00,'2017-09-18 11:56:56','2017-09-19 14:20:05'),
  (20,1,'MARWIN','1',1,1,2,111.00,'2017-09-18 11:57:10','2017-09-19 17:22:58'),
  (21,1,'MARWIN 1','1',1,1,2,1.00,'2017-09-19 10:20:35','2017-09-19 17:22:50'),
  (22,1,'LOTE MAR','1',1,1,2,222.00,'2017-10-04 12:03:58','2017-10-04 12:04:18'),
  (23,0,'#1','200',250,0,4,250.00,'2017-10-11 14:39:03','2017-10-13 10:32:17'),
  (24,0,'#2','250',300,1,4,500.00,'2017-10-11 14:42:37','2017-10-13 10:30:11'),
  (25,1,'LOTE 03','02',2,1,1,2.00,'2017-10-11 10:43:53','2017-10-13 10:42:50'),
  (26,0,'#3','58',58,1,4,58.00,'2017-10-13 10:29:49','2017-10-13 10:35:16');

INSERT INTO `providers` VALUES
  (1,0,'5665416547','listener','wekmkn','45678','a@b.c',NULL,2,'listener',1,'2017-10-20 23:06:50','2017-10-20 23:06:50'),
  (90,0,'113678','cosechador 113Ba','cuidad X3','002553','g3@gmail.com','https://1d8f09d13d909de71fe8-0aa28c49e88e719ee2b71f71fdca5ee1.ssl.cf1.rackcdn.com/example/57cdedbe-756d-495b-b3cc-22cae07a8271.png',2,'cosechador 113Ba',1,'2017-09-06 20:51:09','2017-10-14 19:23:12'),
  (2,0,'11477789','darwin','caracas','145632','darwinrocha@g.com',NULL,2,'frank',1,'2017-09-06 20:51:09','2017-10-02 15:52:59'),
  (3,0,'321','rocha','1478963','1478963','1478963@f.com',NULL,2,'1478963',1,'2017-09-11 17:43:04','2017-09-14 17:24:01'),
  (4,1,'33221100','321nam','321','321','1321',NULL,1,'321',0,'2017-09-11 17:51:11','2017-09-25 16:03:51'),
  (5,0,'332211','cosechador 26m','caracas','25896321','proveedor@h.com',NULL,2,'cosechador 26m',1,'2017-09-14 11:31:23','2017-09-25 17:42:18'),(7,0,'12','proveedor 12','caracas','123456789',NULL,NULL,1,'darwin',0,'2017-09-15 12:07:11','2017-09-21 14:49:40'),(8,0,'12345678','darwin prueba 3','caracas','145632','darwinrocha@g.com',NULL,1,'frank',1,'2017-09-17 00:20:30','2017-10-09 13:39:47'),(9,0,'12345','prueba5','pto ordaz','145632','prueba5@g.com',NULL,1,'frank',0,'2017-09-17 11:28:28','2017-09-17 11:36:42'),(10,0,'1234567','prueba6','pto ordaz','145632','prueba6@g.com',NULL,2,'frank',1,'2017-09-17 11:35:31','2017-09-17 12:10:30'),(11,0,'123456789','prueba8','pto ordaz','145632','prueba8@g.com',NULL,1,'prueba8',0,'2017-09-17 15:02:11','2017-10-13 10:48:07'),(12,0,'666','eudin cosechadorx1k','here','12345','ed@haha.com','https://1d8f09d13d909de71fe8-0aa28c49e88e719ee2b71f71fdca5ee1.ssl.cf1.rackcdn.com/example/605434f3-da91-4d0f-b89d-1ddced765ea5.png',2,'eudin cosechadorx1k',1,'2017-09-17 16:55:03','2017-10-03 00:44:17'),(13,0,'6661','eudin proveedor 2','here','2343','wdw@ass.com',NULL,1,'eudin proveedor 2',0,'2017-09-17 17:09:28','2017-09-25 17:41:42'),(14,0,'2342348','jejejeps','here2','23434','wdsd@b.cc','https://1d8f09d13d909de71fe8-0aa28c49e88e719ee2b71f71fdca5ee1.ssl.cf1.rackcdn.com/example/73ae924f-daab-4de7-8700-59d52770f7dd.png',2,'jejejeps',1,'2017-09-17 17:21:25','2017-10-05 16:45:35'),(15,1,'3424','ada','ef df3232','+13453','asdasd@Axc.com','https://1d8f09d13d909de71fe8-0aa28c49e88e719ee2b71f71fdca5ee1.ssl.cf1.rackcdn.com/example/d9c0b916-b037-4f51-9bd4-91999bf2d5c4.png',2,'sdfdsfs',0,'2017-09-17 17:32:19','2017-09-25 18:28:43'),(16,1,'3423423','Allen Blue','dfsdf 34 3434','342344','ab@ab.com',NULL,2,'Allen Blue',1,'2017-09-17 17:49:49','2017-09-25 16:05:18'),(17,0,'13123','dasdadZ','asdasd3 32 4','+54654234','ewew@sd.com','https://1d8f09d13d909de71fe8-0aa28c49e88e719ee2b71f71fdca5ee1.ssl.cf1.rackcdn.com/example/3c594147-feff-4c00-9827-422a1f4e384f.png',1,'dasdadA',0,'2017-09-17 18:14:01','2017-09-26 00:03:51'),(18,1,'23123','XXX1','DSFSF','234234','ewew@cdc.com',NULL,2,'XXX1',1,'2017-09-17 18:14:28','2017-10-09 10:11:56'),(19,0,'1234567890','prueba 12','qwert','123','qwry',NULL,1,'prueba 12',0,'2017-09-18 16:37:30','2017-09-21 14:49:49'),(20,0,'1299','prueba 13333345','qwertyuio','123456','qwerty',NULL,1,'qwerty',1,'2017-09-18 18:49:35','2017-09-19 14:59:13'),(21,1,'1','123','aas','54545454',NULL,NULL,1,'asas',0,'2017-09-22 15:13:24','2017-09-25 16:03:56'),(22,1,'2','123','asasa','2132323',NULL,NULL,1,'marw',1,'2017-09-22 15:13:53','2017-09-25 16:04:00'),(23,1,'23432535','Sin foto','sdfsdfd','+24234324','ed@sdsd.com',NULL,2,'Sin foto',0,'2017-09-26 00:42:28','2017-09-26 01:03:25'),(24,0,'20286','nuevo cosechador','pto ordaz','55555','nuevo@a.com',NULL,2,'nuevo cosechador',1,'2017-09-26 12:45:23','2017-09-26 12:45:23'),(25,0,'202865','nuevo cosechador','pto ordaz','12345','a@b.c',NULL,2,'nuevo cosechador',1,'2017-09-26 13:14:07','2017-09-26 13:14:07'),(26,0,'2028654','cosechador 20','pto ordaz','123456','a@b.c',NULL,2,'cosechador 20',1,'2017-09-26 15:22:55','2017-09-26 15:22:55'),(27,0,'2147766','123','caracas','145632','darwinrocha@g.com',NULL,1,'frank',1,'2017-10-02 16:00:06','2017-10-05 15:33:22'),(28,1,'14767','123fs','caracas','145632','darwinrocha@g.com','https://1d8f09d13d909de71fe8-0aa28c49e88e719ee2b71f71fdca5ee1.ssl.cf1.rackcdn.com/example/48db230c-ba36-4fec-97b5-c6f234b61121.png',2,'123fs',1,'2017-10-02 16:32:10','2017-10-06 13:20:35'),(29,1,'8688','Nerv','Japan','164691','N@n.com',NULL,1,'Nobody',1,'2017-10-02 20:12:08','2017-10-02 20:17:57'),(30,1,'45354452352','NEW PROV','DSAD','0888','em@em.com',NULL,1,'gdfg',1,'2017-10-02 22:13:48','2017-10-02 22:13:59'),(31,1,'8787','8787','8787','8787','8787@email.com',NULL,1,'8787',1,'2017-10-02 22:25:49','2017-10-02 22:26:06'),(32,1,'9797','9797','9797','9797','9797@ball.com',NULL,1,'9797',1,'2017-10-02 22:28:34','2017-10-02 22:38:52'),(33,1,'9696','9696A','9696','9696','9696@as.com','https://1d8f09d13d909de71fe8-0aa28c49e88e719ee2b71f71fdca5ee1.ssl.cf1.rackcdn.com/example/12324fd9-63e3-4018-a760-9c694909de24.png',1,'9696',1,'2017-10-02 22:39:30','2017-10-02 22:40:42'),(34,1,'234324','Xadas','adas','34234','sdasd@as.com','https://1d8f09d13d909de71fe8-0aa28c49e88e719ee2b71f71fdca5ee1.ssl.cf1.rackcdn.com/example/85311e07-2172-4cd6-b5f4-61f339db54dc.png',1,'dsfsdf',1,'2017-10-02 22:41:10','2017-10-02 23:25:02'),(35,1,'345345','XZXZXZXC','ZXZXZXZX','3534','xsdwsd@sfds.com','https://1d8f09d13d909de71fe8-0aa28c49e88e719ee2b71f71fdca5ee1.ssl.cf1.rackcdn.com/example/b2936276-ad51-416e-9776-ea707b0ac816.png',1,'435345',1,'2017-10-02 22:52:31','2017-10-02 23:25:07'),(36,1,'9595','xdsfdsf','sdfsdf','4234','asda@ccd.com','https://1d8f09d13d909de71fe8-0aa28c49e88e719ee2b71f71fdca5ee1.ssl.cf1.rackcdn.com/example/b08b5d95-593c-4906-97bd-05dac941800d.png',1,'sdfsf',1,'2017-10-02 23:11:34','2017-10-02 23:25:05'),(37,1,'66651','xaa','adasdas','3324','dasd','https://1d8f09d13d909de71fe8-0aa28c49e88e719ee2b71f71fdca5ee1.ssl.cf1.rackcdn.com/example/7fd26584-cc96-4291-907f-0cda1a891734.png',1,'asdasda1',1,'2017-10-02 23:45:59','2017-10-02 23:48:49'),(38,0,'987654','qwertyui','qwerty','234567','qwer',NULL,2,'qwertyui',1,'2017-10-03 00:03:00','2017-10-03 00:03:22'),(39,1,'858433','Cam','Kdkdl','595890','Use@uss.con',NULL,1,'Ndds',1,'2017-10-03 01:06:01','2017-10-03 01:30:04'),(40,0,'8584335','Cam dg','Kdkdl','595890','Use@uss.con','https://1d8f09d13d909de71fe8-0aa28c49e88e719ee2b71f71fdca5ee1.ssl.cf1.rackcdn.com/example/ea0609d6-c050-4867-90d7-99de7903e0c8.png',1,'Ndds',1,'2017-10-03 01:08:40','2017-10-04 17:03:15'),(41,1,'85843353','Cam dy','Kdkdl','595890','Use@uss.con',NULL,1,'Ndds',1,'2017-10-03 01:09:57','2017-10-10 22:13:59'),(42,0,'588698888','Cam jdk','Jkekeek','5959499','Dd@dj.com',NULL,1,'Jdkddo',1,'2017-10-03 01:11:02','2017-10-03 01:11:02'),(43,0,'214773','Cam 551','Jdkdkdl','59959','As@sss.com',NULL,1,'Dkdldl',1,'2017-10-03 01:17:16','2017-10-05 11:51:23'),(44,0,'5698968','Dkkeek','Ekekek','5955','Djdjsj@gmail.com',NULL,1,'Dkdk',1,'2017-10-03 01:19:35','2017-10-03 01:19:35'),(45,0,'88985','Dkdk','Fkekek','994949','Sddl@v.c',NULL,1,'Jdkdoeo',1,'2017-10-03 01:22:20','2017-10-03 01:22:20'),(46,0,'88866322','Djdd','Dkdkdk','59599','Eddj@ddd.com','https://1d8f09d13d909de71fe8-0aa28c49e88e719ee2b71f71fdca5ee1.ssl.cf1.rackcdn.com/example/ba5a3772-a3b5-4aa8-8d66-92cf220e4d64.png',1,'Dkds',1,'2017-10-03 01:29:41','2017-10-03 01:29:49'),(47,0,'1234','vendedor','casa de vendedor','55555555','a@b.c',NULL,1,'Carlos',1,'2017-10-03 11:32:33','2017-10-03 11:32:33'),(48,0,'11877809','marwin campos','la tahona','4166073498','marwin@campo.com','https://1d8f09d13d909de71fe8-0aa28c49e88e719ee2b71f71fdca5ee1.ssl.cf1.rackcdn.com/example/0244ad27-2bd9-416f-9e3d-9457f6924599.png',2,'marwin campos',1,'2017-10-03 16:39:20','2017-10-04 16:37:03'),(49,0,'789650','prueba','prueba direccion','12345','1234',NULL,2,'prueba',1,'2017-10-03 16:42:23','2017-10-03 16:42:23'),(50,0,'12423','mario jsñose','Caracas','23232','marwin@',NULL,2,'mario jsñose',1,'2017-10-03 17:04:39','2017-10-03 17:04:39'),(51,1,'118778099','marwin campos','Caracas','212121','marwin@hotnail.com',NULL,2,'marwin campos',1,'2017-10-03 17:52:29','2017-10-03 17:53:45'),(52,0,'8668','pruebas5','caracas','145632','darwinrocha@g.com',NULL,1,'frank',1,'2017-10-03 21:42:07','2017-10-09 14:10:14'),(53,0,'58898999925','Hey 2','Kkdkld','94949','Eddy@c.xon','https://1d8f09d13d909de71fe8-0aa28c49e88e719ee2b71f71fdca5ee1.ssl.cf1.rackcdn.com/example/490a5ab2-4178-4727-accf-5c8c571b5518.png',1,'Jddo',1,'2017-10-03 21:42:58','2017-10-05 15:44:28'),(54,1,'666588','Np','Here','9695','Eddo@tun.c',NULL,2,'Np',1,'2017-10-03 23:04:24','2017-10-06 13:34:32'),(55,1,'68866','Np2','Dkkdk','5566','Ifso@dodo.com','https://1d8f09d13d909de71fe8-0aa28c49e88e719ee2b71f71fdca5ee1.ssl.cf1.rackcdn.com/example/dec9cfc1-cd02-4352-a7a5-15c0e5c8ef9b.png',2,'Np2',1,'2017-10-03 23:05:16','2017-10-10 22:13:02'),(56,1,'1459874646546','BLA BLA','BLA BLABA','1236974125','BLA@H.COM',NULL,2,'JKJK',1,'2017-10-04 15:23:03','2017-10-04 16:33:35'),(57,1,'85688','Nuevo Co','Jsksso','595#8','Edwinderepuesto@gmail.com',NULL,2,'Nuevo Co',1,'2017-10-04 15:27:24','2017-10-11 10:05:30'),(58,0,'5686666','Nc','Jekdk','96449','Edwinderepuesto@gmail.com','https://1d8f09d13d909de71fe8-0aa28c49e88e719ee2b71f71fdca5ee1.ssl.cf1.rackcdn.com/example/e4e766fb-891f-4898-9d5e-b233e20a18c0.png',2,'Nc',1,'2017-10-04 15:28:12','2017-10-04 15:28:19'),(59,0,'118778091','maria','jsjs','6565','jsjs@dox.xom','https://1d8f09d13d909de71fe8-0aa28c49e88e719ee2b71f71fdca5ee1.ssl.cf1.rackcdn.com/example/7d3f310f-2bd0-4709-a123-9a348e93c8e0.png',2,'maria',1,'2017-10-04 15:35:07','2017-10-04 15:35:25'),(60,0,'212134','qwert','qwer','21321','a@b.com',NULL,2,'qwert',1,'2017-10-05 16:14:44','2017-10-05 16:27:34'),(61,0,'8136481','darwin prueba','caracas','145632','darwinrocha@g.com',NULL,1,'frank',1,'2017-10-06 13:17:29','2017-10-09 10:57:23'),(62,1,'6986864','Rafael','costa riiba','66445533','hola@hola.com',NULL,2,'Rafael',1,'2017-10-06 15:14:40','2017-10-11 10:16:53'),(63,0,'121','ma','jsjs','1616','mj@co.xom',NULL,2,'ma',1,'2017-10-09 14:00:23','2017-10-09 17:34:09'),(64,0,'1232','asasasasasasasa','232323','32232','marwin.campos@gmail.com',NULL,2,'asasasasasasasa',1,'2017-10-09 14:00:44','2017-10-10 13:41:57'),(65,0,'1232333','Marwin Campos sdsd','34','043','marwin.campos@gmail.com',NULL,2,'err',1,'2017-10-09 14:02:57','2017-10-09 14:03:43'),(66,0,'34444','pruebas5','1212','11212','marwin.campos@gmail.com',NULL,1,'232',1,'2017-10-09 14:09:41','2017-10-09 14:09:41'),(67,0,'11877','marwin j campos','tahona','2222','maria@hecticus.com','https://1d8f09d13d909de71fe8-0aa28c49e88e719ee2b71f71fdca5ee1.ssl.cf1.rackcdn.com/example/4951f2db-926c-4b45-8be8-baace123e221.png',2,'marwin j campos',1,'2017-10-11 10:43:39','2017-10-11 10:46:46'),(68,0,'8136483','Alejandro Carvallo','coco del mar','65667896','yo@yo.com','https://1d8f09d13d909de71fe8-0aa28c49e88e719ee2b71f71fdca5ee1.ssl.cf1.rackcdn.com/example/0aa9cfa7-70b6-4e89-bffd-13110f708e13.png',2,'Alejandro Carvallo',1,'2017-10-13 10:25:47','2017-10-13 10:26:09'),(69,0,'6464619','Alejandro','djdjd','13131','ajaj@cs.com',NULL,2,'Alejandro',1,'2017-10-13 10:31:07','2017-10-13 10:31:07'),(70,0,'6466451','Alejandro 2','hshshsh','36363955','hha@jjskw.com',NULL,2,'Alejandro',1,'2017-10-13 10:33:58','2017-10-13 10:33:58'),(71,0,'64643197','Alejandro 3','jajajan','64653194','ahaja@hsjs.com',NULL,1,'aja',1,'2017-10-13 10:34:47','2017-10-13 10:34:47'),(72,0,'6655656','max','miami','58866344','hshsh@gshsh.com',NULL,2,'max',1,'2017-10-13 10:57:56','2017-10-13 10:57:56'),(73,0,'5555','hghh','gghg','6666','vggg@hhhh.net',NULL,2,'hghh',1,'2017-10-13 10:58:43','2017-10-13 10:58:43'),(74,0,'949494994','sjjss','dsss','594659','osksn@djjdd.com',NULL,1,'jdjdkd\ndddddd\nddddd\nsddddd',1,'2017-10-13 11:00:50','2017-10-13 11:00:50'),(75,0,'5457','jj','ahaha','1616','ma@com.com',NULL,2,'jj',1,'2017-10-13 11:02:13','2017-10-13 11:02:13'),(76,0,'234234','EWEWE','EWEW','23123','edwiwd@asas.com',NULL,2,'EWEWE',1,'2017-10-14 18:12:04','2017-10-14 18:12:04'),(77,0,'9963963','Tim','asasas','34234','tim@asas.com',NULL,2,'Tim',1,'2017-10-14 18:28:08','2017-10-14 18:28:08'),(78,0,'35855','Sed','Ghfy','5866','Fdg@hkjb.vom',NULL,2,'Sed',1,'2017-10-14 18:30:15','2017-10-14 18:30:15'),(79,0,'84888885','Ah bueno','Gjfjjhhh','55899','Fetdvvuh@gjvb.co','https://1d8f09d13d909de71fe8-0aa28c49e88e719ee2b71f71fdca5ee1.ssl.cf1.rackcdn.com/example/4bee9399-8977-40ca-9b7b-20c6d7dad5a3.png',1,'Gjgjbh',1,'2017-10-14 18:31:26','2017-10-14 18:31:35'),(80,0,'85888','Ghchhgh','Fjghj','98588','Llkj@hjjg.com',NULL,1,'Gjvj',1,'2017-10-14 18:32:17','2017-10-14 18:32:17'),(81,0,'588887','Gjfjh','Gyghh','98698','Ghfhv@gh.co',NULL,2,'Gjfjh',1,'2017-10-14 18:32:46','2017-10-14 18:32:46'),(82,0,'5688989','Jdkdld','Jdksld','9499494','Adndk@ddd.com',NULL,2,'Jdkdld',1,'2017-10-14 18:37:28','2017-10-14 18:37:28'),(83,0,'78768','fghfg','fghfg','56456','asdas@cdc.com',NULL,2,'fghfg',1,'2017-10-14 19:15:29','2017-10-14 19:15:29'),(84,0,'35345','rtret','fgdgdg','34534534','asdsa@asa.com',NULL,2,'rtret',1,'2017-10-14 19:22:07','2017-10-14 19:22:07'),(85,0,'12345-6-6466 DV 55','marrrrkk','bsjs','4477','mari@hot.com',NULL,1,'mar',1,'2017-10-16 14:33:26','2017-10-16 14:33:26');


INSERT INTO `invoices` VALUES (1,0,4,3,'2012-01-04 00:00:00.000000','2012-01-04 00:00:00.000000',712,'2017-09-18 13:51:46','2017-09-28 15:22:20'),(2,0,13,3,'2017-09-28 00:00:00.000000','2017-09-29 00:00:00.000000',1050,'2017-09-18 13:54:05','2017-10-02 17:27:38'),(3,0,3,3,'2012-02-02 00:00:00.000000','2012-02-02 00:00:00.000000',0,'2017-09-18 13:54:20','2017-09-18 13:54:20'),(4,0,4,0,'2012-02-02 00:00:00.000000','2012-02-02 00:00:00.000000',258,'2017-09-18 14:04:25','2017-09-18 14:24:11'),(5,0,4,3,'2012-01-04 00:00:00.000000','2012-01-04 00:00:00.000000',534,'2017-09-18 14:07:38','2017-09-27 10:58:01'),(6,0,2,3,'2012-01-04 00:00:00.000000','2012-01-04 00:00:00.000000',31058,'2017-09-22 16:00:52','2017-10-05 14:57:30'),(7,0,4,3,'2012-01-04 00:00:00.000000','2012-01-04 00:00:00.000000',2848,'2017-09-25 18:21:19','2017-09-26 10:58:54'),(8,0,12,3,'2017-09-25 00:00:00.000000','2017-09-25 00:00:00.000000',1602,'2017-09-26 11:00:31','2017-09-26 11:03:11'),(9,0,10,3,'2017-09-25 00:00:00.000000','2017-09-25 00:00:00.000000',5340,'2017-09-27 18:17:28','2017-09-27 22:50:47'),(10,0,1,3,'2017-09-28 00:00:00.000000','2017-09-29 00:00:00.000000',109830,'2017-09-27 23:35:03','2017-10-02 15:32:12'),(11,0,3,0,'2012-02-02 00:00:00.000000','2012-02-02 00:00:00.000000',258888,'2017-09-28 18:19:02','2017-09-28 18:19:02'),(12,0,3,1,'2019-09-28 00:00:00.000000','2019-09-28 00:00:00.000000',356,'2017-09-28 18:22:21','2017-10-10 15:31:17'),(13,1,13,3,'2017-09-28 00:00:00.000000','2017-09-29 00:00:00.000000',1000,'2017-09-29 00:05:48','2017-10-02 18:16:12'),(14,0,11,1,'2017-09-28 00:00:00.000000','2017-09-29 00:00:00.000000',90000,'2017-09-29 00:35:40','2017-10-02 17:27:48'),(15,0,1,3,'2017-10-02 17:19:15.000000','2017-10-03 00:00:00.000000',979,'2017-10-02 17:32:03','2017-10-02 18:23:59'),(16,1,26,3,'2017-10-02 16:31:42.000000','2017-10-02 16:31:42.000000',7565,'2017-10-02 17:37:05','2017-10-02 18:19:53'),(17,0,1,3,'2017-10-02 17:20:47.000000','2017-10-02 17:20:47.000000',211488,'2017-10-02 18:24:48','2017-10-05 14:57:18'),(21,1,5,3,'2017-10-02 17:32:04.000000','2017-10-02 17:32:04.000000',1780,'2017-10-02 18:36:04','2017-10-02 18:36:09'),(23,1,26,3,'2017-10-03 01:38:14.000000','2017-10-03 01:38:14.000000',1780,'2017-10-03 01:38:16','2017-10-03 01:38:34'),(25,0,28,3,'2017-10-03 01:44:44.000000','2017-10-04 00:00:00.000000',356,'2017-10-03 01:44:46','2017-10-03 17:25:44'),(28,0,13,3,'2017-09-28 00:00:00.000000','2017-09-28 00:00:00.000000',100,'2017-10-03 11:21:32','2017-10-03 11:21:32'),(29,0,19,3,'2017-10-03 11:30:50.000000','2017-10-04 00:00:00.000000',120000,'2017-10-03 11:31:06','2017-10-03 22:09:36'),(30,0,47,3,'2017-10-03 11:32:44.000000','2017-10-04 00:00:00.000000',15000,'2017-10-03 11:33:00','2017-10-03 17:20:51'),(31,0,9,3,'2017-10-03 12:34:33.000000','2017-10-03 12:34:33.000000',15000,'2017-10-03 11:38:08','2017-10-03 18:08:03'),(32,0,26,3,'2017-10-03 15:53:01.000000','2017-10-04 00:00:00.000000',450400,'2017-10-03 15:53:18','2017-10-03 17:14:40'),(33,1,48,3,'2017-10-03 16:41:05.000000','2017-10-04 00:00:00.000000',2670,'2017-10-03 16:41:05','2017-10-03 16:45:43'),(34,1,49,3,'2017-10-03 16:42:18.000000','2017-10-03 16:42:18.000000',2225,'2017-10-03 16:42:33','2017-10-03 17:02:03'),(35,1,50,3,'2017-10-03 17:06:03.000000','2017-10-04 00:00:00.000000',2670,'2017-10-03 17:06:03','2017-10-03 17:14:28'),(36,0,26,3,'2017-10-03 17:25:04.000000','2017-10-04 00:00:00.000000',6230,'2017-10-03 17:25:04','2017-10-03 17:31:59'),(37,0,49,3,'2017-10-03 17:26:29.000000','2017-10-04 00:00:00.000000',2670,'2017-10-03 17:26:45','2017-10-03 17:26:52'),(38,0,42,3,'2017-10-03 17:27:11.000000','2017-10-04 00:00:00.000000',1785,'2017-10-03 17:27:10','2017-10-03 17:52:55'),(39,0,14,3,'2017-10-03 17:29:10.000000','2017-10-04 00:00:00.000000',1780,'2017-10-03 17:29:09','2017-10-03 17:30:43'),(40,0,27,3,'2017-10-03 17:29:53.000000','2017-10-04 00:00:00.000000',600,'2017-10-03 17:29:53','2017-10-03 18:58:46'),(41,0,49,3,'2017-10-03 17:48:11.000000','2017-10-04 00:00:00.000000',2670,'2017-10-03 17:48:26','2017-10-03 17:48:40'),(42,0,43,3,'2017-10-03 22:10:17.000000','2017-10-04 00:00:00.000000',1,'2017-10-03 22:10:18','2017-10-03 22:10:30'),(43,0,43,3,'2017-10-03 22:14:17.000000','2017-10-04 00:00:00.000000',4,'2017-10-03 22:14:19','2017-10-03 22:14:33'),(44,0,41,3,'2017-10-03 22:18:38.000000','2017-10-04 00:00:00.000000',1,'2017-10-03 22:18:40','2017-10-03 22:18:52'),(45,0,40,3,'2017-10-03 22:27:15.000000','2017-10-04 00:00:00.000000',4,'2017-10-03 22:27:15','2017-10-03 22:27:24'),(46,0,40,3,'2017-10-03 22:27:59.000000','2017-10-04 00:00:00.000000',5,'2017-10-03 22:28:00','2017-10-03 22:29:09'),(47,0,43,3,'2017-10-03 22:29:29.000000','2017-10-04 00:00:00.000000',25,'2017-10-03 22:29:30','2017-10-03 22:29:43'),(48,0,42,3,'2017-10-03 22:30:03.000000','2017-10-04 00:00:00.000000',20,'2017-10-03 22:30:04','2017-10-03 22:30:23'),(49,0,40,3,'2017-10-03 22:31:17.000000','2017-10-04 00:00:00.000000',6,'2017-10-03 22:31:18','2017-10-03 22:31:27'),(50,0,43,3,'2017-10-03 22:42:27.000000','2017-10-04 00:00:00.000000',64,'2017-10-03 22:42:28','2017-10-03 22:42:38'),(51,0,55,3,'2017-10-03 23:05:45.000000','2017-10-04 00:00:00.000000',15764,'2017-10-03 23:05:47','2017-10-03 23:07:10'),(52,0,48,3,'2017-10-04 11:11:29.000000','2017-10-05 00:00:00.000000',2670,'2017-10-04 11:11:31','2017-10-04 11:11:48'),(53,1,48,3,'2017-10-04 11:13:21.000000','2017-10-04 11:13:21.000000',8989,'2017-10-04 11:13:22','2017-10-04 12:03:32'),(54,1,57,3,'2017-10-04 15:27:34.000000','2017-10-04 15:27:34.000000',445,'2017-10-04 15:27:36','2017-10-04 15:36:01'),(55,1,58,3,'2017-10-04 15:28:30.000000','2017-10-04 15:28:30.000000',623,'2017-10-04 15:28:33','2017-10-04 15:35:59'),(56,1,59,3,'2017-10-04 15:35:44.000000','2017-10-04 15:35:44.000000',9790,'2017-10-04 15:35:45','2017-10-04 15:36:29'),(57,1,28,3,'2017-10-04 15:41:26.000000','2017-10-04 15:41:26.000000',890,'2017-10-04 15:41:27','2017-10-04 16:22:29'),(58,1,43,3,'2017-10-04 16:46:50.000000','2017-10-04 16:46:50.000000',0,'2017-10-04 16:41:15','2017-10-04 17:38:30'),(59,0,48,3,'2017-10-04 16:52:32.000000','2017-10-04 16:52:32.000000',176962,'2017-10-04 16:52:33','2017-10-05 15:21:17'),(60,0,41,3,'2017-10-04 17:04:18.000000','2017-10-05 00:00:00.000000',75,'2017-10-04 17:04:19','2017-10-04 17:04:24'),(61,1,28,3,'2017-10-05 14:55:24.000000','2017-10-05 14:55:24.000000',0,'2017-10-05 14:55:25','2017-10-05 15:09:33'),(62,0,9,3,'2017-10-03 12:34:33.000000','2017-10-03 12:34:33.000000',18000,'2017-10-05 15:13:14','2017-10-05 15:50:45'),(63,1,9,3,'2017-10-03 12:34:33.000000','2017-10-03 12:34:33.000000',600,'2017-10-05 15:18:41','2017-10-05 16:38:54'),(64,0,48,3,'2017-10-05 15:38:39.000000','2017-10-05 15:38:39.000000',16821,'2017-10-05 15:38:40','2017-10-06 13:25:26'),(65,0,5,3,'2017-10-05 15:55:48.000000','2017-10-05 15:55:48.000000',890,'2017-10-05 15:55:49','2017-10-05 15:55:49'),(66,1,12,3,'2017-10-05 15:55:59.000000','2017-10-05 15:55:59.000000',0,'2017-10-05 15:56:00','2017-10-05 16:44:28'),(67,0,59,3,'2017-10-05 15:56:09.000000','2017-10-05 15:56:09.000000',89,'2017-10-05 15:56:10','2017-10-05 15:56:10'),(68,0,58,3,'2017-10-05 15:56:21.000000','2017-10-06 00:00:00.000000',89,'2017-10-05 15:56:22','2017-10-05 16:42:35'),(69,1,24,3,'2017-10-05 15:56:33.000000','2017-10-05 15:56:33.000000',0,'2017-10-05 15:56:34','2017-10-05 16:44:13'),(70,1,55,3,'2017-10-05 15:56:44.000000','2017-10-05 15:56:44.000000',1068,'2017-10-05 15:56:45','2017-10-05 16:42:19'),(71,0,10,3,'2017-10-05 15:56:56.000000','2017-10-05 15:56:56.000000',1068,'2017-10-05 15:56:57','2017-10-05 15:56:57'),(72,0,3,3,'2017-10-05 15:57:07.000000','2017-10-05 15:57:07.000000',890,'2017-10-05 15:57:08','2017-10-05 16:25:29'),(73,0,50,3,'2017-10-05 15:57:27.000000','2017-10-05 15:57:27.000000',1068,'2017-10-05 15:57:28','2017-10-05 15:57:28'),(74,0,40,3,'2017-10-05 16:46:14.000000','2017-10-05 16:46:14.000000',40,'2017-10-05 16:46:18','2017-10-05 16:46:18'),(75,0,43,3,'2017-10-05 16:46:57.000000','2017-10-05 16:46:57.000000',4,'2017-10-05 16:47:02','2017-10-05 16:47:02'),(76,0,17,3,'2017-10-05 16:47:40.000000','2017-10-05 16:47:40.000000',18,'2017-10-05 16:47:43','2017-10-05 17:12:38'),(77,0,46,3,'2017-10-05 16:51:27.000000','2017-10-05 16:51:27.000000',7,'2017-10-05 16:50:29','2017-10-05 16:52:23'),(78,0,42,3,'2017-10-05 16:57:58.000000','2017-10-05 16:57:58.000000',231,'2017-10-05 16:57:59','2017-10-05 16:57:59'),(79,0,27,3,'2017-10-05 17:02:45.000000','2017-10-05 17:02:45.000000',1853.2,'2017-10-05 17:02:46','2017-10-05 19:34:54'),(80,0,45,3,'2017-10-05 17:11:54.000000','2017-10-05 17:11:54.000000',4,'2017-10-05 17:11:57','2017-10-05 17:11:57'),(81,1,61,3,'2017-10-06 12:17:52.000000','2017-10-06 12:17:52.000000',0,'2017-10-06 13:17:53','2017-10-06 13:21:08'),(82,0,26,3,'2017-10-06 12:25:07.000000','2017-10-06 12:25:07.000000',5785,'2017-10-06 13:25:07','2017-10-06 13:25:07'),(83,0,7,3,'2017-10-06 12:27:16.000000','2017-10-06 12:27:16.000000',158.7,'2017-10-06 13:27:17','2017-10-06 13:27:17'),(84,0,38,3,'2017-10-09 11:15:37.000000','2017-10-09 11:15:37.000000',1335,'2017-10-09 11:15:38','2017-10-09 11:15:38'),(85,0,64,1,'2017-10-10 12:17:43.000000','2017-10-10 12:17:43.000000',0,'2017-10-10 12:17:53','2017-10-10 22:29:18'),(86,0,26,1,'2017-10-10 12:18:37.000000','2017-10-10 12:18:37.000000',22520,'2017-10-10 12:18:39','2017-10-10 12:18:39'),(87,0,3,1,'2019-09-29 00:00:00.000000','2019-09-29 00:00:00.000000',534,'2017-10-10 15:32:34','2017-10-10 16:02:45'),(88,0,3,1,'2019-09-30 10:05:00.000000','2019-09-30 10:05:00.000000',356,'2017-10-10 16:04:34','2017-10-10 16:16:32'),(89,0,3,1,'2019-10-01 10:05:00.000000','2019-10-01 10:05:00.000000',356,'2017-10-10 16:16:55','2017-10-10 16:17:11'),(90,1,38,3,'2017-10-10 17:01:32.000000','2017-10-10 17:01:32.000000',49544,'2017-10-10 17:01:33','2017-10-10 21:55:22'),(91,1,20,3,'2017-10-10 17:18:52.000000','2017-10-10 17:18:52.000000',1000,'2017-10-10 17:18:53','2017-10-10 22:01:48'),(92,1,42,1,'2017-10-10 22:14:34.000000','2017-10-10 22:14:34.000000',0,'2017-10-10 22:14:35','2017-10-10 22:29:32'),(93,0,12,1,'2017-10-11 00:16:44.000000','2017-10-11 00:16:44.000000',4450,'2017-10-11 00:16:46','2017-10-11 00:16:46'),(94,1,42,3,'2017-10-11 10:04:46.000000','2017-10-11 10:04:46.000000',560,'2017-10-11 10:04:47','2017-10-11 10:05:20'),(95,0,43,1,'2017-10-11 10:09:54.000000','2017-10-11 10:09:54.000000',719.32,'2017-10-11 10:09:55','2017-10-11 10:09:55'),(96,0,1,1,'2017-10-11 10:41:34.000000','2017-10-11 10:41:34.000000',12500,'2017-10-11 10:41:34','2017-10-11 10:41:34'),(97,0,61,1,'2017-10-11 10:41:58.000000','2017-10-11 10:41:58.000000',1000,'2017-10-11 10:41:58','2017-10-11 10:41:58'),(98,0,68,1,'2017-10-13 09:26:45.000000','2017-10-13 09:26:45.000000',287072,'2017-10-13 10:26:46','2017-10-13 11:11:23'),(99,0,27,1,'2017-10-13 09:31:14.000000','2017-10-13 09:31:14.000000',57.6,'2017-10-13 10:31:16','2017-10-13 10:31:16'),(100,0,71,1,'2017-10-13 11:13:29.000000','2017-10-13 11:13:29.000000',78144,'2017-10-13 11:13:30','2017-10-13 11:13:30'),(101,0,69,1,'2017-10-13 17:22:19.000000','2017-10-13 17:22:19.000000',29000,'2017-10-13 17:22:22','2017-10-13 17:22:22'),(102,0,69,1,'2017-10-15 14:56:49.000000','2017-10-15 14:56:49.000000',39500,'2017-10-15 15:56:50','2017-10-15 15:56:50'),(103,0,70,1,'2017-10-16 13:40:15.000000','2017-10-16 13:40:15.000000',12000,'2017-10-16 13:40:16','2017-10-16 13:40:16'),(104,0,1,1,'2017-10-16 14:30:55.000000','2017-10-16 14:30:55.000000',30000,'2017-10-16 14:30:57','2017-10-16 14:34:00'),(105,0,69,1,'2017-10-18 13:38:07.000000','2017-10-18 13:38:07.000000',1334,'2017-10-18 14:38:08','2017-10-18 14:38:08'),(106,0,14,1,'2017-10-21 19:48:35.000000','2017-10-21 19:48:35.000000',112120996,'2017-10-21 23:17:29','2017-10-21 23:17:29'),(107,0,38,1,'2017-10-22 11:29:32.000000','2017-10-22 11:29:32.000000',667000,'2017-10-22 11:29:36','2017-10-22 11:29:36'),(108,0,12,1,'2017-10-23 00:15:56.000000','2017-10-23 00:15:56.000000',12066,'2017-10-23 00:15:57','2017-10-23 00:20:56'),(109,0,84,1,'2017-10-24 01:03:06.000000','2017-10-24 01:03:06.000000',15500,'2017-10-24 01:03:09','2017-10-24 01:03:09'),(110,0,68,1,'2017-10-25 01:21:59.000000','2017-10-25 01:21:59.000000',23000,'2017-10-25 01:22:00','2017-10-25 01:22:00'),(111,0,69,1,'2017-10-25 22:27:39.000000','2017-10-25 22:27:39.000000',11000,'2017-10-25 22:27:44','2017-10-25 22:27:44'),(112,0,1,1,'2017-10-26 00:17:56.000000','2017-10-26 00:17:56.000000',65000,'2017-10-26 00:18:06','2017-10-26 00:18:06'),(113,0,73,1,'2017-10-27 23:56:51.000000','2017-10-27 23:56:51.000000',1234000,'2017-10-27 23:56:52','2017-10-27 23:56:52'),(114,0,68,1,'2017-10-30 14:57:47.000000','2017-10-30 14:57:47.000000',30000,'2017-10-30 14:57:48','2017-10-30 15:32:05'),(115,0,12,1,'2017-10-30 16:23:37.000000','2017-10-30 16:23:37.000000',50000,'2017-10-30 16:23:38','2017-10-30 16:23:39'),(116,0,69,1,'2017-10-31 10:40:58.000000','2017-10-31 10:40:58.000000',1218,'2017-10-31 10:41:16','2017-10-31 10:41:17');

INSERT INTO `item_types` VALUES (1,0,'Libras Maduro',50.00,1,1,1,'2017-09-18 14:52:15','2017-09-18 14:52:15'),(2,0,'Libras Verdes',20.00,1,1,1,'2017-09-18 14:53:46','2017-09-18 14:53:46'),(3,0,'Libras Seco',30.00,1,1,1,'2017-09-18 14:59:37','2017-09-18 15:38:01'),(4,0,'Libras Cereza',40.00,1,2,1,'2017-09-18 14:59:37','2017-09-18 14:59:37'),(5,0,'Libras Pergamino',25.00,1,2,1,'2017-09-18 14:59:37','2017-09-18 14:59:37'),(6,1,'Libras Cosechas Eliminadas',10.00,1,1,1,'2017-09-18 14:59:37','2017-09-18 14:59:37'),(7,1,'Libras Compras Eliminadas',15.00,1,2,1,'2017-09-18 14:59:37','2017-09-18 14:59:37');

INSERT INTO `invoice_details` VALUES (1,0,1,1,1,NULL,25.00,0.00,'2018-02-02 00:00:00.000000',3580,1,NULL,'bane R 11','name D 22',1,'2017-09-18 16:32:45','2017-09-18 16:56:45'),(2,0,5,1,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-18 16:32:45','2017-09-27 10:58:00'),(3,0,1,4,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',50,0,NULL,'nameDelivered 1','nameReceived 1',1,'2017-09-22 15:46:28','2017-09-22 15:46:28'),(4,0,1,4,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',50,0,NULL,'nameDelivered 1','nameReceived 1',1,'2017-09-22 15:48:08','2017-09-22 15:48:08'),(5,0,5,2,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',6,0,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-22 15:48:56','2017-09-27 10:58:01'),(6,0,6,4,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',1,0,NULL,'nameDelivered 11','nameReceived 11',1,'2017-09-22 16:00:52','2017-09-22 16:00:52'),(7,0,6,4,1,NULL,89.00,0.00,'2017-09-28 00:00:00.000000',2,1,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-22 16:02:34','2017-09-28 16:22:33'),(8,0,6,4,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,NULL,'nameDelivered 11','nameReceived 11',1,'2017-09-22 16:14:29','2017-09-22 16:14:29'),(9,0,6,4,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,NULL,'nameDelivered 11','nameReceived 11',1,'2017-09-22 16:22:41','2017-09-22 16:22:41'),(10,0,6,4,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,NULL,'nameDelivered 11','nameReceived 11',1,'2017-09-22 16:22:59','2017-09-22 16:22:59'),(11,0,6,4,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,NULL,'nameDelivered 11','nameReceived 11',1,'2017-09-25 14:32:29','2017-09-25 14:32:29'),(12,0,6,1,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,NULL,'nameDelivered 11','nameReceived 11',1,'2017-09-25 14:34:19','2017-09-25 14:34:19'),(13,0,6,2,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,NULL,'nameDelivered 11','nameReceived 11',1,'2017-09-25 14:34:19','2017-09-25 14:34:19'),(14,0,6,3,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,NULL,'nameDelivered 11','nameReceived 11',1,'2017-09-25 14:34:20','2017-09-25 14:34:20'),(15,0,6,4,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,NULL,'nameDelivered 11','nameReceived 11',1,'2017-09-25 14:34:20','2017-09-25 14:34:20'),(16,0,6,5,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,NULL,'nameDelivered 11','nameReceived 11',1,'2017-09-25 14:34:20','2017-09-25 14:34:20'),(17,0,6,1,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,NULL,'nameDelivered 11','nameReceived 11',1,'2017-09-25 14:36:07','2017-09-25 14:36:07'),(18,0,6,2,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,NULL,'nameDelivered 11','nameReceived 11',1,'2017-09-25 14:36:07','2017-09-25 14:36:07'),(19,0,6,3,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,NULL,'nameDelivered 11','nameReceived 11',1,'2017-09-25 14:36:07','2017-09-25 14:36:07'),(20,0,6,4,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,NULL,'nameDelivered 11','nameReceived 11',1,'2017-09-25 14:36:07','2017-09-25 14:36:07'),(21,0,6,5,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,NULL,'nameDelivered 11','nameReceived 11',1,'2017-09-25 14:36:07','2017-09-25 14:36:07'),(22,0,6,1,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,NULL,'nameDelivered 11','nameReceived 11',1,'2017-09-25 14:36:17','2017-09-25 14:36:17'),(23,0,6,2,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,NULL,'nameDelivered 11','nameReceived 11',1,'2017-09-25 14:36:17','2017-09-25 14:36:17'),(24,0,6,3,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,NULL,'nameDelivered 11','nameReceived 11',1,'2017-09-25 14:36:17','2017-09-25 14:36:17'),(25,0,6,4,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,NULL,'nameDelivered 11','nameReceived 11',1,'2017-09-25 14:36:17','2017-09-25 14:36:17'),(26,0,6,5,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,NULL,'nameDelivered 11','nameReceived 11',1,'2017-09-25 14:36:18','2017-09-25 14:36:18'),(27,0,6,5,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,NULL,'nameDelivered 11','nameReceived 11',1,'2017-09-25 15:47:54','2017-09-25 15:47:54'),(28,0,6,4,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,NULL,'nameDelivered 11','nameReceived 11',1,'2017-09-25 15:47:54','2017-09-25 15:47:54'),(29,0,6,3,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,NULL,'nameDelivered 11','nameReceived 11',1,'2017-09-25 15:47:54','2017-09-25 15:47:54'),(30,0,6,2,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,NULL,'nameDelivered 11','nameReceived 11',1,'2017-09-25 15:47:54','2017-09-25 15:47:54'),(31,0,6,1,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,NULL,'nameDelivered 11','nameReceived 11',1,'2017-09-25 15:47:55','2017-09-25 15:47:55'),(32,0,6,1,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-25 18:05:48','2017-09-25 18:05:48'),(33,0,6,5,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',6,0,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-25 18:06:47','2017-09-25 18:06:47'),(34,0,6,4,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-25 18:16:00','2017-09-25 18:16:00'),(35,0,6,2,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',6,0,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-25 18:16:26','2017-09-25 18:16:26'),(36,0,6,4,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-25 18:18:30','2017-09-25 18:18:30'),(37,0,6,2,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',6,0,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-25 18:19:49','2017-09-25 18:19:49'),(38,0,7,4,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-25 18:21:19','2017-09-25 18:21:19'),(39,0,7,2,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',6,0,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-25 18:21:44','2017-09-25 18:21:44'),(40,0,7,4,1,NULL,89.00,0.00,'2017-09-25 00:00:00.000000',2,0,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-25 22:50:06','2017-09-25 22:50:06'),(41,0,7,2,1,NULL,89.00,0.00,'2017-09-25 00:00:00.000000',6,0,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-25 22:50:07','2017-09-25 22:50:07'),(42,0,7,4,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-26 10:58:06','2017-09-26 10:58:06'),(43,0,7,2,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',6,0,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-26 10:58:06','2017-09-26 10:58:06'),(44,0,7,4,1,NULL,89.00,0.00,'2017-09-25 00:00:00.000000',2,0,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-26 10:58:54','2017-09-26 10:58:54'),(45,0,7,2,1,NULL,89.00,0.00,'2017-09-25 00:00:00.000000',6,0,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-26 10:58:54','2017-09-26 10:58:54'),(46,0,8,4,1,NULL,89.00,0.00,'2017-09-27 00:00:00.000000',2,0,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-26 11:00:31','2017-09-26 11:00:31'),(47,0,8,2,1,NULL,89.00,0.00,'2017-09-27 00:00:00.000000',6,0,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-26 11:00:31','2017-09-26 11:00:31'),(48,1,8,5,1,NULL,89.00,0.00,'2017-09-27 00:00:00.000000',10,0,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-26 11:03:11','2017-09-26 11:03:11'),(49,0,9,4,1,NULL,89.00,0.00,'2017-09-27 00:00:00.000000',10,0,'observaciones','nameDelivered 11','nameReceived 11',1,'2017-09-27 18:17:28','2017-09-27 18:21:02'),(50,0,9,2,1,NULL,89.00,0.00,'2017-09-25 00:00:00.000000',6,0,'observaciones','nameDelivered 11','nameReceived 11',1,'2017-09-27 18:17:28','2017-09-27 18:17:28'),(51,0,9,4,1,NULL,89.00,0.00,'2017-09-25 00:00:00.000000',10,0,'observaciones','nameDelivered 11','nameReceived 11',1,'2017-09-27 18:24:25','2017-09-27 18:24:25'),(52,0,9,4,1,NULL,89.00,0.00,'2017-09-25 00:00:00.000000',10,0,'observaciones','nameDelivered 11','nameReceived 11',1,'2017-09-27 18:26:00','2017-09-27 18:26:00'),(53,1,9,4,1,NULL,89.00,0.00,'2017-09-27 00:00:00.000000',10,0,'observaciones','nameDelivered 11','nameReceived 11',1,'2017-09-27 18:26:30','2017-09-27 18:26:30'),(54,0,9,4,1,NULL,89.00,0.00,'2017-09-25 00:00:00.000000',10,0,'observaciones','nameDelivered 11','nameReceived 11',1,'2017-09-27 18:33:03','2017-09-27 18:33:03'),(55,0,9,4,1,NULL,89.00,0.00,'2017-09-25 00:00:00.000000',10,0,'observaciones','nameDelivered 11','nameReceived 11',1,'2017-09-27 22:50:47','2017-09-27 22:50:47'),(56,1,10,4,3,NULL,5230.00,0.00,'2017-09-28 00:00:00.000000',21,1,'observaciones','cosechador 113','cosechador 113',1,'2017-09-27 23:35:03','2017-09-28 17:18:58'),(57,0,10,5,3,NULL,5230.00,0.00,'2017-09-27 00:00:00.000000',10,0,'nada','cosechador 113','',1,'2017-09-27 23:35:03','2017-09-27 23:35:03'),(58,0,1,4,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-28 15:22:20','2017-09-28 15:22:20'),(59,0,2,4,1,NULL,89.00,0.00,'2017-09-28 00:00:00.000000',2,1,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-28 15:27:11','2017-09-28 19:16:47'),(60,0,2,4,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,0,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-28 15:37:53','2017-09-28 15:37:53'),(61,0,2,4,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,1,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-28 19:09:10','2017-09-28 19:09:10'),(62,0,2,4,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,1,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-28 19:12:59','2017-09-28 19:12:59'),(63,0,2,4,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,1,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-28 19:13:07','2017-09-28 19:13:07'),(64,0,2,4,1,NULL,89.00,0.00,'2017-09-30 00:00:00.000000',2,1,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-28 19:13:36','2017-09-28 19:13:36'),(65,0,2,4,1,NULL,89.00,0.00,'2012-01-04 00:00:00.000000',2,1,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-28 19:14:26','2017-09-28 19:17:23'),(66,0,2,4,1,NULL,89.00,0.00,'2017-09-28 00:00:00.000000',2,1,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-28 23:59:47','2017-09-28 23:59:47'),(67,0,13,4,1,NULL,89.00,0.00,'2017-09-28 00:00:00.000000',20,1,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-29 00:05:48','2017-09-29 00:09:32'),(68,0,2,1,1,NULL,89.00,0.00,'2017-09-28 00:00:00.000000',10,1,'nada','Edwin','',1,'2017-09-29 00:24:23','2017-09-29 00:24:23'),(69,0,14,3,1,1,89.00,3000.00,'2017-09-28 00:00:00.000000',30,1,'texto opcional','nameDelivered 11','nameReceived 11',1,'2017-09-29 00:35:40','2017-10-02 15:57:51'),(70,0,14,3,1,1,89.00,50.00,'2017-09-28 00:00:00.000000',70,1,'texto opcional','nameReceived 11','nameDelivered 11',1,'2017-09-29 00:40:19','2017-10-02 15:31:18'),(71,0,14,3,1,NULL,89.00,0.00,'2017-09-28 00:00:00.000000',10,0,'x','otro','',1,'2017-09-29 01:11:46','2017-09-29 01:11:46'),(72,0,14,2,1,NULL,89.00,0.00,'2017-09-29 00:00:00.000000',10,0,'x','otro','',1,'2017-09-29 01:16:54','2017-09-29 01:16:54'),(73,0,15,4,4,NULL,89.00,0.00,'2017-10-02 16:28:03.000000',10,1,'ninguna','','cosechador 113',1,'2017-10-02 17:32:03','2017-10-02 17:32:03'),(74,0,15,5,4,NULL,89.00,0.00,'2017-10-02 16:28:03.000000',20,1,'ninguna','','cosechador 113',1,'2017-10-02 17:32:04','2017-10-02 17:32:04'),(75,0,16,4,4,NULL,89.00,0.00,'2017-10-02 16:31:42.000000',10,1,'nada','','cosechador 20',1,'2017-10-02 17:37:05','2017-10-02 17:37:05'),(76,0,16,5,4,NULL,89.00,0.00,'2017-10-02 16:31:42.000000',15,1,'nada','','cosechador 20',1,'2017-10-02 17:37:05','2017-10-02 17:37:05'),(77,0,16,4,4,NULL,89.00,0.00,'2017-10-02 16:34:43.000000',10,1,'nada2','','cosechador 20',1,'2017-10-02 17:39:26','2017-10-02 17:39:26'),(78,0,16,5,4,NULL,89.00,0.00,'2017-10-02 16:34:43.000000',20,1,'nada2','','cosechador 20',1,'2017-10-02 17:39:26','2017-10-02 17:39:26'),(79,0,16,4,4,NULL,89.00,0.00,'2017-10-02 16:34:43.000000',10,1,'nada 5','','xx',1,'2017-10-02 17:41:42','2017-10-02 17:41:42'),(80,0,16,5,4,NULL,89.00,0.00,'2017-10-02 16:34:43.000000',20,1,'nada 5','','xx',1,'2017-10-02 17:41:42','2017-10-02 17:41:42'),(81,0,15,4,1,NULL,89.00,0.00,'2017-10-02 17:19:15.000000',11,1,'nada','','cosechador 113',1,'2017-10-02 18:23:15','2017-10-02 18:23:49'),(82,0,15,5,1,NULL,89.00,0.00,'2017-10-02 17:19:15.000000',30,1,'nada','','cosechador 113',1,'2017-10-02 18:23:15','2017-10-02 18:23:15'),(83,0,17,4,2,NULL,2252.00,0.00,'2017-10-02 17:20:47.000000',50,1,'sin observacion','','cosechador 113',1,'2017-10-02 18:24:48','2017-10-02 18:24:48'),(84,0,17,5,2,NULL,2252.00,0.00,'2017-10-02 17:20:47.000000',30,1,'sin observacion','','cosechador 113',1,'2017-10-02 18:24:48','2017-10-02 18:24:48'),(85,0,21,4,1,NULL,89.00,0.00,'2017-10-02 17:32:04.000000',10,1,'wert','','cosechador 26m',1,'2017-10-02 18:36:04','2017-10-02 18:36:04'),(86,0,21,5,1,NULL,89.00,0.00,'2017-10-02 17:32:04.000000',10,1,'wert','','cosechador 26m',1,'2017-10-02 18:36:04','2017-10-02 18:36:04'),(87,0,23,4,1,NULL,89.00,0.00,'2017-10-03 01:38:14.000000',20,1,'Ah ','Edwin de respuesto','cosechador 20',1,'2017-10-03 01:38:16','2017-10-03 01:38:16'),(88,1,25,4,1,NULL,89.00,0.00,'2017-10-03 01:44:44.000000',2,1,'222sds','Edwin de respuesto','123fs',1,'2017-10-03 01:44:46','2017-10-03 01:44:46'),(89,1,25,5,1,NULL,89.00,0.00,'2017-10-03 01:44:44.000000',2,1,'222sds','Edwin de respuesto','123fs',1,'2017-10-03 01:44:46','2017-10-03 01:44:46'),(90,0,6,4,NULL,1,0.00,50.00,'2012-01-04 00:00:00.000000',2,1,'texto opcional','nameReceived 11','nameDelivered 11',1,'2017-10-03 09:56:39','2017-10-03 09:56:39'),(91,0,6,4,NULL,1,0.00,50.00,'2012-01-04 00:00:00.000000',2,1,'texto opcional','nameReceived 11','nameDelivered 11',1,'2017-10-03 11:20:13','2017-10-03 11:20:13'),(92,0,28,4,NULL,1,0.00,50.00,'2017-09-28 00:00:00.000000',2,1,'texto opcional','nameReceived 11','nameDelivered 11',1,'2017-10-03 11:21:32','2017-10-03 11:21:32'),(93,0,29,2,NULL,2,0.00,1000.00,'2017-10-03 11:30:50.000000',120,1,'ninguna','','Juan perez',1,'2017-10-03 11:31:06','2017-10-03 11:41:19'),(94,0,30,1,NULL,1,0.00,50.00,'2017-10-03 11:32:44.000000',300,1,'nada','','Pedro Martinez',1,'2017-10-03 11:33:00','2017-10-03 11:33:00'),(95,0,31,1,NULL,1,0.00,10.00,'2017-10-03 11:34:33.000000',500,1,'nada','','nadie',1,'2017-10-03 11:38:08','2017-10-03 11:38:08'),(96,0,31,1,NULL,1,0.00,50.00,'2017-10-03 12:34:33.000000',300,1,'texto opcional','nadie','otro',1,'2017-10-03 13:59:22','2017-10-03 13:59:22'),(97,0,31,1,NULL,1,0.00,50.00,'2017-10-03 12:34:33.000000',300,1,'texto opcional','nadie','otro',1,'2017-10-03 15:15:18','2017-10-03 15:15:18'),(98,0,31,1,NULL,1,0.00,50.00,'2017-10-03 12:34:33.000000',300,1,'texto opcional','nadie','otro',1,'2017-10-03 15:26:27','2017-10-03 15:26:27'),(99,0,31,1,NULL,1,0.00,50.00,'2017-10-03 12:34:33.000000',300,1,'texto opcional','nadie','otro',1,'2017-10-03 15:43:07','2017-10-03 15:43:07'),(100,1,32,4,2,NULL,2252.00,0.00,'2017-10-03 15:53:01.000000',100,1,'nada','','cosechador 20',1,'2017-10-03 15:53:18','2017-10-03 15:53:18'),(101,1,32,5,2,NULL,2252.00,0.00,'2017-10-03 15:53:01.000000',100,1,'nada','','cosechador 20',1,'2017-10-03 15:53:18','2017-10-03 15:53:18'),(102,0,6,4,4,NULL,100.00,0.00,'2017-10-03 16:38:20.000000',22,1,'ninguna','Andres Campo','darwin',1,'2017-10-03 16:38:19','2017-10-03 16:38:19'),(103,1,33,5,1,NULL,89.00,0.00,'2017-10-03 16:41:05.000000',30,1,'','Andres Campo','marwin campos',1,'2017-10-03 16:41:05','2017-10-03 16:41:05'),(104,1,34,4,1,NULL,89.00,0.00,'2017-10-03 16:42:18.000000',10,1,'nada','','prueba',1,'2017-10-03 16:42:33','2017-10-03 16:42:33'),(105,1,34,5,1,NULL,89.00,0.00,'2017-10-03 16:42:18.000000',15,1,'nada','','prueba',1,'2017-10-03 16:42:33','2017-10-03 16:42:33'),(106,0,35,4,1,NULL,89.00,0.00,'2017-10-03 17:06:03.000000',30,1,'','Andres Campo','mario jsñose',1,'2017-10-03 17:06:03','2017-10-03 17:06:03'),(107,0,36,4,1,NULL,89.00,0.00,'2017-10-03 17:25:04.000000',20,1,'','Andres Campo','cosechador 20',1,'2017-10-03 17:25:04','2017-10-03 17:25:04'),(108,0,36,5,1,NULL,89.00,0.00,'2017-10-03 17:25:25.000000',15,1,'','Andres Campo','cosechador 20',1,'2017-10-03 17:25:24','2017-10-03 17:25:24'),(109,0,36,4,1,NULL,89.00,0.00,'2017-10-03 17:26:17.000000',35,1,'','Andres Campo','cosechador 20',1,'2017-10-03 17:26:16','2017-10-03 17:26:16'),(110,0,37,4,1,NULL,89.00,0.00,'2017-10-03 17:26:29.000000',15,1,'z','','prueba',1,'2017-10-03 17:26:45','2017-10-03 17:26:45'),(111,0,37,5,1,NULL,89.00,0.00,'2017-10-03 17:26:29.000000',15,1,'z','','prueba',1,'2017-10-03 17:26:45','2017-10-03 17:26:45'),(112,0,38,2,NULL,2,0.00,28.00,'2017-10-03 17:27:11.000000',20,1,'','Andres Campo','marwin campos',1,'2017-10-03 17:27:10','2017-10-03 17:27:10'),(113,0,38,1,NULL,1,0.00,35.00,'2017-10-03 17:28:38.000000',35,1,'','Andres Campo','maria',1,'2017-10-03 17:28:38','2017-10-03 17:28:38'),(114,0,39,4,1,NULL,89.00,0.00,'2017-10-03 17:29:10.000000',20,1,'','Andres Campo','jejejepsx',1,'2017-10-03 17:29:09','2017-10-03 17:29:09'),(115,0,40,1,NULL,1,0.00,30.00,'2017-10-03 17:29:53.000000',20,1,'','Andres Campo','andre',1,'2017-10-03 17:29:53','2017-10-03 17:29:53'),(116,0,41,4,1,NULL,89.00,0.00,'2017-10-03 17:48:11.000000',15,1,'nada','','prueba',1,'2017-10-03 17:48:26','2017-10-03 17:48:26'),(117,0,41,5,1,NULL,89.00,0.00,'2017-10-03 17:48:11.000000',15,1,'nada','','prueba',1,'2017-10-03 17:48:26','2017-10-03 17:48:26'),(118,0,42,1,NULL,1,0.00,1.00,'2017-10-03 22:10:17.000000',1,1,'none','Edwin de respuesto','disp',1,'2017-10-03 22:10:18','2017-10-03 22:10:18'),(119,0,43,1,NULL,1,0.00,2.00,'2017-10-03 22:14:17.000000',2,1,'2','Edwin de respuesto','2',1,'2017-10-03 22:14:19','2017-10-03 22:14:19'),(120,0,44,1,NULL,1,0.00,1.00,'2017-10-03 22:18:38.000000',1,1,'sdsd','Edwin de respuesto','sdsd',1,'2017-10-03 22:18:40','2017-10-03 22:18:40'),(121,0,45,1,NULL,1,0.00,2.00,'2017-10-03 22:27:15.000000',2,1,'dffd','Edwin de respuesto','df',1,'2017-10-03 22:27:15','2017-10-03 22:27:15'),(122,0,46,1,NULL,1,0.00,2.50,'2017-10-03 22:27:59.000000',2,1,'sd','Edwin de respuesto','dsd',1,'2017-10-03 22:28:00','2017-10-03 22:28:46'),(123,0,47,1,NULL,1,0.00,5.00,'2017-10-03 22:29:29.000000',5,1,'etret','Edwin de respuesto','rtert',1,'2017-10-03 22:29:30','2017-10-03 22:29:30'),(124,0,48,1,NULL,1,0.00,4.00,'2017-10-03 22:30:03.000000',5,1,'dfd','Edwin de respuesto','df',1,'2017-10-03 22:30:04','2017-10-03 22:30:04'),(125,0,49,1,NULL,1,0.00,2.00,'2017-10-03 22:31:17.000000',3,1,'asdad','Edwin de respuesto','sada',1,'2017-10-03 22:31:18','2017-10-03 22:31:18'),(126,0,50,1,NULL,1,0.00,2.00,'2017-10-03 22:42:27.000000',32,1,'asdasd','Edwin de respuesto','adas',1,'2017-10-03 22:42:28','2017-10-03 22:42:28'),(127,0,51,4,2,NULL,2252.00,0.00,'2017-10-03 23:05:45.000000',3,1,'Jeje ','Edwin de respuesto','Np2',1,'2017-10-03 23:05:47','2017-10-03 23:05:47'),(128,0,51,5,2,NULL,2252.00,0.00,'2017-10-03 23:05:45.000000',4,1,'Jeje ','Edwin de respuesto','Np2',1,'2017-10-03 23:05:47','2017-10-03 23:05:47'),(129,1,52,4,1,NULL,89.00,0.00,'2017-10-04 11:11:29.000000',30,1,'','Andres Campo','marwin campos',1,'2017-10-04 11:11:31','2017-10-04 11:11:31'),(130,1,53,4,1,NULL,89.00,0.00,'2017-10-04 11:13:21.000000',45,1,'nota','Andres Campo','marwin campos',1,'2017-10-04 11:13:22','2017-10-04 12:03:27'),(131,1,53,4,1,NULL,89.00,0.00,'2017-10-04 11:15:05.000000',56,1,'','Andres Campo','marwin campos',1,'2017-10-04 11:15:06','2017-10-04 12:02:57'),(132,0,54,4,1,NULL,89.00,0.00,'2017-10-04 15:27:34.000000',2,1,'Here','Edwin de respuesto','Nuevo Co',1,'2017-10-04 15:27:36','2017-10-04 15:27:36'),(133,0,54,5,1,NULL,89.00,0.00,'2017-10-04 15:27:34.000000',3,1,'Here','Edwin de respuesto','Nuevo Co',1,'2017-10-04 15:27:37','2017-10-04 15:27:37'),(134,1,55,4,1,NULL,89.00,0.00,'2017-10-04 15:28:30.000000',4,1,'3ddd','Edwin de respuesto','Nc',1,'2017-10-04 15:28:33','2017-10-04 15:28:33'),(135,1,55,5,1,NULL,89.00,0.00,'2017-10-04 15:28:30.000000',3,1,'3ddd','Edwin de respuesto','Nc',1,'2017-10-04 15:28:33','2017-10-04 15:28:33'),(136,1,56,4,1,NULL,89.00,0.00,'2017-10-04 15:35:44.000000',60,1,'','Andres Campo','maria',1,'2017-10-04 15:35:45','2017-10-04 15:35:45'),(137,1,56,5,1,NULL,89.00,0.00,'2017-10-04 15:35:44.000000',50,1,'','Andres Campo','maria',1,'2017-10-04 15:35:45','2017-10-04 15:35:45'),(138,1,57,4,1,NULL,80.00,0.00,'2017-10-04 15:41:26.000000',10,1,'','Andres Campo','123fs',1,'2017-10-04 15:41:27','2017-10-04 15:41:27'),(139,1,57,4,1,NULL,9.00,0.00,'2017-10-04 15:41:27.000000',10,1,'','Andres Campo','123fs',1,'2017-10-04 15:41:27','2017-10-04 15:41:27'),(140,0,17,4,1,NULL,89.00,0.00,'2017-10-04 16:37:51.000000',22,1,'','Andres Campo','cosechador 113',1,'2017-10-04 16:37:52','2017-10-04 16:37:52'),(141,0,6,4,1,NULL,89.00,0.00,'2017-10-04 16:38:12.000000',300,1,'','Andres Campo','darwin',1,'2017-10-04 16:38:12','2017-10-04 16:38:12'),(142,0,17,4,1,NULL,89.00,0.00,'2017-10-04 16:38:47.000000',300,1,'','Andres Campo','cosechador 113',1,'2017-10-04 16:38:47','2017-10-04 16:38:47'),(143,1,58,1,NULL,2,0.00,10.00,'2017-10-04 16:41:15.000000',20,1,'ninguna','Andres Campo','maria',1,'2017-10-04 16:41:15','2017-10-04 16:45:04'),(144,1,58,2,NULL,2,0.00,305.02,'2017-10-04 16:46:50.000000',305,1,'','Andres Campo','maria',1,'2017-10-04 16:46:51','2017-10-04 16:47:12'),(145,1,58,1,NULL,1,0.00,35.00,'2017-10-04 16:48:16.000000',20,1,'','Andres Campo','Carlos',1,'2017-10-04 16:48:17','2017-10-04 16:48:17'),(146,1,59,4,3,NULL,5230.00,0.00,'2017-10-04 16:52:32.000000',30,1,'','Andres Campo','marwin campos',1,'2017-10-04 16:52:33','2017-10-04 16:52:33'),(147,1,59,5,1,NULL,89.00,0.00,'2017-10-04 16:53:13.000000',10,1,'','Andres Campo','marwin campos',1,'2017-10-04 16:53:14','2017-10-04 16:53:14'),(148,0,60,1,NULL,2,0.00,25.00,'2017-10-04 17:04:18.000000',3,1,'','Andres Campo','ggd',1,'2017-10-04 17:04:19','2017-10-04 17:04:19'),(149,0,59,4,4,NULL,100.00,0.00,'2017-10-05 14:55:07.000000',30,1,'','Andres Campo','marwin campos',1,'2017-10-05 14:55:08','2017-10-05 14:55:08'),(150,1,61,4,1,NULL,89.00,0.00,'2017-10-05 14:55:24.000000',30,1,'','Andres Campo','123fs',1,'2017-10-05 14:55:25','2017-10-05 14:55:25'),(151,0,59,4,4,NULL,100.00,0.00,'2017-10-05 14:55:44.000000',30,1,'','Andres Campo','marwin campos',1,'2017-10-05 14:55:46','2017-10-05 14:55:46'),(152,0,59,4,1,NULL,89.00,0.00,'2017-10-05 14:57:03.000000',30,1,'','Andres Campo','marwin campos',1,'2017-10-05 14:57:04','2017-10-05 14:57:04'),(153,0,17,4,1,NULL,89.00,0.00,'2017-10-05 14:57:17.000000',30,1,'','Andres Campo','cosechador 113',1,'2017-10-05 14:57:18','2017-10-05 14:57:18'),(154,0,6,5,1,NULL,89.00,0.00,'2017-10-05 14:57:30.000000',20,1,'','Andres Campo','darwin',1,'2017-10-05 14:57:30','2017-10-05 14:57:30'),(155,0,59,4,1,NULL,89.00,0.00,'2017-10-05 15:01:48.000000',5,1,'','Andres Campo','marwin campos',1,'2017-10-05 15:01:50','2017-10-05 15:01:50'),(156,0,59,4,1,NULL,89.00,0.00,'2017-10-05 15:06:10.000000',20,1,'Hehe he ','Edwin de respuesto','marwin campos',1,'2017-10-05 15:06:13','2017-10-05 15:06:13'),(157,0,59,5,1,NULL,89.00,0.00,'2017-10-05 15:06:10.000000',30,1,'Hehe he ','Edwin de respuesto','marwin campos',1,'2017-10-05 15:06:13','2017-10-05 15:06:13'),(158,1,62,4,1,NULL,89.00,0.00,'2017-10-05 15:13:13.000000',30,1,'','Andres Campo','123fs',1,'2017-10-05 15:13:14','2017-10-05 15:13:14'),(159,0,59,4,1,NULL,89.00,0.00,'2017-10-05 15:13:33.000000',3,1,'Ah ','Edwin de respuesto','marwin campos',1,'2017-10-05 15:13:37','2017-10-05 15:13:37'),(160,0,59,5,1,NULL,89.00,0.00,'2017-10-05 15:13:33.000000',45,1,'Ah ','Edwin de respuesto','marwin campos',1,'2017-10-05 15:13:37','2017-10-05 15:13:37'),(161,0,62,1,1,1,89.00,10.00,'2017-10-03 12:34:33.000000',300,1,'texto opcional','nadie','otro',1,'2017-10-05 15:15:35','2017-10-05 15:50:37'),(162,0,63,1,NULL,1,0.00,10.00,'2017-10-03 12:34:33.000000',40,1,'texto opcional','nadie','otro',1,'2017-10-05 15:18:41','2017-10-05 15:54:34'),(163,0,59,4,1,NULL,89.00,0.00,'2017-10-05 15:19:34.000000',3,1,'Gttt','Edwin de respuesto','marwin campos',1,'2017-10-05 15:19:38','2017-10-05 15:19:38'),(164,0,59,5,1,NULL,89.00,0.00,'2017-10-05 15:19:34.000000',3,1,'Gttt','Edwin de respuesto','marwin campos',1,'2017-10-05 15:19:38','2017-10-05 15:19:38'),(165,0,59,4,1,NULL,89.00,0.00,'2017-10-05 15:21:13.000000',4,1,'Tyyyy','Edwin de respuesto','marwin campos',1,'2017-10-05 15:21:17','2017-10-05 15:21:17'),(166,0,59,5,1,NULL,89.00,0.00,'2017-10-05 15:21:13.000000',5,1,'Tyyyy','Edwin de respuesto','marwin campos',1,'2017-10-05 15:21:17','2017-10-05 15:21:17'),(167,0,63,3,NULL,1,0.00,10.00,'2017-10-05 15:22:34.000000',20,1,'','Andres Campo','André',1,'2017-10-05 15:22:35','2017-10-05 15:26:08'),(168,0,62,1,1,1,89.00,50.00,'2017-10-03 12:34:33.000000',300,1,'texto opcional','nadie','otro',1,'2017-10-05 15:27:07','2017-10-05 15:47:24'),(169,0,64,4,1,NULL,89.00,0.00,'2017-10-05 15:38:39.000000',30,1,'','Andres Campo','marwin campos',1,'2017-10-05 15:38:40','2017-10-05 15:38:40'),(170,0,65,4,1,NULL,89.00,0.00,'2017-10-05 15:55:48.000000',10,1,'','Andres Campo','cosechador 26m',1,'2017-10-05 15:55:49','2017-10-05 15:55:49'),(171,1,66,4,1,NULL,89.00,0.00,'2017-10-05 15:55:59.000000',20,1,'','Andres Campo','eudin cosechadorx1k',1,'2017-10-05 15:56:00','2017-10-05 15:56:00'),(172,0,67,4,1,NULL,89.00,0.00,'2017-10-05 15:56:09.000000',1,1,'','Andres Campo','maria',1,'2017-10-05 15:56:10','2017-10-05 15:56:10'),(173,0,68,4,1,NULL,89.00,0.00,'2017-10-05 15:56:21.000000',1,1,'','Andres Campo','Nc',1,'2017-10-05 15:56:22','2017-10-05 15:56:22'),(174,1,69,4,1,NULL,89.00,0.00,'2017-10-05 15:56:33.000000',128,1,'Jeje ','Andres Campo','nuevo cosechador',1,'2017-10-05 15:56:34','2017-10-05 16:43:12'),(175,0,70,4,1,NULL,89.00,0.00,'2017-10-05 15:56:44.000000',12,1,'','Andres Campo','Np2',1,'2017-10-05 15:56:45','2017-10-05 15:56:45'),(176,0,71,4,1,NULL,89.00,0.00,'2017-10-05 15:56:56.000000',12,1,'','Andres Campo','prueba6',1,'2017-10-05 15:56:57','2017-10-05 15:56:57'),(177,0,72,4,1,NULL,89.00,0.00,'2017-10-05 15:57:07.000000',10,1,'','Andres Campo','rocha',1,'2017-10-05 15:57:08','2017-10-05 16:24:50'),(178,0,73,4,1,NULL,89.00,0.00,'2017-10-05 15:57:27.000000',12,1,'','Andres Campo','mario jsñose',1,'2017-10-05 15:57:28','2017-10-05 15:57:28'),(179,1,72,4,1,NULL,89.00,0.00,'2017-10-05 16:21:55.000000',1,1,'','Andres Campo','rocha',1,'2017-10-05 16:21:56','2017-10-05 16:23:53'),(180,0,74,1,NULL,1,0.00,8.00,'2017-10-05 16:46:14.000000',5,0,'Ninguna ','Edwin de respuesto','Mi ',1,'2017-10-05 16:46:18','2017-10-05 16:46:18'),(181,0,75,1,NULL,1,0.00,2.00,'2017-10-05 16:46:57.000000',2,0,'None ','Edwin de respuesto','Mi ',1,'2017-10-05 16:47:02','2017-10-05 16:47:02'),(182,0,76,1,NULL,1,0.00,5.00,'2017-10-05 16:47:40.000000',2,1,'Ninguna ','Edwin de respuesto','Eddy Beio ',1,'2017-10-05 16:47:43','2017-10-05 16:47:43'),(183,0,77,1,NULL,1,0.00,1.00,'2017-10-05 16:50:28.000000',1,1,'','Andres Campo','ma',1,'2017-10-05 16:50:29','2017-10-05 16:50:29'),(184,0,77,1,NULL,1,0.00,2.00,'2017-10-05 16:51:27.000000',3,1,'','Andres Campo','n',1,'2017-10-05 16:51:28','2017-10-05 16:52:23'),(185,0,78,1,NULL,1,0.00,21.00,'2017-10-05 16:57:58.000000',11,1,'etett','','etet',1,'2017-10-05 16:57:59','2017-10-05 16:57:59'),(186,0,79,1,NULL,1,0.00,12.00,'2017-10-05 17:02:45.000000',11,1,'yry','','erete',1,'2017-10-05 17:02:46','2017-10-05 17:02:46'),(187,0,76,1,NULL,1,0.00,2.00,'2017-10-05 17:10:39.000000',2,1,'Gh','','Ni6',1,'2017-10-05 17:10:42','2017-10-05 17:10:42'),(188,0,80,1,NULL,1,0.00,2.00,'2017-10-05 17:11:54.000000',2,1,'Jdkek','','Mi',1,'2017-10-05 17:11:57','2017-10-05 17:11:57'),(189,0,76,1,NULL,1,0.00,2.00,'2017-10-05 17:12:35.000000',2,1,'Kdkek','','Jskswk',1,'2017-10-05 17:12:38','2017-10-05 17:12:38'),(190,0,79,1,NULL,1,0.00,35.56,'2017-10-05 19:34:11.000000',20,1,'','Andres Campo','mari',1,'2017-10-05 19:34:12','2017-10-05 19:34:12'),(191,0,79,1,NULL,1,0.00,50.50,'2017-10-05 19:34:52.000000',20,1,'','Andres Campo','André',1,'2017-10-05 19:34:54','2017-10-05 19:34:54'),(192,1,81,4,1,NULL,89.00,0.00,'2017-10-06 12:17:52.000000',25,1,'café sucio','Andres Campo','Alejandro Carvallo',1,'2017-10-06 13:17:53','2017-10-06 13:17:53'),(193,0,64,4,1,NULL,89.00,0.00,'2017-10-06 12:24:32.000000',65,1,'','Andres Campo','marwin campos',1,'2017-10-06 13:24:33','2017-10-06 13:24:33'),(194,0,82,4,1,NULL,89.00,0.00,'2017-10-06 12:25:07.000000',65,1,'','Andres Campo','cosechador 20',1,'2017-10-06 13:25:07','2017-10-06 13:25:07'),(195,0,64,4,1,NULL,89.00,0.00,'2017-10-06 12:25:25.000000',69,1,'','Andres Campo','marwin campos',1,'2017-10-06 13:25:26','2017-10-06 13:25:26'),(196,0,64,5,1,NULL,89.00,0.00,'2017-10-06 12:25:25.000000',25,1,'','Andres Campo','marwin campos',1,'2017-10-06 13:25:26','2017-10-06 13:25:26'),(197,0,83,1,NULL,1,0.00,2.30,'2017-10-06 12:27:16.000000',69,1,'','Andres Campo','Alejandro',1,'2017-10-06 13:27:17','2017-10-06 13:27:17'),(198,0,84,4,1,NULL,89.00,0.00,'2017-10-09 11:15:37.000000',15,1,'ninguna','','qwertyui',1,'2017-10-09 11:15:38','2017-10-09 11:15:38'),(199,1,85,4,1,NULL,89.00,0.00,'2017-10-10 12:17:43.000000',10,1,'','Yenny1 Fung1','asasasasasasasa',1,'2017-10-10 12:17:53','2017-10-10 12:17:53'),(200,0,86,4,2,NULL,2252.00,0.00,'2017-10-10 12:18:37.000000',10,1,'','Yenny1 Fung1','cosechador 20',1,'2017-10-10 12:18:39','2017-10-10 12:18:39'),(201,0,12,4,1,NULL,89.00,0.00,'2019-09-28 00:00:00.000000',2,1,'texto opcional','nameReceived 11','nameDelivered 11',1,'2017-10-10 15:11:03','2017-10-10 15:11:03'),(202,0,12,4,1,NULL,89.00,0.00,'2019-09-28 00:00:00.000000',2,1,'texto opcional','nameReceived 11','nameDelivered 11',1,'2017-10-10 15:31:17','2017-10-10 15:31:17'),(203,0,87,4,1,NULL,89.00,0.00,'2019-09-29 00:00:00.000000',2,1,'texto opcional','nameReceived 11','nameDelivered 11',1,'2017-10-10 15:32:34','2017-10-10 15:32:34'),(204,0,87,4,1,NULL,89.00,0.00,'2019-09-29 00:00:00.000000',2,1,'texto opcional','nameReceived 11','nameDelivered 11',1,'2017-10-10 15:52:15','2017-10-10 15:52:15'),(205,0,87,4,1,NULL,89.00,0.00,'2019-09-29 10:05:00.000000',2,1,'texto opcional','nameReceived 11','nameDelivered 11',1,'2017-10-10 16:02:45','2017-10-10 16:02:45'),(206,0,88,4,1,NULL,89.00,0.00,'2019-09-30 10:05:00.000000',2,1,'texto opcional','nameReceived 11','nameDelivered 11',1,'2017-10-10 16:04:34','2017-10-10 16:04:34'),(207,0,88,4,1,NULL,89.00,0.00,'2019-09-30 10:05:00.000000',2,1,'texto opcional','nameReceived 11','nameDelivered 11',1,'2017-10-10 16:16:32','2017-10-10 16:16:32'),(208,0,89,4,1,NULL,89.00,0.00,'2019-10-01 10:05:00.000000',2,1,'texto opcional','nameReceived 11','nameDelivered 11',1,'2017-10-10 16:16:55','2017-10-10 16:16:55'),(209,0,89,4,1,NULL,89.00,0.00,'2019-10-01 10:50:00.000000',2,1,'texto opcional','nameReceived 11','nameDelivered 11',1,'2017-10-10 16:17:11','2017-10-10 16:17:11'),(210,0,90,4,2,NULL,2252.00,0.00,'2017-10-10 17:01:32.000000',10,1,'ninguna','','qwertyui',1,'2017-10-10 17:01:33','2017-10-10 17:01:33'),(211,0,90,5,2,NULL,2252.00,0.00,'2017-10-10 17:01:32.000000',12,1,'ninguna','','qwertyui',1,'2017-10-10 17:01:33','2017-10-10 17:01:33'),(212,0,91,1,NULL,2,0.00,10.00,'2017-10-10 17:18:52.000000',100,1,'ninguna','','XX',1,'2017-10-10 17:18:53','2017-10-10 17:18:53'),(213,1,92,1,NULL,1,0.00,1.00,'2017-10-10 22:14:34.000000',1,1,'sdfsd','Edwin de respuesto','sdfd',1,'2017-10-10 22:14:35','2017-10-10 22:14:35'),(214,0,93,4,1,NULL,89.00,0.00,'2017-10-11 00:16:44.000000',20,1,'Fino ','Edwin de respuesto','eudin cosechadorx1k',1,'2017-10-11 00:16:46','2017-10-11 00:16:46'),(215,0,93,5,1,NULL,89.00,0.00,'2017-10-11 00:16:44.000000',30,1,'Fino ','Edwin de respuesto','eudin cosechadorx1k',1,'2017-10-11 00:16:46','2017-10-11 00:16:46'),(216,0,94,1,NULL,1,0.00,28.00,'2017-10-11 10:04:46.000000',20,1,'','Andres Campo','ma',1,'2017-10-11 10:04:47','2017-10-11 10:04:47'),(217,0,95,1,NULL,1,0.00,25.69,'2017-10-11 10:09:54.000000',28,1,'','Andres Campo','André',1,'2017-10-11 10:09:55','2017-10-11 10:09:55'),(218,0,96,4,23,NULL,250.00,0.00,'2017-10-11 10:41:34.000000',50,1,'','Andres Campo','cosechador 113',1,'2017-10-11 10:41:34','2017-10-11 10:41:34'),(219,0,97,1,NULL,1,0.00,50.00,'2017-10-11 10:41:58.000000',20,1,'','Andres Campo','ma',1,'2017-10-11 10:41:58','2017-10-11 10:41:58'),(220,0,98,4,23,NULL,250.00,0.00,'2017-10-13 09:26:45.000000',36,1,'','Alejandro  Carvallo','Alejandro Carvallo',1,'2017-10-13 10:26:46','2017-10-13 10:26:46'),(221,0,98,5,23,NULL,250.00,0.00,'2017-10-13 09:26:45.000000',43,1,'','Alejandro  Carvallo','Alejandro Carvallo',1,'2017-10-13 10:26:46','2017-10-13 10:26:46'),(222,0,99,1,NULL,2,0.00,1.60,'2017-10-13 09:31:14.000000',36,1,'nada','Alejandro  Carvallo','peptlito',1,'2017-10-13 10:31:16','2017-10-13 10:31:16'),(223,0,98,4,26,NULL,58.00,0.00,'2017-10-13 11:11:23.000000',3764,1,'sss\nssss','Orlando Carvallo','Alejandro Carvallo',1,'2017-10-13 11:11:23','2017-10-13 11:11:23'),(224,0,98,5,26,NULL,58.00,0.00,'2017-10-13 11:11:23.000000',845,1,'sss\nssss','Orlando Carvallo','Alejandro Carvallo',1,'2017-10-13 11:11:23','2017-10-13 11:11:23'),(225,0,100,2,NULL,1,0.00,88.00,'2017-10-13 11:13:29.000000',888,1,'ddddd','Orlando Carvallo','dddd',1,'2017-10-13 11:13:30','2017-10-13 11:13:30'),(226,0,101,4,26,NULL,58.00,0.00,'2017-10-13 17:22:19.000000',500,1,'cafw','Andres Campo','Alejandro',1,'2017-10-13 17:22:22','2017-10-13 17:22:22'),(227,0,102,4,24,NULL,500.00,0.00,'2017-10-15 14:56:49.000000',23,1,'','Alejandro  Carvallo','Alejandro',1,'2017-10-15 15:56:50','2017-10-15 15:56:50'),(228,0,102,5,24,NULL,500.00,0.00,'2017-10-15 14:56:49.000000',56,1,'','Alejandro  Carvallo','Alejandro',1,'2017-10-15 15:56:50','2017-10-15 15:56:50'),(229,0,103,4,24,NULL,500.00,0.00,'2017-10-16 13:40:15.000000',12,1,'212','Edwin de respuesto','Alejandro',1,'2017-10-16 13:40:16','2017-10-16 13:40:16'),(230,0,103,5,24,NULL,500.00,0.00,'2017-10-16 13:40:15.000000',12,1,'212','Edwin de respuesto','Alejandro',1,'2017-10-16 13:40:16','2017-10-16 13:40:16'),(231,0,104,4,24,NULL,500.00,0.00,'2017-10-16 14:30:55.000000',60,1,'','Andres Campo','cosechador 113Ba',1,'2017-10-16 14:30:57','2017-10-16 14:34:00'),(232,0,105,4,26,NULL,58.00,0.00,'2017-10-18 13:38:07.000000',23,1,'','Andres Campo','Alejandro',1,'2017-10-18 14:38:08','2017-10-18 14:38:08'),(233,0,106,4,24,NULL,500.00,0.00,'2017-10-21 19:48:35.000000',212121,1,'oiuytrew','Edwin de respuesto','jejejeps',1,'2017-10-21 23:17:29','2017-10-21 23:17:29'),(234,0,106,5,24,NULL,500.00,0.00,'2017-10-21 19:48:35.000000',12121,1,'oiuytrew','Edwin de respuesto','jejejeps',1,'2017-10-21 23:17:29','2017-10-21 23:17:29'),(235,0,107,4,24,NULL,500.00,0.00,'2017-10-22 11:29:32.000000',1213,1,'rewrwr','Edwin de respuesto','qwertyui',1,'2017-10-22 11:29:36','2017-10-22 11:29:36'),(236,0,107,5,24,NULL,500.00,0.00,'2017-10-22 11:29:32.000000',121,1,'rewrwr','Edwin de respuesto','qwertyui',1,'2017-10-22 11:29:36','2017-10-22 11:29:36'),(237,0,108,4,24,NULL,500.00,0.00,'2017-10-23 00:15:56.000000',11,1,'sds','Edwin de respuesto','eudin cosechadorx1k',1,'2017-10-23 00:15:57','2017-10-23 00:15:57'),(238,0,108,5,24,NULL,500.00,0.00,'2017-10-23 00:15:56.000000',10,1,'sds','Edwin de respuesto','eudin cosechadorx1k',1,'2017-10-23 00:15:57','2017-10-23 00:15:57'),(239,0,108,4,26,NULL,58.00,0.00,'2017-10-23 00:20:55.000000',13,1,'nada','Edwin de respuesto','eudin cosechadorx1k',1,'2017-10-23 00:20:56','2017-10-23 00:20:56'),(240,0,108,5,26,NULL,58.00,0.00,'2017-10-23 00:20:55.000000',14,1,'nada','Edwin de respuesto','eudin cosechadorx1k',1,'2017-10-23 00:20:56','2017-10-23 00:20:56'),(241,0,109,4,24,NULL,500.00,0.00,'2017-10-24 01:03:06.000000',15,1,'q','Edwin de respuesto','rtret',1,'2017-10-24 01:03:09','2017-10-24 01:03:09'),(242,0,109,5,24,NULL,500.00,0.00,'2017-10-24 01:03:06.000000',16,1,'q','Edwin de respuesto','rtret',1,'2017-10-24 01:03:09','2017-10-24 01:03:09'),(243,0,110,4,24,NULL,500.00,0.00,'2017-10-25 01:21:59.000000',23,1,'23','Edwin de respuesto','Alejandro Carvallo',1,'2017-10-25 01:22:00','2017-10-25 01:22:00'),(244,0,110,5,24,NULL,500.00,0.00,'2017-10-25 01:21:59.000000',23,1,'23','Edwin de respuesto','Alejandro Carvallo',1,'2017-10-25 01:22:00','2017-10-25 01:22:00'),(245,0,111,4,24,NULL,500.00,0.00,'2017-10-25 22:27:39.000000',11,1,'q','Edwin de respuesto','Alejandro',1,'2017-10-25 22:27:44','2017-10-25 22:27:44'),(246,0,111,5,24,NULL,500.00,0.00,'2017-10-25 22:27:39.000000',11,1,'q','Edwin de respuesto','Alejandro',1,'2017-10-25 22:27:44','2017-10-25 22:27:44'),(247,0,112,4,24,NULL,500.00,0.00,'2017-10-26 00:17:56.000000',15,1,'q','Edwin de respuesto','cosechador 113Ba',1,'2017-10-26 00:18:06','2017-10-26 00:18:06'),(248,0,112,5,24,NULL,500.00,0.00,'2017-10-26 00:17:56.000000',115,1,'q','Edwin de respuesto','cosechador 113Ba',1,'2017-10-26 00:18:06','2017-10-26 00:18:06'),(249,0,113,4,24,NULL,500.00,0.00,'2017-10-27 23:56:51.000000',1234,1,'q','Edwin de respuesto','hghh',1,'2017-10-27 23:56:52','2017-10-27 23:56:52'),(250,0,113,5,24,NULL,500.00,0.00,'2017-10-27 23:56:51.000000',1234,1,'q','Edwin de respuesto','hghh',1,'2017-10-27 23:56:53','2017-10-27 23:56:53'),(251,0,114,4,24,NULL,500.00,0.00,'2017-10-30 14:57:47.000000',30,1,'my obs','Edwin de respuesto','Alejandro Carvallo',1,'2017-10-30 14:57:48','2017-10-30 15:32:05'),(252,0,114,5,24,NULL,500.00,0.00,'2017-10-30 14:57:47.000000',30,1,'my obs','Edwin de respuesto','Alejandro Carvallo',1,'2017-10-30 14:57:48','2017-10-30 15:32:05'),(253,0,115,4,24,NULL,500.00,0.00,'2017-10-30 16:23:37.000000',60,1,'sien?','Edwin de respuesto','eudin cosechadorx1k',1,'2017-10-30 16:23:38','2017-10-30 16:23:38'),(254,0,115,5,24,NULL,500.00,0.00,'2017-10-30 16:23:37.000000',40,1,'sien?','Edwin de respuesto','eudin cosechadorx1k',1,'2017-10-30 16:23:39','2017-10-30 16:23:39'),(255,0,116,4,26,NULL,58.00,0.00,'2017-10-31 10:40:58.000000',11,1,'qw','Edwin de respuesto','Alejandro',1,'2017-10-31 10:41:16','2017-10-31 10:41:16'),(256,0,116,5,26,NULL,58.00,0.00,'2017-10-31 10:40:58.000000',10,1,'qw','Edwin de respuesto','Alejandro',1,'2017-10-31 10:41:17','2017-10-31 10:41:17');



INSERT INTO `invoicesdetails_purities` VALUES (1,0,1,85,200,0,NULL,1,'2017-09-18 16:01:20','2017-09-18 16:03:08'),(2,0,1,25,0,-20,32,1,'2017-09-25 18:05:52','2017-09-25 18:05:52'),(3,0,2,25,0,-45,33,1,'2017-09-25 18:06:51','2017-09-25 18:06:51'),(4,0,1,25,0,-16,34,1,'2017-09-25 18:16:03','2017-09-25 18:16:03'),(5,0,2,25,0,-36,35,1,'2017-09-25 18:16:29','2017-09-25 18:16:29'),(6,0,2,10,0,0,59,1,'2017-09-28 15:27:24','2017-09-28 15:27:28'),(7,0,2,10,0,0,61,1,'2017-09-28 19:09:10','2017-09-28 19:09:10'),(8,0,2,10,0,0,62,1,'2017-09-28 19:12:59','2017-09-28 19:12:59'),(9,0,2,10,0,0,63,1,'2017-09-28 19:13:07','2017-09-28 19:13:07'),(10,0,2,10,0,0,64,1,'2017-09-28 19:13:36','2017-09-28 19:13:36'),(11,0,2,10,0,0,65,1,'2017-09-28 19:14:27','2017-09-28 19:14:27'),(12,0,2,10,0,0,66,1,'2017-09-28 23:59:47','2017-09-28 23:59:47'),(13,0,2,10,0,0,67,1,'2017-09-29 00:05:49','2017-09-29 00:05:49'),(14,0,1,3,0,0,69,1,'2017-09-29 00:24:23','2017-09-29 00:24:23'),(15,0,2,23,0,0,69,1,'2017-09-29 00:35:40','2017-10-02 15:39:42'),(16,0,3,3,0,0,69,1,'2017-09-29 00:40:20','2017-09-29 00:40:20'),(17,0,1,5,0,0,71,1,'2017-09-29 01:11:46','2017-09-29 01:11:46'),(18,0,1,5,0,0,72,1,'2017-09-29 01:16:54','2017-09-29 01:16:55'),(19,0,2,10,0,0,90,1,'2017-10-03 09:56:46','2017-10-03 09:56:51'),(20,0,2,10,0,0,91,1,'2017-10-03 11:20:13','2017-10-03 11:20:13'),(21,0,2,10,0,0,92,1,'2017-10-03 11:21:32','2017-10-03 11:21:32'),(22,0,1,10,0,0,93,1,'2017-10-03 11:31:06','2017-10-03 11:31:06'),(23,0,1,2,0,0,94,1,'2017-10-03 11:33:00','2017-10-03 11:33:00'),(24,0,1,15,0,0,95,1,'2017-10-03 11:38:09','2017-10-03 11:38:09'),(25,0,2,45,0,0,96,1,'2017-10-03 13:59:22','2017-10-03 15:52:19'),(26,0,2,10,0,0,97,1,'2017-10-03 15:16:38','2017-10-03 15:17:20'),(27,0,1,3001,0,0,98,1,'2017-10-03 15:26:49','2017-10-03 15:32:46'),(28,0,2,1001,0,0,98,1,'2017-10-03 15:27:04','2017-10-03 15:32:46'),(29,0,1,25,0,0,99,1,'2017-10-03 15:43:07','2017-10-03 15:43:07'),(30,0,2,10,0,0,112,1,'2017-10-03 17:27:10','2017-10-03 17:27:10'),(31,0,1,15,0,0,112,1,'2017-10-03 17:27:10','2017-10-03 17:27:10'),(32,0,1,0,0,0,113,1,'2017-10-03 17:28:38','2017-10-03 17:28:38'),(33,0,2,1,0,0,115,1,'2017-10-03 17:29:53','2017-10-03 17:29:53'),(34,0,1,25,0,0,96,1,'2017-10-03 18:08:04','2017-10-03 18:08:04'),(35,0,2,1,0,0,118,1,'2017-10-03 22:10:18','2017-10-03 22:10:18'),(36,0,1,1,0,0,118,1,'2017-10-03 22:10:18','2017-10-03 22:10:18'),(37,0,2,2,0,0,119,1,'2017-10-03 22:14:19','2017-10-03 22:14:19'),(38,0,1,2,0,0,119,1,'2017-10-03 22:14:19','2017-10-03 22:14:19'),(39,0,2,1,0,0,120,1,'2017-10-03 22:18:40','2017-10-03 22:18:40'),(40,0,1,1,0,0,120,1,'2017-10-03 22:18:40','2017-10-03 22:18:40'),(41,0,2,2,0,0,121,1,'2017-10-03 22:27:16','2017-10-03 22:27:16'),(42,0,1,2,0,0,121,1,'2017-10-03 22:27:16','2017-10-03 22:27:16'),(43,0,2,2,0,0,122,1,'2017-10-03 22:28:00','2017-10-03 22:28:00'),(44,0,1,2,0,0,122,1,'2017-10-03 22:28:00','2017-10-03 22:28:00'),(45,0,2,5,0,0,123,1,'2017-10-03 22:29:30','2017-10-03 22:29:30'),(46,0,1,5,0,0,123,1,'2017-10-03 22:29:31','2017-10-03 22:29:31'),(47,0,2,4,0,0,124,1,'2017-10-03 22:30:04','2017-10-03 22:30:04'),(48,0,1,5,0,0,124,1,'2017-10-03 22:30:04','2017-10-03 22:30:04'),(49,0,2,3,0,0,125,1,'2017-10-03 22:31:18','2017-10-03 22:31:18'),(50,0,1,2,0,0,125,1,'2017-10-03 22:31:18','2017-10-03 22:31:18'),(51,0,2,2,0,0,126,1,'2017-10-03 22:42:28','2017-10-03 22:42:28'),(52,0,1,2,0,0,126,1,'2017-10-03 22:42:28','2017-10-03 22:42:28'),(53,0,2,10,0,0,143,1,'2017-10-04 16:41:15','2017-10-04 16:41:15'),(54,0,2,10,0,0,144,1,'2017-10-04 16:46:51','2017-10-04 16:46:51'),(55,0,2,10,0,0,145,1,'2017-10-04 16:48:17','2017-10-04 16:48:17'),(56,0,2,5,0,0,148,1,'2017-10-04 17:04:19','2017-10-04 17:04:19'),(57,0,2,100,0,0,162,1,'2017-10-05 15:18:41','2017-10-05 15:54:36'),(58,0,2,1,0,0,167,1,'2017-10-05 15:22:36','2017-10-05 15:22:36'),(59,0,1,300,0,0,168,1,'2017-10-05 15:47:25','2017-10-05 15:47:25'),(60,0,2,100,0,0,168,1,'2017-10-05 15:47:26','2017-10-05 15:47:26'),(61,0,1,300,0,0,161,1,'2017-10-05 15:50:38','2017-10-05 15:50:38'),(62,0,2,100,0,0,161,1,'2017-10-05 15:50:39','2017-10-05 15:50:39'),(63,0,1,300,0,0,162,1,'2017-10-05 15:54:35','2017-10-05 15:54:35'),(64,0,2,8,0,0,180,1,'2017-10-05 16:46:18','2017-10-05 16:46:18'),(65,0,1,6,0,0,180,1,'2017-10-05 16:46:18','2017-10-05 16:46:18'),(66,0,2,2,0,0,181,1,'2017-10-05 16:47:02','2017-10-05 16:47:02'),(67,0,1,6,0,0,181,1,'2017-10-05 16:47:02','2017-10-05 16:47:02'),(68,0,2,6,0,0,182,1,'2017-10-05 16:47:43','2017-10-05 16:47:43'),(69,0,1,5,0,0,182,1,'2017-10-05 16:47:43','2017-10-05 16:47:43'),(70,0,2,10,0,0,183,1,'2017-10-05 16:50:30','2017-10-05 16:50:30'),(71,0,2,10,0,0,184,1,'2017-10-05 16:51:28','2017-10-05 16:51:28'),(72,0,2,3,0,0,185,1,'2017-10-05 16:57:59','2017-10-05 16:57:59'),(73,0,1,4,0,0,185,1,'2017-10-05 16:57:59','2017-10-05 16:57:59'),(74,0,2,2,0,0,186,1,'2017-10-05 17:02:46','2017-10-05 17:02:46'),(75,0,1,21,0,0,186,1,'2017-10-05 17:02:46','2017-10-05 17:02:46'),(76,0,2,5,0,0,187,1,'2017-10-05 17:10:42','2017-10-05 17:10:42'),(77,0,1,5,0,0,187,1,'2017-10-05 17:10:42','2017-10-05 17:10:42'),(78,0,2,5,0,0,188,1,'2017-10-05 17:11:57','2017-10-05 17:11:57'),(79,0,1,6,0,0,188,1,'2017-10-05 17:11:57','2017-10-05 17:11:57'),(80,0,2,5,0,0,189,1,'2017-10-05 17:12:38','2017-10-05 17:12:38'),(81,0,1,4,0,0,189,1,'2017-10-05 17:12:38','2017-10-05 17:12:38'),(82,0,2,15,0,0,191,1,'2017-10-05 19:34:54','2017-10-05 19:34:54'),(83,0,2,1,0,0,197,1,'2017-10-06 13:27:17','2017-10-06 13:27:17'),(84,0,1,0,0,0,197,1,'2017-10-06 13:27:17','2017-10-06 13:27:17'),(85,0,2,1,0,0,212,1,'2017-10-10 17:18:54','2017-10-10 17:18:54'),(86,0,1,2,0,0,212,1,'2017-10-10 17:18:54','2017-10-10 17:18:54'),(87,0,2,1,0,0,213,1,'2017-10-10 22:14:35','2017-10-10 22:14:35'),(88,0,1,1,0,0,213,1,'2017-10-10 22:14:35','2017-10-10 22:14:35'),(89,0,2,42,0,0,217,1,'2017-10-11 10:09:55','2017-10-11 10:09:55'),(90,0,2,1,0,0,222,1,'2017-10-13 10:31:16','2017-10-13 10:31:16'),(91,0,2,555,0,0,225,1,'2017-10-13 11:13:30','2017-10-13 11:13:30'),(92,0,1,55,0,0,225,1,'2017-10-13 11:13:30','2017-10-13 11:13:30');

--# --- !Downs

SET FOREIGN_KEY_CHECKS = 0;

truncate farms;

truncate provider_type;

truncate stores;

truncate units;

truncate purities;

truncate lots;

truncate providers;

truncate invoices;

truncate item_types;

truncate invoice_details;

truncate invoicesdetails_purities;

SET FOREIGN_KEY_CHECKS = 1;