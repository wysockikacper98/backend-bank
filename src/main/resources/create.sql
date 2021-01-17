-- -----------------------------------------------------
-- Schema full-stack-ecommerce
-- -----------------------------------------------------

USE `bank`;

--
-- Prep work
--
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `client`;
DROP TABLE IF EXISTS `address`;
DROP TABLE IF EXISTS `login`;
DROP TABLE IF EXISTS `account`;
DROP TABLE IF EXISTS `payments`;
SET FOREIGN_KEY_CHECKS = 1;

--
-- Table structure for table `address`
--
CREATE TABLE `address`
(
    `id`       bigint NOT NULL AUTO_INCREMENT,
    `city`     varchar(255) DEFAULT NULL,
    `country`  varchar(255) DEFAULT NULL,
    `state`    varchar(255) DEFAULT NULL,
    `street`   varchar(255) DEFAULT NULL,
    `zip_code` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

--
-- Table structure for table `customer`
--
CREATE TABLE `client`
(
    `id`                        bigint NOT NULL AUTO_INCREMENT,
    `first_name`                varchar(255) DEFAULT NULL,
    `last_name`                 varchar(255) DEFAULT NULL,
    `citizenship`               varchar(255) DEFAULT NULL,
    `pesel`                     varchar(255) DEFAULT NULL,
    `date_of_birth`             date         DEFAULT NULL,
    `identity_card_number`      varchar(255) DEFAULT NULL,
    `telephone`                 varchar(255) DEFAULT NULL,
    `email`                     varchar(255) DEFAULT NULL,
    `id_address_correspondence` bigint       DEFAULT NULL,
    `id_address`                bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_id_address_correspondence` FOREIGN KEY (`id_address_correspondence`) REFERENCES `address` (`id`),
    CONSTRAINT `FK_id_address` FOREIGN KEY (`id_address`) REFERENCES `address` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

--
-- Table structure for table `login`
--
CREATE TABLE `login`
(
    `id`        bigint NOT NULL AUTO_INCREMENT,
    `login`     varchar(255) DEFAULT NULL,
    `password`  varchar(255) DEFAULT NULL,
    `id_client` bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_id_client` FOREIGN KEY (`id_client`) REFERENCES `client` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

--
-- Table structure for table `account`
--
CREATE TABLE `account`
(
    `id`             bigint NOT NULL AUTO_INCREMENT,
    `account_number` varchar(255)   DEFAULT NULL,
    `salary`         decimal(19, 2) DEFAULT NULL,
    `id_client`      bigint         DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_client` FOREIGN KEY (`id_client`) REFERENCES `client` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

--
-- Table structure for table `payments`
--
CREATE TABLE `payments`
(
    `id`                        bigint         NOT NULL AUTO_INCREMENT,
    `debited_account_number`    varchar(255)   NOT NULL,
    `debited_name_and_address`  varchar(255) DEFAULT NULL,
    `credited_account_number`   varchar(255)   NOT NULL,
    `credited_name_and_address` varchar(255) DEFAULT NULL,
    `title`                     varchar(255) DEFAULT NULL,
    `amount`                    decimal(19, 2) NOT NULL,
    `id_account`                bigint         NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_id_client_payments` FOREIGN KEY (`id_account`) REFERENCES `account` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;