package com.nighthawk.csa.data;

import com.nighthawk.csa.data.SQL.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

// Built using video: https://www.youtube.com/watch?v=ctwRpskAeIU
@Controller
public class ScrumTeamSqlMvcController implements WebMvcConfigurer {

    @Autowired
    private ScrumTeamSqlRepository scrumTeamSqlRepository;

    @Autowired
    private PersonSqlRepository personSqlRepository;

    @GetMapping("/data/scrum_team")
    public String scrumTeam(Model model) {
        model.addAttribute("list", scrumTeamSqlRepository.listAll());
        return "data/scrum_team";
    }

    /*  The HTML template Forms and Model attributes are bound
        @return - Template for form
        @param -  Class for form
    */
    @GetMapping("/data/scrum_team_create")
    public String scrumTeamCreate(Model model) {
        model.addAttribute("scrumTeam", new ScrumTeam());
        model.addAttribute("listPersons", personSqlRepository.listAll());
        return "data/scrum_team_form";
    }

    @GetMapping("/data/scrum_team_update/{id}")
    public String scrumTeamUpdate(@PathVariable("id") int id, Model model) {
        model.addAttribute("id", id);  //passed to support using one form
        model.addAttribute("scrumTeam", scrumTeamSqlRepository.get(id));
        model.addAttribute("listPersons", personSqlRepository.listAll());
        return "data/scrum_team_form";
    }

    /* Gathers the attributes filled out in the form, tests for and retrieves validation error
    @param - object with @Valid
    @param - BindingResult object
     */
    @PostMapping("/data/scrum_team_save")
    public String saveData(@Valid ScrumTeam scrumTeam, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // Validation of Family attributes, validation of nested object supported
        if (bindingResult.hasErrors()) {
            return "data/scrum_team_form";
        }
        // Redirect to next step
        scrumTeamSqlRepository.save(scrumTeam);
        return "redirect:/data/scrum_team";
    }

    @GetMapping("/data/scrum_team_delete/{id}")
    public String familyDelete(@PathVariable("id") long id) {
        scrumTeamSqlRepository.delete(id);
        return "redirect:/data/scrum_team";
    }
}