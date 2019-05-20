package com.rest.demo.motorcycletests;

import com.rest.demo.controller.MotorCycleController;
import com.rest.demo.service.MotorcycleService;
import org.json.JSONException;
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
public class PostRequest {

    private MockMvc mvc;

    @Autowired
    private MotorCycleController motorCycleController;

    @Autowired
    private MotorcycleService motorcycleService;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(motorCycleController).build();
    }

    @Test
    public void saveAMotorcycle() throws Exception {
        JSONObject motorcycle = new JSONObject();
        motorcycle.put("id", "1");
        motorcycle.put("brand", "Yamaha");
        motorcycle.put("type", "Sport Bike");
        motorcycle.put("model", "Z01");
        motorcycle.put("colour", "Black");
        motorcycle.put("horsePower", 50);
        mvc.perform(post("/motorcycles/save").content(motorcycle.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mvc.perform(get("/motorcycles/search?brand=Yamaha&type=Sport Bike&model=Z01&colour=Black&horsePower=50").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void tryToSaveAnInvalidMotorcycle() throws Exception {
        JSONObject motorcycle = new JSONObject();
        motorcycle.put("type", "Sport Bike");
        motorcycle.put("model", "Z01");
        motorcycle.put("colour", "Black");
        motorcycle.put("horsePower", 50);
        mvc.perform(post("/motorcycles/save").content(motorcycle.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }
}
