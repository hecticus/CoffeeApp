# --- !Ups

INSERT INTO `provider_type` (`id_providertype`,`status_delete`,`name_providertype`)  VALUES
(1, 0, 'Vendedor'),
(2, 0, 'Cosechador'),
(3, 1, 'prueba');


INSERT INTO `providers` (id_provider, identificationdoc_provider, fullname_provider, address_provider, phonenumber_provider,
                         email_provider, provider_type_id_provider_type, contactname_provider)    VALUES
(1130, '42424'	           ,  'Cosechador Tes'      ,   'wweq'	, 332432	  ,'DSDSD@com.com'	  ,	2,	'Cosechador Test'),
(1131, '12345-4-2424 DV 24',	'Proveedor Te'	      ,   'Here'	, 52454547	,'pt@test.com'	    ,	1,	'None'),
(1132, '42343-2-4234 DV 32',	'Proveedor altern'    ,   'dsfdsf', 3424	    ,'ddfd@sds.com'	    ,	1,	'dfsdfsdf'),
(1133, '67567-5-7567 DV 56',	'Proveedor '	        ,   'dfsdf'	, 432	      ,'32432@as.com'	    ,	1,	'sdsd'),
(1134, '12121-2-1212 DV 12',	'PROV'                ,   '342343', 234234	  ,'32423@ASA.COM'	  ,	1,	'324'),
(1135, '32432-4-2342 DV 42',	'Proveedor accidenta' ,   'sdfsdf', 324234	  ,'fdsfsdf@asas.com'	,	1,	'dadsasd'),
(1136, '324234234234'	     ,  'Cosechador novat'    ,   'wewe'	, 2323	    ,'sdsd@as.com'	    ,	2,	'Cosechador novato'),
(1137, '3324324234234'	   ,  'Cosechador novele'   ,   'sdasda', 123434	  ,'asdasd@asas.com'	,	2,	'Cosechador novelero');

INSERT INTO `farms` (id_farm, status_delete, status_farm, name_farm) VALUES
(1, 0, 1, 'granja 1'),
(2, 1, 0, 'granja 2'),
(3, 0, 1, 'granja 3'),
(4, 1, 0, 'Cafe Eleta');

INSERT INTO `stores` (id_store, status_store, status_delete, name_store )VALUES
 (1, 0, 0,'store 1' ),
 (2, 1, 1,'STORE 22'),
 (3, 1, 1,'Beneficio');

INSERT INTO `units` (id_unit, status_unit, name_unit) VALUES
  (1,0,'libra');

INSERT INTO `lots`(id_lot, status_lot, name_lot, area_lot, heigh_lot, status_delete, id_farm, price_lot) VALUES
  (1,   0,  'LOTE 1',     '89lk',   895,      0, 1, 89.00),
  (2,   0,  'LOTE 2',     '256252', 1233252,  0, 2, 2252.00),
  (3,   0,  'LOTE 3',     '25625',  1233253,  1, 1, 5230.00),
  (4,   0,  'LOTE 4',     '89',     89,       1, 2, 100.00),
  (5,   1,  'LOTE 2',     '111',    111,      0, 1, 111.00),
  (6,   0,  'LOTE 6',     '548',    58,       0, 2, 8.00),
  (7,   1,  'LOTE 7',     '258',    2758,     1, 2, 258.00),
  (8,   1,  'LOTE 8',     '56',     56,       1, 1, 56.00),
  (9,   1,  'lote 09',    '123258', 123258,   1, 1, 123258.00),
  (10,  0,  '#4',         '25663',  369,      1, 4, 897646.00),
  (11,  1,  'LOTE 11',    '123',    1212,     1, 3, 121212.00),
  (12,  1,  'lote 12',    '12312',  12121,    1, 2, 121212.00),
  (13,  1,  'LOTE 01',    'gfyu',   126,      1, 1, 126.00),
  (14,  1,  'LOTE 5',     '45',     45,       1, 1, 45.00),
  (15,  1,  'LOTE 3',     '12',     55,       1, 1, 55.00),
  (16,  1,  'LOTE 1',     '58',     89,       1, 2, 89.00),
  (17,  1,  '258',        '5258',   44,       1, 2, 77.00),
  (18,  1,  'MARWIN',     '12',     12,       1, 1, 200.00),
  (19,  0,  'MARWIN',     '2222',   2222,     1, 3, 2222.00),
  (20,  1,  'MARWIN',     '1',      1,        1, 2, 111.00),
  (21,  1,  'MARWIN 1',   '1',      1,        1, 2, 1.00),
  (22,  1,  'LOTE MAR',   '1',      1,        1, 2, 222.00),
  (23,  0,  '#1',         '200',    250,      0, 4, 250.00),
  (24,  0,  '#2',         '250',    300,      1, 4, 500.00),
  (25,  1,  'LOTE 03',    '02',     2,        1, 1, 2.00),
  (26,  0,  '#3',          '58',    58,       1, 4, 58.00);

