package com.charan.erp1.SERVICES;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.charan.erp1.DTO.StudentRequest;
import com.charan.erp1.DTO.StudentResponse;
import com.charan.erp1.ENTITY.Student;
import com.charan.erp1.EXCEPTION.BadRequestException;
import com.charan.erp1.EXCEPTION.ResourceNotFoundException;
import com.charan.erp1.REPOSITORY.StudentRepository;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentResponse> getAllStudents(String search, String department) {
        return studentRepository.searchStudents(search, department)
                .stream()
                .map(StudentResponse::fromEntity)
                .toList();
    }

    public StudentResponse getStudentById(Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Student id must be greater than zero");
        }

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        return StudentResponse.fromEntity(student);
    }

    public StudentResponse createStudent(StudentRequest request) {
        if (request == null) {
            throw new BadRequestException("Student request cannot be null");
        }

        Student student = new Student();
        copyRequestToStudent(request, student);

        return StudentResponse.fromEntity(studentRepository.save(student));
    }

    public StudentResponse updateStudent(Long id, StudentRequest request) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Student id must be greater than zero");
        }
        if (request == null) {
            throw new BadRequestException("Student request cannot be null");
        }

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));

        copyRequestToStudent(request, student);

        return StudentResponse.fromEntity(studentRepository.save(student));
    }

    @Transactional
    public void deleteStudent(Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Student id must be greater than zero");
        }

        if (studentRepository.deleteByIdIfExists(id) == 0) {
            throw new ResourceNotFoundException("Student not found with id: " + id);
        }
    }

    private void copyRequestToStudent(StudentRequest request, Student student) {
        student.setName(request.getName().trim());
        student.setEmail(request.getEmail().trim());
        student.setPhone(request.getPhone().trim());
        student.setRollNo(request.getRollNo().trim());
        student.setDepartment(request.getDepartment().trim());
        student.setSemester(request.getSemester());
        student.setGpa(request.getGpa());
        student.setAttendance(request.getAttendance());
        student.setFeeStatus(request.getFeeStatus());
        student.setStatus(request.getStatus());
        student.setAdmissionDate(request.getAdmissionDate());
        student.setAddress(request.getAddress());
        student.setAvatarColor(request.getAvatarColor());
    }
}
