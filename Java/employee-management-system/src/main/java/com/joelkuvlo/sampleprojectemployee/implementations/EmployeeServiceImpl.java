//package com.joelkuvlo.sampleprojectemployee.implementations;
//
//import com.joelkuvlo.sampleprojectemployee.exceptions.InvalidInputException;
//import com.joelkuvlo.sampleprojectemployee.model.Employee;
//import com.joelkuvlo.sampleprojectemployee.repository.DataSourceRepository;
//import com.joelkuvlo.sampleprojectemployee.service.EmployeeService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class EmployeeServiceImpl implements EmployeeService {
//
//    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
//
//    private final DataSourceRepository dataSourceRepository;
//
//    @Autowired
//    public EmployeeServiceImpl(DataSourceRepository dataSourceRepository) {
//        this.dataSourceRepository = dataSourceRepository;
//    }
//
//    public void createTable() {
//        logger.debug("Creating table");
//        dataSourceRepository.createTable();
//    }
//
//    public void save(Employee employee) throws InvalidInputException {
//        logger.debug("Saving person {}", employee);
//        validateInput(employee);
//        dataSourceRepository.save(employee);
//    }
//
//    public List<Employee> findAll() {
//        logger.debug("Finding all persons");
//        return dataSourceRepository.findAll();
//    }
//
//    public Employee findById(Long id) {
//        logger.debug("Finding person by id {}", id);
//        return dataSourceRepository.findById(id);
//    }
//
//    public void update(Employee employee) throws InvalidInputException {
//        logger.debug("Updating person {}", employee);
//        validateInput(employee);
//        dataSourceRepository.update(employee);
//    }
//
//    public void delete(Long id) {
//        logger.debug("Deleting person by id {}", id);
//        dataSourceRepository.delete(id);
//    }
//
////    private void validateInput(Person person) throws InvalidInputException {
////        if (person == null) {
////            throw new InvalidInputException("Person object cannot be null.");
////        }
////    }
//
//    private void validateInput(Employee employee) throws InvalidInputException {
//        logger.debug("Validating person input");
//        if (employee.getFirstName() == null || employee.getFirstName().isEmpty()) {
//            throw new InvalidInputException("First name cannot be null or empty. Please try again.");
//        }
//        if (employee.getLastName() == null || employee.getLastName().isEmpty()) {
//            throw new InvalidInputException("Last name cannot be null or empty. Please try again.");
//        }
//        if (employee.getDateOfBirth() == null) {
//            throw new InvalidInputException("Date of birth cannot be null. Please try again.");
//        }
//        if (employee.getHireDate() == null) {
//            throw new InvalidInputException("Hire date cannot be null. Please try again.");
//        }
//    }
//}
//
