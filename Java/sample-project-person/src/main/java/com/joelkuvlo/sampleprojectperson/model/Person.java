package com.joelkuvlo.sampleprojectperson.model;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;

public class Person {
    @NotNull
    @NotBlank
    @Pattern(regexp="[a-zA-Z]+", message="First Name must only contain alphabetical characters")
    private String firstName;
    private ArrayList<String> listItems;

    public Person() {
        this.firstName = firstName;
        this.listItems = listItems;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public ArrayList<String> getListItems() {
        return listItems;
    }

    public void setListItems(ArrayList<String> listItems) {
        this.listItems = listItems;
    }
}
