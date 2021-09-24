package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{

//    새로운 할인 정책 적용시 클라이언트 코드 수정 발생 -> DIP 위반
//    구현체가 아닌 인터페이스에 의존하도록 수정해야함
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
//    private final MemberRepository memberRepository = new MemoryMemberRepository();

//    수정자 주입
//    의존관계 선택적 주입, 변경 가능
//    private MemberRepository memberRepository;
//    private DiscountPolicy discountPolicy;
//
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        System.out.println("memberRepository = " + memberRepository);
//        this.memberRepository = memberRepository;
//    }
//
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        System.out.println("discountPolicy = " + discountPolicy);
//        this.discountPolicy = discountPolicy;
//    }

//    생성자 주입
//    생성자 생성해서 불러옴
//    생성자 하나 일때는 생략 가능
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        System.out.println("memberRepository = " + memberRepository);
        System.out.println("discountPolicy = " + discountPolicy);
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

//    필드 주입 / 쓰지마
//    AppConfig 의 OrderService return 주석 처리 후 return null;로 변경\
//    private 인데도 가능함
//    간결해서 좋긴 하지만 외부에서 변경이 불가능해 스프링에서도 추천하지 않음
//    어쨌거나 setter를 만들어야되는데 그럴거면 수정자 쓰지..
//    순수한 자바코드로 테스트 못함
//    스프링 부트 테스트에서는 가능하다는데 그냥 애플리케이션 코드에서는 쓰지 말자
//    @Autowired private MemberRepository memberRepository;
//    @Autowired private DiscountPolicy discountPolicy;


//    일반 메서드 주입
//    한번에 여러 필드 주입 받을 수 있음
//    그냥 생성자, 수정자 쓰지 잘 안씀
//    private MemberRepository memberRepository;
//    private DiscountPolicy discountPolicy;
//
//    @Autowired
//    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        // 할인 정책이 바뀌어도 코드 수정이 필요없음(위에 불러오는게 바뀌면 바뀌는 거고..)
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }


//     @Configurate 와 싱글톤 테스트 용도
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}