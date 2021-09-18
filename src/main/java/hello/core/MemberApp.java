package hello.core;

import hello.core.member.*;
import hello.core.order.OrderService;

public class MemberApp {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
//        MemberService memberService = new MemberServiceImpl();

//        id가 1L 이 아니라 1인데 Long 타입이라 L 붙여준거임 안붙이면 에러
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("member = " + member.getName());
        System.out.println("findMember = "+findMember.getName());
        System.out.println("findMember = " + findMember.getGrade());
    }
}