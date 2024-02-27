-- Drop table if exists to avoid conflicts
DROP TABLE IF EXISTS TBL_EMPLOYEES;

-- Create the TBL_EMPLOYEES table
CREATE TABLE TBL_EMPLOYEES (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               first_name VARCHAR(250) NOT NULL,
                               last_name VARCHAR(250) NOT NULL,
                               email VARCHAR(250) DEFAULT NULL
);