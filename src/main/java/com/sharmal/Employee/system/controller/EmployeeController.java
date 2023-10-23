package com.sharmal.Employee.system.controller;

import com.sharmal.Employee.system.model.Employee;
import com.sharmal.Employee.system.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

//    Basic code
//    @PostMapping("/add")
//    public String addEmployee(@RequestBody Employee employee) {
//        employeeService.createStudent(employee);
//        return "New Employee added";
//    }

//    Method of using response entity

//    @PostMapping("/add")
//    public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
//        Employee createEmployee = employeeService.createEmployee(employee);
//        if (createEmployee != null) {
//            return new ResponseEntity<String>("New user created " + createEmployee.getName(), HttpStatus.CREATED);
//        } else {
//            return new ResponseEntity<>("Failed to create new employee", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    Advance method with better error handling
    @PostMapping("/add")
    public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
        try {
            Employee createEmployee = employeeService.createEmployee(employee);
            return new ResponseEntity<>("New user created with name: " + createEmployee.getName(), HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>("Employee with this name already exists", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return exceptionErrorhandling();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployee();
        if (employees.isEmpty()) {
            return new ResponseEntity<>("No employees found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

//    Get employee by id
    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("employeeId") int employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PutMapping("/update/{employeeId}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable("employeeId") int employeeId,
            @RequestBody Employee employee) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(employeeId, employee);
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("No employee with this Id exists", HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>("Employee with this name exists", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return exceptionErrorhandling();
        }
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("employeeId") int employeeId) {
        try {
            employeeService.deleteEmployee(employeeId);
            return new ResponseEntity<>("Employee deleted", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("No employee with this Id exists", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return exceptionErrorhandling();
        }
    }

    private ResponseEntity<String> exceptionErrorhandling() {
        return new ResponseEntity<>("Failed to due to error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
