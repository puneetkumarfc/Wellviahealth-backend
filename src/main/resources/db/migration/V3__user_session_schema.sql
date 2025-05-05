CREATE TABLE user_session (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    session_token VARCHAR(255) NOT NULL UNIQUE,
    device_info VARCHAR(255),
    ip_address VARCHAR(45),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,

    CONSTRAINT fk_user_session_user FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);