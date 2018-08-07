INSERT INTO `invoices` (`id`,`deleted`,`provider_id`, `status_invoice_id`) VALUES
(600, 0, 1131, 11 ),
(601, 0, 1130, 11 ),
(602, 1, 1130, 11 ),
(603, 1, 1131, 11 ),
(604, 1, 1131, 11 ),
(605, 1, 1131, 11 ),
(606, 1, 1131, 11 ),
(607, 1, 1131, 11 ),
(608, 0, 1132, 11 ),
(609, 0, 1133, 11 ),
(610, 0, 1130, 11 ),
(611, 0, 1131, 12 ),
(612, 0, 1134, 12 ),
(613, 1, 1130, 12 ),
(614, 0, 1132, 12 ),
(615, 0, 1135, 12 ),
(616, 0, 1136, 12 ),
(617, 0, 1130, 12 ),
(618, 0, 1137, 12 ),
(619, 0, 1130, 12 ),
(620, 0, 1137, 13 ),
(621, 0, 1137, 13 ),
(622, 0, 1136, 13 ),
(623, 0, 1136, 13 ),
(624, 0, 1133, 13 ),
(625, 0, 1137, 13 ),
(626, 0, 1134, 13 ),
(627, 0, 1133, 13 ),
(628, 0, 1136, 13 ),
(629, 1, 1136, 13 ),
(630, 0, 1137, 13 );

INSERT INTO `invoice_details` (`id`,`deleted`,`invoice_id`,`item_type_id`,`lot_id`,`store_id`,
          `price_item_type_by_lot`,`cost_item_type`,`amount_invoice_detail`,
           `note`,`name_received`,`name_delivered` ) VALUES
