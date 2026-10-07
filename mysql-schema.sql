CREATE DATABASE IF NOT EXISTS erp2db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;
USE erp2db;

CREATE TABLE IF NOT EXISTS student (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    phone VARCHAR(30) NOT NULL,
    roll_no VARCHAR(30) NOT NULL,
    department VARCHAR(100) NOT NULL,
    semester VARCHAR(30),
    gpa DOUBLE,
    attendance INT,
    fee_status VARCHAR(20),
    status VARCHAR(20),
    admission_date DATE,
    address VARCHAR(500),
    avatar_color VARCHAR(7),
    PRIMARY KEY (id),
    CONSTRAINT uq_student_email UNIQUE (email),
    CONSTRAINT uq_student_roll_no UNIQUE (roll_no),
    CONSTRAINT chk_student_gpa CHECK (gpa IS NULL OR gpa BETWEEN 0.0 AND 10.0),
    CONSTRAINT chk_student_attendance CHECK (attendance IS NULL OR attendance BETWEEN 0 AND 100),
    CONSTRAINT chk_student_fee_status CHECK (fee_status IS NULL OR fee_status IN ('Paid', 'Pending', 'Overdue')),
    CONSTRAINT chk_student_status CHECK (status IS NULL OR status IN ('Active', 'On Leave', 'Inactive'))
) ENGINE=InnoDB
    DEFAULT CHARACTER SET=utf8mb4
    COLLATE=utf8mb4_0900_ai_ci;

SELECT id, name, email, phone, roll_no, department, semester, gpa, attendance,
       fee_status, status, admission_date, address, avatar_color
FROM student;
