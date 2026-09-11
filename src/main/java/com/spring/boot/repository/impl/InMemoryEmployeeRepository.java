package com.spring.boot.repository.impl;

import com.spring.boot.model.Department;
import com.spring.boot.model.Employee;
import com.spring.boot.model.JobTitle;
import com.spring.boot.repository.EmployeeRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryEmployeeRepository implements EmployeeRepository {

    private final Map<Integer, Employee> store = new ConcurrentHashMap<>();

    public InMemoryEmployeeRepository() {
        initializeDummyData();
    }

    private void initializeDummyData() {
        Department engineering = createDepartment(1, "Engineering");
        Department hr = createDepartment(2, "Human Resources");
        Department finance = createDepartment(3, "Finance");
        Department sales = createDepartment(4, "Sales");
        Department it = createDepartment(5, "Information Technology");

        JobTitle developer = createJobTitle(1, "Software Developer");
        JobTitle seniorDeveloper = createJobTitle(2, "Senior Software Developer");
        JobTitle lead = createJobTitle(3, "Technical Lead");
        JobTitle architect = createJobTitle(4, "Software Architect");
        JobTitle qa = createJobTitle(5, "QA Engineer");
        JobTitle manager = createJobTitle(6, "Project Manager");
        JobTitle analyst = createJobTitle(7, "Business Analyst");

        save(createEmployee(1, "John", "Smith", "Male", "1990-05-15", "2020-01-10",
                75000, "john.smith@example.com", "9876543210", "ACTIVE", engineering, developer));
        save(createEmployee(2, "Priya", "Sharma", "Female", "1992-08-20", "2019-06-17",
                92000, "priya.sharma@example.com", "9876543211", "ACTIVE", engineering, seniorDeveloper));
        save(createEmployee(3, "Michael", "Brown", "Male", "1987-03-12", "2017-04-03",
                110000, "michael.brown@example.com", "9876543212", "ACTIVE", engineering, lead));
        save(createEmployee(4, "Anita", "Patel", "Female", "1989-11-25", "2018-09-10",
                105000, "anita.patel@example.com", "9876543213", "ACTIVE", engineering, architect));
        save(createEmployee(5, "Robert", "Wilson", "Male", "1991-01-30", "2021-02-15",
                68000, "robert.wilson@example.com", "9876543214", "ACTIVE", engineering, qa));
        save(createEmployee(6, "Neha", "Reddy", "Female", "1993-07-18", "2022-05-09",
                72000, "neha.reddy@example.com", "9876543215", "ACTIVE", hr, analyst));
        save(createEmployee(7, "David", "Taylor", "Male", "1985-10-05", "2016-08-22",
                98000, "david.taylor@example.com", "9876543216", "ACTIVE", finance, manager));
        save(createEmployee(8, "Sneha", "Kumar", "Female", "1994-02-14", "2023-01-16",
                65000, "sneha.kumar@example.com", "9876543217", "ACTIVE", sales, analyst));
        save(createEmployee(9, "James", "Anderson", "Male", "1988-12-01", "2019-11-04",
                87000, "james.anderson@example.com", "9876543218", "ACTIVE", it, seniorDeveloper));
        save(createEmployee(10, "Lakshmi", "Nair", "Female", "1990-06-22", "2020-07-13",
                115000, "lakshmi.nair@example.com", "9876543219", "ACTIVE", it, architect));
    }

    private Employee createEmployee(Integer id, String firstName, String lastName, String gender,
                                    String dateOfBirth, String hireDate, double salary,
                                    String email, String phoneNumber, String status,
                                    Department department, JobTitle jobTitle) {
        Employee employee = new Employee();
        employee.setEmployeeId(id);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setGender(gender);
        employee.setDateOfBirth(dateOfBirth);
        employee.setHiredate(hireDate);
        employee.setSalary(salary);
        employee.setEmail(email);
        employee.setPhoneNumber(phoneNumber);
        employee.setStatus(status);
        employee.setDepartment(department);
        employee.setJobTitle(jobTitle);
        return employee;
    }

    private Department createDepartment(Integer id, String name) {
        Department department = new Department();
        department.setDepartmentId(id);
        department.setDepartmentName(name);
        return department;
    }

    private JobTitle createJobTitle(Integer id, String name) {
        JobTitle jobTitle = new JobTitle();
        jobTitle.setJobTitleId(id);
        jobTitle.setJobTitleName(name);
        return jobTitle;
    }

    @Override
    public List<Employee> saveAll(Iterable<Employee> employees) {
        List<Employee> saved = new ArrayList<>();
        employees.forEach(employee -> {
            store.put(employee.getEmployeeId(), employee);
            saved.add(employee);
        });
        return saved;
    }

    @Override
    public Employee save(Employee employee) {
        store.put(employee.getEmployeeId(), employee);
        return employee;
    }

    @Override
    public Optional<Employee> findById(Integer id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Employee> findEmployeesByDepartmentId(Integer departmentId) {
        return store.values().stream()
                .filter(employee -> employee.getDepartment() != null
                        && Objects.equals(employee.getDepartment().getDepartmentId(), departmentId))
                .toList();
    }

    @Override
    public List<Employee> findAll() {
        return new ArrayList<>(store.values());
    }
}
