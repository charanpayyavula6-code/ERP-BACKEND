USE erp2db;

ALTER TABLE student
    DROP COLUMN age,
    DROP COLUMN course,
    CHANGE COLUMN dept department VARCHAR(100) NOT NULL,
    ADD COLUMN email VARCHAR(150),
    ADD COLUMN phone VARCHAR(30),
    ADD COLUMN roll_no VARCHAR(30),
    ADD COLUMN semester VARCHAR(30),
    ADD COLUMN gpa DOUBLE,
    ADD COLUMN attendance INT,
    ADD COLUMN fee_status VARCHAR(20),
    ADD COLUMN status VARCHAR(20),
    ADD COLUMN admission_date DATE,
    ADD COLUMN address VARCHAR(500),
    ADD COLUMN avatar_color VARCHAR(7);

UPDATE student
SET email = CONCAT('student', id, '@migration.invalid'),
    phone = 'Not provided',
    roll_no = CONCAT('LEGACY', id);

ALTER TABLE student
    MODIFY COLUMN email VARCHAR(150) NOT NULL,
    MODIFY COLUMN phone VARCHAR(30) NOT NULL,
    MODIFY COLUMN roll_no VARCHAR(30) NOT NULL,
    MODIFY COLUMN department VARCHAR(100) NOT NULL,
    ADD CONSTRAINT uq_student_email UNIQUE (email),
    ADD CONSTRAINT uq_student_roll_no UNIQUE (roll_no),
    ADD CONSTRAINT chk_student_gpa CHECK (gpa IS NULL OR gpa BETWEEN 0.0 AND 10.0),
    ADD CONSTRAINT chk_student_attendance CHECK (attendance IS NULL OR attendance BETWEEN 0 AND 100),
    ADD CONSTRAINT chk_student_fee_status CHECK (fee_status IS NULL OR fee_status IN ('Paid', 'Pending', 'Overdue')),
    ADD CONSTRAINT chk_student_status CHECK (status IS NULL OR status IN ('Active', 'On Leave', 'Inactive'));