package com.cjss.employee.repository;

import com.cjss.employee.entity.EmployeeAttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeAttendanceRepository extends JpaRepository<EmployeeAttendanceEntity,String> {
List<EmployeeAttendanceEntity> findByHolidayTrue();

}
