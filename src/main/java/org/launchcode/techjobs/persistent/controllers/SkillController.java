package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("skills")
public class SkillController {

    // Task 2: Controller #5 Crating SkillsController

    @Autowired
    private SkillRepository skillRepository;

    // index()
    // displaying index of Skill
    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("title", "Skills");
        model.addAttribute("skills", skillRepository.findAll());

        return "skills/index";
    }


    // displayAddSkillForm
    // display ass Skills form
    @GetMapping("add")
    public String displayAddSkillForm(Model model){
        model.addAttribute("title", "Skills");
        model.addAttribute(new Skill());

        return "skills/add";
    }


    // processAddSkillForm
    // Adding skills
    @PostMapping("add")
    public String processAddSkillForm(@ModelAttribute @Valid Skill newSkill, Errors err, Model model){
        if(err.hasErrors()){
            model.addAttribute("title", "Add Skills");
            return "skills/add";
        }

        skillRepository.save(newSkill);
        //return "redirect:";
        return "redirect:/skills/";
    }


    // displayViewSkill
    // display skill by ID
    @GetMapping("view/{skillId}")
    public String displayViewSkill(Model model, @PathVariable int skillId) {

        Optional<Skill> optionalSkill = skillRepository.findById(skillId);

        if(optionalSkill.isPresent()){
            Skill skill = (Skill) optionalSkill.get();    // if a value is present gets the value, else throws NoSuchElementException
            model.addAttribute("title", "Skill " + skill.getName() + " Details" );
            model.addAttribute("skill", skill);

            return "skills/view";
        }
        else{
            return "redirect:../";
        }
    }


}
