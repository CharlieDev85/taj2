package com.taj.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
	private long id;
	
	@Column(name = "category_name")
    @NotEmpty(message = "*Please provide a category name")
	@Length(min = 3, message = "*Your category must have at least 3 characters")
	private String categoryName;
	
	public Category (String categoryName) {
		this.categoryName = categoryName;
	}
	
	@ManyToMany(fetch=FetchType.LAZY, 
			mappedBy = "categories",
			cascade = {
					CascadeType.PERSIST,CascadeType.MERGE
			})
	private List<Joke> jokes;
}
