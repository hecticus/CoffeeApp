# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table auth_user (
  id                            bigint auto_increment not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  username                      varchar(50) not null,
  email                         varchar(50) not null,
  password                      text not null,
  last_login                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  deleted                       tinyint(1) default 0 not null,
  constraint uq_auth_user_username unique (username),
  constraint uq_auth_user_email unique (email),
  constraint pk_auth_user primary key (id)
);

create table auth_user_auth_role (
  auth_user_id                  bigint not null,
  auth_role_id                  bigint not null,
  constraint pk_auth_user_auth_role primary key (auth_user_id,auth_role_id)
);

create table auth_user_auth_group (
  auth_user_id                  bigint not null,
  auth_group_id                 bigint not null,
  constraint pk_auth_user_auth_group primary key (auth_user_id,auth_group_id)
);

create table auth_client_credential (
  id                            bigint auto_increment not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  client_id                     varchar(50) not null,
  client_secret                 varchar(100),
  name                          varchar(100) not null,
  icon                          text,
  home_page_url                 text,
  privacy_policy_url            text,
  auth_callback_uri             text,
  description                   text,
  constraint uq_auth_client_credential_client_id unique (client_id),
  constraint pk_auth_client_credential primary key (id)
);

create table config (
  config                        bigint(50) auto_increment not null,
  name_config                   varchar(20) not null,
  config_key                    varchar(50) not null,
  value                         varchar(255) not null,
  description                   text,
  constraint uq_config_name_config unique (name_config),
  constraint pk_config primary key (config)
);

create table farms (
  id                            bigint auto_increment not null,
  name_farm                     varchar(20) not null,
  status_farm_id                bigint,
  deleted                       tinyint(1) default 0 not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
  constraint uq_farms_name_farm unique (name_farm),
  constraint pk_farms primary key (id)
);

create table auth_group (
  id                            bigint auto_increment not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  name                          varchar(50) not null,
  description                   varchar(255),
  constraint uq_auth_group_name unique (name),
  constraint pk_auth_group primary key (id)
);

create table invoices (
  id                            bigint auto_increment not null,
  provider_id                   bigint not null,
  status_invoice_id             bigint,
  deleted                       tinyint(1) default 0 not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
  closeddate_invoice            TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
  constraint pk_invoices primary key (id)
);

create table invoice_details (
  id                            bigint auto_increment not null,
  invoice_id                    bigint not null,
  item_type_id                  bigint not null,
  lot_id                        bigint not null,
  store_id                      bigint not null,
  price_item_type_by_lot        decimal(38) not null,
  cost_item_type                decimal(38) not null,
  amount_invoice_detail         decimal(38) not null,
  name_received                 varchar(100) not null,
  name_delivered                varchar(100) not null,
  note                          text,
  status_invoice_detail_id      bigint,
  deleted                       tinyint(1) default 0 not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
  constraint pk_invoice_details primary key (id)
);

create table invoicesdetails_purities (
  id                            bigint auto_increment not null,
  purity_id                     bigint not null,
  invoice_detail_id             bigint not null,
  value_rate_invoice_detail_purity integer not null,
  discount_rate_purity          integer not null,
  total_discount_purity         integer not null,
  deleted                       tinyint(1) default 0 not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
  constraint pk_invoicesdetails_purities primary key (id)
);

create table item_types (
  id                            bigint auto_increment not null,
  provider_type_id              bigint not null,
  unit_id                       bigint not null,
  name_item_type                varchar(255) not null,
  cost_item_type                decimal(38) not null,
  deleted                       tinyint(1) default 0 not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
  constraint uq_item_types_name_item_type unique (name_item_type),
  constraint pk_item_types primary key (id)
);

create table lots (
  id                            bigint auto_increment not null,
  farm_id                       bigint not null,
  name_lot                      varchar(50) not null,
  area_lot                      varchar(200) not null,
  heigh_lot                     double not null,
  price_lot                     decimal(38) not null,
  status_lot_id                 bigint,
  deleted                       tinyint(1) default 0 not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
  constraint pk_lots primary key (id)
);

