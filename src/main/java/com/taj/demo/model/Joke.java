package com.taj.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "joke")
public class Joke {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "joke_id")
	private int id;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="usertaj_id")
	private UserTaj userTaj;
	
	@Column(name = "title")
	@NotEmpty(message = "*Please provide a title")
	private String title;
	
	@Column(name = "body")
	@NotEmpty(message = "*Please write your joke")
	private String body;
	
	@Column(name = "avg_rating")
	private Double avgRating;
	
	@ManyToMany(fetch=FetchType.LAZY,
    		cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        })
    @JoinTable(name = "JOKE_CATEGORY", 
    joinColumns = @JoinColumn(name = "JOKE_ID"), 
    inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID"))
	@NotEmpty
    private Set<Category> categories;
	
	@OneToMany(mappedBy = "joke", cascade = CascadeType.MERGE)
    private Set<UserTajRating> userTajRatings;

}
