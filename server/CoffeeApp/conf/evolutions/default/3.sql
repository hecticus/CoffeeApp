INSERT INTO `auth_user` ( id , username, email, password, deleted) VALUES
(100,'fmadriz','fmadriz@grupoeleta.com','12345', 0),
(101,'marwin','marwin.campos@hecticus.com','12345', 0),
(102,'cubilla','lcubilla@cafedeeleta.com','prueba', 0),
(103,'baldia','tdeobaldia@cafedeeleta.com','thania$2602', 0),
(104,'cervantes','mcervantes@cafedeeleta.com','miguel$2602', 0),
(105,'rmadriz','rmadriz@grupoeleta.com','bourbon', 0);

/*(100,'fmadriz','rmadriz@grupoeleta.com','12345', 0),
(101,'marwin','marwin.campos@hecticus.com','12345', 0),
(102,'cubilla','lcubilla@cafedeeleta.com','prueba', 0),
(103,'baldia','tdeobaldia@cafedeeleta.com','thania$2602', 0),
(104,'cervantes','mcervantes@cafedeeleta.com','miguel$2602', 0),
(105,'rmadriz','rmadriz@grupoeleta.com','bourbon', 0);*/

/*INSERT INTO `user` ( id, auth_user_id, first_name, last_name, address, phone, deleted) VALUES
(100,100,'Madriz','Eleta','Panama','1234567', 0),
(101,101,'Marwin','Campos','Caracas','2515151', 0);
(102,102,'Cubilla','Eleta','Caracas','2515151', 0);
(103,103,'Marwin','Campos','Caracas','2515151', 0);
(104,104,'Cervantes','Eleta','Caracas','2515151', 0);
(105,105,'rmadriz','Eleta','Caracas','2515151', 0);*/

INSERT INTO `auth_user_auth_group` ( auth_user_id, auth_group_id) VALUES
(100,7),
(100,8),
(100,9),
(101,7),
(101,8),
(101,9),
(102,7),
(102,8),
(102,9),
(103,7),
(103,8),
(103,9),
(104,7),
(104,8),
(104,9),
(105,7),
(105,8),
(105,9);

# --- !Ups

INSERT INTO `auth_permission` ( id, name) VALUES
(169, 'controllers.InvoiceDetails.findAll'),
(170, 'controllers.InvoiceDetails.findAllSearch'),
(171, 'controllers.InvoiceDetails.findById'),
(172, 'controllers.InvoiceDetails.findAllByIdInvoice'),
(173, 'controllers.InvoiceDetails.preCreate'),
(174, 'controllers.InvoiceDetails.create'),
(175, 'controllers.InvoiceDetails.update'),
(176, 'controllers.InvoiceDetails.delete'),
(177, 'controllers.InvoiceDetails.deleteAllByIdInvoiceAndDate'),
(178, 'controllers.ItemTypes.findAll'),
(179, 'controllers.ItemTypes.findAllSearch'),
(180, 'controllers.ItemTypes.preCreate'),
(181, 'controllers.ItemTypes.findById'),
(182, 'controllers.ItemTypes.getByProviderTypeId'),
(183, 'controllers.ItemTypes.getByNameItemType'),
(184, 'controllers.Units.findById'),
(185, 'controllers.Units.findAll'),
(186, 'controllers.Units.create'),
(187, 'controllers.Units.update'),
(188, 'controllers.Units.delete'),
(189, 'controllers.ProviderTypes.findAllAll'),
(190, 'controllers.ProviderTypes.findById'),
(191, 'controllers.ProviderTypes.getProviderTypesByName'),
(192, 'controllers.Purities.findAll'),
(193, 'controllers.Purities.findAllSearch'),
(194, 'controllers.Purities.findById'),
(195, 'controllers.Purities.getByNamePurity'),
(196, 'controllers.Purities.getByStatusPurity'),
(197, 'controllers.Invoices.findAll'),
(198, 'controllers.Invoices.findAllSearch'),
(199, 'controllers.Invoices.getByDateByTypeProvider'),
(200, 'controllers.Invoices.getByDateByProviderId'),
(201, 'controllers.Invoices.getOpenByProviderId'),
(202, 'controllers.Farms.findById'),
(203, 'controllers.Farms.findAll'),
(204, 'controllers.Farms.findAllSearch'),
(205, 'controllers.Stores.findAll'),
(206, 'Controllers.Stores.findAllSearch'),
(207, 'controllers.Stores.findById'),
(208, 'controllers.Stores.getByStatusStore'),
(209, 'controllers.InvoiceDetailPurities.findAll'),
(210, 'controllers.InvoiceDetailPurities.findById'),
(211, 'controllers.Lots.findAll'),
(212, 'controllers.Lots.findAllSearch'),
(213, 'controllers.Lots.preCreate'),
(214, 'controllers.Lots.create'),
(215, 'controllers.Lots.update'),
(216, 'controllers.Lots.delete'),
(217, 'controllers.Lots.getByNameLot'),
(218, 'controllers.Lots.getByStatusLot'),
(219, 'controllers.Lots.getByIdFarm'),
(220, 'controllers.Lots.deletes'),
(221, 'controllers.InvoiceDetailPurities.create'),
(222, 'controllers.InvoiceDetailPurities.update'),
(223, 'controllers.InvoiceDetailPurities.delete'),
(224, 'controllers.Providers.findById'),
(225, 'controllers.Providers.findAll'),
(226, 'controllers.Providers.findAllSearch'),
(227, 'controllers.Providers.getByIdentificationDoc'),
(228, 'controllers.Providers.getProvidersByName'),
(229, 'controllers.Providers.getByTypeProvider'),
(230, 'controllers.Providers.getByNameDocByTypeProvider'),
(231, 'controllers.Providers.uploadPhotoProvider'),
(232, 'controllers.Providers.create'),
(233, 'controllers.Providers.update'),
(234, 'controllers.Providers.delete'),
(235, 'controllers.Providers.deletes'),
(236, 'controllers.Purities.preCreate'),
(237, 'controllers.Purities.create'),
(238, 'controllers.Purities.update'),
(239, 'controllers.Purities.delete'),
(240, 'controllers.Invoices.createReceipt'),
(241, 'controllers.Invoices.create'),
(242, 'controllers.Invoices.buyHarvestsAndCoffe'),
(243, 'controllers.Invoices.updateBuyHarvestsAndCoffe'),
(244, 'controllers.Invoices.update'),
(245, 'controllers.Invoices.delete'),
(246, 'controllers.ItemTypes.create'),
(247, 'controllers.ItemTypes.update'),
(248, 'controllers.ItemTypes.delete'),
(249, 'controllers.Stores.preCreate'),
(250, 'controllers.Stores.create'),
(251, 'controllers.Stores.update'),
(252, 'controllers.Stores.delete');

