package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configurate 와 싱글톤 
// @Bean memberService -> new MemoryMemberRepository
// @Bean OrderService -> new MemoryMemberRepository
// 그럼 new 로 새로운거 생성된거면 싱글톤이 깨지는거 아닌가?
// 의문을 가져야된다는데 잘 모르겠음은... 맞네... 다른 객체가 생성된거 아닌가?


// call AppConfig.memberService
// call AppConfig.memberRepository
// call AppConfig.memberRepository
// call AppConfig.orderService
// call AppConfig.memberRepository
// 순서는 보장하지 않아도 이런 식으로 출력되어야 할텐데

// 결과는
// call AppConfig.memberService
// call AppConfig.memberRepository
// call AppConfig.orderService
// 왜? 스프링이 싱글톤 안 깨려고 처리해줌 다음 강의에서 설명
// @Configuration과 바이트코드 조작의 마법


@Configuration
public class AppConfig {
    // 각 메서드에 붙여주면 스프링 컨테이너에 추가
    @Bean
    public MemberService memberService(){
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemoryMemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService(){
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
//        return null;
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }
}