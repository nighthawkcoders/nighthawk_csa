package com.nighthawk.csa.data.SQL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Valid;

@Controller
public class ScheduleSqlMvcController implements WebMvcConfigurer {

    @Autowired
    private ScheduleSqlRepository repository;

    @GetMapping("/schedulenow")
    public String schedulenow(Schedule schedule) {
        return "/services/schedulenow";}

    @PostMapping("/schedulenow")
    public String scheduleSave(@Valid Schedule schedule , BindingResult bindingResult) {
        // repository.save(schedule);
        System.out.println(schedule.getName());
        return "/services/scheduleConfirm";
    }
}
