package com.cjss.employee.repository;

import com.cjss.employee.entity.EmployeeAddEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeAddressRepository extends JpaRepository<EmployeeAddEntity,String> {
    List<EmployeeAddEntity> findByCountryIgnoreCase(String country);
    List<EmployeeAddEntity> findByCityOrCityIgnoreCase(String city1, String city2);
    List<EmployeeAddEntity> findByCityAndCountryIgnoreCase(String city, String country);


}
