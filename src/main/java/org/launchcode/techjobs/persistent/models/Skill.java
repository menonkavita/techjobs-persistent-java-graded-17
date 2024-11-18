package org.launchcode.techjobs.persistent.models;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Skill extends AbstractEntity {

    @NotBlank(message="Skill cannot be left blank")
    @Size(min = 3, max=150, message="Skill has to be between 3 and 150 characters long")
    private String description;


    @ManyToMany(mappedBy = "skills")
    private List<Job> jobs = new ArrayList<>();


    // no-arg / empty Constructor sent to add.html
    public Skill(){}


    // accessor methods
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
}
