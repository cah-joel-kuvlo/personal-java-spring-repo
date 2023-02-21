package com.joelkuvlo.sampleprojectperson.service;

import com.joelkuvlo.sampleprojectperson.exceptions.InvalidInputException;
import com.joelkuvlo.sampleprojectperson.model.Person;

import java.util.List;

public interface PersonService {
    void createTable();
    void save(Person person) throws InvalidInputException;
    List<Person> findAll();
    Person findById(Long id);
    void update(Person person) throws InvalidInputException;
    void delete(Long id);
}


