package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("employers")
public class EmployerController {

    @Autowired
    private EmployerRepository employerRepository;

    // Task 2: Controllers #2
    // Add an index method that responds at /employers with a list of all employers
    // in the database
    // displaying index of Employer


    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("title", "Employers");
        model.addAttribute("employers", employerRepository.findAll());
        return "employers/index";
    }


    // displaying the Add Employer Form
    @GetMapping("add")
    public String displayAddEmployerForm(Model model) {
        model.addAttribute("title", "Add Employers");
        model.addAttribute(new Employer());

        return "employers/add";
    }

    // Adding the Employer
    @PostMapping("add")
    public String processAddEmployerForm(@ModelAttribute @Valid Employer newEmployer,
                                    Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Employers");
            return "employers/add";
        }

        employerRepository.save(newEmployer);
        //return "redirect:";             // Redirecting to path employers/
        return "redirect:/employers/";
    }


    // displaying jobs by Employer ID
    @GetMapping("view/{employerId}")
    public String displayViewEmployer(Model model, @PathVariable int employerId) {

        //Optional optEmployer = null;
        Optional<Employer> optEmployer = employerRepository.findById(employerId);   // Task 2: Controllers #4

        if (optEmployer.isPresent()) {
            Employer employer = (Employer) optEmployer.get();
            model.addAttribute("title", "Employer " + employer.getName() + " Details");
            model.addAttribute("employer", employer);

            return "employers/view";
        }
        else {
            return "redirect:../";
        }

    }
}

