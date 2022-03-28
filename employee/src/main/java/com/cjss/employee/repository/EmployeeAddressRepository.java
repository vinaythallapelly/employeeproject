package com.cjss.employee.repository;

import com.cjss.employee.entity.EmployeeAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeAddressRepository extends JpaRepository<EmployeeAddressEntity,String> {
    List<EmployeeAddressEntity> findByCountryIgnoreCase(String country);
    List<EmployeeAddressEntity> findByCityOrCityIgnoreCase(String city1,String city2);
    List<EmployeeAddressEntity> findByCityAndCountryIgnoreCase(String city,String country);


}
