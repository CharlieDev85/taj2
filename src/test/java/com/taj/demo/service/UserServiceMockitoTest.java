package com.taj.demo.service;

import com.taj.demo.model.UserTaj;
import com.taj.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
/*
* test made for practice purpose following udemy course abount testing
* */

@ExtendWith(MockitoExtension.class)
public class UserServiceMockitoTest {


    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @Test
    public void deleteAtLeastOnce(){
        service.delete(new UserTaj());
        service.delete(new UserTaj());
        verify(repository, atLeastOnce()).delete(new UserTaj());
    }

    @Test
    public void testDelete(){
        UserTaj juancho = new UserTaj();
        juancho.setId(777);
        juancho.setUsername("juancho");
        juancho.setPassword("juancho");
        juancho.setEmail("juancho@gmail.com");

        service.delete(juancho);
    }

}