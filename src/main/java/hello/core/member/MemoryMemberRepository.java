package hello.core.member;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MemoryMemberRepository implements MemberRepository{
//    동시성 이슈가 있을수 있기때문에 C~hashMap을 씀
    private static Map<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
