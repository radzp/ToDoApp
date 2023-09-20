package com.marcin.ania.ToDoAPP.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/settings")
public class SettingsController {

    @GetMapping("")
    public String showPage(){
        return "settings";
    }


}
