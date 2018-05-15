# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table auth_user (
  id                            bigint auto_increment not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  email                         varchar(100) not null,
  password                      text not null,
  archived                      tinyint default 0 not null,
  last_login                    datetime,
  constraint uq_auth_user_email unique (email),
  constraint pk_auth_user primary key (id)
);

create table auth_user_auth_role (
  auth_user_id                  bigint not null,
  auth_role_id                  varchar(50) not null,
  constraint pk_auth_user_auth_role primary key (auth_user_id,auth_role_id)
);

create table auth_user_auth_group (
  auth_user_id                  bigint not null,
  auth_group_id                 varchar(50) not null,
  constraint pk_auth_user_auth_group primary key (auth_user_id,auth_group_id)
);

create table auth_client_credential (
  id                            bigint auto_increment not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  client_id                     varchar(50) not null,
  client_secret                 varchar(100),
  name                          varchar(100) not null,
  icon                          text,
  home_page_url                 text,
  privacy_policy_url            text,
  auth_callback_uri             text,
  description                   varchar(255),
  constraint uq_auth_client_credential_client_id unique (client_id),
  constraint pk_auth_client_credential primary key (id)
);

create table config (
  id_config                     bigint auto_increment not null,
  config_key                    varchar(50) not null,
  value                         varchar(255) not null,
  description                   varchar(255),
  constraint pk_config primary key (id_config)
);

create table farms (
  id_farm                       bigint(100) auto_increment not null,
  status_delete                 integer not null,
  name_farm                     varchar(100) not null,
  status_farm                   integer(100) not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
  constraint pk_farms primary key (id_farm)
);

create table auth_group (
  id                            varchar(50) not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  description                   varchar(255),
  constraint pk_auth_group primary key (id)
);

create table invoices (
  id_invoice                    bigint auto_increment not null,
  status_delete                 integer not null,
  id_provider                   bigint not null,
  status_invoice                integer not null,
  duedate_invoice               datetime(6) not null,
  closeddate_invoice            datetime(6) not null,
  total_invoice                 double,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
  constraint pk_invoices primary key (id_invoice)
);

create table invoice_details (
  id_invoicedetail              bigint auto_increment not null,
  status_delete                 integer not null,
  id_invoice                    bigint not null,
  id_itemtype                   bigint not null,
  id_lot                        bigint,
  id_store                      bigint,
  price_itemtypebylot           decimal(10,2) not null,
  cost_itemtype                 decimal(10,2) not null,
  duedate_invoicedetail         datetime(6) not null,
  amount_invoicedetail          float(10,2) not null,
  isfreight_invoicedetail       tinyint(1) default 0 not null,
  note_invoicedetail            varchar(255),
  namereceived_invoicedetail    varchar(255) not null,
  namedelivered_invoicedetail   varchar(255) not null,
  status_invoicedetail          integer not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
  constraint pk_invoice_details primary key (id_invoicedetail)
);

create table invoicesdetails_purities (
  id_invoicedetail_purity       bigint auto_increment not null,
  status_delete                 integer not null,
  id_purity                     bigint not null,
  valuerate_invoicedetail_purity integer not null,
  totaldiscount_purity          integer not null,
  discountrate_purity           integer not null,
  id_invoicedetail              bigint,
  status__invoicedetail_purity  integer not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
  constraint pk_invoicesdetails_purities primary key (id_invoicedetail_purity)
);

create table item_types (
  id_itemtype                   bigint auto_increment not null,
  status_delete                 integer not null,
  name_itemtype                 varchar(255) not null,
  cost_itemtype                 decimal(10,2) not null,
  status_itemtype               integer not null,
  id_providertype               bigint(100) not null,
  id_unit                       bigint not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
  constraint pk_item_types primary key (id_itemtype)
);

create table lots (
  id_lot                        bigint auto_increment not null,
  status_delete                 integer not null,
  name_lot                      varchar(255) not null,
  area_lot                      varchar(255) not null,
  heigh_lot                     double not null,
  status_lot                    integer not null,
  id_farm                       bigint(100) not null,
  price_lot                     decimal(10,2) not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
  constraint pk_lots primary key (id_lot)
);

