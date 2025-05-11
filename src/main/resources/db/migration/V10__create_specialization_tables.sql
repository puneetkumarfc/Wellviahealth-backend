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

-- Insert specializations
INSERT INTO specialization (name, created_by) VALUES
('Cold, Fever, Cough and Sneeze', 1),
('Diabetes Consult', 1),
('General Physician', 1),
('Dental', 1),
('Dermatology', 1),
('Hair Scalp Care', 1),
('Gynecology', 1),
('Infertility', 1),
('Ophthalmology', 1),
('Orthopedics', 1),
('Pediatrics', 1),
('Psychology', 1),
('Sexology', 1),
('Food and Nutrition', 1),
('Ear Nose and Throat', 1),
('Cardiology', 1),
('Psychiatry', 1),
('Pulmonology', 1),
('Neurology', 1),
('Gastroenterology', 1),
('Urology', 1),
('Oncology', 1); 