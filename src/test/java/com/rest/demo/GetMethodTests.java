package com.rest.demo;

import com.rest.demo.controller.UserController;
import com.rest.demo.entity.User;
import com.rest.demo.exception.CustomMessage;
import com.rest.demo.service.UserService;
import javafx.application.Application;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletResponse;

import java.util.Date;

import static junit.framework.TestCase.assertEquals;
import static org.springframework.boot.actuate.metrics.web.servlet.WebMvcTags.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetMethodTests {

    private MockMvc mvc;

    @Autowired
    UserController userController;

    @Autowired
    private UserService userService;


    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void getMethodTestWhenNoUsersArePersisted() throws Exception {
        mvc.perform(get("/users/all").contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", Matchers.hasSize(2)));

    }

    @Test
    public void getRequestTestWithPersistedUsers() throws Exception {
        User user = new User();
        user.setName("Vasil");
        user.setSurname("Vasilev");

        userService.saveUser(user);
        mvc.perform(get("/users/all").contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.*", Matchers.hasSize(1)));

    }
}
