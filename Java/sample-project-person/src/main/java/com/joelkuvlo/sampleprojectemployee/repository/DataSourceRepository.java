package com.joelkuvlo.sampleprojectperson.repository;

import com.joelkuvlo.sampleprojectperson.exceptions.InvalidInputException;
import com.joelkuvlo.sampleprojectperson.model.Employee;

import java.util.List;

public interface DataSourceRepository {
    void createTable();

    void save(Employee employee) throws InvalidInputException;

    List<Employee> findAll();

    Employee findById(Long id);

    void update(Employee employee) throws InvalidInputException;

    void delete(Long id);
}
