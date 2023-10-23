package com.sharmal.Employee.system.service;

import com.sharmal.Employee.system.model.Employee;

import java.util.List;

public interface EmployeeService {
    public Employee createEmployee(Employee employee);

    public List<Employee> getAllEmployee();

    public Employee getEmployeeById(int employeeId);
    public Employee updateEmployee(int employeeId ,Employee employee);

    public void deleteEmployee(int employeeId);
}
