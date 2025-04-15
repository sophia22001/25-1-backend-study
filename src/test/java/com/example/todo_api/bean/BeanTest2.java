package com.example.todo_api.bean;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// 필드 주입 방식의 테스트
// 테스트에서 필드 주입을 하려면, 테스트를 실행할 때 이미 스프링 컨테이너가 존재해야 한다.
// 스프링 부트까지 실행시키므로, 통합 테스트에서도 사용할 수 있다.

@SpringBootTest // 테스트 실행 전에 스프링 부트를 실행시켜서 어플리케이션에 있는 모든 빈을 이 컨테이너에 등록한다.
public class BeanTest2 {

    @Autowired
    private MyBean myBean;

    @Autowired
    private MySubBean mySubBean;

    @Test
    public void dependencyInjection(){
        // 두 객체가 같은 객체인가?
        System.out.println(myBean.getMySubBean());
        System.out.println(mySubBean);

        Assertions.assertThat(myBean.getMySubBean()).isSameAs(mySubBean);
    }
}
