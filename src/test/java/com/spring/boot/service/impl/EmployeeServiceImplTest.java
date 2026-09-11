package com.spring.boot.service.impl;

import com.spring.boot.model.Department;
import com.spring.boot.model.Employee;
import com.spring.boot.model.JobTitle;
import com.spring.boot.repository.EmployeeRepository;
import com.spring.boot.service.DepartmentService;
import com.spring.boot.service.JobTitleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private DepartmentService departmentService;
    @Mock
    private JobTitleService jobTitleService;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveEmployee_success() {
        Department department = new Department();
        department.setDepartmentId(1);
        JobTitle jobTitle = new JobTitle();
        jobTitle.setJobTitleId(1);
        Employee employee = new Employee();
        employee.setDepartment(department);
        employee.setJobTitle(jobTitle);
        List<Employee> employees = Collections.singletonList(employee);
        when(departmentService.getDepartmentById(1)).thenReturn(department);
        when(jobTitleService.getJobById(1)).thenReturn(jobTitle);
        when(employeeRepository.saveAll(anyList())).thenReturn(employees);
        String result = employeeService.saveEmployee(employees);
        assertEquals("Employee(s) saved successfully", result);
    }

    @Test
    void testSaveEmployee_departmentNotFound() {
        Department department = new Department();
        department.setDepartmentId(2);
        Employee employee = new Employee();
        employee.setDepartment(department);
        List<Employee> employees = Collections.singletonList(employee);
        when(departmentService.getDepartmentById(2)).thenReturn(null);
        String result = employeeService.saveEmployee(employees);
        assertTrue(result.contains("Error saving employee(s): Department not found with id: 2"));
    }

    @Test
    void testUpdateEmployee_success() {
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        Department department = new Department();
        department.setDepartmentId(1);
        JobTitle jobTitle = new JobTitle();
        jobTitle.setJobTitleId(1);
        employee.setDepartment(department);
        employee.setJobTitle(jobTitle);
        List<Employee> employees = Collections.singletonList(employee);
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(departmentService.getDepartmentById(1)).thenReturn(department);
        when(jobTitleService.getJobById(1)).thenReturn(jobTitle);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        String result = employeeService.updateEmployee(employees);
        assertEquals("Employee updated successfully", result);
    }

    @Test
    void testUpdateEmployee_employeeNotFound() {
        Employee employee = new Employee();
        employee.setEmployeeId(2);
        List<Employee> employees = Collections.singletonList(employee);
        when(employeeRepository.findById(2)).thenReturn(Optional.empty());
        String result = employeeService.updateEmployee(employees);
        assertTrue(result.contains("Error updating employee: Employee not found with id: 2"));
    }

    @Test
    void testUpdateEmployee_jobTitleNotFound() {
        JobTitle jobTitle = new JobTitle();
        jobTitle.setJobTitleId(3);
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setJobTitle(jobTitle);
        List<Employee> employees = Collections.singletonList(employee);
        Employee existingEmployee = new Employee();
        existingEmployee.setEmployeeId(1);
        when(employeeRepository.findById(1)).thenReturn(Optional.of(existingEmployee));
        when(jobTitleService.getJobById(3)).thenReturn(null);
        String result = employeeService.updateEmployee(employees);
        assertTrue(result.contains("Error updating employee: Job Title not found with id: 3"));
    }
}
