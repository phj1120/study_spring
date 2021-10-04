package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

public class PrototypeTest {

    @Test
    void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        System.out.println("find prototypeBean1");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);

        System.out.println("find PrototypeBean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);
        assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

        prototypeBean1.destroy();
        prototypeBean2.destroy();
        ac.close();
    }
/*
find prototypeBean1
PrototypeBean.init
find PrototypeBean2
PrototypeBean.init
prototypeBean1 = hello.core.scope.PrototypeTest$PrototypeBean@6ee6f53
prototypeBean2 = hello.core.scope.PrototypeTest$PrototypeBean@421bba99
PrototypeBean.destroy
PrototypeBean.destroy

이렇게 출력됨
스프링 빈 조회 후
find ~ bean1 출력
ac.getBean으로 조회 init 호출
find ~ bean2 출력
ac.getBean으로 조회 init 호출
soutv bean1
soutv bean2
수동으로 종료
수동으로 종료


요약하면
싱글톤 스코프 빈은 스프링 컨테이너 생성시 초기화 메소드 실행, 스프링 컨테이너 종료시 종료 메소드 실행
프로토타입 스코프 빈은 스프링 컨테이너에서 빈을 조회할 때 생성, 초기화 메서드 실행, 스프링 컨테이너 종료되도 유지, 직접 종료해야 종료
    - 스프링 컨테이너 요청하때마다 생성
    - 빈의 생성과 의존관계 주입, 초기화 까지만 관여

 */
    @Scope("prototype")
//    @Component 생략 가능 -> AnnotationConfigApplicationContext에 지정해주면 컴포넌트 스캔처럼 동작함
    static class PrototypeBean{
        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean.destroy");
        }
    }
}
