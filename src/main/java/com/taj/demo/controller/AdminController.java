package com.taj.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.taj.demo.model.Category;
import com.taj.demo.model.Role;
import com.taj.demo.model.UserTaj;
import com.taj.demo.service.CategoryService;
import com.taj.demo.service.RoleService;
import com.taj.demo.service.UserService;
import com.taj.demo.helper.Checkbox;

@Controller
public class AdminController {
	
	@Autowired	
    private CategoryService categoryService;
	
	@Autowired	
    private RoleService roleService;
	
	@Autowired	
    private UserService userService;

    @RequestMapping(value="/admin/categories", method = RequestMethod.GET)
    public ModelAndView crudCategories(){
        ModelAndView modelAndView = new ModelAndView();
        List<Category> categories = new ArrayList<Category>();
        categories = categoryService.findAll();
        modelAndView.addObject("categories", categories);        
        modelAndView.setViewName("admin/categories");
        return modelAndView;
    }
    
    @RequestMapping(value="/admin/createUser", method = RequestMethod.GET)
    public ModelAndView createUserGet(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/logout");
        return modelAndView;
    }
    
    @RequestMapping(value="/admin/users", method = RequestMethod.GET)
    public ModelAndView crudUsers(){
        ModelAndView modelAndView = new ModelAndView();
        List<UserTaj> users = new ArrayList<UserTaj>();
        users = userService.findAll();
        modelAndView.addObject("users", users);        
        modelAndView.setViewName("admin/users");
        return modelAndView;
    }
    
    
    
    @RequestMapping(value = "/admin/createCategory", method = RequestMethod.GET)
    public ModelAndView createCategoryGet() {
        ModelAndView modelAndView = new ModelAndView();
        Category category = new Category();
        modelAndView.addObject("category", category);
        modelAndView.setViewName("admin/createCategory");
        return modelAndView;
    }
    
