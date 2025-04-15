package com.example.todo_api.bean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// 생성자 주입 방식

@Component
@Getter
@RequiredArgsConstructor // 자동으로 생성자 추가
public class MyBean {
    // 의존성 주입
    private final MySubBean mySubBean;

    // 생성자로 final 초기화 -> mySubBean을 주입할 때는 이 생성자로 주입하도록 한다.
    // @Autowired (생략) // 통로
//    public MyBean(MySubBean mySubBean) {
//        this.mySubBean = mySubBean;
//    }
}
