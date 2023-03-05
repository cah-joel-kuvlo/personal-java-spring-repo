CREATE TABLE IF NOT EXISTS EMPLOYEE (
    emp_id int NOT NULL AUTO_INCREMENT,
    first_name varchar(255) DEFAULT NULL,
    middle_name varchar(255) DEFAULT NULL,
    last_name varchar(255) DEFAULT NULL,
    date_of_birth date DEFAULT NULL,
    hire_date date DEFAULT NULL,
    PRIMARY KEY (emp_id)
    );