(1209,0,600,1,2,3,0.00,10.00,50,'Nada observado','Edwin de respuesto','Entregador Lopez' ),
(1210,0,601,4,26,1,0.60,0.00,15,'Nada que ver','Edwin de respuesto','Cosechador Test' ),
(1211,0,601,5,26,1,0.60,0.00,15,'Nada que ver','Edwin de respuesto','Cosechador Test' ),
(1212,0,602,4,26,1,0.60,0.00,50,'Nada q ver','Edwin de respuesto','Cosechador Test' ),
(1213,0,602,5,26,1,0.60,0.00,15,'Nada q ver','Edwin de respuesto','Cosechador Test' ),
(1214,1,602,4,26,1,0.60,0.00,15,'NADA','Edwin de respuesto','Cosechador Test' ),
(1215,1,602,5,26,1,0.60,0.00,15,'NADA','Edwin de respuesto','Cosechador Test' ),
(1216,0,603,1,2,3,0.00,0.60, 50,'Nada xd','Edwin de respuesto','Entregador' ),
(1217,0,604,1,2,3,0.00,100.00,300,'Nada jeejje','Edwin de respuesto','Entregador 1' ),
(1218,0,604,1,2,3,0.00,10.00,300, '1','Edwin de respuesto','1' ),
(1219,0,605,1,2,3,0.00,10.00,300, '1','Edwin de respuesto','1' ),
(1220,0,606,1,2,3,0.00,50.00,100, '1','Edwin de respuesto','1' ),
(1221,0,607,1,2,3,0.00,1.00,1, '1','Edwin de respuesto','1' ),
(1222,0,608,1,2,3,0.00,32.00,344, 'Jejeje','Edwin de respuesto','Me' ),
(1223,0,609,1,2,3,0.00,34.00,34, '34','Edwin de respuesto','34' ),
(1224,0,609,1,2,3,0.00,5.00,500, '500','Edwin de respuesto','800' ),
(1225,0,609,1,2,3,0.00,2.00,222, '22','Edwin de respuesto','22' ),
(1226,0,610,4,25,1,0.60,0.00,34, 'DSFSFD','Edwin de respuesto','Cosechador Test' ),
(1227,0,610,5,25,1,0.60,0.00,36, 'DSFSFD','Edwin de respuesto','Cosechador Test' ),
(1228,0,610,4,25,1,0.60,0.00,100, 'DSFSDF','Edwin de respuesto','Cosechador Test' ),
(1229,0,610,5,25,1,0.60,0.00,150, 'DSFSDF','Edwin de respuesto','Cosechador Test' ),
(1230,0,609,3,4,3,0.00,20.00,10, '60','Edwin de respuesto','50' ),
(1231,0,611,1,4,3,0.00,98.00,99, '94','Edwin de respuesto','95' ),
(1232,0,611,1,4,3,0.00,20.00,600, 'Jejeje','Edwin de respuesto','Me' ),
(1233,0,612,1,4,3,0.00,200.00,100, '3','Edwin de respuesto','3' ),
(1234,0,613,4,24,2,0.60,0.00,25, 'DIF','Edwin de respuesto','Cosechador Test' ),
(1235,0,613,5,24,2,0.60,0.00,25, 'DIF','Edwin de respuesto','Cosechador Test' ),
(1236,0,614,1,6,3,0.00,398.00,399, '394','Edwin de respuesto','395' ),
(1237,0,615,1,6,3,0.00,52.00,51, '56','Edwin de respuesto','55' ),
(1238,0,613,4,23,2,0.60,0.00,101, 'fff','Edwin de respuesto','Cosechador Test' ),
(1239,0,613,5,23,2,0.60,0.00,202, 'fff','Edwin de respuesto','Cosechador Test' ),
(1240,0,616,4,23,2,0.60,0.00,23, 'Todo Ok','Edwin de respuesto','Cosechador novato' ),
(1241,0,616,5,23,2,0.60,0.00,124, '194','Edwin de respuesto','195' ),
(1242,0,617,1,23,2,0.60,0.00,51, 'sdsd','Edwin de respuesto','Cosechador Test' ),
(1243,0,617,4,23,2,0.60,0.00,22, 'sdsd','Edwin de respuesto','Cosechador Test' ),
(1244,0,617,5,23,2,0.60,0.00,33, 'sdsd','Edwin de respuesto','Cosechador Test' ),
(1245,0,618,4,23,2,0.60,0.00,222, 'Bad things','Edwin de respuesto','Cosechador novelero' ),
(1246,0,618,5,23,2,0.60,0.00,333, 'Bad things','Edwin de respuesto','Cosechador novelero' ),
(1247,0,614,2,8,3,0.00,22.00,21, '26','Edwin de respuesto','25' ),
(1248,0,619,4,22,1,0.60,0.00,56, '','Andres Campo','Cosechador Test' ),
(1249,0,620,4,22,1,0.60,0.00,66, '','Andres Campo','Cosechador novelero' ),
(1250,0,621,4,22,1,0.60,0.00,58, '','Andres Campo','Cosechador novelero' ),
(1251,0,622,4,22,1,0.60,0.00,52, 'awebao','Andres Campo','Cosechador novato' ),
(1252,0,623,4,22,1,0.60,0.00,60, 'el café fue entregado a pedro','Andres Campo','Cosechador novato' ),
(1253,0,623,5,22,1,0.60,0.00,1000, 'el café fue entregado a pedro','Andres Campo','Cosechador novato' ),
(1254,1,623,4,22,1,0.60,0.00,45, '','Andres Campo','Cosechador novato' ),
(1255,1,623,5,22,1,0.60,0.00,10, '','Andres Campo','Cosechador novato' ),
(1256,0,624,3,10,3,0.00,50.00,20, '','Andres Campo','juan' ),
(1257,0,623,4,20,2,0.60,0.00,15, '','Andres Campo','Cosechador novato' ),
(1258,0,623,5,20,2,0.60,0.00,20, '','Andres Campo','Cosechador novato' ),
(1259,0,625,4,20,2,0.60,0.00,7, '','Andres Campo','Cosechador novelero' ),
(1260,0,625,5,20,2,0.60,0.00,2, '','Andres Campo','Cosechador novelero' ),
(1261,0,626,1,10,3,0.00,20.50,20, '','Andres Campo','ma' ),
(1262,0,627,1,10,3,0.00,20.50,20, '','Edwin de respuesto','none' ),
(1263,0,628,4,20,3,0.60,0.00,238, '','Andres Campo','Cosechador novato' ),
(1264,0,629,4,20,3,0.60,0.00,12, '','Andres Campo','Cosechador novato' ),
(1265,0,629,5,20,3,0.60,0.00,1, '','Andres Campo','Cosechador novato' ),
(1266,0,630,4,20,3,0.60,0.00,20, '','Andres Campo','Cosechador novelero' );


