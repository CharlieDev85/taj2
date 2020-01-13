package com.taj.demo.repository;

import com.taj.demo.model.UserTaj;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserTaj, Long>{
	UserTaj findByEmail(String email);
	UserTaj findByUsername(String username);
	UserTaj findById(int id);
	void delete(UserTaj userTaj);
}
