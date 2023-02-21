package com.joelkuvlo.sampleprojectperson.implementations;

import com.joelkuvlo.sampleprojectperson.exceptions.InvalidInputException;
import com.joelkuvlo.sampleprojectperson.model.Person;
import com.joelkuvlo.sampleprojectperson.repository.DataSourceRepository;
import com.joelkuvlo.sampleprojectperson.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    private final DataSourceRepository dataSourceRepository;

    @Autowired
    public PersonServiceImpl(DataSourceRepository dataSourceRepository) {
        this.dataSourceRepository = dataSourceRepository;
    }

    public void createTable() {
        logger.debug("Creating table");
        dataSourceRepository.createTable();
    }

    public void save(Person person) throws InvalidInputException {
        logger.debug("Saving person {}", person);
        validateInput(person);
        dataSourceRepository.save(person);
    }

    public List<Person> findAll() {
        logger.debug("Finding all persons");
        return dataSourceRepository.findAll();
    }

    public Person findById(Long id) {
        logger.debug("Finding person by id {}", id);
        return dataSourceRepository.findById(id);
    }

    public void update(Person person) throws InvalidInputException {
        logger.debug("Updating person {}", person);
        validateInput(person);
        dataSourceRepository.update(person);
    }

    public void delete(Long id) {
        logger.debug("Deleting person by id {}", id);
        dataSourceRepository.delete(id);
    }

//    private void validateInput(Person person) throws InvalidInputException {
//        if (person == null) {
//            throw new InvalidInputException("Person object cannot be null.");
//        }
//    }

    private void validateInput(Person person) throws InvalidInputException {
        logger.debug("Validating person input");
        if (person.getFirstName() == null || person.getFirstName().isEmpty()) {
            throw new InvalidInputException("First name cannot be null or empty. Please try again.");
        }
        if (person.getLastName() == null || person.getLastName().isEmpty()) {
            throw new InvalidInputException("Last name cannot be null or empty. Please try again.");
        }
        if (person.getDateOfBirth() == null) {
            throw new InvalidInputException("Date of birth cannot be null. Please try again.");
        }
        if (person.getHireDate() == null) {
            throw new InvalidInputException("Hire date cannot be null. Please try again.");
        }
    }
}

