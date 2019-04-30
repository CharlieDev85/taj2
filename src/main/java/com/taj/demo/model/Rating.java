package com.taj.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rating")
@IdClass(RatingId.class)
public class Rating {

	@Id
	@Column(name="usertaj_id")
	private Integer userTajId;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="usertaj_id", insertable = false, updatable = false)
	private UserTaj userTaj;
    
    @Id
    @Column(name="joke_id")
    private Integer jokeId;
    
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="joke_id", insertable = false, updatable = false)
	private Joke joke;
	
	@Column(name = "rating")
	private int rating;

}
