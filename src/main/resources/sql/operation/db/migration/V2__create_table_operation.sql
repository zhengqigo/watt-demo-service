CREATE TABLE IF NOT EXISTS  `operation` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `contents` varchar(32) NOT NULL COMMENT '内容',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='操作记录表';
