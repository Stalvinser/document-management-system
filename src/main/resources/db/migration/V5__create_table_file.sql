DROP TABLE IF EXISTS file;

CREATE TABLE file (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      file_name VARCHAR(255) NOT NULL,
                      uuid VARCHAR(255) NOT NULL UNIQUE,
                      content_type VARCHAR(255) NOT NULL,
                      document_id BIGINT,
                      FOREIGN KEY (document_id) REFERENCES document(id)
);