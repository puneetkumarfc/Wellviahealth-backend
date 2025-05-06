CREATE TABLE laboratories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    lab_name VARCHAR(255) NOT NULL,
    profile_image VARCHAR(255),
    ratings DECIMAL(2,1),
    accreditation VARCHAR(255),
    report_time VARCHAR(100),
    address TEXT,
    city VARCHAR(100),
    state VARCHAR(100),
    zip VARCHAR(10),
    price DECIMAL(10,2),
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    last_modified_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_modified_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE laboratory_slots (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    lab_id BIGINT NOT NULL,
    day_of_week ENUM('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY') NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    is_available BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (lab_id) REFERENCES laboratories(id) ON DELETE CASCADE
); 