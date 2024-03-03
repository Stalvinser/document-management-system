DROP TABLE IF EXISTS document_share;

CREATE TABLE document_share (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                document_id BIGINT,
                                app_user_id BIGINT,
                                can_view BOOLEAN NOT NULL,
                                can_edit BOOLEAN NOT NULL,
                                can_delete BOOLEAN NOT NULL,
                                FOREIGN KEY (document_id) REFERENCES document(id),
                                FOREIGN KEY (app_user_id) REFERENCES app_user(id)
);