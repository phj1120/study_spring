package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest(){
//        부모 : ConfigurableApplicationContext
//        자식 : AnnotationConfigApplicationContext
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig{

//        @Bean(initMethod = "init", destroyMethod = "close")
        @Bean
        public NetworkClient networkClient(){
//            url 없이 null 값 출력되는게 당연함
            NetworkClient networkClient = new NetworkClient();
//            객체 생성후 외부에서 값 세팅 할 경우 가 있음
//            이렇게 넣어주면 값이 들어옴
            networkClient.setUrl("http://hello");
            return networkClient;
        }
    }


}
