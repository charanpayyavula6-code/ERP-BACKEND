package com.charan.erp1.DTO;



import com.charan.erp1.ENTITY.Student;
import com.charan.erp1.ENTITY.Student;
import com.charan.erp1.ENTITY.Student;
import com.charan.erp1.ENTITY.Student;
import com.charan.erp1.ENTITY.Student;
import com.charan.erp1.ENTITY.Student;
import com.charan.erp1.ENTITY.Student;
import com.charan.erp1.ENTITY.Student;
import com.charan.erp1.ENTITY.Student;
import com.charan.erp1.ENTITY.Student;
import com.charan.erp1.ENTITY.Student;
import com.charan.erp1.ENTITY.Student;
import com.charan.erp1.ENTITY.Student;
import com.charan.erp1.ENTITY.Student;
import com.charan.erp1.ENTITY.Student;

public class StudentResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String rollNo;
    private String department;
    private String semester;
    private Double gpa;
    private Integer attendance;
    private String feeStatus;
    private String status;
    private LocalDate admissionDate;
    private String address;
    private String avatarColor;

    public static StudentResponse fromEntity(Student student) {
        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setName(student.getName());
        response.setEmail(student.getEmail());
        response.setPhone(student.getPhone());
        response.setRollNo(student.getRollNo());
        response.setDepartment(student.getDepartment());
        response.setSemester(student.getSemester());
        response.setGpa(student.getGpa());
        response.setAttendance(student.getAttendance());
        response.setFeeStatus(student.getFeeStatus());
        response.setStatus(student.getStatus());
        response.setAdmissionDate(student.getAdmissionDate());
        response.setAddress(student.getAddress());
        response.setAvatarColor(student.getAvatarColor());
        return response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    public Integer getAttendance() {
        return attendance;
    }

    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }

    public String getFeeStatus() {
        return feeStatus;
    }

    public void setFeeStatus(String feeStatus) {
        this.feeStatus = feeStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatarColor() {
        return avatarColor;
    }

    public void setAvatarColor(String avatarColor) {
        this.avatarColor = avatarColor;
    }
}