INSERT INTO `item_types` (`id_itemtype`,`status_delete`,`name_itemtype`,`cost_itemtype`,`status_itemtype`,
                          `id_providertype`,`id_unit`) VALUES
(1,0,'Libras Maduro', 50.00, 1, 1, 1),
(2,0,'Libras Verdes', 20.00, 1, 1, 1),
(3,0,'Libras Seco', 30.00, 1, 1, 1),
(4,0,'Libras Cereza', 40.00, 1, 2, 1),
(5,0,'Libras Pergamino', 25.00, 1, 2, 1),
(6,1,'Libras Cosechas Eliminadas', 10.00, 1, 1, 1),
(7,1,'Libras Compras Eliminadas', 15.00, 1, 2, 1);

INSERT INTO `purities` (`id_purity`,`status_delete`,`name_purity`,`status_purity`,`discountrate_purity`) VALUES
(1, 0, '% Granos Flotes',     1, 20),
(2, 0, '% Granos Bocados',    1, 30),
(3, 1, '% Granos Eliminados', 1, 15);

INSERT INTO `invoices` (`id_invoice`,`status_delete`,`id_provider`,`status_invoice`) VALUES
(600, 0, 1131, 1),
(601, 0, 1130, 1),
(602, 1, 1130, 3),
(603, 1, 1131, 3),
(604, 1, 1131, 3),
(605, 1, 1131, 3),
(606, 1, 1131, 3),
(607, 1, 1131, 3),
(608, 0, 1132, 1),
(609, 0, 1133, 1),
(610, 0, 1130, 1),
(611, 0, 1131, 1),
(612, 0, 1134, 1),
(613, 1, 1130, 3),
(614, 0, 1132, 1),
(615, 0, 1135, 1),
(616, 0, 1136, 1),
(617, 0, 1130, 1),
(618, 0, 1137, 1),
(619, 0, 1130, 1),
(620, 0, 1137, 1),
(621, 0, 1137, 1),
(622, 0, 1136, 1),
(623, 0, 1136, 1),
(624, 0, 1133, 1),
(625, 0, 1137, 1),
(626, 0, 1134, 1),
(627, 0, 1133, 1),
(628, 0, 1136, 1),
(629, 1, 1136, 3),
(630, 0, 1137, 3);


INSERT INTO `invoice_details` (`id_invoicedetail`,`status_delete`,`id_invoice`,`id_itemtype`,`id_lot`,`id_store`,
          `price_itemtypebylot`,`cost_itemtype`,`amount_invoicedetail`,`isfreight_invoicedetail`,
           `note_invoicedetail`,`namereceived_invoicedetail`,`namedelivered_invoicedetail`,`status_invoicedetail`) VALUES
