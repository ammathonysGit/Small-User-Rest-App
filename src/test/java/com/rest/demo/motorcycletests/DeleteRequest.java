package com.rest.demo.motorcycletests;

import com.rest.demo.controller.MotorCycleController;
import com.rest.demo.entity.Motorcycle;
import com.rest.demo.service.MotorcycleService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeleteRequest {

    private MockMvc mockMvc;

    @Autowired
    private MotorCycleController motorCycleController;

    @Autowired
    private MotorcycleService motorcycleService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(motorCycleController).build();
        saveAMotorcycle();
    }


    @Test
    public void deleteMotorcycleId() throws Exception {
        mockMvc.perform(delete("/motorcycles/delete/1")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void deleteNonePersistedMotorcycle() throws Exception {
        mockMvc.perform(delete("/motorcycles/delete/50")).andExpect(MockMvcResultMatchers.status().isNotFound());
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
