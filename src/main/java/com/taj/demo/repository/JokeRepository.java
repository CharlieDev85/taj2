package com.taj.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.taj.demo.model.Joke;
import com.taj.demo.model.UserTaj;

@Repository
public interface JokeRepository extends JpaRepository<Joke, Long>{
	List<Joke> findByUserTaj(UserTaj userTaj);
	Joke findById(int id);
	List<Joke> findAllByOrderByIdAsc();
	
	@Modifying
	@Query(value = "update joke j set j.avg_rating=(select avg(rating) from usertaj_rating utr where utr.joke_id = ?) where j.joke_id = ?", 
	  nativeQuery = true)
	int updateJokeWithAverage(Integer jokeId, Integer jokeId2);
	
	
}
