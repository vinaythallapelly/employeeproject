package com.cjss.employee.repository;

import com.cjss.employee.entity.EmployeeSalaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeSalaryRepository extends JpaRepository<EmployeeSalaryEntity,String> {
    List<EmployeeSalaryEntity> findByPayableIgnoreCaseContaining(String payable);
}

