package com.cjss.employee.controller;

import com.cjss.employee.entity.EmployeeEntity;
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

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public EmployeeEntity addEmployee(@RequestBody EmployeeDetails employeeModel){ return employeeService.addEmployee(employeeModel);}
    @RequestMapping(value = "/get/{country}",method = RequestMethod.GET)
    public Set<EmployeeDetails> getEmployeeByCountry(@PathVariable String country){ return  employeeService.getEmployeeByCountry(country);}
    @RequestMapping(value = "/get/{country}/{city2}",method = RequestMethod.GET)
    public Set<EmployeeDetails> getEmployeeByCity(@PathVariable String city1, @PathVariable String city2){ return  employeeService.getEmployeeByCity(city1,city2);}
    @RequestMapping(value = "/get/{country}/{country}",method = RequestMethod.GET)
    public Set<EmployeeDetails> getEmployeeByCityAndCountry(@PathVariable String city, @PathVariable String country){ return  employeeService.getEmployeeByCityAndCountry(city,country);}
    @RequestMapping(value = "/getSalary",method = RequestMethod.GET)
    public List<EmployeeSalaryDetails> getEmployeeSalaryDetails(){ return employeeService.getEmployeeSalaryDetails();}
    @RequestMapping(value = "/get/{payable}",method = RequestMethod.GET)
    public List<EmployeeSalaryDetails> getEmployeeSalaryDetails(@PathVariable String payable){  return employeeService.getEmployeeSalaryDetails(payable);}
    @RequestMapping(value = "/get/{date}",method = RequestMethod.GET)
    public List<EmployeeAttendanceSalary> getEmployeeAttend(@PathVariable String date){ return employeeService.getEmployeeAttend(date); }
}
