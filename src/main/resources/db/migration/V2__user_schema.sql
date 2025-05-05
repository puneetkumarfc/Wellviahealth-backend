CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    phone_number VARCHAR(15) UNIQUE NOT NULL,
    otp VARCHAR(6),
    otp_verified BOOLEAN DEFAULT FALSE,
    otp_created_at TIMESTAMP,
    user_type_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    provider INT,  -- Social login provider (1 -> Facebook, 2 -> Google)
    provider_user_id VARCHAR(255),  -- Social login provider's user ID (optional)
    FOREIGN KEY (user_type_id) REFERENCES user_type(id)
);