    @RequestMapping(value = "/admin/createCategory", method = RequestMethod.POST)
    public ModelAndView createCategoryPost(@Valid Category category, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Category categoryExists = categoryService.findCategoryByCategoryName(category.getCategoryName());
        
        if (categoryExists != null) {
            bindingResult.rejectValue("categoryName", "error.category",
            		"There is already a Category registered with the name provided");
        }
        
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("admin/createCategory");
        } else {
            categoryService.saveCategory(category);
            modelAndView.setViewName("redirect:/admin/categories");
        }
        return modelAndView;
    }
    
    @RequestMapping(value = "/admin/deleteCategory/{categoryName}", method = RequestMethod.GET)
    public ModelAndView deleteCategory(@PathVariable(name="categoryName") String categoryName) {
    	ModelAndView modelAndView = new ModelAndView();
		Category category = categoryService.findCategoryByCategoryName(categoryName);
		categoryService.delete(category);
		modelAndView.setViewName("redirect:/admin/categories");
		return modelAndView;
    }
    
    @RequestMapping(value = "/admin/deleteUser/{id}", method = RequestMethod.GET)
    public ModelAndView deleteUser(@PathVariable(name="id") int id) {
    	ModelAndView modelAndView = new ModelAndView();
    	UserTaj userTaj = userService.findUserByUserId(id);
		userService.delete(userTaj);
		modelAndView.setViewName("redirect:/admin/users");
		return modelAndView;
    }

    @RequestMapping(value="/admin/editCategory/{id}", method = RequestMethod.GET)
    public ModelAndView editCategoryGet(@PathVariable(name="id") int id) {
    	ModelAndView modelAndView = new ModelAndView();
    	Category category = categoryService.findCategoryById(id);
    	modelAndView.addObject("category", category);
    	modelAndView.setViewName("admin/editCategory");;
    	return modelAndView;
    }
    

    
    @RequestMapping(value="/admin/updateCategory/{id}", method = RequestMethod.POST)
    public ModelAndView editCategoryPost(@Valid Category category, BindingResult bindingResult, @PathVariable ("id") int id) {
    	ModelAndView modelAndView = new ModelAndView();
    	
    	
    	Category categoryExists = categoryService.findCategoryByCategoryName(category.getCategoryName());
        
        if (categoryExists != null) {
            bindingResult.rejectValue("categoryName", "error.category",
            		"There is already a Category registered with the name provided");
        }
        
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("admin/editCategory");
            
        } else {
            categoryService.editCategory(category);
            modelAndView.setViewName("redirect:/admin/categories");
        }
    	
    	return modelAndView;
    }
    
    @RequestMapping(value="/admin/editUser/{id}", method = RequestMethod.GET)
    public ModelAndView editUserGet(@PathVariable(name="id") int id) {
    	List<Checkbox> rolesCheckboxes = new ArrayList<Checkbox>();
    	ModelAndView modelAndView = new ModelAndView();
    	UserTaj userTaj = userService.findUserByUserId(id);
    	List<Role> roles = roleService.findAll();
    	for(Role role: roles) {
    		Checkbox checkbox = new Checkbox(role.getRole(), role.getId(), false);
    		for(Role roleApplied: userTaj.getRoles()) {
    			if(roleApplied.getRole().equals(role.getRole())) {
    				checkbox.setChecked(true);
    			}
    		}
    		rolesCheckboxes.add(checkbox);
    	}
    	//quick fix to avoid error in displaying the page
    	userTaj.setRoles(null);
    	modelAndView.addObject("userTaj", userTaj);
    	modelAndView.addObject("rolesCheckboxes", rolesCheckboxes);
    	modelAndView.setViewName("admin/editUser");;
    	return modelAndView;
    }
    
    @RequestMapping(value="/admin/updateUser/{id}", method = RequestMethod.POST)
    public ModelAndView editUserPost(@Valid UserTaj userTaj, 
    								BindingResult bindingResult, 
    								@PathVariable ("id") int id) {
    	ModelAndView modelAndView = new ModelAndView();
    	UserTaj oldUser = userService.findUserByUserId(id);
    	UserTaj userExists;
    	
    	//check if new User has at least one role
    	if(userTaj.getRoles()==null) {
                bindingResult.rejectValue("roles", "error.roles",
                		"there should be at least one role selected");
            }

    	
    	//check if new Username doesn't exists already
    	if(! oldUser.getUsername().equals(userTaj.getUsername())) {
    		userExists = userService.findUserByUsername(userTaj.getUsername());
            if (userExists != null) {
                bindingResult.rejectValue("username", "error.username",
                		"There is already a UserTaj registered with the username provided");
            }
    	}
    	
    	//check if new email doesn't exists already
    	if(! oldUser.getEmail().equals(userTaj.getEmail())) {
    		userExists = userService.findUserByEmail(userTaj.getEmail());
            if (userExists != null) {
                bindingResult.rejectValue("email", "error.email",
                		"*There is already a UserTaj registered with the email provided");
            }
    	}    	
        
        if (bindingResult.hasErrors()) {
        	List<Checkbox> rolesCheckboxes = new ArrayList<Checkbox>();
        	List<Role> roles = roleService.findAll();
        	for(Role role: roles) {
        		Checkbox checkbox = new Checkbox(role.getRole(), role.getId(), false);
        		if(userTaj.getRoles() != null) {
        			for(Role roleApplied: userTaj.getRoles()) {
            			if(roleApplied.getRole().equals(role.getRole())) {
            				checkbox.setChecked(true);
            			}
            		}
        		}        		
        		rolesCheckboxes.add(checkbox);
        	}
        	//next line should be improved in the future, using the contains method in thymeleaf
        	//https://www.baeldung.com/thymeleaf-arrays
        	modelAndView.addObject("rolesCheckboxes", rolesCheckboxes);
            modelAndView.setViewName("admin/editUser");
            
        } else {
            userService.editUser(userTaj);
            modelAndView.setViewName("redirect:/admin/users");
        }
    	
    	return modelAndView;
    }

}
