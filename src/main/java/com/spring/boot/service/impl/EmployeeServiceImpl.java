package com.spring.boot.service.impl;

import com.spring.boot.model.JobTitle;
import com.spring.boot.repository.EmployeeRepository;
import com.spring.boot.model.Department;
import com.spring.boot.model.Employee;
import com.spring.boot.service.DepartmentService;
import com.spring.boot.service.EmployeeService;
import com.spring.boot.service.JobTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private JobTitleService jobTitleService;

    @Override
    public String saveEmployee(List<Employee> employee) {
        String status = "Employee(s) saved successfully";
        try {
            employee.forEach(emp -> {
                // Validate department exists before saving employee
                Department department = departmentService.getDepartmentById(emp.getDepartment().getDepartmentId());
                if(department == null) {
                    throw new RuntimeException("Department not found with id: " + emp.getDepartment().getDepartmentId());
                }
                emp.setDepartment(department);
                employeeRepository.saveAll(employee);
            });
        } catch (Exception e) {
            status = "Error saving employee(s): " + e.getMessage();
        }
        return status;
    }

    @Override
    public String updateEmployee(List<Employee> employeeList) {
        String status = "Employee updated successfully";
        try {
            employeeList.forEach(employee -> {

            Employee existingEmployee = employeeRepository.findById(employee.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employee.getEmployeeId()));
            existingEmployee.setFirstName(employee.getFirstName());
            existingEmployee.setLastName(employee.getLastName());
            existingEmployee.setGender(employee.getGender());
            existingEmployee.setHiredate(employee.getHiredate());
            existingEmployee.setDateOfBirth(employee.getDateOfBirth());
            JobTitle jobTitle =jobTitleService.getJobById(employee.getJobTitle().getJobTitleId());
            if(jobTitle == null) {
                throw new RuntimeException("Job Title not found with id: " + employee.getJobTitle().getJobTitleId());
            }
            existingEmployee.setJobTitle(jobTitle);
            existingEmployee.setSalary(employee.getSalary());
            existingEmployee.setPhoneNumber(employee.getPhoneNumber());
            existingEmployee.setEmail(employee.getEmail());
            existingEmployee.setStatus(employee.getStatus());
            Department department = departmentService.getDepartmentById(employee.getDepartment().getDepartmentId());
            if(department == null) {
                throw new RuntimeException("Department not found with id: " + employee.getDepartment().getDepartmentId());
            }
            existingEmployee.setDepartment(department);
            employeeRepository.save(existingEmployee);
            });
        } catch (Exception e) {
            status = "Error updating employee: " + e.getMessage();
        }
        return status;
    }

    @Override
    public List<Employee> getEmployeesListBasedOnDepartmentId(Integer departmentId) {
        List<Employee> employees = employeeRepository.findEmployeesByDepartmentId(departmentId);
        if (employees.isEmpty()) {
            throw new RuntimeException("No employees found for department id: " + departmentId);
        }
        return employees;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
