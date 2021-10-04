package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.assertThat;

public class PrototypeProviderTest {

    @Test
    void providerTest(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean ClientBean2 = ac.getBean(ClientBean.class);
        int count2 = ClientBean2.logic();
        assertThat(count2).isEqualTo(1);

    }

    /*
    코드를 이렇게 바꾸는 방법도 있으나
    애플리케이션 컨텍스트 전체를 주입 받게 되면
    스프링 컨테이너에 종속적인 코드가 되고 단위테스트가 어려워짐
    */
//    @Scope("singleton")
//    static class ClientBean {
//        @Autowired
//        private ApplicationContext ac;
//
//        public int logic() {
//            PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class);
//            prototypeBean.addCount();
//            int count = prototypeBean.getCount();
//            return count;
//        }


    /*
    위의 코드는 ApplicationContext 를 전체로 불러왔지만
    아래의 코드는 ObjectProvider를 이용해
    프로로타입 빈을 컨테이너에서 대신 찾아주는 딱 DL 정도의 기능만 제공해준다.
    DL : 의존 관계를 외부에서 주입 받는 것이 아니라 직접 필요한 의존관계를 찾는 것

    ObjectProvider 가 prototype 을 위해서만 사용하는게 아니라
    스프링 컨테이너에서 조회를 할때 직접 조회 해는게 아니라
    ObjectProvider 를 이용해 대신 조회하는 느낌

    ObjectFactory : 기능 단순, 스프링에 의존
    ObjectProvider : ObjectFactory 상속, 별도 기능 추가, 스프링에 의존
    */
//    @Scope("singleton")
//    static class ClientBean {
//
//        private ObjectProvider<PrototypeBean> prototypeBeanProvider;
//
//        @Autowired
//        public ClientBean(ObjectProvider<PrototypeBean> prototypeBeanProvider) {
//            this.prototypeBeanProvider = prototypeBeanProvider;
//        }
//
//        public int logic() {
//            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
//            prototypeBean.addCount();
//            int count = prototypeBean.getCount();
//            return count;
//        }
//    }

    /*
    Provider 라이브러리 추가
    자바 표준이긴 한데 라이브러리를 땡겨야함
    스프링 없어도 테스트 가능
    심플해서 좋음

    실무에서 싱글톤이면 거의다 해결된다네...
    코드 리뷰할때 가끔 보이는데 이때 아 이거구나 하면 됨
    */
    @Scope("singleton")
    static class ClientBean {

        private Provider<PrototypeBean> prototypeBeanProvider;

        @Autowired
        public ClientBean(Provider<PrototypeBean> prototypeBeanProvider) {
            this.prototypeBeanProvider = prototypeBeanProvider;
        }

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }


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

