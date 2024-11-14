package org.launchcode.techjobs.persistent.models;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Employer extends AbstractEntity {

    // Task 2 Models(1)
    @NotBlank(message="Location cannot be left blank")
    @Size(min=3, message="Location has to be greater than 3 characters at least")
    private String location;


    // Blank or No-arg Constructor required for Hibernate to create an object
    public Employer(){}


    // Constructor with parameters??



    // accessor methods
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
