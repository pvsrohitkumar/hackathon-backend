package com.spring.boot.service.impl;

import com.spring.boot.repository.DepartmentRepository;
import com.spring.boot.model.Department;
import com.spring.boot.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public String saveDepartment(List<Department> departmentList) {
        String status = "Department saved successfully";
        try {
            departmentRepository.saveAll(departmentList);
        } catch (Exception e) {
            status = "Error saving department: " + e.getMessage();
        }
        return status;
    }

    @Override
    public Department getDepartmentById(Integer departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + departmentId));
    }

    @Override
    public Department getDepartmentByName(String departmentName) {
        return departmentRepository.findByDepartmentName(departmentName)
                .orElseThrow(() -> new RuntimeException("Department not found with name: " + departmentName));
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
}
