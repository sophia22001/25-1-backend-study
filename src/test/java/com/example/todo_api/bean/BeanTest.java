// 빈이 잘 등록되었는지 테스트
package com.example.todo_api.bean;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// 생성자 주입 방식의 테스트

public class BeanTest {
    // 스프링 컨테이너 생성 
    // Config 클래스에는 컨테이너에 등록할 빈 정보가 담겨있다.
    // 어노테이션을 기반 설정파일로 스프링 컨테이너를 만들것이다.
    ApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test // 테스트용이라는 의미
    public void getAllBeanTest(){
        // 스프링 컨테이너를 설정 파일 정보를 이용해서 생성하고, 스프링 컨테이너 안에 있는 모든 빈을 조회하는 테스트
        for(String name:context.getBeanDefinitionNames()){
            System.out.println(name);
        }
        // context 안에 myBean이 들어있는지 검증
        Assertions.assertThat(context.getBeanDefinitionNames()).contains("myBean");
    }

    @Test
    public void getOneBeanTest(){
        // context에서 하나의 스프링 빈을 스프링 빈 타입으로 가져오자.
        MyBean myBean1 = context.getBean(MyBean.class);
        MyBean myBean2 = context.getBean(MyBean.class);

        // 두 객체가 같은 객체인가?
        System.out.println(myBean1);
        System.out.println(myBean2);

        Assertions.assertThat(myBean1).isSameAs(myBean2);
    }

    @Test
    public void dependencyInjection(){
        MyBean myBean = context.getBean(MyBean.class);
        MySubBean mySubBean = context.getBean(MySubBean.class);

        // 두 객체가 같은 객체인가?
        System.out.println(myBean.getMySubBean());
        System.out.println(mySubBean);

        Assertions.assertThat(myBean.getMySubBean()).isSameAs(mySubBean);
    }
}
