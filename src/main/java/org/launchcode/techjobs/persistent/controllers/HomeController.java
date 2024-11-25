package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private SkillRepository skillRepository;


    @RequestMapping("/")
    public String index(Model model) {

        model.addAttribute("title", "MyJobs");
        model.addAttribute("jobs", jobRepository.findAll());
        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
	model.addAttribute("title", "Add Job");
        model.addAttribute(new Job());
        model.addAttribute("employers", employerRepository.findAll());
        model.addAttribute("skills", skillRepository.findAll());
        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                    Errors errors,
                                    Model model,
                                    @RequestParam int employerId,
                                    @RequestParam List<Integer> skills) {

        if (errors.hasErrors()) {       // checking if job model is valid, then query database for skills
	        model.addAttribute("title", "Add Job");
            return "redirect: add";
        }

        // Employer
        Optional <Employer> employerOptional = employerRepository.findById(employerId);

        // Check for empty Employer obj not required since picking employer from select box
        // But tests failing, so adding check to see if Optional Employee has value in it
        if(employerOptional.isEmpty()) {
            model.addAttribute("title", "Invalid Employer ID: " + employerId);
        }
        else {
            Employer employer = employerOptional.get();      // Gets the Employer object based on id
            newJob.setEmployer(employer);       // Setting the employer name for new job object
        }

        // Skills
        List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);
        newJob.setSkills(skillObjs);

        jobRepository.save(newJob);
        return "redirect:/";
    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {

        Optional<Job> jobObj = jobRepository.findById(jobId);

            // if(jobObj.isEmpty()){       // -- Not required; Coz user will always click on Job from index page

            Job job = jobObj.get();
            model.addAttribute("title", "Job: " + job.getName());
            model.addAttribute("job", job);
            return "view";
    }

    // edit Jobs, Employers, Skills
    // path: localhost:8080/edit/
    @GetMapping("edit")
    public String displayEditForm(Model model){
        model.addAttribute("title", "Edit Job");
        model.addAttribute("jobs", jobRepository.findAll());

        return "edit";
    }

    // Update Job - GetMapping
    @GetMapping("update/{jobId}")
    public String updateJob(@PathVariable int jobId,  Model model){
        Optional <Job> jobOptional = jobRepository.findById(jobId);
        Job jobObj = jobOptional.get();

        model.addAttribute("title", "Update Job");
        model.addAttribute("job", jobObj);
        model.addAttribute("employers", employerRepository.findAll());
        model.addAttribute("skills", skillRepository.findAll());

        return "/update";
    }


    // PostMapping for edit button click


    // Delete pick Job to delete @GetMapping
    @GetMapping("delete")
    public String deleteJobForm(Model m){
        m.addAttribute("title", "Delete Job");
        m.addAttribute("jobs", jobRepository.findAll());
        return "/delete";
    }


    // Delete Job, Employers, Skills @GetMapping
    @GetMapping("delete-job/{id}")
    public String selectedDeleteJob(@PathVariable int id, Model m){
        Optional <Job> jobOptional = jobRepository.findById(id);
        Job jobObj = jobOptional.get();

        m.addAttribute("title", "Delete Job: " + jobObj.getName());
        m.addAttribute("job", jobObj);

        return "/delete-job";
    }


    // Delete Job, Employers, Skills @PostMapping
    @PostMapping("delete-job/{id}")
    public String processDeleteJob(@PathVariable int id){

        Optional <Job> jobOptional= jobRepository.findById(id);
        Job jobObj = jobOptional.get();

        jobRepository.delete(jobObj);

        return "redirect:/";
    }

}
