package com.taj.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taj.demo.model.UserTajRating;

@Repository
public interface UserTajRatingRepository extends JpaRepository<UserTajRating, Long>{

}