(1209,0,600,1,2,3,0.00,10.00,50,1,'Nada observado','Edwin de respuesto','Entregador Lopez',1),
(1210,0,601,4,26,1,0.60,0.00,15,1,'Nada que ver','Edwin de respuesto','Cosechador Test',1),
(1211,0,601,5,26,1,0.60,0.00,15,1,'Nada que ver','Edwin de respuesto','Cosechador Test',1),
(1212,0,602,4,26,1,0.60,0.00,50,1,'Nada q ver','Edwin de respuesto','Cosechador Test',1),
(1213,0,602,5,26,1,0.60,0.00,15,1,'Nada q ver','Edwin de respuesto','Cosechador Test',1),
(1214,1,602,4,26,1,0.60,0.00,15,1,'NADA','Edwin de respuesto','Cosechador Test',1),
(1215,1,602,5,26,1,0.60,0.00,15,1,'NADA','Edwin de respuesto','Cosechador Test',1),
(1216,0,603,1,2,3,0.00,0.60, 50,1,'Nada xd','Edwin de respuesto','Entregador',1),
(1217,0,604,1,2,3,0.00,100.00,300,1,'Nada jeejje','Edwin de respuesto','Entregador 1',1),
(1218,0,604,1,2,3,0.00,10.00,300,1,'1','Edwin de respuesto','1',1),
(1219,0,605,1,2,3,0.00,10.00,300,1,'1','Edwin de respuesto','1',1),
(1220,0,606,1,2,3,0.00,50.00,100,1,'1','Edwin de respuesto','1',1),
(1221,0,607,1,2,3,0.00,1.00,1,1,'1','Edwin de respuesto','1',1),
(1222,0,608,1,2,3,0.00,32.00,344,1,'Jejeje','Edwin de respuesto','Me',1),
(1223,0,609,1,2,3,0.00,34.00,34,1,'34','Edwin de respuesto','34',1),
(1224,0,609,1,2,3,0.00,5.00,500,1,'500','Edwin de respuesto','800',1),
(1225,0,609,1,2,3,0.00,2.00,222,1,'22','Edwin de respuesto','22',1),
(1226,0,610,4,25,1,0.60,0.00,34,1,'DSFSFD','Edwin de respuesto','Cosechador Test',1),
(1227,0,610,5,25,1,0.60,0.00,36,1,'DSFSFD','Edwin de respuesto','Cosechador Test',1),
(1228,0,610,4,25,1,0.60,0.00,100,1,'DSFSDF','Edwin de respuesto','Cosechador Test',1),
(1229,0,610,5,25,1,0.60,0.00,150,1,'DSFSDF','Edwin de respuesto','Cosechador Test',1),
(1230,0,609,3,4,3,0.00,20.00,10,1,'60','Edwin de respuesto','50',1),
(1231,0,611,1,4,3,0.00,98.00,99,1,'94','Edwin de respuesto','95',1),
(1232,0,611,1,4,3,0.00,20.00,600,1,'Jejeje','Edwin de respuesto','Me',1),
(1233,0,612,1,4,3,0.00,200.00,100,1,'3','Edwin de respuesto','3',1),
(1234,0,613,4,24,2,0.60,0.00,25,1,'DIF','Edwin de respuesto','Cosechador Test',1),
(1235,0,613,5,24,2,0.60,0.00,25,1,'DIF','Edwin de respuesto','Cosechador Test',1),
(1236,0,614,1,6,3,0.00,398.00,399,1,'394','Edwin de respuesto','395',1),
(1237,0,615,1,6,3,0.00,52.00,51,1,'56','Edwin de respuesto','55',1),
(1238,0,613,4,23,2,0.60,0.00,101,1,'fff','Edwin de respuesto','Cosechador Test',1),
(1239,0,613,5,23,2,0.60,0.00,202,1,'fff','Edwin de respuesto','Cosechador Test',1),
(1240,0,616,4,23,2,0.60,0.00,23,1,'Todo Ok','Edwin de respuesto','Cosechador novato',1),
(1241,0,616,5,23,2,0.60,0.00,124,0,'194','Edwin de respuesto','195',1),
(1242,0,617,1,23,2,0.60,0.00,51,1,'sdsd','Edwin de respuesto','Cosechador Test',1),
(1243,0,617,4,23,2,0.60,0.00,22,1,'sdsd','Edwin de respuesto','Cosechador Test',1),
(1244,0,617,5,23,2,0.60,0.00,33,1,'sdsd','Edwin de respuesto','Cosechador Test',1),
(1245,0,618,4,23,2,0.60,0.00,222,1,'Bad things','Edwin de respuesto','Cosechador novelero',1),
(1246,0,618,5,23,2,0.60,0.00,333,1,'Bad things','Edwin de respuesto','Cosechador novelero',1),
(1247,0,614,2,8,3,0.00,22.00,21,1,'26','Edwin de respuesto','25',1),
(1248,0,619,4,22,1,0.60,0.00,56,1,'','Andres Campo','Cosechador Test',1),
(1249,0,620,4,22,1,0.60,0.00,66,1,'','Andres Campo','Cosechador novelero',1),
(1250,0,621,4,22,1,0.60,0.00,58,1,'','Andres Campo','Cosechador novelero',1),
(1251,0,622,4,22,1,0.60,0.00,52,1,'awebao','Andres Campo','Cosechador novato',1),
(1252,0,623,4,22,1,0.60,0.00,60,1,'el café fue entregado a pedro','Andres Campo','Cosechador novato',1),
(1253,0,623,5,22,1,0.60,0.00,1000,1,'el café fue entregado a pedro','Andres Campo','Cosechador novato',1),
(1254,1,623,4,22,1,0.60,0.00,45,1,'','Andres Campo','Cosechador novato',1),
(1255,1,623,5,22,1,0.60,0.00,10,1,'','Andres Campo','Cosechador novato',1),
(1256,0,624,3,10,3,0.00,50.00,20,1,'','Andres Campo','juan',1),
(1257,0,623,4,20,2,0.60,0.00,15,1,'','Andres Campo','Cosechador novato',1),
(1258,0,623,5,20,2,0.60,0.00,20,1,'','Andres Campo','Cosechador novato',1),
(1259,0,625,4,20,2,0.60,0.00,7,1,'','Andres Campo','Cosechador novelero',1),
(1260,0,625,5,20,2,0.60,0.00,2,1,'','Andres Campo','Cosechador novelero',1),
(1261,0,626,1,10,3,0.00,20.50,20,1,'','Andres Campo','ma',1),
(1262,0,627,1,10,3,0.00,20.50,20,1,'','Edwin de respuesto','none',1),
(1263,0,628,4,20,3,0.60,0.00,238,1,'','Andres Campo','Cosechador novato',1),
(1264,0,629,4,20,3,0.60,0.00,12,1,'','Andres Campo','Cosechador novato',1),
(1265,0,629,5,20,3,0.60,0.00,1,1,'','Andres Campo','Cosechador novato',1),
(1266,0,630,4,20,3,0.60,0.00,20,1,'','Andres Campo','Cosechador novelero',1);

