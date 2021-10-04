package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }


    @Test
    void singletonClientUsePrototype(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean ClientBean2 = ac.getBean(ClientBean.class);
        int count2 = ClientBean2.logic();
        assertThat(count2).isEqualTo(2);

        /*
        스프링은 싱글톤이 주가 되기 때문에 싱글톤 빈이 프로토타입 빈을 사용할 것임
        만들었던 의도와는 다르게 싱글톤이 유지됨
            -? 왜?
            싱글톤 빈은 생성 시점에만 의존관계 주입을 받기 때문에,
            프로토 타입 빈이 새로 생성되기는 하지만 싱글톤 빈과 함께 계속 유지된다.
                -> 프로토 타입 빈도 이미 스프링 컨테이너에 등록이 되서 관리 받는 다는 뜻인가?


        */

    }

    @Scope("singleton")
    static class ClientBean {
        private final PrototypeBean prototypeBean;  // 생성 시점에 주입 x01

        @Autowired
        public ClientBean(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

        public int logic() {
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }

    }

    /*
    의존 관계를 주입 받을 때마다 새로 생성되기는 하지만 우리가 원하는 것은 사용할 때마다 새로 생기는 것을 원함
    */
//    @Scope("singleton")
//    static class ClientBean2 {
//        private final PrototypeBean prototypeBean;  // 생성 시점에 주입 x02
//
//        @Autowired
//        public ClientBean2(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }
//
//        public int logic() {
//            prototypeBean.addCount();
//            int count = prototypeBean.getCount();
//            return count;
//        }
//
//    }

    @Scope("prototype")
    static class PrototypeBean {

        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}

