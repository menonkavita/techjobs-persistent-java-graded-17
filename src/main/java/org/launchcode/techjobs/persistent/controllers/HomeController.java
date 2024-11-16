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
                                    Errors errors, Model model,
                                    @RequestParam int employerId,
                                    @RequestParam List<Integer> skills) {

        if (errors.hasErrors()) {       // checking if job model is valid, then query database for skills
	        model.addAttribute("title", "Add Job");
            return "add";
        }

        // Employer
        Optional <Employer> employerOptional = employerRepository.findById(employerId);

        //if(emp.isEmpty()){                // Check for empty Employer obj not required, since picking employer from select box
        Employer employer = employerOptional.get();      // Gets the Employer object based on id
        newJob.setEmployer(employer);       // Setting the employer name for new job object


        // Skills
//        List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);
        List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);
        //             skillRepository.findAllById((Iterable<Integer>) any);
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

}