INSERT INTO `invoicesdetails_purities` (`id_invoicedetail_purity`,`status_delete`,`id_purity`,
                                        `valuerate_invoicedetail_purity`,`totaldiscount_purity`,`discountrate_purity`,
                                        `id_invoicedetail`,`status__invoicedetail_purity`) VALUES
(1,  0, 2, 1,0,0,1209,  1),
(2,  0, 1, 1,0,0,1209,  1),
(3,  0, 2, 1,0,0,1216,  1),
(4,  0, 1, 1,0,0,1216,  1),
(5,  0, 2, 20,0,0,1217, 1),
(6,  0, 1, 25,0,0,1217, 1),
(7,  0, 2, 1,0,0,1218,  1),
(8,  0, 1, 1,0,0,1218,  1),
(9,  0, 2, 1,0,0,1219,  1),
(10, 0, 1, 1,0,0,1219, 1),
(11, 0, 2, 1,0,0,1220, 1),
(12, 0, 1, 1,0,0,1220, 1),
(13, 0, 2, 26,0,0,1221,  1),
(14, 0, 1, 27,0,0,1221,  1),
(15, 0, 2, 1,0,0,1222, 1),
(16, 0, 1, 0,0,0,1222, 1),
(17, 0, 2, 34,0,0,1223,  1),
(18, 0, 1, 34,0,0,1223,  1),
(19, 0, 2, 500,0,0,1224, 1),
(20, 0, 1, 500,0,0,1224, 1),
(21, 0, 2, 22,0,0,1225,  1),
(22, 0, 1, 22,0,0,1225,  1),
(23, 0, 2, 30,0,0,1230,  1),
(24, 0, 1, 40,0,0,1230,  1),
(25, 0, 1, 96,0,0,1231,  1),
(26, 0, 2, 97,0,0,1231,  1),
(27, 0, 1, 0,0,0,1232, 1),
(28, 0, 2, 1,0,0,1232, 1),
(29, 0, 1, 3,0,0,1233, 1),
(30, 0, 2, 3,0,0,1233, 1),
(31, 0, 1, 96,0,0,1236,  1),
(32, 0, 2, 97,0,0,1236,  1),
(33, 0, 1, 54,0,0,1237,  1),
(34, 0, 2, 53,0,0,1237,  1),
(35, 0, 2, 1,0,0,1247, 1),
(36, 0, 1, 1,0,0,1247, 1),
(37, 0, 2, 10,0,0,1256,  1),
(38, 0, 2, 10,0,0,1261,  1),
(39, 0, 1, 25,0,0,1261,  1),
(40, 0, 2, 10,0,0,1262,  1),
(41, 0, 1, 25,0,0,1262,  1);

# --- !Downs

SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE farms;

TRUNCATE provider_type;

TRUNCATE stores;

TRUNCATE units;

TRUNCATE purities;

TRUNCATE lots;

TRUNCATE providers;

TRUNCATE invoices;

TRUNCATE item_types;

TRUNCATE invoice_details;

TRUNCATE invoicesdetails_purities;

SET FOREIGN_KEY_CHECKS = 1;