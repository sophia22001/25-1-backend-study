package com.example.todo_api.hw;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyControllerTest {

    @Autowired
    private MyController myController;

    @Autowired
    private MyService myService;

    @Autowired
    private MyRepository myRepository;

    @Test
    public void controllerTest() {
        myController.controllerMethod();
    }
}
