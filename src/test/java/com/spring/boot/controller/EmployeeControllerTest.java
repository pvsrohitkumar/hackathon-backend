package com.spring.boot.controller;

import com.spring.boot.model.Employee;
import com.spring.boot.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEmployee() {
        List<Employee> employees = Arrays.asList(new Employee(), new Employee());
        when(employeeService.saveEmployee(employees)).thenReturn("Created");
        ResponseEntity<String> response = employeeController.createEmployee(employees);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Created", response.getBody());
        verify(employeeService, times(1)).saveEmployee(employees);
    }

    @Test
    void testUpdateEmployee() {
        List<Employee> employees = Arrays.asList(new Employee(), new Employee());
        when(employeeService.updateEmployee(employees)).thenReturn("Updated");
        ResponseEntity<String> response = employeeController.updateEmployee(employees);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated", response.getBody());
        verify(employeeService, times(1)).updateEmployee(employees);
    }

    @Test
    void testGetEmployeesListBasedOnDepartmentId_found() {
        List<Employee> employees = Arrays.asList(new Employee(), new Employee());
        when(employeeService.getEmployeesListBasedOnDepartmentId(1)).thenReturn(employees);
        ResponseEntity<List<Employee>> response = employeeController.getEmployeesListBasedOnDepartmentId(1);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employees, response.getBody());
        verify(employeeService, times(1)).getEmployeesListBasedOnDepartmentId(1);
    }

    @Test
    void testGetEmployeesListBasedOnDepartmentId_notFound() {
        when(employeeService.getEmployeesListBasedOnDepartmentId(2)).thenReturn(Collections.emptyList());
        ResponseEntity<List<Employee>> response = employeeController.getEmployeesListBasedOnDepartmentId(2);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(employeeService, times(1)).getEmployeesListBasedOnDepartmentId(2);
    }
}

