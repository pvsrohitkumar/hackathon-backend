package com.spring.boot.service.impl;

import com.spring.boot.model.Department;
import com.spring.boot.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class DepartmentServiceImplTest {
    @Mock
    private DepartmentRepository departmentRepository;
    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveDepartment_success() {
        List<Department> departments = Arrays.asList(new Department(), new Department());
        when(departmentRepository.saveAll(anyList())).thenReturn(departments);
        String result = departmentService.saveDepartment(departments);
        assertEquals("Department saved successfully", result);
    }

    @Test
    void testSaveDepartment_exception() {
        List<Department> departments = Arrays.asList(new Department(), new Department());
        doThrow(new RuntimeException("DB error")).when(departmentRepository).saveAll(anyList());
        String result = departmentService.saveDepartment(departments);
        assertTrue(result.contains("Error saving department: DB error"));
    }

    @Test
    void testGetDepartmentById_found() {
        Department department = new Department();
        department.setDepartmentId(1);
        when(departmentRepository.findById(1)).thenReturn(Optional.of(department));
        Department result = departmentService.getDepartmentById(1);
        assertNotNull(result);
        assertEquals(1, result.getDepartmentId());
    }

    @Test
    void testGetDepartmentById_notFound() {
        when(departmentRepository.findById(2)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> departmentService.getDepartmentById(2));
        assertEquals("Department not found with id: 2", exception.getMessage());
    }
}
