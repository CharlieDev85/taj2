package com.taj.demo.controller;

import com.taj.demo.model.Joke;
import com.taj.demo.model.UserTaj;
import com.taj.demo.service.CategoryService;
import com.taj.demo.service.JokeService;
import com.taj.demo.service.RatingService;
import com.taj.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RegularControllerTest {


    @Mock private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock private UserService userService;
    @Mock private RatingService ratingService;
    @Mock private CategoryService categoryService;
    @Mock private JokeService jokeService;
    @Mock private BindingResult bindingResult;
    @Mock private UserTaj userTaj;
    @Mock private ModelAndView modelAndView;
    @Mock Authentication auth;

    @InjectMocks private RegularController controller;

    MockMvc mockMvc;


    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void testSimpleRegularHome(){
        //given
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("r/simplehome");
        Joke joke = new Joke();
        joke.setBody("joke for testing");
        List<Joke> jokes = new ArrayList<>();
        jokes.add(joke);
        given(jokeService.findAll()).willReturn(jokes);
        //when
        ModelAndView returnedModel;
        returnedModel = controller.simpleRegularHome();
        //then
        assertThat(returnedModel.getViewName())
                .isEqualToIgnoringCase(modelAndView.getViewName());
    }

    @Test
    public void testControllerSimpleShowRegularHome() throws Exception {
        mockMvc.perform(get("/r/simplehome"))
                .andExpect(status().isOk());
    }

    @Test
    public void testControllerShowRegularHome() throws Exception {
        mockMvc.perform(get("/r")).andExpect(status().isOk());
    }

    @Test
    public void regularHome(){
        //given
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("r/home");

        when(controller.regularHome()).thenReturn(modelAndView);
        //when
        ModelAndView returnedModel;
        returnedModel = controller.regularHome();

        //then
        assertThat(returnedModel.getViewName()).isEqualToIgnoringCase(modelAndView.getViewName());
    }



   @Test
    void processCreationJokeHasErrors(){
       //given
       ModelAndView modelAndView;
       Joke joke = new Joke();
       joke.setTitle("my testing title");
       joke.setBody("my testing body joke hahaha");
       given(bindingResult.hasErrors()).willReturn(true);
       //when
       modelAndView = controller.createJokePost(joke, bindingResult);
       //then
       assertThat(modelAndView.getViewName()).isEqualToIgnoringCase("r/createJoke");
   }
   @Test
    void processCreationJokeNoErrors(){
       //given
       ModelAndView modelAndView;
       Joke joke = new Joke();
       joke.setTitle("my testing title");
       joke.setBody("my testing body joke hahaha");
       given(bindingResult.hasErrors()).willReturn(false);
       //when
       modelAndView = controller.createJokePost(joke, bindingResult);
       //then
       assertThat(modelAndView.getViewName()).isEqualToIgnoringCase("redirect:/r/myjokes");
    }


}