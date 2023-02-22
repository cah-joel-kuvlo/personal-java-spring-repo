package com.joelkuvlo.sampleprojectemployee.repository;

import com.joelkuvlo.sampleprojectemployee.exceptions.InvalidInputException;
import com.joelkuvlo.sampleprojectemployee.model.Employee;

import java.util.List;

public interface DataSourceRepository {
    void createTable();

    void save(Employee employee) throws InvalidInputException;

    List<Employee> findAll();

    Employee findById(Long id);

    void update(Employee employee) throws InvalidInputException;

    void delete(Long id);
}
