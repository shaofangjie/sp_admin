create table admin (
  id                            bigint auto_increment not null,
  user_name                     varchar(100) not null,
  password                      varchar(255) not null,
  nick_name                     varchar(100),
  role_id                       bigint,
  enabled                       tinyint(1) default 0 not null,
  is_lock                       tinyint(1) default 0 not null,
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_updated                  datetime(6) not null,
  constraint uq_admin_user_name unique (user_name),
  constraint pk_admin primary key (id)
);

create table admin_resources (
  id                            bigint auto_increment not null,
  source_pid                    bigint,
  source_type                   integer default 0 not null,
  iconfont                      varchar(100),
  source_name                   varchar(100) not null,
  source_url                    varchar(255),
  source_function               varchar(255),
  enabled                       tinyint(1) default 0 not null,
  source_order                  integer default 0 not null,
  is_lock                       tinyint(1) default 0 not null,
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_updated                  datetime(6) not null,
  constraint pk_admin_resources primary key (id)
);

create table admin_role_resources (
  resource_id                   bigint not null,
  role_id                       bigint not null,
  constraint pk_admin_role_resources primary key (resource_id,role_id)
);

create table admin_role (
  id                            bigint auto_increment not null,
  role_name                     varchar(100) not null,
  is_lock                       tinyint(1) default 0 not null,
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_updated                  datetime(6) not null,
  constraint uq_admin_role_role_name unique (role_name),
  constraint pk_admin_role primary key (id)
);

create index ix_admin_user_name on admin (user_name);
create index ix_admin_when_created on admin (when_created);
create index ix_admin_when_updated on admin (when_updated);
create index ix_admin_resources_when_created on admin_resources (when_created);
create index ix_admin_resources_when_updated on admin_resources (when_updated);
create index ix_admin_role_when_created on admin_role (when_created);
create index ix_admin_role_when_updated on admin_role (when_updated);
create index ix_admin_role_id on admin (role_id);
alter table admin add constraint fk_admin_role_id foreign key (role_id) references admin_role (id) on delete restrict on update restrict;

create index ix_admin_resources_source_pid on admin_resources (source_pid);
alter table admin_resources add constraint fk_admin_resources_source_pid foreign key (source_pid) references admin_resources (id) on delete restrict on update restrict;

create index ix_admin_role_resources_admin_resources on admin_role_resources (resource_id);
alter table admin_role_resources add constraint fk_admin_role_resources_admin_resources foreign key (resource_id) references admin_resources (id) on delete restrict on update restrict;

create index ix_admin_role_resources_admin_role on admin_role_resources (role_id);
alter table admin_role_resources add constraint fk_admin_role_resources_admin_role foreign key (role_id) references admin_role (id) on delete restrict on update restrict;

