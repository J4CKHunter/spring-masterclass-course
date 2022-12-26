package com.eazybytes.eazyschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

//    @RequestMapping("/home")
//    public String displayHomepage(Model model){
//        model.addAttribute("username", "Erdem Nayin");
//        return "home.html";
//    }

    @RequestMapping(value = {"","/", "home"})
    public String displayHomepage(){
//        throw new RuntimeException("Throwing runtime error intentionally.");
        return "home.html";
    }

//    @ExceptionHandler(Exception.class)
//    public ModelAndView exceptionHandler(Exception exception){
//        ModelAndView errorPage = new ModelAndView();
//        errorPage.setViewName("error");
//        errorPage.addObject("errorMessage", exception.getMessage());
//        return errorPage;
//    }

}
