package com.taj.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.taj.demo.model.UserTaj;
import com.taj.demo.service.UserService;

@Controller
public class RegularController {
	
	@Autowired
    private UserService userService;

    @RequestMapping(value= {"/r", "/r/home"}, method = RequestMethod.GET)
    public ModelAndView adminHome(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserTaj userTaj = userService.findUserByUsername(auth.getName());
        modelAndView.addObject("userName", "Welcome " + userTaj.getUsername() + " " + " (" + userTaj.getEmail() + ")");
        modelAndView.addObject("regularMessage","Content Available Only for Users with Regular Role");
        modelAndView.setViewName("r/home");
        return modelAndView;
    }
    
    @RequestMapping(value = "/r/myjokes", method = RequestMethod.GET)
    public ModelAndView myJokes(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("r/myjokes");
        return modelAndView;
    }
}
	