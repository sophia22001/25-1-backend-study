package com.example.todo_api.bean;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration // 설정 파일
@ComponentScan
public class TestConfig {

//    @Bean // 객체가 앞으로 스프링 빈으로 등록된다.
//    MyBean myBean() {
//        return new MyBean();
//    }
}
