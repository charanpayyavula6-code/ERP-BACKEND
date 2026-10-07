USE erp2db;

ALTER TABLE student
    DROP CHECK chk_student_gpa,
    ADD CONSTRAINT chk_student_gpa CHECK (gpa IS NULL OR gpa BETWEEN 0.0 AND 10.0);