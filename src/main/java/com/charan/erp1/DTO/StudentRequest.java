package com.charan.erp1.DTO;

import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class StudentRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name should not exceed 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 150, message = "Email should not exceed 150 characters")
    private String email;

    @NotBlank(message = "Phone is required")
    @Size(max = 30, message = "Phone should not exceed 30 characters")
    private String phone;

    @NotBlank(message = "Roll number is required")
    @Size(max = 30, message = "Roll number should not exceed 30 characters")
    private String rollNo;

    @NotBlank(message = "Department is required")
    @Size(max = 100, message = "Department should not exceed 100 characters")
    private String department;

    @Size(max = 30, message = "Semester should not exceed 30 characters")
    private String semester;

    @DecimalMin(value = "0.0", message = "GPA must be at least 0.00")
    @DecimalMax(value = "10.0", message = "GPA must be at most 10.0")
    private Double gpa;

    @Min(value = 0, message = "Attendance must be between 0 and 100")
    @Max(value = 100, message = "Attendance must be between 0 and 100")
    private Integer attendance;

    @Pattern(regexp = "Paid|Pending|Overdue", message = "Fee status must be Paid, Pending, or Overdue")
    private String feeStatus;

    @Pattern(regexp = "Active|On Leave|Inactive", message = "Status must be Active, On Leave, or Inactive")
    private String status;

    private LocalDate admissionDate;

    @Size(max = 500, message = "Address should not exceed 500 characters")
    private String address;

    @Pattern(regexp = "^#[0-9a-fA-F]{6}$", message = "Avatar color must be a six-digit hex color")
    private String avatarColor;

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