create table auth_permission (
  id                            bigint auto_increment not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  name                          varchar(70),
  route                         varchar(100) not null,
  description                   varchar(255),
  constraint pk_auth_permission primary key (id)
);

create table auth_permission_auth_role (
  auth_permission_id            bigint not null,
  auth_role_id                  varchar(50) not null,
  constraint pk_auth_permission_auth_role primary key (auth_permission_id,auth_role_id)
);

create table providers (
  id_provider                   bigint auto_increment not null,
  status_delete                 integer not null,
  identificationdoc_provider    varchar(255) not null,
  fullname_provider             varchar(255) not null,
  address_provider              varchar(255) not null,
  phonenumber_provider          varchar(255) not null,
  email_provider                varchar(255),
  photo_provider                varchar(255),
  id_providertype               bigint(100) not null,
  contactname_provider          varchar(255) not null,
  status_provider               integer not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
  constraint uq_providers_identificationdoc_provider unique (identificationdoc_provider),
  constraint pk_providers primary key (id_provider)
);

create table provider_type (
  id_providertype               bigint(100) auto_increment not null,
  status_delete                 integer not null,
  name_providertype             varchar(100) not null,
  status_providertype           integer not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
  constraint pk_provider_type primary key (id_providertype)
);

create table purities (
  id_purity                     bigint auto_increment not null,
  status_delete                 integer not null,
  name_purity                   varchar(255) not null,
  status_purity                 integer not null,
  discountrate_purity           integer not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
  constraint pk_purities primary key (id_purity)
);

create table auth_role (
  id                            varchar(50) not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  description                   varchar(255),
  constraint pk_auth_role primary key (id)
);

create table auth_role_auth_group (
  auth_role_id                  varchar(50) not null,
  auth_group_id                 varchar(50) not null,
  constraint pk_auth_role_auth_group primary key (auth_role_id,auth_group_id)
);

create table auth_pin (
  id                            bigint auto_increment not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  auth_user_id                  bigint not null,
  pin                           varchar(50) not null,
  expiration                    datetime,
  tries                         integer,
  constraint uq_auth_pin_auth_user_id unique (auth_user_id),
  constraint pk_auth_pin primary key (id)
);

create table auth_token (
  id                            bigint auto_increment not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  token                         text not null,
  auth_user_id                  bigint not null,
  constraint pk_auth_token primary key (id)
);

create table status (
  status_type                   varchar(31) not null,
  id_status                     bigint auto_increment not null,
  status_delete                 integer not null,
  name                          varchar(100),
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
  constraint pk_status primary key (id_status)
);

create table stores (
  id_store                      bigint auto_increment not null,
  status_delete                 integer not null,
  name_store                    varchar(255) not null,
  status_store                  integer not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
  constraint pk_stores primary key (id_store)
);

create table units (
  id_unit                       bigint auto_increment not null,
  status_delete                 integer not null,
  name_unit                     varchar(255) not null,
  status_unit                   integer not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
  constraint pk_units primary key (id_unit)
);

create table user (
  id                            bigint auto_increment not null,
  status_delete                 integer not null,
  auth_user_id                  bigint not null,
  first_name                    varchar(100) not null,
  last_name                     varchar(100) not null,
  last_login                    datetime not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
  constraint uq_user_auth_user_id unique (auth_user_id),
  constraint pk_user primary key (id)
);

alter table auth_user_auth_role add constraint fk_auth_user_auth_role_auth_user foreign key (auth_user_id) references auth_user (id) on delete restrict on update restrict;
create index ix_auth_user_auth_role_auth_user on auth_user_auth_role (auth_user_id);

alter table auth_user_auth_role add constraint fk_auth_user_auth_role_auth_role foreign key (auth_role_id) references auth_role (id) on delete restrict on update restrict;
create index ix_auth_user_auth_role_auth_role on auth_user_auth_role (auth_role_id);

alter table auth_user_auth_group add constraint fk_auth_user_auth_group_auth_user foreign key (auth_user_id) references auth_user (id) on delete restrict on update restrict;
create index ix_auth_user_auth_group_auth_user on auth_user_auth_group (auth_user_id);

