package com.taj.demo.service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.taj.demo.model.Role;
import com.taj.demo.model.UserTaj;
import com.taj.demo.repository.RoleRepository;
import com.taj.demo.repository.UserRepository;

import static java.util.Objects.requireNonNull;

@Service("userService")
public class UserService {
	
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    public UserTaj findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public UserTaj findUserByUsername(String username) {
    	return userRepository.findByUsername(username);
    }
    
    public UserTaj findUserByUserId(int id) {
    	return userRepository.findById(id);
    }

    public UserTaj saveUser(UserTaj user) {
        requireNonNull(user.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role role = roleRepository.findByRole("regular");
        Set<Role> roles = new HashSet<Role>();
        roles.add(role);
        user.setRoles(roles);
        return userRepository.save(user);
    }
    
    public List<UserTaj> findAll(){
    	return userRepository.findAll();
    }
    
    public UserTaj editUser(UserTaj user) {
    	return userRepository.save(user);
    }
    
    public UserTaj updatePassword(UserTaj userTaj) {
    	return this.saveUser(userTaj);
    }

	public void delete(UserTaj userTaj) {
		userRepository.delete(userTaj);
		
	}
    

}
