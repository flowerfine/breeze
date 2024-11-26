create database if not exists carp default character set utf8mb4 collate utf8mb4_unicode_ci;
use carp;

DROP TABLE IF EXISTS `carp_schedule_job_group`;
CREATE TABLE `carp_schedule_job_group`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `namespace`   varchar(64) NOT NULL COMMENT '命名空间',
    `name`        varchar(64) NOT NULL COMMENT '分组名称',
    `remark`      varchar(256) COMMENT 'remark',
    `creator`     varchar(32)          DEFAULT NULL COMMENT 'creator',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
    `editor`      varchar(32)          DEFAULT NULL COMMENT 'editor',
    `update_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_name` (`namespace`,`name`)
) ENGINE=InnoDB COMMENT='schedule job group';

INSERT INTO `carp_schedule_job_group` (`id`, `namespace`, `name`, `remark`, `creator`, `editor`)
VALUES (1, '1', 'default', NULL, 'sys', 'sys');

DROP TABLE IF EXISTS `carp_schedule_job_config`;
CREATE TABLE `carp_schedule_job_config`
(
    `id`           bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `job_group_id` bigint(20) NOT NULL COMMENT '任务分组 id',
    `type`         varchar(8)  NOT NULL COMMENT '类型。系统、用户',
    `engine_type`  varchar(8)  NOT NULL COMMENT '引擎类型。内置、temporal、DolphinScheduler',
    `job_type`     varchar(8)  NOT NULL COMMENT '任务类型。normal、workflow',
    `name`         varchar(64) NOT NULL COMMENT '任务名称',
    `handler`      varchar(64) COMMENT '任务处理器',
    `remark`       varchar(255) COMMENT 'remark',
    `creator`      varchar(32)          DEFAULT NULL COMMENT 'creator',
    `create_time`  datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
    `editor`       varchar(32)          DEFAULT NULL COMMENT 'editor',
    `update_time`  datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_group_name` (`job_group_id`,`name`)
) ENGINE=InnoDB COMMENT='schedule job config';

INSERT INTO `carp_schedule_job_config` (`id`, `job_group_id`, `type`, `engine_type`, `job_type`, `name`, `handler`, `remark`,
                                        `creator`, `editor`)
VALUES (1, 1, '0', 'internal', '0', 'demo', NULL, NULL, 'sys', 'sys');

DROP TABLE IF EXISTS `carp_schedule_job_instance`;
CREATE TABLE `carp_schedule_job_instance`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `job_config_id` bigint(20) NOT NULL COMMENT '任务配置id',
    `name`          varchar(255) NOT NULL COMMENT '实例名称',
    `cron`          varchar(128) COMMENT 'CRON表达式',
    `timezone`      VARCHAR(64)  NOT NULL,
    `start_time`    DATETIME,
    `end_time`      DATETIME,
    `props`         varchar(255) COMMENT '属性',
    `params`        varchar(255) COMMENT '参数',
    `timeout`       bigint(20) COMMENT '超时时间（毫秒）',
    `status`        varchar(4)   NOT NULL COMMENT '状态',
    `remark`        varchar(255) COMMENT 'remark',
    `creator`       varchar(32)           DEFAULT NULL COMMENT 'creator',
    `create_time`   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
    `editor`        varchar(32)           DEFAULT NULL COMMENT 'editor',
    `update_time`   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='schedule job instance';

INSERT INTO `carp_schedule_job_instance` (`id`, `job_config_id`, `name`, `cron`, `timezone`, `start_time`, `end_time`,
                                          `props`, `params`, `timeout`, `status`, `remark`, `creator`, `editor`)
VALUES (1, 1, 'high', '0/10 * * * * ?', 'GMT+8', '2024-01-01 00:00:00', '9999-01-01 00:00:00', NULL, NULL, NULL, '0',
        NULL, 'sys', 'sys');
INSERT INTO `carp_schedule_job_instance` (`id`, `job_config_id`, `name`, `cron`, `timezone`, `start_time`, `end_time`,
                                          `props`, `params`, `timeout`, `status`, `remark`, `creator`, `editor`)
VALUES (2, 1, 'middle', '0 0/1 * * * ?', 'GMT+8', '2024-01-01 00:00:00', '9999-01-01 00:00:00', NULL, NULL, NULL, '0',
        NULL, 'sys', 'sys');
INSERT INTO `carp_schedule_job_instance` (`id`, `job_config_id`, `name`, `cron`, `timezone`, `start_time`, `end_time`,
                                          `props`, `params`, `timeout`, `status`, `remark`, `creator`, `editor`)
VALUES (3, 1, 'high', '0 0/5 * * * ?', 'GMT+8', '2024-01-01 00:00:00', '9999-01-01 00:00:00', NULL, NULL, NULL, '0',
        NULL, 'sys', 'sys');
