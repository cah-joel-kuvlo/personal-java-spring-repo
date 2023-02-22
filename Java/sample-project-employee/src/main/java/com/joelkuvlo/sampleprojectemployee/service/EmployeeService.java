package com.joelkuvlo.sampleprojectemployee.service;

import com.joelkuvlo.sampleprojectemployee.exceptions.InvalidInputException;
import com.joelkuvlo.sampleprojectemployee.model.Employee;

import java.util.List;

public interface EmployeeService {
    void createTable();
    void save(Employee employee) throws InvalidInputException;
    List<Employee> findAll();
    Employee findById(Long id);
    void update(Employee employee) throws InvalidInputException;
    void delete(Long id);
}


