package com.spring.boot.controller;

import com.spring.boot.model.Department;
import com.spring.boot.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
@Tag(name = "Department Controller", description = "APIs for managing departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/save_department")
    @Operation(summary = "Save Departments", description = "Saves a list of departments")
    public ResponseEntity<String> saveDepartment(@RequestBody List<Department> departmentList) {
        // Logic to save department
        String status = departmentService.saveDepartment(departmentList);
        return ResponseEntity.status(HttpStatus.CREATED).body(status);
    }

    @GetMapping("/get_department/{departmentId}")
    @Operation(summary = "Get Department by ID", description = "Retrieves a department by its ID")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Integer departmentId) {
        // Logic to get department by ID
        Department department = departmentService.getDepartmentById(departmentId);
        return ResponseEntity.ok(department);
    }

    @GetMapping("/get_all_departments")
    @Operation(summary = "Get All Departments", description = "Retrieves all departments")
    public ResponseEntity<List<Department>> getAllDepartments() {
        // Logic to get all departments
        List<Department> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/get_department_by_name/{departmentName}")
    @Operation(summary = "Get Department by Name", description = "Retrieves a department by its name")
    public ResponseEntity<Department> getDepartmentByName(@PathVariable String departmentName) {
        // Logic to get department by name
        Department department = departmentService.getDepartmentByName(departmentName);
        return ResponseEntity.ok(department);
    }
}
