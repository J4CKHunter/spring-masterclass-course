package com.eazybytes.eazyschool.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    // hem get hem post http methodlarını tek java controller methodunda kontrol ediyor !!
    @RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST})
    public String displayLoginPage(@RequestParam(value = "error", required = false) String error,
                                   @RequestParam(value = "logout", required = false) String logout,
                                   @RequestParam(value = "register", required = false) String register,
                                   Model model){

        String errorMessage = null;

        if(error != null){
            errorMessage = "Username or Password is incorrect.";
        }

        // logout başarısız olduğu durumda ne olacağı belli değil !
       else if(logout != null){
            errorMessage = "You have been successfully logged out.";
        }

        else if(register != null){
            errorMessage = "your registration successful. Login with registered credentials.";
        }

        model.addAttribute("errorMessage", errorMessage);

        // model'e attribute ekliyor ama return etmiyor !!! DENE BUNU, ERRORMESSAGE SET ET
        return "login.html";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            // logout işlemi burda başlar,
            // security classında yazdığımız logout.logoutSuccessURl'e yönlendirilir
            // http session'u da kapatılır
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login?logout=true";
    }

}