INSERT INTO `invoicesdetails_purities` (`id`,`deleted`,`purity_id`,
                                        `value_rate_invoice_detail_purity`,`total_discount_purity`,`discount_rate_purity`,
                                        `invoice_detail_id`) VALUES
(1,  0, 2, 1,0,0,1209 ),
(2,  0, 1, 1,0,0,1209 ),
(3,  0, 2, 1,0,0,1216 ),
(4,  0, 1, 1,0,0,1216 ),
(5,  0, 2, 20,0,0,1214 ),
(6,  0, 1, 25,0,0,1214 ),
(7,  0, 2, 1,0,0,1218 ),
(8,  0, 1, 1,0,0,1218 ),
(9,  0, 2, 1,0,0,1219 ),
(10, 0, 1, 1,0,0,1212 ),
(11, 0, 2, 1,0,0,1222 ),
(12, 0, 1, 1,0,0,1222 ),
(13, 0, 2, 26,0,0,1221 ),
(14, 0, 1, 27,0,0,1221 ),
(15, 0, 2, 1,0,0,1220),
(16, 0, 1, 0,0,0,1220),
(17, 0, 2, 34,0,0,1223 ),
(18, 0, 1, 34,0,0,1223 ),
(19, 0, 2, 500,0,0,1226 ),
(20, 0, 1, 500,0,0,1226),
(21, 0, 2, 22,0,0,1225 ),
(22, 0, 1, 22,0,0,1225 ),
(23, 0, 2, 30,0,0,1230 ),
(24, 0, 1, 40,0,0,1230 ),
(25, 0, 1, 96,0,0,1231 ),
(26, 0, 2, 97,0,0,1231 ),
(27, 0, 1, 0,0,0,1234),
(28, 0, 2, 1,0,0,1234),
(29, 0, 1, 3,0,0,1234),
(30, 0, 2, 3,0,0,1234),
(31, 0, 1, 96,0,0,1246 ),
(32, 0, 2, 97,0,0,1236 ),
(33, 0, 1, 54,0,0,1237 ),
(34, 0, 2, 53,0,0,1237 ),
(35, 0, 2, 1,0,0,1242),
(36, 0, 1, 1,0,0,1242),
(37, 0, 2, 10,0,0,1256 ),
(38, 0, 2, 10,0,0,1261 ),
(39, 0, 1, 25,0,0,1261 ),
(40, 0, 2, 10,0,0,1262 ),
(41, 0, 1, 25,0,0,1262 );



INSERT INTO `config` (id, config_key, config_value, description) VALUES
(7,'nameCompany','Cafe de Eleta, S.A',NULL),
(8,'invoiceDescription','Recibo Diario de Cafe',NULL),
(9,'invoiceType','Cosecha Propia',NULL),
(10,'RUC','R.U.C 1727-188-34109 D.V. 69',NULL),
(11,'telephonoCompany','6679-4752',NULL);


INSERT INTO `status` ( id, dtype, name, description) VALUES
  (1,  'farm',          'Activo',     'No deudor'),
  (2,  'farm',          'Inactivo',   'No deudor'),
  (11, 'invoice',       'Abierta',       'Deudor'),
  (12, 'invoice',       'Cerrada',     'Deudor'),
  (13, 'invoice',       'Cancelada',   'Deudor'),
  (31, 'lot',           'Activo',      NULL),
  (32, 'lot',           'Inactivo',    NULL),
  (41, 'provider',      'Activo',      NULL),
  (42, 'provider',      'Inactivo',    NULL),
  (50, 'store',         'Activo',      NULL),
  (51, 'store',         'Inactivo',    NULL);


INSERT INTO `provider_type` (`id`,`deleted`,`name_provider_type`)  VALUES
(1, 0, 'Vendedor'),
(2, 0, 'Cosechador'),
(3, 1, 'prueba');

INSERT INTO `providers` (id, nit_provider, name_provider, address_provider, number_provider,
                         email_provider, provider_type_id,  deleted, status_provider_id, contact_name_provider) VALUES
