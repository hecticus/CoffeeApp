# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table config (
  id_config                     bigint auto_increment not null,
  config_key                    varchar(50) not null,
  value                         varchar(255) not null,
  description                   varchar(255),
  constraint pk_config primary key (id_config)
);

create table farms (
  id_farm                       bigint auto_increment not null,
  status_delete                 integer not null,
  name_farm                     varchar(255) not null,
  status_farm                   integer not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT 0 not null,
  constraint pk_farms primary key (id_farm)
);

create table invoices (
  id_invoice                    bigint auto_increment not null,
  status_delete                 integer not null,
  id_provider                   bigint not null,
  status_invoice                integer not null,
  duedate_invoice               date not null,
  closeddate_invoice            date not null,
  total_invoice                 double,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT 0 not null,
  constraint pk_invoices primary key (id_invoice)
);

create table invoice_details (
  id_invoicedetail              bigint auto_increment not null,
  status_delete                 integer not null,
  id_invoice                    bigint not null,
  id_itemtype                   bigint not null,
  id_lot                        bigint,
  id_store                      bigint,
  cost_itemtype                 decimal(10,2) not null,
  duedate_invoicedetail         date not null,
  amount_invoicedetail          integer,
  isfreight_invoicedetail       tinyint(1) default 0,
  note_invoicedetail            varchar(255),
  namereceived_invoicedetail    varchar(255) not null,
  namedelivered_invoicedetail   varchar(255) not null,
  status_invoicedetail          integer not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT 0 not null,
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
  updated_at                    TIMESTAMP DEFAULT 0 not null,
  constraint pk_invoicesdetails_purities primary key (id_invoicedetail_purity)
);

create table item_types (
  id_itemtype                   bigint auto_increment not null,
  status_delete                 integer not null,
  name_itemtype                 varchar(255) not null,
  cost_itemtype                 decimal(10,2) not null,
  status_itemtype               integer not null,
  id_providertype               bigint not null,
  id_unit                       bigint not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT 0 not null,
  constraint pk_item_types primary key (id_itemtype)
);

create table lots (
  id_lot                        bigint auto_increment not null,
  status_delete                 integer not null,
  name_lot                      varchar(255) not null,
  area_lot                      varchar(255) not null,
  heigh_lot                     double not null,
  status_lot                    integer not null,
  id_farm                       bigint not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT 0 not null,
  constraint pk_lots primary key (id_lot)
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
  id_providertype               bigint not null,
  contactname_provider          varchar(255) not null,
  status_provider               integer not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT 0 not null,
  constraint pk_providers primary key (id_provider)
);

create table provider_type (
  id_providertype               bigint auto_increment not null,
  status_delete                 integer not null,
  name_providertype             varchar(255) not null,
  status_providertype           integer not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT 0 not null,
  constraint pk_provider_type primary key (id_providertype)
);

create table purities (
  id_purity                     bigint auto_increment not null,
  status_delete                 integer not null,
  name_purity                   varchar(255) not null,
  status_purity                 integer not null,
  discountrate_purity           integer not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT 0 not null,
  constraint pk_purities primary key (id_purity)
);

create table role (
  id_role                       bigint auto_increment not null,
  status_delete                 integer not null,
  name                          varchar(100) not null,
  description                   varchar(255) not null,
  status_role_id_status         bigint,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT 0 not null,
  constraint pk_role primary key (id_role)
);

create table route (
  id_security_route             bigint auto_increment not null,
  status_delete                 integer not null,
  name                          varchar(50) not null,
  description                   varchar(255) not null,
  route_type                    integer,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT 0 not null,
  constraint pk_route primary key (id_security_route)
);

create table route_tag (
  route_id_security_route       bigint not null,
  tag_id_security_tag           bigint not null,
  constraint pk_route_tag primary key (route_id_security_route,tag_id_security_tag)
);

create table tag (
  id_security_tag               bigint auto_increment not null,
  status_delete                 integer not null,
  name                          varchar(50) not null,
  description                   varchar(255) not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT 0 not null,
  constraint pk_tag primary key (id_security_tag)
);

