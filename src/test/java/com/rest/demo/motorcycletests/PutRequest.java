package com.rest.demo.motorcycletests;

import com.rest.demo.controller.MotorCycleController;
import com.rest.demo.entity.Motorcycle;
import com.rest.demo.service.MotorcycleService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PutRequest {

    private MockMvc mvc;

    @Autowired
    private MotorCycleController motorCycleController;

    @Autowired
    private MotorcycleService motorcycleService;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(motorCycleController).build();
        saveAMotorcycle();
    }

    @Test
    public void testPutRequest() throws Exception {
        JSONObject motorcycle = new JSONObject();
        motorcycle.put("id", "1");
        motorcycle.put("brand", "Yamaha");
        motorcycle.put("type", "Sport Bike");
        motorcycle.put("model", "Z01");
        motorcycle.put("colour", "Black");
        motorcycle.put("horsePower", 50);
        mvc.perform(put("/motorcycles/update/motorcycle/1").content(motorcycle.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mvc.perform(get("/motorcycles/search?brand=Yamaha&type=Sport Bike&model=Z01&colour=Black&horsePower=50").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8));
    }



    private void saveAMotorcycle() {
        Motorcycle motorcycle = new Motorcycle();
        motorcycle.setModel("Z23");
        motorcycle.setType("All-Rode");
        motorcycle.setColour("Black");
        motorcycle.setHorsePower(50);
        motorcycle.setId("1");
        motorcycle.setBrand("Suzuki");
        motorcycleService.saveMotorcycle(motorcycle);
    }

}
