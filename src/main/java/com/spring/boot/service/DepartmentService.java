package com.spring.boot.service;

import com.spring.boot.model.Department;
import jakarta.validation.Valid;

import java.util.List;

public interface DepartmentService {
    String saveDepartment(@Valid List<Department> departmentList);

    Department getDepartmentById(Integer id);

    Department getDepartmentByName(String departmentName);

    List<Department> getAllDepartments();
}
