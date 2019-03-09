package com.taj.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
	private int id;
	
	@Column(name = "category_name")
    @NotEmpty(message = "*Please provide a category name")
	@Length(min = 3, message = "*Your category must have at least 3 characters")
	private String categoryName;
	
	public Category (String categoryName) {
		this.categoryName = categoryName;
	}
}
