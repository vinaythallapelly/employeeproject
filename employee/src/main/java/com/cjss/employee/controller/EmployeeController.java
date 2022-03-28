package com.cjss.employee.controller;

import com.cjss.employee.entity.EmployeeDetailsEntity;
import com.cjss.employee.model.EmployeeAttendanceSalary;
import com.cjss.employee.model.EmployeeDetails;
import com.cjss.employee.model.EmployeeSalaryDetails;
import com.cjss.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @PostMapping("/add")
    public EmployeeDetailsEntity addEmployee(@RequestBody EmployeeDetails employeeModel){ return employeeService.addEmployee(employeeModel);}
    @GetMapping("/get/{country}")
    public Set<EmployeeDetails> getEmployeeByCountry(@PathVariable String country){ return  employeeService.getEmployeeByCountry(country);}
    @GetMapping("/get/{city1}/{city2}")
    public Set<EmployeeDetails> getEmployeeByCity(@PathVariable String city1, @PathVariable String city2){ return  employeeService.getEmployeeByCity(city1,city2);}
    @GetMapping("/get/{city}/{country}")
    public Set<EmployeeDetails> getEmployeeByCityAndCountry(@PathVariable String city, @PathVariable String country){ return  employeeService.getEmployeeByCityAndCountry(city,country);}
    @GetMapping("/getSalary")
    public List<EmployeeSalaryDetails> getEmployeeSalaryDetails(){ return employeeService.getEmployeeSalaryDetails();}
    @GetMapping("/get/{payable}")
    public List<EmployeeSalaryDetails> getEmployeeSalaryDetails(@PathVariable String payable){  return employeeService.getEmployeeSalaryDetails(payable);}
    @GetMapping("/get/{date}")
    public List<EmployeeAttendanceSalary> getEmployeeAttend(@PathVariable String date){ return employeeService.getEmployeeAttend(date); }
}
