package hello.core.scan;

import hello.core.AutoAppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutoAppConfigTest {

    @Test
    void basicScan() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        MemberService memberService = ac.getBean(MemberService.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);

        OrderServiceImpl orderService = ac.getBean(OrderServiceImpl.class);

        MemberRepository memberRepository = orderService.getMemberRepository();
        System.out.println("memberRepository = " + memberRepository);

        Member member = new Member(1L, "A", Grade.VIP);
        memberService.join(member);
        System.out.println("discount price = " + orderService.createOrder(1L, "itemA", 20000).calculatePrice());
    }
}