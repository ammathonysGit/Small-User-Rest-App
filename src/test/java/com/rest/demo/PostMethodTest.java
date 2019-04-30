package com.rest.demo;

import com.rest.demo.controller.UserController;
import com.rest.demo.entity.User;
import com.rest.demo.service.UserService;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostMethodTest {

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
    public void saveUserTest() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Vasil");
        jsonObject.put("surname", "Vasilev");

        mvc.perform(post("/users/save").content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
        mvc.perform(get("/users/all").contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
    }

}
