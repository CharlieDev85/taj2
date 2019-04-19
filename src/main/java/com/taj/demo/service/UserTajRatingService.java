package com.taj.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taj.demo.model.UserTajRating;
import com.taj.demo.repository.UserTajRatingRepository;



@Service("userTajRatingService")
public class UserTajRatingService {
	
	@Autowired
    private UserTajRatingRepository userTajRatingRepository;

	public UserTajRating save(UserTajRating userTajRating) {
		return userTajRatingRepository.save(userTajRating);
	}

}
