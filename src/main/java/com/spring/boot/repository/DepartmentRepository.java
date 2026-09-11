package com.spring.boot.repository;

import com.spring.boot.model.Department;
import java.util.List;
import java.util.Optional;

public interface DepartmentRepository {
    List<Department> saveAll(Iterable<Department> departments);
    Optional<Department> findById(Integer id);
    Optional<Department> findByDepartmentName(String name);
    List<Department> findAll();
}
