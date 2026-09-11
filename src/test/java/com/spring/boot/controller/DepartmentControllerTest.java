package com.spring.boot.controller;

import com.spring.boot.model.Department;
import com.spring.boot.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartmentControllerTest {

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveDepartment() {
        List<Department> departments = Arrays.asList(new Department(), new Department());
        when(departmentService.saveDepartment(departments)).thenReturn("Saved");
        ResponseEntity<String> response = departmentController.saveDepartment(departments);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode()); // Adjusted to match controller behavior
        assertEquals("Saved", response.getBody());
        verify(departmentService, times(1)).saveDepartment(departments);
    }

    @Test
    void testGetDepartmentById() {
        Department department = new Department();
        department.setDepartmentId(1);
        department.setDepartmentName("IT");
        when(departmentService.getDepartmentById(1)).thenReturn(department);
        ResponseEntity<Department> response = departmentController.getDepartmentById(1);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(department, response.getBody());
        verify(departmentService, times(1)).getDepartmentById(1);
    }
}
