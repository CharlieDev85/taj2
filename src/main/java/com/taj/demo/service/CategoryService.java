package com.taj.demo.service;


import com.taj.demo.model.Category;
import com.taj.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



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
    	return categoryRepository.saveAndFlush(category);
    }
    
    public Category findCategoryById(long id) {
    	
    	return categoryRepository.findById(id);
    }
    
    public List<Category> findAllSorted(){
//    	return categoryRepository.findAllByOrderByIdAsc();
    	//Sort sort = new Sort(Sort.Direction.ASC, "categoryName");
    	//return categoryRepository.findAll(sort);
        return categoryRepository.findAll();
    }

}
