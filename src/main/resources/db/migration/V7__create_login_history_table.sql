CREATE TABLE login_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    login_method ENUM('MOBILE', 'GOOGLE', 'FACEBOOK') NOT NULL,
    provider_user_id VARCHAR(255),  -- For social logins
    device_info VARCHAR(255),
    ip_address VARCHAR(45),
    login_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) NOT NULL,  -- SUCCESS, FAILED
    failure_reason TEXT,  -- If login failed, store the reason
    
    CONSTRAINT fk_login_history_user FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
); 