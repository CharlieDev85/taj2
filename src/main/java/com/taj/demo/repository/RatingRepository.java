package com.taj.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taj.demo.model.Joke;
import com.taj.demo.model.Rating;
import com.taj.demo.model.UserTaj;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long>{
	List<Rating> findByUserTaj(UserTaj userTaj);
	List<Rating> findByJoke(Joke joke);
}
