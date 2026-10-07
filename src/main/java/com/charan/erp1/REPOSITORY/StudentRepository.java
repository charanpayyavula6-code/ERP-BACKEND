package com.charan.erp1.REPOSITORY;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.charan.erp1.ENTITY.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT student FROM Student student WHERE "
            + "(:search IS NULL OR :search = '' OR "
            + "LOWER(student.name) LIKE LOWER(CONCAT('%', :search, '%')) OR "
            + "LOWER(student.email) LIKE LOWER(CONCAT('%', :search, '%')) OR "
            + "LOWER(student.rollNo) LIKE LOWER(CONCAT('%', :search, '%')) OR "
            + "LOWER(student.department) LIKE LOWER(CONCAT('%', :search, '%'))) AND "
            + "(:department IS NULL OR :department = '' OR "
            + "LOWER(student.department) = LOWER(:department)) ORDER BY student.id")
    List<Student> searchStudents(@Param("search") String search, @Param("department") String department);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from Student student where student.id = :id")
    int deleteByIdIfExists(@Param("id") Long id);
}