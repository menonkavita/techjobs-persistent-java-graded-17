package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
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
        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                       Errors errors, Model model, @RequestParam int employerId) {

        if (errors.hasErrors()) {
	    model.addAttribute("title", "Add Job");
            return "add";
        }

        Optional <Employer> emp = employerRepository.findById(employerId);
        if(emp.isEmpty()){
            model.addAttribute("title", "Invalid Employer ");
        }
        else{
            Employer employer = emp.get();      // Gets the Employer object based on id
            newJob.setEmployer(employer);       // Setting the employer name for new job object
        }

        jobRepository.save(newJob);
        return "redirect:/";
    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {

        Optional<Job> jobObj = jobRepository.findById(jobId);

        if(jobObj.isEmpty()){
            model.addAttribute("title", "Job Id " + jobId + " doesn't exist");      // Msg will never be displayed coz user will click on Job from index page
            model.addAttribute("job", jobObj.get() );
            //return "redirect:/";
            //return "view";
        }
        else{
            Job job = jobObj.get();
            model.addAttribute("title", "Job: " + job.getName());
            model.addAttribute("job", job);
        }
            return "view";
    }

}
