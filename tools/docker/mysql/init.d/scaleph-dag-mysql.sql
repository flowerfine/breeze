create database if not exists scaleph default character set utf8mb4 collate utf8mb4_unicode_ci;
use scaleph;

drop table if exists dag_config;
create table dag_config
(
    id             bigint      not null auto_increment comment '自增主键',
    type           varchar(16) not null comment 'DAG 类型',
    name           varchar(128) comment 'DAG名称',
    config_id      varchar(64) not null comment 'DAG ID',
    dag_meta       varchar(128) comment 'DAG元信息',
    dag_attrs      mediumtext comment 'DAG属性',
    intput_options text comment '输入参数声明',
    output_options text comment '输出参数声明',
    version        int         not null default 0 comment '版本号',
    remark         varchar(255) comment '备注',
    creator        varchar(32) comment '创建人',
    create_time    timestamp            default current_timestamp comment '创建时间',
    editor         varchar(32) comment '修改人',
    update_time    timestamp            default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id)
) engine = innodb comment 'DAG 配置';

INSERT INTO `dag_config` (`id`, `type`, `name`, `config_id`, `dag_meta`, `dag_attrs`, `remark`, `creator`, `editor`)
VALUES (1, 'SeaTunnel', 'e_commerce', 'bsag8e409f8c81a64edc8f0e1b27d6a010cb', NULL, NULL, NULL, 'sys', 'sys');
INSERT INTO `dag_config` (`id`, `type`, `name`, `config_id`, `dag_meta`, `dag_attrs`, `remark`, `creator`, `editor`)
VALUES (2, 'SeaTunnel', 'fake', 'ewykdb10bd1e437346369e027a437473d483', NULL, NULL, NULL, 'sys', 'sys');
INSERT INTO `dag_config`(`id`, `type`, `name`, `config_id`, `dag_meta`, `dag_attrs`, `remark`, `creator`, `editor`)
VALUES (3, 'Flink-CDC', 'flink-cdc-example', 'nlly3ab39bb296a34c5888dd6509ffe588e4', NULL, NULL, NULL, 'sys', 'sys');
INSERT INTO `dag_config`(`id`, `type`, `name`, `config_id`, `dag_meta`, `dag_attrs`, `remark`, `creator`, `editor`)
VALUES (4, 'WorkFlow', 'FlinkSessionClusterStatusSyncJob', 'rnsp52fdd5edd77044a9acc0c2f24c42d760', NULL, NULL, NULL, 'sys', 'sys');
INSERT INTO `dag_config`(`id`, `type`, `name`, `config_id`, `dag_meta`, `dag_attrs`, `remark`, `creator`, `editor`)
VALUES (5, 'WorkFlow', 'FlinkJobStatusSyncJob', 'kvqfebc60efa8def410ebfe30f70fd8f1768', NULL, NULL, NULL, 'sys', 'sys');
INSERT INTO `dag_config`(`id`, `type`, `name`, `config_id`, `dag_meta`, `dag_attrs`, `remark`, `creator`, `editor`)
VALUES (6, 'WorkFlow', 'DorisOperatorInstanceStatusSyncJob', 'kepa00f4fdb5e8794cbb931067244caf5ef2', NULL, NULL, NULL, 'sys', 'sys');
INSERT INTO `dag_config`(`id`, `type`, `name`, `config_id`, `dag_meta`, `dag_attrs`, `remark`, `creator`, `editor`)
VALUES (7, 'WorkFlow', 'Demo', 'fssxbe099903bf174c11bf64b0d486383784', NULL, NULL, NULL, 'sys', 'sys');

