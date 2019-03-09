package com.taj.demo.repository;

import com.taj.demo.model.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
	
	Category findByCategoryName(String categoryName);
	Category findById(int id);
	
}