INSERT INTO `auth_role` ( id, name) VALUES
(45,'invoiceDetail_u'),
(46,'itemType_r'),
(47,'unit_u'),
(48,'providerType_u'),
(49,'purity_r'),
(50,'invoice_r'),
(51,'farm_u'),
(52,'providerType_r'),
(53,'invoiceDetail_r'),
(54,'store_r'),
(55,'invoiceDetailPurity_r'),
(56,'farm_r'),
(57,'lot_u'),
(58,'invoiceDetailPurity_u'),
(59,'provider_u'),
(60,'lot_r'),
(61,'provider_r'),
(62,'purity_u'),
(63,'invoice_u'),
(64,'itemType_u'),
(65,'store_u'),
(66,'unit_r');

INSERT INTO `auth_group` ( id, name) VALUES
(7, 'client'),
(8, 'super'),
(9, 'user');

INSERT INTO `auth_client_credential` (id, client_id, name) VALUES
(1,'web_site',    'web site'),
(2,'android_app', 'android app');

INSERT INTO `auth_user` ( id , email, password, deleted) VALUES
(100, 'rmadriz@grupoeleta.com','12345', 0),
(101, 'marwin.campos@hecticus.com','12345', 0),
(102, 'lcubilla@cafedeeleta.com','prueba', 0),
(103, 'tdeobaldia@cafedeeleta.com','thania$2602', 0),
(104, 'mcervantes@cafedeeleta.com','miguel$2602', 0),
(105, 'rrmadriz@grupoeleta.com','bourbon', 0);

INSERT INTO `user` ( id, auth_user_id, first_name, last_name, address, phone, deleted) VALUES
(100,100,'Madriz','Eleta','Panama','1234567', 0),
(101,101,'Marwin','Campos','Caracas','2515151', 0),
(102,102,'Cubilla','Eleta','Caracas','2515151', 0),
(103,103,'baldia','Campos','Caracas','2515151', 0),
(104,104,'Cervantes','Eleta','Caracas','2515151', 0),
(105,105,'rmadriz','Eleta','Caracas','2515151', 0);



