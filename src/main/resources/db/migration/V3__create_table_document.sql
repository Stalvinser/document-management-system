DROP TABLE IF EXISTS document;

CREATE TABLE document (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255),
                          date TIMESTAMP,
                          app_user_id BIGINT,
                          description TEXT,
                          FOREIGN KEY (app_user_id) REFERENCES app_user(id)
);