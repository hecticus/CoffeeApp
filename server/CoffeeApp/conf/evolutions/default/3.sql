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
(189, 'controllers.ProviderTypes.findAllByAll'),
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
(203, 'controllers.Farms.findByAll'),
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
(226, 'controller.Providers.findAllSearch'),
(227, 'controllers.Providers.getByIdentificationDoc'),
(228, 'controllers.Providers.getProvidersByName'),
(229, 'controllers.Providers.getByTypeProvider'),
(230, 'controllers.Providers.getByNameDocByTypeProvider'),
(231, 'controllers.Providers.uploadPhotoProvider'),
(232, 'ccontrollers.Providers.create'),
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

INSERT INTO `auth_user` ( id , username, email, password,  deleted) VALUES
(100,'sm21','shamuel21@gmail.com','123456', 0);

INSERT INTO `user` ( id, auth_user_id, first_name, last_name, address, phone, status_delete) VALUES
(100,100,'shamuel','manrrique','charallave','0141412514', 0);

INSERT INTO `auth_user_auth_group` ( auth_user_id, auth_group_id) VALUES
(100,7),
(100,8),
(100,9);

INSERT INTO `auth_user_auth_role` ( auth_user_id, auth_role_id) VALUES
(100,56),
(100,51);

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

TRUNCATE auth_client_credential;

TRUNCATE auth_permission;

TRUNCATE auth_user;

TRUNCATE auth_role;

TRUNCATE auth_group;

TRUNCATE auth_permission_auth_role;

TRUNCATE auth_role_auth_group;

TRUNCATE auth_client_credential;

TRUNCATE auth_user_auth_group;

TRUNCATE auth_user_auth_role;


SET FOREIGN_KEY_CHECKS = 1;