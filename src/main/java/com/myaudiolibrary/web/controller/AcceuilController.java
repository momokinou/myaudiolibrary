package com.myaudiolibrary.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AcceuilController {

    @RequestMapping(value = "")
    public String acceuil(final ModelMap m) {
        return "accueil";
    }

}
