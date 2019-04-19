package com.taj.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taj.demo.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
	
	Category findByCategoryName(String categoryName);
	Category findById(long id);
	List<Category> findAllByOrderByIdAsc();

	
}
