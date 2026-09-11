package com.spring.boot.repository.impl;

import com.spring.boot.model.Department;
import com.spring.boot.repository.DepartmentRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryDepartmentRepository implements DepartmentRepository {

    private final Map<Integer, Department> store = new ConcurrentHashMap<>();

    public InMemoryDepartmentRepository() {
        initializeDummyData();
    }

    private void initializeDummyData() {
        save(createDepartment(1, "Engineering"));
        save(createDepartment(2, "Human Resources"));
        save(createDepartment(3, "Finance"));
        save(createDepartment(4, "Sales"));
        save(createDepartment(5, "Information Technology"));
    }

    private Department createDepartment(Integer id, String name) {
        Department department = new Department();
        department.setDepartmentId(id);
        department.setDepartmentName(name);
        return department;
    }

    @Override
    public List<Department> saveAll(Iterable<Department> departments) {
        List<Department> saved = new ArrayList<>();
        departments.forEach(department -> {
            store.put(department.getDepartmentId(), department);
            saved.add(department);
        });
        return saved;
    }

    @Override
    public Optional<Department> findById(Integer id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Department> findByDepartmentName(String name) {
        return store.values().stream()
                .filter(department -> Objects.equals(department.getDepartmentName(), name))
                .findFirst();
    }

    @Override
    public List<Department> findAll() {
        return new ArrayList<>(store.values());
    }

    private Department save(Department department) {
        store.put(department.getDepartmentId(), department);
        return department;
    }
}
