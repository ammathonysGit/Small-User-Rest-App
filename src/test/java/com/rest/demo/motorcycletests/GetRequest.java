package com.rest.demo.motorcycletests;

import com.rest.demo.controller.MotorCycleController;
import com.rest.demo.entity.Motorcycle;
import com.rest.demo.service.MotorcycleService;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GetRequest {

    private MockMvc mvc;

    @Autowired
    MotorCycleController motorCycleController;

    @Autowired
    MotorcycleService motorcycleService;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(motorCycleController).build();
        saveAMotorcycle();
    }


    @Test
    public void testGetAll() throws Exception {
        mvc.perform(get("/motorcycles/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void getWithBrandTest() throws Exception {
        mvc.perform(get("/motorcycles/brand/Suzuki").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void getWithIdTest() throws Exception {
        mvc.perform(get("/motorcycles/id/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                .contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void getWithIdFailureTest() throws Exception {
        mvc.perform(get("/motorcycles/id/50").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content()
                .contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void getWithLimitedFilter() throws Exception {
        mvc.perform(get("/motorcycles/search?brand=Suzuki&type=All-Road&model=Z06").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                .contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void getWithAllFilterCriteria() throws Exception {
        mvc.perform(get("/motorcycles/search?brand=Suzuki&type=All-Road&model=Z06&colour=Black&horsePower=50").contentType(MediaType.APPLICATION_JSON))
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
