package com.joelkuvlo.sampleprojectperson.service;

import com.joelkuvlo.sampleprojectperson.exceptions.InvalidInputException;
import com.joelkuvlo.sampleprojectperson.model.Employee;

import java.util.List;

public interface EmployeeService {
    void createTable();
    void save(Employee employee) throws InvalidInputException;
    List<Employee> findAll();
    Employee findById(Long id);
    void update(Employee employee) throws InvalidInputException;
    void delete(Long id);
}


