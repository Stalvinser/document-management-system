DROP TABLE IF EXISTS document;

CREATE TABLE document (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255),
                          date TIMESTAMP,
                          is_public BOOLEAN NOT NULL DEFAULT FALSE,
                          app_user_id BIGINT,
                          description TEXT,
                          FOREIGN KEY (app_user_id) REFERENCES app_user(id)
);