drop table if exists dag_config_history;
create table dag_config_history
(
    id             bigint      not null auto_increment comment '自增主键',
    dag_config_id  bigint      not null comment 'DAG 配置ID',
    type           varchar(16) not null comment 'DAG 类型',
    name           varchar(128) comment 'DAG名称',
    config_id      varchar(64) not null comment 'DAG ID',
    dag_meta       varchar(128) comment 'DAG元信息',
    dag_attrs      mediumtext comment 'DAG属性',
    intput_options text comment '输入参数声明',
    output_options text comment '输出参数声明',
    version        int         not null default 0 comment '版本号',
    remark         varchar(255) comment '备注',
    creator        varchar(32) comment '创建人',
    create_time    timestamp            default current_timestamp comment '创建时间',
    editor         varchar(32) comment '修改人',
    update_time    timestamp            default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id),
    key            idx_dag_config (`dag_config_id`)
) engine = innodb comment 'DAG 配置历史。使用 javers 管理';

drop table if exists dag_config_step;
create table dag_config_step
(
    id          bigint      not null auto_increment comment '自增主键',
    dag_id      bigint      not null comment 'DAG id',
    step_id     varchar(36) not null comment '步骤id',
    step_name   varchar(128) comment '步骤名称',
    position_x  int         not null comment 'x坐标',
    position_y  int         not null comment 'y坐标',
    shape       varchar(64),
    style       text,
    step_meta   varchar(128) comment '步骤元信息',
    step_attrs  mediumtext comment '步骤属性',
    creator     varchar(32) comment '创建人',
    create_time timestamp default current_timestamp comment '创建时间',
    editor      varchar(32) comment '修改人',
    update_time timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id),
    unique key uniq_step (dag_id, step_id)
) engine = innodb comment 'DAG 配置步骤';

INSERT INTO `dag_config_step` (`id`, `dag_id`, `step_id`, `step_name`, `position_x`, `position_y`, `shape`, `style`,
                               `step_meta`, `step_attrs`, `creator`, `editor`)
VALUES (1, 1, '157f118c-9b6c-4d18-a919-fce824676696', 'Jdbc Source', 520, 150, NULL, NULL,
        '{\"name\":\"Jdbc\",\"type\":\"source\",\"engine\":\"seatunnel\"}',
        '{\"stepTitle\":\"Jdbc Source\",\"dataSourceType\":\"MySQL\",\"dataSource\":1,\"fetch_size\":0,\"query\":\"select * from sample_data_e_commerce\"}',
        'sys', 'sys');
INSERT INTO `dag_config_step`(`id`, `dag_id`, `step_id`, `step_name`, `position_x`, `position_y`, `shape`, `style`,
                              `step_meta`, `step_attrs`, `creator`, `editor`)
VALUES (2, 1, 'e69dbf5a-76ad-47be-aa16-175b733a7df2', 'Jdbc Sink', 460, 400, NULL, NULL,
        '{\"name\":\"Jdbc\",\"type\":\"sink\",\"engine\":\"seatunnel\"}',
        '{\"stepTitle\":\"Jdbc Sink\",\"dataSourceType\":\"MySQL\",\"dataSource\":1,\"generate_sink_sql\":false,\"batch_size\":300,\"max_retries\":3,\"is_exactly_once\":false,\"query\":\"insert into sample_data_e_commerce_duplicate \\n( id, invoice_no, stock_code, description, quantity, invoice_date, unit_price, customer_id, country )\\nvalues (?,?,?,?,?,?,?,?,?)\",\"primary_keys\":\"[]\",\"schema_save_mode\":\"CREATE_SCHEMA_WHEN_NOT_EXIST\",\"data_save_mode\":\"APPEND_DATA\"}',
        'sys', 'sys');
INSERT INTO `dag_config_step` (`id`, `dag_id`, `step_id`, `step_name`, `position_x`, `position_y`, `shape`, `style`,
                               `step_meta`, `step_attrs`, `creator`, `editor`)
