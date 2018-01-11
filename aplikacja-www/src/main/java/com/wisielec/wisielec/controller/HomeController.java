package com.wisielec.wisielec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Emerex on 08/12/2017.
 */

@Controller
public class HomeController {
    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("pageTitle","Strona główna");
        return "index";
    }
}
