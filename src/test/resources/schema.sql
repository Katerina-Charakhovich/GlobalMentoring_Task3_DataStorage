CREATE SCHEMA IF NOT EXISTS data_storage;
CREATE TABLE `data_storage`.`files` (
                                        `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
                                        `path` VARCHAR( 255 ) ,
                                        `data` BLOB
)