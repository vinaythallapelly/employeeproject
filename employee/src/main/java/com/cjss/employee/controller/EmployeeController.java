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
//Task-1
    @RequestMapping(value = "/getIndia",method = RequestMethod.GET)
    public Set<EmployeeDetails> getEmployeeByCountry(){ return  employeeService.getEmployeeByCountry();}
//Task-2
    @RequestMapping(value = "/get1/{city}/{city1}",method = RequestMethod.GET)
    public Set<EmployeeDetails> getEmployeeByCity(@PathVariable String city, @PathVariable String city1){ return  employeeService.getEmployeeByCity(city,city1);}
//Task-3
    @RequestMapping(value = "/get2/{city}/{country}",method = RequestMethod.GET)
    public Set<EmployeeDetails> getEmployeeByCityAndCountry(@PathVariable String city, @PathVariable String country){ return  employeeService.getEmployeeByCityAndCountry(city,country);}
//Task-4
    @RequestMapping(value = "/getSalary",method = RequestMethod.GET)
    public List<EmployeeSalaryDetails> getEmployeeSalaryDetails(){ return employeeService.getEmployeeSalaryDetails();}
//Task-5
    @RequestMapping(value = "/get3/{payable}",method = RequestMethod.GET)
    public List<EmployeeSalaryDetails> getEmployeeSalaryDetails(@PathVariable String payable){  return employeeService.getEmployeeSalaryDetails(payable);}
//Task-6
    @RequestMapping(value = "/get4/{date}",method = RequestMethod.GET)
    public List<EmployeeAttendanceSalary> getEmployeeAttend(@PathVariable String date){ return employeeService.getEmployeeAttend(date); }
}
