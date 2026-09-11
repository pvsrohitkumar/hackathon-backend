package com.spring.boot.service;

import com.spring.boot.model.Employee;

import java.util.List;

public interface EmployeeService {

    public String saveEmployee( List<Employee> employee);
    String updateEmployee(List<Employee> employee);
    List<Employee> getEmployeesListBasedOnDepartmentId(Integer departmentId);
    List<Employee> getAllEmployees();
}
