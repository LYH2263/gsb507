CREATE DATABASE IF NOT EXISTS longmao_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE longmao_db;

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `role` varchar(20) DEFAULT 'USER' COMMENT '角色',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- Password is '123456' hashed with BCrypt
INSERT INTO `sys_user` (`username`, `password`, `role`) VALUES ('admin', '$2a$10$Cstc8ZoT65DwRCo7Wht/BuBPMacGV3Gt5hbdfyy70SAaJ0ZPdvfn6', 'ADMIN');

DROP TABLE IF EXISTS `sys_book`;
CREATE TABLE `sys_book` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL COMMENT '书名',
  `author` varchar(50) DEFAULT NULL COMMENT '作者',
  `price` decimal(10,2) DEFAULT NULL COMMENT '价格',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='图书表';

INSERT INTO `sys_book` (`title`, `author`, `price`) VALUES 
('Java Programming', 'Author A', 59.90),
('Spring Boot Action', 'Author B', 79.00),
('Vue.js Guide', 'Author C', 45.50);

DROP TABLE IF EXISTS `sys_borrow_record`;
CREATE TABLE `sys_borrow_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `book_id` bigint(20) NOT NULL COMMENT '图书ID',
  `borrow_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '借阅时间',
  `return_time` datetime DEFAULT NULL COMMENT '归还时间',
  `status` varchar(20) DEFAULT 'BORROWED' COMMENT '状态: BORROWED/RETURNED',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='借阅记录表';
