DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `api_token` varchar(255) DEFAULT NULL,
  `activation_key` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `birthday` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `province_id` int DEFAULT NULL,
  `district_id` int DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `zipcode` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `social_facebook` varchar(255) DEFAULT NULL,
  `social_instagram` varchar(255) DEFAULT NULL,
  `social_twitter` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO `role` VALUES (1,'OKKIO_USER','ACTIVATED'),(2,'OKKIO_ADMIN','ACTIVATED'),(3,'OKKIO_GUEST','ACTIVATED');

DROP TABLE IF EXISTS `refresh_token`;

CREATE TABLE `refresh_token` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `token` varchar(255) DEFAULT NULL,
  `expire_date` datetime DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`)
);

