package com.taj.demo.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.taj.demo.model.Role;
import com.taj.demo.model.UserTaj;
import com.taj.demo.service.RoleService;
import com.taj.demo.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private RoleService roleService;

    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }
	

    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        UserTaj userTaj = new UserTaj();
        modelAndView.addObject("userTaj", userTaj);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid UserTaj userTaj, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        UserTaj userExists = userService.findUserByEmail(userTaj.getEmail());
        UserTaj userExists2 = userService.findUserByUsername(userTaj.getUsername());
        if (userExists != null) {
            bindingResult.rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (userExists2 != null) {
            bindingResult.rejectValue("username", "error.username",
                            "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(userTaj);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("userTaj", new UserTaj());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @RequestMapping(value= {"/admin", "/admin/home"}, method = RequestMethod.GET)
    public ModelAndView adminHome(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserTaj userTaj = userService.findUserByUsername(auth.getName());
        modelAndView.addObject("userName", "Welcome " + userTaj.getUsername() + " " + " (" + userTaj.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }
    
    
    @RequestMapping(value= {"/access-denied"}, method = RequestMethod.GET)
    public ModelAndView accessDenied(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("access-denied");
        return modelAndView;
    }
    
    @RequestMapping(value= {"/default"}, method = RequestMethod.GET)
    public String defaultAfterLogin() {
    	Collection<? extends GrantedAuthority> authorities;
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	authorities = auth.getAuthorities();
    	String myRole = authorities.toArray()[0].toString();
    	String admin = "admin";
    	System.out.println(myRole);
        if (myRole.equals(admin)) {
            return "redirect:/admin/";
        }
        return "redirect:/r/";
    }

}
