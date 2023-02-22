package com.joelkuvlo.sampleprojectemployee.controllers;

import com.joelkuvlo.sampleprojectemployee.exceptions.InvalidInputException;
import com.joelkuvlo.sampleprojectemployee.model.Employee;
import com.joelkuvlo.sampleprojectemployee.service.EmployeeService;
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
public class DataSourceController {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceController.class);

    private final EmployeeService employeeService;

    @Autowired
    public DataSourceController(EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.employeeService.createTable();
    }

    @GetMapping("/MainPage")
    public String showForm(Model model) {
        logger.info("Displaying form");
        model.addAttribute("employee", new Employee());
        model.addAttribute("errorMessage", "");
        return "MainPage";
    }

    @PostMapping("/submit")
    public String submitData(@RequestParam("firstName") String firstName,
                             @RequestParam(value = "middleName", required = false) String middleName,
                             @RequestParam("lastName") String lastName,
                             @RequestParam("dateOfBirth") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
                             @RequestParam("hireDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hireDate,
                             Model model) {

        logger.info("Submitting data");

        try {
            // Create a new Person object without the empId field
            Employee newEmployee = new Employee(
                    null,
                    firstName,
                    middleName,
                    lastName,
                    dateOfBirth,
                    hireDate
            );
            employeeService.save(newEmployee);
        } catch (InvalidInputException e) {
            logger.error("Invalid input received", e);
            model.addAttribute("errorMessage", e.getMessage());
            return "MainPage";
        }

        logger.info("Data submitted successfully");
        return "redirect:/MainPage";
    }


    @GetMapping("/view")
    public String viewData(Model model) {
        logger.info("Viewing data");
        List<Employee> employees = employeeService.findAll();
        System.out.println("Employees: " + employees);
        model.addAttribute("employees", employees);
        return "ViewPage";
    }

    @GetMapping("/edit/{id}")
    public String editData(@PathVariable Long id, Model model) {
        logger.info("Editing data");
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            logger.warn("Data not found for edit");
            return "redirect:/ViewPage";
        }

        model.addAttribute("employee", employee);

        return "ViewPage";
    }

    @PostMapping("/update/{id}")
    public String updateData(@PathVariable Long id, @Valid Employee employee, BindingResult bindingResult, Model model) {
        logger.info("Updating data");
        if (bindingResult.hasErrors()) {
            logger.warn("Invalid input received");
            model.addAttribute("errorMessage", "Invalid input! Please try again.");
            return "ViewPage";
        }

        try {
            employeeService.update(employee);
        } catch (InvalidInputException e) {
            logger.error("Invalid input received", e);
            model.addAttribute("errorMessage", e.getMessage());
            return "ViewPage";
        }

        logger.info("Data updated successfully");
        return "redirect:/ViewPage";
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

        return "redirect:/ViewPage";
    }

    @PostMapping("/save")
    public String saveData(@Valid Employee employee, BindingResult bindingResult, Model model) {
        logger.info("Saving data");
        if (bindingResult.hasErrors()) {
            logger.warn("Invalid input received");
            model.addAttribute("errorMessage", "Invalid input! Please try again.");
            return "redirect:/ViewPage";
        }

        try {
            employeeService.save(employee);
        } catch (InvalidInputException e) {
            logger.error("Invalid input received", e);
            model.addAttribute("errorMessage", e.getMessage());
            return "ViewPage";
        }

        logger.info("Data saved successfully");
        return "redirect:/ViewPage";
    }
}

