-- sample.organizations definition

CREATE OR REPLACE SEQUENCE `organizations_seq` 
START WITH 1 
MINVALUE 1 
MAXVALUE 9223372036854775806 
INCREMENT BY 1 
NOCACHE 
NOCYCLE 
ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `organizations` (
    `id` bigint(20) NOT NULL,
    `name` varchar(255) NOT NULL,
    `institution_name` varchar(255) NOT NULL,
    `country` varchar(255) NOT NULL,
    `street` varchar(255) DEFAULT NULL,
    `number` varchar(50) DEFAULT NULL,
    `neighborhood` varchar(255) DEFAULT NULL,
    `zip_code` varchar(20) DEFAULT NULL,
    `city` varchar(255) DEFAULT NULL,
    `state` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