INSERT INTO `auth_permission_auth_role` (`auth_permission_id`,`auth_role_id`) VALUES
(169,45),
(169,53),
(170,45),
(170,53),
(171,45),
(171,53),
(172,45),
(172,53),
(173,45),
(174,45),
(175,45),
(176,45),
(177,45),
(178,46),
(178,64),
(179,46),
(179,64),
(180,46),
(180,64),
(181,46),
(181,64),
(182,46),
(182,64),
(183,46),
(183,64),
(184,47),
(184,66),
(185,47),
(185,66),
(186,47),
(187,47),
(188,47),
(189,48),
(189,52),
(190,48),
(190,52),
(191,48),
(191,52),
(192,49),
(192,62),
(193,49),
(193,62),
(194,49),
(194,62),
(195,49),
(195,62),
(196,49),
(196,62),
(197,50),
(197,63),
(198,50),
(198,63),
(199,50),
(199,63),
(200,50),
(200,63),
(201,50),
(201,63),
(202,51),
(202,56),
(203,51),
(203,56),
(204,51),
(204,56),
(205,54),
(205,65),
(206,54),
(206,65),
(207,54),
(207,65),
(208,54),
(208,65),
(209,55),
(209,58),
(210,55),
(210,58),
(211,57),
(211,60),
(212,57),
(212,60),
(213,57),
(214,57),
(215,57),
(216,57),
(217,57),
(217,60),
(218,57),
(218,60),
(219,57),
(219,60),
(220,57),
(220,60),
(221,58),
(222,58),
(223,58),
(224,59),
(224,61),
(225,59),
(225,61),
(226,59),
(226,61),
(227,59),
(227,61),
(228,59),
(228,61),
(229,59),
(229,61),
(230,59),
(230,61),
(231,59),
(231,61),
(232,59),
(233,59),
(234,59),
(235,59),
(236,62),
(237,62),
(238,62),
(239,62),
(240,63),
(241,63),
(242,63),
(243,63),
(244,63),
(245,63),
(246,64),
(247,64),
(248,64),
(249,65),
(250,65),
(251,65),
(252,65);


INSERT INTO `auth_user_auth_role` ( auth_user_id, auth_role_id) VALUES
(100, 45),
(100, 46),
(100, 47),
(100, 48),
(100, 49),
(100, 50),
(100, 51),
(100, 52),
(100, 53),
(100, 54),
(100, 55),
(100, 56),
(100, 57),
(100, 58),
(100, 59),
(100, 60),
(100, 61),
(100, 62),
(100, 63),
(100, 64),
(100, 65),
(100, 66),
(101, 45),
(101, 46),
(101, 47),
(101, 48),
(101, 49),
(101, 50),
(101, 51),
(101, 52),
(101, 53),
(101, 54),
(101, 55),
(101, 56),
(101, 57),
(101, 58),
(101, 59),
(101, 60),
(101, 61),
(101, 62),
(101, 63),
(101, 64),
(101, 65),
(102, 45),
(102, 46),
(102, 47),
(102, 48),
(102, 49),
(102, 50),
(102, 51),
(102, 52),
(102, 53),
(102, 54),
(102, 55),
(102, 56),
(102, 57),
(102, 58),
(102, 59),
(102, 60),
(102, 61),
(102, 62),
(102, 63),
(102, 64),
(102, 65),
(102, 66),
(103, 45),
(103, 46),
(103, 47),
(103, 48),
(103, 49),
(103, 50),
(103, 51),
(103, 52),
(103, 53),
(103, 54),
(103, 55),
(103, 56),
(103, 57),
(103, 58),
(103, 59),
(103, 60),
(103, 61),
(103, 62),
(103, 63),
(103, 64),
(103, 65),
(103, 66),
(104, 45),
(104, 46),
(104, 47),
(104, 48),
(104, 49),
(104, 50),
(104, 51),
(104, 52),
(104, 53),
(104, 54),
(104, 55),
(104, 56),
(104, 57),
(104, 58),
(104, 59),
(104, 60),
(104, 61),
(104, 62),
(104, 63),
(104, 64),
(104, 65),
(104, 66),
(105, 45),
(105, 46),
(105, 47),
(105, 48),
(105, 49),
(105, 50),
(105, 51),
(105, 52),
(105, 53),
(105, 54),
(105, 55),
(105, 56),
(105, 57),
(105, 58),
(105, 59),
(105, 60),
(105, 61),
(105, 62),
(105, 63),
(105, 64),
(105, 65),
(105, 66);



INSERT INTO `auth_role_auth_group` ( auth_role_id, auth_group_id) VALUES
(45,7),
(45,8),
(46,9),
(47,8),
(48,8),
(49,7),
(49,9),
(50,7),
(50,9),
(51,7),
(51,8),
(52,7),
(52,9),
(53,9),
(54,7),
(54,9),
(55,9),
(56,9),
(57,8),
(58,7),
(58,8),
(59,8),
(60,7),
(60,9),
(61,7),
(61,9),
(62,8),
(63,8),
(64,7),
(64,8),
(65,8),
(66,7),
(66,9);

# --- !Downs

SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE `auth_client_credential`;

TRUNCATE `auth_permission`;

TRUNCATE `auth_user`;

TRUNCATE `user`;

TRUNCATE `auth_role`;

TRUNCATE `auth_group`;

TRUNCATE `auth_permission_auth_role`;

TRUNCATE `auth_role_auth_group`;

TRUNCATE `auth_client_credential`;

-- TRUNCATE `auth_user_auth_group`;

TRUNCATE `auth_user_auth_role`;

SET FOREIGN_KEY_CHECKS = 1;