alter table auth_user_auth_group add constraint fk_auth_user_auth_group_auth_group foreign key (auth_group_id) references auth_group (id) on delete restrict on update restrict;
create index ix_auth_user_auth_group_auth_group on auth_user_auth_group (auth_group_id);

alter table invoices add constraint fk_invoices_id_provider foreign key (id_provider) references providers (id_provider) on delete restrict on update restrict;
create index ix_invoices_id_provider on invoices (id_provider);

alter table invoice_details add constraint fk_invoice_details_id_invoice foreign key (id_invoice) references invoices (id_invoice) on delete restrict on update restrict;
create index ix_invoice_details_id_invoice on invoice_details (id_invoice);

alter table invoice_details add constraint fk_invoice_details_id_itemtype foreign key (id_itemtype) references item_types (id_itemtype) on delete restrict on update restrict;
create index ix_invoice_details_id_itemtype on invoice_details (id_itemtype);

alter table invoice_details add constraint fk_invoice_details_id_lot foreign key (id_lot) references lots (id_lot) on delete restrict on update restrict;
create index ix_invoice_details_id_lot on invoice_details (id_lot);

alter table invoice_details add constraint fk_invoice_details_id_store foreign key (id_store) references stores (id_store) on delete restrict on update restrict;
create index ix_invoice_details_id_store on invoice_details (id_store);

alter table invoicesdetails_purities add constraint fk_invoicesdetails_purities_id_purity foreign key (id_purity) references purities (id_purity) on delete restrict on update restrict;
create index ix_invoicesdetails_purities_id_purity on invoicesdetails_purities (id_purity);

alter table invoicesdetails_purities add constraint fk_invoicesdetails_purities_id_invoicedetail foreign key (id_invoicedetail) references invoice_details (id_invoicedetail) on delete restrict on update restrict;
create index ix_invoicesdetails_purities_id_invoicedetail on invoicesdetails_purities (id_invoicedetail);

alter table item_types add constraint fk_item_types_id_providertype foreign key (id_providertype) references provider_type (id_providertype) on delete restrict on update restrict;
create index ix_item_types_id_providertype on item_types (id_providertype);

alter table item_types add constraint fk_item_types_id_unit foreign key (id_unit) references units (id_unit) on delete restrict on update restrict;
create index ix_item_types_id_unit on item_types (id_unit);

alter table lots add constraint fk_lots_id_farm foreign key (id_farm) references farms (id_farm) on delete restrict on update restrict;
create index ix_lots_id_farm on lots (id_farm);

alter table auth_permission_auth_role add constraint fk_auth_permission_auth_role_auth_permission foreign key (auth_permission_id) references auth_permission (id) on delete restrict on update restrict;
create index ix_auth_permission_auth_role_auth_permission on auth_permission_auth_role (auth_permission_id);

alter table auth_permission_auth_role add constraint fk_auth_permission_auth_role_auth_role foreign key (auth_role_id) references auth_role (id) on delete restrict on update restrict;
create index ix_auth_permission_auth_role_auth_role on auth_permission_auth_role (auth_role_id);

alter table providers add constraint fk_providers_id_providertype foreign key (id_providertype) references provider_type (id_providertype) on delete restrict on update restrict;
create index ix_providers_id_providertype on providers (id_providertype);

alter table auth_role_auth_group add constraint fk_auth_role_auth_group_auth_role foreign key (auth_role_id) references auth_role (id) on delete restrict on update restrict;
create index ix_auth_role_auth_group_auth_role on auth_role_auth_group (auth_role_id);

alter table auth_role_auth_group add constraint fk_auth_role_auth_group_auth_group foreign key (auth_group_id) references auth_group (id) on delete restrict on update restrict;
create index ix_auth_role_auth_group_auth_group on auth_role_auth_group (auth_group_id);

alter table auth_pin add constraint fk_auth_pin_auth_user_id foreign key (auth_user_id) references auth_user (id) on delete restrict on update restrict;

alter table auth_token add constraint fk_auth_token_auth_user_id foreign key (auth_user_id) references auth_user (id) on delete restrict on update restrict;
create index ix_auth_token_auth_user_id on auth_token (auth_user_id);

