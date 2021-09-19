/*
* 2021.09.19
* 02. 스프링 빈 조회 - 기본
* */

package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

// public 지워도 됨
class ApplicationContextBasicFindTest {
    // application context : 스프링 컨테이너
    // 관례상 ac로 씀
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName(){
        System.out.println(ac);
        MemberService memberService = ac.getBean("memberService", MemberService.class);
//        System.out.println("memberService = " + memberService);
//        System.out.println("memberService.getClass() = " + memberService.getClass());
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("이름 없이 타입으로만 조회")
    void findBeanByType(){
        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("구체 타입으로 조회")
    void findBeanByName2(){
        // 클래스 타입이 MemberServiceImpl로 바뀜
        // -> 인터페이스를 적어도 스프링빈에 등록된 구현체로 연결되기 때문에 바로 구현체를 지정해 줘도 됨
        // -> 구현에 의존한거니까 안좋은 코드이긴한데 살다보면 쓸일이 있을수도...
        MemberService memberService = ac.getBean("memberService", MemberServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("빈 이름으로 조회 x")
    void findBeanByNameX(){
//        ac.getBean("xx", MemberService.class);
        assertThrows(NoSuchBeanDefinitionException.class, ()-> ac.getBean("xx", MemberService.class));
    }
}