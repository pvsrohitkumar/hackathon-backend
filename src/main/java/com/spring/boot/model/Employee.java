package com.spring.boot.model;

import lombok.Data;

@Data
public class Employee {
    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String gender;
    private String dateOfBirth;
    private String hiredate;
    private double salary;
    private String email;
    private String phoneNumber;
    private String status;
    private Department department;
    private JobTitle jobTitle;
}
