# --- !Ups
INSERT INTO `status` VALUES ('user',1,0,'active','2017-05-26 00:00:00','2017-05-26 00:00:00'),('role',2,0,'active','2017-05-26 00:00:00','2017-05-26 00:00:00');

INSERT INTO `tag` VALUES (1,0,'SuperUsuario','negra','2017-05-02 04:30:00','2017-05-02 04:30:00'),(2,0,'basic','BasicLogin','2017-05-02 04:30:00','2015-05-02 04:30:00'),(3,0,'Reportes','AUTOMATIC ADD','2017-06-01 05:09:52','2017-06-01 05:09:52'),(4,0,'Ordenes','AUTOMATIC ADD','2017-06-01 05:09:54','2017-06-01 05:09:54');

INSERT INTO `route` VALUES (1,0,'/unit','AUTOMATIC ADD',1,'2017-05-02 04:30:00','2017-05-02 04:30:00'),(2,0,'/user/findByEmail/:email','AUTOMATIC ADD',0,'2017-05-29 03:39:58','2017-05-29 03:39:58'),(3,0,'/role/getmenu','AUTOMATIC ADD',0,'2017-05-29 03:54:30','2017-05-29 03:54:30'),(4,0,'/route/checkngroute','AUTOMATIC ADD',0,'2017-05-29 04:37:25','2017-05-29 04:37:25'),(5,0,'/user/findByEmail/','AUTOMATIC ADD',0,'2017-06-01 05:09:33','2017-06-01 05:09:33'),(6,0,'/itemType/findAll/:index/:size','AUTOMATIC ADD',0,'2017-06-06 18:49:17','2017-06-06 18:49:17'),(7,0,'/unit/findAll/:index/:size','AUTOMATIC ADD',0,'2017-06-07 03:59:20','2017-06-07 03:59:20'),(8,0,'/lot','AUTOMATIC ADD',0,'2017-06-07 03:59:20','2017-06-07 03:59:20'),(9,0,'/user/verify/','AUTOMATIC ADD',0,'2017-08-01 16:33:05','2017-08-01 16:33:05');

INSERT INTO `role` VALUES (1,0,'admin','Administrador',2,'2017-06-06 14:28:51','2017-06-06 14:28:51'),(2,0,'Operador','Operador',2,'2017-05-05 04:30:00','2017-05-02 04:30:00'),(3,0,'Finanzas','Finanzas',2,'2017-06-06 14:28:51','2017-06-06 14:28:51');

INSERT INTO `route_tag` VALUES (1,1),(1,2),(2,2),(3,2),(3,3),(4,2),(4,4),(5,2),(5,3),(6,1),(6,2),(7,2),(8,1),(8,2),(8,3),(8,4),(9,1),(9,2),(9,3),(9,4);

INSERT INTO `tag_role` VALUES (2,1),(3,2),(4,3);

INSERT INTO `config` VALUES (1,'SecurityEnabled','Enabled',NULL),(2,'Content-Type','application/json',NULL),(3,'method','POST',NULL),(4,'cdn-container','test01',''),(5,'cdn-parent','example',NULL),(6,'Url-WS-Rackspace','http://10.0.3.4:9000/media/create','url del servicio que sube la imagen al rackspace'),(7,'nameCompany','Cafe de Eleta, S.A',NULL),(8,'invoiceDescription','Recibo Diario de Cafe',NULL),(9,'invoiceType','Cosecha Propia',NULL),(10,'RUC','R.U.C 1727-188-34109 D.V. 69',NULL),(11,'telephonoCompany','6679-4752',NULL);

INSERT INTO `user` VALUES
(1,0,'Yenny1','123456','Yenny1','Fung1','darwinrocha@usb.ve',1,0,'2017-05-22 23:33:52',1,'2017-05-22 00:00:00','2017-10-17 14:34:21'),
(2,0,'drocha','258963','darwin','Rocha','darwin.rocha@hecticus.com',1,0,'2017-05-22 23:33:52',2,'2017-05-22 23:34:26','2017-10-27 17:45:45'),
(3,0,'Andres','123456','Andres','Corra','darwinrocha85@gmail.com',1,0,'2017-05-22 23:33:52',3,'2017-05-23 16:18:49','2017-05-23 20:23:00'),
(4,0,'Marwin','1234','Andres','Campo','marwin@hecticus.com',1,0,'2017-05-22 23:33:52',2,'2017-05-23 20:21:56','2017-10-18 14:37:40'),
(5,0,'Edwin','123456','Edwin','de respuesto','edwinderepuesto@gmail.com',1,0,NULL,2,'2017-09-14 14:01:29','2017-10-31 10:40:27'),
(6,0,'Alejandro','12345','Alejandro ','Carvallo','alejandro@hecticus.com',1,0,NULL,2,'2017-10-11 14:49:24','2017-10-13 10:24:20'),
(7,0,'Max','12345','Orlando','Carvallo','orlando@hecticus.com',1,0,NULL,2,'2017-10-11 14:50:07','2017-10-11 12:56:44'),
(9,0,'joha','johanna','Johanna','Chan','johanna.chan@hecticus.com',1,0,NULL,2,'2017-10-19 19:56:03','2017-10-19 17:19:32');

# --- !Downs
