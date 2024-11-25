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

    // edit
    @GetMapping("edit")
    public String editEmployer(Model model){
        model.addAttribute("title", "Edit Employer");
        model.addAttribute("employers", employerRepository.findAll());

        return "employers/edit";
    }

    // update: have to use to update & save new record
    // Get the record to update
    // path: /employers/update/1
    @GetMapping("update/{id}")
    public String displayUpdateEmployerForm(Model model, @PathVariable int id) {
        Optional<Employer> empObj = employerRepository.findById(id);
        if (empObj.isPresent()) {
            model.addAttribute("title", "Update Employer");
            model.addAttribute("employer", empObj.get());
            return "employers/update";
        } else {

            return "redirect:../";
        }
    }

    // Save the edited record
    @PostMapping("update/{id}")
    //@PatchMapping("update")
    //@PutMapping("update")
    public String processUpdateEmployerForm(@PathVariable int id, Employer empUpdate){

        Optional <Employer> empOpt = employerRepository.findById(id);
        Employer empObj = empOpt.get();

        empObj.setName(empUpdate.getName());
        empObj.setLocation(empUpdate.getLocation());

        employerRepository.save(empObj);
        return "redirect:/employers/";
}


    // pick employer to delete
    @GetMapping("delete")
    public String delete(Model model){
        model.addAttribute("title", "Click on Employer to Delete");
        model.addAttribute("employers", employerRepository.findAll());
        return "employers/delete";
    }


    // delete @GetMapping
    @GetMapping("delete-employer/{id}")
    public String deleteEmployer(@PathVariable int id, Model model){

        Optional <Employer> employerOptional = employerRepository.findById(id);

        if(employerOptional.isPresent()){
            Employer empObj = employerOptional.get();

            model.addAttribute("title", "Delete Employer: " + empObj.getName());
            model.addAttribute("employer", empObj);
        }
        else{
            return "employers/delete";
        }
        return "employers/delete-employer";
    }



    // delete @PostMapping
    @PostMapping("delete-employer/{id}")
    public String processDeleteEmployer(@PathVariable int id){
        Optional <Employer> employerOptional = employerRepository.findById(id);
        Employer empObj = employerOptional.get();

        employerRepository.delete(empObj);;

        return "redirect:/employers/";
    }

}

