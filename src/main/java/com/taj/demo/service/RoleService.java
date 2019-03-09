package com.taj.demo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taj.demo.model.Role;
import com.taj.demo.repository.RoleRepository;
@Service("roleService")
public class RoleService {
	
	@Autowired
    private RoleRepository roleRepository;
	
    public List<Role> findAll(){
    	return roleRepository.findAll();
    }
    
    public Role findByRole(String role) {
    	return roleRepository.findByRole(role);
    }
    
    
}
