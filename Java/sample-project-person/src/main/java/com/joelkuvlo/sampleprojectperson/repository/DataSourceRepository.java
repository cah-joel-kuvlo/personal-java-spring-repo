package com.joelkuvlo.sampleprojectperson.repository;

import com.joelkuvlo.sampleprojectperson.exceptions.InvalidInputException;
import com.joelkuvlo.sampleprojectperson.model.Person;

import java.util.List;

public interface DataSourceRepository {
    void createTable();

    void save(Person person) throws InvalidInputException;

    List<Person> findAll();

    Person findById(Long id);

    void update(Person person) throws InvalidInputException;

    void delete(Long id);
}
