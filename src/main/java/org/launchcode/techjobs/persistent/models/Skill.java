package org.launchcode.techjobs.persistent.models;


import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Skill extends AbstractEntity {

    @NotBlank(message="Skill cannot be left blank")
    @Size(min = 3, max=150, message="Skill has to be between 3 and 150 characters long")
    private String description;

    public Skill(){}        // no-arg Constructor


    // Constructor with parameters??


    // accessor methods
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
