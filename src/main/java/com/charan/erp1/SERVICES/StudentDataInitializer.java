package com.charan.erp1.SERVICES;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.charan.erp1.ENTITY.Student;
import com.charan.erp1.REPOSITORY.StudentRepository;

@Component
public class StudentDataInitializer implements CommandLineRunner {
    private final StudentRepository studentRepository;

    public StudentDataInitializer(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void run(String... args) {
        if (studentRepository.count() > 0) {
            return;
        }

        studentRepository.saveAll(List.of(
                createStudent("Aarav Sharma", "aarav.sharma@example.edu", "+1-555-0101", "2024CS101",
                        "Computer Science", "4th Sem", 3.82, 94, "Paid", "Active",
                        LocalDate.of(2024, 8, 15), "12 Cedar Street", "#4f46e5"),
                createStudent("Maya Patel", "maya.patel@example.edu", "+1-555-0102", "2023IT204",
                        "Information Technology", "6th Sem", 3.64, 88, "Pending", "Active",
                        LocalDate.of(2023, 8, 16), "48 Lake Avenue", "#0f766e"),
                createStudent("Noah Williams", "noah.williams@example.edu", "+1-555-0103", "2024ME115",
                        "Mechanical Engineering", "4th Sem", 3.41, 91, "Paid", "Active",
                        LocalDate.of(2024, 8, 15), "7 Oak Road", "#b45309"),
                createStudent("Sofia Chen", "sofia.chen@example.edu", "+1-555-0104", "2022EC309",
                        "Electronics", "8th Sem", 3.75, 79, "Overdue", "On Leave",
                        LocalDate.of(2022, 8, 17), "33 Pine Lane", "#be123c")));
    }

    private Student createStudent(String name, String email, String phone, String rollNo, String department,
            String semester, Double gpa, Integer attendance, String feeStatus, String status,
            LocalDate admissionDate, String address, String avatarColor) {
        Student student = new Student();
        student.setName(name);
        student.setEmail(email);
        student.setPhone(phone);
        student.setRollNo(rollNo);
        student.setDepartment(department);
        student.setSemester(semester);
        student.setGpa(gpa);
        student.setAttendance(attendance);
        student.setFeeStatus(feeStatus);
        student.setStatus(status);
        student.setAdmissionDate(admissionDate);
        student.setAddress(address);
        student.setAvatarColor(avatarColor);
        return student;
    }
}