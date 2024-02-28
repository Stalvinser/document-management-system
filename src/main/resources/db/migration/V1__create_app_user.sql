-- Drop table if exists to avoid conflicts
DROP TABLE IF EXISTS app_user;

-- Create the TBL_EMPLOYEES table
CREATE TABLE app_user (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          first_name VARCHAR(255) NOT NULL,
                          last_name VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL UNIQUE,
                          password VARCHAR(255) NOT NULL,
                          app_user_role VARCHAR(255) NOT NULL,
                          locked BOOLEAN NOT NULL DEFAULT FALSE,
                          enabled BOOLEAN NOT NULL DEFAULT FALSE
);