create table auth_permission (
  id                            bigint auto_increment not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  name                          varchar(100) not null,
  description                   text,
  constraint uq_auth_permission_name unique (name),
  constraint pk_auth_permission primary key (id)
);

create table auth_permission_auth_role (
  auth_permission_id            bigint not null,
  auth_role_id                  bigint not null,
  constraint pk_auth_permission_auth_role primary key (auth_permission_id,auth_role_id)
);

create table providers (
  id                            bigint auto_increment not null,
  provider_type_id              bigint not null,
  nit_provider                  varchar(255) not null,
  name_provider                 varchar(60) not null,
  address_provider              varchar(60) not null,
  number_provider               varchar(20) not null,
  email_provider                varchar(255) not null,
  photo_provider                varchar(255),
  contact_name_provider         varchar(50) not null,
  status_provider_id            bigint,
  deleted                       tinyint(1) default 0 not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
  constraint uq_providers_nit_provider unique (nit_provider),
  constraint uq_providers_contact_name_provider unique (contact_name_provider),
  constraint pk_providers primary key (id)
);

create table provider_type (
  id                            bigint auto_increment not null,
  name_provider_type            varchar(60) not null,
  deleted                       tinyint(1) default 0 not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
  constraint uq_provider_type_name_provider_type unique (name_provider_type),
  constraint pk_provider_type primary key (id)
);

create table purities (
  id                            bigint auto_increment not null,
  name_purity                   varchar(20) not null,
  discount_rate_purity          integer not null,
  deleted                       tinyint(1) default 0 not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
  constraint uq_purities_name_purity unique (name_purity),
  constraint pk_purities primary key (id)
);

create table auth_role (
  id                            bigint auto_increment not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  name                          varchar(50) not null,
  description                   varchar(255),
  constraint uq_auth_role_name unique (name),
  constraint pk_auth_role primary key (id)
);

create table auth_role_auth_group (
  auth_role_id                  bigint not null,
  auth_group_id                 bigint not null,
  constraint pk_auth_role_auth_group primary key (auth_role_id,auth_group_id)
);

create table auth_pin (
  id                            bigint auto_increment not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  auth_user_id                  bigint not null,
  pin                           varchar(50) not null,
  tries                         integer,
  expiration                    datetime,
  constraint uq_auth_pin_auth_user_id unique (auth_user_id),
  constraint pk_auth_pin primary key (id)
);

create table auth_token (
  id                            bigint auto_increment not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  token                         text not null,
  auth_user_id                  bigint not null,
  constraint pk_auth_token primary key (id)
);

create table status (
  dtype                         varchar(50) not null,
  id                            bigint auto_increment not null,
  name                          varchar(20) not null,
  description                   text,
  constraint uq_status_name unique (name),
  constraint pk_status primary key (id)
);

create table stores (
  id                            bigint auto_increment not null,
  name_store                    varchar(50) not null,
  status_store_id               bigint,
  deleted                       tinyint(1) default 0 not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
  constraint uq_stores_name_store unique (name_store),
  constraint pk_stores primary key (id)
);

create table units (
  id                            bigint auto_increment not null,
  name_unit                     varchar(255) not null,
  deleted                       tinyint(1) default 0 not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
  constraint uq_units_name_unit unique (name_unit),
  constraint pk_units primary key (id)
);

