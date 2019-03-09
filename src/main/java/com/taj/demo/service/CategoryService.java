package com.taj.demo.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.taj.demo.model.Category;
import com.taj.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;



@Service("categoryService")
public class CategoryService {
	
	@Autowired
    private CategoryRepository categoryRepository;

    public Category findCategoryByCategoryName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }

    
    
    public List<Category> findAll(){
    	return categoryRepository.findAll();
    }
    
    public void delete(Category category) {
    	categoryRepository.delete(category);
    }
    
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }
    
    public Category editCategory(Category category) {
    	return categoryRepository.save(category);
    }
    
    public Category findCategoryById(int id) {
    	
    	return categoryRepository.findById(id);
    }

}
