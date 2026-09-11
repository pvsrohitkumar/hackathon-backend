package com.spring.boot.repository;

import com.spring.boot.model.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
    List<Employee> saveAll(Iterable<Employee> employees);
    Employee save(Employee employee);
    Optional<Employee> findById(Integer id);
    List<Employee> findEmployeesByDepartmentId(Integer departmentId);
    List<Employee> findAll();
}
