-- Initial Sequence

DROP DATABASE IF EXISTS timeline;
CREATE DATABASE timeline CHARACTER SET utf8mb4 COLLATE = utf8mb4_general_ci;

-- In mysql 8 or higher, creating user and granting should be separated.

-- Drop user

DROP USER IF EXISTS 'readonly'@'%';
DROP USER IF EXISTS 'readonly'@'localhost';
DROP USER IF EXISTS 'readwrite'@'%';
DROP USER IF EXISTS 'readwrite'@'localhost';

-- Prepare user

CREATE USER 'readonly'@'%'         IDENTIFIED BY 'readonly';
CREATE USER 'readonly'@'localhost' IDENTIFIED BY 'readonly';

GRANT SELECT, LOCK TABLES ON timeline.* TO 'readonly'@'%';
GRANT SELECT, LOCK TABLES ON timeline.* TO 'readonly'@'localhost';

CREATE USER 'readwrite'@'%'         IDENTIFIED BY 'readwrite';
CREATE USER 'readwrite'@'localhost' IDENTIFIED BY 'readwrite';

GRANT SELECT, UPDATE, INSERT, DELETE, CREATE, DROP, INDEX, ALTER, LOCK TABLES ON timeline.* TO readwrite@'%';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, INDEX, ALTER, LOCK TABLES ON timeline.* TO readwrite@'localhost';

FLUSH PRIVILEGES;

-- Table Definition

SET sql_mode='TRADITIONAL,NO_AUTO_VALUE_ON_ZERO,ONLY_FULL_GROUP_BY';
