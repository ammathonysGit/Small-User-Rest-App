package com.rest.demo;

import com.rest.demo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
public class RestApplicationTests {


    private TestEntityManager testEntityManager;
    private UserService userService;

    @Autowired
    public RestApplicationTests(TestEntityManager testEntityManager, UserService userService) {
        this.testEntityManager = testEntityManager;
        this.userService = userService;
    }


    @Test
    public void testGetMethodWhen() {
    }

}
