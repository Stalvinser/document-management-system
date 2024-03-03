CREATE VIEW document_info_view AS
SELECT
    d.id AS document_id,
    d.name AS document_name,
    d.date AS document_date,
    d.description,
    CONCAT(u.first_name, ' ', u.last_name) AS author_name,
    f.id AS file_id,
    f.file_name,
    f.uuid AS file_uuid,
    f.content_type
FROM
    document d
        LEFT JOIN file f ON d.id = f.document_id
        LEFT JOIN app_user u ON d.app_user_id = u.id;