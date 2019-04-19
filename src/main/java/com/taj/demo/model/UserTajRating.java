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
@Table(name = "usertaj_rating")
public class UserTajRating {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "usertaj_rating_id")
	private long id;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="usertaj_id")
	private UserTaj userTaj;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="joke_id")
	private Joke joke;
	
	@Column(name = "rating")
	private int rating;

}
