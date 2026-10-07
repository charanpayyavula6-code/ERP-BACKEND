package com.charan.erp1.ENTITY;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name is mandatory")
    @Size(max = 100, message = "name should not exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "email is mandatory")
    @Email(message = "email must be valid")
    @Size(max = 150, message = "email should not exceed 150 characters")
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @NotBlank(message = "phone is mandatory")
    @Size(max = 30, message = "phone should not exceed 30 characters")
    @Column(nullable = false, length = 30)
    private String phone;

    @NotBlank(message = "rollNo is mandatory")
    @Size(max = 30, message = "rollNo should not exceed 30 characters")
    @Column(nullable = false, unique = true, length = 30)
    private String rollNo;

    @NotBlank(message = "department is mandatory")
    @Size(max = 100, message = "department should not exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String department;

    @Size(max = 30, message = "semester should not exceed 30 characters")
    @Column(length = 30)
    private String semester;

    @DecimalMin(value = "0.0", message = "gpa must be at least 0.00")
    @DecimalMax(value = "10.0", message = "gpa must be at most 10.0")
    private Double gpa;

    @Min(value = 0, message = "attendance must be between 0 and 100")
    @Max(value = 100, message = "attendance must be between 0 and 100")
    private Integer attendance;

    @Size(max = 20, message = "feeStatus should not exceed 20 characters")
    @Column(length = 20)
    private String feeStatus;

    @Size(max = 20, message = "status should not exceed 20 characters")
    @Column(length = 20)
    private String status;

    private LocalDate admissionDate;

    @Size(max = 500, message = "address should not exceed 500 characters")
    @Column(length = 500)
    private String address;

    @Size(max = 7, message = "avatarColor must be a hex color")
    @Column(length = 7)
    private String avatarColor;

    public Student() {
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
