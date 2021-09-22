package hello.core.member;

//구현체가 하나만 있을 경우 관례상 인터페이스 이름 뒤에 Imple을 붙여 사용함
public class MemberServiceImpl implements  MemberService{
//    이 코드가 좋은 코드 인가? 아님
//    추상화에도 의존하고 구현체에도 의존하는 문제 발생 DIP 위반
//    이를 해결하기 위해 AppConfig 이용
//    private final MemberRepository memberRepository = new MemoryMemberRepository();

    private MemberRepository memberRepository;
    public MemberServiceImpl(MemberRepository memberRepository) {
//        추상화에만 의존!! DIP 지킴
//        생성자 주입이라함
        this.memberRepository = memberRepository;
    }


    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }


    // @Configurate 와 싱글톤 테스트 용도
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}