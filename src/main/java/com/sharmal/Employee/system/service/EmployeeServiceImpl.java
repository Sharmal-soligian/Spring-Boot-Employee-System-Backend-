package com.sharmal.Employee.system.service;

import com.sharmal.Employee.system.model.Employee;
import com.sharmal.Employee.system.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        checkEmployeeName(employee);
        return employeeRepo.save(employee);
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepo.findAll();
    }

    @Override
    public Employee getEmployeeById(int employeeId) {
        return employeeRepo.findById(employeeId).get();
    }

    @Override
    public Employee updateEmployee(int employeeId, Employee employee) {
        // check for id
        invalidIdException(employeeId);
        // check for same name
        checkEmployeeName(employee);
        employee.setId(employeeId);
        return employeeRepo.save(employee);
    }

    @Override
    public void deleteEmployee(int employeeId) {
        invalidIdException(employeeId);
        employeeRepo.deleteById(employeeId);
    }

    private void checkEmployeeName(Employee employee) {
        Optional<Employee> employeeName = employeeRepo.findByName(employee.getName());
        if (employeeName.isPresent()) {
            throw new IllegalStateException("Employee with this name already exists");
        }
    }

    private void invalidIdException(int employee) {
        Optional<Employee> empId = employeeRepo.findById(employee);
        if (!empId.isPresent()) {
            throw new IllegalArgumentException("Employee with this Id not exists");
        }
    }
}
