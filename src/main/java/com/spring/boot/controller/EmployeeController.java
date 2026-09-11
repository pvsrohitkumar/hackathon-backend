package com.spring.boot.controller;

import com.spring.boot.model.Employee;
import com.spring.boot.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@Tag(name = "Employee Controller", description = "APIs for managing employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @PostMapping(value = "/save")
    @Operation(summary = "Create Employees", description = "Save a list of employees")
    public ResponseEntity<String> createEmployee(@Valid @RequestBody List<Employee> employee) {
        String status=  employeeService.saveEmployee(employee);
        System.out.println(employeeService.hashCode());
        return ResponseEntity.status(HttpStatus.CREATED).body(status);
    }

    @PostMapping(value = "/update")
    @Operation(summary = "Update Employees", description = "Update a list of employees")
    public ResponseEntity<String> updateEmployee(@Valid @RequestBody List<Employee> employeeList) {
        String status = employeeService.updateEmployee(employeeList);
        return ResponseEntity.ok(status);
    }

    @GetMapping(value = "/get_employees_by_department_id/{departmentId}")
    @Operation(summary = "Get Employees by Department ID", description = "Retrieve a list of employees based on department ID")
    public ResponseEntity<List<Employee>> getEmployeesListBasedOnDepartmentId(@PathVariable Integer departmentId) {
        List<Employee> employeeList = employeeService.getEmployeesListBasedOnDepartmentId(departmentId);
        if (employeeList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(employeeList);
    }

    @GetMapping(value = "/get_all_employees")
    @Operation(summary = "Get All Employees", description = "Retrieve a list of all employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        System.out.println(employeeService.hashCode());
        List<Employee> employeeList = employeeService.getAllEmployees();
        if (employeeList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(employeeList);
    }
}
