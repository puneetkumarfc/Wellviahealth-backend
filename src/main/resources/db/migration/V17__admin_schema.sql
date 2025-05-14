CREATE TABLE `admin` (
    `admin_id` INT NOT NULL AUTO_INCREMENT,          -- Unique ID for the admin
    `user_id` BIGINT NULL,                          -- Foreign Key from users table
    `name` VARCHAR(255) NULL,                       -- Admin's name
    `email` VARCHAR(255) NULL,                      -- Email address
    `password` VARCHAR(255) NULL,                   -- Password (hashed)
    `created_on` DATETIME NULL,                     -- Date and time of creation
    `created_by` INT NULL,                          -- ID of who created the admin record
    `last_modified_by` INT NULL,                    -- ID of who last modified the record
    `last_modified_on` DATETIME NULL,               -- Date and time of last modification
    `is_deleted` BOOLEAN NULL DEFAULT FALSE,        -- Flag to soft-delete the record
    PRIMARY KEY (`admin_id`),
    CONSTRAINT `fk_admin_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);