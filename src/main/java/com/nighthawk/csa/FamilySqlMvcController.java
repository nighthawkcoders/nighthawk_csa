package com.nighthawk.csa;

import com.nighthawk.csa.model.SQL.*;
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
public class FamilySqlMvcController implements WebMvcConfigurer {

    @Autowired
    private FamilySqlRepository familySqlRepository;

    @Autowired
    private PersonSqlRepository personSqlRepository;

    @GetMapping("/sql/family")
    public String family(Model model) {
        model.addAttribute("list", familySqlRepository.listAll());
        return "sql/family";
    }

    /*  The HTML template Forms and Model attributes are bound
        @return - Template for form
        @param -  Class for form
    */
    @GetMapping("/sql/familycreate")
    public String familyCreate(Model model) {
        model.addAttribute("family", new Family());
        model.addAttribute("listPersons", personSqlRepository.listAll());
        return "sql/familyform";
    }

    @GetMapping("/sql/familyupdate/{id}")
    public String familyUpdate(@PathVariable("id") int id, Model model) {
        model.addAttribute("edit_id", id);  //passed to support using one form
        model.addAttribute("family", familySqlRepository.get(id));
        model.addAttribute("listPersons", personSqlRepository.listAll());
        return "sql/familyform";
    }

    /* Gathers the attributes filled out in the form, tests for and retrieves validation error
    @param - object with @Valid
    @param - BindingResult object
     */
    @PostMapping("/sql/familysave")
    public String saveData(@Valid Family family, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // Validation of Family attributes, validation of nested object supported
        if (bindingResult.hasErrors()) {
            return "sql/familyform";
        }
        // Redirect to next step
        familySqlRepository.save(family);
        return "redirect:/sql/family";
    }

    @GetMapping("/sql/familydelete/{id}")
    public String familyDelete(@PathVariable("id") long id) {
        familySqlRepository.delete(id);
        return "redirect:/sql/family";
    }
}