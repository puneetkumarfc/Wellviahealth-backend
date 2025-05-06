CREATE TABLE `doctor` (
    `doctor_id` INT NOT NULL AUTO_INCREMENT,         -- Unique ID for the doctor
    `user_id` BIGINT NOT NULL,                          -- Foreign Key from users table
    `name` VARCHAR(255) NOT NULL,                    -- Doctor's name
    `gender` ENUM('MALE', 'FEMALE', 'OTHER') NOT NULL,  -- Doctor's gender
    `dob` DATE NOT NULL,                             -- Date of Birth
    `email` VARCHAR(255) NOT NULL,                   -- Email address
    `mobile` VARCHAR(15) NOT NULL,                   -- Mobile number
    `address` TEXT NOT NULL,                         -- Address
    `education` TEXT NOT NULL,                       -- Education details
    `bio` TEXT,                                      -- Brief biography of the doctor
    `specialization` VARCHAR(255),                   -- Doctor's area of specialization
    `registration_number` VARCHAR(100),              -- Registration number for practice
    `profile_image` VARCHAR(255),                    -- Path to profile image file
    `experience` INT NOT NULL,                       -- Years of experience
    `certificate` VARCHAR(255),                      -- Path to certificate image
    `signature` VARCHAR(255),                        -- Path to the doctor's signature image
    `practice_address` TEXT,                         -- Address of the practice location
    `agreement_file` VARCHAR(255),                   -- Path to agreement file
    `is_deleted` BOOLEAN NOT NULL DEFAULT FALSE,     -- Flag to soft-delete the record
    PRIMARY KEY (`doctor_id`),
    CONSTRAINT `fk_doctor_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);


CREATE TABLE `patient` (
    `patient_id` INT NOT NULL AUTO_INCREMENT,        -- Unique ID for the patient
    `user_id` BIGINT NOT NULL,                          -- Foreign Key from users table
    `name` VARCHAR(255) NOT NULL,                     -- Patient's name
    `gender` ENUM('MALE', 'FEMALE', 'OTHER'),          -- Patient's gender
    `dob` DATE,                                       -- Date of Birth
    `email` VARCHAR(255),                             -- Email address
    `mobile` VARCHAR(15) NOT NULL,                    -- Mobile number
    `address` TEXT,                                   -- Address
    `city` VARCHAR(255),                              -- City
    `state` VARCHAR(255),                             -- State
    `zip` VARCHAR(10),                                -- Zip code
    `country` VARCHAR(255),                           -- Country
    `profile_image` VARCHAR(255),                     -- Path to profile image file
    `blood_group` ENUM('A+', 'A-', 'B+', 'B-', 'O+', 'O-', 'AB+', 'AB-'), -- Blood group
    `allergies` TEXT,                                 -- List of allergies (if any)
    `medical_conditions` TEXT,                        -- Medical conditions (if any)
    `password` VARCHAR(255),                          -- Password (for future usage, or if you plan to implement login directly for patients)
    `is_deleted` BOOLEAN NOT NULL DEFAULT FALSE,      -- Flag to soft-delete the record
    PRIMARY KEY (`patient_id`),
    CONSTRAINT `fk_patient_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);