package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Contact;
import com.eazybytes.eazyschool.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
public class ContactController {

//    private static Logger logger = LoggerFactory.getLogger(ContactController.class);
    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

//    @RequestMapping("/contact")
//    public String displayContactPage(){
//        return "contact.html";
//    }

    @RequestMapping("/contact")
    public String displayContactPage(Model model){
        model.addAttribute("contact", new Contact());
        return "contact.html";
    }

//    @RequestMapping(value = "/saveMsg", method = RequestMethod.POST)
//    @PostMapping("/saveMsg")
//    public ModelAndView saveMessage(@RequestParam String name, @RequestParam String mobileNum,
//                                    @RequestParam String email, @RequestParam(value = "subject") String konu,
//                                    @RequestParam(name = "message") String gelenMesaj){
//
//        logger.info("name: " + name);
//        logger.info("mobileNumber: " + mobileNum);
//        logger.info("email: " + email);
//        logger.info("subject: " + konu);
//        logger.info("message: " + gelenMesaj);
//
//        return new ModelAndView("redirect:/contact");
//
//    }

//    @PostMapping("/saveMsg")
//    public ModelAndView saveMessage(Contact contact){
//
//        contactService.saveMessageDetails(contact);
//        return new ModelAndView("redirect:/contact");
//    }

    @PostMapping("/saveMsg")
    public ModelAndView saveMessage(@Valid @ModelAttribute("contact") Contact contact, Errors errors){

        if(errors.hasErrors()){
            log.error("Contact form validation failed due to: " + errors.toString() );

            // error log göster, contact page'i göstermeye devam et
            // bu sayede kullanıcı verileri doldurmaya devam etsin
            return new ModelAndView("contact.html");
        }

        contactService.saveMessageDetails(contact);

        // redirect action'unu yap, yani tekrar istek at sıfırdan ilgili sayfaya,
        // bu da displayContactPage() methodunu çağırır
        return new ModelAndView("redirect:/contact");
    }

//    @RequestMapping("/displayMessages")
//    public ModelAndView displayMessages(Model model){
//        List<Contact> contactList = contactService.findMessagesWithOpenStatus();
//        ModelAndView modelAndView = new ModelAndView("messages.html");
//        modelAndView.addObject("contactList", contactList);
//        return modelAndView;
//    }

    @RequestMapping("/displayMessages/page/{pageNum}")
    public ModelAndView displayMessages(Model model,
                                        @PathVariable(name = "pageNum") int pageNum,@RequestParam("sortField") String sortField,
                                        @RequestParam("sortDir") String sortDir) {
        Page<Contact> msgPage = contactService.findMessagesWithOpenStatus(pageNum,sortField,sortDir);
        List<Contact> contactMsgs = msgPage.getContent();
        ModelAndView modelAndView = new ModelAndView("messages.html");
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", msgPage.getTotalPages());
        model.addAttribute("totalMsgs", msgPage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        modelAndView.addObject("contactMsgs",contactMsgs);
        return modelAndView;
    }

    @RequestMapping(value = "/closeMsg", method = RequestMethod.GET)
    public String closeMsg(@RequestParam int id, Authentication authentication){
        contactService.updateMessageStatusToClose(id/*, authentication.getName()*/);
        return "redirect:/displayMessages/page/1?sortField=name&sortDir=desc";
    }

}