VALUES (3, 2, '6223c6c3-b552-4c69-adab-5300b7514fad', 'Fake Source', 380, 140, NULL, NULL,
        '{"name":"FakeSource","type":"source","engine":"seatunnel"}',
        '{"stepTitle":"Fake Source","fields":[{"field":"c_string","type":"string"},{"field":"c_boolean","type":"boolean"},{"field":"c_tinyint","type":"tinyint"},{"field":"c_smallint","type":"smallint"},{"field":"c_int","type":"int"},{"field":"c_bigint","type":"bigint"},{"field":"c_float","type":"float"},{"field":"c_double","type":"double"},{"field":"c_decimal","type":"decimal(30, 8)"},{"field":"c_bytes","type":"bytes"},{"field":"c_map","type":"map<string, string>"},{"field":"c_date","type":"date"},{"field":"c_time","type":"time"},{"field":"c_timestamp","type":"timestamp"}],"schema":"{\\\"fields\\\":{\\\"c_string\\\":\\\"string\\\",\\\"c_boolean\\\":\\\"boolean\\\",\\\"c_tinyint\\\":\\\"tinyint\\\",\\\"c_smallint\\\":\\\"smallint\\\",\\\"c_int\\\":\\\"int\\\",\\\"c_bigint\\\":\\\"bigint\\\",\\\"c_float\\\":\\\"float\\\",\\\"c_double\\\":\\\"double\\\",\\\"c_decimal\\\":\\\"decimal(30, 8)\\\",\\\"c_bytes\\\":\\\"bytes\\\",\\\"c_map\\\":\\\"map<string, string>\\\",\\\"c_date\\\":\\\"date\\\",\\\"c_time\\\":\\\"time\\\",\\\"c_timestamp\\\":\\\"timestamp\\\"}}"}',
        'sys', 'sys');
INSERT INTO `dag_config_step` (`id`, `dag_id`, `step_id`, `step_name`, `position_x`, `position_y`, `shape`, `style`,
                               `step_meta`, `step_attrs`, `creator`, `editor`)
VALUES (4, 2, 'f08143b4-34dc-4190-8723-e8d8ce49738f', 'Console Sink', 360, 290, NULL, NULL,
        '{"name":"Console","type":"sink","engine":"seatunnel"}', '{"stepTitle":"Console Sink"}', 'sys', 'sys');
INSERT INTO `dag_config_step` (`id`, `dag_id`, `step_id`, `step_name`, `position_x`, `position_y`, `shape`, `style`,
                               `step_meta`, `step_attrs`, `creator`, `editor`)
VALUES (7, 4, '7f7ced76-7771-4870-91d9-435ef1c4e623', 'FlinkSessionClusterStatus', 460, 400, NULL, NULL,
        '{\"handler\":\"cn.sliew.scaleph.application.flink.action.FlinkSessionClusterStatusSyncJob\",\"type\":\"1\"}',
        NULL, 'sys', 'sys');
INSERT INTO `dag_config_step` (`id`, `dag_id`, `step_id`, `step_name`, `position_x`, `position_y`, `shape`, `style`,
                               `step_meta`, `step_attrs`, `creator`, `editor`)
VALUES (8, 5, '5d5d67c5-ade3-4005-a0db-d514bf11616d', 'FlinkJobStatus', 460, 400, NULL, NULL,
        '{\"handler\":\"cn.sliew.scaleph.application.flink.action.FlinkJobStatusSyncJob\",\"type\":\"1\"}', NULL,
        'sys', 'sys');
INSERT INTO `dag_config_step` (`id`, `dag_id`, `step_id`, `step_name`, `position_x`, `position_y`, `shape`, `style`,
                               `step_meta`, `step_attrs`, `creator`, `editor`)
VALUES (9, 6, '8c7b171c-f232-4b96-b842-5f4fbef34bc1', 'DorisOperatorInstanceStatus', 460, 400, NULL, NULL,
        '{\"handler\":\"cn.sliew.scaleph.application.doris.action.DorisOperatorInstanceStatusSyncJob\",\"type\":\"1\"}',
        NULL, 'sys', 'sys');
INSERT INTO `dag_config_step` (`id`, `dag_id`, `step_id`, `step_name`, `position_x`, `position_y`, `shape`, `style`,
                               `step_meta`, `step_attrs`, `creator`, `editor`)
