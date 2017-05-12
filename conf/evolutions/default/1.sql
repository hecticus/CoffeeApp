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

create table invoices (
  id_invoice                    bigint auto_increment not null,
  status_delete                 integer not null,
  id_provider                   bigint,
  status_invoice                integer not null,
  start_date_invoice            date not null,
  closed_date_invoice           date not null,
  total_invoice                 double,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT 0 not null,
  constraint pk_invoices primary key (id_invoice)
);

create table invoices_details (
  id_invoice_detail             bigint auto_increment not null,
  status_delete                 integer not null,
  id_invoice                    bigint,
  id_itemtype                   bigint,
  id_lot                        bigint,
  id_store                      bigint,
  cost_item_type                decimal(10,2) not null,
  start_date_invoice_detail     datetime not null,
  amount_invoice_detail         integer,
  freight_invoice_detail        tinyint(1) default 0,
  note_invoice_detail           varchar(255),
  name_received_invoice_detail  varchar(255) not null,
  name_delivered_invoice_detail varchar(255) not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT 0 not null,
  constraint pk_invoices_details primary key (id_invoice_detail)
);

create table invoicesdetails_purities (
  id_invoice_detail_purity      bigint auto_increment not null,
  status_delete                 integer not null,
  id_purity                     bigint,
  value_rate_invoice_detail_purity integer not null,
  total_discount_purity         integer not null,
  id_invoicedetail              bigint,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT 0 not null,
  constraint pk_invoicesdetails_purities primary key (id_invoice_detail_purity)
);

create table item_types (
  id_item_type                  bigint auto_increment not null,
  status_delete                 integer not null,
  name_item_type                varchar(255) not null,
  cost_item_type                decimal(10,2) not null,
  status_item_type              integer not null,
  id_providertype               bigint,
  id_unit                       bigint,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT 0 not null,
  constraint pk_item_types primary key (id_item_type)
);

create table lots (
  id_lot                        bigint auto_increment not null,
  status_delete                 integer not null,
  name_lot                      varchar(255) not null,
  area_lot                      varchar(255) not null,
  farm_lot                      varchar(255) not null,
  heigh_lot                     double not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT 0 not null,
  constraint pk_lots primary key (id_lot)
);

create table providers (
  id_provider                   bigint auto_increment not null,
  status_delete                 integer not null,
  identification_doc_provider   varchar(255) not null,
  full_name_provider            varchar(255) not null,
  address_provider              varchar(255) not null,
  phone_number_provider         varchar(255) not null,
  email_provider                varchar(255),
  photo_provider                varchar(255),
  id_providertype               bigint,
  contact_name_provider         varchar(255) not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT 0 not null,
  constraint pk_providers primary key (id_provider)
);

create table provider_types (
  id_provider_type              bigint auto_increment not null,
  status_delete                 integer not null,
  name_provider_type            varchar(255) not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT 0 not null,
  constraint pk_provider_types primary key (id_provider_type)
);

create table purities (
  id_purity                     bigint auto_increment not null,
  status_delete                 integer not null,
  name_purity                   varchar(255) not null,
  status_purity                 integer not null,
  discount_rate_purity          integer not null,
  created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  updated_at                    TIMESTAMP DEFAULT 0 not null,
  constraint pk_purities primary key (id_purity)
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

alter table invoices add constraint fk_invoices_id_provider foreign key (id_provider) references providers (id_provider) on delete restrict on update restrict;
create index ix_invoices_id_provider on invoices (id_provider);

alter table invoices_details add constraint fk_invoices_details_id_invoice foreign key (id_invoice) references invoices (id_invoice) on delete restrict on update restrict;
create index ix_invoices_details_id_invoice on invoices_details (id_invoice);

alter table invoices_details add constraint fk_invoices_details_id_itemtype foreign key (id_itemtype) references item_types (id_item_type) on delete restrict on update restrict;
create index ix_invoices_details_id_itemtype on invoices_details (id_itemtype);

alter table invoices_details add constraint fk_invoices_details_id_lot foreign key (id_lot) references lots (id_lot) on delete restrict on update restrict;
create index ix_invoices_details_id_lot on invoices_details (id_lot);

alter table invoices_details add constraint fk_invoices_details_id_store foreign key (id_store) references stores (id_store) on delete restrict on update restrict;
create index ix_invoices_details_id_store on invoices_details (id_store);

alter table invoicesdetails_purities add constraint fk_invoicesdetails_purities_id_purity foreign key (id_purity) references purities (id_purity) on delete restrict on update restrict;
create index ix_invoicesdetails_purities_id_purity on invoicesdetails_purities (id_purity);

alter table invoicesdetails_purities add constraint fk_invoicesdetails_purities_id_invoicedetail foreign key (id_invoicedetail) references invoices_details (id_invoice_detail) on delete restrict on update restrict;
create index ix_invoicesdetails_purities_id_invoicedetail on invoicesdetails_purities (id_invoicedetail);

alter table item_types add constraint fk_item_types_id_providertype foreign key (id_providertype) references provider_types (id_provider_type) on delete restrict on update restrict;
create index ix_item_types_id_providertype on item_types (id_providertype);

alter table item_types add constraint fk_item_types_id_unit foreign key (id_unit) references units (id_unit) on delete restrict on update restrict;
create index ix_item_types_id_unit on item_types (id_unit);

alter table providers add constraint fk_providers_id_providertype foreign key (id_providertype) references provider_types (id_provider_type) on delete restrict on update restrict;
create index ix_providers_id_providertype on providers (id_providertype);


# --- !Downs

alter table invoices drop foreign key fk_invoices_id_provider;
drop index ix_invoices_id_provider on invoices;

alter table invoices_details drop foreign key fk_invoices_details_id_invoice;
drop index ix_invoices_details_id_invoice on invoices_details;

alter table invoices_details drop foreign key fk_invoices_details_id_itemtype;
drop index ix_invoices_details_id_itemtype on invoices_details;

alter table invoices_details drop foreign key fk_invoices_details_id_lot;
drop index ix_invoices_details_id_lot on invoices_details;

alter table invoices_details drop foreign key fk_invoices_details_id_store;
drop index ix_invoices_details_id_store on invoices_details;

alter table invoicesdetails_purities drop foreign key fk_invoicesdetails_purities_id_purity;
drop index ix_invoicesdetails_purities_id_purity on invoicesdetails_purities;

alter table invoicesdetails_purities drop foreign key fk_invoicesdetails_purities_id_invoicedetail;
drop index ix_invoicesdetails_purities_id_invoicedetail on invoicesdetails_purities;

alter table item_types drop foreign key fk_item_types_id_providertype;
drop index ix_item_types_id_providertype on item_types;

alter table item_types drop foreign key fk_item_types_id_unit;
drop index ix_item_types_id_unit on item_types;

alter table providers drop foreign key fk_providers_id_providertype;
drop index ix_providers_id_providertype on providers;

drop table if exists config;

drop table if exists invoices;

drop table if exists invoices_details;

drop table if exists invoicesdetails_purities;

drop table if exists item_types;

drop table if exists lots;

drop table if exists providers;

drop table if exists provider_types;

drop table if exists purities;

drop table if exists stores;

drop table if exists units;

