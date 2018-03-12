package org.elevator.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elevator.business.ElevatorMain;
import org.elevator.common.FloorCall;
import org.elevator.service.ElevatorOptions;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping("/")
public class LoginController {
	
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(LoginController.class);
	private static final String VIEW_LOGIN = "login";
	private static final String VIEW_AD = "accessDenied";
	
   // Security Mappings

    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public ModelAndView accessDeniedPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        ModelAndView mav = new ModelAndView();
    	mav.setViewName(VIEW_AD);
    	return mav;
    }
 
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginPage() {
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName(VIEW_LOGIN);
    	return mav;
    }
 
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
 
    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
    
}
