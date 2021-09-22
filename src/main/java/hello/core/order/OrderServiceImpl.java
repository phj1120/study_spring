package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;

public class OrderServiceImpl implements OrderService{

//    새로운 할인 정책 적용시 클라이언트 코드 수정 발생 -> DIP 위반
//    구현체가 아닌 인터페이스에 의존하도록 수정해야함
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;
//    생성자 생성해서 불러옴
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        // 할인 정책이 바뀌어도 코드 수정이 필요없음(위에 불러오는게 바뀌면 바뀌는 거고..)
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }


    // @Configurate 와 싱글톤 테스트 용도
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}