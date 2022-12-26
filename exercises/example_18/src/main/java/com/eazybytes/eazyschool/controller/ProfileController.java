package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Address;
import com.eazybytes.eazyschool.model.Person;
import com.eazybytes.eazyschool.model.Profile;
import com.eazybytes.eazyschool.repository.AdressRepository;
import com.eazybytes.eazyschool.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;

@Slf4j
@Controller("ProfileControllerBean")
public class ProfileController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    AdressRepository adressRepository;

    @RequestMapping("/displayProfile")
    public ModelAndView displayMessages(Model model, HttpSession httpSession){
        Person person = (Person)httpSession.getAttribute("loggedInPerson");
        Profile profile = new Profile();

        profile.setName(person.getName());
        profile.setMobileNumber(person.getMobileNumber());
        profile.setEmail(person.getEmail());

        if(person.getAddress() != null && person.getAddress().getAddressId() > 0){
            profile.setAddress1(person.getAddress().getAddress1());
            profile.setAddress2(person.getAddress().getAddress2());
            profile.setCity(person.getAddress().getCity());
            profile.setState(person.getAddress().getState());
            profile.setZipCode(person.getAddress().getZipCode());
        }

        ModelAndView modelAndView = new ModelAndView("profile.html");
        modelAndView.addObject("profile", profile);

        return modelAndView;
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@Valid @ModelAttribute("profile") Profile profile, Errors errors,
                                HttpSession httpSession){

        if(errors.hasErrors()){
            return "profile.html";
        }

        Person person = (Person) httpSession.getAttribute("loggedInPerson");

        person.setName(profile.getName());
        person.setEmail(profile.getEmail());
        person.setMobileNumber(profile.getMobileNumber());

        if(person.getAddress() == null || !(person.getAddress().getAddressId()>0)){
            person.setAddress(new Address());
        }

        person.getAddress().setAddress1(profile.getAddress1());
        person.getAddress().setAddress2(profile.getAddress2());
        person.getAddress().setCity(profile.getCity());
        person.getAddress().setState(profile.getState());
        person.getAddress().setZipCode(profile.getZipCode());

//        System.out.println("------------before saving address to the db------------");
//        System.out.println(person);
//        System.out.println(profile);
//
//        Address address = adressRepository.save(person.getAddress());
//        System.out.println(address);
//
//        System.out.println("------------after saving address to the db------------");
//        System.out.println(person);
//        System.out.println(profile);

        // person'un primary'keyi olduğu için db'de spring jpa bakar ve bulur
        // ardından db'ye insert etmez, ilgili person'u update eder

        Person savedPerson = null;
        try{
            savedPerson = personRepository.save(person);
        }catch (Exception e){
            e.printStackTrace();
        }
        httpSession.setAttribute("loggedInPerson", savedPerson);

        return "redirect:/displayProfile";

    }
}