VALUES (10, 7, 'cae1a622-6c96-4cec-81d3-883510c17702', 'FlinkJobStatus-1', 460, 400, NULL, NULL,
        '{\"handler\":\"cn.sliew.scaleph.application.flink.action.FlinkJobStatusSyncJobStepOne\",\"type\":\"1\"}',
        NULL, 'sys', 'sys');
INSERT INTO `dag_config_step` (`id`, `dag_id`, `step_id`, `step_name`, `position_x`, `position_y`, `shape`, `style`,
                               `step_meta`, `step_attrs`, `creator`, `editor`)
VALUES (11, 7, '2c2cb6c8-794b-4cc1-8258-cd1898912744', 'FlinkJobStatus-2', 460, 400, NULL, NULL,
        '{\"handler\":\"cn.sliew.scaleph.application.flink.action.FlinkJobStatusSyncJobStepTwo\",\"type\":\"1\"}',
        NULL, 'sys', 'sys');
INSERT INTO `dag_config_step` (`id`, `dag_id`, `step_id`, `step_name`, `position_x`, `position_y`, `shape`, `style`,
                               `step_meta`, `step_attrs`, `creator`, `editor`)
VALUES (12, 7, 'd82a947b-f414-4273-973a-06f20fe33f0d', 'FlinkJobStatus-3-1', 460, 400, NULL, NULL,
        '{\"handler\":\"cn.sliew.scaleph.application.flink.action.FlinkJobStatusSyncJobStepThreeOne\",\"type\":\"1\"}',
        NULL, 'sys', 'sys');
INSERT INTO `dag_config_step` (`id`, `dag_id`, `step_id`, `step_name`, `position_x`, `position_y`, `shape`, `style`,
                               `step_meta`, `step_attrs`, `creator`, `editor`)
VALUES (13, 7, '027db10b-9150-403d-9d11-e4a36c99e1db', 'FlinkJobStatus-3-2', 460, 400, NULL, NULL,
        '{\"handler\":\"cn.sliew.scaleph.application.flink.action.FlinkJobStatusSyncJobStepThreeTwo\",\"type\":\"1\"}',
        NULL, 'sys', 'sys');

drop table if exists dag_config_link;
create table dag_config_link
(
    id           bigint      not null auto_increment comment '自增主键',
    dag_id       bigint      not null comment 'DAG id',
    link_id      varchar(36) not null comment '连线id',
    link_name    varchar(128) comment '连线名称',
    from_step_id varchar(36) not null comment '源步骤id',
    to_step_id   varchar(36) not null comment '目标步骤id',
    shape        varchar(64),
    style        text,
    link_meta    varchar(128) comment '连线元信息',
    link_attrs   mediumtext comment '连线属性',
    creator      varchar(32) comment '创建人',
    create_time  timestamp default current_timestamp comment '创建时间',
    editor       varchar(32) comment '修改人',
    update_time  timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id),
    unique key uniq_link (dag_id, link_id)
) engine = innodb comment 'DAG 配置连线';

INSERT INTO `dag_config_link` (`id`, `dag_id`, `link_id`, `link_name`, `from_step_id`, `to_step_id`, `shape`, `style`,
                               `link_meta`, `link_attrs`, `creator`, `editor`)
VALUES (1, 1, '78ca5c31-0eaa-4d43-8f30-0d8f7d0ec317', NULL, '157f118c-9b6c-4d18-a919-fce824676696',
        'e69dbf5a-76ad-47be-aa16-175b733a7df2', NULL, NULL, NULL, NULL, 'sys', 'sys');
INSERT INTO `dag_config_link` (`id`, `dag_id`, `link_id`, `link_name`, `from_step_id`, `to_step_id`, `shape`, `style`,
                               `link_meta`, `link_attrs`, `creator`, `editor`)
