package com.taj.demo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taj.demo.model.Joke;
import com.taj.demo.model.UserTaj;
import com.taj.demo.repository.JokeRepository;



@Service("jokeService")
@Transactional
public class JokeService {
	
	@Autowired
    private JokeRepository jokeRepository;


    public Joke saveJoke(Joke joke) {
        return jokeRepository.save(joke);
    }

    public List<Joke> allJokesByUserTaj(UserTaj userTaj){
    	return jokeRepository.findByUserTaj(userTaj);
    }
    
    public void deleteJoke(Joke joke) {
    	jokeRepository.delete(joke);
    	}
    
    public Joke findById(int id) {
    	return jokeRepository.findById(id);
    }
    
    public List<Joke> findAll(){
    	return jokeRepository.findAllByOrderByIdAsc();
    }
    
    public int updateJokeWithAverage(Integer jokeId) {
    	return jokeRepository.updateJokeWithAverage(jokeId, jokeId);
    }
}
