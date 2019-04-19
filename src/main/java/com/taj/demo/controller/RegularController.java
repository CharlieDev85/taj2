package com.taj.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.taj.demo.model.Category;
import com.taj.demo.model.Joke;
import com.taj.demo.model.MyMessage;
import com.taj.demo.model.UserTaj;
import com.taj.demo.model.UserTajRating;
import com.taj.demo.service.CategoryService;
import com.taj.demo.service.JokeService;
import com.taj.demo.service.UserService;
import com.taj.demo.service.UserTajRatingService;

@Controller
public class RegularController {
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private UserTajRatingService userTajRatingService;
		
	@Autowired
    private CategoryService categoryService;
	
	@Autowired
    private JokeService jokeService;

    @RequestMapping(value= {"/r", "/r/home"}, method = RequestMethod.GET)
    public ModelAndView regularHome(){
        ModelAndView modelAndView = new ModelAndView();
        List<Joke> jokes = jokeService.findAll();
        modelAndView.addObject("jokes", jokes);
        modelAndView.addObject("userInfo", this.getUserInfo());
        modelAndView.setViewName("r/home");
        return modelAndView;
    }
    
    @RequestMapping(value = "/r/myjokes", method = RequestMethod.GET)
    public ModelAndView myJokes(){
        ModelAndView modelAndView = new ModelAndView();
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserTaj userTaj = userService.findUserByUsername(auth.getName());
        List<Joke> allUserTajJokes = jokeService.allJokesByUserTaj(userTaj);
        modelAndView.addObject("allUserTajJokes", allUserTajJokes);        
        modelAndView.addObject("userInfo", this.getUserInfo());        
        modelAndView.setViewName("r/myjokes");
        return modelAndView;
    }

    @GetMapping(value="/r/deleteJoke/{id}")
    public ModelAndView deleteJoke(@PathVariable(name="id") int id) {
    	ModelAndView modelAndView = new ModelAndView();
    	Joke joke = jokeService.findById(id);
    	jokeService.deleteJoke(joke);
    	modelAndView.setViewName("redirect:/r/myjokes");
    	return modelAndView;
    }
    
    @RequestMapping(value = "/r/createJoke", method = RequestMethod.GET)
    public ModelAndView createJokeGet(){
        ModelAndView modelAndView = new ModelAndView();
        Joke joke = new Joke();
		List<Category> allCategories = new ArrayList<Category>(categoryService.findAllSorted());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserTaj userTaj = userService.findUserByUsername(auth.getName());
        joke.setUserTaj(userTaj);
        modelAndView.addObject("joke", joke);
        modelAndView.addObject("allCategories", allCategories);
        modelAndView.addObject("userInfo", this.getUserInfo());
        modelAndView.setViewName("r/createJoke");
        return modelAndView;
    }
    
    @RequestMapping(value = "/r/createJoke", method = RequestMethod.POST)
    public ModelAndView createJokePost(@Valid Joke joke,BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
        	List<Category> allCategories = categoryService.findAll();
        	modelAndView.addObject("allCategories", allCategories);
            modelAndView.setViewName("r/createJoke");
        } else {
            jokeService.saveJoke(joke);
            modelAndView.setViewName("redirect:/r/myjokes");
        }
        return modelAndView;
    }
    
    @GetMapping(value = "/r/myprofile")
    public ModelAndView myProfile() {
    	ModelAndView modelAndView = new ModelAndView();
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserTaj userTaj = userService.findUserByUsername(auth.getName());
        MyMessage myMessage = new MyMessage("");
        modelAndView.addObject(userTaj);
        modelAndView.addObject(myMessage);
    	modelAndView.setViewName("r/myprofile");
    	return modelAndView;
    }
    
    @PostMapping(value = "/r/updatepassword")
    public ModelAndView updatePassword (
    		@RequestParam("pass") String pass, 
    		@RequestParam("passConfirm") String passConfirm, 
    		@RequestParam("oldPass") String oldPass) {
    	ModelAndView modelAndView = new ModelAndView();
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserTaj userTaj = userService.findUserByUsername(auth.getName());
        if(!pass.equals(passConfirm)) {
        	modelAndView.addObject(userTaj);
        	modelAndView.addObject("myMessage", new MyMessage("passwords not matching"));
        	modelAndView.setViewName("r/myprofile");
        	return modelAndView;
        }
        if(bCryptPasswordEncoder.matches(oldPass, userTaj.getPassword())) {
        	userTaj.setPassword(pass);
        	userService.saveUser(userTaj);
        	modelAndView.addObject(userTaj);
        	modelAndView.addObject("myMessage", new MyMessage("Password was changed"));
    		modelAndView.setViewName("r/myprofile");
    	} else {
    		modelAndView.addObject(userTaj);
    		modelAndView.addObject("myMessage", new MyMessage("Old password is incorrect"));
    		modelAndView.setViewName("r/myprofile");
    	}
    	return modelAndView;
    }	
    
    @PostMapping(value = "/r/rating")
    public ModelAndView rating(@RequestParam("jokeId") int jokeId, @RequestParam("rating") int rating) {
    	ModelAndView modelAndView = new ModelAndView();
    	Joke joke = jokeService.findById(jokeId);
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserTaj userTaj = userService.findUserByUsername(auth.getName());
        UserTajRating userTajRating = new UserTajRating();
        userTajRating.setUserTaj(userTaj);
        userTajRating.setJoke(joke);
        userTajRating.setRating(rating);
        UserTajRating userTajRatingSaved = userTajRatingService.save(userTajRating);
        Integer avgRatingSaved = jokeService.updateJokeWithAverage(jokeId);
        modelAndView.setViewName("redirect:/r/home");
    	return modelAndView;
    }
    
    private String getUserInfo() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserTaj userTaj = userService.findUserByUsername(auth.getName());
        return "Welcome " + userTaj.getUsername() + " " + " (" + userTaj.getEmail() + ")";
    }
}
	