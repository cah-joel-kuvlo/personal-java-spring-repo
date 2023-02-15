package com.joelkuvlo.sampleprojectperson.controller;

import com.joelkuvlo.sampleprojectperson.DataSource;
import com.joelkuvlo.sampleprojectperson.implementation.DataSourceRepositoryImpl;
import com.joelkuvlo.sampleprojectperson.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Controller
public class DataSourceController {
    @Autowired
    private DataSourceRepositoryImpl dataSourceRepository;

    @Autowired
    public DataSourceController(DataSourceRepositoryImpl dataSourceRepository) {
        this.dataSourceRepository = dataSourceRepository;
        this.dataSourceRepository.createTable();
    }

    @GetMapping("/MainPage")
    public String showForm(Model model) {
        DataSource dataSource = new DataSource();
        Set<String> dataElements = Set.of("Element 0", "Element 1", "Element 2");
        dataSource.setDataElements(dataElements);
        dataSourceRepository.save(dataSource);
        model.addAttribute("options", dataSourceRepository.findAllDataElements());
        model.addAttribute("person", new Person());
        return "MainPage";
    }


    @PostMapping("/submit")
    public String submitData(@Valid Person person, @RequestParam("firstName") String firstName,
                             @RequestParam("list") String list,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Invalid input! Please try again.");
            return "MainPage";
        }

        person.setFirstName(firstName);
        List<String> listItems = Arrays.asList(list.split("\n"));
        person.setListItems((ArrayList<String>) listItems);


        model.addAttribute("person", person);
        return "success";
    }
}
