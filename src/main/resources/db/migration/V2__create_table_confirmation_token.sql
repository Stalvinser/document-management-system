DROP TABLE IF EXISTS confirmation_token;

CREATE TABLE confirmation_token (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    token VARCHAR(255) NOT NULL,
                                    created_at TIMESTAMP NOT NULL,
                                    expires_at TIMESTAMP NOT NULL,
                                    confirmed_at TIMESTAMP,
                                    app_user_id BIGINT NOT NULL,
                                    FOREIGN KEY (app_user_id) REFERENCES app_user(id)
);