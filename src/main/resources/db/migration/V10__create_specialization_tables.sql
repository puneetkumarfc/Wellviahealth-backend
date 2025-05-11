-- Create specialization table
CREATE TABLE specialization (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    last_modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_modified_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    UNIQUE KEY uk_specialization_name (name),
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (last_modified_by) REFERENCES users(id)
);

-- Create doctor_specialization_mapping table
CREATE TABLE doctor_specialization_mapping (
    ds_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    doctor_id INT NOT NULL,
    specialization_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    last_modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_modified_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (doctor_id) REFERENCES doctor(doctor_id),
    FOREIGN KEY (specialization_id) REFERENCES specialization(id),
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (last_modified_by) REFERENCES users(id),
    UNIQUE KEY uk_doctor_specialization (doctor_id, specialization_id)
);