alter table user add constraint fk_user_auth_user_id foreign key (auth_user_id) references auth_user (id) on delete restrict on update restrict;


# --- !Downs

alter table auth_user_auth_role drop foreign key fk_auth_user_auth_role_auth_user;
drop index ix_auth_user_auth_role_auth_user on auth_user_auth_role;

alter table auth_user_auth_role drop foreign key fk_auth_user_auth_role_auth_role;
drop index ix_auth_user_auth_role_auth_role on auth_user_auth_role;

alter table auth_user_auth_group drop foreign key fk_auth_user_auth_group_auth_user;
drop index ix_auth_user_auth_group_auth_user on auth_user_auth_group;

alter table auth_user_auth_group drop foreign key fk_auth_user_auth_group_auth_group;
drop index ix_auth_user_auth_group_auth_group on auth_user_auth_group;

alter table invoices drop foreign key fk_invoices_id_provider;
drop index ix_invoices_id_provider on invoices;

alter table invoice_details drop foreign key fk_invoice_details_id_invoice;
drop index ix_invoice_details_id_invoice on invoice_details;

alter table invoice_details drop foreign key fk_invoice_details_id_itemtype;
drop index ix_invoice_details_id_itemtype on invoice_details;

alter table invoice_details drop foreign key fk_invoice_details_id_lot;
drop index ix_invoice_details_id_lot on invoice_details;

alter table invoice_details drop foreign key fk_invoice_details_id_store;
drop index ix_invoice_details_id_store on invoice_details;

alter table invoicesdetails_purities drop foreign key fk_invoicesdetails_purities_id_purity;
drop index ix_invoicesdetails_purities_id_purity on invoicesdetails_purities;

alter table invoicesdetails_purities drop foreign key fk_invoicesdetails_purities_id_invoicedetail;
drop index ix_invoicesdetails_purities_id_invoicedetail on invoicesdetails_purities;

alter table item_types drop foreign key fk_item_types_id_providertype;
drop index ix_item_types_id_providertype on item_types;

alter table item_types drop foreign key fk_item_types_id_unit;
drop index ix_item_types_id_unit on item_types;

alter table lots drop foreign key fk_lots_id_farm;
drop index ix_lots_id_farm on lots;

alter table auth_permission_auth_role drop foreign key fk_auth_permission_auth_role_auth_permission;
drop index ix_auth_permission_auth_role_auth_permission on auth_permission_auth_role;

alter table auth_permission_auth_role drop foreign key fk_auth_permission_auth_role_auth_role;
drop index ix_auth_permission_auth_role_auth_role on auth_permission_auth_role;

alter table providers drop foreign key fk_providers_id_providertype;
drop index ix_providers_id_providertype on providers;

alter table auth_role_auth_group drop foreign key fk_auth_role_auth_group_auth_role;
drop index ix_auth_role_auth_group_auth_role on auth_role_auth_group;

alter table auth_role_auth_group drop foreign key fk_auth_role_auth_group_auth_group;
drop index ix_auth_role_auth_group_auth_group on auth_role_auth_group;

alter table auth_pin drop foreign key fk_auth_pin_auth_user_id;

alter table auth_token drop foreign key fk_auth_token_auth_user_id;
drop index ix_auth_token_auth_user_id on auth_token;

alter table user drop foreign key fk_user_auth_user_id;

drop table if exists auth_user;

drop table if exists auth_user_auth_role;

drop table if exists auth_user_auth_group;

drop table if exists auth_client_credential;

drop table if exists config;

drop table if exists farms;

drop table if exists auth_group;

drop table if exists invoices;

drop table if exists invoice_details;

drop table if exists invoicesdetails_purities;

drop table if exists item_types;

drop table if exists lots;

drop table if exists auth_permission;

drop table if exists auth_permission_auth_role;

drop table if exists providers;

drop table if exists provider_type;

drop table if exists purities;

drop table if exists auth_role;

drop table if exists auth_role_auth_group;

drop table if exists auth_pin;

drop table if exists auth_token;

drop table if exists status;

drop table if exists stores;

drop table if exists units;

drop table if exists user;