create table tag_role (
  tag_id_security_tag           bigint not null,
  role_id_role                  bigint not null,
  constraint pk_tag_role primary key (tag_id_security_tag,role_id_role)
);

create table status (
  status_type                   varchar(31) not null,
  id_status                     bigint auto_increment not null,
  status_delete                 integer not null,
  name                          varchar(100),
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT 0 not null,
  constraint pk_status primary key (id_status)
);

create table stores (
  id_store                      bigint auto_increment not null,
  status_delete                 integer not null,
  name_store                    varchar(255) not null,
  status_store                  integer not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT 0 not null,
  constraint pk_stores primary key (id_store)
);

create table units (
  id_unit                       bigint auto_increment not null,
  status_delete                 integer not null,
  name_unit                     varchar(255) not null,
  status_unit                   integer not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT 0 not null,
  constraint pk_units primary key (id_unit)
);

create table user (
  id_user                       bigint auto_increment not null,
  status_delete                 integer not null,
  name                          varchar(100),
  password                      varchar(100),
  first_name                    varchar(100) not null,
  last_name                     varchar(100) not null,
  email                         varchar(255) not null,
  email_validated               smallint,
  archived                      tinyint default 0 not null,
  last_login                    datetime,
  token                         varchar(255),
  role_id_role                  bigint,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT 0 not null,
  constraint uq_user_email unique (email),
  constraint pk_user primary key (id_user)
);

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

alter table providers add constraint fk_providers_id_providertype foreign key (id_providertype) references provider_type (id_providertype) on delete restrict on update restrict;
create index ix_providers_id_providertype on providers (id_providertype);

alter table role add constraint fk_role_status_role_id_status foreign key (status_role_id_status) references status (id_status) on delete restrict on update restrict;
create index ix_role_status_role_id_status on role (status_role_id_status);

alter table route_tag add constraint fk_route_tag_route foreign key (route_id_security_route) references route (id_security_route) on delete restrict on update restrict;
create index ix_route_tag_route on route_tag (route_id_security_route);

alter table route_tag add constraint fk_route_tag_tag foreign key (tag_id_security_tag) references tag (id_security_tag) on delete restrict on update restrict;
create index ix_route_tag_tag on route_tag (tag_id_security_tag);

alter table tag_role add constraint fk_tag_role_tag foreign key (tag_id_security_tag) references tag (id_security_tag) on delete restrict on update restrict;
create index ix_tag_role_tag on tag_role (tag_id_security_tag);

alter table tag_role add constraint fk_tag_role_role foreign key (role_id_role) references role (id_role) on delete restrict on update restrict;
create index ix_tag_role_role on tag_role (role_id_role);

alter table user add constraint fk_user_role_id_role foreign key (role_id_role) references role (id_role) on delete restrict on update restrict;
create index ix_user_role_id_role on user (role_id_role);


# --- !Downs

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

alter table providers drop foreign key fk_providers_id_providertype;
drop index ix_providers_id_providertype on providers;

alter table role drop foreign key fk_role_status_role_id_status;
drop index ix_role_status_role_id_status on role;

alter table route_tag drop foreign key fk_route_tag_route;
drop index ix_route_tag_route on route_tag;

alter table route_tag drop foreign key fk_route_tag_tag;
drop index ix_route_tag_tag on route_tag;

alter table tag_role drop foreign key fk_tag_role_tag;
drop index ix_tag_role_tag on tag_role;

alter table tag_role drop foreign key fk_tag_role_role;
drop index ix_tag_role_role on tag_role;

alter table user drop foreign key fk_user_role_id_role;
drop index ix_user_role_id_role on user;

drop table if exists config;

drop table if exists farms;

drop table if exists invoices;

drop table if exists invoice_details;

drop table if exists invoicesdetails_purities;

drop table if exists item_types;

drop table if exists lots;

drop table if exists providers;

drop table if exists provider_type;

drop table if exists purities;

drop table if exists role;

drop table if exists route;

drop table if exists route_tag;

drop table if exists tag;

drop table if exists tag_role;

drop table if exists status;

drop table if exists stores;

drop table if exists units;

drop table if exists user;

