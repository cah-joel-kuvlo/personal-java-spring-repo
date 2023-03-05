package com.joelkuvlo.employeemanagementsystem.controllers;

import com.joelkuvlo.employeemanagementsystem.exceptions.InvalidInputException;
import com.joelkuvlo.employeemanagementsystem.model.Employee;
import com.joelkuvlo.employeemanagementsystem.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
public class EmployeeAppController {

  private static final Logger logger = LoggerFactory.getLogger(EmployeeAppController.class);

  private final EmployeeService employeeService;

  @Autowired
  public EmployeeAppController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping("/mainpage")
  public String showForm(Model model) {
    logger.info("Displaying form");
    model.addAttribute("employee", new Employee());
    model.addAttribute("errorMessage", "");
    return "mainpage";
  }

  @PostMapping("/submit")
  public String submitData(
      @RequestParam("firstName") String firstName,
      @RequestParam(value = "middleName", required = false) String middleName,
      @RequestParam("lastName") String lastName,
      @RequestParam("dateOfBirth") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
      @RequestParam("hireDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hireDate,
      Model model) {

    logger.info("Submitting data");

    try {
      // Create a new Person object without the empId field
      Employee newEmployee =
          new Employee(null, firstName, middleName, lastName, dateOfBirth, hireDate);
      employeeService.save(newEmployee);
    } catch (InvalidInputException e) {
      logger.error("Invalid input received", e);
      model.addAttribute("errorMessage", e.getMessage());
      return "mainpage";
    }

    logger.info("Data submitted successfully");
    return "redirect:/mainpage";
  }


  @GetMapping("/view")
  public String viewData(Model model) {
    logger.info("Viewing data");
    List<Employee> employees = employeeService.findAll();
    System.out.println("Employees: " + employees);
    model.addAttribute("employees", employees);
    return "viewpage";
  }

  @GetMapping("/edit/{id}")
  public String editData(@PathVariable Long id, Model model) {
    logger.info("Editing data");
    Employee employee = employeeService.findById(id);
    if (employee == null) {
      logger.warn("Data not found for edit");
      return "redirect:/viewpage";
    }

    model.addAttribute("employee", employee);

    return "viewpage";
  }

  @PostMapping("/update/{id}")
  public String updateData(@PathVariable Long id, @Valid Employee employee,
      BindingResult bindingResult, Model model) {
    logger.info("Updating data");
    if (bindingResult.hasErrors()) {
      logger.warn("Invalid input received");
      model.addAttribute("errorMessage", "Invalid input! Please try again.");
      return "viewpage";
    }

    try {
      employeeService.update(employee);
    } catch (InvalidInputException e) {
      logger.error("Invalid input received", e);
      model.addAttribute("errorMessage", e.getMessage());
      return "viewpage";
    }

    logger.info("Data updated successfully");
    return "redirect:/viewpage";
  }

  @GetMapping("/delete/{empId}")
  public String delete(@PathVariable Long empId, @RequestParam("redirect") String redirect) {
    employeeService.delete(empId);
    List<Employee> employees = employeeService.findAll();
    return "redirect:" + redirect;
  }


  @GetMapping("/add")
  public String addData(Model model) {
    logger.info("Adding data");
    model.addAttribute("person", new Employee());

    return "redirect:/viewpage";
  }

  @PostMapping("/save")
  public String saveData(@Valid Employee employee, BindingResult bindingResult, Model model) {
    logger.info("Saving data");
    if (bindingResult.hasErrors()) {
      logger.warn("Invalid input received");
      model.addAttribute("errorMessage", "Invalid input! Please try again.");
      return "redirect:/viewpage";
    }

    try {
      employeeService.save(employee);
    } catch (InvalidInputException e) {
      logger.error("Invalid input received", e);
      model.addAttribute("errorMessage", e.getMessage());
      return "viewpage";
    }

    logger.info("Data saved successfully");
    return "redirect:/viewpage";
  }
}

