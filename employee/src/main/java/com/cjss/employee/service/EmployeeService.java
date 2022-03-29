package com.cjss.employee.service;

import com.cjss.employee.entity.EmployeeAddEntity;
import com.cjss.employee.entity.EmployeeAttEntity;
import com.cjss.employee.entity.EmployeeEntity;
import com.cjss.employee.entity.EmployeeSalaryEntity;
import com.cjss.employee.model.*;
import com.cjss.employee.repository.EmployeeAddressRepository;
import com.cjss.employee.repository.EmployeeAttendanceRepository;
import com.cjss.employee.repository.EmployeeDetailsRepository;
import com.cjss.employee.repository.EmployeeSalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    EmployeeDetailsRepository employeeDetailsRepository;
    @Autowired
    EmployeeSalaryRepository employeeSalaryRepository;
    @Autowired
    EmployeeAddressRepository employeeAddressRepository;
    @Autowired
    EmployeeAttendanceRepository employeeAttendanceRepository;


    public EmployeeEntity addEmployee(EmployeeDetails employeeModel){
        EmployeeEntity employeeEntity = new EmployeeEntity(employeeModel.getId(),employeeModel.getName(),
                employeeModel.getEmail());
        EmployeeSalaryEntity employeeSalaryEntity = new EmployeeSalaryEntity(employeeModel.getEmployeeSalaryList().getId(),
                employeeModel.getEmployeeSalaryList().getSalary(),employeeModel.getEmployeeSalaryList().getPayable());
        employeeSalaryEntity.setEmployeeDetails(employeeEntity);
        employeeSalaryRepository.save(employeeSalaryEntity);
        employeeEntity.setEmployeeSalary(employeeSalaryEntity);

        List<EmployeeAddEntity> employeeAddresses = new ArrayList<>();
        employeeModel.getEmployeeAddressList().forEach(employeeAddress -> {
            EmployeeAddEntity employeeAddEntity = new EmployeeAddEntity(employeeAddress.getId(),
                    employeeAddress.getLine1(),employeeAddress.getLine2(),employeeAddress.getCity(),
                    employeeAddress.getPhoneNumber(),employeeAddress.getCountry());
            employeeAddEntity.setEmployeeDetails(employeeEntity);
            employeeAddresses.add(employeeAddressRepository.save(employeeAddEntity));
        });
        List<EmployeeAttEntity> employeeAttendanceEntities =new ArrayList<>();
        employeeModel.getEmployeeAttendanceList().forEach(employeeAttendance -> {
            EmployeeAttEntity employeeAttEntity = new EmployeeAttEntity(employeeAttendance.getId(),
                    employeeAttendance.getDate(), employeeAttendance.isHoliday(), employeeAttendance.getReasonForHoliday());
            employeeAttEntity.setEmployeeDetails(employeeEntity);
            employeeAttendanceRepository.save(employeeAttEntity);
            employeeAttendanceEntities.add(employeeAttEntity);
        });
        employeeEntity.setEmployeeAddressSet(employeeAddresses);
      employeeEntity.setEmployeeAttendanceList(employeeAttendanceEntities);
      return employeeDetailsRepository.save(employeeEntity);
    }

    public Set<EmployeeDetails> getEmployeeByCountry(){
                List<EmployeeAddEntity> employeeAddEntities = employeeAddressRepository.findByCountry("india");
      Set<EmployeeEntity> employeeEntities = employeeAddEntities.stream().map(EmployeeAddEntity::getEmployeeDetails).collect(Collectors.toSet());
      return  employeeEntities.stream().map(this::getEmployeeModel).collect(Collectors.toSet());
    }
    public Set<EmployeeDetails> getEmployeeByCity(String city1, String city2){
        List<EmployeeAddEntity> employeeAddEntities = employeeAddressRepository.findByCityOrCityIgnoreCase(city1,city2);
        Set<String> employeess = employeeAddEntities.stream().map(EmployeeAddEntity::getEmployeeDetails).map(EmployeeEntity::getEmployeeId).collect(Collectors.toSet());
        return   employeess.stream().map(e -> employeeDetailsRepository.findById(e).orElse(null)).map(this::getEmployeeModel).collect(Collectors.toSet());
    }
    public Set<EmployeeDetails> getEmployeeByCityAndCountry(String city, String country){
        List<EmployeeAddEntity> employeeAddEntities = employeeAddressRepository.findByCityAndCountryIgnoreCase(city,country);
        return   employeeAddEntities.stream().map(EmployeeAddEntity::getEmployeeDetails).map(this::getEmployeeModel).collect(Collectors.toSet());
    }

    public List<EmployeeSalaryDetails> getEmployeeSalaryDetails(){
       List<EmployeeEntity> employeeDetails1 = employeeDetailsRepository.findAll();
      return employeeDetails1.stream().map(this::getEmployeeWithSalary).collect(Collectors.toList());
    }
    public List<EmployeeSalaryDetails> getEmployeeSalaryDetails(String payable){

        List<EmployeeSalaryEntity> salaryies = employeeSalaryRepository.findByPayableIgnoreCaseContaining(payable);
        List<EmployeeEntity> emp = salaryies.stream().map(EmployeeSalaryEntity::getEmployeeDetails).collect(Collectors.toList());
        return emp.stream().map(this::getEmployeeWithSalary).collect(Collectors.toList());
    }
    public List<EmployeeAttendanceSalary> getEmployeeAttend(String date){
        List<EmployeeAttEntity> empAtd = employeeAttendanceRepository.findByHolidayTrue();
        Set<String> e1 = empAtd.stream().map(EmployeeAttEntity::getEmployeeDetails)
                .map(EmployeeEntity::getEmployeeId).collect(Collectors.toSet());
        List<Integer> size = new LinkedList<>();
                e1.forEach(e2 -> {List<EmployeeAttEntity> e = empAtd.stream()
                        .filter(employeeAttEntity -> employeeAttEntity.getDate().endsWith(date))
                        .filter(empAtd1 -> empAtd1.getEmployeeDetails().getEmployeeId().equalsIgnoreCase(e2))
                        .collect(Collectors.toList());
                    size.add(e.size());});

        Set<EmployeeEntity> e = e1.stream().map(em -> employeeDetailsRepository.findById(em).orElse(null))
                .collect(Collectors.toSet());
        List<EmployeeAttendanceSalary> eAS = new ArrayList<>();
        AtomicInteger i =  new AtomicInteger();
        e.forEach(a -> {
            EmployeeAttendanceSalary employeeAttendanceSalary = new EmployeeAttendanceSalary(
                    a.getEmployeeId(), a.getName(),
                    a.getEmployeeAddressSet().stream().map(EmployeeAddEntity::getPhoneNumber).collect(Collectors.toList()),
                    a.getEmployeeSalary().getSalary(), size.get(i.getAndIncrement()));
            eAS.add(employeeAttendanceSalary);
        });
        return eAS;

    }
    public  EmployeeSalaryDetails getEmployeeWithSalary(EmployeeEntity employeeEntity){
        return new EmployeeSalaryDetails(
                employeeEntity.getEmployeeId(), employeeEntity.getName(), employeeEntity.getEmail(),
                employeeEntity.getEmployeeSalary().getSalary(), employeeEntity.getEmployeeSalary().getPayable());
    }
    public EmployeeDetails getEmployeeModel(EmployeeEntity employeeEntity){
        EmployeeDetails employeeDetails = new EmployeeDetails(employeeEntity.getEmployeeId(),
                employeeEntity.getName(),employeeEntity.getEmail());

        EmployeeSalary employeeSalary = new EmployeeSalary(employeeEntity.getEmployeeSalary().getEmployeeId(),
                employeeEntity.getEmployeeSalary().getSalary(),employeeEntity.getEmployeeSalary().getPayable());
        employeeDetails.setEmployeeSalaryList(employeeSalary);

        List<EmployeeAddress> employeeAddresses = new ArrayList<>();
        employeeEntity.getEmployeeAddressSet().forEach(employeeAddEntity -> {
            EmployeeAddress employeeAddress = new EmployeeAddress(employeeAddEntity.getEmployeeId(),employeeAddEntity.getLine1(),employeeAddEntity.getLine2(),
                    employeeAddEntity.getCity(),employeeAddEntity.getPhoneNumber(),employeeAddEntity.getCountry());
            employeeAddresses.add(employeeAddress);
        });

        employeeDetails.setEmployeeAddressList(employeeAddresses);
        List<EmployeeAttendance> employeeAttendances = new ArrayList<>();
        employeeEntity.getEmployeeAttendanceList().forEach(employeeAttEntity -> {
            EmployeeAttendance employeeAttendance = new EmployeeAttendance(employeeAttEntity.getEmployeeId(),
                    employeeAttEntity.getDate(),employeeAttEntity.isHoliday(),employeeAttEntity.getReasonForHoliday());
            employeeAttendances.add(employeeAttendance);
        });
        employeeDetails.setEmployeeAttendanceList(employeeAttendances);
        return  employeeDetails;
    }
}
