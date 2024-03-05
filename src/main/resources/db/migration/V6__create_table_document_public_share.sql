DROP TABLE IF EXISTS document_public_share;

CREATE TABLE document_public_share (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                document_id BIGINT,
                                can_view BOOLEAN NOT NULL,
                                can_edit BOOLEAN NOT NULL,
                                can_delete BOOLEAN NOT NULL,
                                FOREIGN KEY (document_id) REFERENCES document(id)
);