VALUES (2, 2, 'd57021a1-65c7-4dfe-ae89-3b73d00fcf72', NULL, '6223c6c3-b552-4c69-adab-5300b7514fad',
        'f08143b4-34dc-4190-8723-e8d8ce49738f', NULL, NULL, NULL, NULL, 'sys', 'sys');
INSERT INTO `dag_config_link` (`id`, `dag_id`, `link_id`, `link_name`, `from_step_id`, `to_step_id`, `shape`, `style`,
                               `link_meta`, `link_attrs`, `creator`, `editor`)
VALUES (4, 7, '2d172e1a-ef92-431c-9889-7461bccae7a5', NULL, 'cae1a622-6c96-4cec-81d3-883510c17702',
        '2c2cb6c8-794b-4cc1-8258-cd1898912744', NULL, NULL, NULL, NULL, 'sys', 'sys');
INSERT INTO `dag_config_link` (`id`, `dag_id`, `link_id`, `link_name`, `from_step_id`, `to_step_id`, `shape`, `style`,
                               `link_meta`, `link_attrs`, `creator`, `editor`)
VALUES (5, 7, 'af16c8ee-0abf-4555-aa0e-98ec01964ce1', NULL, '2c2cb6c8-794b-4cc1-8258-cd1898912744',
        'd82a947b-f414-4273-973a-06f20fe33f0d', NULL, NULL, NULL, NULL, 'sys', 'sys');
INSERT INTO `dag_config_link` (`id`, `dag_id`, `link_id`, `link_name`, `from_step_id`, `to_step_id`, `shape`, `style`,
                               `link_meta`, `link_attrs`, `creator`, `editor`)
VALUES (6, 7, '027db10b-9150-403d-9d11-e4a36c99e1db', NULL, '2c2cb6c8-794b-4cc1-8258-cd1898912744',
        '027db10b-9150-403d-9d11-e4a36c99e1db', NULL, NULL, NULL, NULL, 'sys', 'sys');

drop table if exists dag_instance;
create table dag_instance
(
    id          bigint not null auto_increment comment '自增主键',
    dag_meta    varchar(128) comment 'DAG元信息',
    dag_attrs   mediumtext comment 'DAG属性',
    creator     varchar(32) comment '创建人',
    create_time timestamp default current_timestamp comment '创建时间',
    editor      varchar(32) comment '修改人',
    update_time timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id)
) engine = innodb comment 'DAG 实例';

drop table if exists dag_step;
create table dag_step
(
    id          bigint      not null auto_increment comment '自增主键',
    dag_id      bigint      not null comment 'DAG id',
    step_id     varchar(36) not null comment '步骤id',
    step_name   varchar(128) comment '步骤名称',
    position_x  int         not null comment 'x坐标',
    position_y  int         not null comment 'y坐标',
    step_meta   varchar(128) comment '步骤元信息',
    step_attrs  mediumtext comment '步骤属性',
    creator     varchar(32) comment '创建人',
    create_time timestamp default current_timestamp comment '创建时间',
    editor      varchar(32) comment '修改人',
    update_time timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id),
    unique key uniq_step (dag_id, step_id)
) engine = innodb comment 'DAG 步骤';

drop table if exists dag_link;
create table dag_link
(
    id           bigint      not null auto_increment comment '自增主键',
    dag_id       bigint      not null comment 'DAG id',
    link_id      varchar(36) not null comment '连线id',
    link_name    varchar(128) comment '连线名称',
    from_step_id varchar(36) not null comment '源步骤id',
    to_step_id   varchar(36) not null comment '目标步骤id',
    link_meta    varchar(128) comment '连线元信息',
    link_attrs   mediumtext comment '连线属性',
    creator      varchar(32) comment '创建人',
    create_time  timestamp default current_timestamp comment '创建时间',
    editor       varchar(32) comment '修改人',
    update_time  timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id),
    unique key uniq_link (dag_id, link_id)
) engine = innodb comment 'DAG 连线';