package com.joelkuvlo.sampleprojectperson.controllers;

import com.joelkuvlo.sampleprojectperson.exceptions.InvalidInputException;
import com.joelkuvlo.sampleprojectperson.model.Person;
import com.joelkuvlo.sampleprojectperson.service.PersonService;
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

    private final PersonService personService;

    @Autowired
    public DataSourceController(PersonService personService) {
        this.personService = personService;
        this.personService.createTable();
    }

    @GetMapping("/MainPage")
    public String showForm(Model model) {
        logger.info("Displaying form");
        model.addAttribute("person", new Person());
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
            Person newPerson = new Person(
                    null,
                    firstName,
                    middleName,
                    lastName,
                    dateOfBirth,
                    hireDate
            );
            personService.save(newPerson);
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
        List<Person> persons = personService.findAll();
        model.addAttribute("persons", persons);
        return "SuccessPage";
    }

    @GetMapping("/edit/{id}")
    public String editData(@PathVariable Long id, Model model) {
        logger.info("Editing data");
        Person person = personService.findById(id);
        if (person == null) {
            logger.warn("Data not found for edit");
            return "redirect:/SuccessPage";
        }

        model.addAttribute("person", person);

        return "EditPage";
    }

    @PostMapping("/update/{id}")
    public String updateData(@PathVariable Long id, @Valid Person person, BindingResult bindingResult, Model model) {
        logger.info("Updating data");
        if (bindingResult.hasErrors()) {
            logger.warn("Invalid input received");
            model.addAttribute("errorMessage", "Invalid input! Please try again.");
            return "SuccessPage";
        }

        try {
            personService.update(person);
        } catch (InvalidInputException e) {
            logger.error("Invalid input received", e);
            model.addAttribute("errorMessage", e.getMessage());
            return "SuccessPage";
        }

        logger.info("Data updated successfully");
        return "redirect:/SuccessPage";
    }

    @GetMapping("/SuccessPage/{id}")
    public String deleteData(@PathVariable Long id, Model model) {
        logger.info("Deleting data");
        personService.delete(id);

        List<Person> persons = personService.findAll();
        model.addAttribute("persons", persons);

        return "SuccessPage";
    }

    @GetMapping("/add")
    public String addData(Model model) {
        logger.info("Adding data");
        model.addAttribute("person", new Person());

        return "SuccessPage";
    }

    @PostMapping("/save")
    public String saveData(@Valid Person person, BindingResult bindingResult, Model model) {
        logger.info("Saving data");
        if (bindingResult.hasErrors()) {
            logger.warn("Invalid input received");
            model.addAttribute("errorMessage", "Invalid input! Please try again.");
            return "SuccessPage";
        }

        try {
            personService.save(person);
        } catch (InvalidInputException e) {
            logger.error("Invalid input received", e);
            model.addAttribute("errorMessage", e.getMessage());
            return "SuccessPage";
        }

        logger.info("Data saved successfully");
        return "redirect:/SuccessPage";
    }
}

