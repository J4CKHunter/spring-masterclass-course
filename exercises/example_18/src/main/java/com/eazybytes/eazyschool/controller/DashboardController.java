package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Person;
import com.eazybytes.eazyschool.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class DashboardController {

    @Autowired
    PersonRepository personRepository;

    @RequestMapping("/dashboard")
    public String displayDashboard(Model model, Authentication authentication, HttpSession session){
        // burada name ile email'i çekeriz çünkü custom security'de email yolluyoruz
        Person person = personRepository.readByEmail(authentication.getName());

//         model.addAttribute("username", authentication.getName());
        model.addAttribute("username", person.getName());
         model.addAttribute("roles", authentication.getAuthorities().toString());

//         throw new RuntimeException("Throwing a runtime exception intentionally");

        if(null != person.getEazyClass() & null != person.getEazyClass().getName()){
            model.addAttribute("enrolledClass", person.getEazyClass().getName());
        }

        session.setAttribute("loggedInPerson", person);

         // yine eklemedi model'i burada
         return "dashboard.html";
    }
}
