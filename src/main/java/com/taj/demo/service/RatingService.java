package com.taj.demo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taj.demo.model.Joke;
import com.taj.demo.model.Rating;
import com.taj.demo.model.UserTaj;
import com.taj.demo.repository.RatingRepository;



@Service("ratingService")
public class RatingService {
	
	@Autowired
    private RatingRepository ratingRepository;

	public Rating save(Rating rating) {
		return ratingRepository.save(rating);
	}
	
	public List<Rating> userTajRatings(UserTaj userTaj){
		return ratingRepository.findByUserTaj(userTaj);
	}
	
	public List<Rating> findRatingsByJoke(Joke joke){
		return ratingRepository.findByJoke(joke);
	}

}