create table user (
  id                            bigint auto_increment not null,
  auth_user_id                  bigint not null,
  first_name                    varchar(50) not null,
  last_name                     varchar(50) not null,
  description                   text,
  latitude                      double,
  longitude                     double,
  address                       varchar(200),
  phone                         varchar(20),
  phone2                        varchar(20),
  email2                        varchar(50),
  deleted                       tinyint(1) default 0 not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
  last_login                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
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

alter table farms add constraint fk_farms_status_farm_id foreign key (status_farm_id) references status (id) on delete restrict on update restrict;
create index ix_farms_status_farm_id on farms (status_farm_id);

alter table invoices add constraint fk_invoices_provider_id foreign key (provider_id) references providers (id) on delete restrict on update restrict;
create index ix_invoices_provider_id on invoices (provider_id);

alter table invoices add constraint fk_invoices_status_invoice_id foreign key (status_invoice_id) references status (id) on delete restrict on update restrict;
create index ix_invoices_status_invoice_id on invoices (status_invoice_id);

alter table invoice_details add constraint fk_invoice_details_invoice_id foreign key (invoice_id) references invoices (id) on delete restrict on update restrict;
create index ix_invoice_details_invoice_id on invoice_details (invoice_id);

alter table invoice_details add constraint fk_invoice_details_item_type_id foreign key (item_type_id) references item_types (id) on delete restrict on update restrict;
create index ix_invoice_details_item_type_id on invoice_details (item_type_id);

alter table invoice_details add constraint fk_invoice_details_lot_id foreign key (lot_id) references lots (id) on delete restrict on update restrict;
create index ix_invoice_details_lot_id on invoice_details (lot_id);

alter table invoice_details add constraint fk_invoice_details_store_id foreign key (store_id) references stores (id) on delete restrict on update restrict;
create index ix_invoice_details_store_id on invoice_details (store_id);

alter table invoice_details add constraint fk_invoice_details_status_invoice_detail_id foreign key (status_invoice_detail_id) references status (id) on delete restrict on update restrict;
create index ix_invoice_details_status_invoice_detail_id on invoice_details (status_invoice_detail_id);

alter table invoicesdetails_purities add constraint fk_invoicesdetails_purities_purity_id foreign key (purity_id) references purities (id) on delete restrict on update restrict;
create index ix_invoicesdetails_purities_purity_id on invoicesdetails_purities (purity_id);

alter table invoicesdetails_purities add constraint fk_invoicesdetails_purities_invoice_detail_id foreign key (invoice_detail_id) references invoice_details (id) on delete restrict on update restrict;
create index ix_invoicesdetails_purities_invoice_detail_id on invoicesdetails_purities (invoice_detail_id);

alter table item_types add constraint fk_item_types_provider_type_id foreign key (provider_type_id) references provider_type (id) on delete restrict on update restrict;
create index ix_item_types_provider_type_id on item_types (provider_type_id);

alter table item_types add constraint fk_item_types_unit_id foreign key (unit_id) references units (id) on delete restrict on update restrict;
create index ix_item_types_unit_id on item_types (unit_id);

alter table lots add constraint fk_lots_farm_id foreign key (farm_id) references farms (id) on delete restrict on update restrict;
create index ix_lots_farm_id on lots (farm_id);

alter table lots add constraint fk_lots_status_lot_id foreign key (status_lot_id) references status (id) on delete restrict on update restrict;
create index ix_lots_status_lot_id on lots (status_lot_id);

alter table auth_permission_auth_role add constraint fk_auth_permission_auth_role_auth_permission foreign key (auth_permission_id) references auth_permission (id) on delete restrict on update restrict;
create index ix_auth_permission_auth_role_auth_permission on auth_permission_auth_role (auth_permission_id);

alter table auth_permission_auth_role add constraint fk_auth_permission_auth_role_auth_role foreign key (auth_role_id) references auth_role (id) on delete restrict on update restrict;
create index ix_auth_permission_auth_role_auth_role on auth_permission_auth_role (auth_role_id);

alter table providers add constraint fk_providers_provider_type_id foreign key (provider_type_id) references provider_type (id) on delete restrict on update restrict;
create index ix_providers_provider_type_id on providers (provider_type_id);

alter table providers add constraint fk_providers_status_provider_id foreign key (status_provider_id) references status (id) on delete restrict on update restrict;
create index ix_providers_status_provider_id on providers (status_provider_id);

alter table auth_role_auth_group add constraint fk_auth_role_auth_group_auth_role foreign key (auth_role_id) references auth_role (id) on delete restrict on update restrict;
create index ix_auth_role_auth_group_auth_role on auth_role_auth_group (auth_role_id);

alter table auth_role_auth_group add constraint fk_auth_role_auth_group_auth_group foreign key (auth_group_id) references auth_group (id) on delete restrict on update restrict;
create index ix_auth_role_auth_group_auth_group on auth_role_auth_group (auth_group_id);

alter table auth_pin add constraint fk_auth_pin_auth_user_id foreign key (auth_user_id) references auth_user (id) on delete restrict on update restrict;

alter table auth_token add constraint fk_auth_token_auth_user_id foreign key (auth_user_id) references auth_user (id) on delete restrict on update restrict;
create index ix_auth_token_auth_user_id on auth_token (auth_user_id);

alter table stores add constraint fk_stores_status_store_id foreign key (status_store_id) references status (id) on delete restrict on update restrict;
create index ix_stores_status_store_id on stores (status_store_id);

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

alter table farms drop foreign key fk_farms_status_farm_id;
drop index ix_farms_status_farm_id on farms;

alter table invoices drop foreign key fk_invoices_provider_id;
drop index ix_invoices_provider_id on invoices;

alter table invoices drop foreign key fk_invoices_status_invoice_id;
drop index ix_invoices_status_invoice_id on invoices;

alter table invoice_details drop foreign key fk_invoice_details_invoice_id;
drop index ix_invoice_details_invoice_id on invoice_details;

alter table invoice_details drop foreign key fk_invoice_details_item_type_id;
drop index ix_invoice_details_item_type_id on invoice_details;

alter table invoice_details drop foreign key fk_invoice_details_lot_id;
drop index ix_invoice_details_lot_id on invoice_details;

alter table invoice_details drop foreign key fk_invoice_details_store_id;
drop index ix_invoice_details_store_id on invoice_details;

alter table invoice_details drop foreign key fk_invoice_details_status_invoice_detail_id;
drop index ix_invoice_details_status_invoice_detail_id on invoice_details;

alter table invoicesdetails_purities drop foreign key fk_invoicesdetails_purities_purity_id;
drop index ix_invoicesdetails_purities_purity_id on invoicesdetails_purities;

alter table invoicesdetails_purities drop foreign key fk_invoicesdetails_purities_invoice_detail_id;
drop index ix_invoicesdetails_purities_invoice_detail_id on invoicesdetails_purities;

alter table item_types drop foreign key fk_item_types_provider_type_id;
drop index ix_item_types_provider_type_id on item_types;

alter table item_types drop foreign key fk_item_types_unit_id;
drop index ix_item_types_unit_id on item_types;

alter table lots drop foreign key fk_lots_farm_id;
drop index ix_lots_farm_id on lots;

alter table lots drop foreign key fk_lots_status_lot_id;
drop index ix_lots_status_lot_id on lots;

alter table auth_permission_auth_role drop foreign key fk_auth_permission_auth_role_auth_permission;
drop index ix_auth_permission_auth_role_auth_permission on auth_permission_auth_role;

alter table auth_permission_auth_role drop foreign key fk_auth_permission_auth_role_auth_role;
drop index ix_auth_permission_auth_role_auth_role on auth_permission_auth_role;

alter table providers drop foreign key fk_providers_provider_type_id;
drop index ix_providers_provider_type_id on providers;

alter table providers drop foreign key fk_providers_status_provider_id;
drop index ix_providers_status_provider_id on providers;

alter table auth_role_auth_group drop foreign key fk_auth_role_auth_group_auth_role;
drop index ix_auth_role_auth_group_auth_role on auth_role_auth_group;

alter table auth_role_auth_group drop foreign key fk_auth_role_auth_group_auth_group;
drop index ix_auth_role_auth_group_auth_group on auth_role_auth_group;

alter table auth_pin drop foreign key fk_auth_pin_auth_user_id;

alter table auth_token drop foreign key fk_auth_token_auth_user_id;
drop index ix_auth_token_auth_user_id on auth_token;

alter table stores drop foreign key fk_stores_status_store_id;
drop index ix_stores_status_store_id on stores;

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

