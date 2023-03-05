package com.joelkuvlo.sampleprojectemployee.service;

import com.joelkuvlo.sampleprojectemployee.exceptions.InvalidInputException;
import com.joelkuvlo.sampleprojectemployee.model.Employee;
import com.joelkuvlo.sampleprojectemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

  @Autowired
  private EmployeeRepository employeeRepository;

  public List<Employee> findAll() {
    return employeeRepository.findAll();
  }

  public Employee findById(Long id) {
    Optional<Employee> result = employeeRepository.findById(id);
    return result.orElse(null);
  }

  public void save(Employee employee) throws InvalidInputException {
    if (employee == null || employee.getFirstName() == null || employee.getLastName() == null) {
      throw new InvalidInputException("Invalid input!");
    }
    employeeRepository.save(employee);
  }

  public void update(Employee employee) throws InvalidInputException {
    if (employee == null || employee.getEmpId() == null || employee.getFirstName() == null
        || employee.getLastName() == null) {
      throw new InvalidInputException("Invalid input!");
    }
    employeeRepository.save(employee);
  }

  public void delete(Long id) {
    employeeRepository.deleteById(id);
  }

}



