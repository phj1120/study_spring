package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AllBeanTest {

    @Test
    void findAllBean(){
//        이렇게 하면 둘다 땡겨옴
//        땡겨오는거까지는 ㅇㅋ
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);

        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "userA", Grade.VIP);
        int discountPrice = discountService.discount(member, 20000, "fixDiscountPolicy");

        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(discountPrice).isEqualTo(1000);

        int rateDiscountPrice = discountService.discount(member, 20000, "rateDiscountPolicy");
        assertThat(rateDiscountPrice).isEqualTo(2000);
    }

    static class DiscountService {
        private final Map<String, DiscountPolicy> policyMap;
//        private final List<DiscountPolicy> policies;

        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
            this.policyMap = policyMap;
//            this.policies = policies;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
        }

        public int discount(Member member, int price, String discountCode) {
            DiscountPolicy discountPolicy = policyMap.get(discountCode);
//            받아온 값을 키로 이용해서 유연한 전력 수정 가능함
//            동적으로 빈을 받아들여할 때 이렇게하면 다형성 코드를 유지하면서 사용할 수 있음
            System.out.println(member+ " "+ discountCode +" "+discountPolicy.discount(member, price));
//            discountPolicy.discount 하면 왜 할인 된 가격이 출력 될까?
//            여기서 호출한 discount 가 지금 생성한 DiscountService의 discount 인줄알고
//            콜백...으로 불러온건가.. 그래도 이 값이 나올리가 없는데? 했는데
//            hello.core.discount 패키지의 discountPolicy 임플리먼트로 생성해
//            각각에 맞는 구현체에서 생성된 discount 함수 불러온거였음
            return discountPolicy.discount(member, price);
        }
    }

}
