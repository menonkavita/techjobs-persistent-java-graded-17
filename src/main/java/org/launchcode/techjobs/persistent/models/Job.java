package org.launchcode.techjobs.persistent.models;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Job extends AbstractEntity {

//    Came with Starter Code
//    @Id
//    @GeneratedValue
//    private int id;
//    private String name;
//    private String employer;
//    private String skills;


    // Task 3.Job.3
    @ManyToOne
    private Employer employer;

    @ManyToMany             // --- Don't think mappedBy to field in Skill is required here
    private List<Skill> skills = new ArrayList<>();  // make final??


    public Job() {
    }

    // Initialize the id and value fields.
    public Job(Employer anEmployer, List<Skill> someSkills) {
        super();
        this.employer = anEmployer;
        this.skills = someSkills;
    }

    // Getters and setters.
    //    public String getName() {
    //        return name;
    //    }
    //
    //    public void setName(String name) {
    //        this.name = name;
    //    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }


    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }


    // --- toString not required since AbstractEntity has it

}
