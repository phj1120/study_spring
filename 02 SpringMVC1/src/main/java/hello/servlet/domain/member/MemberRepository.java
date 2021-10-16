package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberRepository {

//    싱글톤으로 만들 거임, 이건 스프링 이용 안하는 코드니까 하나 하나 만듬
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    private static final MemberRepository instance = new MemberRepository();

    public static MemberRepository getInstance() {
        return instance;
    }

//    생성 되는 것을 막기 위해 private 생성자 만들어 둠
    private MemberRepository() {
    }

    public Member save(Member member) {
        member.setId((++sequence));
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

//    store를 보호하기 위한 메서드드
   public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

//    테스트 같은 곳에서 사용되는 메서드
    public void clearStore() {
        store.clear();
    }
}