(1130, '42424'	           ,  'Cosechador Tes'      ,   'wweq'	, 332432	  ,'DSDSD@com.com'	  ,	2,  0, 41,	'Cosechador Test'),
(1131, '12345-4-2424 DV 24',	'Proveedor Te'	      ,   'Here'	, 52454547	,'pt@test.com'	    ,	1,  0, 41,	'None'),
(1132, '42343-2-4234 DV 32',	'Proveedor altern'    ,   'dsfdsf', 3424	    ,'ddfd@sds.com'	    ,	1,  0, 41,	'dfsdfsdf'),
(1133, '67567-5-7567 DV 56',	'Proveedor '	        ,   'dfsdf'	, 432	      ,'32432@as.com'	    ,	1,  0, 41,	'sdsd'),
(1134, '12121-2-1212 DV 12',	'PROV'                ,   '342343', 234234	  ,'32423@ASA.COM'	  ,	1,  0, 41,	'324'),
(1135, '32432-4-2342 DV 42',	'Proveedor accidenta' ,   'sdfsdf', 324234	  ,'fdsfsdf@asas.com'	,	1,  1, 42,  'dadsasd'),
(1136, '324234234234'	     ,  'Cosechador novat'    ,   'wewe'	, 2323	    ,'sdsd@as.com'	    ,	2,  1, 42,  'Cosechador novato'),
(1137, '3324324234234'	   ,  'Cosechador novele'   ,   'sdasda', 123434	  ,'asdasd@asas.com'	,	2,  1, 42,  'Cosechador novelero');

INSERT INTO `farms` (id, deleted, status_farm_id, name_farm) VALUES
(1, 0, 1, 'granja 1'),
(2, 1, 2, 'granja 2'),
(3, 0, 1, 'granja 3'),
(4, 1, 2, 'Cafe Eleta');

INSERT INTO `stores` (id, deleted, status_store_id, name_store )VALUES
 (1,  0, 50, 'store 1' ),
 (2,  1, 51, 'STORE 22'),
 (3,  1, 51, 'Beneficio');

INSERT INTO `units` (id, name_unit) VALUES
  (1,'libra');

INSERT INTO `lots`(id, name_lot, area_lot, heigh_lot,  farm_id, status_lot_id, deleted, price_lot) VALUES
  (1,     'LOTE 1',     '89lk',   895,        1, 31, 0, 50.00),
  (2,     'LOTE 2',     '256252', 1233252,    2, 31, 0, 60.00),
  (3,     'LOTE 3',     '25625',  1233253,    1, 31, 0, 100.00),
  (4,     'LOTE 4',     '89',     89,         2, 31, 0, 200.00),
  (5,     'LOTE 2',     '111',    111,        1, 31, 0, 250.00),
  (6,     'LOTE 6',     '548',    58,         2, 31, 0, 8.00),
  (7,     'LOTE 7',     '258',    2758,       2, 32, 1, 258.00),
  (8,     'LOTE 8',     '56',     56,         1, 32, 1, 56.00),
  (9,     'lote 09',    '123258', 123258,     1, 32, 1, 123258.00);

INSERT INTO `item_types` (`id`,`deleted`,`name_item_type`,`cost_item_type`,
                          `provider_type_id`,`unit_id`) VALUES
(1,0,'Libras Maduro',               10,  1, 1),
(2,0,'Libras Verdes',               20,  1, 1),
(3,0,'Libras Seco',                 30,  1, 1),
(4,0,'Libras Cereza',               10,  2, 1),
(5,0,'Libras Pergamino',            20,  2, 1),
(6,1,'Libras Cosechas Eliminadas',  10,  1, 1),
(7,1,'Libras Compras Eliminadas',   10,  2, 1);

INSERT INTO `purities` (`id`,`deleted`,`name_purity`,`discount_rate_purity`) VALUES
(1, 0, '% Granos Flotes',      20),
(2, 0, '% Granos Bocados',     30),
(3, 1, '% Granos Eliminados',  15);

SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE `config`;

TRUNCATE `status`;

TRUNCATE `farms`;

TRUNCATE `provider_type`;

TRUNCATE `stores`;

TRUNCATE `units`;

TRUNCATE `purities`;

TRUNCATE `lots`;

TRUNCATE `providers`;

TRUNCATE `invoices`;

TRUNCATE `item_types`;

TRUNCATE `invoice_details`;

TRUNCATE `invoicesdetails_purities`;

TRUNCATE `multimedia`;

TRUNCATE `multimedia_cdn`;

SET FOREIGN_KEY_CHECKS = 1;

# --- !Ups

# --- !Downs