CREATE TABLE doctor_specialization_mapping (
    ds_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    doctor_id BIGINT NOT NULL,
    specialization_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    last_modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_modified_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (doctor_id) REFERENCES doctor(id),
    FOREIGN KEY (specialization_id) REFERENCES specializations(id),
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (last_modified_by) REFERENCES users(id)
); 