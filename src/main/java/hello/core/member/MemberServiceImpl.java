package hello.core.member;

// 구현체가 하나만 있을 경우 관례상 인터페이스 이름 뒤에 Imple을 붙여 사용함
public class MemberServiceImpl implements  MemberService{
    // 이 코드가 좋은 코드 인가? 아님
    // 추상화에도 의존하고 구현체에도 의존하는 문제 발생 DIP 위반
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}