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
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    EmployeeDetailsRepository employeeDetailsRepository;
    @Autowired
    EmployeeSalaryRepository employeeSalaryRepo;
    @Autowired
    EmployeeAddressRepository employeeAddressRepo;
    @Autowired
    EmployeeAttendanceRepository employeeAttendanceRepo;


    public EmployeeEntity addEmployee(EmployeeDetails employeeModel){
        EmployeeEntity employeeEntity = new EmployeeEntity(employeeModel.getId(),employeeModel.getName(),
                employeeModel.getEmail());
        EmployeeSalaryEntity employeeSalaryEntity = new EmployeeSalaryEntity();
        employeeSalaryEntity.setSalary(employeeModel.getEmployeeSalaryList().getSalary());
        employeeSalaryEntity.setPayable(employeeModel.getEmployeeSalaryList().getPayable());
        employeeSalaryEntity.setEmployeeDetails(employeeEntity);
        employeeSalaryRepo.save(employeeSalaryEntity);
        employeeEntity.setEmployeeSalary(employeeSalaryEntity);

        List<EmployeeAddEntity> employeeAddresses = new ArrayList<>();
        employeeModel.getEmployeeAddressList().forEach(employeeAddress -> {
            EmployeeAddEntity employeeAddEntity = new EmployeeAddEntity();
            employeeAddEntity.setLine1(employeeAddress.getLine1());
            employeeAddEntity.setLine2(employeeAddress.getLine2());
            employeeAddEntity.setCity(employeeAddress.getCity());
            employeeAddEntity.setPhoneNumber(employeeAddress.getPhoneNumber());
            employeeAddEntity.setCountry(employeeAddress.getCountry());
            employeeAddEntity.setEmployeeDetails(employeeEntity);
            employeeAddresses.add(employeeAddressRepo.save(employeeAddEntity));
        });
        List<EmployeeAttEntity> employeeAttendanceEntities =new ArrayList<>();
        employeeModel.getEmployeeAttendanceList().forEach(employeeAttendance -> {
            EmployeeAttEntity employeeAttEntity = new EmployeeAttEntity();
            employeeAttEntity.setDate(employeeAttendance.getDate());
            employeeAttEntity.setHoliday(employeeAttendance.isHoliday());
            employeeAttEntity.setReasonForHoliday(employeeAttendance.getReasonForHoliday());
            employeeAttEntity.setEmployeeDetails(employeeEntity);
            employeeAttendanceRepo.save(employeeAttEntity);
            employeeAttendanceEntities.add(employeeAttEntity);
        });
        employeeEntity.setEmployeeAddressSet(employeeAddresses);
      employeeEntity.setEmployeeAttendanceList(employeeAttendanceEntities);
      return employeeDetailsRepository.save(employeeEntity);
    }
    public Set<EmployeeDetails> getEmployeeByCountry(String country){
                List<EmployeeAddEntity> a = employeeAddressRepo.findByCountryIgnoreCase(country);
      Set<EmployeeEntity> b = a.stream().map(EmployeeAddEntity::getEmployeeDetails).collect(Collectors.toSet());
      return  b.stream().map(this::getEmployeeModel).collect(Collectors.toSet());
    }
    public Set<EmployeeDetails> getEmployeeByCity(String city1, String city2){
        List<EmployeeAddEntity> a = employeeAddressRepo.findByCityOrCityIgnoreCase(city1,city2);
        Set<String> a1 = a.stream().map(EmployeeAddEntity::getEmployeeDetails).map(EmployeeEntity::getEmployeeId).collect(Collectors.toSet());
        return   a1.stream().map(e -> employeeDetailsRepository.findById(e).orElse(null)).map(this::getEmployeeModel).collect(Collectors.toSet());
    }
    public Set<EmployeeDetails> getEmployeeByCityAndCountry(String city, String country){
        List<EmployeeAddEntity> a = employeeAddressRepo.findByCityAndCountryIgnoreCase(city,country);
        return   a.stream().map(EmployeeAddEntity::getEmployeeDetails).map(this::getEmployeeModel).collect(Collectors.toSet());
    }

    public List<EmployeeSalaryDetails> getEmployeeSalaryDetails(){
       List<EmployeeEntity> empDls = employeeDetailsRepository.findAll();
      return empDls.stream().map(this::getEmployeeWithSalary).collect(Collectors.toList());
    }
    public List<EmployeeSalaryDetails> getEmployeeSalaryDetails(String payable){

        List<EmployeeSalaryEntity> sal =employeeSalaryRepo.findByPayableIgnoreCaseContaining(payable);
        List<EmployeeEntity> emp = sal.stream().map(EmployeeSalaryEntity::getEmployeeDetails).collect(Collectors.toList());
        return emp.stream().map(this::getEmployeeWithSalary).collect(Collectors.toList());
    }
    public List<EmployeeAttendanceSalary> getEmployeeAttend(String date){
//       List<EmployeeAttendanceEntity> empAtd = employeeAttendanceRepo.findByDateContains(date);
        List<EmployeeAttEntity> empAtd = employeeAttendanceRepo.findByHolidayTrue();
        Set<String> e1 = empAtd.stream().map(EmployeeAttEntity::getEmployeeDetails).map(EmployeeEntity::getEmployeeId).collect(Collectors.toSet());
        List<Integer> size = new ArrayList<>();
                e1.forEach(e2 -> {List<EmployeeAttEntity> e = empAtd.stream().filter(employeeAttEntity -> employeeAttEntity.getDate().endsWith(date)).filter(empAtd1 -> empAtd1.getEmployeeDetails().getEmployeeId().equalsIgnoreCase(e2)).collect(Collectors.toList()); size.add(e.size());});
        List<EmployeeAttEntity> empAtt = empAtd.stream().filter(EmployeeAttEntity::isHoliday).collect(Collectors.toList());
        System.out.println(empAtt);
      Set<EmployeeEntity> e = e1.stream().map(em -> employeeDetailsRepository.findById(em).orElse(null)).collect(Collectors.toSet());
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
