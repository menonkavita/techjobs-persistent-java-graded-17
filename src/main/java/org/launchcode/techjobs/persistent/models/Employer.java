package org.launchcode.techjobs.persistent.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Employer extends AbstractEntity {

    // Task 2 Models(1)
    @NotBlank(message="Location cannot be left blank")
    @Size(min=3, max = 100, message="Location has to be between 3 and 100 characters")
    private String location;


    // Task 3.Employer.1: added job field to Employer class
    @OneToMany
    @JoinColumn(name="employer_id")
    private List<Job> jobs = new ArrayList<>();
    // joins by column employer_id; means Employer is the owner of this relationship
    // & the corresponding table (Job) has a column with a foreign key to the referenced table (Employer).


    // Blank or No-arg Constructor required for Hibernate to create an object
    // empty Constructor sent to add.html
    public Employer(){}


    // accessor methods
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
}
