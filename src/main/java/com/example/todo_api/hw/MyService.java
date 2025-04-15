package com.example.todo_api.hw;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
public class MyService {
    private final MyRepository myRepository;

    public void ServiceMethod() {
        System.out.println("service");
        myRepository.repositoryMethod();
    }
}
